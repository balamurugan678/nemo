package com.novacroft.nemo.tfl.innovator.controller.workflow;

import static com.novacroft.nemo.tfl.common.constant.PageAttribute.COUNTRIES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUNDS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUNDS_SCENARIO_TYPES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REJECTION_REASONS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.TITLES;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.APPROVAL_ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.CASE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameter.ID;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_APPROVE;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CANCEL;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_CLOSE;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_EDIT;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_EDIT_REFUND;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_REJECT;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_SAVE_CHANGES;
import static com.novacroft.nemo.tfl.common.constant.PageParameterArgument.TARGET_ACTION_SAVE_COMMENT;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.ApprovalsRejectionValidator;
import com.novacroft.nemo.tfl.common.form_validator.WorkflowItemEditValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.controller.FailedCardRefundCartController;
import com.novacroft.nemo.tfl.innovator.controller.LostRefundController;
import com.novacroft.nemo.tfl.innovator.controller.StolenRefundController;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Controller
@RequestMapping(value = Page.VIEW_WORKFLOW_ITEM)
public class ViewWorkflowItemController {

	@Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected CountrySelectListService countrySelectListService;
    @Autowired
    protected WorkflowItemConverter converter;
    @Autowired
    protected ApprovalsRejectionValidator rejectionValidator;
    @Autowired
    protected WorkflowItemEditValidator editValidator;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected FailedCardRefundCartController failedCardRefundController;
    @Autowired
    protected LostRefundController lostRefundController;
    @Autowired
    protected StolenRefundController stolenRefundController;
    @Autowired
    protected RefundService refundService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;
    @Autowired
    protected EditRefundPaymentService editRefundPaymentService;
    
    @ModelAttribute
    public void populateCountrySelectList(Model model) {
        model.addAttribute(COUNTRIES, countrySelectListService.getSelectList());
    }
    
    @ModelAttribute
    public void populateTitlesSelectList(Model model) {
        model.addAttribute(TITLES, selectListService.getSelectList(PageSelectList.TITLES));
    }

    @ModelAttribute
    public void populateRefundsSelectList(Model model) {
        model.addAttribute(REFUNDS, selectListService.getSelectList(PageSelectList.REFUNDS));
    }

    @ModelAttribute
    public void populateRejectionReasonSelectList(Model model) {
        model.addAttribute(REJECTION_REASONS, selectListService.getSelectList(PageSelectList.REJECTION_REASONS));
    }

