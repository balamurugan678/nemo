package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_ADDRESS_LINE_1;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_ADDRESS_LINE_3;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_COUNTRY;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_CUSTOMER_ID;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_EMAIL_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_FIRST_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_HOME_PHONE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_HOUSE_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_HOUSE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_LAST_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_MIDDLE_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_MOBILE_PHONE_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_POST_CODE;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_STREET_NAME;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_TFL_MARKETING_OPTIN;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_TITLE_DESCRIPTION;
import static com.novacroft.nemo.tfl.common.constant.SingleSignOnConstant.PARAMETER_TOC_MARKETING_OPTIN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.google.gson.Gson;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

public final class SingleSignOnUtil {
    
    public static boolean isCustomerEqual(CustomerDTO ssoCustomer, CustomerDTO localCustomer) {
        return new EqualsBuilder()
                    .append(ssoCustomer.getTflMasterId(), localCustomer.getTflMasterId())
                    .append(ssoCustomer.getInitials(), localCustomer.getInitials())
                    .append(ssoCustomer.getFirstName(), localCustomer.getFirstName())
                    .append(ssoCustomer.getLastName(), localCustomer.getLastName())
                    .append(ssoCustomer.getTitle(), localCustomer.getTitle())
                    .append(ssoCustomer.getEmailAddress(), localCustomer.getEmailAddress())
                    .append(ssoCustomer.getUsername(), localCustomer.getUsername())
                    .isEquals();
    }
    
    public static boolean isAddressEqual(AddressDTO ssoAddress, AddressDTO localAddress) {
        return new EqualsBuilder()
                    .append(ssoAddress.getPostcode(), localAddress.getPostcode())
                    .append(ssoAddress.getCountryName(), localAddress.getCountryName())
                    .append(ssoAddress.getHouseNameNumber(), localAddress.getHouseNameNumber())
                    .append(ssoAddress.getStreet(), localAddress.getStreet())
                    .append(ssoAddress.getTown(), localAddress.getTown())
                    .isEquals();
    }
    
    public static boolean isCountryEqual(CountryDTO ssoCountryDTO, CountryDTO localCountryDTO) {
        return StringUtils.equals(ssoCountryDTO.getName(), localCountryDTO.getName());
    }
    
    public static boolean isContactEqual(List<ContactDTO> ssoContacts, List<ContactDTO> localContacts) {
        return CollectionUtils.isEqualCollection(ssoContacts, localContacts);
    }
    
    public static boolean isCustomerPreferencesEqual(CustomerPreferencesDTO ssoPreference, CustomerPreferencesDTO localPreference) {
        return new EqualsBuilder()
                    .append(ssoPreference.getCanTflContact(), localPreference.getCanTflContact())
                    .append(ssoPreference.getCanThirdPartyContact(), localPreference.getCanThirdPartyContact())
                    .isEquals();
    }
    
    public static void updateLocalAddress(AddressDTO ssoAddressDTO, AddressDTO localAddressDTO) {
        localAddressDTO.setPostcode(ssoAddressDTO.getPostcode());
        localAddressDTO.setHouseNameNumber(ssoAddressDTO.getHouseNameNumber());
        localAddressDTO.setStreet(ssoAddressDTO.getStreet());
        localAddressDTO.setTown(ssoAddressDTO.getTown());
    }
    
    public static void updateLocalCustomer(CustomerDTO ssoCustomerDTO, CustomerDTO localCustomerDTO) {
        localCustomerDTO.setTitle(ssoCustomerDTO.getTitle());
        localCustomerDTO.setFirstName(ssoCustomerDTO.getFirstName());
        localCustomerDTO.setInitials(ssoCustomerDTO.getInitials());
        localCustomerDTO.setLastName(ssoCustomerDTO.getLastName());
        localCustomerDTO.setEmailAddress(ssoCustomerDTO.getEmailAddress());
        localCustomerDTO.setUsername(ssoCustomerDTO.getUsername());
    }
    
