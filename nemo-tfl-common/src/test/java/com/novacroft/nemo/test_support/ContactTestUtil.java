package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.tfl.common.domain.Contact;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;

/**
 * Utilities for Contact tests
 */
public final class ContactTestUtil extends CommonContactTestUtil {
    
    public static List<ContactDTO> getContactDTOList() {
        List<ContactDTO> contactDTOList = new ArrayList<ContactDTO>();
        contactDTOList.add(getTestContactDTOHomePhone1());
        contactDTOList.add(getTestContactDTOMobilePhone1());
        return contactDTOList;
    }

    public static ContactDTO getTestContactDTOHomePhone1() {
        return getTestContactDTO(CONTACT_ID_1, NAME_1, VALUE_1, ContactType.HomePhone.name(), CUSTOMER_ID_1);
    }

    public static ContactDTO getTestContactDTOMobilePhone1() {
        return getTestContactDTO(CONTACT_ID_2, NAME_2, VALUE_2, ContactType.MobilePhone.name(), CUSTOMER_ID_1);
    }

    public static ContactDTO getTestContactDTO(Long contactId, String name, String value, String type, Long customerId) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contactId);
        contactDTO.setName(name);
        contactDTO.setValue(value);
        contactDTO.setType(type);
        contactDTO.setCustomerId(customerId);
        return contactDTO;
    }

    public static Contact getTestContactHomePhone1() {
        return getTestContact(CONTACT_ID_1, NAME_1, VALUE_1, ContactType.HomePhone.name(), CUSTOMER_ID_1);
    }

    public static Contact getTestContactHomePhone2() {
        return getTestContact(CONTACT_ID_2, NAME_2, VALUE_2, ContactType.HomePhone.name(), CUSTOMER_ID_1);
    }

    public static Contact getTestContactMobilePhone1() {
        return getTestContact(CONTACT_ID_1, NAME_1, VALUE_1, ContactType.MobilePhone.name(), CUSTOMER_ID_1);
    }

    public static Contact getTestContactMobilePhone2() {
        return getTestContact(CONTACT_ID_2, NAME_2, VALUE_2, ContactType.MobilePhone.name(), CUSTOMER_ID_1);
    }

    public static Contact getTestContact(Long contactId, String name, String value, String type, Long customerId) {
        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setName(name);
        contact.setValue(value);
        contact.setType(type);
        contact.setCustomerId(customerId);
        return contact;
    }

}
