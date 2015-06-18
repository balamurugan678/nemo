package com.novacroft.nemo.tfl.common.domain.cubic;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Get Card Information Request Version 2
 */
public class CardInfoRequestV2 {
    protected String prestigeId;
    protected String userId;
    protected String password;

    public CardInfoRequestV2() {
    }

    public CardInfoRequestV2(String prestigeId, String userId, String password) {
        this.prestigeId = prestigeId;
        this.userId = userId;
        this.password = password;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
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

        CardInfoRequestV2 that = (CardInfoRequestV2) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(userId, that.userId)
                .append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.CARD_INFO_REQUEST_V2.initialiser(),
                HashCodeSeed.CARD_INFO_REQUEST_V2.multiplier()).append(prestigeId).append(password).append(password)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("userId", userId).append("password", password)
                .toString();
    }
}
