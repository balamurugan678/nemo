package com.novacroft.nemo.tfl.online.controller;
 
import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;
import static com.novacroft.nemo.tfl.common.constant.AutoTopUpActivityType.SET_UP;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CARD_ID;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.TICKET_TYPE;

import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.FailedAutoTopUpCaseService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TopUpTicketService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpPayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.EmailNotificationValidator;
import com.novacroft.nemo.tfl.common.form_validator.FailedAutoTopUpCaseValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.TicketTypeValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.UserCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Controller for add or renew or top-up ticket
 */
@Controller
@RequestMapping(value = PageUrl.TOP_UP_TICKET)
public class TopUpTicketController extends OnlineBaseController {
    static final Logger logger = LoggerFactory.getLogger(TopUpTicketController.class);

    @Autowired
    protected AutoTopUpPayAsYouGoValidator autoTopUpPayAsYouGoValidator;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected BusPassService busPassService;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected PayAsYouGoValidator payAsYouGoValidator;
    @Autowired
    protected SelectCardValidator selectCardValidator;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected TicketTypeValidator ticketTypeValidator;
    @Autowired
    protected TravelCardValidator travelCardValidator;
    @Autowired
    protected UserCartValidator userCartValidator;
    @Autowired
    protected ZoneMappingValidator zoneMappingValidator;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected TopUpTicketService topUpTicketService;
    @Autowired
    protected ZoneService zoneService;
    @Autowired
    protected EmailNotificationValidator emailNotificationValidator;
    @Autowired
    protected FailedAutoTopUpCaseValidator failedAutoTopUpValidator;
    @Autowired
    protected FailedAutoTopUpCaseService failedAutoTopUpCaseService;
    
