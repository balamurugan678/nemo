package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.tfl.common.constant.AutoTopUpActivityType.AUTOLOAD;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.resolveEventNameForDecision;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.UIDGenerator;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.cubic_import.PayForAutoLoadPerformedService;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpItemDataService;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpPerformedSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpPerformedSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

/**
 * Take payment for an auto load performed by CUBIC
 */
@Service("payForAutoLoadPerformedService")
public class PayForAutoLoadPerformedServiceImpl implements PayForAutoLoadPerformedService {
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected AutoTopUpDataService autoTopUpDataService;
    @Autowired
    protected AutoTopUpItemDataService autoTopUpItemDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;
    @Autowired
    protected AutoTopUpPerformedSettlementDataService autoTopUpPerformedSettlementDataService;
    @Autowired
    protected UIDGenerator uidGenerator;
    @Autowired
    protected CyberSourceTransactionDataService cyberSourceTransactionDataService;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public Boolean isExistingCard(String cardNumber) {
        return this.cardDataService.findByCardNumber(cardNumber) != null;
    }

    @Override
    @Transactional
    public void payForAutoLoadPerformed(String cardNumber, Integer pickUpLocation, Integer topUpAmount, String currency) {
        CardDTO cardDTO = this.cardDataService.findByCardNumber(cardNumber);
        OrderDTO orderDTO = createOrder(cardDTO, topUpAmount);
        orderDTO.setCardId(cardDTO.getId());
        orderDTO.setStationId(new Long(pickUpLocation));
        AutoTopUpConfigurationItemDTO autoTopUpItemDTO = createAutoTopUpItemDTO(orderDTO, cardDTO, topUpAmount);
        autoTopUpItemDTO.setAutoTopUpActivity(AUTOLOAD.getCode());
        orderDTO.getOrderItems().add(autoTopUpItemDTO);
        PaymentCardSettlementDTO paymentCardSettlementDTO = createSettlementForOrder(orderDTO, topUpAmount);
        CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO =
                requestPayment(preparePaymentRequest(orderDTO, paymentCardSettlementDTO, currency, topUpAmount));
        createAutoTopUpPerformedSettlementForOrder(orderDTO, topUpAmount, paymentCardSettlementDTO);
        paymentCardSettlementDTO =
                updateSettlementWithPaymentGatewayResponse(paymentCardSettlementDTO, cyberSourceSoapReplyDTO);
        orderDTO = updateOrderWithPaymentGatewayResponse(orderDTO, cyberSourceSoapReplyDTO);
        createApplicationEvent(cardDTO, orderDTO, cyberSourceSoapReplyDTO);
    }

    protected OrderDTO createOrder(CardDTO cardDTO, Integer topUpAmount) {
        return this.orderDataService
                .create(new OrderDTO(cardDTO.getCustomerId(), new Date(), topUpAmount, OrderStatus.NEW.code()));
    }

