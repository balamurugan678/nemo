package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.CARDID;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.getGoodwillReasonDTO1;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.getTestGoodwillReason1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class GoodwillPaymentItemConverterImplTest {
    private GoodwillPaymentItemConverterImpl converter;
    private GoodwillReasonConverterImpl mockGoodwillReasonConverter;
    
    @Before
    public void setUp() {
        converter = new GoodwillPaymentItemConverterImpl();
        mockGoodwillReasonConverter = mock(GoodwillReasonConverterImpl.class);
        converter.goodwillReasonConverter = mockGoodwillReasonConverter;
        
        when(mockGoodwillReasonConverter.convertEntityToDto(any(GoodwillReason.class)))
                .thenReturn(getGoodwillReasonDTO1());
        when(mockGoodwillReasonConverter.convertDtoToEntity(any(GoodwillReasonDTO.class), any(GoodwillReason.class)))
                .thenReturn(getTestGoodwillReason1());
    }
    
    @Test
    public void shouldConvertEntityToDto() {
        Item testItem = getGoodwillPaymentItem1();
        ItemDTO result = converter.convertEntityToDto(testItem);
        
        verify(mockGoodwillReasonConverter).convertEntityToDto(any(GoodwillReason.class));
        assertTrue(result instanceof GoodwillPaymentItemDTO);
        assertEquals(CARDID, result.getId());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        ItemDTO testItemDTO = getGoodwillPaymentItemDTOItem1();
        Item testEntity = new GoodwillPaymentItem();
        Item result = converter.convertDtoToEntity(testItemDTO, testEntity);
        
        verify(mockGoodwillReasonConverter).convertDtoToEntity(any(GoodwillReasonDTO.class), any(GoodwillReason.class));
        assertTrue(result instanceof GoodwillPaymentItem);
        assertEquals(CARDID, result.getId());
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
}
