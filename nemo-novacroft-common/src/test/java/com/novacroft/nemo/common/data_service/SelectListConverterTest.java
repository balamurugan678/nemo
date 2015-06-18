package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.SelectListConverterImpl;
import com.novacroft.nemo.common.converter.impl.SelectListOptionConverterImpl;
import com.novacroft.nemo.common.domain.SelectList;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import org.junit.Test;

import static com.novacroft.nemo.test_support.SelectListTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Unit tests for select list converter
 */
public class SelectListConverterTest {

    @Test
    public void shouldConvertEntityToDto() {
        SelectListConverterImpl selectListConverter = new SelectListConverterImpl();
        SelectListOptionConverterImpl selectListOptionConverter = new SelectListOptionConverterImpl();
        setField(selectListConverter, "selectListOptionConverter", selectListOptionConverter);

        SelectList entity = getTestSelectList1();

        SelectListDTO dto = selectListConverter.convertEntityToDto(entity);

        assertEquals(TEST_LIST_1_ID, dto.getId());
        assertEquals(TEST_LIST_1_NAME, dto.getName());
        assertEquals(TEST_LIST_1_DESCRIPTION, dto.getDescription());

        assertEquals(2, dto.getOptions().size());
        assertTrue(dto.getOptions().contains(getTestSelectListOptionDTO1()));
        assertTrue(dto.getOptions().contains(getTestSelectListOptionDTO2()));
    }

    @Test
    public void shouldConvertDtoToEntity() {
        SelectListConverterImpl selectListConverter = new SelectListConverterImpl();
        SelectListOptionConverterImpl selectListOptionConverter = new SelectListOptionConverterImpl();
        setField(selectListConverter, "selectListOptionConverter", selectListOptionConverter);

        SelectListDTO dto = getTestSelectListDTO();
        SelectList entity = selectListConverter.convertDtoToEntity(dto, new SelectList());

        assertEquals(TEST_LIST_1_ID, entity.getId());
        assertEquals(TEST_LIST_1_NAME, entity.getName());
        assertEquals(TEST_LIST_1_DESCRIPTION, entity.getDescription());

        assertEquals(2, entity.getOptions().size());
        assertTrue(entity.getOptions().contains(getTestSelectListOption1()));
        assertTrue(entity.getOptions().contains(getTestSelectListOption2()));
    }
}
