package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.SelectListOptionConverterImpl;
import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for select list option converter
 */
public class SelectListOptionConverterTest {
    @Test
    public void shouldConvertEntityToDto() {
        SelectListOptionConverterImpl converter = new SelectListOptionConverterImpl();
        SelectListOption entity = new SelectListOption();
        entity.setId(99L);
        entity.setValue("test-value");
        SelectListOptionDTO dto = converter.convertEntityToDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getValue(), dto.getValue());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        SelectListOptionConverterImpl converter = new SelectListOptionConverterImpl();
        SelectListOptionDTO dto = new SelectListOptionDTO();
        dto.setId(99L);
        dto.setValue("test-value");
        SelectListOption entity = new SelectListOption();
        entity = converter.convertDtoToEntity(dto, entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getValue(), entity.getValue());
    }

    @Test
    public void shouldGetNewDTO() {
        SelectListOptionConverterImpl converter = new SelectListOptionConverterImpl();
        SelectListOptionDTO optionDTO = converter.getNewDto();
        assertTrue(optionDTO instanceof SelectListOptionDTO);
    }
}
