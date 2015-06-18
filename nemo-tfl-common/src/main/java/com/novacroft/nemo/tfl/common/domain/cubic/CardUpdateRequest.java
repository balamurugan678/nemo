package com.novacroft.nemo.tfl.common.domain.cubic;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_REAL_TIME_FLAG;

public class CardUpdateRequest {
    
    protected String realTimeFlag = DEFAULT_REAL_TIME_FLAG;
    protected String prestigeId;
    protected String action;
    protected Integer pickupLocation;
    protected Integer paymentMethod = DEFAULT_PAYMENT_METHOD;
    protected String userId;
    protected String password;
    
    protected Integer prePayValue;
    protected Integer currency;
    
    public CardUpdateRequest() {
        
    }
    
    public CardUpdateRequest(String realTimeFlag, String prestigeId, String action, Integer pickupLocation, Integer paymentMethod, String userId,
                    String password) {
        super();
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.action = action;
        this.pickupLocation = pickupLocation;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.password = password;
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
    public Integer getPickupLocation() {
        return pickupLocation;
    }
    public void setPickupLocation(Integer pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public Integer getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(Integer paymentMethod) {
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

    public Integer getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Integer prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
    
    

}
