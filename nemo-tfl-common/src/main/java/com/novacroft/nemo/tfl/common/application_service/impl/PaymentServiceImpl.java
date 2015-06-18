package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsInteger;
import static com.novacroft.nemo.common.utils.CyberSourceDateUtil.parseDateAndTime;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine1;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine2;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isAccepted;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isCancelled;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isIncomplete;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.resolveEventNameForDecision;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.UIDGenerator;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PaymentDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;

/**
 * Payment service implementation
 */
@Service("paymentService")
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;
    @Autowired
    protected UIDGenerator uidGenerator;
    @Autowired
    protected ApplicationEventService applicationEventService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected AutoTopUpConfigurationService autoTopUpConfigurationService;
    @Autowired
    protected WebCreditService webCreditService;
    @Autowired
    protected PaymentCardService paymentCardService;
    @Autowired
    protected WebCreditSettlementDataService webAccountCreditSettlementDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected BaseEmailPreparationService baseEmailPreparationService;
    @Autowired
    protected AutoTopUpConfirmationEmailService autoTopUpConfirmationEmailService;
    @Autowired
    protected SecurityService securityService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected FulfilmentQueuePopulationService fulfilmentQueuePopulationService;
    @Autowired
    protected CustomerService customerService;

    @Override
    @Transactional
    public CartCmdImpl createOrderAndSettlementsFromManagedAutoTopUp(CartDTO cartDTO, CartCmdImpl cmd) {
        OrderDTO orderDTO = createOrderFromCart(cmd.getCustomerId(), cmd.getTotalAmt(), cmd.getStationId());
        PaymentCardSettlementDTO settlementDTO = createPaymentCardSettlementForAutoTopUpOrder(orderDTO.getId(), cmd.getToPayAmount(),
                        cmd.getPaymentCardId());
        cartDTO.setPaymentCardSettlement(settlementDTO);
        orderDTO = updateOrderStatusAndItems(cartDTO, orderDTO);
        moveCartItemsToOrder(cartDTO, orderDTO);
        cartDTO.setOrder(orderDTO);
        cmd.setCartDTO(cartDTO);
        sendConfirmationEmail(cmd);
        return cmd;
    }

    @Override
    @Transactional
    public CartCmdImpl createOrderAndSettlementsFromCart(CartDTO cartDTO, CartCmdImpl cmd) {
        PaymentCardSettlementDTO settlementDTO = null;
        OrderDTO orderDTO = createOrderFromCart(cmd.getCustomerId(), cmd.getTotalAmt(), cmd.getStationId());
        cartDTO.setOrder(orderDTO);
        moveCartItemsToOrder(cartDTO, orderDTO);
        settlementDTO = createPaymentCardSettlementForOrder(orderDTO.getId(), cmd.getToPayAmount());
        cartDTO.setPaymentCardSettlement(settlementDTO);
        processWebCreditApplyAmount(orderDTO.getId(), cmd.getWebCreditApplyAmount());
        handleAutoTopUpChange(cmd, cartDTO);
        cmd.setCartDTO(cartDTO);
        return cmd;
    }

    protected void sendConfirmationEmail(CartCmdImpl cmd) {
        EmailArgumentsDTO emailArguments = new EmailArgumentsDTO();
        CardDTO cardDTO = this.cardDataService.findById(cmd.getCardId());
        CustomerDTO customerDTO = this.customerDataService.findById(cardDTO.getCustomerId());
        emailArguments.setToAddress(customerDTO.getEmailAddress());
        emailArguments.setSalutation(this.baseEmailPreparationService.getSalutation(customerDTO));
        emailArguments.setPickUpLocationName(cmd.getStationName());
        emailArguments.setRangeFrom(DateUtil.parse(cmd.getStartDate()));
        emailArguments.setRangeTo(DateUtil.parse(cmd.getEndDate()));
        emailArguments.setReferenceNumber(cmd.getCartDTO().getOrder().getOrderNumber());
        this.autoTopUpConfirmationEmailService.sendConfirmationMessage(emailArguments);

    }

    @Override
    @Transactional
    @Deprecated
    public CartCmdImpl processPaymentGatewayReply(CartDTO cartDTO, CartCmdImpl cmd) {
        cmd.setCardId(cartDTO.getCardId());
        OrderDTO orderDTO = processPaymentGateway(cmd);
        deleteCartRecord(cartDTO, orderDTO);

        return cmd;
    }

    private OrderDTO processPaymentGateway(CartCmdImpl cmd) {
        OrderDTO orderDTO = cmd.getCartDTO().getOrder();
        orderDTO = setOrderStatusFromCybersourceReply(cmd, orderDTO);
        orderDTO = createCardWithSecurityQuestionsIfFirstIssueOrReplacement(cmd, orderDTO);

        cmd.getCartDTO().setOrder(updateOrderRecord(orderDTO));
        pushCartItemsToCubic(cmd);

        PaymentCardSettlementDTO settlementDTO = updateSettlements(cmd);
        cmd.getCartDTO().setPaymentCardSettlement(settlementDTO);

        paymentCardService.createPaymentCardOnTokenRequest(cmd);
        paymentCardService.linkPaymentCardToCardOnAutoLoadOrder(cmd.getCartDTO().getOrder());
        setFulfilmentStatusQueue(cmd);
        createEvent(cmd);
        return orderDTO;
    }

    protected OrderDTO createCardWithSecurityQuestionsIfFirstIssueOrReplacement(CartCmdImpl cmd, OrderDTO orderDTO) {
        if (cmd.isFirstIssueOrder() || cmd.isReplacementOrder()) {
            orderDTO.setCardId(customerService
                            .createCardWithSecurityQuestion(cmd.getCustomerId(), cmd.getSecurityQuestion(), cmd.getSecurityAnswer()).getId());
        }
        return orderDTO;
    }

    protected void setFulfilmentStatusQueue(CartCmdImpl cmd) {
        fulfilmentQueuePopulationService.determineFulfilmentQueueAndUpdateOrderStatus(cmd.isFirstIssueOrder(), cmd.isReplacementOrder(), cmd
                        .getCartDTO().getOrder());
    }

    @Override
    @Transactional
    public CartCmdImpl processPaymentGatewayReply(CartCmdImpl cmd) {
        OrderDTO orderDTO = processPaymentGateway(cmd);
        deleteCartRecord(cmd.getCartDTO(), orderDTO);
        return cmd;
    }

    protected void pushCartItemsToCubic(CartCmdImpl cmd) {
        OrderDTO orderDTO = cmd.getCartDTO().getOrder();
        if (isOrderStatusPaid(orderDTO) && isExistingOysterCard(cmd.getCardId()) && isExisitingStation(cmd.getStationId())) {
            boolean prePayValueUpdateError = false;
            boolean prePayTicketUpdateError = false;
            prePayValueUpdateError = payAsYouGoService.updatePrePayValueToCubic(cmd);
            if (!prePayValueUpdateError) {
                prePayTicketUpdateError = travelCardService.addPrePayTicketToCubic(cmd);
            }
            verifyCubicUpdatesAllSuccessful(prePayValueUpdateError, prePayTicketUpdateError);
        }
    }

    protected void verifyCubicUpdatesAllSuccessful(boolean prePayValueUpdateError, boolean prePayTicketUpdateError) {
        if (prePayTicketUpdateError || prePayValueUpdateError) {
            String errorText = String.format(PrivateError.CARD_UPDATE_REQUEST_ERROR_DETECTED.message(), prePayValueUpdateError,
                            prePayTicketUpdateError);
            logger.error(errorText);
            throw new ApplicationServiceException(errorText);
        }
    }

    protected void deleteCartRecord(CartDTO cartDTO, OrderDTO orderDTO) {
        if (isOrderStatusPaid(orderDTO)) {
            cartService.deleteCart(cartDTO.getId());
        }
    }

    private boolean isOrderStatusPaid(OrderDTO orderDTO) {
        return OrderStatus.PAID.code().equals(orderDTO.getStatus());
    }

    @Override
    public PaymentDetailsCmdImpl populatePaymentDetails(Long customerId) {
        PaymentDetailsCmdImpl cmd = new PaymentDetailsCmdImpl();

        CustomerDTO customerIDDTO = this.customerDataService.findById(customerId);
        cmd.setBillToFirstName(customerIDDTO.getFirstName());
        cmd.setBillToLastName(customerIDDTO.getLastName());
        cmd.setBillToEmail(customerIDDTO.getEmailAddress());

        AddressDTO addressDTO = this.addressDataService.findById(customerIDDTO.getAddressId());
        cmd.setBillToAddressLine1(formatLine1(addressDTO.getHouseNameNumber(), addressDTO.getStreet()));
        cmd.setBillToAddressLine2(formatLine2(addressDTO.getHouseNameNumber(), addressDTO.getStreet()));
        cmd.setBillToAddressCity(addressDTO.getTown());
        cmd.setBillToAddressPostalCode(addressDTO.getPostcode());
        cmd.setBillToAddressCountry(addressDTO.getCountry());
        return cmd;
    }

    @Override
    @Transactional
    public PaymentCardSettlementDTO updatePaymentCardSettlementWithPaymentGatewayResponse(PaymentCardSettlementDTO paymentCardSettlementDTO,
                    CyberSourceReplyDTO cyberSourceReplyDTO) {
        paymentCardSettlementDTO.setStatus(resolveNextPaymentCardSettlementStatus(cyberSourceReplyDTO.getDecision()));
        paymentCardSettlementDTO.setDecision(cyberSourceReplyDTO.getDecision());
        paymentCardSettlementDTO.setMessage(cyberSourceReplyDTO.getMessage());
        paymentCardSettlementDTO.setReasonCode(cyberSourceReplyDTO.getReasonCode());
        paymentCardSettlementDTO.setTransactionId(cyberSourceReplyDTO.getTransactionId());
        paymentCardSettlementDTO.setAuthorisedAmount(convertPoundsAndPenceAsStringToPenceAsInteger(cyberSourceReplyDTO.getTransactionAmount()));
        paymentCardSettlementDTO.setAuthorisationTime(parseDateAndTime(cyberSourceReplyDTO.getTransactionAt()));
        paymentCardSettlementDTO.setAuthorisationTransactionReferenceNumber(cyberSourceReplyDTO.getTransactionReference());
        return this.paymentCardSettlementDataService.createOrUpdate(paymentCardSettlementDTO);
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatusWithPaymentGatewayResponse(OrderDTO orderDTO, CyberSourceReplyDTO cyberSourceReplyDTO) {
        orderDTO.setStatus(resolveNextOrderStatus(cyberSourceReplyDTO.getDecision()));
        return orderDTO;
    }

    protected OrderDTO createOrderFromCart(Long customerId, Integer totalAmount, Long stationId) {
        OrderDTO orderDTO = new OrderDTO(customerId, new Date(), totalAmount, OrderStatus.NEW.code(), stationId);
        orderDTO = this.orderDataService.create(orderDTO);
        return orderDTO;
    }

    protected PaymentCardSettlementDTO createPaymentCardSettlementForOrder(Long orderId, Integer amount) {
        if (isPaymentCardSettlementUsed(amount)) {
            return this.paymentCardSettlementDataService.createOrUpdate(new PaymentCardSettlementDTO(orderId, amount, this.uidGenerator
                            .getIdAsString()));
        }
        return null;
    }

    protected PaymentCardSettlementDTO createPaymentCardSettlementForAutoTopUpOrder(Long orderId, Integer amount, Long paymentCardid) {
        if (isPaymentCardSettlementUsed(amount)) {
            return this.paymentCardSettlementDataService.createOrUpdate(new PaymentCardSettlementDTO(orderId, amount, this.uidGenerator
                            .getIdAsString(), paymentCardid));
        }
        return null;
    }

    protected String resolveNextOrderStatus(String decision) {
        if (isAccepted(decision)) {
            return OrderStatus.PAID.code();
        } else if (isCancelled(decision)) {
            return OrderStatus.CANCELLED.code();
        } else if (isIncomplete(decision)) {
            return OrderStatus.ERROR.code();
        }
        throw new ApplicationServiceException(String.format(PrivateError.INVALID_PAYMENT_GATEWAY_DECISION.message(), decision));
    }

    @Override
    public Long getDefaultPaymentCardId(CartCmdImpl cmd) {
        Long cardId = getCardFromCart(cmd);
        if (null == cardId) {
            return null;
        }
        return this.cardDataService.findById(cardId).getPaymentCardId();
    }

    protected Long getCardFromCart(CartCmdImpl cmd) {
        if (null != cmd.getCardId()) {
            return cmd.getCardId();
        }
        for (CartItemCmdImpl itemCmd : cmd.getCartItemList()) {
            if (null != itemCmd.getCardId()) {
                return itemCmd.getCardId();
            }
        }
        return null;
    }

    protected String resolveNextPaymentCardSettlementStatus(String decision) {
        if (isAccepted(decision)) {
            return SettlementStatus.ACCEPTED.code();
        } else if (isCancelled(decision)) {
            return SettlementStatus.CANCELLED.code();
        } else if (isIncomplete(decision)) {
            return SettlementStatus.INCOMPLETE.code();
        }
        throw new ApplicationServiceException(String.format(PrivateError.INVALID_PAYMENT_GATEWAY_DECISION.message(), decision));
    }

    protected String resolveNextWebCreditSettlementStatus(CartCmdImpl cmd) {
        if (isWebCreditSettlementUsed(cmd.getToPayAmount())) {
            return SettlementStatus.ACCEPTED.code().equals(
                            resolveNextPaymentCardSettlementStatus(cmd.getCartDTO().getCyberSourceReply().getDecision())) ? SettlementStatus.COMPLETE
                            .code() : SettlementStatus.INCOMPLETE.code();
        }
        return SettlementStatus.COMPLETE.code();
    }

    protected OrderDTO updateOrderStatusAndItems(CartDTO cartDTO, OrderDTO orderDTO) {
        if (isOrderStatusPaid(orderDTO)) {
            moveCartItemsToOrder(cartDTO, orderDTO);
        }
        orderDTO = updateOrderRecord(orderDTO);
        return orderDTO;
    }

    protected OrderDTO setOrderStatusFromCybersourceReply(CartCmdImpl cmd, OrderDTO orderDTO) {
        String newOrderStatus = (cmd.getToPayAmount() > 0) ? resolveNextOrderStatus(cmd.getCartDTO().getCyberSourceReply().getDecision())
                        : OrderStatus.PAID.code();
        orderDTO.setStatus(newOrderStatus);
        return orderDTO;
    }

    protected OrderDTO moveCartItemsToOrder(CartDTO cartDTO, OrderDTO orderDTO) {
        List<ItemDTO> orderItems = new ArrayList<ItemDTO>();
        for (ItemDTO cartItemDTO : cartDTO.getCartItems()) {
            cartItemDTO.setId(null);
            cartItemDTO.setNullable(true);
            cartItemDTO.setCartId(null);
            cartItemDTO.setOrderId(orderDTO.getId());
            cartItemDTO.setExternalId(null);
            orderItems.add(cartItemDTO);
        }
        orderDTO.setOrderItems(orderItems);
        orderDTO.setCardId(cartDTO.getCardId());
        return updateOrderRecord(orderDTO);
    }

    protected OrderDTO updateOrderRecord(OrderDTO orderDTO) {
        return this.orderDataService.createOrUpdate(orderDTO);
    }

    protected PaymentCardSettlementDTO updateSettlements(CartCmdImpl cmd) {
        PaymentCardSettlementDTO settlementDTO = cmd.getCartDTO().getPaymentCardSettlement();
        if (isPaymentCardSettlementUsed(cmd.getToPayAmount())) {
            settlementDTO = updatePaymentCardSettlementRecord(cmd);
        }
        if (isWebCreditSettlementUsed(cmd.getWebCreditApplyAmount())) {
            updateWebAccountCreditSettlementRecord(cmd);
        }
        return settlementDTO;
    }

    protected PaymentCardSettlementDTO updatePaymentCardSettlementRecord(CartCmdImpl cmd) {
        assert (cmd.getCartDTO().getPaymentCardSettlement() != null);
        PaymentCardSettlementDTO settlementDTO = cmd.getCartDTO().getPaymentCardSettlement();
        settlementDTO = updatePaymentCardSettlementWithPaymentGatewayResponse(settlementDTO, cmd.getCartDTO().getCyberSourceReply());
        return settlementDTO;
    }

    protected void updateWebAccountCreditSettlementRecord(CartCmdImpl cmd) {
        List<WebCreditSettlementDTO> webCreditSettlements = this.webAccountCreditSettlementDataService.findByOrderId(cmd.getCartDTO().getOrder()
                        .getId());
        String newSettlementStatus = resolveNextWebCreditSettlementStatus(cmd);
        if (CollectionUtils.isNotEmpty(webCreditSettlements)) {
            for (SettlementDTO settlementDTO : webCreditSettlements) {
                settlementDTO.setStatus(newSettlementStatus);
                this.webAccountCreditSettlementDataService.createOrUpdate((WebCreditSettlementDTO) settlementDTO);
            }
        }
    }

    protected void createEvent(CartCmdImpl cmd) {
        EventName eventName = (isPaymentCardSettlementUsed(cmd.getToPayAmount())) ? resolveEventName(cmd.getCartDTO().getCyberSourceReply()
                        .getDecision()) : EventName.PAYMENT_RESOLVED;
        this.applicationEventService.create(cmd.getCustomerId(), eventName, buildAdditionalInformation(cmd));
    }

    protected EventName resolveEventName(String decision) {
        return resolveEventNameForDecision(decision);
    }

    protected String buildAdditionalInformation(CartCmdImpl cmd) {
        PayAsYouGoItemDTO payAsYouGoItem = cmd.getCartDTO().getPayAsYouGoItem();
        String typeOfOrder = "";
        if (null != payAsYouGoItem) {
            typeOfOrder = typeOfOrder + "Pay As You Go";
        }

        for (ItemDTO cartItem : cmd.getCartDTO().getCartItems()) {
            if (travelCardService.isTravelCard(cartItem)) {
                typeOfOrder = typeOfOrder + " Travel Card";
                break;
            }
        }

        InformationBuilder additionalInformation = new InformationBuilder().append(typeOfOrder + " Purchase" + "; Order Number [%s]", cmd
                        .getCartDTO().getOrder().getOrderNumber());
        if (isPaymentCardSettlementUsed(cmd.getToPayAmount())) {
            additionalInformation = additionalInformation.append("; Mode Of Payment [Payment Card]").append("; amount [%s]", cmd.getToPayAmount())
                            .append("; Transaction Result [%s]", cmd.getCartDTO().getCyberSourceReply().getDecision())
                            .append("; Authorised At [%s]", cmd.getCartDTO().getCyberSourceReply().getTransactionAt());
            additionalInformation.append(StringUtil.isNotEmpty(cmd.getCartDTO().getCyberSourceReply().getTransactionId()) ? "; Transaction ID ["
                            + cmd.getCartDTO().getCyberSourceReply().getTransactionId() + "]" : "");
            additionalInformation
                            .append(StringUtil.isNotEmpty(cmd.getCartDTO().getCyberSourceReply().getTransactionReference()) ? "; Authorisation Reference ["
                                            + cmd.getCartDTO().getCyberSourceReply().getTransactionReference() + "]"
                                            : "");
            if (StringUtil.isEmpty(cmd.getCartDTO().getCyberSourceReply().getTransactionId())) {
                additionalInformation.append(StringUtil.isNotEmpty(cmd.getCartDTO().getCyberSourceReply().getRequestId()) ? "; Request Id ["
                                + cmd.getCartDTO().getCyberSourceReply().getRequestId() + "]" : "");
            }
        }

        if (isWebCreditSettlementUsed(cmd.getWebCreditApplyAmount())) {
            additionalInformation = additionalInformation.append("; Mode Of Payment [Web Credit]").append("; amount [%s]",
                            cmd.getWebCreditApplyAmount());
        }

        return additionalInformation.toString();
    }

    protected void handleAutoTopUpChange(CartCmdImpl cmd, CartDTO cartDTO) {
        if (isExisitingStation(cmd.getStationId()) && cmd.getAutoTopUpAmount() != 0) {
            this.autoTopUpConfigurationService.changeConfiguration(cmd.getCardId(), cartDTO.getOrder().getId(), cmd.getAutoTopUpAmount(),
                            cmd.getStationId());
        }
    }

    protected boolean isExistingOysterCard(Long cardId) {
        CardDTO cardDTO = cardDataService.findById(cardId);
        return cardDTO != null && cardDTO.getCardNumber() != null;
    }

    protected boolean isExisitingStation(Long stationId) {
        return stationId != null;
    }

    protected void processWebCreditApplyAmount(Long orderId, Integer webCreditApplyAmount) {
        if (isWebCreditSettlementUsed(webCreditApplyAmount)) {
            this.webCreditService.applyWebCreditToOrder(orderId, webCreditApplyAmount);
        }
    }

    protected boolean isPaymentCardSettlementUsed(Integer toPayAmount) {
        return toPayAmount > 0;
    }

    protected boolean isWebCreditSettlementUsed(Integer webCreditApplyAmount) {
        return webCreditApplyAmount > 0;
    }

}
