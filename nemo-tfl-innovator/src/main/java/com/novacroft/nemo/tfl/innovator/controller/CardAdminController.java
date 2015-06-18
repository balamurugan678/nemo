package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CARD_PREFERENCES;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_JOURNEY_HISTORY;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_MANAGE_AUTO_TOP_UP;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_INV_INCOMPLETE_JOURNEYS;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.INV_CARD_PREFERENCES;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.INV_INCOMPLETE_JOURNEYS;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.INV_MANAGE_AUTO_TOP_UP;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.JOURNEY_HISTORY;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CARD_ADMIN;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;


@Controller
@RequestMapping(value = Page.INV_CARD_ADMIN)
public class CardAdminController extends com.novacroft.nemo.tfl.common.controller.BaseController {
    @Autowired
    CardDataService cardDataService;
	
	public static final int CARD_ID_ZERO = 0;
	
    static final Logger logger = LoggerFactory.getLogger(CardAdminController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view(@RequestParam(CARD_NUMBER) String cardNumber, @RequestParam("customerId") Long customerId) {
        ModelAndView modelAndView = new ModelAndView(INV_CARD_ADMIN);
        modelAndView.addObject(CARD_NUMBER, cardNumber);
        modelAndView.addObject("customerId", customerId);
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_JOURNEY_HISTORY, method = RequestMethod.POST)
    public ModelAndView loadJourneyHistory(@RequestParam(CARD_NUMBER) String cardNumber) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(JOURNEY_HISTORY));
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        modelAndView.addObject("id", cardDTO != null ? cardDTO.getId() : CARD_ID_ZERO);
        modelAndView.addObject(CARD_NUMBER, cardNumber);
        return modelAndView;
    }
    
    @RequestMapping(params = TARGET_INV_INCOMPLETE_JOURNEYS, method = RequestMethod.POST)
    public ModelAndView loadUnFinishedJourneys(@RequestParam(CARD_NUMBER) String cardNumber) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(INV_INCOMPLETE_JOURNEYS));
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        modelAndView.addObject("id", cardDTO != null ? cardDTO.getId() : CARD_ID_ZERO);
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_CARD_PREFERENCES, method = RequestMethod.POST)
    public ModelAndView loadEditCardPreferences(@RequestParam(CARD_NUMBER) String cardNumber) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(INV_CARD_PREFERENCES));
        modelAndView.addObject("id", cardNumber);
        return modelAndView;
    }
    
    @RequestMapping(params = TARGET_ACTION_MANAGE_AUTO_TOP_UP, method = RequestMethod.POST)
    public ModelAndView loadManageAutoTopUp(@RequestParam(CARD_NUMBER) String cardNumber, @RequestParam("customerId") String customerId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(INV_MANAGE_AUTO_TOP_UP));
        
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        this.addAttributeToSession(session, CartAttribute.CARD_ID, cardDTO.getId()); 
        
        modelAndView.addObject("cardNumber", cardNumber);
        modelAndView.addObject("customerId", customerId);
        return modelAndView;
    }
}
