package com.novacroft.nemo.common.transfer;

/**
 * card transfer common definition
 */
public class CommonCardDTO extends AbstractBaseDTO {
    protected String cardNumber;
    protected Long webaccountId;
    protected Long customerId;
    protected String name;


    public CommonCardDTO() {
        super();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getWebaccountId() {
        return webaccountId;
    }

    public void setWebaccountId(Long webaccountId) {
        this.webaccountId = webaccountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }


}
