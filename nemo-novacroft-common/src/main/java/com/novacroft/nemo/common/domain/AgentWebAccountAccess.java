package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * AgentWebaccountAccess domain class to hold the domain data. Automatically created.
 */
@Audited
@Entity
public class AgentWebAccountAccess extends CommonAgentWebaccountAccess {

    private static final long serialVersionUID = -7713435500700730489L;

    public AgentWebAccountAccess() {
        super();
    }

    @SequenceGenerator(name = "AGENTWEBACCOUNTACCESS_SEQ", sequenceName = "AGENTWEBACCOUNTACCESS_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGENTWEBACCOUNTACCESS_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }
}
