package com.novacroft.nemo.common.transfer;

/**
* Agentwebaccountaccess   transfer common definition.
* Automatically created.
*/

public class CommonAgentWebAccountAccessDTO extends AbstractBaseDTO {
	protected Long id;
	protected String agentId;
	protected Long webaccountId;
	protected Long customerId;
	protected String token;
	
	public Long getId(){ 
	  return id;
	}
	public void setId(Long id){ 
	  this.id = id;
	}
	public String getAgentId(){ 
	  return agentId;
	}
	public void setAgentId(String agentId){ 
	  this.agentId = agentId;
	}
	public Long getWebaccountId(){ 
	  return webaccountId;
	}
	public void setWebaccountId(Long webaccountId){ 
	  this.webaccountId = webaccountId;
	}
	public String getToken(){ 
	  return token;
	}
	public void setToken(String token){ 
	  this.token = token;
	}
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long id) {
		this.customerId = id;
	}
}
