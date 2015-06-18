package com.novacroft.nemo.tfl.innovator.controller.purchase;

import static com.novacroft.nemo.common.constant.DateConstant.DAY_WEEK_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.Durations.ANNUAL;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.ProductDateUtil.getProductStartDates;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_AVAILABLE_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.START_DATES;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CUSTOMER_ID;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_ORDER_ADD;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.BusPassService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.controller.purchase.BasePurchaseController;
import com.novacroft.nemo.tfl.common.form_validator.BusPassValidator;
import com.novacroft.nemo.tfl.common.form_validator.CartValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.form_validator.TravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.ZoneMappingValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Controller
@RequestMapping(value = Page.CART)
public class CartController extends BasePurchaseController {
    static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected PayAsYouGoValidator payAsYouGoValidator;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected BusPassService busPassService;
    @Autowired
    protected CartValidator cartValidator;
    @Autowired
    protected TravelCardValidator travelCardValidator;
    @Autowired
    protected ZoneMappingValidator zoneMappingValidator;
    @Autowired
    protected BusPassValidator busPassValidator;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
	protected CardService cardService;

    @ModelAttribute(PageCommand.CART)
    public CartCmdImpl getCartCmd() {
        return new CartCmdImpl();
    }

    @ModelAttribute
    public void populateModels(Model model) {
        populateCartShippingSelectList(model);
        populateBasketTicketTypesSelectList(model);
        populatePayAsYouGoCreditBalancesSelectList(model);
        populatePayAsYouGoAutoTopUpAmtsSelectList(model);
        populateStartDatesSelectList(model);
        populateEmailRemindersSelectList(model);
        populateTravelCardTypesSelectList(model);
        populateTravelCardZonesSelectList(model);
    }

    public void populateCartShippingSelectList(Model model) {
        model.addAttribute(PageAttribute.CART_SHIPPING_METHODS,
                selectListService.getSelectList(PageSelectList.CART_SHIPPING_METHODS));
    }

    @ModelAttribute
    public void populateSecurityQuestionSelectList(Model model) {
        model.addAttribute(PageAttribute.SECURITY_QUESTIONS, selectListService.getSelectList(PageSelectList.SECURITY_QUESTIONS));
    }

