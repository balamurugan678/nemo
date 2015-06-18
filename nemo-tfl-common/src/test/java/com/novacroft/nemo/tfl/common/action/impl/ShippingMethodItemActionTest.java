package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1;
import static com.novacroft.nemo.test_support.GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem2;
import static com.novacroft.nemo.test_support.ShippingMethodItemTestUtil.getTestShippingMethodItemDTO1;
import static com.novacroft.nemo.test_support.ShippingMethodItemTestUtil.getTestShippingMethodItemDTO2;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.getTestShippingMethodDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.ShippingMethodDataService;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

public class ShippingMethodItemActionTest {

    private ShippingMethodItemActionImpl service;
    private CartItemCmdImpl cartItemCmd;
    private ShippingMethodDataService mockShippingMethodDataService;

    @Before
    public void setUp() {
        service = new ShippingMethodItemActionImpl();
        cartItemCmd = new CartItemCmdImpl();
        mockShippingMethodDataService = mock(ShippingMethodDataService.class);
        service.shippingMethodDataService = mockShippingMethodDataService;
    }

    @Test
    public void createItemDTOForCmdItemShouldReturnShippingMethodItemDTOAndShouldCallShippingMethodDataService() {
        when(mockShippingMethodDataService.findByShippingMethodName(anyString(), anyBoolean())).thenReturn(getTestShippingMethodDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockShippingMethodDataService, atLeastOnce()).findByShippingMethodName(anyString(), anyBoolean());
        assertTrue(itemDTO instanceof ShippingMethodItemDTO);
        assertEquals(getTestShippingMethodDTO1().getPrice(), itemDTO.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createItemDTOForCmdItemShouldThrowIllegalArgumentExceptionWhenShippingMethodDataServiceReturnsNull() {
        when(mockShippingMethodDataService.findByShippingMethodName(anyString(), anyBoolean())).thenReturn(null);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setStationId(20L);
        service.createItemDTO(cartItemCmd);
        verify(mockShippingMethodDataService, atLeastOnce()).findByShippingMethodName(anyString(), anyBoolean());
    }

    @Test
    public void updateItemDTOForShippingMethodItemDTOShouldCopyPriceFromNewItemToItemTobeUpdated() {
        ShippingMethodItemDTO dto1 = getTestShippingMethodItemDTO1();
        ShippingMethodItemDTO dto2 = getTestShippingMethodItemDTO2();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertEquals(dto1.getPrice(), dto2.getPrice());
        assertTrue(itemDTO instanceof ShippingMethodItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnShippingMethodItemDTOAndShouldCallPayAsYouGoDataService() {
        when(mockShippingMethodDataService.findById(anyLong())).thenReturn(getTestShippingMethodDTO1());
        ShippingMethodItemDTO shippingMethodItemDTO = getTestShippingMethodItemDTO1();
        ItemDTO itemDTO = service.postProcessItemDTO(shippingMethodItemDTO,true);
        verify(mockShippingMethodDataService, atLeastOnce()).findById(anyLong());
        assertEquals(getTestShippingMethodDTO1().getName(), itemDTO.getName());
    }

    @Test
    public void postProcessCartItemDTOShouldReturnShippingMethodItemDTOWithDifferentNameWhenShippingMethodDataServiceReturnsNull() {
        when(mockShippingMethodDataService.findById(anyLong())).thenReturn(null);
        ShippingMethodItemDTO shippingMethodItemDTO = getTestShippingMethodItemDTO1();
        shippingMethodItemDTO.setName("Test");
        ItemDTO itemDTO = service.postProcessItemDTO(shippingMethodItemDTO,true);
        verify(mockShippingMethodDataService, atLeastOnce()).findById(anyLong());
        assertEquals("Test", itemDTO.getName());
    }

    @Test
    public void hasItemExpiredShouldReturnFalseAndShouldCallSystemParameterService() {
        ShippingMethodItemDTO shippingMethodItemDTO = getTestShippingMethodItemDTO1();
        assertFalse(service.hasItemExpired(new Date(), shippingMethodItemDTO));
    }

    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        ShippingMethodItemDTO dto1 = getTestShippingMethodItemDTO1();
        ShippingMethodItemDTO dto2 = getTestShippingMethodItemDTO2();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof ShippingMethodItemDTO);
    }
}
