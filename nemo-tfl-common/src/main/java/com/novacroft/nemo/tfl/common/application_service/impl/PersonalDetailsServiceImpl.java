package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.novacroft.nemo.common.utils.Converter.convert;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.*;

/**
 * personal details service implementation
 */
@Service(value = "personalDetailsService")
public class PersonalDetailsServiceImpl implements PersonalDetailsService {
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected ContactDataService contactDataService;
    @Autowired
    protected CustomerPreferencesDataService customerPreferencesDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected SelectListService selectListService;

    @Override
    public PersonalDetailsCmdImpl getPersonalDetails(String username) {
        return getPersonalDetails(username, null);
    }

    @Override
    public PersonalDetailsCmdImpl getPersonalDetailsByCustomerId(Long customerId) {
        return getPersonalDetails(null, customerId);
    }

    @Override
    @Transactional
    public PersonalDetailsCmdImpl updatePersonalDetails(PersonalDetailsCmdImpl cmd) {
        updateCustomer(cmd);
        updateAddress(cmd);
        updateHomePhoneContact(cmd);
        updateMobilePhoneContact(cmd);
        updatePreferences(cmd);
        return cmd;
    }

    @Override
    public PersonalDetailsCmdImpl getPersonalDetailsByCardNumber(String cardNumber) {
        CardDTO cardDTO = this.cardDataService.findByCardNumber(cardNumber);
        CustomerDTO customerDTO = this.customerDataService.findById(cardDTO.getCustomerId());
        return getPersonalDetailsByCustomerId(customerDTO.getId());
    }

    protected PersonalDetailsCmdImpl getPersonalDetails(String username, Long customerId) {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(customerId);
        try {
            addCustomerToCommand(cmd, username, customerId);
        } catch (AssertionError e) {
            cmd.setCustomerDeactivated(true);
        }
        addCustomerToCommand(cmd);
        addAddressToCommand(cmd);
        addHomePhoneContactToCommand(cmd);
        addMobilePhoneContactToCommand(cmd);
        addPreferencesToCommand(cmd);
        return cmd;
    }

    protected void addCustomerToCommand(PersonalDetailsCmdImpl cmd, String username, Long customerId) {
        CustomerDTO customerDTO = getCustomer(username, customerId);
        assert (customerDTO != null);
        assert (customerDTO.getId() != null);
        cmd.setCustomerId(customerDTO.getId());
        convert(customerDTO, cmd);
        addCustomerDeactivatedToCommand(cmd, customerDTO);
        addCustomerDeactivationReasonToCommand(cmd, customerDTO);
    }

    protected void addCustomerDeactivatedToCommand(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        cmd.setCustomerDeactivated(customerDTO.getDeactivated() == CUSTOMER_DEACTIVATED ? true : false);
    }

    protected void addCustomerDeactivationReasonToCommand(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        assert (customerDTO != null);
        if (customerDTO.getDeactivationReason() != null) {
            if (isCustomerDeactivationReasonInCustomerDeactivationReasonList(customerDTO.getDeactivationReason())) {
                cmd.setCustomerDeactivationReason(customerDTO.getDeactivationReason());
            } else {
                cmd.setCustomerDeactivationReason(CUSTOMER_DEACTIVATION_REASON_OTHER);
                cmd.setCustomerDeactivationReasonOther(customerDTO.getDeactivationReason());
            }
        }
    }

    protected boolean isCustomerDeactivationReasonInCustomerDeactivationReasonList(String customerDeactivationReason) {
        SelectListDTO selectList = selectListService.getSelectList(PageSelectList.CUSTOMER_DEACTIVATION_REASONS);
        List<SelectListOptionDTO> selectListOptions = selectList.getOptions();
        for (SelectListOptionDTO selectListOption : selectListOptions) {
            if (customerDeactivationReason.equalsIgnoreCase(selectListOption.getValue())) {
                return true;
            }
        }
        return false;
    }

