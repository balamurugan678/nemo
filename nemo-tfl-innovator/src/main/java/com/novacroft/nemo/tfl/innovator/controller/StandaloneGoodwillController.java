package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CART_ITEM_ID;
import static com.novacroft.nemo.tfl.common.constant.CartType.STANDALONE_GOODWILL_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.FIELD_SELECT_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.ID;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CONTINUE;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_STANDALONE_GOODWILL;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

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

import com.google.gson.Gson;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.ManageGoodwillService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.GoodwillValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartPaymentValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;

@Controller
@RequestMapping(value = Page.INV_STANDALONE_GOODWILL)
@SessionAttributes({ CART, ID })
public class StandaloneGoodwillController extends BaseController {

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected ManageGoodwillService manageGoodwillService;
    @Autowired
    protected RefundSelectListService refundSelectListService;
    @Autowired
    protected RefundPaymentService refundPaymentService;
    @Autowired
    protected RefundService refundService;
    @Autowired
	protected WorkFlowService workflowService;
    @Autowired
   	protected CardSelectListService cardSelectListService;
    @Autowired
    protected CartService cartService;
    @Autowired
    @Qualifier(value="BaseWorkFlowGeneratorStrategy")
	protected WorkFlowGeneratorStrategy workFlowGeneratorStrategy;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CardPreferencesService cardPreferencesService;
    @Autowired
    protected RefundCartPaymentValidator refundCartPaymentValidator;
    @Autowired
	protected GoodwillValidator goodwillValidator;
    
    @InitBinder
    protected void initBinder(final ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = DateUtil.createShortDateFormatter();
        dateFormat.setLenient(false);
        final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    @ModelAttribute
    public void populateGoodwillRefundTypes(Model model) {
        refundSelectListService.getStandaloneGoodwillSelectListModel(model);
      
    }

    @RequestMapping(method = RequestMethod.GET, params ={PageParameter.APPROVAL_ID})
    public ModelAndView viewStandaloneGoodwillForApproval(@RequestParam(ID)  Long customerId, @RequestParam(PageParameter.APPROVAL_ID) Long approvalId,  HttpSession session) {
    	CartDTO cartDTO = cartService.findByApprovalId(approvalId);
        CartCmdImpl cartCmdImpl = new CartCmdImpl();
        cartCmdImpl.setCartDTO(cartDTO);
        cartCmdImpl.setCartId(cartDTO.getId());
        ModelAndView modelAndView = new ModelAndView(INV_STANDALONE_GOODWILL, CART, cartCmdImpl);
        modelAndView.addObject(ID, customerId);
        refundService.storeCartIdInCartSessionDataDTOInSession(session, cartCmdImpl.getCartDTO().getId());
        refundService.updateDateOfRefundInCartCmdImpl(cartCmdImpl,cartCmdImpl.getCartDTO());
		refundService.updatePaymentDetailsInCartCmdImpl(cartCmdImpl, cartCmdImpl.getCartDTO());
		modelAndView.addObject(FIELD_SELECT_CARD, cardSelectListService.getCardsSelectListForCustomer(customerId));
		modelAndView.addObject(CART_DTO, cartCmdImpl.getCartDTO());
        return modelAndView;
    }
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewStandaloneGoodwill(@RequestParam(ID)  Long customerId,  HttpSession session) {
        CartCmdImpl cartCmdImpl = refundService.createCartCmdImplWithCartDTOFromCustomerId(customerId, STANDALONE_GOODWILL_REFUND.code());
        ModelAndView modelAndView = new ModelAndView(INV_STANDALONE_GOODWILL, CART, cartCmdImpl);
        modelAndView.addObject(ID, customerId);
        refundService.storeCartIdInCartSessionDataDTOInSession(session, cartCmdImpl.getCartDTO().getId());
        modelAndView.addObject(FIELD_SELECT_CARD, cardSelectListService.getCardsSelectListForCustomer(customerId));
        return modelAndView;
    }
    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(@ModelAttribute(ID) Long customerId , @ModelAttribute(PageCommand.CART) CartCmdImpl cartCmdImpl, BindingResult result, Model model, HttpSession session) {
    	CartDTO cartDTO = refundService.getCartDTOUsingCartSessionDataDTOInSession(session);
    	cartCmdImpl.setCartDTO(cartDTO);
    	goodwillValidator.validate(cartCmdImpl, result);
    	if (!result.hasErrors()) {
    		cartCmdImpl.setCartId(CartUtil.getCartSessionDataDTOFromSession(session).getCartId());
            cartCmdImpl = manageGoodwillService.addGoodwill(cartCmdImpl, result, STANDALONE_GOODWILL_REFUND.code(), model);
            setUpCartDataInModel(cartCmdImpl, model, session);
    	}
    	model.addAttribute(FIELD_SELECT_CARD, cardSelectListService.getCardsSelectListForCustomer(customerId));
    	return new ModelAndView(INV_STANDALONE_GOODWILL, CART, cartCmdImpl);
    }
    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(@ModelAttribute(ID) Long customerId , @ModelAttribute(CART) CartCmdImpl cartCmdImpl, @RequestParam(value = CART_ITEM_ID) int cartItemid, Model model, HttpSession session) {
    	model.addAttribute(FIELD_SELECT_CARD, cardSelectListService.getCardsSelectListForCustomer(customerId));
        cartCmdImpl.setCartId(CartUtil.getCartSessionDataDTOFromSession(session).getCartId());
        cartCmdImpl = manageGoodwillService.deleteGoodwill(cartCmdImpl, cartItemid, STANDALONE_GOODWILL_REFUND.code(), model);
        setUpCartDataInModel(cartCmdImpl, model, session);
        cartCmdImpl.setCartDTO(refundService.getCartDTOUsingCartSessionDataDTOInSession(session));
        return new ModelAndView(INV_STANDALONE_GOODWILL, CART, cartCmdImpl);
    }

    @RequestMapping(params = TARGET_ACTION_CONTINUE, method = RequestMethod.POST)
    public ModelAndView continueCart(@ModelAttribute(ID) Long customerId, HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
    	cmd.setCartDTO(refundService.getCartDTOUsingCartSessionDataDTOInSession(session));
    	cmd.setCartId(cmd.getCartDTO().getId());
    	cmd.setCartType(CartType.STANDALONE_GOODWILL_REFUND.code());
    	updateCardNumbersAndDate(cmd);
    	refundCartPaymentValidator.validate(cmd, result);
    	updateTotalIntoCmd(cmd, cmd.getCartDTO());
    	if(result.hasErrors()){
    		ModelAndView modelAndView = new  ModelAndView(INV_STANDALONE_GOODWILL, PageCommand.CART, cmd);
    		modelAndView.addObject(FIELD_SELECT_CARD, cardSelectListService.getCardsSelectListForCustomer(customerId));
    		return modelAndView;
    	}
    	if (cmd.getCartDTO().getApprovalId() != null) {
    		cmd.setApprovalId(cmd.getCartDTO().getApprovalId());
	    	workflowService.getChangesAfterEditRefunds(cmd);
	        return returnToWorkflow(cmd.getCartDTO().getApprovalId());
	    } else {
	    	ModelAndView modelAndView = new ModelAndView();
	    	cmd.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_INNOVATOR);
	        workflowService.generateWorkflowEntity(cmd, workFlowGeneratorStrategy);
            modelAndView.setView(new RedirectView(PageUrl.INV_REFUND_SUMMARY));
            modelAndView.addObject(CART, cmd);
            modelAndView.addObject(CART_DTO, cmd.getCartDTO());
	        modelAndView.addObject(ID, cmd.getCartDTO().getCustomerId());
	        return modelAndView;
	    }
    }
    
