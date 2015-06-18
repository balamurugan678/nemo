package com.novacroft.nemo.tfl.common.transfer;

public class CustomerWebAccountDTO {
	//TODO This class will soon disappear

    CustomerDTO customerDTO;
    WebAccountDTO webAccountDTO;
    
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }
    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
    public WebAccountDTO getWebAccountDTO() {
        return webAccountDTO;
    }
    public void setWebAccountDTO(WebAccountDTO webAccountDTO) {
        this.webAccountDTO = webAccountDTO;
    }

}
