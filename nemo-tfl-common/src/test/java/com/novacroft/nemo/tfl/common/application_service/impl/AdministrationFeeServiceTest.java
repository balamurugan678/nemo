package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeTestUtil.ADMINISTRATION_FEE_PRICE_1;
import static com.novacroft.nemo.test_support.AdministrationFeeTestUtil.getTestAdministrationFeeDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Unit tests for New Administration Fee Service
 */
public class AdministrationFeeServiceTest {

    private AdministrationFeeServiceImpl service;
    private CartItemCmdImpl cartItemCmd;
    private AdministrationFeeDataService mockAdministrationFeeDataService;

    @Before
    public void setUp() {
        service = new AdministrationFeeServiceImpl();
        cartItemCmd = new CartItemCmdImpl();

        mockAdministrationFeeDataService = mock(AdministrationFeeDataService.class);

        service.administrationFeeDataService = mockAdministrationFeeDataService;
    }

    @Test
    public void getNewAdministrationFeeItemDTOShouldReturnAdministrationFeeItemDTO() {
        when(mockAdministrationFeeDataService.findByType(anyString())).thenReturn(getTestAdministrationFeeDTO1());
        cartItemCmd.setCartType("Test Cart");
        AdministrationFeeItemDTO adminFeeItemDTO = service.getNewAdministrationFeeItemDTO(cartItemCmd);
        verify(mockAdministrationFeeDataService, atLeastOnce()).findByType(anyString());
        assertEquals(ADMINISTRATION_FEE_PRICE_1, adminFeeItemDTO.getPrice());
    }

    @Test
    public void getNewAdministrationFeeItemDTOShouldReturnNull() {
        when(mockAdministrationFeeDataService.findByType(anyString())).thenReturn(null);
        AdministrationFeeItemDTO adminFeeItemDTO = service.getNewAdministrationFeeItemDTO(cartItemCmd);
        verify(mockAdministrationFeeDataService, atLeastOnce()).findByType(anyString());
        assertEquals(null, adminFeeItemDTO);
    }
    
    @Test
    public void getAdministrationFeeDTOShouldInvokeDataServiceTest(){
        service.getAdministrationFeeDTO(CartType.FAILED_CARD_REFUND.code());
        verify(mockAdministrationFeeDataService, atLeastOnce()).findByType(anyString());
    }
}
