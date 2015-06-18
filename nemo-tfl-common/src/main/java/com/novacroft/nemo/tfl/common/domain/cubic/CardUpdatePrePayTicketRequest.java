package com.novacroft.nemo.tfl.common.domain.cubic;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_REAL_TIME_FLAG;



/**
 *
     * <code>
 <CardUpdateRequest>
    <RealTimeFlag>N</RealTimeFlag>
    <PrestigeID>123456789</PrestigeID>
    <Action>ADD</Action>
    <PPT>
        <ProductCode>123</ProductCode>
        <StartDate>10/10/2002</StartDate>
        <ExpiryDate>11/10/2002</ExpiryDate>
        <ProductPrice>360</ProductPrice>
        <Currency>0</Currency>
    </PPT>
    <PickupLocation>740</PickupLocation>
    <PaymentMethod>32</PaymentMethod>
    <OriginatorInfo>
        <UserID>LTWebUser</UserID>
        <Password>secrets</Password>
    </OriginatorInfo>
</CardUpdateRequest>
</code>
     *
 *
 */
public class CardUpdatePrePayTicketRequest implements ICardUpdateRequest {

    protected String realTimeFlag = DEFAULT_REAL_TIME_FLAG;
    protected String prestigeId;
    protected String action;
    protected Long pickupLocation;
    protected Integer paymentMethod = DEFAULT_PAYMENT_METHOD;
    protected String userId;
    protected String password;
    protected Integer productCode;
    protected String startDate;
    protected String expiryDate;
    protected Integer currency = 0;
    protected Integer productPrice;
    
    
    public CardUpdatePrePayTicketRequest(String prestigeId, String action, Integer productCode, String startDate, String expiryDate,
                    Integer productPrice, Long pickupLocation,  String userId, String password) {
        this.prestigeId = prestigeId;
        this.action = action;
        this.pickupLocation = pickupLocation;
        this.userId = userId;
        this.password = password;
        this.productCode = productCode;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.productPrice = productPrice;

    }
    
    public CardUpdatePrePayTicketRequest(String realTimeFlag, String prestigeId, String action,   Integer productCode, String startDate, String expiryDate,
                    Integer currency, Integer productPrice, Long pickupLocation, Integer paymentMethod, String userId, String password) {
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.action = action;
        this.pickupLocation = pickupLocation;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.password = password;
        this.productCode = productCode;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.currency = currency;
        this.productPrice = productPrice;

    }

    public CardUpdatePrePayTicketRequest() {
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

    public String getRealTimeFlag() {
        return realTimeFlag;
    }

    public void setRealTimeFlag(String realTimeFlag) {
        this.realTimeFlag = realTimeFlag;
    }

    @Override
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

    public Long getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Long pickupLocation) {
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

    
    
    
    
    
}
