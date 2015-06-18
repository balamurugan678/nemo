package com.novacroft.nemo.mock_cubic.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.command.StationCmd;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;

public class StationControllerTest {

    private StationController controller;
    private BindingResult result;
    private StationCmd cmd;
    private UpdateCardService updateCardService;
    private GetCardService getCardService;

    @Before
    public void setUp() throws Exception {
        controller = new StationController();
        result = mock(BindingResult.class);
        cmd = new StationCmd();
        updateCardService = mock(UpdateCardService.class);
        getCardService = mock(GetCardService.class);

        controller.updateCardService = updateCardService;
        controller.getCardService = getCardService;
    }

    @Test
    public void shouldAddPending() {
        when(updateCardService.populatePrePayTicketFromPendingTicket2(anyString())).thenReturn("");
        String message = controller.addPending(cmd, result);
        assertNotNull(message);
    }

    @Test
    public void shouldGetCard() {
        when(getCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        cmd.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        String cardInforResponseDTO = controller.getCard(cmd, result);
        assertNotNull(cardInforResponseDTO);
    }

    @Test
    public void shouldLoad() {
        ModelAndView modelAndView = controller.load();
        assertNotNull(modelAndView);
        assertEquals(StationController.VIEW, modelAndView.getViewName());
    }

    @Test
    public void shouldLoadMain() {
        ModelAndView modelAndView = controller.loadMain();
        assertNotNull(modelAndView);
        assertEquals("MainView", modelAndView.getViewName());
    }

}
