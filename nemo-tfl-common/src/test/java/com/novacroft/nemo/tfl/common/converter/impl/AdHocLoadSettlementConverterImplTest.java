package com.novacroft.nemo.tfl.common.converter.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;

public class AdHocLoadSettlementConverterImplTest {
    
    private AdHocLoadSettlementConverterImpl converter;
    private ItemConverterImpl itemConverter;
    private ProductItemConverterImpl productItemConverter;

    @Before
    public void setUp() throws Exception {
        converter = new AdHocLoadSettlementConverterImpl();
        itemConverter = new ItemConverterImpl();
        productItemConverter = new ProductItemConverterImpl();
        
        itemConverter.productItemConverter = productItemConverter;
        
        converter.itemConverterImpl = itemConverter;
    }

    @Test
    public void shouldConvertEntityToDto() {
        AdHocLoadSettlementDTO dto = converter.convertEntityToDto(SettlementTestUtil.getTestAdHocLoadSettlement1());
        assertEquals(SettlementTestUtil.getTestAdHocLoadSettlementDTO1().getPickUpNationalLocationCode(), dto.getPickUpNationalLocationCode());
    }
    
    @Test
    public void shouldConvertDtoToEntity(){
        AdHocLoadSettlement settlement = SettlementTestUtil.getTestAdHocLoadSettlement1();
        AdHocLoadSettlement convertedSettlement = converter.convertDtoToEntity(SettlementTestUtil.getTestAdHocLoadSettlementDTO1(), settlement);
        assertEquals(settlement.getPickUpNationalLocationCode(), convertedSettlement.getPickUpNationalLocationCode());
    }
    
    @Test
    public void shouldConvertEntityToDtoWithItem() {
        AdHocLoadSettlement entity = SettlementTestUtil.getTestAdHocLoadSettlement1();
        entity.setItem(ItemTestUtil.getTestProductItem());
        AdHocLoadSettlementDTO expectedResult = SettlementTestUtil.getTestAdHocLoadSettlementDTO1();
     
        AdHocLoadSettlementDTO result = converter.convertEntityToDto(entity);
        assertEquals(expectedResult.getPickUpNationalLocationCode(), result.getPickUpNationalLocationCode());
        assertNotNull(result.getItem());
    }
    
    @Test
    public void shouldConvertDtoToEntityWithItem(){
        AdHocLoadSettlementDTO dto = SettlementTestUtil.getTestAdHocLoadSettlementDTO1();
        dto.setItem(ItemTestUtil.getTestProductItemDTO1());
        
        AdHocLoadSettlement expectedResult = SettlementTestUtil.getTestAdHocLoadSettlement1();
        AdHocLoadSettlement result = converter.convertDtoToEntity(dto, new AdHocLoadSettlement());
        
        assertEquals(expectedResult.getPickUpNationalLocationCode(), result.getPickUpNationalLocationCode());
        assertNotNull(result.getItem());
    }

}
