package com.novacroft.nemo.tfl.common.domain.cubic;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Change Auto Load Configuration CUBIC service request
 */
public class AutoLoadRequest {
    protected String realTimeFlag;
    protected String prestigeId;
    protected Integer autoLoadState;
    protected Integer paymentMethod;
    protected Integer pickupLocation;
    protected String userId;
    protected String password;

    public AutoLoadRequest() {
    }

    public AutoLoadRequest(String realTimeFlag, String prestigeId, Integer autoLoadState, Integer paymentMethod,
                           Integer pickupLocation, String userId, String password) {
        this.realTimeFlag = realTimeFlag;
        this.prestigeId = prestigeId;
        this.autoLoadState = autoLoadState;
        this.paymentMethod = paymentMethod;
        this.pickupLocation = pickupLocation;
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

    public Integer getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Integer pickupLocation) {
        this.pickupLocation = pickupLocation;
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

        AutoLoadRequest that = (AutoLoadRequest) object;

        return new EqualsBuilder().append(realTimeFlag, that.realTimeFlag).append(prestigeId, that.prestigeId)
                .append(autoLoadState, that.autoLoadState).append(paymentMethod, that.paymentMethod)
                .append(pickupLocation, that.pickupLocation).append(userId, that.userId).append(password, that.password)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.AUTO_LOAD_REQUEST.initialiser(), HashCodeSeed.AUTO_LOAD_REQUEST.multiplier())
                .append(realTimeFlag).append(prestigeId).append(autoLoadState).append(paymentMethod).append(pickupLocation)
                .append(password).append(password).toHashCode();
    }

    @Override
    public String toString() {
    	return new ToStringBuilder(this).append("realTimeFlag", realTimeFlag).append("prestigeId", prestigeId)
                .append("autoLoadState", autoLoadState).append("paymentMethod", paymentMethod)
                .append("pickupLocation", pickupLocation).append("userId", userId).append("password", password).toString();
    }
}
