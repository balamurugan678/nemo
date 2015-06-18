package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.RandomApprovalRulesConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.RandomApprovalSampleThresholdDAO;
import com.novacroft.nemo.tfl.common.data_service.RandomApprovalRulesDataService;
import com.novacroft.nemo.tfl.common.domain.RandomApprovalSampleThresholdRule;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;


@Service(value = "randomApprovalRulesDataService")
@Transactional(readOnly = true)
public class RandomApprovalRulesDataServiceImpl extends BaseDataServiceImpl<RandomApprovalSampleThresholdRule, RandomApprovalSampleThresholdRuleDTO> implements RandomApprovalRulesDataService{


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(RandomApprovalSampleThresholdRuleDTO randomApprovalSampleThresholdDTO) {
        createOrUpdate(randomApprovalSampleThresholdDTO);
    }


    @Autowired
    public void setDao(RandomApprovalSampleThresholdDAO dao) {
        this.dao = dao;
    }

   @Autowired
    public void setConverter(RandomApprovalRulesConverterImpl converter) {
        this.converter = converter;
    }

   @Override
    public RandomApprovalSampleThresholdRule getNewEntity() {
        return new RandomApprovalSampleThresholdRule();
    }

    
    @Override
    public List<RandomApprovalSampleThresholdRuleDTO> findAllRandomApprovalSampleThresholds() {
        return this.convert(this.dao.findAll());
    }
    @Override
    public List<RandomApprovalSampleThresholdRuleDTO> findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(String organization, String team) {
        RandomApprovalSampleThresholdRule example = new RandomApprovalSampleThresholdRule();
        example.setTeam(team);
        example.setOrganisation(organization);
        return this.convert(this.dao.findByExample(example));
    }


  
    @SuppressWarnings("unchecked")
    @Override
    public List<RandomApprovalSampleThresholdRuleDTO> findOrganisationAndTeamById(String id) {
        List<RandomApprovalSampleThresholdRuleDTO> result = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();

       
       String sql = "select g.name as team, u.organisationId as organisation "
               + "from users u, user_groups ug, groups g "
               + "where u.id = ug.userid AND ug.groupid=g.id AND u.id = ?";
       Query query = dao.createSQLQuery(sql);
       query.setParameter(0,id);
       
       List<Object[]> rows = query.list();
       for (Object[] row : rows) {
           RandomApprovalSampleThresholdRuleDTO rule = new RandomApprovalSampleThresholdRuleDTO((String) row[0], (String) row[1]);
           result.add(rule);
       }
       return result;
    }

}
