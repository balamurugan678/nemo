package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.AutoTopUpHistoryTestUtil.getAutoTopUpHistoryList;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.AutoTopUpHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;

public class ViewAutoTopUpHistoryControllerTest {

    private ViewAutoTopUpHistoryController mockController;
    private AutoTopUpHistoryService mockAutoTopUpHistoryService;
    private CardDataService mockCardDataService;
    private CartCmdImpl mockCartCmdImpl;
    private HttpSession mockHttpSession;
    
    @Before
    public void setup() {
        mockController = mock(ViewAutoTopUpHistoryController.class);
        mockAutoTopUpHistoryService = mock(AutoTopUpHistoryService.class);
        mockCardDataService = mock(CardDataService.class);
        mockController.autoTopUpHistoryService = mockAutoTopUpHistoryService;
        mockController.cardDataService = mockCardDataService;
        
        mockCartCmdImpl = mock(CartCmdImpl.class);
        mockHttpSession = mock(HttpSession.class);
    }

    @Test
    public void shouldGetAutoTopUpHistory() {
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        when(mockAutoTopUpHistoryService.getAutoTopUpHistoryForOysterCard(anyLong())).thenReturn(getAutoTopUpHistoryList());
        doCallRealMethod().when(mockController).getAutoTopUpHistory(mockCartCmdImpl, mockHttpSession);
        
        ModelAndView modelAndView = mockController.getAutoTopUpHistory(mockCartCmdImpl, mockHttpSession);
        
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockAutoTopUpHistoryService, atLeastOnce()).getAutoTopUpHistoryForOysterCard(anyLong());
        assertViewName(modelAndView, PageView.VIEW_AUTO_TOP_UP_HISTORY);
    }

}
