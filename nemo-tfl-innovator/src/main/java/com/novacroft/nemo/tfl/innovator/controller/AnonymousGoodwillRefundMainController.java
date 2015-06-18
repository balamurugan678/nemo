package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CONTINUE;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ANONYMOUS_GOODWILL_REFUND_MAIN;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.controller.CommonController;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Controller
@RequestMapping(value = Page.INV_ANONYMOUS_GOODWILL_REFUND_MAIN)
public class AnonymousGoodwillRefundMainController  extends CommonController{
	static final Logger logger = LoggerFactory
			.getLogger(AnonymousGoodwillRefundMainController.class);

	@Autowired
	protected RefundSelectListService refundSelectListService;

	@Autowired
	protected CardDataService cardDataService;

	@Autowired
	protected OysterCardValidator oysterCardValidator;
	
	@Autowired
	protected RefundService refundService;

	@InitBinder
	protected final void initBinder(final ServletRequestDataBinder binder) {
        final CustomDateEditor editor = new CustomDateEditor(DateUtil.createShortDateFormatter(), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@ModelAttribute
	public void populateModelAttributes(Model model) {
		refundSelectListService.getSelectListModel(model);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView viewCart(HttpSession session) {
		CartCmdImpl cmdImpl = refundService.createCartCmdImplWithCartDTO();
		CartUtil.addCartSessionDataDTOToSession(session, new CartSessionData(cmdImpl.getCartDTO().getId()));
		return new ModelAndView(PageView.INV_ANONYMOUS_GOODWILL_REFUND_MAIN , CART,
	    		cmdImpl);
	}

	@RequestMapping(params = TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
	public ModelAndView continueCart(
			@ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
			BindingResult result, final RedirectAttributes redirectAttributes) {
		oysterCardValidator.validate(cmd, result);

		if (!result.hasErrors()) {
		    	setFlashAttribute(redirectAttributes, PageAttribute.CARD_NUMBER, cmd.getCardNumber());
			return new ModelAndView(new RedirectView(PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL + "?" + PageAttribute.CARD_NUMBER + PageParameterArgument.NAME_VALUE_SEPARATOR +
					cmd.getCardNumber()));
		} else {
			return new ModelAndView(INV_ANONYMOUS_GOODWILL_REFUND_MAIN, CART,
					cmd);
		}

	}
}
