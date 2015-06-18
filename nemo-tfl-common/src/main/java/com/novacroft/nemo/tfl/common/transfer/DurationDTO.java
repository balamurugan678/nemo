package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.PeriodicAbstractBaseDTO;

public class DurationDTO extends PeriodicAbstractBaseDTO {
	
	private static final long serialVersionUID = 5593043084076981519L;
    protected Integer duration;
    protected String unit;
    protected String code;
    protected String name;

    public DurationDTO() {

    }

    public DurationDTO(Long id, Integer duration, String unit, String code, String name, Date effectiveFrom, Date effectiveTo, String cubicReference) {
        this.id = id;
        this.duration = duration;
        this.unit = unit;
        this.code = code;
        this.name = name;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.cubicReference = cubicReference;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
