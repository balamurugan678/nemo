package com.novacroft.nemo.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.data_access.BaseDAO;

/**
 * Specification for a data service
 */
public interface BaseDataService<ENTITY, DTO> {
    DTO findById(Long id);

    List<DTO> findAll();

    DTO createOrUpdate(DTO dto);

    List<DTO> createOrUpdateAll(List<DTO> dto);

    void delete(DTO dto);

    void setDao(BaseDAO<ENTITY> dao);

    void setConverter(DtoEntityConverter<ENTITY, DTO> converter);

    ENTITY getNewEntity();
    
    Long getInternalIdFromExternalId(Long externalId);

    DTO findByExternalId(Long externalId);
}
