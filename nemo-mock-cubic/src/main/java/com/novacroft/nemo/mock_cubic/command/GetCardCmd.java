package com.novacroft.nemo.mock_cubic.command;

import com.novacroft.nemo.mock_cubic.constant.CardAction;

public class GetCardCmd {
    protected String prestigeId;
    protected String response;
    protected CardAction cardAction;
    
    public GetCardCmd(){}

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public CardAction getCardAction() {
        return cardAction;
    }

    public void setCardAction(CardAction cardAction) {
        this.cardAction = cardAction;
    }

    
    

}
