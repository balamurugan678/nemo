package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.CommonCardDTO;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

import java.util.Date;

import javax.persistence.Transient;

/**
 * TfL card transfer implementation
 */

public class CardDTO extends CommonCardDTO {
    private Date hotlistDateTime;
    @Transient
    private Boolean autoTopUpEnabled = Boolean.FALSE;
    @Transient
    private Integer autoTopUpState = 0;
    protected HotlistReason hotlistReason;
    private String hotlistStatus;
    protected Long paymentCardId;
    protected String securityQuestion;
    protected String securityAnswer;
    @Transient
    protected CardInfoResponseV2DTO cardInfo;

    public Date getHotlistDateTime() {
        return hotlistDateTime;
    }

    public void setHotlistDateTime(Date hotlistDateTime) {
        this.hotlistDateTime = hotlistDateTime;
    }

    public Boolean getAutoTopUpEnabled() {
        return autoTopUpEnabled;
    }

    public void setAutoTopUpEnabled(Boolean autoTopUpEnabled) {
        this.autoTopUpEnabled = autoTopUpEnabled;
    }

    public Integer getAutoTopUpState() {
        return autoTopUpState;
    }

    public void setAutoTopUpState(Integer autoTopUpState) {
        this.autoTopUpState = autoTopUpState;
    }

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

    public CardInfoResponseV2DTO getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfoResponseV2DTO cardInfo) {
        this.cardInfo = cardInfo;
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
