package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Unit tests for View Oyster Card Controller
 */
public class ViewOysterCardControllerTest {

    private ViewOysterCardController controller;
    private HttpSession mockSession;
    private ManageCardCmd mockManageCardCmd;
    private CardDataService mockCardDataService;

    @Before
    public void setUp() {
        controller = new ViewOysterCardController();
        mockManageCardCmd = mock(ManageCardCmd.class);
        mockSession = mock(HttpSession.class);
        mockCardDataService = mock(CardDataService.class);
        controller.cardDataService = mockCardDataService;
    }

    @Test
    public void shouldShowViewOysterCard() {
        when(mockSession.getAttribute(anyString())).thenReturn(CardTestUtil.CARD_ID_1);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        ModelAndView result = controller.viewOysterCard(mockManageCardCmd, mockSession);
        assertViewName(result, PageView.VIEW_OYSTER_CARD);
        verify(mockCardDataService).findById(anyLong());
    }

    @Test
    public void shouldShowDashboard() {
        when(mockSession.getAttribute(anyString())).thenReturn(null);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        ModelAndView result = controller.viewOysterCard(mockManageCardCmd, mockSession);
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
        verify(mockCardDataService, never()).findById(anyLong());
    }
}
