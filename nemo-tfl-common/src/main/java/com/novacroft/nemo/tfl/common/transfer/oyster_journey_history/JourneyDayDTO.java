package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

/**
 * Transfer class for Oyster journey web service journey
 */
public class JourneyDayDTO {
    protected Date effectiveTrafficOn;
    protected List<JourneyDTO> journeys = new ArrayList<JourneyDTO>();
    protected Integer dailyBalance;
    protected Integer totalSpent;
    protected Boolean containsExplanatoryWarningFlag = Boolean.FALSE;
    protected Boolean containsExplanatoryCappingFlag = Boolean.FALSE;
    protected Boolean containsExplanatoryAutoCompleteFlag = Boolean.FALSE;
    protected Boolean containsExplanatoryManuallyCorrectedFlag = Boolean.FALSE;

    public JourneyDayDTO() {
    }

    public JourneyDayDTO(Date effectiveTrafficOn, List<JourneyDTO> journeys, Integer dailyBalance, Integer totalSpent) {
        this.effectiveTrafficOn = effectiveTrafficOn;
        this.journeys = journeys;
        this.dailyBalance = dailyBalance;
        this.totalSpent = totalSpent;
    }

    public Date getEffectiveTrafficOn() {
        return effectiveTrafficOn;
    }

    public void setEffectiveTrafficOn(Date effectiveTrafficOn) {
        this.effectiveTrafficOn = effectiveTrafficOn;
    }

    public List<JourneyDTO> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyDTO> journeys) {
        this.journeys = journeys;
    }

    public Integer getDailyBalance() {
        return dailyBalance;
    }

    public void setDailyBalance(Integer dailyBalance) {
        this.dailyBalance = dailyBalance;
    }

    public Integer getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Integer totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Boolean getContainsExplanatoryWarningFlag() {
        return containsExplanatoryWarningFlag;
    }

    public void setContainsExplanatoryWarningFlag(Boolean containsExplanatoryWarningFlag) {
        this.containsExplanatoryWarningFlag = containsExplanatoryWarningFlag;
    }

    public Boolean getContainsExplanatoryCappingFlag() {
        return containsExplanatoryCappingFlag;
    }

    public void setContainsExplanatoryCappingFlag(Boolean containsExplanatoryCappingFlag) {
        this.containsExplanatoryCappingFlag = containsExplanatoryCappingFlag;
    }

    public Boolean getContainsExplanatoryAutoCompleteFlag() {
        return containsExplanatoryAutoCompleteFlag;
    }

    public void setContainsExplanatoryAutoCompleteFlag(Boolean containsExplanatoryAutoCompleteFlag) {
        this.containsExplanatoryAutoCompleteFlag = containsExplanatoryAutoCompleteFlag;
    }

    public Boolean getContainsExplanatoryManuallyCorrectedFlag() {
        return containsExplanatoryManuallyCorrectedFlag;
    }

    public void setContainsExplanatoryManuallyCorrectedFlag(Boolean containsExplanatoryManuallyCorrectedFlag) {
        this.containsExplanatoryManuallyCorrectedFlag = containsExplanatoryManuallyCorrectedFlag;
    }

    public Boolean getMultipleExplanatory() {
        int trueCount = 0;
        if (containsExplanatoryWarningFlag) {
            trueCount++;
        }
        if (containsExplanatoryCappingFlag) {
            trueCount++;
        }
        if (containsExplanatoryAutoCompleteFlag) {
            trueCount++;
        }
        if (containsExplanatoryManuallyCorrectedFlag) {
            trueCount++;
        }

        return trueCount > 1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        JourneyDayDTO that = (JourneyDayDTO) object;

        return new EqualsBuilder().append(effectiveTrafficOn, that.effectiveTrafficOn).append(journeys, that.journeys)
                        .append(dailyBalance, that.dailyBalance).append(totalSpent, that.totalSpent).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.JOURNEY_DAY_DTO.initialiser(), HashCodeSeed.JOURNEY_DAY_DTO.multiplier()).append(effectiveTrafficOn)
                        .append(journeys).append(dailyBalance).append(totalSpent).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("effectiveTrafficOn", effectiveTrafficOn).append("journeys", journeys)
                        .append("dailyBalance", dailyBalance).append("totalSpent", totalSpent).toString();
    }
}
