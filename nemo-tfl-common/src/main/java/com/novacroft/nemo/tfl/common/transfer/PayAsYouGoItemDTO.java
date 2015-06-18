package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

/**
 * TfL pay as you go item common definition
 */

public class PayAsYouGoItemDTO extends ItemDTO {
	private static final long serialVersionUID = -8251002125292421882L;
	protected Long payAsYouGoId;
    protected Date startDate;
    protected Date endDate;
    protected String reminderDate;
    protected Integer autoTopUpAmount;

    public PayAsYouGoItemDTO() {
        super();
    }
    
    public PayAsYouGoItemDTO(Long id, Long cardId, Long cartId, Integer price, Long payAsYouGoId, Date startDate, Date endDate, String reminderDate,
                    Integer autoTopUpAmount) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
        this.price = price;
        this.payAsYouGoId = payAsYouGoId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderDate = reminderDate;
    }

    public PayAsYouGoItemDTO(Long cardId, Integer price, Long payAsYouGoId, Date startDate, Date endDate, String reminderDate, Integer autoTopUpAmount) {
        this.cardId = cardId;
        this.price = price;
        this.payAsYouGoId = payAsYouGoId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminderDate = reminderDate;
    }
    
    public PayAsYouGoItemDTO(Long id, Long cardId, Long cartId) {
        this.id = id;
        this.cardId = cardId;
        this.cartId = cartId;
    }
    
    public PayAsYouGoItemDTO(String name, Integer price, Long cardId) {
        this.name = name;
        this.price = price;
        this.cardId = cardId;
    }

    public Long getPayAsYouGoId() {
        return payAsYouGoId;
    }

    public void setPayAsYouGoId(Long payAsYouGoId) {
        this.payAsYouGoId = payAsYouGoId;
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

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }
}
