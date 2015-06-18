package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.tfl.common.util.PaymentCardUtil.createDisplayName;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isAccepted;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isCancelled;
import static com.novacroft.nemo.tfl.common.util.cyber_source.CyberSourceUtil.isIncomplete;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourcePostService;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSoapService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AddPaymentCardAction;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.CookieConstant;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceHeartbeatDataService;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.form_validator.AddPaymentCardActionValidator;
import com.novacroft.nemo.tfl.common.form_validator.CyberSourcePostReplyValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourcePostReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Controller to handle interaction with the payment gateway.
 * <ul>
 * <li>Shows payment summary</li>
 * <li>Payment summary page posts directly to payment gateway (i.e. does not pass through this controller)</li>
 * <li>Payment gateway posts back to this controller with payment transaction outcome</li>
 * <li>Gateway post back also captures customer and card tokens where applicable</li>
 * </ul>
 */
@Controller
@RequestMapping(PageUrl.PAYMENT)
public class BasePaymentController extends BasePurchaseController {
    static final Logger logger = LoggerFactory.getLogger(BasePaymentController.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected CyberSourcePostReplyValidator cyberSourcePostReplyValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected PaymentCardSelectListService paymentCardSelectListService;
    @Autowired
    protected AddPaymentCardActionValidator addPaymentCardActionValidator;
    @Autowired
    protected CyberSourcePostService cyberSourcePostService;
    @Autowired
    protected CyberSourceSoapService cyberSourceSoapService;
    @Autowired
    protected PaymentCardDataService paymentCardDataService;
    @Autowired
    protected CyberSourceTransactionDataService cyberSourceTransactionDataService;
    @Autowired
    protected CyberSourceHeartbeatDataService cyberSourceHeartbeatDataService;
    @Autowired
    protected PaymentCardService paymentCardService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected FulfilmentQueuePopulationService fulfilmentQueuePopulationService;

    @Value("${CyberSource.post.url:unknown}")
    protected String paymentGatewayUrl;

    @ModelAttribute(PageAttribute.PAYMENT_GATEWAY_URL)
    public String getPaymentGatewayUrl() {
        return this.paymentGatewayUrl;
    }

    @ModelAttribute
    public void populatePaymentCardTypeSelectList(Model model) {
        model.addAttribute(PageAttribute.PAYMENT_CARD_TYPES, selectListService.getSelectList(PageSelectList.PAYMENT_CARD_TYPES));
    }

    @ModelAttribute
    public void populateCountrySelectList(Model model) {
        model.addAttribute(PageAttribute.COUNTRIES, this.countrySelectListService.getSelectList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showChoosePaymentCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, HttpServletResponse response) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
        return (isCardPaymentRequired(cartSessionData)) ? getShowChoosePaymentCardView(cartDTO, cmd, response) : getConfirmPaymentView(cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SELECT_PAYMENT_CARD, method = RequestMethod.POST)
    public ModelAndView choosePaymentCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result,
                    @CookieValue(value = CookieConstant.COOKIE_STATUS, defaultValue = CookieConstant.COOKIE_STATUS_OFF) String cookieStatus,
                    HttpServletRequest request) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        this.addPaymentCardActionValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return getShowChoosePaymentCardView(cartDTO, cmd);
        }
        updateOrderAndPaymentCardSettlementToCmd(cartSessionData, cartDTO, cmd);
        if (isAddPaymentCard(cmd)) {
            return getEnterPaymentCardDetailsView(cartDTO, cmd, result, cookieStatus, request);
        } else {
            this.paymentCardService.updateSettlementWithPaymentCard(cmd.getCartDTO().getOrder(), Long.parseLong(cmd.getPaymentCardAction()));
            return getConfirmExistingCardView(cmd, request);
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONFIRM_SAVED_PAYMENT_CARD_PAYMENT, method = RequestMethod.POST)
    public ModelAndView payUsingSavedPaymentCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, HttpServletRequest request) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        updateOrderAndPaymentCardSettlementToCmd(cartSessionData, cartDTO, cmd);
        setCartCmdPropertiesForProcessPaymentGatewayReply(cartSessionData, cartDTO, cmd);
        if (isCardPaymentRequired(cartSessionData)) {
            cmd.getCartDTO().setCyberSourceRequest(
                            this.cyberSourceSoapService.preparePaymentRequestData(cmd.getCartDTO().getOrder(), cmd.getCartDTO()
                                            .getPaymentCardSettlement(), getClientIpAddress(request)));
            cmd.getCartDTO().setCyberSourceReply(
                            this.cyberSourceTransactionDataService.runTransaction((CyberSourceSoapRequestDTO) cmd.getCartDTO()
                                            .getCyberSourceRequest()));
            return new ModelAndView(resolveViewName(cmd.getCartDTO().getCyberSourceReply()), PageCommand.CART,
                            this.paymentService.processPaymentGatewayReply(cmd));
        } else {
            CartUtil.removeCartSessionDataDTOFromSession(session);
            return new ModelAndView(PageView.PAYMENT_ACCEPTED, PageCommand.CART, this.paymentService.processPaymentGatewayReply(cmd));
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView receiveCyberSourcePostReply(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd, BindingResult result,
                    @RequestParam Map<String, String> replyParameters) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        updateOrderAndPaymentCardSettlementToCmd(cartSessionData, cartDTO, cartCmd);
        CyberSourcePostReplyDTO cyberSourcePostReply = new CyberSourcePostReplyDTO(replyParameters);
        cartCmd.getCartDTO().setCyberSourceReply(cyberSourcePostReply);
        logger.debug(String.format("Payment Gateway Reply: %s", cyberSourcePostReply.toString()));
        this.cyberSourcePostReplyValidator.validate(cartCmd.getCartDTO(), result);
        if (result.hasErrors()) {
            logger.error(String.format(PrivateError.INVALID_PAYMENT_GATEWAY_REPLY.message(), cyberSourcePostReply.toString()));
            throw new ControllerException(PrivateError.INVALID_GATEWAY_REPLY.message());
        }
        setCartCmdPropertiesForProcessPaymentGatewayReply(cartSessionData, cartDTO, cartCmd);
        String viewName = resolveViewName(cyberSourcePostReply);
        if (PageView.PAYMENT_ACCEPTED.equals(viewName)) {
            CartUtil.removeCartSessionDataDTOFromSession(session);
        }
        return new ModelAndView(viewName, PageCommand.CART, this.paymentService.processPaymentGatewayReply(cartDTO, cartCmd));
    }

    protected void setCartCmdPropertiesForProcessPaymentGatewayReply(CartSessionData cartSessionData, CartDTO cartDTO, CartCmdImpl cmd) {
        Long customerId = deriveCustomerId(cartDTO);
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
        cmd.setCustomerId(customerId);
        cmd.setWebAccountId(customerId);
        cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
        cmd.setStationId(cartSessionData.getStationId());
        cmd.setSecurityQuestion(cartSessionData.getSecurityQuestion());
        cmd.setSecurityAnswer(cartSessionData.getSecurityAnswer());
        setCartCmdForFulfilmentOrder(cartSessionData, cmd);
    }

    protected void setCartCmdForFulfilmentOrder(CartSessionData cartSessionData, CartCmdImpl cartCmd){
        cartCmd.setFirstIssueOrder(cartSessionData.getFirstIssueOrder());
        cartCmd.setReplacementOrder(cartSessionData.getReplacementOrder());
    }
    
    protected String resolveViewName(CyberSourceReplyDTO reply) {
        if (isAccepted(reply.getDecision())) {

            return PageView.PAYMENT_ACCEPTED;
        } else if (isCancelled(reply.getDecision())) {
            return PageView.PAYMENT_CANCELLED;
        } else if (isIncomplete(reply.getDecision())) {
            return PageView.PAYMENT_INCOMPLETE;
        }
        throw new ControllerException(String.format(PrivateError.INVALID_PAYMENT_GATEWAY_DECISION.message(), reply.getDecision()));
    }

    protected boolean isCookieEnabledOnClient(String cookieStatus) {
        return CookieConstant.COOKIE_STATUS_ON.equalsIgnoreCase(cookieStatus);
    }

    protected void setCookieStatus(HttpServletResponse response) {
        response.addCookie(new Cookie(CookieConstant.COOKIE_STATUS, CookieConstant.COOKIE_STATUS_ON));
    }

    protected void addPaymentCardSelectListToModel(ModelAndView modelAndView, CartDTO cartDTO) {
        Long customerId = deriveCustomerId(cartDTO);
        SelectListDTO selectListDTO = null;
        if (cartDTO.isAutoTopUpPresent()) {
            selectListDTO = paymentCardSelectListService.getPaymentCardSelectListWithOnlySaveOption(customerId);
        } else {
            selectListDTO = paymentCardSelectListService.getPaymentCardSelectListWithAllOptions(customerId);
        }

        modelAndView.addObject(PageAttribute.PAYMENT_CARDS, selectListDTO);
    }

    protected boolean isAddPaymentCard(CartCmdImpl cmd) {
        return AddPaymentCardAction.isAddPaymentCardAction(cmd.getPaymentCardAction());
    }

    protected ModelAndView getEnterPaymentCardDetailsView(CartDTO cartDTO, CartCmdImpl cmd, BindingResult result, String cookieStatus,
                    HttpServletRequest request) {
        Long customerId = deriveCustomerId(cartDTO);
        try {
            this.cyberSourceHeartbeatDataService.checkHeartbeat(customerId);
        } catch (ServiceNotAvailableException e) {
            result.reject(ContentCode.PAYMENT_GATEWAY_UNAVAILABLE.errorCode());
            ModelAndView modelAndView = new ModelAndView(PageView.CHOOSE_PAYMENT_CARD, PageCommand.CART, cmd);
            addPaymentCardSelectListToModel(modelAndView, cartDTO);
            return modelAndView;
        }

        cmd.getCartDTO().setCyberSourceRequest(
                        this.cyberSourcePostService.preparePaymentRequestData(cmd.getCartDTO().getOrder(), cmd.getCartDTO()
                                        .getPaymentCardSettlement(), isCreateToken(cmd), isCookieEnabledOnClient(cookieStatus),
                                        getClientIpAddress(request)));
        ModelAndView modelAndView = new ModelAndView(PageView.ENTER_PAYMENT_CARD_DETAILS, PageCommand.CART, cmd);
        modelAndView.addObject(PageCommand.PAYMENT_DETAILS, this.paymentService.populatePaymentDetails(customerId));
        return modelAndView;
    }

    protected Long deriveCustomerId(CartDTO cartDTO) {
        Long customerId = cartDTO.getCustomerId();
        if (null == customerId) {
            customerId = cardService.getCardDTOById(cartDTO.getCardId()).getCustomerId();
        }
        return customerId;
    }

    protected ModelAndView getConfirmExistingCardView(CartCmdImpl cmd, HttpServletRequest request) {
        cmd.getCartDTO().setCyberSourceRequest(
                        this.cyberSourceSoapService.preparePaymentRequestData(cmd.getCartDTO().getOrder(), cmd.getCartDTO()
                                        .getPaymentCardSettlement(), getClientIpAddress(request)));
        ModelAndView modelAndView = new ModelAndView(PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT, PageCommand.CART, cmd);
        modelAndView.addObject(PageAttribute.PAYMENT_CARD_DISPLAY_NAME,
                        createDisplayName(this.paymentCardDataService.findById(Long.valueOf(cmd.getPaymentCardAction()))));
        return modelAndView;
    }

    protected boolean isCreateToken(CartCmdImpl cmd) {
        return AddPaymentCardAction.ADD_AND_SAVE.code().equals(cmd.getPaymentCardAction());
    }

    protected boolean isCardPaymentRequired(CartSessionData cartSessionData) {
        return (cartSessionData.getToPayAmount() > 0);
    }

    protected ModelAndView getShowChoosePaymentCardView(CartDTO cartDTO, CartCmdImpl cmd, HttpServletResponse response) {
        setCookieStatus(response);
        return getShowChoosePaymentCardView(cartDTO, cmd);
    }

    protected ModelAndView getShowChoosePaymentCardView(CartDTO cartDTO, CartCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(PageView.CHOOSE_PAYMENT_CARD, PageCommand.CART, cmd);
        addPaymentCardSelectListToModel(modelAndView, cartDTO);
        return modelAndView;
    }

    protected ModelAndView getConfirmPaymentView(CartCmdImpl cmd) {
        return new ModelAndView(PageView.CONFIRM_EXISTING_PAYMENT_CARD_PAYMENT, PageCommand.CART, cmd);
    }

    protected CartCmdImpl updateOrderAndPaymentCardSettlementToCmd(CartSessionData cartSessionData, CartDTO cartDTO, CartCmdImpl cmd) {
        cartDTO.setOrder(orderDataService.findById(cartSessionData.getOrderId()));
        if (null != cartSessionData.getPaymentCardSettlementId()) {
            cartDTO.setPaymentCardSettlement(paymentCardSettlementDataService.findById(cartSessionData.getPaymentCardSettlementId()));
        }
        cmd.setCartDTO(cartDTO);
        return cmd;
    }

    protected void updateCartSessionTotalsToCmd(CartSessionData cartSessionData, CartCmdImpl cmd) {
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_PAYMENT_PAGE_BACK, method = RequestMethod.POST)
    public ModelAndView cancelPaymentPage() {
        return new ModelAndView(new RedirectView(PageUrl.CHECKOUT));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CHOOSE_PAYMENT_CARD, method = RequestMethod.POST)
    public ModelAndView showChoosePaymentCardForCancel(HttpSession session, CartCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(PageView.CHOOSE_PAYMENT_CARD, PageCommand.CART, cmd);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        addPaymentCardSelectListToModel(modelAndView, cartDTO);
        return modelAndView;
    }
}
