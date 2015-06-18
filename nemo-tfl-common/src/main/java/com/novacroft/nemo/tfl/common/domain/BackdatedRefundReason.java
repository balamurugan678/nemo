package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;

import javax.persistence.*;

@Entity
public class BackdatedRefundReason extends AbstractBaseEntity {

    @Transient
    private static final long serialVersionUID = -2375012180112800108L;

    private Long reasonId;
    private String description;
    private String extraValidationCode;

    public BackdatedRefundReason() {
    }

    public BackdatedRefundReason(Long reasonId, String description) {
        this.reasonId = reasonId;
        this.description = description;
    }

    @SequenceGenerator(name = "BACKDATEDREFUNDREASON_SEQ", sequenceName = "BACKDATEDREFUNDREASON_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BACKDATEDREFUNDREASON_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    @Column(name = "REASONID")
    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraValidationCode() {
        return extraValidationCode;
    }

    public void setExtraValidationCode(String extraValidationCode) {
        this.extraValidationCode = extraValidationCode;
    }
}