    protected void addCustomerToCommand(PersonalDetailsCmdImpl cmd) {
        assert (cmd.getCustomerId() != null);
        CustomerDTO customer = this.customerDataService.findByCustomerId(cmd.getCustomerId());
        assert (customer != null);
        cmd.setCustomerId(customer.getId());
        convert(customer, cmd);
    }

    protected void addAddressToCommand(PersonalDetailsCmdImpl cmd) {
        assert (cmd.getAddressId() != null);
        AddressDTO address = this.addressDataService.findById(cmd.getAddressId());
        assert (address != null);
        cmd.setAddressId(address.getId());
        convert(address, cmd);
    }

    protected void addHomePhoneContactToCommand(PersonalDetailsCmdImpl cmd) {
        ContactDTO homePhoneContact = this.contactDataService.findHomePhoneByCustomerId(cmd.getCustomerId());
        if (homePhoneContact != null) {
            cmd.setHomePhoneContactId(homePhoneContact.getId());
            cmd.setHomePhone(homePhoneContact.getValue());
        }
    }

    protected void addMobilePhoneContactToCommand(PersonalDetailsCmdImpl cmd) {
        ContactDTO mobilePhoneContact = this.contactDataService.findMobilePhoneByCustomerId(cmd.getCustomerId());
        if (mobilePhoneContact != null) {
            cmd.setMobilePhoneContactId(mobilePhoneContact.getId());
            cmd.setMobilePhone(mobilePhoneContact.getValue());
        }
    }

    protected void addPreferencesToCommand(PersonalDetailsCmdImpl cmd) {
        CustomerPreferencesDTO customerPreferences = this.customerPreferencesDataService.findByCustomerId(cmd.getCustomerId());
        if (customerPreferences != null) {
            cmd.setCustomerPreferencesId(customerPreferences.getId());
            cmd.setCanTflContact(customerPreferences.getCanTflContact());
            cmd.setCanThirdPartyContact(customerPreferences.getCanThirdPartyContact());
            cmd.setStationId(customerPreferences.getStationId());
        }
    }

    protected CustomerDTO getCustomer(String username, Long customerId) {
        assert (username != null || customerId != null);
        return (username != null) ? this.customerDataService.findByUsernameOrEmail(username) :
                this.customerDataService.findByCustomerId(customerId);

    }

    protected void updateCustomer(PersonalDetailsCmdImpl cmd) {
        CustomerDTO customerDTO = this.customerDataService.findByCustomerId(cmd.getCustomerId());
        assert (customerDTO != null);
        convert(cmd, customerDTO);
        updateCustomerDeactivated(cmd, customerDTO);
        updateCustomerDeactivationReasonIfCustomerDeactivated(cmd, customerDTO);
        customerDTO = this.customerDataService.createOrUpdate(customerDTO);
        convert(customerDTO, cmd);
        setFlagForEnableWebAccountDeactivationAlert(cmd);
    }

    protected void updateAddress(PersonalDetailsCmdImpl cmd) {
        AddressDTO address = this.addressDataService.findById(cmd.getAddressId());
        assert (address != null);
        convert(cmd, address);
        address = this.addressDataService.createOrUpdate(address);
        convert(address, cmd);
    }

    protected void updateCustomerDeactivated(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        customerDTO.setDeactivated(cmd.getCustomerDeactivated() ? CUSTOMER_DEACTIVATED : CUSTOMER_ACTIVE);
    }

    protected void updateCustomerDeactivationReasonIfCustomerDeactivated(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        if (cmd.getCustomerDeactivated()) {
            updateCustomerDeactivationReason(cmd, customerDTO);
        } else {
            updateCustomerDeactivationReasonAsEmpty(cmd, customerDTO);
        }
    }

    protected void updateCustomerDeactivationReason(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        if (isCustomerDeactivationReasonOther(cmd.getCustomerDeactivationReason())) {
            customerDTO.setDeactivationReason(cmd.getCustomerDeactivationReasonOther());
        } else {
            customerDTO.setDeactivationReason(cmd.getCustomerDeactivationReason());
            cmd.setCustomerDeactivationReasonOther(EMPTY_STRING);
        }
    }

