package com.novacroft.nemo.tfl.common.application_service.impl;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.novacroft.nemo.test_support.EmailTestUtil;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.EmailTestUtil.FORMATTED_PICKUP_EXPIRE_DATE_IN_EMAIL;
import static com.novacroft.nemo.test_support.EmailTestUtil.FORMATTED_REFUND_AMOUNT_IN_EMAIL;
import static com.novacroft.nemo.test_support.RefundTestUtil.PICK_UP_LOCATION_NAME_1;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.junit.Assert.assertEquals;

public class RefundPickUpWindowExpiredEmailServiceImplTest {
    
    private RefundPickUpWindowExpiredEmailServiceImpl mockService;
    
    @Before
    public void setUp() {
        mockService = mock(RefundPickUpWindowExpiredEmailServiceImpl.class);
    }
    
    @Test
    public void testGetSubject() {
        when(mockService.getSubject(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(mockService.getContent(anyString())).then(returnsFirstArg());
        assertEquals(ContentCode.REFUND_PICK_UP_WINDOW_EXPIRED_EMAIL_SUBJECT.textCode(), 
                        mockService.getSubject(null));
    }
    
    @Test
    public void testGetBody() {
        when(mockService.getBody(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(mockService.getContent(anyString(), (String) anyVararg()))
            .then(new Answer<String>() {

                @Override
                public String answer(InvocationOnMock invocation) throws Throwable {
                    return StringUtils.join(invocation.getArguments());
                }
            });
        
        String expectedResult = ContentCode.REFUND_PICK_UP_WINDOW_EXPIRED_EMAIL_BODY.textCode() +
                        FORMATTED_REFUND_AMOUNT_IN_EMAIL + 
                        PICK_UP_LOCATION_NAME_1 + 
                        OYSTER_NUMBER_1 + 
                        FORMATTED_PICKUP_EXPIRE_DATE_IN_EMAIL;
                        
        assertEquals(expectedResult, 
                        mockService.getBody(EmailTestUtil.getTestRefundEmailArgumentDTO()));
    }
}
