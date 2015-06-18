package com.novacroft.nemo.tfl.common.action.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO1;
import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO3;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ANNUAL_START_DATE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.application_service.AdministrationFeeService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

public class AdministrationFeeItemActionTest {

    public static final String ADMINISTRATION_FEE_FAILED_CARD_REFUND = "Administration Fee Failed Card Refund";
    private AdministrationFeeItemActionImpl service;
    private CartItemCmdImpl cartItemCmd;
    private AdministrationFeeService mockAdministrationFeeService;
    private AdministrationFeeDataService mockAdministrationFeeDataService;
    
    @Before
    public void setUp() {
        service = new AdministrationFeeItemActionImpl();
        cartItemCmd = new CartItemCmdImpl();

        mockAdministrationFeeService = mock(AdministrationFeeService.class);
        mockAdministrationFeeDataService = mock(AdministrationFeeDataService.class);
        service.administrationFeeService = mockAdministrationFeeService;
        service.administrationFeeDataService = mockAdministrationFeeDataService;
    }

    @Test
    public void createItemDTOForCmdItemShouldCallNewAdministrationFeeService() {
        when(mockAdministrationFeeService.getNewAdministrationFeeItemDTO(any(CartItemCmdImpl.class))).thenReturn(getTestAdministrationFeeItemDTO1());
        cartItemCmd.setStartDate(ANNUAL_START_DATE_1);
        cartItemCmd.setCreditBalance(20);
        cartItemCmd.setTravelCardType("");
        ItemDTO itemDTO = service.createItemDTO(cartItemCmd);
        verify(mockAdministrationFeeService, atLeastOnce()).getNewAdministrationFeeItemDTO(any(CartItemCmdImpl.class));
        assertTrue(itemDTO instanceof AdministrationFeeItemDTO);
    }

    @Test
    public void updateItemDTOForAdministrationFeeItemDTOShouldReturnItemToBeUpdatedAsIs() {
        AdministrationFeeItemDTO dto1 = getTestAdministrationFeeItemDTO1();
        AdministrationFeeItemDTO dto2 = (AdministrationFeeItemDTO) getTestAdministrationFeeItemDTO3();
        ItemDTO itemDTO = service.updateItemDTO(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof AdministrationFeeItemDTO);
    }

    @Test
    public void postProcessCartItemDTOShouldReturnAdministrationFeeItemDTO() {
        AdministrationFeeItemDTO dto1 = getTestAdministrationFeeItemDTO1();
        when(mockAdministrationFeeDataService.findById(anyLong())).thenReturn(new AdministrationFeeDTO());
        ItemDTO itemDTO = service.postProcessItemDTO(dto1,true);
        assertEquals(dto1.getId(), itemDTO.getId());
    }
    
    @Test
    public void postProcessCartItemDTOShouldSetNameInItemDTO() {
        AdministrationFeeItemDTO administrationFeeItemDTO = getTestAdministrationFeeItemDTO1();;
        AdministrationFeeDTO administrationFeeDTO = new AdministrationFeeDTO();
        administrationFeeDTO.setDescription(ADMINISTRATION_FEE_FAILED_CARD_REFUND);
        when(mockAdministrationFeeDataService.findById(anyLong())).thenReturn(administrationFeeDTO);
        ItemDTO itemDTO = service.postProcessItemDTO(administrationFeeItemDTO,true);
        assertEquals(ADMINISTRATION_FEE_FAILED_CARD_REFUND, itemDTO.getName());
    }

    @Test
    public void hasItemExpiredShouldReturnFalse() {
        AdministrationFeeItemDTO dto1 = getTestAdministrationFeeItemDTO1();
        assertFalse(service.hasItemExpired(new Date(), dto1));
    }

    @Test
    public void updateItemDTOForBackDatedAndDeceasedShouldReturnItemToBeUpdatedAsIs() {
        AdministrationFeeItemDTO dto1 = getTestAdministrationFeeItemDTO1();
        AdministrationFeeItemDTO dto2 = (AdministrationFeeItemDTO) getTestAdministrationFeeItemDTO3();
        ItemDTO itemDTO = service.updateItemDTOForBackDatedAndDeceased(dto1, dto2);
        assertEquals(dto1.getId(), itemDTO.getId());
        assertTrue(itemDTO instanceof AdministrationFeeItemDTO);
    }
}
