package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.Contact;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;

/**
 * Contact data service specification
 */
public interface ContactDataService extends BaseDataService<Contact, ContactDTO> {
	ContactDTO findHomePhoneByCustomerId(Long customerId);

	ContactDTO findMobilePhoneByCustomerId(Long customerId);

	ContactDTO createOrUpdateHomePhone(ContactDTO homePhoneContactDTO);

	ContactDTO createOrUpdateMobilePhone(ContactDTO mobilePhoneContactDTO);
	
	List<ContactDTO> findPhoneNumbersByCustomerId(Long customerId);
}
