package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.RepeatClaimLimitRuleConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RepeatClaimLimitRuleDAO;
import com.novacroft.nemo.tfl.common.data_service.RefundCeilingLimitRulesDataService;
import com.novacroft.nemo.tfl.common.domain.RepeatClaimLimitRule;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;


@Service(value = "refundCeilingLimitRulesDataService")
@Transactional(readOnly = true)
public class RefundCeilingLimitRulesDataServiceImpl extends BaseDataServiceImpl<RepeatClaimLimitRule, RepeatClaimLimitRuleDTO> implements RefundCeilingLimitRulesDataService{

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(RepeatClaimLimitRuleDTO randomApprovalSampleThresholdDTO) {
        createOrUpdate(randomApprovalSampleThresholdDTO);
    }


    @Autowired
    public void setDao(RepeatClaimLimitRuleDAO dao) {
        this.dao = dao;
    }

   @Autowired
    public void setConverter(RepeatClaimLimitRuleConverterImpl converter) {
        this.converter = converter;
    }

   @Override
    public RepeatClaimLimitRule getNewEntity() {
        return new RepeatClaimLimitRule();
    }

    
    @Override
    public List<RepeatClaimLimitRuleDTO> findAllRepeatClaimLimitRules() {
        return this.convert(this.dao.findAll());
        
        

    }

  
}