    protected ModelAndView returnToWorkflow(Long approvalId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(PageUrl.VIEW_WORKFLOW_ITEM));
        modelAndView.addObject(CASE_NUMBER, approvalId);
        modelAndView.addObject(EDIT, Boolean.TRUE);
        return modelAndView;
    }
    
    @RequestMapping(value = "/getPreferredStation", method = RequestMethod.POST)
    @ResponseBody
    public String getPreferredStationId(@RequestParam(value = CARD_NUMBER, required = false) Long cardId) {
    	Long preferredStationId  = cardPreferencesService.getPreferencesByCardId(cardId).getStationId();
        return (preferredStationId == null ? null : preferredStationId.toString());
    }
    
    private void updateCardNumbersAndDate(CartCmdImpl cmd){
    	if(cmd.getDateOfRefund() == null){
    		cmd.setDateOfRefund(new Date());
    	}
    	cmd.getCartDTO().setDateOfRefund(cmd.getDateOfRefund());
    	final String cardId = cmd.getTargetCardNumber();
    	if(StringUtil.isNotBlank(cardId)){
    		final CardDTO cardDTO = cardService.getCardDTOById(new Long(cardId));
    		cmd.setTargetCardNumber(cardDTO.getCardNumber());
    		cmd.setCardNumber(cardDTO.getCardNumber());
    	}

    }
    
    private  void updateTotalIntoCmd(CartCmdImpl cmd, CartDTO cart) {
    	cmd.setToPayAmount(cart.getCartRefundTotal());
    }
    
    private void setUpCartDataInModel(CartCmdImpl cartCmdImpl, Model model,
			HttpSession session) {
		final CartDTO cartDTO = refundService.getCartDTOUsingCartSessionDataDTOInSession(session);
        model.addAttribute(CART_DTO, cartDTO);
        cartCmdImpl.setCartDTO(cartDTO);
	}
   
    @RequestMapping(value = "/getCardSpecificStationId", method = RequestMethod.POST)
    @ResponseBody
    public String getCardSpecificStationId(@RequestParam(value = CARD_NUMBER, required = false) Long cardId) {
    	return new Gson().toJson(cardPreferencesService.getPreferencesByCardId(cardId).getStationId()); 
    }
}
