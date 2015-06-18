package com.novacroft.nemo.tfl.innovator.controller.workflow;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.APPROVAL_ID;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.util.PayeePaymentTestUtil.getPayeePaymentDTOWithBACS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.RefundWorkflowTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundService;
import com.novacroft.nemo.tfl.common.application_service.impl.WorkFlowServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.form_validator.ApprovalsRejectionValidator;
import com.novacroft.nemo.tfl.common.form_validator.WorkflowItemEditValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class ViewWorkflowItemControllerTest {
    private static String REJECT_REASON = "Duplicate Case";
    private static String INVALID_REJECT_REASON = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private ViewWorkflowItemController viewWorkflowItemController;
    private ViewWorkflowItemController mockController;
    private WorkFlowServiceImpl mockWorkflowService;
    private SelectListService mockSelectListService;
    private CardDataService mockCardService;
    private WorkflowItemConverter mockConverter;
    private WorkflowItemDTO mockWorkflowItemDTO;
    private WorkflowCmd mockWorkflowCommand;
    private ApprovalsRejectionValidator rejectValidator;
    private WorkflowItemEditValidator mockEditValidator;
    private RefundDetailDTO mockRefundDetails;
    private CustomerDTO mockCustomer;
    private CardDTO mockCard;
    private WorkflowEditCmd mockWorkflowEditCmd;
    private BindingResult errors;
    private AddressDTO mockAddressDTO;
    private CartService mockCartService;
    private CartDTO mockCartDto;
    private RefundService mockRefundService;
    private WorkflowCmd mockWorkflowCmd;
    private EditRefundPaymentService mockEditRefundPaymentService;
    private Refund mockRefund;
    private CountrySelectListService mockCountrySelectListService;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {

        viewWorkflowItemController = new ViewWorkflowItemController();
        mockController = mock(ViewWorkflowItemController.class);
        mockWorkflowService = mock(WorkFlowServiceImpl.class);
        mockSelectListService = mock(SelectListService.class);
        mockCardService = mock(CardDataService.class);
        mockConverter = mock(WorkflowItemConverter.class);
        mockWorkflowItemDTO = mock(WorkflowItemDTO.class);
        mockWorkflowCommand = mock(WorkflowCmd.class);
        rejectValidator = new ApprovalsRejectionValidator();
        mockEditValidator = mock(WorkflowItemEditValidator.class);
        mockRefundDetails = mock(RefundDetailDTO.class);
        mockCustomer = mock(CustomerDTO.class);
        mockCard = mock(CardDTO.class);
        errors = mock(BindingResult.class);
        mockAddressDTO = mock(AddressDTO.class);
        mockCartService = mock(CartService.class);
        mockCartDto = mock(CartDTO.class);
        mockRefundService = mock(RefundService.class);
        mockWorkflowEditCmd = new WorkflowEditCmd();
        mockWorkflowCmd = new WorkflowCmd();
        mockRefund = mock(Refund.class);
        mockRefund.setRefundAmount(APPROVAL_ID);
        mockWorkflowItemDTO.setRefundDetails(mockRefundDetails);
        mockWorkflowCmd.setWorkflowItem(mockWorkflowItemDTO);
        mockWorkflowCmd.setCustomerAddressDTO(mockAddressDTO);
        mockEditRefundPaymentService = mock(EditRefundPaymentService.class);
        mockCountrySelectListService = mock(CountrySelectListService.class);
        
        viewWorkflowItemController.workflowService = mockWorkflowService;
        viewWorkflowItemController.selectListService = mockSelectListService;
        viewWorkflowItemController.converter = mockConverter;
        viewWorkflowItemController.rejectionValidator = rejectValidator;
        viewWorkflowItemController.editValidator = mockEditValidator;
        viewWorkflowItemController.cardDataService = mockCardService;
        viewWorkflowItemController.cartService = mockCartService;
        viewWorkflowItemController.refundService = mockRefundService;
        viewWorkflowItemController.editRefundPaymentService = mockEditRefundPaymentService;
        viewWorkflowItemController.countrySelectListService = mockCountrySelectListService;
        
        mockController.workflowService = mockWorkflowService;
        mockController.selectListService = mockSelectListService;
        mockController.converter = mockConverter;
        mockController.rejectionValidator = rejectValidator;
        mockController.editValidator = mockEditValidator;
        mockController.cardDataService = mockCardService;
        mockController.cartService = mockCartService;
        mockController.refundService = mockRefundService;
        mockController.workflowService = mockWorkflowService;
        

        when(mockWorkflowService.getWorkflowItem(anyString())).thenReturn(mockWorkflowItemDTO);
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(mockWorkflowCommand);
        when(mockConverter.convert(mockWorkflowItemDTO)).thenReturn(mockWorkflowCommand);
        when(mockWorkflowCommand.getWorkflowItem()).thenReturn(mockWorkflowItemDTO);
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetails);
        when(mockWorkflowCommand.getCustomerAddressDTO()).thenReturn(mockAddressDTO);
        when(mockWorkflowCommand.getPaymentAddressDTO()).thenReturn(mockAddressDTO);
        when(mockRefundDetails.getCustomer()).thenReturn(mockCustomer);
        when(mockCardService.findById(anyLong())).thenReturn(mockCard);
        when(mockCartService.findById(anyLong())).thenReturn(mockCartDto);
        when(mockRefundDetails.getPaymentType()).thenReturn(PaymentType.AD_HOC_LOAD, PaymentType.BACS, PaymentType.WEB_ACCOUNT_CREDIT, PaymentType.PAYMENT_CARD, PaymentType.CHEQUE);

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.FAILEDCARD, RefundScenarioEnum.LOST, RefundScenarioEnum.STOLEN,
                        RefundScenarioEnum.CANCEL_AND_SURRENDER, RefundScenarioEnum.DESTROYED);

        doCallRealMethod().when(mockController).setPaymentDetails(any(WorkflowCmd.class), any(WorkflowEditCmd.class), any(CartDTO.class));
        doCallRealMethod().when(mockController).updateCartDTOItems(any(WorkflowEditCmd.class), Mockito.anyList(), Mockito.anyList(), Mockito.anyList());

    }

    @Test
    public void shouldReturnView() {
        ModelAndView workflowItemView = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.FALSE, null);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
    }

    @Test
    public void shouldReturnViewWhenEditIsTrue() {
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartCmdTestUtil.getAllCartDTOItems());
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        viewWorkflowItemController.getCartItemsDtoList(CartCmdTestUtil.getAllCartDTOInCartCmdImpl());

        ModelAndView workflowItemView = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);

        verify(mockWorkflowService, atLeastOnce()).createWorkflowBean(anyString(), anyBoolean());
        verify(mockWorkflowService, atLeastOnce()).getCartDtoFromCardId(anyLong());
        verify(mockWorkflowService, atLeastOnce()).isItemDtoTypeOfGoodwillPaymentDTO(any(ItemDTO.class));
        verify(mockWorkflowService, atLeastOnce()).isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class));
        verify(mockWorkflowService, atLeastOnce()).isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class));
        verify(mockWorkflowService, atLeastOnce()).isTravelCardOrBusPass(any(ItemDTO.class));

        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_REJECT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_REJECT_COMMAND) instanceof WorkflowRejectCmd);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_EDIT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_EDIT_COMMAND) instanceof WorkflowEditCmd);
    }

    @Test
    public void shouldReturnViewWhenEditIsTrueAndRefundForProductItems() {
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockWorkflowService.isTravelCardOrBusPass(any(ItemDTO.class))).thenReturn(true);
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());

        ModelAndView workflowItemView = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);

        verify(mockWorkflowService, atLeastOnce()).createWorkflowBean(anyString(), anyBoolean());
        verify(mockWorkflowService, atLeastOnce()).isTravelCardOrBusPass(any(ItemDTO.class));

        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_REJECT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_REJECT_COMMAND) instanceof WorkflowRejectCmd);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_EDIT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_EDIT_COMMAND) instanceof WorkflowEditCmd);
    }

    @Test
    public void shouldReturnViewWhenEditIsTrueAndRefundForAdministrationFeeItems() {
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockWorkflowService.isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class))).thenReturn(true);
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartTestUtil.getNewCartDTOWithItem());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());

        ModelAndView workflowItemView = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);

        verify(mockWorkflowService, atLeastOnce()).createWorkflowBean(anyString(), anyBoolean());

        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_REJECT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_REJECT_COMMAND) instanceof WorkflowRejectCmd);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_EDIT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_EDIT_COMMAND) instanceof WorkflowEditCmd);
    }

    @Test
    public void shouldReturnViewWhenEditIsTrueAndRefundForPayAsYouGoItems() {
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithCartRefundTotalForPayASyouGoItem());
        when(mockWorkflowService.isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class))).thenReturn(true);
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartCmdTestUtil.getAllCartDTOItems());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        ModelAndView workflowItemView = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);

        verify(mockWorkflowService, atLeastOnce()).createWorkflowBean(anyString(), anyBoolean());
        verify(mockWorkflowService, atLeastOnce()).isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class));

        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_REJECT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_REJECT_COMMAND) instanceof WorkflowRejectCmd);
        assertTrue(workflowItemView.getModel().containsKey(PageCommand.WORKFLOW_EDIT_COMMAND));
        assertTrue(workflowItemView.getModel().get(PageCommand.WORKFLOW_EDIT_COMMAND) instanceof WorkflowEditCmd);
    }

    @Test
    public void shouldPopulateTitlesSelectList() {
        Model model = new BindingAwareModelMap();
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        viewWorkflowItemController.populateTitlesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.TITLES));
    }

    @Test
    public void shouldPopulateRefundsSelectList() {
        Model model = new BindingAwareModelMap();
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        viewWorkflowItemController.populateRefundsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.REFUNDS));
    }

    @Test
    public void shouldPopulateRefundsScenarioSelectList() {
        Model model = new BindingAwareModelMap();
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        viewWorkflowItemController.populateRefundsScenarioSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.REFUNDS_SCENARIO_TYPES));
    }

    @Test
    public void shouldPopulateRejectionReasonSelectList() {
        Model model = new BindingAwareModelMap();
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        viewWorkflowItemController.populateRejectionReasonSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.REJECTION_REASONS));
    }

    @Test
    public void shouldRejectCaseWithValidInput() {
        when(mockWorkflowService.reject(anyString(), any(WorkflowRejectCmd.class))).thenReturn(AgentGroup.EXCEPTIONS);
        WorkflowRejectCmd cmd = new WorkflowRejectCmd();
        cmd.setRejectReason(REJECT_REASON);
        cmd.setRejectFreeText(REJECT_REASON);
        BindingResult errors = new BeanPropertyBindingResult(cmd, "cmd");
        ModelAndView workflowItemView = viewWorkflowItemController.reject(REFUND_IDENTIFIER, cmd, errors);
        assertEquals(PageUrl.EXCEPTIONLIST, ((RedirectView) workflowItemView.getView()).getUrl());
        verify(mockWorkflowService, atLeastOnce()).reject(anyString(), any(WorkflowRejectCmd.class));
        assertFalse(errors.hasErrors());
    }

    @Test
    public void shouldNotRejectCaseWithInvalidInput() {
        when(mockWorkflowService.reject(anyString(), any(WorkflowRejectCmd.class))).thenReturn(null);
        WorkflowRejectCmd cmd = new WorkflowRejectCmd();
        cmd.setRejectFreeText(INVALID_REJECT_REASON);
        BindingResult errors = new BeanPropertyBindingResult(cmd, "cmd");
        ModelAndView workflowItemView = viewWorkflowItemController.reject(REFUND_IDENTIFIER, cmd, errors);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertTrue(errors.hasErrors());
        verify(mockWorkflowService, never()).reject(anyString(), any(WorkflowRejectCmd.class));
    }

    @Test
    public void shouldReturnEditView() {
        ModelAndView workflowItemView = viewWorkflowItemController.edit(REFUND_IDENTIFIER);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
    }

    @Test
    public void shouldRedirectToRefund() {
        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.FAILEDCARD);
        RedirectView workflowItemViewFailed = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_FAILED_CARD_REFUND_CART, workflowItemViewFailed.getUrl());

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.LOST);
        RedirectView workflowItemViewLost = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_LOST_REFUND, workflowItemViewLost.getUrl());

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.STOLEN);
        RedirectView workflowItemViewStolen = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_STOLEN_REFUND, workflowItemViewStolen.getUrl());

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.CANCEL_AND_SURRENDER);
        RedirectView workflowItemViewCancelAndSurrender = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_CANCEL_AND_SURRENDER_CARD, workflowItemViewCancelAndSurrender.getUrl());

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.DESTROYED);
        RedirectView workflowItemViewNull = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(null, workflowItemViewNull);
    }

    @Test
    public void shouldCancelEdit() {
    	when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(mockWorkflowCmd);
        ModelAndView workflowItemView = viewWorkflowItemController.cancelEdit(REFUND_IDENTIFIER, null);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
    }

    @Test
    public void shouldSaveChangesWithNoErrors() {
        doNothing().when(mockEditValidator).validate(anyObject(), any(Errors.class));
        when(errors.hasErrors()).thenReturn(Boolean.FALSE);
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        when(mockWorkflowCommand.getWorkflowItem()).thenReturn(RefundWorkflowTestUtil.getWorkflowItem());
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(RefundWorkflowTestUtil.getRefundDetails());
        when(mockRefundDetails.getCustomer()).thenReturn(RefundWorkflowTestUtil.getCustomerDto());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());

        doCallRealMethod().when(mockController).saveChanges(anyString(), any(WorkflowEditCmd.class), any(BindingResult.class));
        doCallRealMethod().when(mockWorkflowCommand).setEdit(anyBoolean());
        doCallRealMethod().when(mockWorkflowCommand).getEdit();
        doCallRealMethod().when(mockController).updateWorkflowEditCmd(any(WorkflowEditCmd.class), any(WorkflowCmd.class));

        ModelAndView workflowItemView = mockController.saveChanges(REFUND_IDENTIFIER, RefundWorkflowTestUtil.getWorkFlowEditcmd(), errors);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertEquals(null, mockWorkflowCommand.getEdit());
    }

    @Test
    public void shouldSaveChangesWithErrors() {
        doNothing().when(mockEditValidator).validate(anyObject(), any(Errors.class));
        when(errors.hasErrors()).thenReturn(Boolean.TRUE);
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmd());
        doCallRealMethod().when(mockController).saveChanges(anyString(), any(WorkflowEditCmd.class), any(BindingResult.class));
        ModelAndView workflowItemView = mockController.saveChanges(REFUND_IDENTIFIER, RefundWorkflowTestUtil.getWorkFlowEditcmd(), errors);
        assertViewName(workflowItemView, PageView.VIEW_WORKFLOW_ITEM);
        assertEquals(Boolean.TRUE, RefundWorkflowTestUtil.getWorkflowCmd().getEdit());
    }

    @Test
    public void shouldUpdateWorkflowEditCmdWithCartWithTravelcard() {

        when(mockCartDto.getCartItems()).thenReturn(RefundWorkflowTestUtil.getProductItemDto());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        doCallRealMethod().when(mockController).updateWorkflowEditCmd(any(WorkflowEditCmd.class), any(WorkflowCmd.class));
        doCallRealMethod().when(mockWorkflowService).isTravelCardOrBusPass(any(ItemDTO.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfGoodwillPaymentDTO(any(ItemDTO.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class));
        RefundItemCmd mockRefundItem = new RefundItemCmd((ProductItemDTO)CartTestUtil.getNewCartDTOWithProductItemWithRefund().getCartItems().get(0));
        WorkflowEditCmd resultEditCmd = mockController.updateWorkflowEditCmd(mockWorkflowEditCmd, RefundWorkflowTestUtil.getWorkflowCmd());
        assertEquals(mockRefundItem, resultEditCmd.getRefundItems().get(0));
    }

    @Test
    public void shouldUpdateWorkflowEditCmdWithCartWithBusPass() {

        when(mockCartDto.getCartItems()).thenReturn(RefundWorkflowTestUtil.getProductItemDto());
        RefundItemCmd mockRefundItem = new RefundItemCmd((ProductItemDTO)CartTestUtil.getNewCartDTOWithProductItemWithRefund().getCartItems().get(0));
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(RefundWorkflowTestUtil.getRefundDetails());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId());
        doCallRealMethod().when(mockController).updateWorkflowEditCmd(any(WorkflowEditCmd.class), any(WorkflowCmd.class));
        doCallRealMethod().when(mockWorkflowService).isTravelCardOrBusPass(any(ItemDTO.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class));

        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.FAILEDCARD);
        WorkflowEditCmd resultEditCmd = mockController.updateWorkflowEditCmd(mockWorkflowEditCmd, RefundWorkflowTestUtil.getWorkflowCmd());
        assertEquals(mockRefundItem, resultEditCmd.getRefundItems().get(0));
    }

    @Test
    public void shouldAddCaseNote() {
        doNothing().when(mockWorkflowService).addCaseHistoryNote(anyString(), anyString());
        ModelAndView saveComment = viewWorkflowItemController.saveComment("1", mockWorkflowCommand);
        assertEquals(mockWorkflowCommand, saveComment.getModel().get(PageCommand.WORKFLOWITEM_COMMAND));
    }
   

    @Test
    public void testshouldUpdateWorkflowEditCmdWithgoodwillPaymentItem() {
        GoodwillPaymentItemCmd mockGoodwillPaymentItemCmd = RefundWorkflowTestUtil.getCardGoodwillPaymentItem().get(0);
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(RefundWorkflowTestUtil.getRefundDetails());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        doCallRealMethod().when(mockController).updateWorkflowEditCmd(any(WorkflowEditCmd.class), any(WorkflowCmd.class));
        doCallRealMethod().when(mockWorkflowService).isItemDtoTypeOfGoodwillPaymentDTO((any(ItemDTO.class)));

        WorkflowEditCmd resultEditCmd = mockController.updateWorkflowEditCmd(mockWorkflowEditCmd, RefundWorkflowTestUtil.getWorkflowCmd());
        assertEquals(mockGoodwillPaymentItemCmd, resultEditCmd.getGoodwillPaymentItems().get(0));

    }

    @Test
    public void testApprove() {
        when(mockWorkflowService.approve(anyString())).thenReturn(AgentGroup.FIRST_STAGE_APPROVER);
        viewWorkflowItemController.approve(REFUND_IDENTIFIER);
        verify(mockWorkflowService, atLeastOnce()).approve(anyString());
    }
    
    @Test
    public void closeShouldShowExceptionsListView() {
        when(mockWorkflowService.close(anyString())).thenReturn(AgentGroup.EXCEPTIONS);
        ModelAndView workflowItemView = viewWorkflowItemController.close(REFUND_IDENTIFIER);
        assertEquals(PageUrl.EXCEPTIONLIST, ((RedirectView) workflowItemView.getView()).getUrl());
        verify(mockWorkflowService, atLeastOnce()).close(anyString());
    }
    
    @Test
    public void testSetPaymentDetailsShouldSetWorkflowEditForBACS(){
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmdWithBACSPaymentType());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartCmdTestUtil.getAllCartDTOItems());
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartTestUtil.getNewCartDTOWithGoodwillItem());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTOWithBACS());
        viewWorkflowItemController.getCartItemsDtoList(CartCmdTestUtil.getAllCartDTOInCartCmdImpl());

        ModelAndView modelAndView  = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);
        ModelMap modelMap = modelAndView.getModelMap();
    	WorkflowEditCmd workflowEditCmd = (WorkflowEditCmd) modelMap.get(PageCommand.WORKFLOW_EDIT_COMMAND);
    	
    	assertEquals(PaymentType.BACS.code(), workflowEditCmd.getPaymentMethod());
    }
    
    @Test
    public void testSetPaymentDetailsShouldSetWorkflowEditForCheque(){
        when(mockWorkflowService.createWorkflowBean(anyString(), anyBoolean())).thenReturn(RefundWorkflowTestUtil.getWorkflowCmdWithChequePaymentType());
        when(mockWorkflowService.getCartDtoFromCardId(anyLong())).thenReturn(CartCmdTestUtil.getAllCartDTOItems());
        when(mockWorkflowService.getLocalPayeePayment(anyString())).thenReturn(getPayeePaymentDTOWithBACS());
        when(mockCartService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockRefundService.getCartDTOForRefund(anyString(), anyString(), anyBoolean())).thenReturn(CartTestUtil.getCartDTOWithProductItemWithRefundAndApprovalId());
        viewWorkflowItemController.getCartItemsDtoList(CartCmdTestUtil.getAllCartDTOInCartCmdImpl());

        ModelAndView modelAndView  = viewWorkflowItemController.viewWorkFlowItem(REFUND_IDENTIFIER, Boolean.TRUE, null);
        ModelMap modelMap = modelAndView.getModelMap();
    	WorkflowEditCmd workflowEditCmd = (WorkflowEditCmd) modelMap.get(PageCommand.WORKFLOW_EDIT_COMMAND);
    	
    	assertEquals(PaymentType.CHEQUE.code(), workflowEditCmd.getPaymentMethod());
    }
    
    @Test
    public void shouldPopulateCountrySelectList() {
        Model model = new BindingAwareModelMap();
        when(mockCountrySelectListService.getSelectList()).thenReturn(new SelectListDTO());
        viewWorkflowItemController.populateCountrySelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.COUNTRIES));
    }
    
    @Test
    public void shouldRedirectToStandaloneRefund() {
        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.STANDALONE_GOODWILL_REFUND);
        RedirectView workflowItemViewFailed = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_STANDALONE_GOODWILL, workflowItemViewFailed.getUrl());
    }

    @Test
    public void shouldRedirectToAnonymousRefund() {
        when(mockRefundDetails.getRefundScenario()).thenReturn(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND);
        RedirectView workflowItemViewFailed = viewWorkflowItemController.editRefund(REFUND_IDENTIFIER);
        assertEquals(PageUrl.INV_ANONYMOUS_GOODWILL_REFUND_GOODWILL, workflowItemViewFailed.getUrl());
    }
    
    @Test
    public void getFullNameOfCustomerShouldReturnNull() {
        RefundDetailDTO testDTO = new RefundDetailDTO();
        testDTO.setCustomer(null);
        assertNull(viewWorkflowItemController.getFullNameOfCustomer(testDTO));
    }
}
