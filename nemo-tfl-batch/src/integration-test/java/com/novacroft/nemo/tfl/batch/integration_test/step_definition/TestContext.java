package com.novacroft.nemo.tfl.batch.integration_test.step_definition;

import org.quartz.JobDataMap;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

@Component
public class TestContext {
    private CustomerDTO customerDTO;
    private CardDTO cardDTO;
    private OrderDTO orderDTO;
    private PayAsYouGoItemDTO payAsYouGoItemDTO;
    private BACSSettlementDTO bacsSettlementDTO;
    private AdHocLoadSettlementDTO adHocLoadSettlementDTO;
    private String[] bacsRequestHandledRecord;
    private String[] bacsRejectRecord;
    private String[] adHocLoadPickedUpRecord;
    private JobLogDTO jobLogDTO;
    private JobDataMap jobDataMap;
    private Integer requestSequenceNumber;

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public PayAsYouGoItemDTO getPayAsYouGoItemDTO() {
        return payAsYouGoItemDTO;
    }

    public void setPayAsYouGoItemDTO(PayAsYouGoItemDTO payAsYouGoItemDTO) {
        this.payAsYouGoItemDTO = payAsYouGoItemDTO;
    }

    public BACSSettlementDTO getBacsSettlementDTO() {
        return bacsSettlementDTO;
    }

    public void setBacsSettlementDTO(BACSSettlementDTO bacsSettlementDTO) {
        this.bacsSettlementDTO = bacsSettlementDTO;
    }

    public String[] getBacsRequestHandledRecord() {
        return bacsRequestHandledRecord;
    }

    public void setBacsRequestHandledRecord(String[] bacsRequestHandledRecord) {
        this.bacsRequestHandledRecord = bacsRequestHandledRecord;
    }

    public String[] getBacsRejectRecord() {
        return bacsRejectRecord;
    }

    public void setBacsRejectRecord(String[] bacsRejectRecord) {
        this.bacsRejectRecord = bacsRejectRecord;
    }

    public JobLogDTO getJobLogDTO() {
        return jobLogDTO;
    }

    public void setJobLogDTO(JobLogDTO jobLogDTO) {
        this.jobLogDTO = jobLogDTO;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }
    
    public AdHocLoadSettlementDTO getAdHocLoadSettlementDTO() {
        return adHocLoadSettlementDTO;
    }

    public void setAdHocLoadSettlementDTO(AdHocLoadSettlementDTO adHocLoadSettlementDTO) {
        this.adHocLoadSettlementDTO = adHocLoadSettlementDTO;
    }

    public String[] getAdHocLoadPickedUpRecord() {
        return adHocLoadPickedUpRecord;
    }

    public void setAdHocLoadPickedUpRecord(String[] adHocLoadPickedUpRecord) {
        this.adHocLoadPickedUpRecord = adHocLoadPickedUpRecord;
    }
    
    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public void reset() {
        customerDTO = null;
        cardDTO = null;
        orderDTO = null;
        payAsYouGoItemDTO = null;
        bacsSettlementDTO = null;
        adHocLoadSettlementDTO = null;
        bacsRequestHandledRecord = null;
        bacsRejectRecord = null;
        adHocLoadPickedUpRecord=null;
        jobLogDTO = null;
        jobDataMap = null;
    }

    
    
}
