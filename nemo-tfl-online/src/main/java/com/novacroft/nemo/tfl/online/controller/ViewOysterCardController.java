package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(PageUrl.VIEW_OYSTER_CARD)
public class ViewOysterCardController extends OnlineBaseController {
    
    protected static final Logger logger = LoggerFactory.getLogger(ViewOysterCardController.class);

    @Autowired
    protected CardDataService cardDataService;    

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewOysterCard(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd manageCardCmd,
    								   HttpSession session) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        if (null == cardId) {
            return new ModelAndView(getRedirectViewWithoutExposedAttributes(PageUrl.DASHBOARD));
        }
        manageCardCmd.setCardId(cardId);
        manageCardCmd.setCardNumber(cardDataService.findById(cardId).getCardNumber());
        return new ModelAndView(PageView.VIEW_OYSTER_CARD, PageCommand.MANAGE_CARD, manageCardCmd);
    }
}
