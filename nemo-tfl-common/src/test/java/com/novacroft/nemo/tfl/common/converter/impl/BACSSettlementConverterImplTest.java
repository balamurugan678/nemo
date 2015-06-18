package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

public class BACSSettlementConverterImplTest {
    private BACSSettlementConverterImpl converter;
    private DtoEntityConverter<Address, AddressDTO> mockAddressConverter;
    
    private Address mockAddress;
    private AddressDTO mockAddressDTO;
    
    @Before
    public void setUp() {
        converter = new BACSSettlementConverterImpl();
        mockAddressConverter = mock(DtoEntityConverter.class);
        converter.addressConverter = mockAddressConverter;
        
        mockAddress = getTestAddress1();
        mockAddressDTO = getTestAddressDTO1();
        when(mockAddressConverter.convertEntityToDto(any(Address.class)))
                .thenReturn(mockAddressDTO);
        when(mockAddressConverter.convertDtoToEntity(any(AddressDTO.class), any(Address.class)))
                .thenReturn(mockAddress);
    }
    
    @Test(expected=AssertionError.class)
    public void convertEntityToDtoShouldAssertError() {
        BACSSettlement testSettlement = new BACSSettlement();
        testSettlement.setAddress(null);
        
        converter.convertEntityToDto(testSettlement);
    }
    
    @Test
    public void convertEntityToDtoShouldReturnDTO() {
        BACSSettlement testSettlement = new BACSSettlement();
        testSettlement.setAddress(mockAddress);
        BACSSettlementDTO actualResult = converter.convertEntityToDto(testSettlement);
        
        verify(mockAddressConverter).convertEntityToDto(any(Address.class));
        assertNotNull(actualResult);
        assertEquals(mockAddressDTO, actualResult.getAddressDTO());
    }
    
    @Test(expected=AssertionError.class)
    public void convertDtoToEntityShouldAssertError() {
        BACSSettlementDTO testDTO = new BACSSettlementDTO();
        testDTO.setAddressDTO(null);
        
        converter.convertDtoToEntity(testDTO, new BACSSettlement());
    }
    
    @Test
    public void convertDtoToEntityShouldReturnEntity() {
        BACSSettlementDTO testDTO = new BACSSettlementDTO();
        testDTO.setAddressDTO(mockAddressDTO);
        
        BACSSettlement actualResult = converter.convertDtoToEntity(testDTO, new BACSSettlement());
        
        verify(mockAddressConverter).convertDtoToEntity(any(AddressDTO.class), any(Address.class));
        assertNotNull(actualResult);
        assertEquals(mockAddress, actualResult.getAddress());
    }
}
