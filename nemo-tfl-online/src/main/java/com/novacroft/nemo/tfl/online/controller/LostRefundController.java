package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CONFIRM;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CONTINUE;
import static com.novacroft.nemo.tfl.common.constant.PageView.CONFIRM_LOST_CARD_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageView.LOST_CARD_REFUND_SUMMARY;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.form_validator.PaymentMethodValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

@Controller
@RequestMapping(value = PageUrl.LOST_CARD_REFUND)
public class LostRefundController extends OnlineBaseController{

	@Autowired
    protected RefundService refundService;
	@Autowired
    protected CardUpdateService cardUpdateService;
	@Autowired
    protected WorkFlowService workflowService;
	@Autowired
    protected SelectListService selectListService;
	@Autowired
    protected CardService cardService;
	@Autowired
    protected RefundPaymentService refundPaymentService;
	@Autowired
	protected PaymentMethodValidator paymentMethodValidator;
	@Autowired
    protected CartAdministrationService cartAdministrationService;
	@Autowired
	protected RefundScenarioHotListReasonTypeDataService refundScenarioHotListReasonTypeDataService;
	@Autowired
    protected HotlistCardService hotlistCardService;
	@Autowired
	protected CartService cartService;
	@Autowired
	protected CardDataService cardDataService;

	public static final int LOST_CARD_HOTLIST_REASON_ID = 1;
	
	@ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCart(@RequestParam(CARD_NUMBER) String cardNumber) {
		CartCmdImpl cartCmdImpl = new CartCmdImpl();
		cartCmdImpl.setCardNumber(cardNumber);
		return cartCmdImpl;
    }
	
	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewRefundSummary(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, @RequestParam(CARD_NUMBER) String cardNumber) {
		CartDTO cartDTO = refundService.getCartDTOForRefund(cardNumber, CartType.LOST_REFUND.code(), true);
		refundService.storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId());
		
		ModelAndView modelAndView = new ModelAndView(LOST_CARD_REFUND_SUMMARY, CART, cmd);
		modelAndView.addObject(CART_DTO, cartDTO);
		return modelAndView;
		
    }
	
	@RequestMapping(params = TARGET_ACTION_CONTINUE,  method = RequestMethod.POST)
    public ModelAndView confirmRefund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
    		BindingResult result) {
		paymentMethodValidator.validate(cmd, result);
		if (!result.hasErrors()) {
			CartDTO cartDTO = refundService.getCartDTOUsingCartSessionDataDTOInSession(session);
			PaymentType paymentType = PaymentType.lookUpPaymentType(cmd.getPaymentType()); 
			ModelAndView modelAndView = null;
			if(paymentType == PaymentType.WEB_ACCOUNT_CREDIT) {
				modelAndView = new ModelAndView(CONFIRM_LOST_CARD_REFUND, CART, cmd);
				modelAndView.addObject(CART_DTO, cartDTO);
				return modelAndView;
			} else {
				modelAndView = new ModelAndView(LOST_CARD_REFUND_SUMMARY, CART, cmd);
				modelAndView.addObject(CART_DTO, cartDTO);
				return modelAndView;
			}
		} else {
			return new ModelAndView(LOST_CARD_REFUND_SUMMARY, CART, cmd);
		}
    }
	
	@RequestMapping(params = TARGET_ACTION_CONFIRM,  method = RequestMethod.POST)
    public ModelAndView showConfirmation(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
    		RedirectAttributes redirectAttributes) {
		CartDTO cartDTO = refundService.getCartDTOUsingCartSessionDataDTOInSession(session);
		if(cartDTO.getCustomerId() == null){
			CardDTO cardDTO = cardDataService.findByCardNumber(cmd.getCardNumber());
			cartDTO.setCustomerId(cardDTO.getCustomerId());
		}
		cmd.setCartDTO(cartDTO);
		cmd.setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT.code());
		cmd.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_ONLINE);
		Long refundCaseNumber = cartAdministrationService.addApprovalId(cmd.getCartDTO());
		cmd.setApprovalId(refundCaseNumber);
		refundPaymentService.completeRefund(cmd);
        Integer hotListReaosnId = cmd.getHotListReasonId();
        if (hotListReaosnId == null) {
            RefundScenarioHotListReasonTypeDTO refundScenarioHotListReasonTypeDTO = refundScenarioHotListReasonTypeDataService
                            .findByRefundScenario(RefundScenarioEnum.find(CartType.LOST_REFUND.code()));
            hotListReaosnId = refundScenarioHotListReasonTypeDTO != null ? refundScenarioHotListReasonTypeDTO.getHotlistReasonDTO().getId()
                            .intValue() : null;
        }
        if (hotListReaosnId != null) {
            hotlistCardService.toggleCardHotlisted(cmd.getCardNumber(), hotListReaosnId);
        }
        cardUpdateService.createLostOrStolenEventForHotlistedCard(cmd.getCardNumber(), LOST_CARD_HOTLIST_REASON_ID);
		redirectAttributes.addAttribute(REFUND_CASE_NUMBER, refundCaseNumber.toString() );
		redirectAttributes.addAttribute(REFUND_AMOUNT,CurrencyUtil.formatPenceWithHtmlCurrencySymbol(cmd.getCartDTO().getCartRefundTotal()));
		redirectAttributes.addAttribute(CARD_NUMBER, cmd.getCardNumber());

        return new ModelAndView(new RedirectView(PageUrl.LOST_CARD_REFUND_CONFIRMATION));
    }
	
	@Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.LOST_OR_STOLEN_CARD));
    }
}
