package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CART_ITEM_ID;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.LINE_NO;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.VALUE;
import static com.novacroft.nemo.tfl.common.constant.CartType.CANCEL_SURRENDER_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.CART_DTO;
import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRADED_TICKET;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_ADD_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_GOODWILL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_DELETE_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_UPDATE_CART_TOTAL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_UPDATE_REFUND_BASIS_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_VIEW_CART_USING_CUBIC;
import static com.novacroft.nemo.tfl.common.constant.PageUrl.INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_CANCEL_AND_SURRENDER_CARD;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.CancelAndSurrenderService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.form_validator.CancelAndSurrenderCardRefundCartValidator;
import com.novacroft.nemo.tfl.common.form_validator.CancelAndSurrenderValidator;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;

@Controller
@RequestMapping(value = Page.INV_CANCEL_AND_SURRENDER_CARD)
@SessionAttributes(CART)
public class CancelAndSurrenderCardController extends RefundController {
    static final Logger logger = LoggerFactory.getLogger(CancelAndSurrenderCardController.class);

    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CancelAndSurrenderService cancelAndSurrenderService;
    @Autowired
    protected CancelAndSurrenderValidator cancelandSurrenderValidator;
    @Autowired
    protected CancelAndSurrenderCardRefundCartValidator cancelAndSurrenderCartValidator;
    @Autowired
    protected ProductDataService productDataService;
    @Autowired
    protected TravelCardService travelCardService;

    @Override
    protected String getRefundType() {
        return INV_CANCEL_AND_SURRENDER_CARD;
    }

    @Override
    protected String getCartTypeCode() {
        return CartType.CANCEL_SURRENDER_REFUND.code();
    }

    @Override
    protected BaseValidator getValidator() {
        return cancelAndSurrenderCartValidator;
    }

    @Override
    @InitBinder
    protected final void initBinder(final ServletRequestDataBinder binder) {
        super.initBinder(binder);
    }

    @Override
    @ModelAttribute
    public void populateModelAttributes(Model model) {
        super.populateModelAttributes(model);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewCart(HttpSession session, @RequestParam("id") String cardNumber, @RequestParam(PageParameter.APPROVAL_ID) Long approvalId) {
        return super.viewCart(session, cardNumber, approvalId);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_VIEW_CART_USING_CUBIC, method = RequestMethod.GET)
    public ModelAndView viewCartUsingCubic(HttpSession session, @RequestParam("id") String cardNumber) {
        return super.viewCartUsingCubic(session, cardNumber);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_UPDATE_CART_TOTAL, method = RequestMethod.POST)
    public ModelAndView updateCartTotal(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        cmd.getCartItemCmd().setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        return super.updateCartTotal(session, cmd, result);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_ADD_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView addTravelCard(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(INV_CANCEL_AND_SURRENDER_CARD);
        CartItemCmdImpl cartItem = cmd.getCartItemCmd();
        String travelCardType = null;
        if (cartItem != null) {
            travelCardType = cartItem.getTravelCardType();
            cartItem.setCartType(CANCEL_SURRENDER_REFUND.code());
        }
        addUnlistedProductService.setTicketType(cartItem);
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cartItem);        

        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        cmd.setCartDTO(cartDTO);
        cancelandSurrenderValidator.validate(cmd, result);

        if (!result.hasErrors()) {
            cartDTO = refundService.addTravelCardToCart(cartDTO, cmd, CANCEL_SURRENDER_REFUND.code());
            cmd.setCartDTO(cartDTO);
            if(cartItem != null && cartItem.getDeceasedCustomer()){
                cartDTO = refundService.updateDateOfRefund(cartDTO, cartItem.getDateOfLastUsage());
            } else if (cartItem != null && cartItem.getBackdated()){
                cartDTO = refundService.updateDateOfRefund(cartDTO, cartItem.getDateOfCanceAndSurrender());
            } else if (cartItem != null && cartItem.getTicketUnused()){
                cartDTO = refundService.updateDateOfRefund(cartDTO, DateUtil.formatStringAsDate(cartItem.getStartDate()));
            } else {
                cartDTO = refundService.updateDateOfRefund(cartDTO, cmd.getDateOfRefund());
            }
            cartAdministrationService.addOrRemoveAdministrationFeeToCart(cartDTO, cartDTO.getCardId(), getCartTypeCode());
            refundService.updateAdministrationFeeValueInCartCmdImpl(cmd, cartDTO);
            clearTravelcardDetailsFromCommand(cartItem);
        } else {
            cmd.getCartItemCmd().setTravelCardType(travelCardType);
        }
        cmd.setStationId(cmd.getStationId());
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        setOysterCardsList(cmd, modelAndView, cartDTO);
        return modelAndView;
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_DELETE_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView deleteTravelCard(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd,
                    @RequestParam(value = CART_ITEM_ID) int cartItemId) {
        return super.deleteTravelCard(session, cmd, cartItemId);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_REFUND, method = RequestMethod.POST)
    public ModelAndView refund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView(INV_CANCEL_AND_SURRENDER_CARD);
        CartDTO cartDTO = getCartDTOUsingCartSessionDataDTOInSession(session);
        setOysterCardsList(cmd, modelAndView, cartDTO);
        cmd.setCartType(CANCEL_SURRENDER_REFUND.code());
        cmd.setCartDTO(cartDTO);
        cartDTO.setCartType(CANCEL_SURRENDER_REFUND.code());
        updateTotalIntoCmd(cmd, cartDTO,cmd.getCartType());
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(cmd.getCartItemCmd());
        cancelAndSurrenderCartValidator.validate(cmd, result);
        refundCartPaymentValidator.validate(cmd, result);
        refundPaymentValidator.validate(cartDTO, result);

        if (isCartIdNotNull(cmd) && isTicketTypeNotNull(cmd)) {
            cartDTO = travelCardService.addUpdateItems(cmd.getCartDTO().getId(), cmd.getCartItemCmd());
        }
        
        if (!result.hasErrors()) {
            if (PaymentType.AD_HOC_LOAD.code().equals(cmd.getPaymentType())) {
                updatePreviousOysterBalanceForAdhocLoad(cmd);
                setStationNameInCartCmd(cmd);
                refundPaymentValidator.validate(cartDTO, result);
                if (result.hasErrors()) {
                    modelAndView.addObject(CART, cmd);
                    modelAndView.addObject(CART_DTO, cartDTO);
                    return modelAndView;
                }
            }
            if (cmd.getApprovalId() != null) {
                modelAndView = new ModelAndView();
                modelAndView.setView(new RedirectView(PageUrl.VIEW_WORKFLOW_ITEM));
                modelAndView.addObject(CASE_NUMBER, cmd.getApprovalId());
                modelAndView.addObject(EDIT, Boolean.TRUE);
            } else {
                cmd.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_INNOVATOR);
                setDateOfRefundForDeceasedCustomerAndBackdatedRefunds(cmd);
                workflowService.generateWorkflowEntity(cmd, workFlowGeneratorStrategy);
                modelAndView.setView(new RedirectView(INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY));
            }
            return modelAndView;
        }
        modelAndView.addObject(CART, cmd);
        modelAndView.addObject(CART_DTO, cartDTO);
        return modelAndView;
    }
     
