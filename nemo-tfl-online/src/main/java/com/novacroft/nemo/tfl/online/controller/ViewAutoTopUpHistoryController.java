package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.PageParameterValue.AUTO_TOP_UP_HISTORY;
import static com.novacroft.nemo.tfl.common.constant.PageView.VIEW_AUTO_TOP_UP_HISTORY;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.AutoTopUpHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

@Controller
@RequestMapping(value = PageUrl.VIEW_AUTO_TOP_UP_HISTORY)
public class ViewAutoTopUpHistoryController extends OnlineBaseController {
	
    @Autowired
    protected AutoTopUpHistoryService autoTopUpHistoryService ;
    @Autowired
    protected CardDataService cardDataService;
	    
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAutoTopUpHistory(@ModelAttribute(PageCommand.CART) CartCmdImpl cmd, HttpSession session) {
	    Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardDataService.findById(cardId).getCardNumber());
        List<AutoTopUpHistoryItemDTO> autoTopUpHistoryItems = autoTopUpHistoryService.getAutoTopUpHistoryForOysterCard(cardId);	  
		ModelAndView modelAndView = new ModelAndView(VIEW_AUTO_TOP_UP_HISTORY);
		modelAndView.addObject(AUTO_TOP_UP_HISTORY, autoTopUpHistoryItems);
		modelAndView.addObject(PageCommand.CART, cmd);
        return modelAndView;
	}   
	
	@Override
	@RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }
	
}
