package com.novacroft.nemo.tfl.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

import com.novacroft.nemo.common.domain.CommonCard;

/**
 * TfL address implementation
 */

@Audited
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "card")
public class Card extends CommonCard implements Serializable {

    protected Date hotListDateTime;
    protected HotlistReason hotlistReason;
    private String hotlistStatus;
    protected Long paymentCardId;
    protected String securityQuestion;
    protected String securityAnswer;

    @Transient
    private static final long serialVersionUID = -2982517268040775022L;

    public Card() {
        super();
    }

    @SequenceGenerator(name = "CARD_SEQ", sequenceName = "CARD_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public Date getHotListDateTime() {
        return hotListDateTime;
    }

    public void setHotListDateTime(Date hotListDateTime) {
        this.hotListDateTime = hotListDateTime;
    }

    @ManyToOne
    @JoinColumn(name = "hotListReasonId", nullable = true)
    public HotlistReason getHotlistReason() {
        return hotlistReason;

    }

    public void setHotlistReason(HotlistReason hotlistReason) {
        this.hotlistReason = hotlistReason;
    }

    public Long getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(Long paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public String getHotlistStatus() {
        return hotlistStatus;
    }

    public void setHotlistStatus(String hotlistStatus) {
        this.hotlistStatus = hotlistStatus;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
}
