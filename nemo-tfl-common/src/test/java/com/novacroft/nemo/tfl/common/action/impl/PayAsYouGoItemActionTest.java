package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestPayAsYouGoItemDTO1;
import static com.novacroft.nemo.test_support.ItemTestUtil.getTestPayAsYouGoItemDTO2;
import static com.novacroft.nemo.test_support.PayAsYouGoTestUtil.getTestPayAsYouGoDTO1;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

public class PayAsYouGoItemActionTest {

    private PayAsYouGoItemActionImpl service;
    private CartItemCmdImpl cartItemCmd;
    private PayAsYouGoDataService mockPayAsYouGoDataService;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        service = new PayAsYouGoItemActionImpl();
        cartItemCmd = new CartItemCmdImpl();

        mockPayAsYouGoDataService = mock(PayAsYouGoDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        service.payAsYouGoDataService = mockPayAsYouGoDataService;
        service.systemParameterService = mockSystemParameterService;
    }

    @Test
    public void createItemDTOForCmdItemWithCreditBalanceShouldReturnPayAsYouGoItemDTOAndShouldCallPayAsYouGoDataService() {
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(getTestPayAsYouGoDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockPayAsYouGoDataService, atLeastOnce()).findByTicketPrice(anyInt());
        assertTrue(itemDTO instanceof PayAsYouGoItemDTO);
        assertEquals(Integer.valueOf(20), itemDTO.getPrice());
    }

    @Test
    public void createItemDTOForCmdItemWithZeroCreditBalanceAndWithAutoTopUpCreditBalanceShouldReturnPayAsYouGoItemDTOAndShouldCallPayAsYouGoDataService() {
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(getTestPayAsYouGoDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setAutoTopUpCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockPayAsYouGoDataService, atLeastOnce()).findByTicketPrice(anyInt());
        assertTrue(itemDTO instanceof PayAsYouGoItemDTO);
        assertEquals(Integer.valueOf(20), itemDTO.getPrice());
    }

    @Test
    public void createItemDTOForCmdItemWithNullPayAsYouGoDTOShouldReturnNull() {
        when(mockPayAsYouGoDataService.findByTicketPrice(anyInt())).thenReturn(null);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockPayAsYouGoDataService, atLeastOnce()).findByTicketPrice(anyInt());
        assertEquals(null, itemDTO);
    }

    @Test
    public void updateItemDTOForPayAsYouGoItemDTOShouldCopyPriceFromNewItemToItemTobeUpdated() {
        PayAsYouGoItemDTO dto1 = getTestPayAsYouGoItemDTO1();
        PayAsYouGoItemDTO dto2 = getTestPayAsYouGoItemDTO2();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertTrue(itemDTO instanceof PayAsYouGoItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnPayAsYouGoItemDTOAndShouldCallPayAsYouGoDataService() {
        when(mockPayAsYouGoDataService.findById(anyLong())).thenReturn(getTestPayAsYouGoDTO1());
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouGoItemDTO1();
        ItemDTO itemDTO = service.postProcessItemDTO(payAsYouGoItemDTO,true);
        verify(mockPayAsYouGoDataService, atLeastOnce()).findById(anyLong());
        assertEquals(getTestPayAsYouGoDTO1().getPayAsYouGoName(), itemDTO.getName());
    }

    @Test
    public void hasItemExpiredShouldReturnTrueAndShouldCallSystemParameterService() {
        when(mockSystemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)).thenReturn(10);
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouGoItemDTO1();
        payAsYouGoItemDTO.setStartDate(new Date());
        assertTrue(service.hasItemExpired(new Date(), payAsYouGoItemDTO));
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS);
    }

    @Test
    public void hasItemExpiredShouldReturnFalseAndShouldCallSystemParameterService() {
        when(mockSystemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)).thenReturn(-10);
        PayAsYouGoItemDTO payAsYouGoItemDTO = getTestPayAsYouGoItemDTO1();
        payAsYouGoItemDTO.setStartDate(new Date());
        assertFalse(service.hasItemExpired(new Date(), payAsYouGoItemDTO));
        verify(mockSystemParameterService, atLeastOnce()).getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS);
    }
    
    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        PayAsYouGoItemDTO dto1 = getTestPayAsYouGoItemDTO1();
        PayAsYouGoItemDTO dto2 = getTestPayAsYouGoItemDTO2();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof PayAsYouGoItemDTO);
    }

}
