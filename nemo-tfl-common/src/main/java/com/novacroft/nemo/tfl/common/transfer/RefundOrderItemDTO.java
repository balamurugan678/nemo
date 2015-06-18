package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;

public class RefundOrderItemDTO extends AbstractBaseDTO {

    private static final long serialVersionUID = 1742621135855345919L;

    protected Long orderNumber;

    protected String oysterCardNumber;

    protected Long customerOrderId;

    protected String name;

    protected Integer amount;

    protected String stationName;

    protected Long stationId;

    protected Address address;

    protected String status;

    protected Date dateOfRefund;

    protected AddressDTO addressDTO;

    private String paymentMethod;

    public RefundOrderItemDTO() {

    }

    public RefundOrderItemDTO(Long id, Long orderNumber, String oysterCardNumber, Date dateOfRefund) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.oysterCardNumber = oysterCardNumber;
        this.dateOfRefund = dateOfRefund;
    }

    public RefundOrderItemDTO(Long id, Long orderNumber, String status, String oysterCardNumber, Date dateOfRefund, String firstName,
                    String lastName, Long stationId, Integer amount, Address address, String paymentMethod) {
        this(id, orderNumber, oysterCardNumber, dateOfRefund);
        this.name = StringUtil.isNotBlank(lastName) ? firstName + " " + lastName : firstName;
        this.amount = amount;
        this.address = address;
        this.stationId = stationId;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOysterCardNumber() {
        return oysterCardNumber;
    }

    public void setOysterCardNumber(String oysterCardNumber) {
        this.oysterCardNumber = oysterCardNumber;
    }

    public Long getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateOfRefund() {
        return dateOfRefund;
    }

    public void setDateOfRefund(Date dateOfRefund) {
        this.dateOfRefund = dateOfRefund;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