    protected void setDateOfRefundForDeceasedCustomerAndBackdatedRefunds(CartCmdImpl cartCmdImpl) {
        CartItemCmdImpl cartItemCmd = cartCmdImpl.getCartItemCmd();
        if (cartItemCmd != null && cartItemCmd.getDeceasedCustomer()) {
            cartCmdImpl.setDateOfRefund(cartItemCmd.getDateOfLastUsage());
        } else if (cartItemCmd != null && cartItemCmd.getBackdated()) {
            cartCmdImpl.setDateOfRefund(cartItemCmd.getDateOfCanceAndSurrender());
        }
    }
    
    @RequestMapping(params = TARGET_ACTION_UPDATE_REFUND_BASIS_TRAVEL_CARD, method = RequestMethod.POST)
    public ModelAndView updateRefundBasis(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd, @RequestParam(value = LINE_NO) int lineNo,
                    @RequestParam(value = VALUE) String value) {
        return super.updateRefundCalculationBasis(session, cmd, lineNo, value);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_ADD_GOODWILL, method = RequestMethod.POST)
    public ModelAndView addGoodwill(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result, Model model) {
        return super.addGoodwill(session, cmd, result, model);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_DELETE_GOODWILL, method = RequestMethod.POST)
    public ModelAndView deleteGoodwill(HttpSession session, @ModelAttribute(CART) CartCmdImpl cmd,
                    @RequestParam(value = CART_ITEM_ID) int cartItemId, Model model) {
        return super.deleteGoodwill(session, cmd, cartItemId, model);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelRefund(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result) {
        return super.cancelRefund(session, cmd, result);
    }

    @Override
    @RequestMapping(params = TARGET_ACTION_ADD_TRADED_TICKET, method = RequestMethod.POST)
    public ModelAndView addTradedTicket(HttpSession session, @ModelAttribute(PageCommand.CART) CartCmdImpl cmd, BindingResult result,
                    @RequestParam(value = CART_ITEM_ID) long cartItemId) {
        return super.addTradedTicket(session, cmd, result, cartItemId);
    }
    
    protected Boolean isCartIdNotNull(CartCmdImpl cmd) {
        return cmd.getCartDTO() != null && cmd.getCartDTO().getId() != null;
    }
    
    protected Boolean isTicketTypeNotNull(CartCmdImpl cmd) {
        return cmd.getCartItemCmd() != null && cmd.getCartItemCmd().getTicketType() != null;
    }
}
