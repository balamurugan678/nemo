package com.novacroft.nemo.mock_cubic.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.command.StationCmd;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Controller
public class StationController {
	public static final String VIEW = "StationView";
    private static final Logger logger = LoggerFactory.getLogger(GetCardController.class);
    
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected UpdateCardService updateCardService;
    

    @RequestMapping(method = RequestMethod.GET, value = "/station")
    public final ModelAndView load() {
        return new ModelAndView(VIEW, PageCommand.STATION, new StationCmd());
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_GET_CARD, method = RequestMethod.POST, value = "/station")
    @ResponseBody
    public final String getCard( @ModelAttribute(PageCommand.STATION) final StationCmd cmd, final BindingResult result)  {
    	logger.debug(cmd.getPrestigeId().toString());
    	CardInfoResponseV2DTO card = getCardService.getCard(cmd.getPrestigeId());
    	Gson gson = new Gson();
        return gson.toJson(card);
        
    }
    
    
    @RequestMapping(method = RequestMethod.GET, value = "/station/main")
    public final ModelAndView loadMain() {
        return new ModelAndView("MainView", PageCommand.STATION, new StationCmd());
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/station")
    @ResponseBody
    public final String addPending(@ModelAttribute(PageCommand.STATION) final StationCmd cmd, final BindingResult result)  {
    	logger.debug(cmd.toString());
    	return updateCardService.populatePrePayTicketFromPendingTicket2(cmd.getPrestigeId());
    }
    
    
    
   
}