    protected boolean isCustomerDeactivationReasonOther(String customerDeactivationReason) {
        return (customerDeactivationReason != null &&
                customerDeactivationReason.equalsIgnoreCase(CUSTOMER_DEACTIVATION_REASON_OTHER)) ? true : false;
    }

    protected void updateCustomerDeactivationReasonAsEmpty(PersonalDetailsCmdImpl cmd, CustomerDTO customerDTO) {
        customerDTO.setDeactivationReason(EMPTY_STRING);
        cmd.setCustomerDeactivationReason(EMPTY_STRING);
        customerDTO.setDeactivationReason(EMPTY_STRING);
        cmd.setCustomerDeactivationReasonOther(EMPTY_STRING);
    }

    protected void updateHomePhoneContact(PersonalDetailsCmdImpl cmd) {
        ContactDTO homePhoneContactDTO = null;
        if (cmd.getHomePhoneContactId() != null) {
            homePhoneContactDTO = this.contactDataService.findById(cmd.getHomePhoneContactId());
        } else {
            homePhoneContactDTO = new ContactDTO();
            homePhoneContactDTO.setCustomerId(cmd.getCustomerId());
        }
        homePhoneContactDTO.setValue(cmd.getHomePhone());
        homePhoneContactDTO = this.contactDataService.createOrUpdateHomePhone(homePhoneContactDTO);
        cmd.setHomePhoneContactId(homePhoneContactDTO.getId());
        cmd.setHomePhone(homePhoneContactDTO.getValue());
    }

    protected void updateMobilePhoneContact(PersonalDetailsCmdImpl cmd) {
        ContactDTO mobilePhoneContactDTO = null;
        if (cmd.getMobilePhoneContactId() != null) {
            mobilePhoneContactDTO = this.contactDataService.findById(cmd.getMobilePhoneContactId());
        } else {
            mobilePhoneContactDTO = new ContactDTO();
            mobilePhoneContactDTO.setCustomerId(cmd.getCustomerId());
        }
        mobilePhoneContactDTO.setValue(cmd.getMobilePhone());
        mobilePhoneContactDTO = this.contactDataService.createOrUpdateMobilePhone(mobilePhoneContactDTO);
        cmd.setMobilePhoneContactId(mobilePhoneContactDTO.getId());
        cmd.setMobilePhone(mobilePhoneContactDTO.getValue());
    }

    protected void updatePreferences(PersonalDetailsCmdImpl cmd) {
        CustomerPreferencesDTO customerPreferences = getCustomerPreferences(cmd);
        customerPreferences.setCanTflContact(cmd.getCanTflContact());
        customerPreferences.setCanThirdPartyContact(cmd.getCanThirdPartyContact());
        customerPreferences.setStationId(cmd.getStationId());
        customerPreferences = this.customerPreferencesDataService.createOrUpdate(customerPreferences);
        cmd.setCustomerPreferencesId(customerPreferences.getId());
    }

    protected CustomerPreferencesDTO getCustomerPreferences(PersonalDetailsCmdImpl cmd) {
        CustomerPreferencesDTO customerPreferences = this.customerPreferencesDataService.findByCustomerId(cmd.getCustomerId());
        if (customerPreferences == null) {
            if (cmd.getCustomerPreferencesId() != null) {
                customerPreferences = this.customerPreferencesDataService.findById(cmd.getCustomerPreferencesId());
            } else {
                customerPreferences = new CustomerPreferencesDTO();
                customerPreferences.setCustomerId(cmd.getCustomerId());
            }
        }
        return customerPreferences;
    }

    protected void setFlagForEnableWebAccountDeactivationAlert(PersonalDetailsCmdImpl cmd) {
        cmd.setShowWebAccountDeactivationEnableFlag(true);
    }

}
