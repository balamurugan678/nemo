package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.getGoodwillReasonDTOList;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.TYPE;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.MESSAGE;
import static org.junit.Assert.assertTrue;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;

import com.novacroft.nemo.tfl.common.data_service.GoodwillReasonDataService;

public class GoodwillServiceImplTest {
    private GoodwillServiceImpl goodwillService;
    private GoodwillReasonDataService mockGoodwillReasonDataService;
    private MessageSource mockMessageSource;

    @Before
    public void setUp() {
        goodwillService = new GoodwillServiceImpl();
        mockGoodwillReasonDataService = mock(GoodwillReasonDataService.class);
        mockMessageSource = mock(MessageSource.class);
        goodwillService.goodwillReasonDataService = mockGoodwillReasonDataService;
        goodwillService.messageSource = mockMessageSource;
    }

    @Test
    public void shouldGetGoodwillRefundTypesWhenGoodwillReasonDTOListNotEmpty() {
        when(mockGoodwillReasonDataService.findByType(anyString())).thenReturn(getGoodwillReasonDTOList());
        goodwillService.getGoodwillRefundTypes(TYPE);
        assertTrue(mockGoodwillReasonDataService.findByType(anyString()).size() > 0);
        verify(mockGoodwillReasonDataService, atLeastOnce()).findByType(anyString());        
    }

    @Test
    public void shouldGetGoodwillRefundExtraValidationMessages() {
        when(mockGoodwillReasonDataService.findByType(anyString())).thenReturn(getGoodwillReasonDTOList());
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class))).thenReturn(MESSAGE);
        goodwillService.getGoodwillRefundExtraValidationMessages(TYPE);
        assertTrue(mockGoodwillReasonDataService.findByType(anyString()).size() > 0);
        verify(mockGoodwillReasonDataService, atLeastOnce()).findByType(anyString());
        verify(mockMessageSource, atLeastOnce()).getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class));
    }

}
