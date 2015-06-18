package com.novacroft.nemo.tfl.common.controller;

import static com.novacroft.nemo.tfl.common.constant.AutoLoadState.NO_TOP_UP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.NumberUtil;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.OrderService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.PaymentCardService;
import com.novacroft.nemo.tfl.common.application_service.PaymentService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.command.impl.ManagePaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.AutoLoadState;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameterArgument;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpItemDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.form_validator.AutoTopUpValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public abstract class BaseManageAutoTopUpController extends BaseController {
    protected static final String OLD_CARD = "OLD_CARD";
    protected static final int MONTHS_NEEDED_IN_ADVANCE = 2;

    @Autowired
    protected LocationSelectListService locationSelectListService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected SelectStationValidator selectStationValidator;
    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected AutoTopUpValidator autoTopUpValidator;
    @Autowired
    protected CardUpdateService cardUpdateService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected PaymentService paymentService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected PayAsYouGoService payAsYouGoService;
    @Autowired
    protected AutoTopUpItemDataService autoTopUpItemDataService;
    @Autowired
    protected AutoTopUpConfigurationService autoTopUpConfigurationService;
    @Autowired
    protected PaymentCardService paymentCardService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected PaymentCardSelectListService paymentCardSelectListService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected OrderService orderService;
    
    @ModelAttribute
    public void populateLocationsSelectList(Model model) {
        model.addAttribute(PageAttribute.LOCATIONS, this.locationSelectListService.getLocationSelectList());
    }

    @ModelAttribute
    public void populatePayAsYouGoAutoTopUpAmtsSelectList(Model model) {
        model.addAttribute(PageAttribute.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS,
                selectListService.getSelectList(PageSelectList.PAY_AS_YOU_GO_AUTO_TOPUP_AMOUNTS));
    }

    @ModelAttribute
    public void populatePaymentCardTypeSelectList(Model model) {
        model.addAttribute(PageAttribute.PAYMENT_CARD_TYPES,
                this.selectListService.getSelectList(PageSelectList.PAYMENT_CARD_TYPES));
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_VIEW_ACTIVATE_AUTO_TOP_UP, method = RequestMethod.POST)
    public ModelAndView manageAutoTopUpActivate(HttpSession session, @ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd,
                                                BindingResult result) {

        cmd.setStartDateforAutoTopUpCardActivate(cmd.getStartDateforAutoTopUpCardActivate());
        cmd.setEndDateforAutoTopUpCardActivate(cmd.getEndDateforAutoTopUpCardActivate());
        Long cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        this.selectStationValidator.validate(cmd, result);
        if (result.hasErrors()) {
            return new ModelAndView(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP, PageCommand.MANAGE_CARD, cmd);
        } else {
            cmd.setStationName(
                    locationDataService.getActiveLocationById(NumberUtil.convertLongToInt(cmd.getStationId())).getName());
            if (cmd.getAutoTopUpState() > 0) {
                processManageAutoTopUp(session, cmd);
            } else {
                processManageAutoTopUpNoTopUpRequired(cmd, cardId);
            }
            Date date = DateUtil.parse(cmd.getStartDateforAutoTopUpCardActivate(), "dd/MM/yyyy");
        	cmd.setIsStartDateTomorrow((DateUtil.isTomorrow(date)));
        	updateDatesFormats(cmd);
            addCustomerEmailForAutoTopUpConfirmation(cmd);
            return new ModelAndView(PageView.ACTIVATE_CHANGES_TO_AUTO_TOP_UP_CONFIRM, PageCommand.MANAGE_CARD, cmd);
        }
    }
    
    protected void updateDatesFormats(ManageCardCmd cmd) {
    	DateFormat formatterDestination = new SimpleDateFormat(DateConstant.DAYINMONTH_SHORTMONTH_YEAR);
		Date startDate = DateUtil.parse(cmd.getStartDateforAutoTopUpCardActivate(), "dd/MM/yyyy");
		Date endDate = DateUtil.parse(cmd.getEndDateforAutoTopUpCardActivate(), "dd/MM/yyyy");
		if (startDate != null && endDate != null) {
			cmd.setStartDateforAutoTopUpCardActivate(formatterDestination.format(startDate).toString());
        	cmd.setEndDateforAutoTopUpCardActivate(formatterDestination.format(endDate).toString());
		}
    }

    protected void processManageAutoTopUp(HttpSession session, ManageCardCmd cmd) {
        Long cardId;
        cardId = (Long) this.getFromSession(session, CartAttribute.CARD_ID);
        CartDTO cartDTO = cartService.createCartFromCardId(cardId);
        cartService.emptyCart(cartDTO);
        CardDTO cardDTO = cardService.getCardDTOById(cardId);

        CartCmdImpl cartCmd = createCartCmdImplForOrderSettltement(cardDTO, cmd);

        cartDTO = cartService.addUpdateItem(cartDTO, createCartItemCmdImpl(cartCmd), AutoTopUpConfigurationItemDTO.class);

        cardUpdateService
                .requestCardAutoLoadChange(cardId, cmd.getStationId(), AutoLoadState.lookUpState(cmd.getAutoTopUpState()));

        cartCmd = this.paymentService.createOrderAndSettlementsFromManagedAutoTopUp(cartDTO, cartCmd);
        ManagePaymentCardCmdImpl managecard = paymentCardService.getPaymentCards(getCustomerId(cardId));

        cmd.setOrderNumber(cartCmd.getCartDTO().getOrder().getOrderNumber());
        cmd.setPaymentCards(managecard.getPaymentCards());

        this.autoTopUpConfigurationService
                .changeConfiguration(cardId, cartCmd.getCartDTO().getOrder().getId(), cmd.getAutoTopUpState(), cartCmd.getStationId());
    }

    protected void processManageAutoTopUpNoTopUpRequired(ManageCardCmd cmd, Long cardId) {
        cardUpdateService.requestCardAutoLoadChange(cardId, cmd.getStationId(), NO_TOP_UP.state());
    }

    protected CartCmdImpl fillValueFromManageCardCmdToCartCmdImpl(ManageCardCmd cmd) {
        CartCmdImpl cartCmd =
                new CartCmdImpl(cmd.getCardNumber(), getCustomerId(cmd.getCardId()), 0, 0, cmd.getStationId(), true,
                        cmd.getCardId(), TicketType.AUTO_TOP_UP.code(), cmd.getAutoTopUpState(), cmd.getPaymentCardID(),
                        cmd.getStartDateforAutoTopUpCardActivate(), cmd.getEndDateforAutoTopUpCardActivate());
        cmd.setStationId(cmd.getStationId());
        cartCmd.setStationName(cmd.getStationName());
        cmd.setPaymentCardID(cmd.getCardId());
        return cartCmd;
    }

    protected CartItemCmdImpl createCartItemCmdImpl(CartCmdImpl cmd) {
        CartItemCmdImpl cartItemCmd = new CartItemCmdImpl();
        cartItemCmd.setAutoTopUpUpdate(Boolean.TRUE);
        cartItemCmd.setAutoTopUpAmt(cmd.getAutoTopUpAmount());
        cartItemCmd.setAutoTopUpActivity(cmd.getAutoTopUpActivity());
        return cartItemCmd;
    }

    public Long getCustomerId(Long cardId) {
        return this.customerDataService.findByCardId((cardId)).getId();
    }

    private CartCmdImpl createCartCmdImplForOrderSettltement(CardDTO cardDTO, ManageCardCmd cmd) {
        CartCmdImpl cartCmd =
                new CartCmdImpl(cmd.getCardNumber(), cardDTO.getWebaccountId(), 0, 0, cmd.getStationId(), true, cardDTO.getId(),
                        TicketType.AUTO_TOP_UP.code(), cmd.getAutoTopUpState(), cmd.getPaymentCardID(),
                        cmd.getStartDateforAutoTopUpCardActivate(), cmd.getEndDateforAutoTopUpCardActivate());
        cartCmd.setCustomerId(cardDTO.getCustomerId());
        cartCmd.setCardId(cardDTO.getId());
        cartCmd.setAutoTopUpActivity(cmd.getAutoTopUpActivity());
        return cartCmd;
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancel(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd) {
        return cancelView(cmd);
    }

    @RequestMapping(params = PageParameterArgument.TARGET_ACTION_BACK_TO_MANAGE_AUTO_TOP_UP_PAGE, method = RequestMethod.POST)
    public ModelAndView backToManageAutoTopUp(@ModelAttribute(PageCommand.MANAGE_CARD) ManageCardCmd cmd) {
        return new ModelAndView(PageView.MANAGE_AUTO_TOP_UP, PageCommand.MANAGE_CARD, cmd);
    }

    public boolean checkManageCardCmdForChanges(ManageCardCmd manageCardCmd) {
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setCardNumber(manageCardCmd.getCardNumber());
        cmd.setCustomerId(manageCardCmd.getCustomerId());
        cmd.setStationId(manageCardCmd.getStationId());
        cardService.populateManageCardCmdWithCubicCardDetails(cmd.getCardNumber(), cmd.getCustomerId(), cmd);
        return cmd.equals(manageCardCmd);
    }
    
    protected void addCustomerEmailForAutoTopUpConfirmation(ManageCardCmd manageCardCmd){
    	manageCardCmd.setEmailAddress(getCustomer().getEmailAddress());
    }
    
    protected Long getLoggedInUserCustomerId() {
        return this.securityService.getLoggedInCustomer().getId();
    }
    
    protected abstract ModelAndView completeView(ManageCardCmd manageCardCmd);

    protected abstract ModelAndView cancelView(ManageCardCmd manageCardCmd);

    protected abstract CustomerDTO getCustomer();

}