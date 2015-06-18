package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.CardRefundableDepositItemTestUtil.getTestCardRefundableDepositItemDTO1;
import static com.novacroft.nemo.test_support.CardRefundableDepositItemTestUtil.getTestCardRefundableDepositItemDTO2;
import static com.novacroft.nemo.test_support.CardRefundableDepositTestUtil.CARD_REFUNDABLE_DEPOSIT_AMOUNT_1;
import static com.novacroft.nemo.test_support.CardRefundableDepositTestUtil.getTestCardRefundableDepositDTO1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardRefundableDepositDataService;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class CardRefundableDepositItemActionTest {

    private CardRefundableDepositItemActionImpl service;
    private CartItemCmdImpl cartItemCmd;
    private CardRefundableDepositDataService mockCardRefundableDepositDataService;

    @Before
    public void setUp() {
        service = new CardRefundableDepositItemActionImpl();
        cartItemCmd = new CartItemCmdImpl();

        mockCardRefundableDepositDataService = mock(CardRefundableDepositDataService.class);

        service.cardRefundableDepositDataService = mockCardRefundableDepositDataService;

    }

    @Test
    public void createItemDTOForCmdItemShouldCallCardRefundableDepositDataService() {
        when(mockCardRefundableDepositDataService.findRefundableDepositAmount()).thenReturn(getTestCardRefundableDepositDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockCardRefundableDepositDataService, atLeastOnce()).findRefundableDepositAmount();
        assertTrue(itemDTO instanceof CardRefundableDepositItemDTO);
        assertEquals(CARD_REFUNDABLE_DEPOSIT_AMOUNT_1, itemDTO.getPrice());
    }

    @Test
    public void updateItemDTOForCardRefundableDepositItemDTOShouldReturnItemToBeUpdatedAsIs() {
        CardRefundableDepositItemDTO dto1 = getTestCardRefundableDepositItemDTO1();
        CardRefundableDepositItemDTO dto2 = getTestCardRefundableDepositItemDTO2();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof CardRefundableDepositItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnCardRefundableDepositItemDTO() {
        CardRefundableDepositItemDTO dto1 = getTestCardRefundableDepositItemDTO1();
        ItemDTO itemDTO = service.postProcessItemDTO(dto1,true);
        assertEquals(dto1.getId(), itemDTO.getId());
    }

    @Test
    public void hasItemExpiredShouldReturnFalse() {
        CardRefundableDepositItemDTO dto1 = getTestCardRefundableDepositItemDTO1();
        assertFalse(service.hasItemExpired(new Date(), dto1));
    }

    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        CardRefundableDepositItemDTO dto1 = getTestCardRefundableDepositItemDTO1();
        CardRefundableDepositItemDTO dto2 = getTestCardRefundableDepositItemDTO2();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof CardRefundableDepositItemDTO);
    }
}
