package com.novacroft.nemo.common.transfer;

/**
 * contact transfer common definition
 */
public class CommonContactDTO extends AbstractBaseDTO {
	protected String name;
    protected String value;
    protected String type;
    protected Long customerId;

    public CommonContactDTO() {
        super();
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
    
}
