package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CART_ITEM_ID;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CONTINUE;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.TicketType.GOODWILL;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;

@Controller
@RequestMapping(value = Page.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL)
public class AnonymousGoodwillRefundGoodwillController {
    static final Logger logger = LoggerFactory.getLogger(AnonymousGoodwillRefundGoodwillController.class);

    @Autowired
    protected RefundSelectListService refundSelectListService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected GoodwillValidator goodwillValidator;
    @Autowired
    @Qualifier("AnonymousGoodwillValidator")
    protected BaseValidator  anonymousGoodwillValidator;
    @Autowired
    protected RefundPaymentService refundPaymentService;
    @Autowired
    protected WorkFlowService workFlowService;
    @Autowired
    @Qualifier(value="AnonymousGoodWillWorkFlowGeneratorStrategy")
    protected WorkFlowGeneratorStrategy workFlowGeneratorStrategy; 
    
    @Autowired
    protected RefundService refundService;
    

    @ModelAttribute
    public void populateModelAttributes(Model model) {
        refundSelectListService.getAnonymousGoodwillSelectListModel(model);
    }

    @RequestMapping(method = RequestMethod.GET, params = {PageParameter.APPROVAL_ID})
    public ModelAndView viewApprovalCart(@RequestParam(PageAttribute.CARD_NUMBER ) String cardNumber, @RequestParam(PageParameter.APPROVAL_ID) Long approvalId,  @ModelAttribute( PageCommand.CART) CartCmdImpl cmd, BindingResult result, HttpSession session) {
    	CartDTO cartDTO =  cartService.findByApprovalId(approvalId);
    	storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId()); 
    	cmd.setCartDTO(cartDTO);
    	cmd.setCardNumber(cardNumber);
    	cmd.setPaymentType(PaymentType.AD_HOC_LOAD.code());
    	refundService.updatePaymentDetailsInCartCmdImpl(cmd, cmd.getCartDTO());
        return new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, CART, cmd);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewCart(@ModelAttribute(PageAttribute.CARD_NUMBER ) String cardNumber,  @ModelAttribute( PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
    	cmd.setCardNumber(cardNumber);
    	cmd.setPaymentType(PaymentType.AD_HOC_LOAD.code());
        return new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, CART, cmd);
    }

    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {

        ModelAndView modelAndView = new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL);
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);

        if (cartDTO == null) {
            cartDTO = cartService.createCart();
        }
        cmd.setCartDTO(cartDTO);
        goodwillValidator.validate(cmd, result);

        if (!result.hasErrors()) {
            CartItemCmdImpl cartItemCmd = new CartItemCmdImpl(cmd.getCartItemCmd().getGoodwillPrice(), cmd.getCartItemCmd().getGoodwillPaymentId(), GOODWILL.code(), cmd.getCardId(), cmd.getCartItemCmd().getGoodwillOtherText());
            cartDTO = cartService.addItem(cartDTO, cartItemCmd, GoodwillPaymentItemDTO.class);
            cmd.setCartDTO(cartDTO);
        }

        cmd.setStationId(cmd.getStationId());
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId());
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd, @RequestParam(value = CART_ITEM_ID) int cartItemid) {

        ModelAndView modelAndView = new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL);
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartDTO = cartService.deleteItem(cartDTO, Long.valueOf(cartItemid));
        cmd.setStationId(cmd.getStationId());
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView continueCart(HttpSession session,@ModelAttribute(PageCommand.CART)  CartCmdImpl cmd, BindingResult result) {
    	cmd.setCartDTO(getCartDTOUsingCartSessionDataDTOInSession(session));
    	cmd.setCartType(CartType.ANONYMOUS_GOODWILL_REFUND.code());
    	anonymousGoodwillValidator.validate(cmd, result);
    	if(result.hasErrors()){
    		return new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, CART, cmd) ;
    	}
    	if (cmd.getCartDTO().getApprovalId() != null) {
    		cmd.setApprovalId(cmd.getCartDTO().getApprovalId());
    		workFlowService.getChangesAfterEditRefunds(cmd);
	        return returnToWorkflow(cmd.getCartDTO().getApprovalId());
	    }
    	cmd.setDateOfRefund(new Date());
    	cmd.getCartDTO().setDateOfRefund(cmd.getDateOfRefund());
    	cmd.setTargetCardNumber(cmd.getCardNumber());
    	cmd.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_INNOVATOR);
    	workFlowService.generateWorkflowEntity(cmd, workFlowGeneratorStrategy);
        return new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER_SEARCH));
    }

    protected CartDTO getCartDTOUsingCartSessionDataDTOInSession(HttpSession session) {
        CartSessionData cartSessionData = getCartSessionDataDTOFromSession(session);
        if (cartSessionData == null) {
            return cartService.createCart();
        }
        return cartService.findById(cartSessionData.getCartId());
    }

    protected CartSessionData getCartSessionDataDTOFromSession(HttpSession session) {
        return (CartSessionData) session.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }

    protected void storeCartIdInCartSessionDataDTOInSession(HttpSession session, Long cartId) {
        CartUtil.addCartSessionDataDTOToSession(session, new CartSessionData(cartId));
    }
    
    protected ModelAndView returnToWorkflow(Long approvalId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(PageUrl.VIEW_WORKFLOW_ITEM));
        modelAndView.addObject(CASE_NUMBER, approvalId);
        modelAndView.addObject(EDIT, Boolean.TRUE);
        return modelAndView;
    }
}