    public void populateBasketTicketTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_TICKET_TYPES,
                selectListService.getSelectList(PageSelectList.BASKET_TICKET_TYPES));
    }

    public void populatePayAsYouGoCreditBalancesSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_CREDIT_BALANCES,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_CREDIT_BALANCES));
    }

    public void populatePayAsYouGoAutoTopUpAmtsSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    public void populateStartDatesSelectList(Model model) {
        model.addAttribute(PageAttribute.START_DATES, getProductStartDateList());
    }

    protected SelectListDTO getProductStartDateList() {
        List<Date> startDateList = getProductStartDates(systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS), systemParameterService.getIntegerParameterValue(PRODUCT_AVAILABLE_DAYS), systemParameterService.getIntegerParameterValue(MAXIMUM_HOUR_OF_DAY_BEFORE_DENYING_NEXTDAY_TRAVEL_START), systemParameterService.getIntegerParameterValue(MAXIMUM_MINUTES_BEFORE_DENYING_NEXTDAY_TRAVEL_START));
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(START_DATES);
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (Date startDate : startDateList) {
            selectListDTO.getOptions()
                    .add(new SelectListOptionDTO(formatDate(startDate), formatDate(startDate, DAY_WEEK_DATE_PATTERN)));
        }
        return selectListDTO;
    }

    public void populateEmailRemindersSelectList(Model model) {
        model.addAttribute(PageAttribute.BASKET_EMAIL_REMINDERS,
                selectListService.getSelectList(PageSelectList.BASKET_EMAIL_REMINDERS));
    }

    public void populateTravelCardTypesSelectList(Model model) {
        model.addAttribute(PageAttribute.TRAVEL_CARD_TYPES, selectListService.getSelectList(PageSelectList.TRAVEL_CARD_TYPES));
    }

    public void populateTravelCardZonesSelectList(Model model) {
        model.addAttribute(PageAttribute.TRAVEL_CARD_ZONES, selectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewCart(HttpSession session, @RequestParam(value = CUSTOMER_ID) Long customerId) {
        CartDTO cartDTO = cartService.createCartFromCustomerId(customerId);

        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, new CartCmdImpl());
        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        modelAndView.addObject(PageCommand.CART_ITEM, new CartItemCmdImpl());

        storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId());

        return modelAndView;
    }

    protected void storeCartIdInCartSessionDataDTOInSession(HttpSession session, Long cartId) {
        CartUtil.addCartSessionDataDTOToSession(session, new CartSessionData(cartId));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView continueCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
    	CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartDTO = addUpdateShippingMethodToCart(cartDTO, cmd.getShippingType());
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        getTicketType(cartDTO,cartSessionData);
        return new ModelAndView(new RedirectView(PageUrl.VERIFY_SECURITY_QUESTION));
    }

    protected void getTicketType(CartDTO cartDTO, CartSessionData cartSessionData) {
        boolean payAsYouItem = false;
        boolean autoTopUpItem = false;
        for (ItemDTO itemDTOObj : cartDTO.getCartItems()) {
            if (itemDTOObj.getClass().equals(PayAsYouGoItemDTO.class)) {
                payAsYouItem = true;
            }
            if (itemDTOObj.getClass().equals(AutoTopUpConfigurationItemDTO.class)) {
                autoTopUpItem = true;
            }
        }
        if (payAsYouItem && autoTopUpItem) {
            cartSessionData.setTicketType(TicketType.PAY_AS_YOU_GO_AUTO_TOP_UP.code());
        }
    }
    
    protected CartDTO getCartDTOUsingCartSessionDataDTOInSession(HttpSession session) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        return cartService.findById(cartSessionData.getCartId());
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_DELETE, method = RequestMethod.POST)
    public ModelAndView deleteItem(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                   @RequestParam(value = LINE_NO) Long lineNo) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cmd);
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartDTO = cartService.deleteItem(cartDTO, lineNo);
        cartDTO = cartAdminService.removeRefundableDepositAndShippingCost(cartDTO);
        cartDTO = payAsYouGoService.removeNonApplicableAutoTopUpCartItem(cartDTO);
        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_EMPTY_BASKET, method = RequestMethod.POST)
    public ModelAndView emptyBasket(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cmd);

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);

        cartDTO = cartService.emptyCart(cartDTO);

        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_UPDATE_TOTAL, method = RequestMethod.POST)
    public ModelAndView updateTotalCost(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cmd);

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);

        cartDTO = addUpdateShippingMethodToCart(cartDTO, cmd.getShippingType());

        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        return modelAndView;
    }

    protected CartDTO addUpdateShippingMethodToCart(CartDTO cartDTO, String shippingType) {
        if (!StringUtil.isBlank(shippingType)) {
            CartItemCmdImpl cartItemCmd = new CartItemCmdImpl();
            cartItemCmd.setShippingMethodType(shippingType);
            cartDTO = cartAdminService.applyShippingCost(cartDTO, cartItemCmd);
        }
        return cartDTO;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_PAY_AS_YOU_GO_ITEM_TO_CART, method = RequestMethod.POST)
    public ModelAndView addPayAsYouGoToCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd,
                                            BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cmd);
        validateUsingPayAsYouGoValidator(cmd.getCartItemCmd(), result);

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);

        if (!result.hasErrors()) {
            cartDTO = payAsYouGoService.addCartItemForNewCard(cartDTO, cmd.getCartItemCmd());
        }

        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_BUS_PASS_ITEM_TO_CART, method = RequestMethod.POST)
    public ModelAndView addBusPassToCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmdImpl,
                                         BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cartCmdImpl);

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartCmdImpl.getCartItemCmd().setCartId(cartDTO.getId());

        validateUsingBusPassValidator(cartCmdImpl.getCartItemCmd(), result);

        if (!result.hasErrors()) {
            cartCmdImpl.getCartItemCmd().setTicketType(cartCmdImpl.getTicketType());
            cartCmdImpl.getCartItemCmd().setTravelCardType(ANNUAL.getDurationType());
            cartDTO = busPassService.addCartItemForNewCard(cartDTO, cartCmdImpl.getCartItemCmd());
        }

        modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
        return modelAndView;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_ADD_TRAVEL_CARD_ITEM_TO_CART, method = RequestMethod.POST)
    public ModelAndView addTravelCardToCart(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmdImpl,
                                            BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView(INV_ORDER_ADD, PageCommand.CART, cartCmdImpl);

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartCmdImpl.getCartItemCmd().setCartId(cartDTO.getId());

        validateAddTravelCard(cartCmdImpl, cartCmdImpl.getCartItemCmd(), result);

        if (!result.hasErrors()) {
            cartCmdImpl.setAutoTopUpVisible(false);
            cartDTO = travelCardService.addCartItemForNewCard(cartDTO, cartCmdImpl.getCartItemCmd());
            modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
            return modelAndView;
        } else {
            modelAndView.addObject(PageAttribute.CART_DTO, cartDTO);
            return modelAndView;
        }
    }

    protected void validateAddTravelCard(CartCmdImpl cmd, CartItemCmdImpl cartItemCmd, BindingResult result) {
        result.pushNestedPath("cartItemCmd");
        travelCardValidator.validate(cartItemCmd, result);
        cardService.updatePassengerAndDiscountForCardItemCmd(cartItemCmd);
        zoneMappingValidator.validate(cartItemCmd, result);
        if (!result.hasErrors()) {
            cartValidator.validate(cartItemCmd, result);
        }
        result.popNestedPath();
    }

    protected void validateUsingPayAsYouGoValidator(CartItemCmdImpl cartItemCmd, BindingResult result) {
        result.pushNestedPath("cartItemCmd");
        ValidationUtils.invokeValidator(payAsYouGoValidator, cartItemCmd, result);
        result.popNestedPath();
    }

    protected void validateUsingBusPassValidator(CartItemCmdImpl cartItemCmd, BindingResult result) {
        result.pushNestedPath("cartItemCmd");
        ValidationUtils.invokeValidator(busPassValidator, cartItemCmd, result);
        result.popNestedPath();
    }
}
