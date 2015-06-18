package com.novacroft.nemo.common.converter.impl;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.domain.SelectList;
import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Select list DTO/entity translator
 */
@Component(value = "selectListConverter")
public class SelectListConverterImpl implements DtoEntityConverter<SelectList, SelectListDTO> {

    @Autowired
    protected SelectListOptionConverterImpl selectListOptionConverter;

    @Override
    public SelectListDTO convertEntityToDto(SelectList entity) {
        SelectListDTO dto = new SelectListDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        for (SelectListOption optionEntity : entity.getOptions()) {
            dto.getOptions().add(selectListOptionConverter.convertEntityToDto(optionEntity));
        }
        return dto;
    }

    @Override
    public SelectList convertDtoToEntity(SelectListDTO dto, SelectList entity) {
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        for (SelectListOptionDTO optionDto : dto.getOptions()) {
            entity.getOptions().add(selectListOptionConverter.convertDtoToEntity(optionDto, new SelectListOption()));
        }
        return entity;
    }
}
