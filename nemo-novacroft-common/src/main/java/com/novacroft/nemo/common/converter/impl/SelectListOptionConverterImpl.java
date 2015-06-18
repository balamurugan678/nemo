package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.stereotype.Component;

/**
 * Translate between select list DTO and entity
 */
@Component(value = "selectListOptionConverter")
public class SelectListOptionConverterImpl extends BaseDtoEntityConverterImpl<SelectListOption, SelectListOptionDTO> {
    @Override
    public SelectListOptionDTO convertEntityToDto(SelectListOption entity) {
        SelectListOptionDTO dto = new SelectListOptionDTO();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        dto.setDisplayOrder(entity.getDisplayOrder());
        return dto;
    }

    @Override
    public SelectListOption convertDtoToEntity(SelectListOptionDTO dto, SelectListOption entity) {
        entity.setId(dto.getId());
        entity.setValue(dto.getValue());
        entity.setDisplayOrder(dto.getDisplayOrder());
        return entity;
    }

    @Override
	public SelectListOptionDTO getNewDto() {
        return new SelectListOptionDTO();
    }
}
