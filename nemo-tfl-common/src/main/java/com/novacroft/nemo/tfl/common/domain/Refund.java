package com.novacroft.nemo.tfl.common.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Refund  implements Serializable{

    private static final long serialVersionUID = -1915708303922676071L;
    
    protected Long refundAmount;
    private Integer refundableWeeks;
    private Integer refundableDays;
    private Integer refundableMonths;
    private Integer usedDays;
    protected Map<String, String> refundReasonings;

    public Refund(){
        refundableWeeks = 0;
        refundableDays = 0;
        refundableMonths = 0;
        refundAmount = 0L;
        refundReasonings = new HashMap<String, String>();
    }
    
    public Integer getRefundableWeeks() {
        return refundableWeeks;
    }

    public Integer getRefundableDays() {
        return refundableDays;
    }

    public void setRefundableWeeks(Integer refundableWeeks) {
        this.refundableWeeks = refundableWeeks;
    }

    public void setRefundableDays(Integer refundableDays) {
        this.refundableDays = refundableDays;
    }

    public Long getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundableMonths() {
        return refundableMonths;
    }

    public void setRefundableMonths(Integer refundableMonths) {
        this.refundableMonths = refundableMonths;
    }

    public Map<String, String> getRefundReasonings() {
        return refundReasonings;
    }

    public void setRefundReasonings(Map<String, String> refundReasonings) {
        this.refundReasonings = refundReasonings;
    }
    
    public void setRefundReasoning(String key,String value){
        this.refundReasonings.put(key, value);
        
    }

    public Integer getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(Integer usedDays) {
        this.usedDays = usedDays;
    }
}
