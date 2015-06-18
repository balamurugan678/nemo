package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.CustomerTestUtil.FORMATED_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.getWorkflowItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowItemConverterTest {
    private WorkflowItemConverter converter;
    private LocationDataService mockLocationDataService;

    @Before
    public void init() {
        converter = new WorkflowItemConverter();
        mockLocationDataService = mock(LocationDataService.class);
        converter.locationDataService = mockLocationDataService;

        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1());
    }

    @Test
    public void convertSuccessfully() {        
        WorkflowItemDTO testDTO = getWorkflowItem();
        testDTO.setCreatedTime(new DateTime());
        testDTO.setPaymentMethod(PaymentType.BACS.code());
        
        WorkflowCmd actualResult = converter.convert(testDTO);

        assertNotNull(actualResult);
        assertEquals(testDTO, actualResult.getWorkflowItem());
    }
    
    @Test(expected=AssertionError.class)
    public void formatCustomerNameShouldAssertError() {
        converter.formatCustomerName(null);
    }
    
    @Test
    public void formatCustomerNameShouldReturnName() {
        assertEquals(FORMATED_NAME_1, converter.formatCustomerName(getTestCustomerDTO1()));
    }
    
    @Test
    public void getCartTypeCodeShouldReturnNull() {
        assertNull(converter.getCartTypeCode(RefundScenarioEnum.OTHER));
    }
    
    @Test
    public void getCartTypeCodeIfFaildCard() {
        assertEquals(CartType.FAILED_CARD_REFUND.code(), converter.getCartTypeCode(RefundScenarioEnum.FAILEDCARD));
    }
    
    @Test
    public void getCartTypeCodeIfDestroyedCard() {
        assertEquals(CartType.DESTROYED_CARD_REFUND.code(), converter.getCartTypeCode(RefundScenarioEnum.DESTROYED));
    }
    
    @Test
    public void getCartTypeCodeIfLost() {
        assertEquals(CartType.LOST_REFUND.code(), converter.getCartTypeCode(RefundScenarioEnum.LOST));
    }
    
    @Test
    public void getCartTypeCodeIfStolen() {
        assertEquals(CartType.STOLEN_REFUND.code(), converter.getCartTypeCode(RefundScenarioEnum.STOLEN));
    }
    
    @Test
    public void getCartTypeCodeIfCancelled() {
        assertEquals(CartType.CANCEL_SURRENDER_REFUND.code(), converter.getCartTypeCode(RefundScenarioEnum.CANCEL_AND_SURRENDER));
    }
    
    @Test
    public void convertToWorkflowCommandPaymentMethodShouldReturnWebCredit() {
        assertEquals("Web Credit", 
                        converter.convertToWorkflowCommandPaymentMethod(PaymentType.WEB_ACCOUNT_CREDIT.code()));
    }
    
    @Test
    public void convertToWorkflowCommandPaymentMethodShouldReturnAdhocLoad() {
        assertEquals("Ad-hoc Load", 
                        converter.convertToWorkflowCommandPaymentMethod(PaymentType.AD_HOC_LOAD.code()));
    }
    
    @Test
    public void convertToWorkflowCommandPaymentMethodShouldReturnPaymentCard() {
        assertEquals("Payment Card", 
                        converter.convertToWorkflowCommandPaymentMethod(PaymentType.PAYMENT_CARD.code()));
    }
    
    @Test
    public void convertToWorkflowCommandPaymentMethodShouldReturnParameter() {
        String testMethod = "Other method";
        assertEquals(testMethod, 
                        converter.convertToWorkflowCommandPaymentMethod(testMethod));
    }
}