    @ModelAttribute
    public void populateRefundsScenarioSelectList(Model model) {
        model.addAttribute(REFUNDS_SCENARIO_TYPES, selectListService.getSelectList(PageSelectList.REFUNDS_SCENARIO_TYPES));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewWorkFlowItem(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber,
                    @RequestParam(value = EDIT, required = false) Boolean edit, HttpServletRequest request) {
        WorkflowCmd workflowCmd;
        CartDTO cartDTO;
        WorkflowEditCmd workflowEditCmd;
        if (edit != null && edit) {
            workflowCmd = workflowService.createWorkflowBean(caseNumber, edit);
            WorkflowItemDTO workflowItem = workflowCmd.getWorkflowItem();
            cartDTO = cartService.findByApprovalId(new Long(workflowCmd.getWorkflowItem().getCaseNumber()));
            validateRefundScenarioSubTypeForWorkFlowDisplay(workflowCmd);
            workflowEditCmd = new WorkflowEditCmd(workflowCmd, cartDTO);
            workflowEditCmd = setEditedValuesInApproval(workflowCmd, cartDTO, workflowEditCmd, workflowItem);
        } else {
            workflowCmd = workflowService.createWorkflowBean(caseNumber, Boolean.FALSE);
            validateRefundScenarioSubTypeForWorkFlowDisplay(workflowCmd);
            workflowEditCmd = new WorkflowEditCmd(workflowCmd);
        }
        workflowEditCmd = updateChangesAfterEditRefunds(workflowCmd.getWorkflowItem(), workflowEditCmd);
        ModelAndView modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM, PageCommand.WORKFLOWITEM_COMMAND, workflowCmd);
        modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, new WorkflowRejectCmd());
        modelAndView.addObject(PageCommand.WORKFLOW_EDIT_COMMAND, workflowEditCmd);
        return modelAndView;
    }

    protected WorkflowEditCmd setEditedValuesInApproval(WorkflowCmd workflowCmd, CartDTO cartDTO, WorkflowEditCmd workflowEditCmd,
                    WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        workflowEditCmd = setCartDTORefundItemValues(workflowEditCmd, cartDTO);
        workflowEditCmd = setRefundScenarioDetails(workflowCmd, workflowEditCmd);
        refundDetails.setTotalRefundAmount(getCartRefundTotal(cartDTO).longValue());
        workflowItem.setRefundDetails(refundDetails);
        workflowCmd.setTotalRefund(getCartRefundTotal(cartDTO).longValue());
        workflowEditCmd = setPaymentDetails(workflowCmd, workflowEditCmd, cartDTO);
        return workflowEditCmd;
    }

    protected WorkflowEditCmd setRefundScenarioDetails(WorkflowCmd workflowCmd, WorkflowEditCmd workflowEditCmd) {
        workflowEditCmd.setRefundScenarioType(workflowCmd.getRefundScenarioType());
        workflowEditCmd.setRefundScenarioSubType(workflowCmd.getRefundScenarioSubType());
        workflowEditCmd.setRefundablePeriodStartDate(workflowCmd.getRefundablePeriodStartDate());
        return workflowEditCmd;

    }

    protected Integer getCartRefundTotal(CartDTO cartDTO) {
        return cartDTO.getCartRefundTotal();
    }

    protected WorkflowEditCmd setCartDTORefundItemValues(WorkflowEditCmd workflowEditCmd, CartDTO cartDTO) {

        List<ItemDTO> cartDTOItems = cartDTO.getCartItems();
        List<RefundItemCmd> refundItemList = new ArrayList<RefundItemCmd>();
        List<GoodwillPaymentItemCmd> goodWillPaymentItemsList = new ArrayList<GoodwillPaymentItemCmd>();
        updateCartDTOItems(workflowEditCmd, cartDTOItems, refundItemList, goodWillPaymentItemsList);

        workflowEditCmd.setRefundItems(refundItemList);
        workflowEditCmd.setGoodwillPaymentItems(goodWillPaymentItemsList);
        workflowEditCmd.setTotalTicketAmount(getTotalTicketAmount(cartDTOItems));
        return workflowEditCmd;

    }

    public void updateCartDTOItems(WorkflowEditCmd workflowEditCmd, List<ItemDTO> cartDTOItems, List<RefundItemCmd> refundItemList,
                    List<GoodwillPaymentItemCmd> goodWillPaymentItemsList) {
        for (ItemDTO itemDto : cartDTOItems) {
            if (workflowService.isTravelCardOrBusPass(itemDto)) {
                RefundItemCmd refundItem = new RefundItemCmd((ProductItemDTO) itemDto);
                refundItemList.add(refundItem);
            } else if (workflowService.isItemDtoTypeOfAdministrationFeeItemDto(itemDto)) {
                workflowEditCmd.setTicketAdminFee(itemDto.getPrice());
            } else if (workflowService.isItemDtoTypeOfGoodwillPaymentDTO(itemDto)) {
                GoodwillPaymentItemCmd goodwillPaymentItem = new GoodwillPaymentItemCmd((GoodwillPaymentItemDTO) itemDto);
                goodWillPaymentItemsList.add(goodwillPaymentItem);
            } else if (workflowService.isItemDtoTypeOfPayAsYouGoDTO(itemDto)) {
                workflowEditCmd.setPayAsYouGoCredit(itemDto.getPrice());
            }
        }
    }

    protected Integer getTotalTicketAmount(List<ItemDTO> cartDTOItems) {
        return refundService.getTotalTicketPrice(cartDTOItems);
    }

    protected List<ItemDTO> getCartItemsDtoList(CartDTO cartDTO) {

        CartDTO cartDto = workflowService.getCartDtoFromCardId(cartDTO.getCardId());
        return cartDto.getCartItems();
    }

    protected void validateRefundScenarioSubTypeForWorkFlowDisplay(WorkflowCmd workflowCmd) {
        WorkflowItemDTO workflowItem = workflowCmd.getWorkflowItem();
        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        RefundScenarioEnum refundScenarioEnum = refundDetails.getRefundScenario();
        switch (refundScenarioEnum) {
        case FAILEDCARD:
            workflowCmd.setRefundScenarioSubType(RefundConstants.FAILED_CARD_REFUND_DISPLAY);
            break;
        case DESTROYED:
            workflowCmd.setRefundScenarioSubType(RefundConstants.DESTROYED_CARD_REFUND_DISPLAY);
            break;
        case LOST:
            workflowCmd.setRefundScenarioSubType(RefundConstants.LOST_REFUND_DISPLAY);
            break;
        case STOLEN:
            workflowCmd.setRefundScenarioSubType(RefundConstants.STOLEN_REFUND_DISPLAY);
            break;
        case CANCEL_AND_SURRENDER:
            workflowCmd.setRefundScenarioSubType(RefundConstants.CANCEL_AND_SURRENDER_REFUND_DISPLAY);
            break;
        case ANONYMOUS_GOODWILL_REFUND:
            workflowCmd.setRefundScenarioSubType(RefundConstants.ANONYMOUS_GOODWILL_REFUND_DISPLAY);
            break;
        case STANDALONE_GOODWILL_REFUND:
            workflowCmd.setRefundScenarioSubType(RefundConstants.STANDALONE_GOODWILL_REFUND_DISPLAY);
            break;
        default:
            workflowCmd.setRefundScenarioSubType(StringUtil.EMPTY_STRING);
        }
    }

    protected String getCartTypeCode(RefundScenarioEnum refundScenarioEnum) {
        switch (refundScenarioEnum) {
        case FAILEDCARD:
            return CartType.FAILED_CARD_REFUND.code();
        case DESTROYED:
            return CartType.DESTROYED_CARD_REFUND.code();
        case LOST:
            return CartType.LOST_REFUND.code();
        case STOLEN:
            return CartType.STOLEN_REFUND.code();
        case CANCEL_AND_SURRENDER:
            return CartType.CANCEL_SURRENDER_REFUND.code();
        case ANONYMOUS_GOODWILL_REFUND:
            return CartType.ANONYMOUS_GOODWILL_REFUND.code();
        case STANDALONE_GOODWILL_REFUND:
            return CartType.STANDALONE_GOODWILL_REFUND.code();
        default:
            return null;
        }
    }

    @RequestMapping(params = TARGET_ACTION_APPROVE, method = RequestMethod.POST)
    public ModelAndView approve(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber) {

        AgentGroup group = workflowService.approve(caseNumber);
        ApprovalListCmdImpl approvalListCmd = new ApprovalListCmdImpl();
        approvalListCmd.setFormLocation(group.listCode());
        return new ModelAndView(new RedirectView(group.listCode() + ".htm"));
    }

    @RequestMapping(params = TARGET_ACTION_REJECT, method = RequestMethod.POST)
    public ModelAndView reject(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber,
                    @ModelAttribute(PageCommand.WORKFLOW_REJECT_COMMAND) WorkflowRejectCmd cmd, BindingResult result) {
        AgentGroup group = null;
        ModelAndView modelAndView =null;
        rejectionValidator.validate(cmd, result);
        if (!result.hasErrors()) {
            group = workflowService.reject(caseNumber, cmd);
            cmd.setRejectFreeText(null);
            cmd.setRejectReason(null);
            ApprovalListCmdImpl approvalListCmd = new ApprovalListCmdImpl();
            approvalListCmd.setFormLocation(group.listCode());
            modelAndView = new ModelAndView(new RedirectView(group.listCode() + ".htm"));
            modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, cmd);
        } else{
            modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM, PageCommand.WORKFLOW_REJECT_COMMAND, cmd);
            modelAndView.addObject(PageCommand.WORKFLOWITEM_COMMAND, workflowService.createWorkflowBean(caseNumber, Boolean.FALSE));
            return modelAndView;
        }
         return modelAndView;
    }

    protected WorkflowEditCmd setPaymentDetails(WorkflowCmd workflowCmd, WorkflowEditCmd workflowEditCmd, CartDTO cartDTO) {

        workflowEditCmd.setPaymentName(getFullNameOfCustomer(workflowCmd.getWorkflowItem().getRefundDetails()));
        workflowEditCmd.setPaymentMethod(workflowCmd.getPaymentMethod());
        workflowEditCmd.setPaymentType(PaymentType.lookUpPaymentType(workflowCmd.getPaymentMethod()));
        if(PaymentType.BACS.code().equalsIgnoreCase(workflowCmd.getPaymentMethod()) 
        		|| PaymentType.CHEQUE.code().equalsIgnoreCase(workflowCmd.getPaymentMethod())){
        	workflowEditCmd.setPayeeAddress(workflowCmd.getWorkflowItem().getRefundDetails().getAddress());
        	workflowEditCmd.setPaymentHouseNameNumber(workflowCmd.getWorkflowItem().getRefundDetails().getAddress().getHouseNameNumber());
	        workflowEditCmd.setPaymentStreet(workflowCmd.getWorkflowItem().getRefundDetails().getAddress().getStreet());
	        workflowEditCmd.setPaymentTown(workflowCmd.getWorkflowItem().getRefundDetails().getAddress().getTown());
	        workflowEditCmd.setPaymentPostCode(workflowCmd.getWorkflowItem().getRefundDetails().getAddress().getPostcode());
	        workflowEditCmd.setPaymentCountry(workflowCmd.getWorkflowItem().getRefundDetails().getAddress().getCountry());
	    }
        workflowEditCmd.setAmount(getCartRefundTotal(cartDTO));
        return workflowEditCmd;

    }

    @RequestMapping(params = TARGET_ACTION_EDIT, method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber) {
        WorkflowCmd workflowCmd = workflowService.createWorkflowBean(caseNumber, Boolean.TRUE);
        ModelAndView modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM, PageCommand.WORKFLOWITEM_COMMAND, workflowCmd);
        modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, new WorkflowRejectCmd());
        modelAndView.addObject(PageCommand.WORKFLOW_EDIT_COMMAND, new WorkflowEditCmd(workflowCmd));
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_EDIT_REFUND, method = RequestMethod.POST)
    public RedirectView editRefund(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber) {

        WorkflowItemDTO workflowItem = workflowService.getWorkflowItem(caseNumber);
        return getViewForRefund(workflowItem);
    }

	private RedirectView getViewForRefund(WorkflowItemDTO workflowItem) {
		final RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
		final Long cardId = refundDetails.getCardId();
		final Long approvalId = workflowItem.getApprovalId();
		RedirectView redirectView ;
        switch (workflowItem.getRefundDetails().getRefundScenario()) {
        case FAILEDCARD:
            return createRedirectViewForURLWithCard(cardId, approvalId, PageUrl.INV_FAILED_CARD_REFUND_CART);
        case LOST:
            return createRedirectViewForURLWithCard(cardId, approvalId, PageUrl.INV_LOST_REFUND);
        case STOLEN:
            return createRedirectViewForURLWithCard(cardId, approvalId, PageUrl.INV_STOLEN_REFUND);
        case CANCEL_AND_SURRENDER:
            return createRedirectViewForURLWithCard(cardId, approvalId, PageUrl.INV_CANCEL_AND_SURRENDER_CARD);
        case STANDALONE_GOODWILL_REFUND:
        	redirectView = new RedirectView(PageUrl.INV_STANDALONE_GOODWILL);
        	redirectView.addStaticAttribute(ID, refundDetails.getCustomerId());
        	 if(PaymentType.AD_HOC_LOAD.equals(refundDetails.getPaymentType())){
        		 redirectView.addStaticAttribute(CARD_NUMBER, refundDetails.getCardNumber());
        	 }
        	 redirectView.addStaticAttribute(APPROVAL_ID, approvalId);
        	 return redirectView;
        case ANONYMOUS_GOODWILL_REFUND :
        	redirectView = new RedirectView(PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL);
    		redirectView.addStaticAttribute(CARD_NUMBER, refundDetails.getCardNumber());
    		redirectView.addStaticAttribute(APPROVAL_ID, approvalId);
        	return redirectView;
        default:
            return null;
        }
	}

	private RedirectView createRedirectViewForURLWithCard(Long cardId, Long approvalId, String url) {
		CardDTO card = cardDataService.findById(cardId);
        String cardNumber = card.getCardNumber();
        RedirectView redirectView = new RedirectView(url);
		redirectView.addStaticAttribute(ID, cardNumber);
		redirectView.addStaticAttribute(APPROVAL_ID, approvalId);
		return redirectView;
	}

    
    
    @RequestMapping(params = TARGET_ACTION_SAVE_CHANGES, method = RequestMethod.POST)
    public ModelAndView saveChanges(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber,
                    @ModelAttribute(PageCommand.WORKFLOW_EDIT_COMMAND) WorkflowEditCmd workflowEditCmd, BindingResult result) {

        WorkflowCmd workflowCmd = workflowService.createWorkflowBean(caseNumber, Boolean.FALSE);
        workflowEditCmd = updateWorkflowEditCmd(workflowEditCmd, workflowCmd);
        updateChangesAfterEditRefunds(workflowCmd.getWorkflowItem(), workflowEditCmd);

        editValidator.validate(workflowEditCmd, result);

        ModelAndView modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM);
        if (!result.hasErrors()) {
            workflowCmd = workflowService.saveChanges(workflowCmd, workflowEditCmd);
        } else {
            modelAndView.addObject(PageCommand.WORKFLOW_EDIT_COMMAND, workflowEditCmd);
            workflowCmd.setEdit(Boolean.TRUE);
        }
        modelAndView.addObject(PageCommand.WORKFLOWITEM_COMMAND, workflowCmd);
        modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, new WorkflowRejectCmd());
        return modelAndView;
    }


    protected String getFullNameOfCustomer(RefundDetailDTO refundDetails) {

        StringBuffer fullName = new StringBuffer();
        CustomerDTO customerDto = refundDetails.getCustomer();
        if (customerDto == null){
        	return null;
        }
        return fullName.append(customerDto.getFirstName()).append(StringUtil.SPACE).append(customerDto.getInitials()).append(StringUtil.SPACE)
                        .append(customerDto.getLastName()).toString();
    }


    protected WorkflowEditCmd updateWorkflowEditCmd(WorkflowEditCmd workflowEditCmd, WorkflowCmd workflowCmd) {

        RefundDetailDTO refundDetails = workflowCmd.getWorkflowItem().getRefundDetails();

        CartDTO cartDTO = cartService.findByApprovalId(new Long(workflowCmd.getWorkflowItem().getCaseNumber()));
        List<ItemDTO> cartDtoItems = cartDTO.getCartItems();
        List<GoodwillPaymentItemCmd> goodWillPaymentItemsList = new ArrayList<GoodwillPaymentItemCmd>();

        List<RefundItemCmd> refundItems = new ArrayList<RefundItemCmd>();
        updateCartDTOItems(workflowEditCmd, cartDtoItems, refundItems, goodWillPaymentItemsList);
        workflowEditCmd.setRefundItems(refundItems);
        workflowEditCmd.setGoodwillPaymentItems(goodWillPaymentItemsList);
        workflowEditCmd.setPaymentMethod(refundDetails.getPaymentType().code());
        workflowEditCmd.setPaymentType(refundDetails.getPaymentType());
        workflowEditCmd.setAmount(getCartRefundTotal(cartDTO));
        workflowEditCmd.setTicketDeposit(refundDetails.getDeposit());
        workflowEditCmd.setTotalTicketAmount(getTotalTicketAmount(cartDtoItems));

        workflowEditCmd.setRefundScenarioType(workflowCmd.getRefundScenarioType());
        workflowEditCmd.setRefundScenarioSubType(workflowCmd.getRefundScenarioSubType());
        workflowEditCmd.setPaymentName(getFullNameOfCustomer(refundDetails));
        workflowEditCmd.setPaymentFirstName(refundDetails.getCustomer().getFirstName());
        workflowEditCmd.setPaymentInitials(refundDetails.getCustomer().getInitials());
        workflowEditCmd.setPaymentLastName(refundDetails.getCustomer().getLastName());
        workflowEditCmd = setPaymentDetails(workflowCmd, workflowEditCmd, cartDTO);

        return workflowEditCmd;
    }

    @RequestMapping(params = TARGET_ACTION_CANCEL, method = RequestMethod.POST)
    public ModelAndView cancelEdit(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber, HttpServletRequest request) {
    	WorkflowCmd workflowCmd = workflowService.createWorkflowBean(caseNumber, Boolean.FALSE);
        ModelAndView modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM, PageCommand.WORKFLOWITEM_COMMAND,workflowCmd);
        modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, new WorkflowRejectCmd());
        return modelAndView;
    }

    @RequestMapping(params = TARGET_ACTION_SAVE_COMMENT, method = RequestMethod.POST)
    public ModelAndView saveComment(@RequestParam(value = CASE_NUMBER, required = false) String workflowItemId,
                    @ModelAttribute(PageCommand.WORKFLOW_REJECT_COMMAND) WorkflowCmd workflowCmd) {

        workflowService.addCaseHistoryNote(workflowItemId, workflowCmd.getCaseNotes());

        workflowCmd = workflowService.createWorkflowBean(workflowItemId, Boolean.FALSE);
        WorkflowEditCmd workflowEditCmd = new WorkflowEditCmd(workflowCmd);

        ModelAndView modelAndView = new ModelAndView(PageView.VIEW_WORKFLOW_ITEM, PageCommand.WORKFLOWITEM_COMMAND, workflowCmd);
        modelAndView.addObject(PageCommand.WORKFLOW_REJECT_COMMAND, new WorkflowRejectCmd());
        modelAndView.addObject(PageCommand.WORKFLOW_EDIT_COMMAND, workflowEditCmd);

        return modelAndView;
    }
    
    @RequestMapping(params = TARGET_ACTION_CLOSE, method = RequestMethod.POST)
    public ModelAndView close(@RequestParam(value = CASE_NUMBER, required = false) String caseNumber) {

        AgentGroup group = workflowService.close(caseNumber);
        ApprovalListCmdImpl approvalListCmd = new ApprovalListCmdImpl();
        approvalListCmd.setFormLocation(group.listCode());
        return new ModelAndView(new RedirectView(group.listCode() + ".htm"));
    }
    
    protected WorkflowEditCmd updateChangesAfterEditRefunds(WorkflowItemDTO workflowItem, WorkflowEditCmd workflowEditCmd) {
    	PayeePaymentDTO payeePayment = workflowService.getLocalPayeePayment(workflowItem.getTaskId());
    	if(payeePayment!= null){
    		editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
    	}
    	return workflowEditCmd;
    }
}
