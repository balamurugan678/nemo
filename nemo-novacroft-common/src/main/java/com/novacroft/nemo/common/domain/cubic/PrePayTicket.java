package com.novacroft.nemo.common.domain.cubic;


/**
 * Card Pre Pay Ticket
 */
public class PrePayTicket extends PrePayBase implements PrePay {
    protected Integer productCode;
    protected Integer productPrice;
    protected String startDate;
    protected String expiryDate;
    
    
   
    public Integer getProductCode() {
        return productCode;
    }
    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }
    public Integer getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
  
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
   
    
}
