package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

/**
 * TfL auto top-up configuration item definition (for when an auto top-up added or amount changed event occurs via online)
 */

public class AutoTopUpConfigurationItemDTO extends ItemDTO {

    private static final long serialVersionUID = -5996671215377202595L;
    protected Long autoTopUpId;
    protected Integer autoTopUpAmount;
    protected Date startDate;
    protected Date endDate;
    protected String autoTopUpActivity;

    public AutoTopUpConfigurationItemDTO() {
        super();
    }

    public AutoTopUpConfigurationItemDTO(Long cardId, Integer price, Long autoTopUpId, Integer autoTopUpAmount) {
        this.cardId = cardId;
        this.price = price;
        this.autoTopUpId = autoTopUpId;
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public AutoTopUpConfigurationItemDTO(Long cardId, Integer price, Long autoTopUpId, Integer autoTopUpAmount, String autoTopUpActivity) {
        this.cardId = cardId;
        this.price = price;
        this.autoTopUpId = autoTopUpId;
        this.autoTopUpAmount = autoTopUpAmount;
        this.autoTopUpActivity = autoTopUpActivity;
    }

    public AutoTopUpConfigurationItemDTO(Long cardId, Long cartId, Integer price, Long autoTopUpId, Integer autoTopUpAmount) {
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = price;
        this.autoTopUpId = autoTopUpId;
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public AutoTopUpConfigurationItemDTO(Long orderId, Long cardId, Long autoTopUpId, Integer autoTopUpAmount, Integer price) {
        this.orderId = orderId;
        this.cardId = cardId;
        this.price = price;
        this.autoTopUpId = autoTopUpId;
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public AutoTopUpConfigurationItemDTO(Long orderId, Long cardId, Long autoTopUpId, Integer autoTopUpAmount, Integer price, Date startDate,
                    Date endDate) {
        this.orderId = orderId;
        this.cardId = cardId;
        this.price = price;
        this.autoTopUpId = autoTopUpId;
        this.autoTopUpAmount = autoTopUpAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getAutoTopUpId() {
        return autoTopUpId;
    }

    public void setAutoTopUpId(Long autoTopUpId) {
        this.autoTopUpId = autoTopUpId;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }

}
