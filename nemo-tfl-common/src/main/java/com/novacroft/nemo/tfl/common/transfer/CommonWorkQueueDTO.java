package com.novacroft.nemo.tfl.common.transfer;

import java.util.Calendar;
import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.common.utils.DateUtil;

/**
* Workqueue   transfer common definition.
* Automatically created.
*/

public class CommonWorkQueueDTO extends AbstractBaseDTO {
protected Long id;
protected Long stage;
protected String status;
protected String description;
protected Boolean hasPassedThreshholdDate;
protected Boolean canEscalate;

private static final Integer DAYS_BEFORE_ESCALATION = 5;

    public Boolean getHasPassedThreshholdDate() {
    
        Date createdDate = super.getCreatedDateTime();
        
        Calendar c = Calendar.getInstance(); 
        Date today = c.getTime();
        
        c.setTime(createdDate); 
        c.add(Calendar.DATE, DAYS_BEFORE_ESCALATION);
        Date overduedate = c.getTime();

        hasPassedThreshholdDate = DateUtil.isAfter(today , overduedate );
        
        return hasPassedThreshholdDate;
    }
    
    public Boolean getCanEscalate() {
        this.canEscalate = Boolean.FALSE;
        
        if (this.getHasPassedThreshholdDate() && !"H".equals(this.status)) {
            this.canEscalate = Boolean.TRUE;
        }
        return this.canEscalate;
    }
    
    public Long getId(){ 
      return id;
    }
    public void setId(Long id){ 
      this.id = id;
    }
    public Long getStage(){ 
      return stage;
    }
    public void setStage(Long stage){ 
      this.stage = stage;
    }
    public String getStatus(){ 
      return status;
    }
    public void setStatus(String status){ 
      this.status = status;
    }
    public String getDescription(){ 
      return description;
    }
    public void setDescription(String description){ 
      this.description = description;
    }
    
    
}
