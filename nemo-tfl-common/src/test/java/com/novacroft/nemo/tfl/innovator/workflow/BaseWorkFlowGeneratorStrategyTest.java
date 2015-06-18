package com.novacroft.nemo.tfl.innovator.workflow;

import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@RunWith(MockitoJUnitRunner.class)
public class BaseWorkFlowGeneratorStrategyTest {

	
	private BaseWorkFlowGeneratorStrategyImpl baseWorkFlowGeneratorStrategy = new BaseWorkFlowGeneratorStrategyImpl();
	@Mock
	protected GetCardService mockGetCardService;
    @Mock
    protected RefundToWorkflowConverter mockRefundToWorkflowConverter;
    @Mock
    protected CustomerDataService mockCustomerDataService;
    @Mock
    protected AddressDataService mockAddressService;
    @Mock
    protected CardService mockCardService;
    @Mock
    protected CartService mockCartService;
    @Mock
    protected CartAdministrationService mockCartAdministrationService;
	
    @Before
    public void setUp(){
    	baseWorkFlowGeneratorStrategy.refundToWorkflowConverter = mockRefundToWorkflowConverter;
    	baseWorkFlowGeneratorStrategy.cardService = mockCardService;
    	baseWorkFlowGeneratorStrategy.cartService = mockCartService;
    	baseWorkFlowGeneratorStrategy.cartAdministrationService = mockCartAdministrationService;
    	baseWorkFlowGeneratorStrategy.customerDataService = mockCustomerDataService;
    	baseWorkFlowGeneratorStrategy.addressService= mockAddressService;
    }
    
    
    @Test
    public void testSetUpWorkFlowVariablesShouldSetUpVariables(){
    	
        WorkflowItemDTO workflowItemDTO  = new WorkflowItemDTO();
        RefundDetailDTO refundDetailDTO = new RefundDetailDTO();
        Long refundAmount = 3l;
        Refund refund = new Refund();
        refund.setRefundAmount(refundAmount);
        refundDetailDTO.setRefundEntity(refund);
        workflowItemDTO.setRefundDetails(refundDetailDTO);
        CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
        cartCmdImpl.setApprovalId(3l);
        cartCmdImpl.setPaymentType("TEST");
        when(mockRefundToWorkflowConverter.convert(any(CartCmdImpl.class))).thenReturn(workflowItemDTO)  ;
        Map<String, Object> workflowProcessVariablesMap = new HashMap<>();
        baseWorkFlowGeneratorStrategy.setUpWorkFlowVariables(cartCmdImpl, workflowProcessVariablesMap);
        verify(mockRefundToWorkflowConverter).convert(any(CartCmdImpl.class));
        assertEquals(workflowProcessVariablesMap.get(WORKFLOW_ITEM), workflowItemDTO);
    	
    }
   
   @Test
   public void testSetupCustomerDetailsForWorkFlow(){
       WorkflowItemDTO workflowItemDTO  = new WorkflowItemDTO();
       RefundDetailDTO refundDetailDTO = new RefundDetailDTO();
       Long refundAmount = 3l;
       Refund refund = new Refund();
       refund.setRefundAmount(refundAmount);
       refundDetailDTO.setRefundEntity(refund);
       workflowItemDTO.setRefundDetails(refundDetailDTO);
       CartCmdImpl cartCmdImpl = CartCmdTestUtil.getTestCartCmd1();
       cartCmdImpl.setApprovalId(3l);
       AddressDTO addressDTO = new AddressDTO();
       addressDTO.setId(3l);
       cartCmdImpl.setPayeeAddress(addressDTO );
       cartCmdImpl.setCartDTO(CartTestUtil.getCartDTOWithAllItemsForOrderNewCard());
       when(mockCartService.findCustomerForCart(any(Long.class))).thenReturn(CustomerTestUtil.getCustomerDTO(3l));
       Map<String, Object> workflowProcessVariablesMap = new HashMap<>();
       baseWorkFlowGeneratorStrategy.setUpCustomerDetailsForWorkFlow(cartCmdImpl, workflowProcessVariablesMap);
       verify(mockCartService).findCustomerForCart(any(Long.class));
   }
}
