package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.AdHocLoadRefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Service("adHocLoadRefundPaymentService")
public class AdHocLoadRefundPaymentServiceImpl implements AdHocLoadRefundPaymentService {
    static final Logger logger = LoggerFactory.getLogger(AdHocLoadRefundPaymentServiceImpl.class);

    @Autowired
    protected CustomerDataService customerDataService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected CardUpdateService cardUpdateService;

    @Autowired
    protected CommonRefundPaymentService commonRefundPaymentService;

    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected OrderDataService orderDataService;

    @Override
    @Transactional
    public void makePayment(CartCmdImpl cmd) {
        commonRefundPaymentService.updateToPayAmount(cmd);
        commonRefundPaymentService.createOrderFromCart(cmd);
        populateStationIdToOrder(cmd);
        commonRefundPaymentService.updateOrderStatusToPaid(cmd);
        commonRefundPaymentService.updateCartItemsWithOrderId(cmd);
        updatePrePayValueInCubic(cmd);
        commonRefundPaymentService.createEvent(cmd);
    }

    protected void updatePrePayValueInCubic(CartCmdImpl cmd) {
        Long stationId = cmd.getStationId();
        if (stationId == null) {
            Long customerId = customerDataService.findByCardNumber(cmd.getCardNumber()).getId();
            stationId = customerService.getPreferredStationId(customerId);
        }
        Integer currency = getCardService.getCard(cmd.getCardNumber()).getPpvDetails().getCurrency();
        int previousCredit = getPreviousCreditForTargetCardNumber(cmd.getTargetCardNumber());
        cmd.setPreviousCreditAmountOnCard(previousCredit);
        Integer cubicRequestNumber = cardUpdateService.requestCardUpdatePrePayValue(cmd.getTargetCardNumber(), stationId,
                        previousCredit + cmd.getToPayAmount(), currency, cmd.getCartType());
        if (cubicRequestNumber != null) {
            updateAdHocLoadSettlement(cmd, cubicRequestNumber);
        }
    }

    protected Integer getPreviousCreditForTargetCardNumber(String targetCardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(targetCardNumber);
        if (isPendingItemPrePayValueForTargetCardNumberAvailable(cardInfoResponseV2DTO)) {
        	Integer sumPrePayValue = 0;
        	for(PrePayValue prePayValue : cardInfoResponseV2DTO.getPendingItems().getPpvs()) {
        		sumPrePayValue += prePayValue.getPrePayValue();
        	}
            return sumPrePayValue;
        }
        return 0;
    }

    protected boolean isPendingItemPrePayValueForTargetCardNumberAvailable(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return cardInfoResponseV2DTO != null &&
                cardInfoResponseV2DTO.getPendingItems() != null &&
                cardInfoResponseV2DTO.getPendingItems().getPpvs() != null &&
                cardInfoResponseV2DTO.getPendingItems().getPpvs().size() > 0;
    }

    protected void updateAdHocLoadSettlement(CartCmdImpl cmd, Integer cubicRequestNumber) {
        CartDTO cart = cmd.getCartDTO();
        CardDTO card = cardDataService.findByCardNumber(cmd.getTargetCardNumber());
        Long cardId = card != null ? card.getId() : null;
        Date settlementExpriresOn = cart.getPayAsYouGoItem() != null ? cart.getPayAsYouGoItem().getEndDate() : null;
        AdHocLoadSettlementDTO adHocLoadSettlementDTO =
                new AdHocLoadSettlementDTO(cart.getOrder().getId(), SettlementStatus.COMPLETE.code(), cart.getCartRefundTotal(),
                        cart.getDateOfRefund(), cardId, cubicRequestNumber, cmd.getTargetPickupLocationId(),
                        settlementExpriresOn);
        adHocLoadSettlementDataService.createOrUpdate(adHocLoadSettlementDTO);
    }

    protected void populateStationIdToOrder(CartCmdImpl cmd) {
        OrderDTO order = cmd.getCartDTO().getOrder();
        order.setStationId(cmd.getStationId());
        orderDataService.createOrUpdate(order);
    }
}
