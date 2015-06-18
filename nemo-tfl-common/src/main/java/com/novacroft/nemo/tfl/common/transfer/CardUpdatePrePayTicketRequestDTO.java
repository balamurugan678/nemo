package com.novacroft.nemo.tfl.common.transfer;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_CURRENCY;



public class CardUpdatePrePayTicketRequestDTO extends CardUpdateRequestDTO{
    protected Integer productCode;
    protected String startDate;
    protected String expiryDate;
    protected Integer currency = DEFAULT_CURRENCY;
    protected Integer productPrice;
    
    public CardUpdatePrePayTicketRequestDTO() {
        super();
    }
    
    public CardUpdatePrePayTicketRequestDTO(String prestigeId, String action,   Integer productCode, String startDate, String expiryDate,
                     Integer productPrice, Long pickupLocation,  String userId, String password) {
        super();
        this.prestigeId = prestigeId;
        this.action = action;
        this.productCode = productCode;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.productPrice = productPrice;
        this.pickupLocation = pickupLocation;
        this.userId = userId;
        this.password = password;
    }
    
    public CardUpdatePrePayTicketRequestDTO(String realTimeFlag, String prestigeId, String action,  Integer productCode, String startDate, String expiryDate,
                    Integer currency, Integer productPrice, Long pickupLocation, Integer paymentMethod, String userId, String password) {
        super();
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.action = action;
        this.productCode = productCode;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.currency = currency;
        this.productPrice = productPrice;
        this.pickupLocation = pickupLocation;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.password = password;
    }
    
  
    public Integer getProductCode() {
        return productCode;
    }
    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Integer getCurrency() {
        return currency;
    }
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
    public Integer getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
    

}
