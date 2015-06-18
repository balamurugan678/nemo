package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CART_ITEM_ID;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.REFUND_CALCULATION_BASIS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_REDIRECT_TO_TRANSFERS;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_UPDATE_CART_TOTAL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_UPDATE_REFUND_CALCULATION_BASIS;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_VIEW_CART_USING_CUBIC;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.MIN_START_DATE_FOR_CALENDAR;
import static com.novacroft.nemo.tfl.common.constant.TicketType.GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.TicketType.TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.TransferConstants.LOST_STOLEN_MODE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.NumberUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.ManageGoodwillService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.form_validator.AddUnlistedProductValidator;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundPaymentValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;
import com.novacroft.nemo.tfl.common.form_validator.TradedTicketValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;

@Controller
@SessionAttributes(CART)
public abstract class RefundController extends BaseController {

    @Autowired
    protected RefundSelectListService refundSelectListService;
    @Autowired
    protected ManageGoodwillService manageGoodwillService;
    @Autowired
    protected RefundPaymentValidator refundPaymentValidator;
    @Autowired
    protected AddUnattachedCardService addUnattachedCardService;
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected RefundService refundService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected AddUnlistedProductValidator addUnlistedProductValidator;
    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;
    @Autowired
    protected RefundPaymentService refundPaymentService;
    @Autowired
    protected HotlistCardService hotlistCardService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartAdministrationService cartAdministrationService;
    @Autowired
    protected RefundCartPaymentValidator refundCartPaymentValidator;
    @Autowired
    protected WebCreditSettlementDataService webCreditSettlementDataService;
    @Autowired
    protected GoodwillValidator goodwillValidator;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected RefundToWorkflowConverter refundToWorkflowConverter;
 	@Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    
    @Autowired
    @Qualifier(value="BaseWorkFlowGeneratorStrategy")
    protected WorkFlowGeneratorStrategy workFlowGeneratorStrategy; 

 	@Autowired
    protected CountrySelectListService countrySelectListService;
 	
 	@Autowired
    protected TradedTicketValidator tradedTicketValidator;
 	
 	@Autowired
 	protected CountryDataService countryDataService;
 	
 	@Autowired
 	protected SelectStationValidator selectStationValidator;
 	


    public static final String FAILED_VALIDATION = "failedValidation";
    public static final int HOTLIST_REASON_ID = 1;

    protected abstract String getCartTypeCode();

    protected abstract String getRefundType();

    protected abstract BaseValidator getValidator();

    @InitBinder
    protected void initBinder(final ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = DateUtil.createShortDateFormatter();
        dateFormat.setLenient(false);
        final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @ModelAttribute
    public void populateModelAttributes(Model model) {
        refundSelectListService.getSelectListModel(model);
        model.addAttribute(PageAttribute.COUNTRIES, countrySelectListService.getSelectList());
    }
    
    @ModelAttribute
    public void populateMinStartDateForCalendarIcon(Model model) {
        model.addAttribute(PageAttribute.MIN_START_DATE_FOR_REFUND_CALENDAR, systemParameterService.getParameterValue(MIN_START_DATE_FOR_CALENDAR.code()));
    }
    
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewCart(HttpSession session, @RequestParam("id") String cardNumber, @RequestParam(PageParameter.APPROVAL_ID) Long approvalId) {
		ModelAndView modelAndView = new ModelAndView(getRefundType());
        CartDTO cartDTO = cartService.findByApprovalId(approvalId);
        storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId());
        modelAndView.addObject(CART_DTO, cartDTO);
        
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
		refundService.updatePayAsYouGoValueInCartCmdImpl(cartCmdImpl, cartDTO);
        refundService.updateAdministrationFeeValueInCartCmdImpl(cartCmdImpl, cartDTO);
        refundService.updateDateOfRefundInCartCmdImpl(cartCmdImpl, cartDTO);
        refundService.updatePaymentDetailsInCartCmdImpl(cartCmdImpl, cartDTO);
        
        setOysterCardsList(cartCmdImpl, modelAndView, cartDTO);
        updatePreviousWebCredit(cartCmdImpl);
        cartCmdImpl.setCartDTO(cartDTO);
        modelAndView.addObject(CART, cartCmdImpl);
        
        return modelAndView;
    }
	
