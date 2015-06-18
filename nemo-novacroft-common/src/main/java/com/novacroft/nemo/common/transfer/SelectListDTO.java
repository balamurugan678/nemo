package com.novacroft.nemo.common.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * Select list transfer class (for list of values)
 */
public class SelectListDTO extends AbstractBaseDTO {
    protected String name;
    protected String description;
    protected List<SelectListOptionDTO> options = new ArrayList<SelectListOptionDTO>();

    public SelectListDTO() {
    }

    public SelectListDTO(String name, String description, List<SelectListOptionDTO> options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SelectListOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<SelectListOptionDTO> options) {
        this.options = options;
    }
}
