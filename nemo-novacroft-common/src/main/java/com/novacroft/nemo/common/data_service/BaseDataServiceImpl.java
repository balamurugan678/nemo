package com.novacroft.nemo.common.data_service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.converter.DtoEntityConverter;
import com.novacroft.nemo.common.data_access.BaseDAO;
import com.novacroft.nemo.common.domain.BaseEntity;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.BaseDTO;

/**
 * Base implementation for a data service
 */
public abstract class BaseDataServiceImpl<ENTITY extends BaseEntity, DTO extends BaseDTO> implements
		BaseDataService<ENTITY, DTO> {

	protected static final String CURRENT_DATE_PLACE_HOLDER = " AND $.effectiveFrom <= CURRENT_DATE() and ($.effectiveTo is null or $.effectiveTo >= CURRENT_DATE()) ";
	protected static final String NAMED_DATE_PLACE_HOLDER = " AND $.effectiveFrom <= :effectiveDate and ($.effectiveTo is null or $.effectiveTo >= :effectiveDate) ";

	protected BaseDAO<ENTITY> dao;
	protected DtoEntityConverter<ENTITY, DTO> converter;
	@Autowired
	protected NemoUserContext nemoUserContext;

	@Override
	@Transactional(readOnly = true)
	public DTO findById(Long id) {
		return converter.convertEntityToDto(dao.findById(id));
	}

	@Override
	@Transactional(readOnly = true)
	public Long getInternalIdFromExternalId(Long externalId) {
		return dao.getInternalIdFromExternalId(externalId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DTO> findAll() {
		List<DTO> results = new ArrayList<DTO>();
		for (ENTITY entity : dao.findAll()) {
			results.add(converter.convertEntityToDto(entity));
		}
		return results;
	}

	@Override
	@Transactional
	public DTO createOrUpdate(DTO dto) {
		ENTITY entity = (null != dto.getId()) ? dao.findById(dto.getId()) : getNewEntity();
		entity = converter.convertDtoToEntity(dto, entity);
		if (null == dto.getId()) {
			entity.setCreatedDateTime(new Date());
			entity.setCreatedUserId(this.nemoUserContext.getUserName());
		} else {
			entity.setModifiedDateTime(new Date());
			entity.setModifiedUserId(this.nemoUserContext.getUserName());
		}
		entity = dao.createOrUpdate(entity);
		return converter.convertEntityToDto(entity);
	}

	@Override
	@Transactional
	public List<DTO> createOrUpdateAll(List<DTO> dtos) {
		for (DTO dto : dtos) {
			createOrUpdate(dto);
		}
		return dtos;
	}

	@Override
	@Transactional
	public void delete(DTO dto) {
		ENTITY entity = dao.findById(dto.getId());
		dao.delete(entity);
	}

	@Override
	public void setDao(BaseDAO<ENTITY> dao) {
		this.dao = dao;
	}

	@Override
	public void setConverter(DtoEntityConverter<ENTITY, DTO> converter) {
		this.converter = converter;
	}

	@Override
	public abstract ENTITY getNewEntity();

	protected String addLike(String value, boolean exact) {
		return (exact ? value : value + "%");
	}

	// Method used to convert a single domain object or a list
	@SuppressWarnings("unchecked")
	protected List<DTO> convert(Object items) {
		List<DTO> dtoItems = new ArrayList<DTO>();
		if (items != null) {
			if (items instanceof List<?>) {
				if (((List<ENTITY>) items).size() > 0) {
					for (ENTITY item : (List<ENTITY>) items) {
						dtoItems.add(converter.convertEntityToDto(item));
					}
				}
			} else {
				dtoItems.add(converter.convertEntityToDto((ENTITY) items));
			}
		}
		return dtoItems;
	}

	@Override
	@Transactional(readOnly = true)
	public DTO findByExternalId(Long externalId) {
		return converter.convertEntityToDto(dao.findByExternalId(externalId));
	}

	protected String getPeriodClause(final Date effectiveDate, String... entities) {
		StringBuilder dateClauseBuilder = new StringBuilder();
		if (effectiveDate == null) {
			for (String namedEntity : entities) {
				dateClauseBuilder.append(StringUtils.replace(CURRENT_DATE_PLACE_HOLDER, "$", namedEntity));
			}

		} else {
			for (String namedEntity : entities) {
				dateClauseBuilder.append(StringUtils.replace(NAMED_DATE_PLACE_HOLDER, "$", namedEntity));
			}
		}
		return dateClauseBuilder.toString();
	}
}
