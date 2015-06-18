package com.novacroft.nemo.common.converter;

/**
 * Entity/DTO converter specification.
 */
public interface DtoEntityConverter<ENTITY, DTO> {

    DTO convertEntityToDto(ENTITY entity);

    ENTITY convertDtoToEntity(DTO dto, ENTITY entity);
}
