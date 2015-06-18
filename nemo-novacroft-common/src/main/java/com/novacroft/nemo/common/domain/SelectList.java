package com.novacroft.nemo.common.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * HTML select tag data list
 *
 * @see SelectListOption
 */
public class SelectList extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected String description;
    protected List<SelectListOption> options = new ArrayList<SelectListOption>();

    public SelectList() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectListOption> getOptions() {
        return options;
    }

    public void setOptions(List<SelectListOption> options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectList that = (SelectList) o;

        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
