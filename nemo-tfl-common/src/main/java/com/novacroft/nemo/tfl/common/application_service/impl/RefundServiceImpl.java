package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.PrivateError.UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS;
import static com.novacroft.nemo.tfl.common.constant.PrivateError.UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS_WITH_TYPE_DETAIL;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.TradedTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.application_service.fare_aggregation_service.FareAggregationService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.constant.OysterCardPassengerType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.CubicCardDetailsToCartItemCmdImplConverter;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.JourneyCompletedRefundItemDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundOrderItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.RefundOrchestrationResultDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * New refund service implementation
 */
@Service(value = "refundService")
public class RefundServiceImpl implements RefundService {
    protected static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CustomerDataService customerDataService;

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected CubicCardDetailsToCartItemCmdImplConverter cubicCardDetailsToCartItemCmdImplConverter;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected TravelCardService travelCardService;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected CartAdministrationService cartAdministrationService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected IncompleteJourneyService incompleteJourneyService;
    @Autowired
    protected FareAggregationService fareAggregationService;
    @Autowired
    protected JourneyCompletedRefundItemDataService journeyCompletedRefundItemDataService;
    @Autowired
    protected CardUpdateService cardUpdateService;
    @Autowired
    protected WebCreditService webCreditService;
    @Autowired
    protected RefundScenarioHotListReasonTypeDataService refundScenarioHotListReasonTypeDataService;
    @Autowired
    protected WorkFlowService workFlowService;
    @Autowired
    protected WorkflowItemConverter workflowItemConverter;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected EditRefundPaymentService editRefundPaymentService;
    @Autowired
    protected TradedTravelCardService tradedTravelCardService;
    @Autowired
    protected CardRefundableDepositDataService cardRefundableDepositDataService;

    public RefundServiceImpl() {
        super();
    }

    @Override
    public CartCmdImpl createCartCmdImplWithCartDTO() {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(cartService.createCart());
        return cartCmdImpl;
    }

    @Override
    public CartCmdImpl createCartCmdImplWithCartDTOFromCustomerId(Long customerId, String cartType) {
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(cartService.createCartFromCustomerId(customerId));
        return cartCmdImpl;
    }

    @Override
    public CartDTO updateCartDTOWithCardDetailsInCubic(CartDTO cartDTO, String cardNumber, String refundCartType) {
        if (isCardDetailsAvailableFromCubic(cardNumber)) {
            Set<CartItemCmdImpl> cartItemCmdImpls = cubicCardDetailsToCartItemCmdImplConverter.convertCubicCardDetailsToCartItemCmdImpls(cardNumber,
                            refundCartType);
            persistCartItemCmdImpls(cartDTO, cartItemCmdImpls);

            cartDTO = cartService.findById(cartDTO.getId());
            cartDTO = cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        }
        return cartDTO;
    }

    @Override
    public CartDTO addTravelCardToCart(CartDTO cartDTO, CartCmdImpl cmd, String refundType) {
        updateCartItemCmdImplFromCartDTO(cartDTO, cmd, refundType);
        cartDTO = cartService.addItem(cartDTO, cmd.getCartItemCmd(), ProductItemDTO.class);
        return cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }

    protected void updateCartItemCmdImplFromCartDTO(CartDTO cartDTO, CartCmdImpl cmd, String refundType) {
        if (cmd.getCartItemCmd() != null) {
            cmd.getCartItemCmd().setDateOfRefund(cartDTO.getDateOfRefund());
            cmd.getCartItemCmd().setRefundType(refundType);
            cmd.getCartItemCmd().setPassengerType(OysterCardPassengerType.ADULT.code());
            cmd.getCartItemCmd().setDiscountType(OysterCardDiscountType.NO_DISCOUNT.code());
        }
    }

    protected boolean isCardDetailsAvailableFromCubic(String cardNumber) {
        return getCardInfoResponseV2DTOFromCardNumber(cardNumber) != null ? true : false;
    }

