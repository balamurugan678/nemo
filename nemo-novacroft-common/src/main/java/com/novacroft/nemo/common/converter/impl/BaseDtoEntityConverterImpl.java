package com.novacroft.nemo.common.converter.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.domain.BaseEntity;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.BaseDTO;
import com.novacroft.nemo.common.utils.Converter;

/**
 * Base DTO class to automatically convert Entity to DTO.
 * This will look for the getters in the Entity and the setters in the DTO and assign the values from the Entity to the DTO.
 *
 * @param <ENTITY>
 * @param <DTO>
 * 
 */
public abstract class BaseDtoEntityConverterImpl<ENTITY extends BaseEntity, DTO extends BaseDTO> implements DtoEntityConverter<ENTITY, DTO> {
    static final Logger logger = LoggerFactory.getLogger(BaseDtoEntityConverterImpl.class);
    
    @Autowired
    public NemoUserContext nemoUserContext;

    @SuppressWarnings("unchecked")
    public DTO convertEntityToDto(ENTITY entity) {
        return (DTO) Converter.convert(entity, getNewDto());
    }

    @SuppressWarnings("unchecked")
    @Override
    public ENTITY convertDtoToEntity(DTO dto, ENTITY entity) {
    	  Converter.convert(dto, entity);
    	  if (null == dto.getId()) {
              entity.setCreatedDateTime(new Date());
              entity.setCreatedUserId(getUserName());
          } else {
              entity.setModifiedDateTime(new Date());
              entity.setModifiedUserId(getUserName());
          }
    	  return  entity;
    }
    

    protected String getUserName(){
    	return this.nemoUserContext != null ? this.nemoUserContext.getUserName() : null;
    }
    protected abstract DTO getNewDto();
}

