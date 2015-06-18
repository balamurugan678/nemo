package com.novacroft.nemo.tfl.common.controller.purchase;

import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;
import static com.novacroft.nemo.tfl.common.constant.TicketType.TRAVEL_CARD;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Controller
@RequestMapping(value = Page.TRAVEL_CARD)
@SessionAttributes(PageCommand.CART)
public class BaseTravelCardController extends BasePurchaseController {
    @Autowired
    protected CartValidator cartValidator;
    @Autowired
    protected TravelCardValidator travelCardValidator;
    @Autowired
    protected ZoneMappingValidator zoneMappingValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
	protected CardService cardService;


    @ModelAttribute
    public void populateTravelCardTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.TRAVEL_CARD_TYPES, selectListService.getSelectList(PageSelectList.TRAVEL_CARD_TYPES));
    }

    @ModelAttribute
    public void populateTravelCardZonesSelectList(Model model) {
        model.addAttribute(PageAttribute.TRAVEL_CARD_ZONES, selectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES));
    }

    @ModelAttribute
    public void populateStartDatesSelectList(Model model) {
        model.addAttribute(PageAttribute.START_DATES, cartAdminService.getProductStartDateList());
    }

    @ModelAttribute
    public void populateEmailRemindersSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_EMAIL_REMINDERS,
                selectListService.getSelectList(PageSelectList.BASKET_EMAIL_REMINDERS));
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewTravelCard() {
        return new ModelAndView(PageView.TRAVEL_CARD, PageCommand.CART_ITEM, new CartItemCmdImpl());
    }
    
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_TO_CART, method = RequestMethod.POST)
    public ModelAndView addTravelCardToCart(HttpSession session, @ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cartItemCmd,
                                                      BindingResult result, RedirectAttributes redirectAttributes) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);         
        CartDTO cartDTO = loadCartDTO(cartSessionData.getCartId());
        cartItemCmd.setCartId(cartDTO.getId());
        cartItemCmd.setTicketType(TRAVEL_CARD.code());

        validateAddTravelCard(cartItemCmd, result);
        
        if (!result.hasErrors()) {
            if (isNotEmpty(cartItemCmd.getStatusMessage())) {
                setFlashStatusMessage(redirectAttributes, cartItemCmd.getStatusMessage());
            }
            cartDTO = travelCardService.addCartItemForNewCard(cartDTO, cartItemCmd);
            return new ModelAndView(new RedirectView(PageUrl.CART));
        }else{
            return new ModelAndView(PageView.TRAVEL_CARD);            
        }
    }

    protected void validateAddTravelCard(CartItemCmdImpl cartItemCmd, BindingResult result) {
        travelCardValidator.validate(cartItemCmd, result);
        cardService.updatePassengerAndDiscountForCardItemCmd(cartItemCmd);
        zoneMappingValidator.validate(cartItemCmd, result);
        if (!result.hasErrors()) {
            cartValidator.validate(cartItemCmd, result);
        }
    }
        
}