    protected CardInfoResponseV2DTO getCardInfoResponseV2DTOFromCardNumber(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    protected void persistCartItemCmdImpls(CartDTO cartDTO, Set<CartItemCmdImpl> cartItemCmdImpls) {
        for (Iterator<CartItemCmdImpl> it = cartItemCmdImpls.iterator(); it.hasNext();) {
            CartItemCmdImpl cartItemCmdImpl = it.next();
            if (cartItemCmdImpl != null) {
                cartDTO = cartService.findById(cartDTO.getId());
                cartItemCmdImpl.setPassengerType(OysterCardPassengerType.ADULT.code());
                cartItemCmdImpl.setDiscountType(OysterCardDiscountType.NO_DISCOUNT.code());
                cartService.addItem(cartDTO, cartItemCmdImpl, getItemDTOSubclassInstanceForCartItemCmdImpl(cartItemCmdImpl));
            }
        }
    }

    protected Class<? extends ItemDTO> getItemDTOSubclassInstanceForCartItemCmdImpl(CartItemCmdImpl cartItemCmdImpl) {
        if (cartItemCmdImpl.getTicketType().equals(TicketType.PAY_AS_YOU_GO.code())) {
            return PayAsYouGoItemDTO.class;
        } else if (cartItemCmdImpl.getTicketType().equals(TicketType.TRAVEL_CARD.code())) {
            return ProductItemDTO.class;
        } else {
            logger.error(String.format(UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS_WITH_TYPE_DETAIL.message(), cartItemCmdImpl.getTicketType()));
            throw new IllegalArgumentException(UNABLE_TO_DETERMINE_ITEMDTO_SUBCLASS.message());
        }
    }

    protected boolean isCustomerExistsWithCustomerId(Long customerId) {
        CustomerDTO customerDTO = customerDataService.findById(customerId);
        if (customerDTO != null && customerDTO.getId() != null) {
            return true;
        }
        return false;
    }

    protected boolean isCardExistsWithCardId(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        if (cardDTO != null && cardDTO.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteCartForCustomerId(Long customerId) {
        cartService.deleteCartForCustomerId(customerId);
    }

    protected int getRefundAmountInItemDTO(ItemDTO itemDTO) {
        return itemDTO.getPrice();
    }

    @Override
    public CartDTO updateProductItemDTO(Long cartId, Long itemId, String refundCalculationBasis) {
        cartService.updateRefundCalculationBasis(cartId, itemId, refundCalculationBasis);
        CartDTO cartDTO = cartService.findById(cartId);
        return cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }

    @Override
    public CartDTO updatePayAsYouGoItemDTO(CartDTO cartDTO, Integer updatedPrice) {
        Long itemId = getPayAsYouGoItemId(cartDTO);
        return cartService.updatePrice(cartDTO, itemId, updatedPrice);
    }

    protected Long getPayAsYouGoItemId(CartDTO cartDTO) {
        cartDTO = cartService.findById(cartDTO.getId());
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof PayAsYouGoItemDTO) {
                return itemDTO.getId();
            }
        }
        return null;
    }

    @Override
    public CartDTO updateAdministrationFeeItemDTO(CartDTO cartDTO, Integer updatedPrice) {
        Long itemId = getAdministrationFeeItemPriceId(cartDTO);
        return cartService.updatePrice(cartDTO, itemId, updatedPrice);
    }

    protected Long getAdministrationFeeItemPriceId(CartDTO cartDTO) {
        cartDTO = cartService.findById(cartDTO.getId());
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof AdministrationFeeItemDTO) {
                return itemDTO.getId();
            }
        }
        return null;
    }

    @Override
    public CartDTO updateDateOfRefund(CartDTO cartDTO, Date updatedDateOfRefund) {
        CartDTO cartDTOFromDB = cartService.findById(cartDTO.getId());
        cartDTOFromDB.setDateOfRefund(updatedDateOfRefund);
        return updateDateOfRefund(cartDTOFromDB);
    }
    
    public CartDTO updateDateOfRefundAndRefundCalculationBasis(CartDTO cartDTO, Date updatedDateOfRefund, String refundCalculationBasis) {
        CartDTO cartDTOFromDB = cartService.findById(cartDTO.getId());
        cartDTOFromDB.setDateOfRefund(updatedDateOfRefund);
        return updateDateOfRefundAndRefundCalculationBasis(cartDTOFromDB, refundCalculationBasis);
    }

    protected CartDTO updateDateOfRefund(CartDTO cartDTO) {
        boolean isRefundDateUpdated = false;
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof ProductItemDTO) {
                ((ProductItemDTO) itemDTO).setDateOfRefund(cartDTO.getDateOfRefund());
                isRefundDateUpdated = true;
            }
        }
        
        cartDTO = cartService.updateCart(cartDTO);
        if (isRefundDateUpdated) {
            cartDTO =  cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        }
        return cartDTO;
    }
    
    protected CartDTO updateDateOfRefundAndRefundCalculationBasis(CartDTO cartDTO, String refundCalculationBasis) {
        boolean isRefundDateUpdated = false;
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof ProductItemDTO) {
                ((ProductItemDTO) itemDTO).setDateOfRefund(cartDTO.getDateOfRefund());
                ((ProductItemDTO) itemDTO).setRefundCalculationBasis(refundCalculationBasis);
                isRefundDateUpdated = true;
            }
        }
        
        cartDTO = cartService.updateCart(cartDTO);
        if (isRefundDateUpdated) {
            cartDTO =  cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        }
        return cartDTO;
    }

    @Override
    public CartDTO getUpdatedCart(Long cartId) {
        return cartService.findById(cartId);
    }

    @Override
    public CartDTO getCartDTOForRefund(String cardNumber, String cartType, Boolean isGetDetailsFromCubic) {
        Long cardId = cardService.getCardIdFromCardNumber(cardNumber);

        doNotDeleteCartIfItsFromApproval(cardId, isGetDetailsFromCubic);

        CartDTO cartDTO = cartService.createCartFromCardId(cardId);
        cartDTO = getDetailsFromCubic(cartDTO, cardNumber, cartType, isGetDetailsFromCubic);

        cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cardId, cartType);

        cartDTO.setDateOfRefund(new Date());
        updateDateOfRefund(cartDTO);
        if (CartUtil.isDestroyedOrFaildCartType(cartType)) {
            updateCartDTOWithDepositItem(cartDTO, cardNumber);
        }
        cartDTO.setCartType(cartType);
        return cartDTO;
    }

    protected void doNotDeleteCartIfItsFromApproval(Long cardId, Boolean isGetDetailsFromCubic) {
        if (isGetDetailsFromCubic) {
            cartService.deleteCartForCardId(cardId);
        }
    }

    protected CartDTO getDetailsFromCubic(CartDTO cartDTO, String cardNumber, String cartType, boolean isGetDetailsFromCubic) {
        if (isGetDetailsFromCubic) {
            return updateCartDTOWithCardDetailsInCubic(cartDTO, cardNumber, cartType);
        }
        return cartDTO;
    }

    @Override
    public void updatePayAsYouGoValueInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO) {
        if (cartDTO != null) {
            cmd.setPayAsYouGoValue(getPayAsYouGoValueFromCartDTO(cartDTO));
        }
    }

    protected Integer getPayAsYouGoValueFromCartDTO(CartDTO cartDTO) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof PayAsYouGoItemDTO) {
                return itemDTO.getPrice();
            }
        }
        return null;
    }

    @Override
    public void updateAdministrationFeeValueInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO) {
        if (cartDTO != null) {
            cmd.setAdministrationFeeValue(getAdministrationFeeValueFromCartDTO(cartDTO));
        }
    }

    protected Integer getAdministrationFeeValueFromCartDTO(CartDTO cartDTO) {
        for (ItemDTO itemDTO : cartDTO.getCartItems()) {
            if (itemDTO instanceof AdministrationFeeItemDTO) {
                return itemDTO.getPrice();
            }
        }
        return null;
    }

    @Override
    public void updateDateOfRefundInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO) {
        if (cartDTO != null) {
            cmd.setDateOfRefund(getDateOfRefundFromCartDTO(cartDTO));
        }
    }

    protected Date getDateOfRefundFromCartDTO(CartDTO cartDTO) {
        return cartDTO.getDateOfRefund();
    }

    @Override
    public CartDTO getCartDTOUsingCartSessionDataDTOInSession(HttpSession session) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        return cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }

    @Override
    public void storeCartIdInCartSessionDataDTOInSession(HttpSession session, Long cartId) {
        CartUtil.addCartSessionDataDTOToSession(session, new CartSessionData(cartId));
    }

    @Override
    public List<RefundOrderItemDTO> findAllRefundsForCustomer(Long customerId) {
        return orderDataService.findAllRefundsForCustomer(customerId);
    }

    @Override
    public RefundOrderItemDTO findRefundDetailsForIdAndCustomer(Long refundId, Long customerId) {
        return orderDataService.findRefundDetailForItemAndCustomer(refundId, customerId);
    }

    @Override
    public Integer getTotalTicketPrice(List<ItemDTO> cartItemsDto) {
        Integer totalTicketPrice = 0;
        for (ItemDTO itemDto : cartItemsDto) {
            if (itemDto instanceof ProductItemDTO) {
                totalTicketPrice += itemDto.getPrice();
            }
        }
        return totalTicketPrice;

    }

    @Transactional
    @Override
    public CompletedJourneyProcessingResult processRefundsForCompletedJourney(CompleteJourneyCommandImpl command) {
        final RefundOrchestrationResultDTO refundOrchestrationResult = fareAggregationService.orchestrateServicesForRefundSubmission(
                        command.getCardId(), command.getLinkedTransactionTime(), command.getLinkedStationKey(), command.getMissingStationId(),
                        command.getReasonForMissisng(), false);
        if (refundOrchestrationResult.getAutoFillSelfServiceRefundNotificationStatus().equals(
                        AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED)) {
            try {
                final CardDTO cardDTO = cardDataService.findById(command.getCardId());
                final Long stationId = command.getPreferredStation() == null ? command.getPickUpStation() : command.getPreferredStation();
                final String cardNumber = cardDTO.getCardNumber();
                Integer currency = getCardService.getCard(cardNumber).getPpvDetails().getCurrency();
                cardUpdateService.requestCardUpdatePrePayValue(cardNumber, stationId, refundOrchestrationResult.getRefundAmount(), currency, null);
                final OrderDTO orderDto = this.orderDataService.create(new OrderDTO(cardDTO.getWebaccountId(), new Date(), -refundOrchestrationResult
                                .getRefundAmount(), OrderStatus.PAID.code(), stationId));
                journeyCompletedRefundItemDataService
                                .createOrUpdate(new JourneyCompletedRefundItemDTO(command.getCardId(), -refundOrchestrationResult.getRefundAmount(),
                                                orderDto.getId(), new Date(), command.getLinkedTransactionTime(), command.getLinkedStationKey(),
                                                command.getMissingStationId(), stationId.intValue(), null, "", command.getJourneyId()));
            } catch (Exception exception) {
                incompleteJourneyService.notifyAutofill(command.getCardId(), command.getLinkedTransactionTime(), command.getLinkedStationKey(),
                                AutoFillSSRNotificationStatus.SSR_COMMENCED_BUT_FAILED);
                return new CompletedJourneyProcessingResult(null, false);
            }

        }
        if (refundOrchestrationResult.getAutoFillSelfServiceRefundNotificationStatus().equals(
                        AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED)) {
            incompleteJourneyService.notifyAutofill(command.getCardId(), command.getLinkedTransactionTime(), command.getLinkedStationKey(),
                            AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_OF_COMPLETION);
        }

        return new CompletedJourneyProcessingResult(refundOrchestrationResult.getRefundAmount(), refundOrchestrationResult
                        .getAutoFillSelfServiceRefundNotificationStatus().equals(AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED));
    }

    @Override
    public void updatePaymentDetailsInCartCmdImpl(CartCmdImpl cartCmdImpl, CartDTO cartDTO) {

        WorkflowItemDTO workflowItem = workFlowService.getWorkflowItem(Long.toString(cartDTO.getApprovalId()));
        WorkflowCmd workflowCommand = workflowItemConverter.convert(workflowItem);
        workflowCommand.setWorkflowItem(workflowItem);
        cartCmdImpl.setApprovalId(Long.valueOf(workflowItem.getCaseNumber()));
        cartCmdImpl.setPayeeAccountNumber(workflowCommand.getWorkflowItem().getRefundDetails().getPayeeAccountNumber());
        cartCmdImpl.setPayeeSortCode(workflowCommand.getWorkflowItem().getRefundDetails().getPayeeSortCode());

        cartCmdImpl.setTargetCardNumber(workflowCommand.getWorkflowItem().getRefundDetails().getTargetCardNumber());
        cartCmdImpl.setStationId(workflowCommand.getWorkflowItem().getRefundDetails().getStationId());

        cartCmdImpl.setPaymentType(getPaymentType(workflowCommand.getPaymentMethod()));
        cartCmdImpl.setPayeeAddress(workflowCommand.getWorkflowItem().getRefundDetails().getAlternativeAddress());
        getPayeeName(cartCmdImpl, workflowCommand.getPaymentName());

        PayeePaymentDTO payeePayment = workFlowService.getLocalPayeePayment(workflowItem.getTaskId());
        if (payeePayment != null && payeePayment.getIsEdited()) {
            editRefundPaymentService.populateEditedValuesInCartCmdImplFromPayeePayment(cartCmdImpl, payeePayment);
        }
    }

    protected String getPaymentType(String paymentType) {
        if (WEB_CREDIT.equalsIgnoreCase(paymentType)) {
            return PaymentType.WEB_ACCOUNT_CREDIT.code();
        } else if (ADHOC_LOAD.equalsIgnoreCase(paymentType)) {
            return PaymentType.AD_HOC_LOAD.code();
        } else if (PAYMENT_CARD.equalsIgnoreCase(paymentType)) {
            return PaymentType.PAYMENT_CARD.code();
        }

        return paymentType;
    }

    protected CartCmdImpl getPayeeName(CartCmdImpl cmd, String fullName) {
        if (fullName != null) {
            int start = fullName.indexOf(' ');
            int end = fullName.lastIndexOf(' ');
            if (start >= 0) {
                cmd.setFirstName(fullName.substring(0, start));
                if (end > start) {
                    cmd.setInitials(fullName.substring(start + 1, end));
                }
                cmd.setLastName(fullName.substring(end + 1, fullName.length()));
            }
        }
        return cmd;
    }

    @Override
    public void updateCustomerNameAndAddress(String cardNumber, CartCmdImpl cartCmdImpl, CartDTO cartDTO) {
        CustomerDTO customer = customerDataService.findByCardNumber(cardNumber);
        cartCmdImpl.setTitle(customer.getTitle());

        cartCmdImpl.setFirstName(customer.getFirstName());
        cartCmdImpl.setInitials(customer.getInitials());
        cartCmdImpl.setLastName(customer.getLastName());

        AddressDTO address = addressDataService.findById(customer.getAddressId());
        cartCmdImpl.setPayeeAddress(address);
    }

    @Override
    public CartDTO attachPreviouslyExchangedTicketToTheExistingProductItem(CartItemCmdImpl cartItemCmd, CartDTO cartDTO, Long cartItemId) {
        if (isTradedTicketAvailable(cartItemCmd)) {
            ItemDTO itemDTO = cartService.getMatchedProductItemDTOFromCartDTO(cartDTO, ProductItemDTO.class, cartItemId);
            ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
            ProductItemDTO tradedTicketProductItemDTO = tradedTravelCardService.getTravelCardItemForTradedTicket(cartItemCmd, productItemDTO);
            if (null != tradedTicketProductItemDTO) {
                cartDTO.addCartItem(tradedTicketProductItemDTO);
            }
            cartDTO = cartService.updateCart(cartDTO);
        } else {
            cartItemCmd.setPreviouslyExchanged(false);
            cartItemCmd.getTradedTicket().setExchangedDate(null);
            cartItemCmd.getTradedTicket().setTravelCardType(null);
            cartItemCmd.getTradedTicket().setRate(null);
            cartItemCmd.getTradedTicket().setStartDate(null);
        }
        return cartDTO;
    }

    protected boolean isTradedTicketAvailable(CartItemCmdImpl cmd) {
        return cmd.getTradedTicket() != null && cmd.getTradedTicket().getStartZone() != null && cmd.getTradedTicket().getStartZone() > 0;

    }

    @Override
    public CartDTO updateCartDTOWithDepositItem(CartDTO cartDTO, String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
        if (cardInfoResponseV2DTO.getCardDeposit() > 0) {
            CardRefundableDepositItemDTO cardRefundableDepositItemDTO = new CardRefundableDepositItemDTO();
            CardRefundableDepositDTO cardRefundableDepositDTO = cardRefundableDepositDataService.findByPrice(cardInfoResponseV2DTO.getCardDeposit());
            cardRefundableDepositItemDTO.setCardRefundableDepositId(cardRefundableDepositDTO.getId());
            cardRefundableDepositItemDTO.setPrice(cardInfoResponseV2DTO.getCardDeposit());
            cardRefundableDepositItemDTO.setCardId(cartDTO.getCardId());
            cartDTO.getCartItems().add(cardRefundableDepositItemDTO);
        }
        cartService.updateCart(cartDTO);
        return cartDTO;
    }

}
