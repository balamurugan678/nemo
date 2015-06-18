package com.novacroft.nemo.common.domain;

/**
 * HTML option tag data item
 *
 * @see SelectList
 */
public class SelectListOption extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;
    protected Long selectListId;
    protected String value;
    protected Integer displayOrder;

    public SelectListOption() {
        super();
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

    public Long getSelectListId() {
        return selectListId;
    }

    public void setSelectListId(Long selectListId) {
        this.selectListId = selectListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
