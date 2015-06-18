package com.novacroft.nemo.tfl.services.test_support;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.services.transfer.Cart;

public class TestSupportUtilities {
    
    private static final String testName = "Day Travelcard";
    private static final String testDescription = "1 Day Travelcard";
    private static final String testMeaning = "4 Days before expiry";
    private static final String testValue = "4";
	private static final Long CART_ID = 1l;
    
    
    public static List<LocationDTO> getTestLocationDTOList() {
        List<LocationDTO> locationDTOList = Lists.newArrayList();
        locationDTOList.add( getTestLocationDTO() );
        return locationDTOList;
    }
    
    public static LocationDTO getTestLocationDTO() {
        LocationDTO l = new LocationDTO();
        l.setId(1L);
        l.setName("Eastenders Station 1");
        l.setStatus("1");
        return l;
    }
    
    public static SelectListDTO getTestSelectListDTO1() {
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName(testName);
        selectListDTO.setDescription(testDescription);
        selectListDTO.setId(1l);
        List<SelectListOptionDTO> selectListOptionDTOList = new ArrayList<>();
        selectListOptionDTOList.add(getSelectListOptionDTO1());
        
        selectListDTO.setOptions(selectListOptionDTOList );
        return selectListDTO;
    }

    public static SelectListOptionDTO getSelectListOptionDTO1() {
        SelectListOptionDTO selectListOptionDTO = new SelectListOptionDTO();
        selectListOptionDTO.setId(11l);
        selectListOptionDTO.setMeaning(testMeaning);
        selectListOptionDTO.setValue(testValue);
        return selectListOptionDTO;
    }

	public static Cart getTestCart1() {
		Cart cart = new Cart();
		cart.setId(CART_ID);
		cart.setCustomerId(CustomerTestUtil.CUSTOMER_ID_1);
		return cart;
	}

}
