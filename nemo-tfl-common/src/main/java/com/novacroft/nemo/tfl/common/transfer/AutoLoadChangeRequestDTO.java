package com.novacroft.nemo.tfl.common.transfer;

import static com.novacroft.nemo.tfl.common.constant.CubicConstant.DEFAULT_REAL_TIME_FLAG;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

/**
 * Transfer class that represents a CUBIC Change Autoload Configuration Request
 */
public class AutoLoadChangeRequestDTO {
    protected static final Integer DEBIT_CARD = 32;
    protected static final Integer CREDIT_CARD = 64;
    public static final Integer DEFAULT_PAYMENT_METHOD = DEBIT_CARD;

    protected String realTimeFlag = DEFAULT_REAL_TIME_FLAG;
    protected String prestigeId;
    protected Integer autoLoadState;
    protected Integer paymentMethod;
    protected Long pickUpLocation;
    protected String userId;
    protected String password;

    public AutoLoadChangeRequestDTO() {
    }

    public AutoLoadChangeRequestDTO(String prestigeId, Integer autoLoadState, Long pickUpLocation, String userId,
                                    String password) {
        this.realTimeFlag = DEFAULT_REAL_TIME_FLAG;
        this.prestigeId = prestigeId;
        this.autoLoadState = autoLoadState;
        this.paymentMethod = DEFAULT_PAYMENT_METHOD;
        this.pickUpLocation = pickUpLocation;
        this.userId = userId;
        this.password = password;
    }

    public AutoLoadChangeRequestDTO(String realTimeFlag, String prestigeId, Integer autoLoadState, Integer paymentMethod,
                                    Long pickUpLocation, String userId, String password) {
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.autoLoadState = autoLoadState;
        this.paymentMethod = paymentMethod;
        this.pickUpLocation = pickUpLocation;
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

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Long pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AutoLoadChangeRequestDTO that = (AutoLoadChangeRequestDTO) object;

        return new EqualsBuilder().append(realTimeFlag, that.realTimeFlag).append(prestigeId, that.prestigeId)
                .append(autoLoadState, that.autoLoadState).append(paymentMethod, that.paymentMethod)
                .append(pickUpLocation, that.pickUpLocation).append(userId, that.userId).append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.AUTO_LOAD_CHANGE_REQUEST_DTO.initialiser(),
                HashCodeSeed.AUTO_LOAD_CHANGE_REQUEST_DTO.multiplier()).append(realTimeFlag).append(prestigeId)
                .append(autoLoadState).append(paymentMethod).append(pickUpLocation).append(password).append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("realTimeFlag", realTimeFlag).append("prestigeId", prestigeId)
                .append("autoLoadState", autoLoadState).append("paymentMethod", paymentMethod)
                .append("pickUpLocation", pickUpLocation).append("userId", userId).append("password", password).toString();
    }
}
