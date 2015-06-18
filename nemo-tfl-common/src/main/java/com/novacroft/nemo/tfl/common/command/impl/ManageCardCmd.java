package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;


public class ManageCardCmd extends CartCmdImpl {

    protected Integer autoTopUpState;
    protected Boolean autoTopUpEnabled;
    protected Long  paymentCardID;
    protected OrderDTO order;
    protected String startDateforAutoTopUpCardActivate;
    protected String endDateforAutoTopUpCardActivate;
    protected List<PaymentCardCmdImpl> paymentCards;
    protected Long orderNumber;
    protected String stationName;
    protected ManageCardCmd commandObject;
    protected String paymentMethod;
    protected boolean disableAutoTopUpConfigurationChange;
    protected Integer autoTopUpStateExistingPendingAmount;
    protected String autoTopUpActivity;
    protected Boolean isStartDateTomorrow;
    
    public ManageCardCmd getCommandObject() {
		return commandObject;
	}

	public void setCommandObject(ManageCardCmd commandObject) {
		this.commandObject = commandObject;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

    public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

    @Override
    public String getStationName() {
		return stationName;
	}

    @Override
    public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStartDateforAutoTopUpCardActivate() {
		return startDateforAutoTopUpCardActivate;
	}

	public void setStartDateforAutoTopUpCardActivate(
			String startDateforAutoTopUpCardActivate) {
		this.startDateforAutoTopUpCardActivate = startDateforAutoTopUpCardActivate;
	}

	public String getEndDateforAutoTopUpCardActivate() {
		return endDateforAutoTopUpCardActivate;
	}

	public void setEndDateforAutoTopUpCardActivate(
			String endDateforAutoTopUpCardActivate) {
		this.endDateforAutoTopUpCardActivate = endDateforAutoTopUpCardActivate;
	}

	public List<PaymentCardCmdImpl> getPaymentCards() {
		return paymentCards;
	}

	public void setPaymentCards(List<PaymentCardCmdImpl> paymentCards) {
		this.paymentCards = paymentCards;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public Long getPaymentCardID() {
		return paymentCardID;
	}

	public void setPaymentCardID(Long paymentCardID) {
		this.paymentCardID = paymentCardID;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public ManageCardCmd() {
    }
  
    public Integer getAutoTopUpState() {
        return autoTopUpState;
    }

    public void setAutoTopUpState(Integer autoTopUpState) {
       this.autoTopUpState = autoTopUpState;
    }

    public Boolean getAutoTopUpEnabled() {
        return autoTopUpEnabled;
    }

    public void setAutoTopUpEnabled(Boolean autoTopUpEnabled) {
        this.autoTopUpEnabled = autoTopUpEnabled;
    }
    
    public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

    public boolean isDisableAutoTopUpConfigurationChange() {
        return disableAutoTopUpConfigurationChange;
    }

    public void setDisableAutoTopUpConfigurationChange(boolean disableAutoTopUpConfigurationChange) {
        this.disableAutoTopUpConfigurationChange = disableAutoTopUpConfigurationChange;
    }
    
    public Integer getAutoTopUpStateExistingPendingAmount() {
		return autoTopUpStateExistingPendingAmount;
	}

	public void setAutoTopUpStateExistingPendingAmount(Integer autoTopUpStateExistingPendingAmount) {
		this.autoTopUpStateExistingPendingAmount = autoTopUpStateExistingPendingAmount;
	}
	
    @Override
    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    @Override
    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }
    
    public void setIsStartDateTomorrow(Boolean isStartDateTomorrow) {
    	this.isStartDateTomorrow = isStartDateTomorrow;
    }
    
    public Boolean getIsStartDateTomorrow() {
    	return isStartDateTomorrow;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ManageCardCmd that = (ManageCardCmd) object;
        
        return new EqualsBuilder()
            .append(autoTopUpState, that.autoTopUpState)
            .append(stationId, that.stationId)
            .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.MANAGE_CARD_CMD.initialiser(), HashCodeSeed.MANAGE_CARD_CMD.multiplier())
                .append(autoTopUpState).append(stationId).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("autoTopUpState", autoTopUpState).append("stationId", stationId).toString();
    }

}
