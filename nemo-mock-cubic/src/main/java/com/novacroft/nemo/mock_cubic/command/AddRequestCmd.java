package com.novacroft.nemo.mock_cubic.command;

public class AddRequestCmd {
    protected Long requestSequenceNumber;
    protected String realTimeFlag;
    protected String prestigeId;
    protected String action;
    protected Long productCode;
    protected String startDate;
    protected String expiryDate;
    protected Long productPrice;
    protected Long currency;
    protected Long prePayValue;
    protected Long pickupLocation;
    protected Long availableSlots;
    protected Long paymentMethod;
    protected String userId;
    protected String password;

    public AddRequestCmd() {

    }

    public AddRequestCmd(String prestigeId, Integer requestSequenceNumber, String realTimeFlag, Integer prePayValue, Integer currency,
                    Long pickupLocation) {
        this.requestSequenceNumber = Long.valueOf(requestSequenceNumber);
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.currency = Long.valueOf(currency);
        this.pickupLocation = pickupLocation;
        this.prePayValue = Long.valueOf(prePayValue);
    }

    public AddRequestCmd(Long requestSequenceNumber, String realTimeFlag, String prestigeId, String action, Integer currency, Long pickupLocation,
                    Integer paymentMethod, String userId, String password, Integer prePayValue) {
        this.requestSequenceNumber = requestSequenceNumber;
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.action = action;
        this.currency = (currency == null) ? null : Long.valueOf(currency);
        this.pickupLocation = pickupLocation;
        this.paymentMethod = (paymentMethod == null) ? null : Long.valueOf(paymentMethod);
        this.userId = userId;
        this.password = password;
        this.prePayValue = (prePayValue == null) ? null : Long.valueOf(prePayValue);
    }

    public AddRequestCmd(Long requestSequenceNumber, String realTimeFlag, String prestigeId, String action, Integer currency, Long pickupLocation,
                    Integer paymentMethod, String userId, String password, Integer productCode, Integer productPrice, String startDate,
                    String expiryDate) {
        this.requestSequenceNumber = requestSequenceNumber;
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.action = action;
        this.currency = (currency == null) ? null : Long.valueOf(currency);
        this.pickupLocation = pickupLocation;
        this.paymentMethod = (paymentMethod == null) ? null : Long.valueOf(paymentMethod);
        this.userId = userId;
        this.password = password;
        this.productCode = (productCode == null) ? null : Long.valueOf(productCode);
        this.productPrice = (productPrice == null) ? null : Long.valueOf(productPrice);
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }

    public AddRequestCmd(String realTimeFlag, Integer productCode, Integer productPrice, Integer currency, String startDate, String expiryDate,
                    Long pickupLocation) {
        this.realTimeFlag = realTimeFlag;
        this.productCode = Long.valueOf(productCode);
        this.productPrice = Long.valueOf(productPrice);
        this.currency = Long.valueOf(currency);
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.pickupLocation = pickupLocation;
    }

    public Long getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Long requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getRealTimeFlag() {
        return realTimeFlag;
    }

    public void setRealTimeFlag(String realTimeFlag) {
        this.realTimeFlag = realTimeFlag;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
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

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Long prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Long getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Long pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Long getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Long availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
