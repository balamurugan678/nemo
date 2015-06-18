package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS;

import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.action.ItemDTOActionDelegate;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Service("travelCardService")
public class TravelCardServiceImpl implements TravelCardService {
    static final Logger logger = LoggerFactory.getLogger(TravelCardServiceImpl.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected ItemDTOActionDelegate cartItemActionDelegate;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CardUpdateService cardUpdateService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected ProductDataService productDataService;
    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;
    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;

    private static final int NO_TRAVEL_ZONE_VALUE = 0;

    @Override
    @Transactional
    public CartDTO addCartItemForExistingCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        assert (cartDTO != null);
        assert (cartItemCmd != null);
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartDTO = cartService.addItem(cartDTO, cartItemCmd, ProductItemDTO.class);
        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO addCartItemForNewCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        assert (cartDTO != null);
        assert (cartItemCmd != null);
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartDTO = cartService.addItem(cartDTO, cartItemCmd, ProductItemDTO.class);
        cartDTO = cartAdminService.applyRefundableDeposit(cartDTO, cartItemCmd);
        cartDTO = cartAdminService.applyShippingCost(cartDTO, cartItemCmd);
        return cartDTO;
    }

    @Override
    public boolean isTicketLongerThanTenMonthsTwelveDays(int diffMonths, int diffDaysExcludingMonths, long diffDays) {
        if (isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(diffMonths, diffDaysExcludingMonths,
                        systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS),
                        systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS))
                        && (diffDays <= systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS))) {
            return true;
        }
        return false;
    }

    protected boolean isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(int diffMonths, int diffDaysExcludingMonths, int maximumAllowedMonths,
                    int maximumAllowedDays) {
        if (diffMonths > maximumAllowedMonths || (diffMonths == maximumAllowedMonths && diffDaysExcludingMonths > maximumAllowedDays)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addPrePayTicketToCubic(CartCmdImpl cmd) {
        assert (cmd != null);
        assert (cmd.getCartDTO() != null);
        return addPrePayTicketsToCubic(cmd.getCartDTO(), cmd.getStationId());
    }

    private boolean addPrePayTicketsToCubic(CartDTO cartDTO, Long stationId) {
        assert (stationId != null);
        CardDTO cardDTO = cardDataService.findById(cartDTO.getCardId());
        if (cardDTO != null && cardDTO.getCardNumber() != null) {
            for (ItemDTO cartItem : cartDTO.getOrder().getOrderItems()) {
                if(addPrePayTicketToCubic(cartItem, cartDTO, cardDTO, stationId)){
                    return true;
                }
                
            }
        }
        return false;
    }

    protected boolean addPrePayTicketToCubic(ItemDTO itemDTO, CartDTO cartDTO, CardDTO cardDTO, Long stationId) {
        Integer cubicRequestNumber = null;
        if (isTravelCard(itemDTO)) {
            ProductItemDTO travelCardItem = (ProductItemDTO) itemDTO;
            PrePaidTicketDTO product = prePaidTicketDataService.findById(travelCardItem.getProductId());
            try {
                cubicRequestNumber = cardUpdateService.requestCardUpdatePrePayTicket(cardDTO.getCardNumber(),
                                Integer.valueOf(product.getAdHocPrePaidTicketCode()), DateUtil.formatDate(travelCardItem.getStartDate()),
                                DateUtil.formatDate(travelCardItem.getEndDate()), travelCardItem.getPrice(), stationId);
                if (null != cubicRequestNumber) {
                    persistRequestedAdHocLoadSettlementRecord(cartDTO, cubicRequestNumber, travelCardItem, stationId);
                } else {
                    persistFailedAdHocLoadSettlementRecord(cartDTO, travelCardItem, stationId);
                    return true;
                }
            } catch (Exception cubicAddPPTException) {
                logger.error(PrivateError.UNEXPECTED.message(), cubicAddPPTException);
                persistFailedAdHocLoadSettlementRecord(cartDTO, travelCardItem, stationId);
                return true;
            }

        }
        return false;
    }

    protected void persistRequestedAdHocLoadSettlementRecord(CartDTO cartDTO, Integer cubicRequestNumber, ProductItemDTO cartItem, Long stationId) {
        AdHocLoadSettlementDTO settlementDTO = adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(cubicRequestNumber,
                        cardDataService.findById(cartItem.getCardId()).getCardNumber());
        if (settlementDTO == null) {
            settlementDTO = new AdHocLoadSettlementDTO();
        }
        updateAdhocLoadSettlementDTO(settlementDTO, cartDTO, cartItem, stationId);
        settlementDTO.setStatus(SettlementStatus.REQUESTED.code());
        settlementDTO.setRequestSequenceNumber(cubicRequestNumber);
        adHocLoadSettlementDataService.createOrUpdate(settlementDTO);
    }

    protected void persistFailedAdHocLoadSettlementRecord(CartDTO cartDTO, ProductItemDTO cartItem, Long stationId) {
        AdHocLoadSettlementDTO settlementDTO = new AdHocLoadSettlementDTO();
        updateAdhocLoadSettlementDTO(settlementDTO, cartDTO, cartItem, stationId);
        settlementDTO.setStatus(SettlementStatus.FAILED.code());
        adHocLoadSettlementDataService.createOrUpdate(settlementDTO);
    }

    protected void updateAdhocLoadSettlementDTO(AdHocLoadSettlementDTO settlementDTO, CartDTO cartDTO, ProductItemDTO travelCardItem, Long stationId) {
        OrderDTO orderDTO = cartDTO.getOrder();
        settlementDTO.setOrderId(orderDTO != null ? orderDTO.getId() : null);
        if (null != cartDTO.getPaymentCardSettlement()) {
            settlementDTO.setSettlementDate(cartDTO.getPaymentCardSettlement().getSettlementDate());
        } else {
            settlementDTO.setSettlementDate(new Date());
        }
        settlementDTO.setItem(travelCardItem);
        settlementDTO.setAmount(travelCardItem.getPrice());
        settlementDTO.setCardId(travelCardItem.getCardId());
        settlementDTO.setExpiresOn(travelCardItem.getEndDate());
        settlementDTO.setPickUpNationalLocationCode(stationId.intValue());
    }

    @Override
    public boolean isTravelCard(ItemDTO itemDTO) {
        boolean travelCardFound = false;
        if (itemDTO instanceof ProductItemDTO) {
            ProductItemDTO travelCardItem = (ProductItemDTO) itemDTO;
            if (null != travelCardItem.getStartZone() && null != travelCardItem.getEndZone() && travelCardItem.getStartZone() > NO_TRAVEL_ZONE_VALUE
                            && travelCardItem.getEndZone() > NO_TRAVEL_ZONE_VALUE) {
                travelCardFound = true;
            }
        }
        return travelCardFound;
    }

    @Override
    public CartDTO addUpdateItems(Long cartId, CartItemCmdImpl cartItemCmd) {
        CartDTO cartDTO = cartService.findById(cartId);
        for (ItemDTO existingItemDTO : cartDTO.getCartItems()) {
            if (existingItemDTO.getClass().equals(ProductItemDTO.class)) {
                ProductItemDTO targetItem = (ProductItemDTO) existingItemDTO;
                updateCartItemForBackdatedRefund(cartItemCmd, targetItem);
                updateCartItemForDeceasedCustomerRefund(cartItemCmd, targetItem);
            }
        }
        cartDTO = cartService.updateCart(cartDTO);
        return cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }

    protected void updateCartItemForBackdatedRefund(CartItemCmdImpl cartItemCmd, ProductItemDTO targetItem) {
        if (cartItemCmd != null && BooleanUtils.isTrue(cartItemCmd.getBackdated())) {
            targetItem.setTicketBackdated(cartItemCmd.getBackdated());
            targetItem.setBackdatedRefundReasonId(cartItemCmd.getBackdatedRefundReasonId());
        }
    }

    protected void updateCartItemForDeceasedCustomerRefund(CartItemCmdImpl cartItemCmd, ProductItemDTO targetItem) {
        if (cartItemCmd != null && cartItemCmd.getDeceasedCustomer() != null && cartItemCmd.getDeceasedCustomer()) {
            targetItem.setDeceasedCustomer(cartItemCmd.getDeceasedCustomer());
        }
    }

}
