package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.BANK_ACCOUNT;
import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.SORT_CODE;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.getTestCartCmd1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.INITIALS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TITLE_1;
import static com.novacroft.nemo.test_support.OrderTestUtil.STATION_ID;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.generateWorkflowItem;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.util.PayeePaymentTestUtil.getPayeePaymentDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.activiti.engine.TaskService;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class EditRefundPaymentServiceImplTest {

	private EditRefundPaymentServiceImpl editRefundPaymentService;
	private TaskService mockTaskService;
	private WorkflowItemDTO workflowItem;
	private CartCmdImpl cartCmdImpl;
	private PayeePaymentDTO payeePayment;
	private WorkflowEditCmd workflowEditCmd;
	private ContentDataService mockContentDataService;
	private ContentDTO mockContentDTO;
    private LocationDataService mockLocationDataService;

	@Before
	public void setUp() {
		editRefundPaymentService = new EditRefundPaymentServiceImpl();
		mockTaskService = mock(TaskService.class);
		cartCmdImpl = new CartCmdImpl();
		workflowEditCmd = new WorkflowEditCmd();
		workflowItem = generateWorkflowItem();
		workflowItem.getRefundDetails().setCustomer(getCustomerInfo());
		payeePayment = getPayeePaymentDTO1();
		payeePayment.setIsEdited(Boolean.TRUE);
		mockContentDataService = mock(ContentDataService.class);
		mockContentDTO = mock(ContentDTO.class);
		mockContentDTO.setContent(PaymentType.WEB_ACCOUNT_CREDIT.code());
        mockLocationDataService = mock(LocationDataService.class);
		editRefundPaymentService.taskService = mockTaskService;
		editRefundPaymentService.contentDataService = mockContentDataService;
        editRefundPaymentService.locationDataService = mockLocationDataService;

        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
	}
	
	private CustomerDTO getCustomerInfo(){
		CustomerDTO customer = new CustomerDTO();
		customer.setTitle(TITLE_1);
		customer.setFirstName(FIRST_NAME_1);
		customer.setInitials(INITIALS_1);
		customer.setLastName(LAST_NAME_1);
		return customer;
	}
	
	private CartCmdImpl prepareCartCmdImplForTest(){
		cartCmdImpl = getTestCartCmd1();
		cartCmdImpl.setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT.code());
		cartCmdImpl.setFirstName(FIRST_NAME_1);
		cartCmdImpl.setInitials(INITIALS_1);
		cartCmdImpl.setLastName(LAST_NAME_1);
		cartCmdImpl.setPayeeAddress(getTestAddressDTO1());
		cartCmdImpl.setPayeeSortCode(SORT_CODE);
		cartCmdImpl.setPayeeAccountNumber(BANK_ACCOUNT);
		cartCmdImpl.setTargetCardNumber(OYSTER_NUMBER_1);
		cartCmdImpl.setStationId(STATION_ID);
		
		return cartCmdImpl;
	}
	
	@Test
	public void getModifiedRefundPaymentFieldsMustReturnPayeePaymentDTOTest(){
		when(mockContentDataService.findByLocaleAndCode(anyString(), anyString())).thenReturn(mockContentDTO);
		cartCmdImpl = prepareCartCmdImplForTest();
		payeePayment = editRefundPaymentService.getModifiedRefundPaymentFields(workflowItem, cartCmdImpl);
        getModifiedRefundPaymentFieldsIsEditedAsserts(payeePayment);
		
        cartCmdImpl.setPaymentType(PaymentType.BACS.code());
        payeePayment = editRefundPaymentService.getModifiedRefundPaymentFields(workflowItem, cartCmdImpl);
        getModifiedRefundPaymentFieldsIsEditedAsserts(payeePayment);

        cartCmdImpl.setPaymentType(PaymentType.CHEQUE.code());
        payeePayment = editRefundPaymentService.getModifiedRefundPaymentFields(workflowItem, cartCmdImpl);
        getModifiedRefundPaymentFieldsIsEditedAsserts(payeePayment);
    }

    protected void getModifiedRefundPaymentFieldsIsEditedAsserts(PayeePaymentDTO payeePayment) {
        assertTrue(payeePayment.getIsEdited());

        assertEquals(FIRST_NAME_1, payeePayment.getFirstName());
        assertEquals(INITIALS_1, payeePayment.getInitials());
        assertEquals(LAST_NAME_1, payeePayment.getLastName());

        assertEquals(BANK_ACCOUNT, payeePayment.getPayeeAccountNumber());
        assertEquals(SORT_CODE, payeePayment.getPayeeSortCode());
        assertEquals(STATION_ID, payeePayment.getStationId());
        assertEquals(OYSTER_NUMBER_1, payeePayment.getTargetCardNumber());
	}
	
	@Test
	public void populateEditedValuesInCartCmdImplFromPayeePaymentTest(){
		
		cartCmdImpl = editRefundPaymentService.populateEditedValuesInCartCmdImplFromPayeePayment(cartCmdImpl, payeePayment);
		
		assertEquals( FIRST_NAME_1, cartCmdImpl.getFirstName());
		assertEquals( INITIALS_1, cartCmdImpl.getInitials());
		assertEquals( LAST_NAME_1, cartCmdImpl.getLastName());
		
		assertEquals( BANK_ACCOUNT, cartCmdImpl.getPayeeAccountNumber());
		assertEquals( SORT_CODE, cartCmdImpl.getPayeeSortCode());
		assertEquals( STATION_ID, cartCmdImpl.getStationId());
		assertEquals( OYSTER_NUMBER_1, cartCmdImpl.getTargetCardNumber());
	}

	@Test
	public void updateWorkflowEditCmdWithModifiedEditRefundsDetailsShouldSetPaymentTypeTest(){
		workflowEditCmd = editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
		assertEquals( PaymentType.WEB_ACCOUNT_CREDIT.code(), workflowEditCmd.getPaymentMethod());
	}
	
	@Test
	public void updateWorkflowEditCmdWithModifiedEditRefundsDetailsShouldSetAdhocLoadDetailsTest(){
		payeePayment.setPaymentType(ADHOC_LOAD);
        payeePayment.setStationId(STATION_ID);
        payeePayment.setTargetCardNumber(OYSTER_NUMBER_1);
		workflowEditCmd = editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
		
		assertEquals( ADHOC_LOAD, workflowEditCmd.getPaymentMethod());
		assertEquals( STATION_ID, workflowEditCmd.getStationId());
		assertEquals( OYSTER_NUMBER_1, workflowEditCmd.getTargetCardNumber());
	}
	
	@Test
	public void updateWorkflowEditCmdWithModifiedEditRefundsDetailsShouldSetWebCreditDetailsTest(){
		payeePayment.setPaymentType(WEB_CREDIT);
		workflowEditCmd = editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
		
		assertEquals( WEB_CREDIT, workflowEditCmd.getPaymentMethod());
		assertNull(workflowEditCmd.getPayeeSortCode());
	}
	
	@Test
	public void updateWorkflowEditCmdWithModifiedEditRefundsDetailsShouldSetBACSDetailsTest(){
		payeePayment.setPaymentType(PaymentType.BACS.code());
		workflowEditCmd = editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
		
		assertEquals( PaymentType.BACS.code(), workflowEditCmd.getPaymentMethod());
		assertEquals( SORT_CODE, workflowEditCmd.getPayeeSortCode());
	}
	
	@Test
	public void updateWorkflowEditCmdWithModifiedEditRefundsDetailsShouldSetChequeDetailsTest(){
		payeePayment.setPaymentType(PaymentType.CHEQUE.code());
		workflowEditCmd = editRefundPaymentService.updateWorkflowEditCmdWithModifiedEditRefundsDetails(workflowItem, workflowEditCmd, payeePayment);
		
		assertEquals( PaymentType.CHEQUE.code(), workflowEditCmd.getPaymentMethod());
		assertNull(workflowEditCmd.getPayeeSortCode());
	}
	
	@Test
	public void clearEditedPayeePaymentInfoShouldInvokeTaskServiceTest(){
		editRefundPaymentService.clearEditedPayeePaymentInfo(workflowItem);
		verify(mockTaskService).setVariableLocal(anyString(), anyString(), any(PayeePaymentDTO.class));
	}
}
