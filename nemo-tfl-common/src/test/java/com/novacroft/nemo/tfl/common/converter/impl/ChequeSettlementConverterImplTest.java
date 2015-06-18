package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.CHEQUE_SERIAL_NUMBER;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.getTestChequeSettlement1;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.getTestChequeSettlementDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class ChequeSettlementConverterImplTest {
    private ChequeSettlementConverterImpl converter;
    private DtoEntityConverter<Address, AddressDTO> mockAddressConverter;
    
    private Address mockAddress;
    private AddressDTO mockAddressDTO;
    
    @Before
    public void setUp() {
        converter = new ChequeSettlementConverterImpl();
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
    public void convertEntityToDtoShouldThrowAssertError() {
        ChequeSettlement chequeSettlement = new ChequeSettlement();
        chequeSettlement.setAddress(null);
        converter.convertEntityToDto(chequeSettlement);
    }
    
    @Test
    public void convertEntityToDtoShouldSuccess() {
        ChequeSettlementDTO actualResult = converter.convertEntityToDto(getTestChequeSettlement1());
        
        assertNotNull(actualResult);
        assertEquals(CHEQUE_SERIAL_NUMBER, actualResult.getChequeSerialNumber());
        assertEquals(mockAddressDTO, actualResult.getAddressDTO());
    }
    
    @Test(expected=AssertionError.class)
    public void convertDtoToEntityShouldThrowAssertError() {
        ChequeSettlementDTO chequeSettlementDTO = new ChequeSettlementDTO();
        chequeSettlementDTO.setAddressDTO(null);
        converter.convertDtoToEntity(chequeSettlementDTO, new ChequeSettlement());
    }
    
    @Test
    public void convertDtoToEntityShouldSuccess() {
        ChequeSettlement actualResult = 
                        converter.convertDtoToEntity(getTestChequeSettlementDTO1(), new ChequeSettlement());
        
        assertNotNull(actualResult);
        assertEquals(CHEQUE_SERIAL_NUMBER, actualResult.getChequeSerialNumber());
        assertEquals(mockAddress, actualResult.getAddress());
    }
    
    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }
}