    @ModelAttribute
    public void populatePayAsYouGoCreditBalancesSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

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
        model.addAttribute(PageAttribute.START_DATES, cartAdminService.getUserProductStartDateList());
    }

    @ModelAttribute
    public void populateEmailRemindersSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_EMAIL_REMINDERS,
                selectListService.getSelectList(PageSelectList.BASKET_EMAIL_REMINDERS));
    }

    @ModelAttribute
    public void populatePayAsYouGoAutoTopUpAmtsSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showTopUpTicket(HttpSession session, @ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cmd, Model model) {
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardDataService.findById(cardId).getCardNumber());
        cmd.setAutoTopUpVisible(cardService.getAutoTopUpVisibleOptionForCard(cardId));
        CartDTO cartDTO = cartService.createCartFromCardId(cardId);
        cartDTO = cartService.removeExpiredCartItems(cartDTO);
        CartSessionData cartSessionData = new CartSessionData(cartDTO.getId());
        cartSessionData.setAutoTopUpVisible(cmd.getAutoTopUpVisible());
        cartSessionData.setPageName(Page.TOP_UP_TICKET);
        CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        model.addAttribute(PageAttribute.CART_DTO);
        checkOysterCardFailedAutoTopUpCase(cmd);
        return new ModelAndView(PageView.TOP_UP_TICKET, PageCommand.CART_ITEM, cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView topUpTicket(HttpSession session, @ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cmd,
                                    BindingResult result, RedirectAttributes redirectAttributes, Model model) {
    	checkOysterCardFailedAutoTopUpCase(cmd);
    	ticketTypeValidator.validate(cmd, result);
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        session.setAttribute(TICKET_TYPE, cmd.getTicketType());
    	if (!result.hasErrors()) {
    		CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
    		ModelAndView redirectView = resolveRedirectView(cartDTO, cmd, result, redirectAttributes, model);
    		if (redirectView != null) {
    			return redirectView;
    		}
        }
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        cmd.setAutoTopUpVisible(cardService.getAutoTopUpVisibleOptionForCard(cardId));
        return new ModelAndView(PageView.TOP_UP_TICKET, PageCommand.CART_ITEM, cmd);
    }

    protected void checkOysterCardFailedAutoTopUpCase(CartItemCmdImpl cmd) {
    	String cardNumber = cmd.getCardNumber();
    	boolean isOysterCardWithFailedAutoTopUpCase = failedAutoTopUpCaseService.isOysterCardWithFailedAutoTopUpCase(cardNumber);
    	cmd.setOysterCardWithFailedAutoTopUpCaseCheck(isOysterCardWithFailedAutoTopUpCase);
	}

	protected ModelAndView resolveRedirectView(CartDTO cartDTO, CartItemCmdImpl cmd, BindingResult result,
                                               RedirectAttributes redirectAttributes, Model model) {
        ModelAndView redirectView = null;
        TicketType ticketType = TicketType.lookUpTicketType(cmd.getTicketType());
        switch (ticketType) {
            case PAY_AS_YOU_GO:
                redirectView = addPayAsYouGo(cartDTO, cmd, result, model);
                break;
            case PAY_AS_YOU_GO_AUTO_TOP_UP:
                redirectView = addAutoTopUpPayAsYouGo(cartDTO, cmd, result, model);
                break;
            case TRAVEL_CARD:
                redirectView = addTravelCard(cartDTO, cmd, result, redirectAttributes, model);
                break;
            default:
                break;
        }
        return redirectView;
    }

    protected ModelAndView addPayAsYouGo(CartDTO cartDTO, CartItemCmdImpl cmd, BindingResult result, Model model) {
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.USER_CART));
        redirectView.addObject(CARD_ID, cmd.getCardId());
        setExistingCartDTOCreditBalanceWithCurrentCmd(cartDTO, cmd);
        cmd.setCartId(cartDTO.getId());
        payAsYouGoValidator.validate(cmd, result);
        userCartValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            cmd.setAutoTopUpCreditBalance(null);
            cartDTO = payAsYouGoService.addCartItemForExistingCard(cartDTO, cmd);
            model.addAttribute(PageAttribute.CART_DTO);
            return redirectView;
        }
        return null;
    }

    protected void setExistingCartDTOCreditBalanceWithCurrentCmd(CartDTO cartDTO, CartItemCmdImpl cmd) {
        List<ItemDTO> listOfItems = cartDTO.getCartItems();
        for (final Iterator<ItemDTO> iterator = listOfItems.iterator(); iterator.hasNext(); ) {
            ItemDTO itemDTO = iterator.next();
            Integer existingPayAsYouGoPrice = itemDTO.getPrice();
            if ((itemDTO.getClass().equals(PayAsYouGoItemDTO.class)) && (existingPayAsYouGoPrice > 0)) {
                cmd.setExistingCreditBalance(existingPayAsYouGoPrice);
            }
        }
    }

    protected ModelAndView addAutoTopUpPayAsYouGo(CartDTO cartDTO, CartItemCmdImpl cmd, BindingResult result, Model model) {
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.COLLECT_PURCHASE));
        redirectView.addObject(CARD_ID, cmd.getCardId());
        setExistingCartDTOCreditBalanceWithCurrentCmd(cartDTO, cmd);
        autoTopUpPayAsYouGoValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            cmd.setCreditBalance(null);
            cmd.setAutoTopUpActivity(SET_UP.getCode());
            cartDTO = payAsYouGoService.addCartItemForExistingCard(cartDTO, cmd);
            model.addAttribute(PageAttribute.CART_DTO);
            return redirectView;
        }
        return null;
    }

    protected ModelAndView addTravelCard(CartDTO cartDTO, CartItemCmdImpl cmd, BindingResult result,
                                         RedirectAttributes redirectAttributes, Model model) {
        ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.USER_CART));
        redirectView.addObject(CARD_ID, cmd.getCardId());
        travelCardValidator.validate(cmd, result);
        cardService.updatePassengerAndDiscountForCardItemCmd(cmd);
        zoneMappingValidator.validate(cmd, result);
        emailNotificationValidator.validate(cmd, result);

        cmd.setCartId(cartDTO.getId());
        userCartValidator.validate(cmd, result);

        if (!result.hasErrors()) {
            cartDTO = travelCardService.addCartItemForExistingCard(cartDTO, cmd);
            model.addAttribute(PageAttribute.CART_DTO);
            if (isNotEmpty(cmd.getStatusMessage())) {
                setFlashStatusMessage(redirectAttributes, cmd.getStatusMessage());
            }
            return redirectView;
        }
        return null;
    }

    protected CardInfoResponseV2DTO getCardProductDTOFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_SHOPPING_BASKET, method = RequestMethod.POST)
    public ModelAndView shoppingBasket(@ModelAttribute(PageCommand.CART_ITEM) CartItemCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(PageUrl.USER_CART));
        modelAndView.addObject(CARD_ID, cmd.getCardId());
        return modelAndView;
    }
    
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_RESETTLE, method = RequestMethod.POST)
    public ModelAndView resettleFailedAutoTopUp(@ModelAttribute(PageCommand.FAILED_AUTOTOPUP_RESETTLEMENT) CartItemCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView(PageUrl.RESETTLE_FAILED_AUTO_TOP_UP));
        modelAndView.addObject(CARD_ID, cmd.getCardId());
        modelAndView.addObject(new FailedAutoTopUpCaseCmdImpl());
        return modelAndView;
    }
    
    @Override
    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel() {
        return new ModelAndView(new RedirectView(PageUrl.VIEW_OYSTER_CARD));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String getEndZones(@RequestParam String pageListName, @RequestParam Integer startZone,
                              @RequestParam String travelCardType) {
        return new Gson()
                .toJson(zoneService.getAvailableZonesForTravelCardTypeAndStartZone(pageListName, startZone, travelCardType)
                        .getOptions());
    }

}
