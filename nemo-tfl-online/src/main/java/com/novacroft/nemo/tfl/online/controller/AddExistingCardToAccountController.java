package com.novacroft.nemo.tfl.online.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.command.impl.CardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Controller for adding an existing card to an account
 */
@Controller
@RequestMapping(PageUrl.ADD_EXISTING_CARD_TO_ACCOUNT)
public class AddExistingCardToAccountController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(AddExistingCardToAccountController.class);

    @Autowired
    protected OysterCardValidator oysterCardValidator;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected SecurityQuestionService securityQuestionService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CardService cardService;
    
    @ModelAttribute
    public void populateSecurityQuestionsSelectList(Model model) {
        model.addAttribute(PageAttribute.SECURITY_QUESTIONS, selectListService.getSelectList(PageSelectList.SECURITY_QUESTIONS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPage() {
        return new ModelAndView(PageView.ADD_EXISTING_CARD_TO_ACCOUNT, PageCommand.CARD, new CardCmdImpl());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView saveChanges(@ModelAttribute(PageCommand.CARD) CardCmdImpl cmd, BindingResult result,
                                    final RedirectAttributes redirectAttributes) {
        this.oysterCardValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.ADD_EXISTING_CARD_TO_ACCOUNT, PageCommand.CARD, cmd);
        } else {
            CardDTO cardDTO = this.customerService.createCard(this.securityService.getLoggedInCustomer().getId(), cmd.getCardNumber());
            SecurityQuestionCmdImpl securityQuestionDetails = null;
            if (cardDTO != null && cardDTO.getId() != null) {
                securityQuestionDetails = securityQuestionService.getSecurityQuestionDetails(cardDTO.getId());
                if (securityQuestionDetails.getSecurityAnswer() != null && securityQuestionDetails.getSecurityQuestion() != null) {
                    return new ModelAndView(PageView.VERIFY_SECURITY_QUESTION, PageCommand.SECURITY_QUESTION, securityQuestionDetails);
                }
            }
            SecurityQuestionCmdImpl securityQuestionCmdImpl = new SecurityQuestionCmdImpl();
            securityQuestionCmdImpl.setCardId(cardService.getCardIdFromCardNumber(cmd.getCardNumber()));
            securityQuestionCmdImpl.setCardNumber(cmd.getCardNumber());
            return new ModelAndView(PageView.SECURITY_QUESTION, PageCommand.SECURITY_QUESTION, securityQuestionCmdImpl);
        }
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_TRANSFER_PRODUCT_BACK, method = RequestMethod.POST)
    public ModelAndView cancelForTransferProduct() {
        return new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT));
    }
}