	@RequestMapping(params = TARGET_ACTION_VIEW_CART_USING_CUBIC, method = RequestMethod.GET)
    public ModelAndView viewCartUsingCubic(HttpSession session, @RequestParam(ID) String cardNumber) {
        ModelAndView modelAndView = new ModelAndView(getRefundType());
        
        CartDTO cartDTO = refundService.getCartDTOForRefund(cardNumber, getCartTypeCode(), true);
        storeCartIdInCartSessionDataDTOInSession(session, cartDTO.getId());
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        refundService.updateCustomerNameAndAddress(cardNumber, cartCmdImpl,cartDTO);
		refundService.updatePayAsYouGoValueInCartCmdImpl(cartCmdImpl, cartDTO);
        refundService.updateAdministrationFeeValueInCartCmdImpl(cartCmdImpl, cartDTO);
        refundService.updateDateOfRefundInCartCmdImpl(cartCmdImpl, cartDTO);
        setOysterCardsList(cartCmdImpl, modelAndView, cartDTO);
        
        updatePreviousWebCredit(cartCmdImpl);
        modelAndView.addObject(CART, cartCmdImpl);
        cartCmdImpl.setCartDTO(cartDTO);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
	

	protected void storeCartIdInCartSessionDataDTOInSession(HttpSession session, Long cartId) {
		CartUtil.addCartSessionDataDTOToSession(session, new CartSessionData(cartId));
    }
    
    @RequestMapping(params = TARGET_ACTION_ADD_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView addTravelCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(getRefundType());
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartDTO.setCartType(getCartTypeCode());
        cmd.setCartDTO(cartDTO);
        addUnlistedProductValidator.validate(cmd, result);
        
        String inputTravelcardType = cmd.getCartItemCmd().getTravelCardType();
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cmd.getCartItemCmd());
        
        setOysterCardsList(cmd, modelAndView, cartDTO);
        
        if (!result.hasErrors()) {
        	cmd.getCartItemCmd().setCardId(cartDTO.getCardId());
        	cartDTO.setCardId(cartDTO.getCardId());
        	cartDTO = refundService.addTravelCardToCart(cartDTO, cmd, getCartTypeCode());
            cmd.setStationId(cmd.getStationId());
            cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cartDTO.getCardId(), getCartTypeCode());
            refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
            clearTravelcardDetailsFromCommand(cmd.getCartItemCmd());
        }else{
            cmd.getCartItemCmd().setTravelCardType(inputTravelcardType);
        }
        cartDTO.setCartType(getCartTypeCode());
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
    
    protected CartDTO getCartDTOUsingCartSessionDataDTOInSession(HttpSession session) {
        CartSessionData cartSessionData = getCartSessionDataDTOFromSession(session);
        CartDTO cartDTO = cartService.findById(cartSessionData.getCartId());
        return cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
    }
    
    protected CartSessionData getCartSessionDataDTOFromSession(HttpSession session) {
        return (CartSessionData) session.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }
    
    @RequestMapping(params = TARGET_ACTION_DELETE_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView deleteTravelCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, @RequestParam(value = CART_ITEM_ID) int cartItemId) {
        ModelAndView modelAndView = new ModelAndView(getRefundType());
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        setOysterCardsList(cmd, modelAndView, cartDTO);
        
        cartDTO = cartService.deleteItem(cartDTO, new Long(cartItemId));
        cartDTO = cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        
        cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cartDTO.getId(), getCartTypeCode());
        
        refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
        cmd.setStationId(cmd.getStationId());
        
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }

    protected void setOysterCardsList(CartCmdImpl cmd, ModelAndView modelAndView, CartDTO cartDTO) {
		updateCardNumberInCartCmdImpl(cmd, cartDTO);
		if (cmd.getCardNumber() != null) {
	    	modelAndView.addObject(PageCommandAttribute.FIELD_SELECT_CARD, refundPaymentService.createCardsSelectListForAdHocLoad(cmd.getCardNumber()));
		}
    }
    
    protected void updateCardNumberInCartCmdImpl(CartCmdImpl cmd, CartDTO cartDTO) {
		if (cartDTO != null) {
	    	CardDTO cardDTO = cardDataService.findById(cartDTO.getCardId());
	    	cmd.setCardNumber(cardDTO.getCardNumber());
		}
    }
    
    @RequestMapping(params = TARGET_ACTION_UPDATE_REFUND_CALCULATION_BASIS, method = RequestMethod.POST)
    public ModelAndView updateRefundCalculationBasis(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, @RequestParam(value = CART_ITEM_ID) long cartItemId, @RequestParam(value = REFUND_CALCULATION_BASIS) String refundCalculationBasis) {
		ModelAndView modelAndView = new ModelAndView(getRefundType());
		CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
		setOysterCardsList(cmd, modelAndView, cartDTO);
	
		cartDTO = refundService.updateProductItemDTO(cartDTO.getId(), cartItemId, refundCalculationBasis);
		
		cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cartDTO.getId(), getCartTypeCode());
        refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
        cartDTO.setCartType(getCartTypeCode());
        modelAndView.addObject(CART_DTO, cartDTO);
		return modelAndView;
    }
	
	@RequestMapping(params = TARGET_ACTION_UPDATE_CART_TOTAL, method = RequestMethod.POST)
    public ModelAndView updateCartTotal(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
		getValidator().validate(cmd, result);
		ModelAndView modelAndView = new ModelAndView(getRefundType());
		CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
    	setOysterCardsList(cmd, modelAndView, cartDTO);
		
		if (!result.hasErrors()) {
			cartDTO = refundService.updatePayAsYouGoItemDTO(cartDTO, cmd.getPayAsYouGoValue());
			cartDTO = refundService.updateAdministrationFeeItemDTO(cartDTO, cmd.getAdministrationFeeValue());
			
			if(cmd.getCartItemCmd() != null && CartType.CANCEL_SURRENDER_REFUND.code().equals(cmd.getCartItemCmd().getCartType())){
			    if(cmd.getCartItemCmd().getDeceasedCustomer()) {
			        cartDTO = refundService.updateDateOfRefundAndRefundCalculationBasis(cartDTO, cmd.getCartItemCmd().getDateOfLastUsage(), RefundCalculationBasis.PRO_RATA.code());    
			    } else if(cmd.getCartItemCmd().getBackdated()) {
			        cartDTO = refundService.updateDateOfRefund(cartDTO, cmd.getCartItemCmd().getDateOfCanceAndSurrender());
			    } else {
			        cartDTO = refundService.updateDateOfRefund(cartDTO, cmd.getDateOfRefund());
			    }
			} else {
			    cartDTO = refundService.updateDateOfRefund(cartDTO, cmd.getDateOfRefund());
			}
			
	    	refundService.updatePayAsYouGoValueInCartCmdImpl(cmd, cartDTO);
	    	refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
		} else {
	    	modelAndView.setViewName(getRefundType());
	    	modelAndView.addObject(FAILED_VALIDATION, true);
		}
		modelAndView.addObject(CART_DTO, cartDTO);
    	return modelAndView;
    }
	
	@RequestMapping(params = TARGET_ACTION_REDIRECT_TO_TRANSFERS, method = RequestMethod.POST)
	public ModelAndView redirectToTransfers(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
		CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cartDTO = cartService.emptyCart(cartDTO);
		createCartSessionData(session, cmd);
		CardDTO cardDTO = null;
    	if (cartDTO != null) {
    		cardDTO = cardDataService.findById(cartDTO.getCardId());
    		cmd.setSourceCardNumber(cardDTO.getCardNumber());
    	}
    	Long cardId = (null != cardDTO ? cardDTO.getId() : null);
    	cmd.setLostOrStolenMode(Boolean.TRUE);
		ModelAndView mav = new ModelAndView(new RedirectView(PageUrl.TRANSFER_PRODUCT), PageCommand.CART, cmd);
		mav.addObject(ID, cmd.getSourceCardNumber());
		mav.addObject(CART, cmd);
		mav.addObject(CARD_ID, cardId);
		mav.addObject(PageAttribute.LOST_STOLEN_MODE, LOST_STOLEN_MODE);
		return mav;
	}
    
    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result, Model model) {
    	cmd.setCartType(getCartTypeCode());
    	ModelAndView modelAndView = new ModelAndView(getRefundType());
    	
    	CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
    	cmd.setCartDTO(cartDTO);
        goodwillValidator.validate(cmd, result);
    	setOysterCardsList(cmd, modelAndView, cartDTO);
        
        if (!result.hasErrors()) {
        	CartItemCmdImpl cartItemCmd = new CartItemCmdImpl(cmd.getCartItemCmd().getGoodwillPrice(),
                    cmd.getCartItemCmd().getGoodwillPaymentId(),
                            GOODWILL.code(), cartDTO.getCardId(), cmd.getCartItemCmd().getGoodwillOtherText());
            
            cartDTO = cartService.addItem(cartDTO, cartItemCmd, GoodwillPaymentItemDTO.class);
            cartDTO = cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
            
            cmd.setCartDTO(cartDTO);
            clearGoodwillAmountAndRefundReasonFromCommand(cmd);
        } 
            
        cmd.setStationId(cmd.getStationId());
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
    
    protected void clearGoodwillAmountAndRefundReasonFromCommand(CartCmdImpl cmd) {
        cmd.getCartItemCmd().setGoodwillPrice(null);
        cmd.getCartItemCmd().setGoodwillPaymentId(null);
    }
    
    protected void clearTravelcardDetailsFromCommand(CartItemCmdImpl cmd) {
        cmd.setTravelCardType(null);
        cmd.setRate(null);
        cmd.setStartDate(null);
        cmd.setEndDate(null);
        cmd.setStartZone(null);
        cmd.setEndZone(null);
        cmd.setPreviouslyExchanged(null);
        cmd.setTradedTicket(null);
    }
	
    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd,
                                       @RequestParam(value = LINE_NO) int lineNo, Model model) {
    	cmd.setCartType(getCartTypeCode());
    	ModelAndView modelAndView = new ModelAndView(getRefundType());
        
    	CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        setOysterCardsList(cmd, modelAndView, cartDTO);
        
        cartDTO = cartService.deleteItem(cartDTO, Long.valueOf(lineNo));
        cartDTO = cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartDTO);
        cmd.setCartDTO(cartDTO);
        cmd.setStationId(cmd.getStationId());
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
	
	@RequestMapping(params = TARGET_ACTION_REFUND, method = RequestMethod.POST)
    public ModelAndView refund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
		
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        updateCardNumberInCartCmdImpl(cmd, cartDTO);
        
        updateCountryDTOInCartCmdImpl(cmd);
        
    	ModelAndView modelAndView = new ModelAndView();
    	setOysterCardsList(cmd, modelAndView, cartDTO);
    	cartDTO.setCartType(getCartTypeCode());
        cmd.setCartType(getCartTypeCode());
        cmd.setCartDTO(cartDTO);
        updateTotalIntoCmd(cmd, cartDTO, getCartTypeCode());
        updatePreviousOysterBalanceForAdhocLoad(cmd);
        getValidator().validate(cmd, result);
        refundCartPaymentValidator.validate(cmd, result);
        refundPaymentValidator.validate(cartDTO, result);
        if (!result.hasErrors()) {
            if (PaymentType.AD_HOC_LOAD.code().equals(cmd.getPaymentType())) {
            	setStationNameInCartCmd(cmd);
                refundPaymentValidator.validate(cartDTO, result);
                if (result.hasErrors()) {
                    modelAndView.setViewName(getRefundType());
                    modelAndView.addObject(FAILED_VALIDATION, true);
                    refundService.updatePayAsYouGoValueInCartCmdImpl(cmd, cartDTO);
                	refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
                    modelAndView.addObject(CART, cmd);
                    modelAndView.addObject(CART_DTO, cartDTO);
                    return modelAndView;
                }
            }
        	cmd.setCartDTO(cartDTO);
            modelAndView = complete(modelAndView, cmd);
            modelAndView.addObject(CART_DTO, cartDTO);
        } else {
            modelAndView.setViewName(getRefundType());
            modelAndView.addObject(FAILED_VALIDATION, true);
        }
        refundService.updatePayAsYouGoValueInCartCmdImpl(cmd, cartDTO);
    	refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    } 
	
    @RequestMapping(params = TARGET_ACTION_ADD_TRADED_TICKET, method = RequestMethod.POST)
    public ModelAndView addTradedTicket(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result,
                    @RequestParam(value = CART_ITEM_ID) long cartItemId) {
        ModelAndView modelAndView;
        CartItemCmdImpl cartItemCmd = null;
        tradedTicketValidator.validate(cmd, result);
        for (CartItemCmdImpl cartItemCmdImpl : cmd.getCartItemList()) {
            if (null != cartItemCmdImpl.getPreviouslyExchanged() && cartItemCmdImpl.getPreviouslyExchanged()) {
                cartItemCmd = cartItemCmdImpl;
            }
        }
        cartItemCmd.setTicketType(TRAVEL_CARD.code());
        cartItemCmd.setCartType(getCartTypeCode());
        cartItemCmd.setRefundType(getCartTypeCode());
        String travelCardType = cartItemCmd.getTravelCardType();
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        if (!result.hasErrors()) {
            cartItemCmd.setCardId(cartDTO.getCardId());
            cartItemCmd.setTicketUnused(cmd.getCartItemCmd().getTicketUnused());
            cartItemCmd.setBackdated(cmd.getCartItemCmd().getBackdated());
            cartItemCmd.setBackdatedRefundReasonId(cmd.getCartItemCmd().getBackdatedRefundReasonId());
            cartItemCmd.setDeceasedCustomer(cmd.getCartItemCmd().getDeceasedCustomer());
            cartItemCmd.setDateOfRefund(cmd.getCartItemCmd().getDateOfRefund());
            cartItemCmd.setDateOfCanceAndSurrender(cmd.getCartItemCmd().getDateOfCanceAndSurrender());
            cartDTO = refundService.attachPreviouslyExchangedTicketToTheExistingProductItem(cartItemCmd, cartDTO, new Long(cartItemId));
            cmd.setCartDTO(cartDTO);
            cartDTO = refundService.updateDateOfRefund(cartDTO, cmd.getDateOfRefund());
            cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cartDTO.getCardId(), getCartTypeCode());
            refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
            modelAndView = new ModelAndView(getRefundType(), CART, cmd);
            setOysterCardsList(cmd, modelAndView, cartDTO);
        } else {
            cartItemCmd.setTravelCardType(travelCardType);
            modelAndView = new ModelAndView(getRefundType(), CART, cmd);
            setOysterCardsList(cmd, modelAndView, cartDTO);
        }
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
	
    protected ModelAndView complete(ModelAndView modelAndView, CartCmdImpl cmd) {
        if (cmd.getCartDTO().getApprovalId() != null) {
        	workflowService.getChangesAfterEditRefunds(cmd);
            return returnToWorkflow(cmd.getCartDTO().getApprovalId());
        } else {
        	cmd.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_INNOVATOR);
            workflowService.generateWorkflowEntity(cmd, workFlowGeneratorStrategy);
            modelAndView.setView(new RedirectView(PageUrl.INV_REFUND_SUMMARY));
            return modelAndView;
        }
    }

	protected void setStationNameInCartCmd(CartCmdImpl cmd) {
		if((cmd.getTargetPickupLocationId()== 0) || (cmd.getTargetPickupLocationId() == NumberUtil.convertLongToInt(cmd.getStationId()))){
			cmd.setStationName(locationDataService.getActiveLocationById(NumberUtil.convertLongToInt(cmd.getStationId())).getName());
		} else {
			CartDTO pickupLocationFlagedDTO = cmd.getCartDTO();
			pickupLocationFlagedDTO.setPpvPickupLocationAddFlag(true);
			pickupLocationFlagedDTO.setPpvPickupLocationName(locationDataService.getActiveLocationById(cmd.getTargetPickupLocationId()).getName());
			cmd.setCartDTO(pickupLocationFlagedDTO);
		}
	}
	
    protected void updatePreviousWebCredit(CartCmdImpl cmd) {
    	CardDTO card = cardDataService.findByCardNumber(cmd.getCardNumber());
    	cmd.setWebCreditAvailableAmount(webCreditSettlementDataService.getBalance(card.getCustomerId()));
	}
    
    protected void updatePreviousOysterBalanceForAdhocLoad(CartCmdImpl cmd) {
        if (PaymentType.AD_HOC_LOAD.code().equals(cmd.getPaymentType()) && StringUtils.isNotBlank(cmd.getTargetCardNumber())) {
            CardDTO targetCard = cardDataService.findByCardNumber(cmd.getTargetCardNumber());
            final CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(targetCard.getCardNumber().toString());
            List<PrePayValue> ppvs = cardInfoResponseV2DTO.getPendingItems().getPpvs();
            final Integer currentValueOnTargetCard = cardInfoResponseV2DTO.getPpvDetails() != null ? cardInfoResponseV2DTO.getPpvDetails()
                            .getBalance() : null;
            int sumPrePayValue = currentValueOnTargetCard != null ? currentValueOnTargetCard : 0;
            for (PrePayValue ppv : ppvs) {
                sumPrePayValue += ppv.getPrePayValue();
                if (null != ppv.getPickupLocation()) {
                    cmd.setTargetPickupLocationId(ppv.getPickupLocation().intValue());
                }
            }
            cmd.setPreviousCreditAmountOnCard(sumPrePayValue);
        }
    }
    
    public void updateTotalIntoCmd(CartCmdImpl cmd, CartDTO cart, String cartType) {
        if (CartUtil.isDestroyedOrFaildCartType(cartType)) {
            cmd.setToPayAmount(cart.getCartRefundTotal() + cart.getCardRefundableDepositAmount());
        } else {
            cmd.setToPayAmount(cart.getCartRefundTotal());
        }
    }  
    
    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelRefund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        if (cmd.getApprovalId() != null) {
            return returnToWorkflow(cmd.getApprovalId());
        } else {
        	CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
            PersonalDetailsCmdImpl personalDetailsByCustomerId = addUnattachedCardService.retrieveOysterDetails(cmd.getCardNumber());
            ModelAndView redirectView = new ModelAndView(new RedirectView(PageUrl.INV_CUSTOMER));
            redirectView.addObject(ID, personalDetailsByCustomerId.getCustomerId());
            setOysterCardsList(cmd, redirectView, cartDTO);
            return redirectView;
        }
    }

    @RequestMapping(value = "/getPreferredStation", method = RequestMethod.POST)
    @ResponseBody
    public String getPreferredStationId(@RequestParam(value = CARD_NUMBER) String cardNumber) {
        Long preferredStationId = cardPreferencesService.getPreferencesByCardNumber(cardNumber).getStationId();
        return (preferredStationId == null ? null : preferredStationId.toString());
    }

    protected ModelAndView returnToWorkflow(Long approvalId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(PageUrl.VIEW_WORKFLOW_ITEM));
        modelAndView.addObject(CASE_NUMBER, approvalId);
        modelAndView.addObject(EDIT, Boolean.TRUE);
        return modelAndView;
    }
    
    protected void updateCountryDTOInCartCmdImpl(CartCmdImpl cmd) {
        if (cmd.getPayeeAddress() != null && cmd.getPayeeAddress().getCountryCode() != null) {
            cmd.getPayeeAddress().setCountry(
                            countryDataService.findCountryByCode(cmd.getPayeeAddress().getCountryCode()));
        }
    }
    
    protected void createCartSessionData(HttpSession session, CartCmdImpl cmd) {
        CartSessionData cartSessionData = CartUtil.getCartSessionDataDTOFromSession(session);
        if (null == cartSessionData) {
            cartSessionData = new CartSessionData(cmd.getCartId());
            cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
            cartSessionData.setTransferProductMode(Boolean.TRUE);
            cartSessionData.setCartId(cmd.getCartId());
            cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
            cartSessionData.setLostOrStolenMode(cmd.isLostOrStolenMode());
            CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
        } else {
        	cartSessionData.setPageName(Page.TRANSFER_PRODUCT);
            cartSessionData.setSourceCardNumber(cmd.getSourceCardNumber());
            cartSessionData.setLostOrStolenMode(cmd.isLostOrStolenMode());
            CartUtil.addCartSessionDataDTOToSession(session, cartSessionData);
            cmd.setCardId(cardService.getCardIdFromCardNumber(cartSessionData.getTargetCardNumber()));
        }
    }
}