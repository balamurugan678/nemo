package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.ContentConverterImpl;
import com.novacroft.nemo.common.domain.Content;
import com.novacroft.nemo.common.support.NemoUserContextImpl;
import com.novacroft.nemo.common.transfer.ContentDTO;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Content converter unit tests
 */
public class ContentConverterTest {

    @Test
    public void shouldConvertEntityToDto() {
        ContentConverterImpl converter = new ContentConverterImpl();
        Content entity = new Content();
        entity.setId(99L);
        entity.setLocale("en_GB");
        entity.setContent("Test Content");
        entity.setCode("test.code");
        ContentDTO dto = converter.convertEntityToDto(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getLocale(), dto.getLocale());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getCode(), dto.getCode());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        ContentConverterImpl converter = new ContentConverterImpl();
        ContentDTO dto = new ContentDTO();
        dto.setId(99L);
        dto.setLocale("en_GB");
        dto.setContent("Test Content");
        dto.setCode("test.code");
        Content entity = new Content();
        NemoUserContextImpl nemoContext = new NemoUserContextImpl();
        converter.nemoUserContext = nemoContext;
        entity = converter.convertDtoToEntity(dto, entity);
        assertEquals(dto.getLocale(), entity.getLocale());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getCode(), entity.getCode());
    }
}
