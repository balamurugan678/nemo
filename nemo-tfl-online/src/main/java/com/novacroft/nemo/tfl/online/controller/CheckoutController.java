package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.AddPaymentCardAction.ADD;
import static java.lang.Math.min;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;
import com.novacroft.nemo.tfl.common.form_validator.WebCreditPurchaseValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Checkout controller
 */
@Controller
@RequestMapping(value = PageUrl.CHECKOUT)
public class CheckoutController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    protected PaymentTermsValidator paymentTermsValidator;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected WebCreditService webCreditService;
    @Autowired
    protected WebCreditPurchaseValidator webCreditPurchaseValidator;
    @Autowired
    protected FulfilmentQueuePopulationService fulfilmentQueuePopulationService;
    
    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCheckout(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cmd.setPageName(cartSessionData.getPageName());
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        addCartDTOToModel(model, cartDTO);
        updateWebCreditAndTotalsToCartSessionData(cartDTO, cartSessionData, cmd);
        setWebCreditAndTotalsToCmd(cartSessionData, cmd);
        return new ModelAndView(PageView.CHECKOUT, PageCommand.CART, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE, method = RequestMethod.POST)
    public ModelAndView updateToPayWithWebCredit(HttpSession session, Model model,
                                                 @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cmd.setPageName(cartSessionData.getPageName());
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount());
        cmd.setWebCreditApplyAmount(null != cmd.getWebCreditApplyAmount() ? cmd.getWebCreditApplyAmount() : 0);
        this.webCreditPurchaseValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            applyWebCreditToPayAmount(cartSessionData, cmd);
            updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        }
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        addCartDTOToModel(model, cartDTO);
        return new ModelAndView(PageView.CHECKOUT, PageCommand.CART, cmd);
    }

	protected void addCartDTOToModel(Model model, CartDTO cartDTO) {
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView checkout(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                 BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cmd.setPageName(cartSessionData.getPageName());
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        applyWebCreditToPayAmount(cartSessionData, cmd);
        updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        
        this.paymentTermsValidator.validate(cmd, result);
        this.webCreditPurchaseValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount() - cartSessionData.getWebCreditApplyAmount());
            cmd.setPaymentCardAction(ADD.code());
            cmd.setCustomerId(getLoggedInUserCustomerId());
            setCartCmdPropertiesForOrderSettlement(cartSessionData, cartDTO, cmd);
            cmd = paymentService.createOrderAndSettlementsFromCart(cartDTO, cmd);
            updateOrderSettlementToCartSessionData(cmd, cartSessionData);
            updateFirstIssueReplacementOrderToCartSessionData(cartSessionData, cartDTO);
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.PAYMENT));
        }
        addCartDTOToModel(model, cartDTO);
        updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount() - cartSessionData.getWebCreditApplyAmount());
        return new ModelAndView(PageView.CHECKOUT, PageCommand.CART, cmd);
    }

    protected void updateFirstIssueReplacementOrderToCartSessionData(CartSessionData cartSessionData, CartDTO cartDTO){
        cartSessionData.setFirstIssueOrder(fulfilmentQueuePopulationService.isFirstIssue(cartDTO));
    }
    
    protected void setCartCmdPropertiesForOrderSettlement(CartSessionData cartSessionData, CartDTO cartDTO, CartCmdImpl cmd) {
        cmd.setTotalAmt(cartDTO.getCartTotal());
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
        cmd.setAutoTopUpAmount(cartDTO.getAutoTopUpAmount());
        cmd.setStationId(cartSessionData.getStationId());
        cmd.setCustomerId(getLoggedInUserCustomerId());
        cmd.setCardId(cartDTO.getCardId());
        if (cartSessionData.getManageAutoTopUpMode()) {
            cmd.setTicketType(TicketType.AUTO_TOP_UP.code());
        }
        cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
    }

    protected void updateOrderSettlementToCartSessionData(CartCmdImpl cmd, CartSessionData cartSessionData) {
        cartSessionData.setOrderId(cmd.getCartDTO().getOrder().getId());
        if (null != cmd.getCartDTO().getPaymentCardSettlement()) {
            cartSessionData.setPaymentCardSettlementId(cmd.getCartDTO().getPaymentCardSettlement().getId());
        }
    }

    protected void updateWebCreditAndTotalsToCartSessionData(CartDTO cartDTO, CartSessionData cartSessionData, CartCmdImpl cmd) {
        cartSessionData.setCartTotal(cartDTO.getCartTotal());
        cartSessionData.setWebCreditAvailableAmount(webCreditService.getAvailableBalance(getLoggedInUserCustomerId()));
        removeWebCreditForAutoTopUpAndPayAsYou(cartSessionData, cartDTO);
        if(cartSessionData.getToPayAmount() == 0){
            cartSessionData
                    .setWebCreditApplyAmount(min(cartSessionData.getWebCreditAvailableAmount(), cartSessionData.getCartTotal()));
            cartSessionData.setToPayAmount(cartSessionData.getCartTotal() - cartSessionData.getWebCreditApplyAmount());
        }
    }
    
    protected void removeWebCreditForAutoTopUpAndPayAsYou(CartSessionData cartSessionData, CartDTO cartDTO) {
        boolean payAsYouItem = false;
        boolean autoTopUpItem = false;
        for (ItemDTO itemDTOObj : cartDTO.getCartItems()) {
            if (itemDTOObj.getClass().equals(PayAsYouGoItemDTO.class)) {
                payAsYouItem = true;
            }
            if (itemDTOObj.getClass().equals(AutoTopUpConfigurationItemDTO.class)) {
                autoTopUpItem = true;
            }
        }
        if ((payAsYouItem && autoTopUpItem) || (TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code().equalsIgnoreCase(cartSessionData.getTicketType()))) {
            cartSessionData.setWebCreditAvailableAmount(0);
        }
    }

    protected void setWebCreditAndTotalsToCmd(CartSessionData cartSessionData, CartCmdImpl cmd) {
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount());
        if (null != cartSessionData.getWebCreditApplyAmount() && (null == cmd.getWebCreditApplyAmount() || cmd.getWebCreditApplyAmount() == 0)) {
            cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
        }
    }

    protected void updateWebCreditAndTotalsToCmd(CartSessionData cartSessionData, CartCmdImpl cmd) {
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount());
        if (null != cartSessionData.getWebCreditApplyAmount() && (null == cmd.getWebCreditApplyAmount() 
                        || cmd.getWebCreditApplyAmount() == 0)) {
            cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
        }
    }

    protected void applyWebCreditToPayAmount(CartSessionData cartSessionData, CartCmdImpl cmd) {
        if (null != cmd.getWebCreditApplyAmount()) {
            cartSessionData.setWebCreditApplyAmount(cmd.getWebCreditApplyAmount());
            cartSessionData.setToPayAmount(cartSessionData.getCartTotal() - cartSessionData.getWebCreditApplyAmount());
        } else {
            cartSessionData.setWebCreditApplyAmount(0);
        }
    }
    
    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE));
    }
}
