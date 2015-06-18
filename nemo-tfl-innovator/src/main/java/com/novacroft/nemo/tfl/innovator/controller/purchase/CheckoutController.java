package com.novacroft.nemo.tfl.innovator.controller.purchase;

import static java.lang.Math.min;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentQueuePopulationService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.controller.purchase.BasePurchaseController;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.PaymentTermsValidator;
import com.novacroft.nemo.tfl.common.form_validator.WebCreditPurchaseValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Controller
@RequestMapping(value = Page.CHECKOUT)
public class CheckoutController extends BasePurchaseController {

    @Autowired
    protected PaymentTermsValidator paymentTermsValidator;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected WebCreditService webCreditService;
    @Autowired
    protected WebCreditPurchaseValidator webCreditPurchaseValidator;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected FulfilmentQueuePopulationService fulfilmentQueuePopulationService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showCheckout(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        updateWebCreditAndTotalsToCartSessionData(cartDTO, cartSessionData);
        updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        return new ModelAndView(PageView.INV_CHECKOUT, PageCommand.CART, cmd);
    }

    protected void updateWebCreditAndTotalsToCartSessionData(CartDTO cartDTO, CartSessionData cartSessionData) {
        cartSessionData.setCartTotal(cartDTO.getCartTotal());
        cartSessionData.setWebCreditAvailableAmount(webCreditService.getAvailableBalance(cartDTO.getCustomerId()));
        removeWebCreditForAutoTopUpAndPayAsYou(cartSessionData);
        cartSessionData.setWebCreditApplyAmount(min(cartSessionData.getWebCreditAvailableAmount(), cartSessionData.getCartTotal()));
        cartSessionData.setToPayAmount(cartSessionData.getCartTotal() - cartSessionData.getWebCreditApplyAmount());
    }

    protected void removeWebCreditForAutoTopUpAndPayAsYou(CartSessionData cartSessionData) {
        if (TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code().equalsIgnoreCase(cartSessionData.getTicketType())) {
            cartSessionData.setWebCreditAvailableAmount(0);
        }
    }

    protected void updateWebCreditAndTotalsToCmd(CartSessionData cartSessionData, CartCmdImpl cmd) {
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setToPayAmount(cartSessionData.getToPayAmount());
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount());
        if (null != cartSessionData.getWebCreditApplyAmount() && (null == cmd.getWebCreditApplyAmount() || cmd.getWebCreditApplyAmount() == 0)) {
            cmd.setWebCreditApplyAmount(cartSessionData.getWebCreditApplyAmount());
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView checkout(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        
        this.paymentTermsValidator.validate(cmd, result);
        this.webCreditPurchaseValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            applyWebCreditToPayAmount(cartSessionData, cmd);
            cmd.setCustomerId(cartDTO.getCustomerId());
            cmd = paymentService.createOrderAndSettlementsFromCart(cartDTO, cmd);
            updateOrderSettlementToCartSessionData(cmd, cartSessionData);
            updateFirstIssueReplacementOrderToCartSessionData(cartSessionData, cartDTO);
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.PAYMENT));
        }
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
        updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        updateFirstIssueReplacementOrderToCartSessionData(cartSessionData, cartDTO);
        return new ModelAndView(PageView.INV_CHECKOUT, PageCommand.CART, cmd);
    }

    protected void updateFirstIssueReplacementOrderToCartSessionData(CartSessionData cartSessionData, CartDTO cartDTO){
        cartSessionData.setFirstIssueOrder(fulfilmentQueuePopulationService.isFirstIssue(cartDTO));
    }
    
    protected void addCartDTOToModel(Model model, CartDTO cartDTO) {
        model.addAttribute(PageAttribute.CART_DTO, cartDTO);
    }

    protected void updateOrderSettlementToCartSessionData(CartCmdImpl cmd, CartSessionData cartSessionData) {
        cartSessionData.setOrderId(cmd.getCartDTO().getOrder().getId());
        if (cmd.getCartDTO().getPaymentCardSettlement() != null) {
            cartSessionData.setPaymentCardSettlementId(cmd.getCartDTO().getPaymentCardSettlement().getId());
        }
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE, method = RequestMethod.POST)
    public ModelAndView updateToPayWithWebCredit(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                    BindingResult result) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cmd.setPageName(cartSessionData.getPageName());
        cmd.setTotalAmt(cartSessionData.getCartTotal());
        cmd.setWebCreditAvailableAmount(cartSessionData.getWebCreditAvailableAmount());
        this.webCreditPurchaseValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            applyWebCreditToPayAmount(cartSessionData, cmd);
            updateWebCreditAndTotalsToCmd(cartSessionData, cmd);
        }
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        addCartDTOToModel(model, cartDTO);
        return new ModelAndView(PageView.CHECKOUT, PageCommand.CART, cmd);
    }

    protected void applyWebCreditToPayAmount(CartSessionData cartSessionData, CartCmdImpl cmd) {
        if (null != cmd.getWebCreditApplyAmount()) {
            cartSessionData.setWebCreditApplyAmount(cmd.getWebCreditApplyAmount());
            cartSessionData.setToPayAmount(cartSessionData.getCartTotal() - cartSessionData.getWebCreditApplyAmount());
        } else {
            cartSessionData.setWebCreditApplyAmount(0);
        }
        cartSessionData.setToPayAmount(cartSessionData.getCartTotal() - cartSessionData.getWebCreditApplyAmount());
    }

}
