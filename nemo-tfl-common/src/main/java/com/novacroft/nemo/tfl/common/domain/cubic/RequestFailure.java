package com.novacroft.nemo.tfl.common.domain.cubic;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * CUBIC service fail response
 */
public class RequestFailure {
    protected Integer errorCode;
    protected String errorDescription;

    public RequestFailure() {
    }

    public RequestFailure(Integer errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        RequestFailure that = (RequestFailure) object;

        return new EqualsBuilder().append(errorCode, that.errorCode).append(errorDescription, that.errorDescription).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.REQUEST_FAILURE.initialiser(), HashCodeSeed.REQUEST_FAILURE.multiplier())
                .append(errorCode).append(errorDescription).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("errorCode", errorCode).append("errorDescription", errorDescription).toString();
    }

}
