package com.novacroft.nemo.tfl.common.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO implements Serializable {

    private static final long serialVersionUID = -1618038526099464923L;
    
    private List<ShoppingCartItemDTO> itemList;
    private Integer subTotalAmount;
    private Long stationId;
    private String station;
    private String cardNumber;
    private String firstName;
    private String lastName;
    private Long cardId;
    private String userName;
    private String applicationID;
    private String oysterCardNumber;
    private Long customerId;
    private String ticketType;

    public List<ShoppingCartItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<ShoppingCartItemDTO> itemList) {
        this.itemList = itemList;
    }

    public Integer getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Integer subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationId) {
        this.applicationID = applicationId;
    }
    
    public void removeItem(ShoppingCartItemDTO item) {
        itemList.remove(item);
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOysterCardNumber() {
        return oysterCardNumber;
    }

    public void setOysterCardNumber(String oysterCardNumber) {
        this.oysterCardNumber = oysterCardNumber;
    }

    public void clearList() {
        this.itemList = null;
        this.itemList = new ArrayList<ShoppingCartItemDTO>();
    }
    
    public void addItem(ShoppingCartItemDTO item) {
        this.itemList.add(item);
    }
    
    public Long getCustomerId() {
    	return customerId;
    }
    
    public void setCustomerId(Long customerId) {
    	this.customerId = customerId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
