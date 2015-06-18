package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.CONTENT_CODE_AUTO_TOPUP;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.CONTENT_CODE_AUTO_TOPUP_NAME;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_ACTIVITY;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUpDTO1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUpItemDTO1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUpItemDTO2;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpDataService;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class AutoTopUpConfigurationItemActionTest {

    private AutoTopUpConfigurationItemActionImpl service;
    private AutoTopUpConfigurationItemActionImpl mockService;
    private CartItemCmdImpl cartItemCmd;
    private CartItemCmdImpl mockCartItemCmd;
    private AutoTopUpDataService mockAutoTopUpDataService;
    private ApplicationContext mockApplicationContext;

    @Before
    public void setUp() {
        service = new AutoTopUpConfigurationItemActionImpl();
        mockService = mock(AutoTopUpConfigurationItemActionImpl.class);
        cartItemCmd = new CartItemCmdImpl();
        mockCartItemCmd = mock(CartItemCmdImpl.class);

        mockAutoTopUpDataService = mock(AutoTopUpDataService.class);
        mockApplicationContext = mock(ApplicationContext.class);

        service.autoTopUpDataService = mockAutoTopUpDataService;
        service.applicationContext = mockApplicationContext;

        mockService.autoTopUpDataService = mockAutoTopUpDataService;
        mockService.applicationContext = mockApplicationContext;
    }

    @Test
    public void createItemDTOForCmdItemShouldCallAutoTopUpDataService() {
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(getTestAutoTopUpDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setStationId(20L);
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockAutoTopUpDataService, atLeastOnce()).findByAutoTopUpAmount(anyInt());
        assertTrue(itemDTO instanceof AutoTopUpConfigurationItemDTO);
    }

    @Test
    public void createItemDTOForCmdItemShouldNotCallAutoTopUpDataService() {
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(getTestAutoTopUpDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setStationId(20L);
        cartItemCmd.setAutoTopUpActivity(AUTO_TOP_UP_ACTIVITY);
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockAutoTopUpDataService, atLeastOnce()).findByAutoTopUpAmount(anyInt());
        assertTrue(itemDTO instanceof AutoTopUpConfigurationItemDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createItemDTOForCmdItemShouldThrowIllegalArgumentExceptionWhenAutoTopUpDataServiceReturnsNull() {
        when(mockAutoTopUpDataService.findByAutoTopUpAmount(anyInt())).thenReturn(null);
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setStationId(20L);
        service.createItemDTO(cartItemCmd);
        verify(mockAutoTopUpDataService, atLeastOnce()).findByAutoTopUpAmount(anyInt());
    }

    @Test
    public void updateItemDTOForAutoTopUpItemDTOShouldReturnItemToBeUpdatedAsIs() {
        AutoTopUpConfigurationItemDTO dto1 = getTestAutoTopUpItemDTO1();
        AutoTopUpConfigurationItemDTO dto2 = getTestAutoTopUpItemDTO2();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof AutoTopUpConfigurationItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnAutoTopUpItemDTOShouldCallApplicationContext() {
        when(mockApplicationContext.getMessage(CONTENT_CODE_AUTO_TOPUP, null, null)).thenReturn(CONTENT_CODE_AUTO_TOPUP_NAME);
        AutoTopUpConfigurationItemDTO dto1 = getTestAutoTopUpItemDTO1();
        ItemDTO itemDTO = service.postProcessItemDTO(dto1,true);
        assertEquals(CONTENT_CODE_AUTO_TOPUP_NAME, itemDTO.getName());
    }

    @Test
    public void hasItemExpiredShouldReturnFalse() {
        AutoTopUpConfigurationItemDTO dto1 = getTestAutoTopUpItemDTO1();
        assertFalse(service.hasItemExpired(DateTestUtil.get1Jan(), dto1));
    }

    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        AutoTopUpConfigurationItemDTO dto1 = getTestAutoTopUpItemDTO1();
        AutoTopUpConfigurationItemDTO dto2 = getTestAutoTopUpItemDTO2();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof AutoTopUpConfigurationItemDTO);
    }
}