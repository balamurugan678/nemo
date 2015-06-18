package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.SettlementTestUtil.REQUEST_SEQUENCE_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

public class AdHocLoadErrorStatusServiceImplTest {

    private AdHocLoadErrorStatusServiceImpl service;
    private AdHocLoadSettlementDTO mockAdHocLoadSettlementDTO;
    private ApplicationContext mockApplicationContext;

    @Before
    public void setUp() {
        this.service = mock(AdHocLoadErrorStatusServiceImpl.class);
        this.mockAdHocLoadSettlementDTO = mock(AdHocLoadSettlementDTO.class);
        this.mockApplicationContext = mock(ApplicationContext.class);
        setField(this.service, "applicationContext", this.mockApplicationContext);
    }

    @Test
    public void shouldUpdateStatusToPickUpExpired() {
        doCallRealMethod().when(this.service).updateStatusToFailed(anyInt(), anyString());
        when(this.service.findSettlement(anyInt(), anyString())).thenReturn(this.mockAdHocLoadSettlementDTO);
        doNothing().when(this.service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        doNothing().when(this.service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));

        this.service.updateStatusToFailed(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);

        verify(this.service).findSettlement(anyInt(), anyString());
        verify(this.service).createEvent(anyString(), any(AdHocLoadSettlementDTO.class), any(EventName.class));
        verify(this.service).updateStatus(any(AdHocLoadSettlementDTO.class), any(SettlementStatus.class));
    }

    @Test
    public void shouldBuildAdditionalInformation() {
        when(this.service.buildAdditionalInformation(anyString(), any(AdHocLoadSettlementDTO.class), anyString())).thenCallRealMethod();
        when(this.mockAdHocLoadSettlementDTO.getExpiresOn()).thenReturn(getAug19());
        when(this.service.getContent(anyString())).thenReturn("Item pick up Error: ");
        assertEquals("Item pick up Error: amount [0.00]; pick up tried at []; Oyster card number [100000000001]",
                this.service.buildAdditionalInformation(OYSTER_NUMBER_1, this.mockAdHocLoadSettlementDTO, ""));
    }
}