package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PassengerTypeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PassengerTypeDAO;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "passengerTypeDataService")
@Transactional(readOnly = true)
public class PassengerTypeDataServiceImpl extends BaseDataServiceImpl<PassengerType, PassengerTypeDTO>
        implements PassengerTypeDataService {

    @Override
    public PassengerType getNewEntity() {
        return new PassengerType();
    }

    @Autowired
    public void setDao(PassengerTypeDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PassengerTypeConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public PassengerTypeDTO findByName(String name) {
        final String hsql = "from PassengerType p where p.name = ?";
        PassengerType passengerType = dao.findByQueryUniqueResult(hsql, new Object[]{name});
        return (passengerType != null ? this.converter.convertEntityToDto(passengerType) : null);
    }

    @Override
    public PassengerTypeDTO findByCode(String code, Date effectiveDate) {
        String hql = "from PassengerType p where p.code = :code ";
        hql = hql + getPeriodClause(effectiveDate, "p");

        final Map<String, Object> namedParameterMap = new HashMap<>();
        if (effectiveDate != null) {
            namedParameterMap.put("effectiveDate", effectiveDate);
        }
        namedParameterMap.put("code", code);

        final PassengerType passengerType = dao.findByQueryUniqueResultUsingNamedParameters(hql, namedParameterMap);
        return passengerType == null ? null : converter.convertEntityToDto(passengerType);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PassengerTypeDTO> findAll(){
        String hql = "from PassengerType p where p.effectiveFrom <= :effectiveDate and (p.effectiveTo is null or p.effectiveTo >= :effectiveDate)";
        final Map<String, Object> namedParameterMap = new HashMap<>();
        namedParameterMap.put("effectiveDate", new Date());
        List<PassengerType> passengerTypes = dao.findByQueryUsingNamedParameters(hql, namedParameterMap);
        if(passengerTypes != null && passengerTypes.size()> 0){
            ArrayList<PassengerTypeDTO> passengerTypeDTOs = new ArrayList<PassengerTypeDTO>();
            for(PassengerType passengerType: passengerTypes){
                passengerTypeDTOs.add(converter.convertEntityToDto(passengerType));
            }
            return passengerTypeDTOs;
        }
        return null;
    }

}
