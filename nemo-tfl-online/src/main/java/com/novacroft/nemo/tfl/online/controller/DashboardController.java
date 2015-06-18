package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.*;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.FailedAutoTopUpCaseDataService;
import com.novacroft.nemo.tfl.common.form_validator.ViewCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

/**
 * controller for customer account dashboard
 */
@Controller
@RequestMapping(PageUrl.DASHBOARD)
public class DashboardController extends OnlineBaseController {
    protected static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    protected PersonalDetailsService personalDetailsService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected WebCreditService webCreditService;
    @Autowired
    protected ViewCardValidator viewCardValidator;
    @Autowired
    protected TopUpTicketService topUpTicketService;
    @Autowired
    protected FailedAutoTopUpCaseDataService failedAutoTopUpCaseDataService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDashboard(RedirectAttributes redirectAttributes, HttpSession session) {
    	
        clearFlashStatusMessage(redirectAttributes);
        if (session != null && this.getFromSession(session, CartAttribute.CARD_ID) != null) {
            this.deleteAttributeFromSession(session, CartAttribute.CARD_ID);
        }
        if (session != null && this.getFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA) != null) {
            this.deleteAttributeFromSession(session, CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
        }
        PersonalDetailsCmdImpl cmd = this.personalDetailsService.getPersonalDetails(getLoggedInUsername());
        if(cmd.getCustomerDeactivated()){
            SecurityContextHolder.clearContext();
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.OYSTER_HOME));
        }
        CustomerDTO customer = this.getCustomer();
        Long customerId = customer.getId();
        List<CardDTO> cards = this.cardDataService.findByCustomerId(customerId);
        
        for (CardDTO card : cards) {
            if (card.getCardNumber() != null) {
                try {
                    CardInfoResponseV2DTO cardInfo = getCardService.getCard(card.getCardNumber());
                    assert(cardInfo != null);
                    topUpTicketService.removeExpiredPrePaidTicketsInACard(card, cardInfo);
                    card.setCardInfo(cardInfo);    
                } catch ( ApplicationServiceException e) {
                    logger.error(e.getMessage(), e);
                    card.setCardInfo(null);
                }
            }
        }
        
        int amount = failedAutoTopUpCaseDataService.findPendingAmountByCustomerId(customerId);
        
        ModelAndView modelAndView = new ModelAndView(PageView.DASHBOARD, PageCommand.PERSONAL_DETAILS, cmd);
        modelAndView.addObject("formattedCustomerName", formatName("", cmd.getFirstName(), cmd.getInitials(), cmd.getLastName()));
        modelAndView.addObject("cards", cards);
        modelAndView.addObject("webAccountCredit",webCreditService.getAvailableBalance(getCustomer().getId()));
        modelAndView.addObject(PageParameter.AMOUNT, amount);
        return modelAndView;
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_VIEW_OYSTER_CARD, method = RequestMethod.POST)
    public ModelAndView selectCard(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd,
                                   @RequestParam(value = CartAttribute.CARD_ID) Long cardId,
                                   HttpSession session,
                                   BindingResult result) {
        this.viewCardValidator.validate(cardId, result);
        if (result.hasErrors()) {
            throw new ApplicationServiceException();
        }
        this.addAttributeToSession(session, CartAttribute.CARD_ID, cardId); 
        return new ModelAndView(this.getRedirectViewWithoutExposedAttributes(PageUrl.VIEW_OYSTER_CARD), PageCommand.MANAGE_CARD, cmd); 
    } 
}
