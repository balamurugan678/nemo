package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.getHotlistReasonDTO1;
import static com.novacroft.nemo.test_support.RefundScenarioHotListReasonTypeTestUtil.getTestRefundScenarioHotListReasonType1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

public class RefundScenarioHotListReasonTypeConverterImplTest {
    private RefundScenarioHotListReasonTypeConverterImpl converter;
    private HotlistReasonConverterImpl mockHotlistReasonConverter;
    
    @Before
    public void init() {
        converter = new RefundScenarioHotListReasonTypeConverterImpl();
        mockHotlistReasonConverter = mock(HotlistReasonConverterImpl.class);
        converter.hotListReasonConverter = mockHotlistReasonConverter;
    }
    
    @Test
    public void shouldConvertEntityToDto() {
        HotlistReasonDTO mockReturnedDTO = getHotlistReasonDTO1();
        when(mockHotlistReasonConverter.convertEntityToDto(any(HotlistReason.class))).thenReturn(mockReturnedDTO);
        
        RefundScenarioHotListReasonTypeDTO actualResult = converter.convertEntityToDto(getTestRefundScenarioHotListReasonType1());
        verify(mockHotlistReasonConverter).convertEntityToDto(any(HotlistReason.class));
        assertNotNull(actualResult);
        assertEquals(mockReturnedDTO, actualResult.getHotlistReasonDTO());
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
}