    public static void updateLocalContacts(List<ContactDTO> ssoContacts, List<ContactDTO> localContacts) {
        for (ContactDTO ssoContact : ssoContacts) {
            ContactDTO localContact = findContactByType(localContacts, ssoContact.getType());
            if (localContact == null) {
                localContacts.add(ssoContact);
            }
            else if (!ObjectUtils.equals(ssoContact, localContact)){
                localContact.setValue(ssoContact.getValue());
            }
        }
    }
    
    protected static ContactDTO findContactByType(List<ContactDTO> contactList, String type) {
        for (ContactDTO contactDto : contactList) {
            if (contactDto.getType().equals(type)) {
                return contactDto;
            }
        }
        return null;
    }
    
    public static void populateCustomerIdToContacts(List<ContactDTO> contacts, Long customerId) {
        for (ContactDTO contact : contacts) {
            contact.setCustomerId(customerId);
        }
    }
    
    public static void updateLocalCustomerPreferences(CustomerPreferencesDTO ssoPreferences, CustomerPreferencesDTO localPreferences) {
        localPreferences.setCanTflContact(ssoPreferences.getCanTflContact());
        localPreferences.setCanThirdPartyContact(ssoPreferences.getCanThirdPartyContact());
    }
    
    public static Map<String, String> generateSingleSignOnChangeSet(PersonalDetailsCmdImpl details) {
        Map<String, String> changeSet = new HashMap<>();
        changeSet.put(PARAMETER_CUSTOMER_ID, details.getTflMasterId().toString());
        changeSet.put(PARAMETER_TITLE_DESCRIPTION, details.getTitle());
        changeSet.put(PARAMETER_FIRST_NAME, details.getFirstName());
        changeSet.put(PARAMETER_MIDDLE_NAME, details.getInitials());
        changeSet.put(PARAMETER_LAST_NAME, details.getLastName());
        changeSet.put(PARAMETER_HOME_PHONE_NUMBER, details.getHomePhone());
        changeSet.put(PARAMETER_MOBILE_PHONE_NUMBER, details.getMobilePhone());
        changeSet.put(PARAMETER_EMAIL_ADDRESS, details.getEmailAddress());
        Integer tflOptIn = BooleanUtils.isTrue(details.getCanTflContact()) ? Integer.valueOf(1) : Integer.valueOf(0);
        changeSet.put(PARAMETER_TFL_MARKETING_OPTIN, tflOptIn.toString());
        Integer tocOptIn = BooleanUtils.isTrue(details.getCanThirdPartyContact()) ? Integer.valueOf(1) : Integer.valueOf(0);
        changeSet.put(PARAMETER_TOC_MARKETING_OPTIN, tocOptIn.toString());
        
        changeSet.put(PARAMETER_POST_CODE, details.getPostcode());
        String[] houseNumberName = AddressFormatUtil.extractHouseNameNumber(details.getHouseNameNumber());
        changeSet.put(PARAMETER_HOUSE_NUMBER, houseNumberName[0]);
        changeSet.put(PARAMETER_HOUSE_NAME, houseNumberName[1]);
        changeSet.put(PARAMETER_STREET_NAME, details.getStreet());
        String line1 = AddressFormatUtil.formatLine1(details.getHouseNameNumber(), details.getStreet());
        changeSet.put(PARAMETER_ADDRESS_LINE_1, line1);
        changeSet.put(PARAMETER_ADDRESS_LINE_3, details.getTown());
        
        CountryDTO countryDto = details.getCountry();
        changeSet.put(PARAMETER_COUNTRY, countryDto.getName());
        
        return changeSet;
    }
    
    public static SingleSignOnResponseDTO createSingleSignOnResponseDTO(Object jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        else {
            Gson gson = new Gson();
            String receivedJson = gson.toJson(jsonObject);
            return gson.fromJson(receivedJson, SingleSignOnResponseDTO.class);
        }
    }
    
    private SingleSignOnUtil() {}
}
