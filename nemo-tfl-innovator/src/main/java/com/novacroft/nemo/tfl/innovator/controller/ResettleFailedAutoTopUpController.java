package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.FAILED_AUTOTOPUP_HISTORY_RECORDS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.PAYMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_RESETTLE;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_RESETTLE_UPDATE;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_RESETTLE_FAILED_AUTO_TOP_UP;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpCaseService;
import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpHistory;
import com.novacroft.nemo.tfl.common.form_validator.FailedAutoTopUpCaseValidator;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpHistoryDTO;

/**
 * Controller for adding an existing card to an account
 */
@Controller
@RequestMapping(PageUrl.RESETTLE_FAILED_AUTO_TOP_UP)
public class ResettleFailedAutoTopUpController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(ResettleFailedAutoTopUpController.class);

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected FailedAutoTopUpHistoryService failedAutoTopUpHistoryService;
    @Autowired
    protected FailedAutoTopUpCaseService failedAutoTopUpCaseService;
    @Autowired
    protected FailedAutoTopUpCaseValidator failedAutoTopUpCaseValidator;
    
    @ModelAttribute
    public void populatePaymentTypes(Model model) {
        model.addAttribute(PAYMENT_TYPE, selectListService.getSelectList(PageSelectList.PAYMENT_TYPE));
    }
    
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadResettleViewPage(HttpSession session,  @ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) FailedAutoTopUpCaseCmdImpl autoTopUpCaseCmdImpl, @RequestParam(CARD_NUMBER) String cardNumber) {
		ModelAndView modelAndView = new ModelAndView(INV_RESETTLE_FAILED_AUTO_TOP_UP);
		addCustomerFailedAutoTopUpCaseDetailsInCmd(autoTopUpCaseCmdImpl);
		autoTopUpCaseCmdImpl.setOysterCardNumber(cardNumber);
		addCustomerFailedAutoTopUpHistoryDetailsInModel(modelAndView, autoTopUpCaseCmdImpl.getCustomerId());
		modelAndView.addObject(FAILED_AUTOTOPUP_RESETTLEMENT, autoTopUpCaseCmdImpl);
		return modelAndView;
	}

	protected void addCustomerFailedAutoTopUpCaseDetailsInCmd(FailedAutoTopUpCaseCmdImpl autoTopUpCaseCmdImpl) {
		FailedAutoTopUpCaseDTO autoTopUpCaseDTO = failedAutoTopUpCaseService.findByCardNumber(autoTopUpCaseCmdImpl.getCardNumber());
		autoTopUpCaseCmdImpl.setFailedAutoTopUpCaseId(autoTopUpCaseDTO.getId());
		autoTopUpCaseCmdImpl.setCaseStatus(autoTopUpCaseDTO.getCaseStatus());
		autoTopUpCaseCmdImpl.setFailedAutoTopUpAmount(autoTopUpCaseDTO.getFailedAutoTopUpAmount().intValue());
	}
	
    protected void addCustomerFailedAutoTopUpHistoryDetailsInModel(ModelAndView modelAndView, Long customerId) {
    	FailedAutoTopUpHistory failedAutoTopUpHistory = new FailedAutoTopUpHistory();
    	failedAutoTopUpHistory.setCustomerId(customerId);
    	List<FailedAutoTopUpHistoryDTO> listOfFailedAutoTopUpHistory = failedAutoTopUpHistoryService.findByCustomerId(failedAutoTopUpHistory);
    	modelAndView.addObject(FAILED_AUTOTOPUP_HISTORY_RECORDS, listOfFailedAutoTopUpHistory);
	}

	@RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(HttpSession session, @ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) FailedAutoTopUpCaseCmdImpl cmd, BindingResult result) {
        PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cmd.getCardNumber());
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
        redirectView.addObject(ID, personalDetailsByCustomerId.getCustomerId());
        return redirectView;
    }
	
	@RequestMapping(params = TARGET_ACTION_RESETTLE_UPDATE, method = RequestMethod.POST)
    public ModelAndView update(HttpSession session, @ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) FailedAutoTopUpCaseCmdImpl cmd, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView(INV_RESETTLE_FAILED_AUTO_TOP_UP);
		this.failedAutoTopUpCaseValidator.validate(cmd, result);
		if(!result.hasErrors()){
			cmd = failedAutoTopUpCaseService.updateFailedAutoTopUpCaseDetails(cmd);
			modelAndView.addObject(FAILED_AUTOTOPUP_RESETTLEMENT, cmd);
		}
		return modelAndView;
    }
	
	@RequestMapping(params = TARGET_ACTION_RESETTLE, method = RequestMethod.POST)
    public ModelAndView resettle(HttpSession session, @ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) FailedAutoTopUpCaseCmdImpl cmd, BindingResult result) {
		this.failedAutoTopUpCaseValidator.validate(cmd, result);
		if(!result.hasErrors()){
			ModelAndView modelAndView = new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.PAYMENT)); 
			modelAndView.addObject(FAILED_AUTOTOPUP_RESETTLEMENT, cmd);
			return modelAndView;
		}
		return new ModelAndView(INV_RESETTLE_FAILED_AUTO_TOP_UP);
	}
	
    @RequestMapping(value = "/validateResettlementPeriod", method = RequestMethod.POST)
    public ModelAndView resettlementPeriod(@ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) FailedAutoTopUpCaseCmdImpl cmd, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView(INV_RESETTLE_FAILED_AUTO_TOP_UP);
		this.failedAutoTopUpCaseValidator.validate(cmd, result);
		return modelAndView;
    }
}