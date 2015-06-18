package com.novacroft.nemo.common.transfer;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Select list option transfer class (for list of values items)
 */
public class SelectListOptionDTO extends AbstractBaseDTO {
    protected String value;
    protected Integer displayOrder;
    protected String meaning;
    protected Boolean disabled = Boolean.FALSE;

    public SelectListOptionDTO() {
    }

    public SelectListOptionDTO(String value) {
        this.value = value;
        this.disabled = Boolean.FALSE;
    }

    public SelectListOptionDTO(String value, Integer displayOrder, String meaning) {
        this.value = value;
        this.displayOrder = displayOrder;
        this.meaning = meaning;
        this.disabled = Boolean.FALSE;
    }

    public SelectListOptionDTO(String value, String meaning) {
        this.value = value;
        this.meaning = meaning;
        this.disabled = Boolean.FALSE;
    }

    public SelectListOptionDTO(String value, String meaning, Boolean disabled) {
        this.value = value;
        this.meaning = meaning;
        this.disabled = disabled;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SelectListOptionDTO that = (SelectListOptionDTO) object;

        return new EqualsBuilder().append(value, that.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(CommonHashCodeSeed.SELECT_LIST_OPTION_DTO.initialiser(),
                CommonHashCodeSeed.SELECT_LIST_OPTION_DTO.multiplier()).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("value", value).append("displayOrder", displayOrder)
                .append("meaning", meaning).toString();
    }
}
