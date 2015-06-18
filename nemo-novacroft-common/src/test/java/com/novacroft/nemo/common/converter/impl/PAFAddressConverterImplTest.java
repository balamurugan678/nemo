package com.novacroft.nemo.common.converter.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getPAFFullTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getPAFFullTestAddressWithBuildingNumber;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.CommonAddressDTO;
import com.novacroft.phoenix.service.paf.bean.PAFFullAddress;

public class PAFAddressConverterImplTest {
    private PAFAddressConverterImpl converter;

    @Before
    public void setUp() {
        converter = new PAFAddressConverterImpl();
      
    }

    @Test
    public void shouldConvertEntityToDto() {
        CommonAddressDTO actualResult =  converter.convertEntityToDto(getPAFFullTestAddress1());
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void shouldConvertEntityToDtoWithBuildingNumber() {
        CommonAddressDTO actualResult =  converter.convertEntityToDto(getPAFFullTestAddressWithBuildingNumber());
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void shouldconvertEntitiesToDtos() {
        PAFFullAddress[] addresses = new PAFFullAddress[] {getPAFFullTestAddress1()};
        List<CommonAddressDTO> addressDTOs  =  converter.convertEntitiesToDtos(addresses);
        assertNotNull(addressDTOs);
        assertEquals(1, addressDTOs.size());
        assertNotNull(addressDTOs.get(0).getStreet());
    }
    
    @Test
    public void formatFullAddressShouldReturnCommonAddressDTO() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("100");
        CommonAddressDTO actualResult =  converter.formatFullAddress(fullAddress);
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void formatFullAddressWithBuildingNumberShouldReturnCommonAddressDTO() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("");
        fullAddress.setBuildingNumber(50);
        fullAddress.setOrganisationName("");
        CommonAddressDTO actualResult =  converter.formatFullAddress(fullAddress);
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void formatFullAddressWithNoneOfBuildingDetailsShouldReturnCommonAddressDTO() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("Cirrus Park");
        fullAddress.setOrganisationName("100");
        CommonAddressDTO actualResult =  converter.formatFullAddress(fullAddress);
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void formatFullAddressWithOutBuildingNameShouldReturnCommonAddressDTO() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("");
        CommonAddressDTO actualResult =  converter.formatFullAddress(fullAddress);
        assertNotNull(actualResult);
        assertNotNull(actualResult.getStreet());
    }
    
    @Test
    public void buildAddressShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("");
        fullAddress.setOrganisationDepartmentName("IT Department");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithPOBoxShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setPoBoxNumber("507117");;
        fullAddress.setOrganisationDepartmentName("IT Department");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithOnlyBuildingNumberShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddressWithBuildingNumber();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithOnlyBuildingNameShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setSubBuildingName("");
        fullAddress.setBuildingName("100");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithBuildingNameShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setSubBuildingName("");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithBuildingNameNoAndSubBuildingShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddressWithBuildingNumber();
        fullAddress.setSubBuildingName("100");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithOnlyBuildingNameAndNumberShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddressWithBuildingNumber();
        fullAddress.setSubBuildingName("");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithOnlySubBuildingNameAndNumberShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddressWithBuildingNumber();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("100");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void buildAddressWithSubBuildingNameAndNumberWithContactinationIndicatorShouldReturnAddressLine() {
        PAFFullAddress fullAddress = getPAFFullTestAddressWithBuildingNumber();
        fullAddress.setBuildingName("");
        fullAddress.setSubBuildingName("100");
        fullAddress.setConcatenationIndicator("Y");
        String[] lineAddress =   converter.buildAddress(false ,fullAddress);
        assertNotNull(lineAddress.toString());
    }
    
    @Test
    public void getBuildingNoOrNameFromBuildingNameShouldReturnBuildingNameOrNo() {
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setOrganisationName("");
        fullAddress.setSubBuildingName("");
        String buildingNameOrNo =   converter.getBuildingNoOrNameFromBuildingName(fullAddress);
        assertNotNull(buildingNameOrNo);
    }
    
    @Test
    public void shouldAddSubBuildingNameAndBuildingName() {
        List<String> lineAddress = new ArrayList<String>();
        lineAddress.add("Lower Farm Road");
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setSubBuildingName("10CirrusPark48");
        converter.addSubBuildingNameAndBuildingName(fullAddress, lineAddress);
    }
    
    @Test
    public void shouldAddBuildingNameAndSubBuildingNameWithAlphaNumericBuildingName() {
        List<String> lineAddress = new ArrayList<String>();
        lineAddress.add("Lower Farm Road");
        PAFFullAddress fullAddress = getPAFFullTestAddress1();
        fullAddress.setBuildingName("10CirrusPark48");
        converter.addSubBuildingNameAndBuildingName(fullAddress, lineAddress);
    }
    
    @Test
    public void isFormat1BuildingNameShouldReturnTrue() {
        assertTrue(converter.isFormat1BuildingName("150CirrusPar25k"));
    }

   }
