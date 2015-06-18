package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Calls stored in Innovator.
 */
@Audited
@Entity
public class Call extends CommonCall {
    @Transient
    private static final long serialVersionUID = -4729346841222462544L;

    protected int anonymised = 0;
    protected int readonly = 0;

    public Call() {
        super();
    }

    @SequenceGenerator(name = "CALL_SEQ", sequenceName = "CALL_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALL_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public int getAnonymised() {
        return anonymised;
    }

    public void setAnonymised(int anonymised) {
        this.anonymised = anonymised;
    }

    public int getReadonly() {
        return readonly;
    }

    public void setReadonly(int readonly) {
        this.readonly = readonly;
    }

}
