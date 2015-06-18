package com.novacroft.nemo.tfl.common.transfer;

public class CardUpdatePrePayValueRequestDTO extends CardUpdateRequestDTO {
    
    protected Integer prePayValue;
    protected Integer currency;
    
    public CardUpdatePrePayValueRequestDTO() {
    }
    
    public CardUpdatePrePayValueRequestDTO(String prestigeId, String action, Long pickupLocation,  String userId, String password, 
                    Integer prePayValue,Integer currency) {
        this.prestigeId = prestigeId;
        this.action = action;
        this.pickupLocation = pickupLocation;
        this.userId = userId;
        this.password = password;
        this.prePayValue = prePayValue;
        this.currency = currency;
    }

    public Integer getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Integer prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
    


    

}
