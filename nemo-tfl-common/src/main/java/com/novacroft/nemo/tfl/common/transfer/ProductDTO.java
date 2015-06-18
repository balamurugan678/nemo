package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.CommonProductDTO;

/**
 * TfL product transfer implementation
 */

public class ProductDTO extends CommonProductDTO {
    
    private static final long serialVersionUID = 3008547625926692543L;
    protected Integer startZone;
    protected Integer endZone;


    public ProductDTO(Long productItemId, int price, String duration, int startZone, int endZone) {
        this.id = productItemId;
        this.ticketPrice = price;
        this.duration = duration;
        this.startZone = startZone;
        this.endZone = endZone;
    }
    
    public ProductDTO() {
       
    }

    public ProductDTO(Integer startZone, Integer endZone, Date date, Date date2, String duration, String productName) {
        this.duration = duration;
        this.startZone = startZone;
        this.endZone = endZone;
        this.productName = productName;
        
    }

    public Integer getStartZone() {
        return startZone;
    }
    public Integer getEndZone() {
        return endZone;
    }
    public void setStartZone(Integer startZone) {
        this.startZone = startZone;
    }
    public void setEndZone(Integer endZone) {
        this.endZone = endZone;
    }
}
