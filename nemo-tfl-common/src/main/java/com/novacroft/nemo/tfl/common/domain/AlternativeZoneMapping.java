package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

/**
 * TfL alternative zone mapping domain definition
 */

@Entity
public class AlternativeZoneMapping extends AbstractBaseEntity {
    protected Integer startZone;
    protected Integer endZone;
    protected Integer alternativeStartZone;
    protected Integer alternativeEndZone;

    @Transient
    private static final long serialVersionUID = 6200449962689402878L;

    public AlternativeZoneMapping() {
        super();
    }

    @SequenceGenerator(name = "ALTERNATIVEZONEMAPPING_SEQ", sequenceName = "ALTERNATIVEZONEMAPPING_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALTERNATIVEZONEMAPPING_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Integer getStartZone() {
        return startZone;
    }

    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }

    public Integer getEndZone() {
        return endZone;
    }

    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }

    public Integer getAlternativeStartZone() {
        return alternativeStartZone;
    }

    public void setAlternativeStartZone(Integer alternativeStartZone) {
        this.alternativeStartZone = alternativeStartZone;
    }

    public Integer getAlternativeEndZone() {
        return alternativeEndZone;
    }

    public void setAlternativeEndZone(Integer alternativeEndZone) {
        this.alternativeEndZone = alternativeEndZone;
    }

}
