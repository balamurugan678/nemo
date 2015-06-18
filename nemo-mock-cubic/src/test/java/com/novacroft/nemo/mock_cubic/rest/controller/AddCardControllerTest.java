package com.novacroft.nemo.mock_cubic.rest.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.test_support.AddCardTestUtil;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

public class AddCardControllerTest {

    private AddCardController controller;
    private OysterCardDetailsService mockCardDetailsService;
    private GetCardService mockGetCardService;

    @Before
    public void setUp() throws Exception {
        controller = new AddCardController();
        mockCardDetailsService = mock(OysterCardDetailsService.class);
        mockGetCardService = mock(GetCardService.class);

        controller.oysterCardDetailsService = mockCardDetailsService;
        controller.getCardService = mockGetCardService;

    }

    @Test
    public void shouldAddOysterCard() {
        AddCardResponseCmd cmd = AddCardTestUtil.createAddCardCmd(false);
        
        doNothing().when(mockCardDetailsService).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        CardInfoResponseV2DTO cardInfoResponseDTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseDTO.setPrestigeId(cmd.getPrestigeId());
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseDTO);
        CardInfoResponseV2 response = controller.addCard(cmd);
        
        assertNotNull(response);
        assertNotNull(response.getPrestigeId());

        verify(mockCardDetailsService, times(1)).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        verify(mockGetCardService, times(1)).getCard(anyString());
    }
    
    @Test
    public void shouldAddOysterCardSetDefaultValues() {
        AddCardResponseCmd cmd = new AddCardResponseCmd();
        cmd.setPrestigeId(AddCardTestUtil.PRESTIGE_ID);
        
        doNothing().when(mockCardDetailsService).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        CardInfoResponseV2DTO cardInfoResponseDTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfoResponseDTO.setPrestigeId(cmd.getPrestigeId());
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfoResponseDTO);
        CardInfoResponseV2 response = controller.addCard(cmd);
        
        assertNotNull(response);
        assertNotNull(response.getPrestigeId());

        verify(mockCardDetailsService, times(1)).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        verify(mockGetCardService, times(1)).getCard(anyString());
    }

    @Test
    public void shouldNotAddOysterCardIfPrestigeIdIsNull() {
        AddCardResponseCmd cmd = AddCardTestUtil.getAddCardResponseWithBlankPrestigeId();
        cmd.setPrestigeId(null);
        CardInfoResponseV2 response = controller.addCard(cmd);
        assertNotNull(response);
        assertNull(response.getPrestigeId());
        verify(mockCardDetailsService, never()).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        verify(mockGetCardService, never()).getCard(anyString());
    }

    @Test
    public void shouldNotAddOysterCardIfPrestigeIdIsBlank() {
        AddCardResponseCmd cmd = AddCardTestUtil.getAddCardResponseWithBlankPrestigeId();

        CardInfoResponseV2 response = controller.addCard(cmd);
        assertNotNull(response);
        assertNull(response.getPrestigeId());
        verify(mockCardDetailsService, never()).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        verify(mockGetCardService, never()).getCard(anyString());
    }

    @Test
    public void shouldNotAddOysterCardIfCmdIsNull() {
        CardInfoResponseV2 response = controller.addCard(null);
        assertNotNull(response);
        assertNull(response.getPrestigeId());
        verify(mockCardDetailsService, never()).createOrUpdateOysterCard(any(AddCardResponseCmd.class));
        verify(mockGetCardService, never()).getCard(anyString());
    }

}
