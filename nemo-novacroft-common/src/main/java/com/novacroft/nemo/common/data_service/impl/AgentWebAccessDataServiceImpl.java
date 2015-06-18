package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.AgentWebAccessConverterImpl;
import com.novacroft.nemo.common.data_access.AgentWebAccountAccessDAO;
import com.novacroft.nemo.common.data_service.AgentWebAccountAccessDataService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.domain.AgentWebAccountAccess;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AgentWebaccountAccessDataService implementation
 */
@Service(value = "agentWebAccessDataService")
@Transactional(readOnly = true)
public class AgentWebAccessDataServiceImpl extends BaseDataServiceImpl<AgentWebAccountAccess, AgentWebAccessDTO>
        implements AgentWebAccountAccessDataService {

    @Autowired
    public void setDao(AgentWebAccountAccessDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AgentWebAccessConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AgentWebAccountAccess getNewEntity() {
        return new AgentWebAccountAccess();
    }

    @Override
    public List<AgentWebAccessDTO> findAllAgentIdAndWebacountId(AgentWebAccessDTO agentWebaccount) {
        AgentWebAccountAccess agentWebaccountAccess = new AgentWebAccountAccess();
        agentWebaccountAccess.setAgentId(agentWebaccount.getAgentId());
        agentWebaccountAccess.setWebaccountId(agentWebaccount.getWebaccountId());
        List<AgentWebAccountAccess> agentWebaccountAccessList = dao.findByExample(agentWebaccountAccess);
        return convert(agentWebaccountAccessList);
    }

}