    protected AutoTopUpConfigurationItemDTO addItemToOrder(OrderDTO orderDTO, CardDTO cardDTO, Integer topUpAmount) {
        AutoTopUpDTO autoTopUpDTO = this.autoTopUpDataService.findByAutoTopUpAmount(topUpAmount);
        if (autoTopUpDTO == null) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.INVALID_AUTO_AMOUNT.message(), topUpAmount, cardDTO.getCardNumber()));
        }
        return this.autoTopUpItemDataService.createOrUpdate(
                new AutoTopUpConfigurationItemDTO(orderDTO.getId(), cardDTO.getId(), autoTopUpDTO.getId(), topUpAmount, topUpAmount));
    }

    protected AutoTopUpConfigurationItemDTO createAutoTopUpItemDTO(OrderDTO orderDTO, CardDTO cardDTO, Integer topUpAmount) {
        AutoTopUpDTO autoTopUpDTO = this.autoTopUpDataService.findByAutoTopUpAmount(topUpAmount);
        if (autoTopUpDTO == null) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.INVALID_AUTO_AMOUNT.message(), topUpAmount, cardDTO.getCardNumber()));
        }
        return new AutoTopUpConfigurationItemDTO(orderDTO.getId(), cardDTO.getId(), autoTopUpDTO.getId(), topUpAmount, topUpAmount);
    }
    
    protected PaymentCardSettlementDTO createSettlementForOrder(OrderDTO orderDTO, Integer topUpAmount) {
        PaymentCardSettlementDTO paymentCardSettlementDTO = new PaymentCardSettlementDTO(orderDTO.getId(), topUpAmount, this.uidGenerator.getIdAsString());
        return this.paymentCardSettlementDataService.createOrUpdate(paymentCardSettlementDTO);
    }

    protected void createAutoTopUpPerformedSettlementForOrder(OrderDTO orderDTO, Integer topUpAmount, PaymentCardSettlementDTO paymentCardSettlementDTO) {
        AutoTopUpPerformedSettlementDTO autoTopUpPerformedSettlementDTO = new AutoTopUpPerformedSettlementDTO(orderDTO.getId(),
                                                                                                              SettlementStatus.ACCEPTED.code(),
                                                                                                              paymentCardSettlementDTO.getSettlementDate(),
                                                                                                              topUpAmount,
                                                                                                              AutoLoadState.lookUpState(topUpAmount));
        autoTopUpPerformedSettlementDataService.createOrUpdate(autoTopUpPerformedSettlementDTO);
    }

    protected CyberSourceSoapRequestDTO preparePaymentRequest(OrderDTO orderDTO,
                                                              PaymentCardSettlementDTO paymentCardSettlementDTO,
                                                              String currency, Integer topUpAmount) {
        // TODO validate payment card and add token - waiting for info on API
        CustomerDTO customerDTO = this.customerDataService.findById(orderDTO.getCustomerId());
        return new CyberSourceSoapRequestDTO(String.valueOf(orderDTO.getOrderNumber()),
                paymentCardSettlementDTO.getTransactionUuid(), currency, topUpAmount, customerDTO.getId());
    }

    protected CyberSourceSoapReplyDTO requestPayment(CyberSourceSoapRequestDTO cyberSourceSoapRequestDTO) {
        return this.cyberSourceTransactionDataService.runTransaction(cyberSourceSoapRequestDTO);
    }

    protected PaymentCardSettlementDTO updateSettlementWithPaymentGatewayResponse(
            PaymentCardSettlementDTO paymentCardSettlementDTO, CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        return this.paymentService
                .updatePaymentCardSettlementWithPaymentGatewayResponse(paymentCardSettlementDTO, cyberSourceSoapReplyDTO);
    }

    protected OrderDTO updateOrderWithPaymentGatewayResponse(OrderDTO orderDTO, CyberSourceReplyDTO cyberSourceReplyDTO) {
        OrderDTO orderDTOWithStatusSet = this.paymentService.updateOrderStatusWithPaymentGatewayResponse(orderDTO, cyberSourceReplyDTO);
        return this.orderDataService.createOrUpdate(orderDTOWithStatusSet);
    }

    protected void createApplicationEvent(CardDTO cardDTO, OrderDTO orderDTO, CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        this.applicationEventService
                .create(null, resolveEventNameForDecision(cyberSourceSoapReplyDTO.getDecision()),
                        buildAdditionalInformation(cardDTO, orderDTO, cyberSourceSoapReplyDTO));
    }

    protected String buildAdditionalInformation(CardDTO cardDTO, OrderDTO orderDTO,
                                                CyberSourceSoapReplyDTO cyberSourceSoapReplyDTO) {
        return new InformationBuilder().append("Card Number [%s]", cardDTO.getCardNumber())
                .append("; Order Number [%s]", orderDTO.getOrderNumber())
                .append("; Transaction Result [%s]", cyberSourceSoapReplyDTO.getDecision())
                .append("; Transaction ID [%s]", cyberSourceSoapReplyDTO.getTransactionId())
                .append("; Authorisation Reference [%s]", cyberSourceSoapReplyDTO.getTransactionReference())
                .append("; Authorised At [%s]", cyberSourceSoapReplyDTO.getAuthorizedAt()).toString();
    }
}
