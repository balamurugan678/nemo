package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Entity
public class CallType extends CommonCallType {

    @Transient
    private static final long serialVersionUID = -6138329223857492620L;

    public CallType() {
        super();
    }

    @SequenceGenerator(name = "CALLTYPE_SEQ", sequenceName = "CALLTYPE_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALLTYPE_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

}
