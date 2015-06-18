package com.novacroft.nemo.common.converter;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.common.domain.AgentWebAccountAccess;
import com.novacroft.nemo.common.transfer.AgentWebAccessDTO;
import org.springframework.stereotype.Component;

/**
 * AgentWebaccountAccess  entity/DTO converter.
 */

@Component(value = "agentWebAccessConverter")
public class AgentWebAccessConverterImpl
        extends BaseDtoEntityConverterImpl<AgentWebAccountAccess, AgentWebAccessDTO> {
    @Override
    public AgentWebAccessDTO getNewDto() {
        return new AgentWebAccessDTO();
    }
}
