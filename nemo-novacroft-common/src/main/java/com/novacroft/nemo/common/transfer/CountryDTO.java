package com.novacroft.nemo.common.transfer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;

/**
 * Country transfer class
 */
public class CountryDTO extends CommonApplicationEventDTO {

    private static final long serialVersionUID = 2403658195473057262L;
    protected String name;
    protected String code;
    protected String isoNumericCode;

    public String getName() {
        return name;
    }

    public CountryDTO(){
    	
    }
    public CountryDTO(String code) {
		this.code = code;
	}
    
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getIsoNumericCode() {
        return isoNumericCode;
    }
    
    public void setIsoNumericCode(String isoNumericCode) {
        this.isoNumericCode = isoNumericCode;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CountryDTO)) {
            return false;
        }
        
        if (obj == this) {
            return true;
        }
        
        CountryDTO rhs = (CountryDTO) obj;
        return StringUtils.equalsIgnoreCase(code, rhs.getCode());
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(CommonHashCodeSeed.APPLICATION_EVENT.initialiser(), 
                        CommonHashCodeSeed.APPLICATION_EVENT.multiplier()).append(code).toHashCode();
    }
    
    @Override
    public String toString() {
        return code;
    }
}
