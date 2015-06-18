package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

/**
 * Payment Card command class
 */
public class PaymentCardCmdImpl implements AddressCmd, CommonPostcodeCmd {
    protected PaymentCardDTO paymentCardDTO;
    protected Boolean inUseFlag = Boolean.FALSE;
    protected String addressForPostcode;

    public PaymentCardCmdImpl() {
    }

    public PaymentCardCmdImpl(PaymentCardDTO paymentCardDTO, Boolean inUseFlag) {
        this.paymentCardDTO = paymentCardDTO;
        this.inUseFlag = inUseFlag;
    }

    public PaymentCardDTO getPaymentCardDTO() {
        return paymentCardDTO;
    }

    public void setPaymentCardDTO(PaymentCardDTO paymentCardDTO) {
        this.paymentCardDTO = paymentCardDTO;
    }

    public Boolean getInUseFlag() {
        return inUseFlag;
    }

    public void setInUseFlag(Boolean inUseFlag) {
        this.inUseFlag = inUseFlag;
    }

    @Override
    public String getHouseNameNumber() {
        return paymentCardDTO.getAddressDTO().getHouseNameNumber();
    }

    @Override
    public String getStreet() {
        return paymentCardDTO.getAddressDTO().getStreet();
    }

    @Override
    public String getTown() {
        return paymentCardDTO.getAddressDTO().getTown();
    }

    @Override
    public String getPostcode() {
        return paymentCardDTO.getAddressDTO().getPostcode();
    }

    public String getAddressForPostcode() {
        return addressForPostcode;
    }

    public void setAddressForPostcode(String addressForPostcode) {
        this.addressForPostcode = addressForPostcode;
    }

	@Override
	public void setCounty(String county) {
		
	}

	@Override
	public CountryDTO getCountry() {
		
		return null;
	}
    
}
