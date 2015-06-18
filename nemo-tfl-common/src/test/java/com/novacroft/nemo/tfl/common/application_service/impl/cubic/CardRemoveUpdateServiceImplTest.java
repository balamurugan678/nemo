package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.cubic.CardRemoveUpdateDataService;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardRemoveUpdateRequestDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CubicTestUtil.*;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.CUBIC_USERID;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CardRemoveUpdateServiceImplTest {

    private CardRemoveUpdateServiceImpl cardRemoveUpdateServiceImpl;
    private CardRemoveUpdateServiceImpl mockService;

    private SystemParameterService systemParameterService;
    private CardRemoveUpdateDataService cardRemoveUpdateDataService;
    private CubicServiceAccess mockCubicServiceAccess;

    @Before
    public void setup() {
        cardRemoveUpdateServiceImpl = new CardRemoveUpdateServiceImpl();
        systemParameterService = mock(SystemParameterService.class);
        cardRemoveUpdateDataService = mock(CardRemoveUpdateDataService.class);
        mockService = mock(CardRemoveUpdateServiceImpl.class);
        mockCubicServiceAccess = mock(CubicServiceAccess.class);

        cardRemoveUpdateServiceImpl.systemParameterService = systemParameterService;
        cardRemoveUpdateServiceImpl.cardRemoveUpdateDataService = cardRemoveUpdateDataService;
        cardRemoveUpdateServiceImpl.cubicServiceAccess = mockCubicServiceAccess;

        mockService.systemParameterService = systemParameterService;
        mockService.cardRemoveUpdateDataService = cardRemoveUpdateDataService;
        mockService.cubicServiceAccess = mockCubicServiceAccess;
    }

    @Test
    public void removePendingUpdate() {
        when(systemParameterService.getParameterValue(CUBIC_USERID.code())).thenReturn(CUBIC_USER_ID);
        when(systemParameterService.getParameterValue(SystemParameterCode.CUBIC_PASSWORD.code())).thenReturn(CUBIC_PASSWORD);
        when(cardRemoveUpdateDataService.removePendingUpdate((CardRemoveUpdateRequestDTO) any()))
                .thenReturn(getTestResponseDTO1());
        CardUpdateResponseDTO responseDTO = cardRemoveUpdateServiceImpl.removePendingUpdate("", 12L);
        assertNotNull(responseDTO);
        assertNull(responseDTO.getErrorCode());

    }

    @Test
    public void removePendingUpdateErrorResponse() {
        when(systemParameterService.getParameterValue(CUBIC_USERID.code())).thenReturn(CUBIC_USER_ID);
        when(systemParameterService.getParameterValue(SystemParameterCode.CUBIC_PASSWORD.code())).thenReturn(CUBIC_PASSWORD);
        when(cardRemoveUpdateDataService.removePendingUpdate((CardRemoveUpdateRequestDTO) any()))
                .thenReturn(getTestErrorResponseDTO1());
        CardUpdateResponseDTO responseDTO = cardRemoveUpdateServiceImpl.removePendingUpdate("", 1L);
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getErrorCode());
    }

    @Test
    public void removePendingUpdateStringCall() {
        doCallRealMethod().when(mockService).removePendingUpdate(anyString());
        when(this.mockCubicServiceAccess.callCubic(anyString())).thenReturn(new StringBuffer());
        mockService.removePendingUpdate(anyString());
        verify(this.mockCubicServiceAccess).callCubic(anyString());
    }
}
