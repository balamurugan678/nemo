package com.novacroft.nemo.common.transfer;

import java.util.Date;

public class JobCentrePlusDiscountDTO {
    protected String prestigeId;
    protected Date discountExpiryDate;
    protected Integer paygBalance;
    protected Boolean jobCentrePlusDiscountAvailable = Boolean.FALSE;

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Date getDiscountExpiryDate() {
        return discountExpiryDate;
    }

    public void setDiscountExpiryDate(Date discountExpiryDate) {
        this.discountExpiryDate = discountExpiryDate;
    }

    public Integer getPaygBalance() {
        return paygBalance;
    }

    public void setPaygBalance(Integer paygBalance) {
        this.paygBalance = paygBalance;
    }

    public Boolean getJobCentrePlusDiscountAvailable() {
        return jobCentrePlusDiscountAvailable;
    }

    public void setJobCentrePlusDiscountAvailable(Boolean jobCentrePlusDiscountAvailable) {
        this.jobCentrePlusDiscountAvailable = jobCentrePlusDiscountAvailable;
    }

}
