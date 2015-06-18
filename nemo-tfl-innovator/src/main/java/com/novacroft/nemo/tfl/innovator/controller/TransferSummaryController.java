package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.FIELD_SELECT_PICKUP_STATION;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.IS_AUTO_TOP_UP_ENABLED_IN_TARGET_CARD;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.IS_TRANSFER_SUCCESSFUL;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.utils.NumberUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.TransferProductService;
import com.novacroft.nemo.tfl.common.application_service.TransferTargetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Transfer Summary Controller
 */
@Controller
@RequestMapping(value = PageUrl.TRANSFER_SUMMARY)
public class TransferSummaryController extends BaseController {
    static final Logger logger = LoggerFactory.getLogger(TransferSummaryController.class);

    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected TransferProductService transferProductsPersistenceService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected TransferTargetCardService transferTargetCardService;
    @Autowired
    protected LocationSelectListService locationSelectListService;
    
    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateStationSelectList(Model model) {
        model.addAttribute(FIELD_SELECT_PICKUP_STATION, locationSelectListService.getLocationSelectList());
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showTransferSummary(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd) {
        setCmdParameters(session, cartCmd);
        return new ModelAndView(PageView.TRANSFER_SUMMARY, PageCommand.CART, cartCmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView showTransferConfirmation(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd,
                    final RedirectAttributes redirectAttributes) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        Long sourceCardId = getCardIdForPersistence(cartSessionData.getSourceCardNumber());
        Long targetCardId = getCardIdForPersistence(cartSessionData.getTargetCardNumber());
        Map<String,Object> transactionalData = transferProductsPersistenceService.transferProductFromSourceCardToTargetCard(session, sourceCardId,
                        targetCardId, cartSessionData.getStationId(),cartSessionData.getLostOrStolenMode());
        redirectAttributes.addFlashAttribute(IS_AUTO_TOP_UP_ENABLED_IN_TARGET_CARD, transactionalData.get(IS_AUTO_TOP_UP_ENABLED_IN_TARGET_CARD));
        return redirectConfirmationPage((Boolean)transactionalData.get(IS_TRANSFER_SUCCESSFUL));
    }
 
    protected void setCmdParameters(HttpSession session, CartCmdImpl cartCmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        cartCmd.setSourceCardNumber(cartSessionData.getSourceCardNumber());
        cartCmd.setTargetCardNumber(cartSessionData.getTargetCardNumber());
        cartCmd.setStationId(cartSessionData.getStationId());
        cartCmd.setStationName(locationDataService.getActiveLocationById(NumberUtil.convertLongToInt(cartCmd.getStationId())).getName());
    }
     
    protected Long getCardIdForPersistence(String cardNumber) {
        return cardService.getCardIdFromCardNumber(cardNumber);
    }
    
    protected ModelAndView redirectConfirmationPage(final boolean transferProductsPersistence) {
        if (transferProductsPersistence) {
            return new ModelAndView(new RedirectView(PageUrl.TRANSFER_CONFIRMATION));
        }
        return new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT));
    }
     
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(HttpSession session, Model model, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmd) {
    	ModelAndView modelAndView = new ModelAndView(PageView.TRANSFER_PRODUCT);
    	CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
    	CartDTO cartDTO = cartService.createCartFromCardId(cardService.getCardIdFromCardNumber(cartSessionData.getSourceCardNumber()));
		cartCmd.setCardId(cartCmd.getCardId());
		cartCmd.setCartId(cartDTO.getId());
		cartCmd.setSourceCardNumber(cartSessionData.getSourceCardNumber());
		createCartSessionData(session, cartCmd);
		transferTargetCardService.populateCardsSelectList(cartSessionData.getSourceCardNumber(), model);
		return modelAndView; 
    }
    
    protected void createCartSessionData(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = new CartSessionData(cmd.getCartId());
        cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
        cartSessionData.setTransferProductMode(Boolean.TRUE);
        cartSessionData.setCartId(cmd.getCardId());
        cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
    } 
    
}
