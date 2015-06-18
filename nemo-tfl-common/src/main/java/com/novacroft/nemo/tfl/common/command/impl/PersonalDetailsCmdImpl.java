package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.tfl.common.command.UserCredentialsCmd;

/**
 * Personal details command (MVC "view") class TfL definition.
 */
public class PersonalDetailsCmdImpl extends CommonOrderCardCmd
        implements  UserCredentialsCmd{
    protected String securityOption;
    protected String securityPassword;
    protected String dateOfBirth;
    protected String addressForPostcode;
    protected Boolean canTflContact = Boolean.TRUE;
    protected Boolean canThirdPartyContact = Boolean.TRUE;
    protected String username;
    protected String newPassword;
    protected String newPasswordConfirmation;
    protected Long homePhoneContactId;
    protected Long mobilePhoneContactId;
    protected Long customerPreferencesId;
    protected Long webAccountId;
    protected Long customerId;
    protected Long addressId;
    protected Long stationId;
    protected Boolean customerDeactivated = Boolean.FALSE;
    protected String customerDeactivationReason;
    protected String customerDeactivationReasonOther;
    protected boolean showWebAccountDeactivationEnableFlag=false;
    protected Long tflMasterId;
    
    public String getSecurityOption() {
        return securityOption;
    }

    public void setSecurityOption(String securityOption) {
        this.securityOption = securityOption;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getNoTflServiceInfo() {
        return getNotCanTflContact();
    }

    public void setNoTflServiceInfo(Boolean noTflServiceInfo) {
        setNotCanTflContact(noTflServiceInfo);
    }

    public Boolean getOptOutToc() {
        return getNotCanThirdPartyContact();
    }

    public void setOptOutToc(Boolean optOutToc) {
        setNotCanThirdPartyContact(optOutToc);
    }

    public String getAddressForPostcode() {
        return addressForPostcode;
    }

    public void setAddressForPostcode(String addressForPostcode) {
        this.addressForPostcode = addressForPostcode;
    }

    public Boolean getCanTflContact() {
        return canTflContact;
    }

    public void setCanTflContact(Boolean canTflContact) {
        this.canTflContact = canTflContact;
    }

    public Boolean getCanThirdPartyContact() {
        return canThirdPartyContact;
    }

    public void setCanThirdPartyContact(Boolean canThirdPartyContact) {
        this.canThirdPartyContact = canThirdPartyContact;
    }

    public Boolean getNotCanTflContact() {
        return !canTflContact;
    }

    public void setNotCanTflContact(Boolean canTflContact) {
        this.canTflContact = !canTflContact;
    }

    public Boolean getNotCanThirdPartyContact() {
        return !canThirdPartyContact;
    }

    public void setNotCanThirdPartyContact(Boolean canThirdPartyContact) {
        this.canThirdPartyContact = !canThirdPartyContact;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public Long getHomePhoneContactId() {
        return homePhoneContactId;
    }

    public void setHomePhoneContactId(Long homePhoneContactId) {
        this.homePhoneContactId = homePhoneContactId;
    }

    public Long getMobilePhoneContactId() {
        return mobilePhoneContactId;
    }

    public void setMobilePhoneContactId(Long mobilePhoneContactId) {
        this.mobilePhoneContactId = mobilePhoneContactId;
    }

    public Long getCustomerPreferencesId() {
        return customerPreferencesId;
    }

    public void setCustomerPreferencesId(Long customerPreferencesId) {
        this.customerPreferencesId = customerPreferencesId;
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public Boolean getCustomerDeactivated() {
		return customerDeactivated;
	}

	public void setCustomerDeactivated(Boolean customerDeactivated) {
		this.customerDeactivated = customerDeactivated;
	}

	public String getCustomerDeactivationReason() {
		return customerDeactivationReason;
	}

	public void setCustomerDeactivationReason(String customerDeactivationReason) {
		this.customerDeactivationReason = customerDeactivationReason;
	}

	public String getCustomerDeactivationReasonOther() {
		return customerDeactivationReasonOther;
	}

	public void setCustomerDeactivationReasonOther(
			String customerDeactivationReasonOther) {
		this.customerDeactivationReasonOther = customerDeactivationReasonOther;
	}

	public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

	public boolean isShowWebAccountDeactivationEnableFlag() {
		return showWebAccountDeactivationEnableFlag;
	}

	public void setShowWebAccountDeactivationEnableFlag(boolean showWebAccountDeactivationEnableFlag) {
		this.showWebAccountDeactivationEnableFlag = showWebAccountDeactivationEnableFlag;
	}
	
	public Long getTflMasterId() {
	    return tflMasterId;
	}
	
	public void setTflMasterId(Long tflMasterId) {
	    this.tflMasterId = tflMasterId;
	}
  
}
