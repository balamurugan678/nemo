package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.DiscountTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.DiscountTypeDAO;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;

@Service(value = "discountTypeDataService")
@Transactional(readOnly = true)
public class DiscountTypeDataServiceImpl extends BaseDataServiceImpl<DiscountType, DiscountTypeDTO>
        implements DiscountTypeDataService {

    @Override
    public DiscountType getNewEntity() {
        return new DiscountType();
    }

    @Autowired
    public void setDao(DiscountTypeDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(DiscountTypeConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public DiscountTypeDTO findByName(String name) {
        final String hsql = "from DiscountType p where p.name = ?";
        DiscountType discountType = dao.findByQueryUniqueResult(hsql, new Object[]{name});
        return (discountType != null ? this.converter.convertEntityToDto(discountType) : null);
    }

    @Override
    public DiscountTypeDTO findByCode(String code, Date effectiveDate) {
        String hql = "from DiscountType d where d.code = :code ";
        hql = hql + getPeriodClause(effectiveDate, "d");

        final Map<String, Object> namedParameterMap = new HashMap<>();
        if (effectiveDate != null) {
            namedParameterMap.put("effectiveDate", effectiveDate);
        }
        namedParameterMap.put("code", code);

        final DiscountType discountType = dao.findByQueryUniqueResultUsingNamedParameters(hql, namedParameterMap);
        return discountType == null ? null : converter.convertEntityToDto(discountType);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DiscountTypeDTO> findAll(){
        String hql = "from DiscountType p where p.effectiveFrom <= :effectiveDate and (p.effectiveTo is null or p.effectiveTo >= :effectiveDate)";
        final Map<String, Object> namedParameterMap = new HashMap<>();
        namedParameterMap.put("effectiveDate", new Date());
        List<DiscountType> discountTypes = dao.findByQueryUsingNamedParameters(hql, namedParameterMap);
        if(discountTypes != null && discountTypes.size()> 0){
            ArrayList<DiscountTypeDTO> discountTypeDTOs = new ArrayList<DiscountTypeDTO>();
            for(DiscountType discountType: discountTypes){
                discountTypeDTOs.add(converter.convertEntityToDto(discountType));
            }
            return discountTypeDTOs;
        }
        return null;
    }

}
