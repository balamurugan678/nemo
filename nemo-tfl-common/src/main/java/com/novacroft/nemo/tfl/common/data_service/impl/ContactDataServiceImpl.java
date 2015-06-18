package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.converter.impl.ContactConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ContactDAO;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.domain.Contact;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact data service implementation
 */
@Service(value = "contactDataService")
@Transactional(readOnly = true)
public class ContactDataServiceImpl extends BaseDataServiceImpl<Contact, ContactDTO> implements ContactDataService {
    static final Logger logger = LoggerFactory.getLogger(ContactDataServiceImpl.class);

    public ContactDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(ContactDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ContactConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Contact getNewEntity() {
        return new Contact();
    }

    @Override
    @Transactional
    public ContactDTO findHomePhoneByCustomerId(Long customerId) {
        return findByTypeAndCustomerId(ContactType.HomePhone, customerId);
    }

    @Override
    @Transactional
    public List<ContactDTO> findPhoneNumbersByCustomerId(Long customerId) {
        return findByTypesAndCustomerId(ContactType.HomePhone, ContactType.MobilePhone, customerId);
    }

    @Override
    @Transactional
    public ContactDTO findMobilePhoneByCustomerId(Long customerId) {
        return findByTypeAndCustomerId(ContactType.MobilePhone, customerId);
    }

    @SuppressWarnings("unchecked")
    public ContactDTO findByTypeAndCustomerId(ContactType type, Long customerId) {
        final String hsql = "select c from Contact c where c.type = ? and c.customerId = ?";
        List<Contact> results = dao.findByQuery(hsql, type.name(), customerId);
        if (results.size() > 1) {
            String msg = String.format(PrivateError.MORE_THAN_ONE_CONTACT_FOR_CUSTOMER.message(), type.name(), customerId);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<ContactDTO> findByTypesAndCustomerId(ContactType type1, ContactType type2, Long customerId) {
        final String hsql = "select c from Contact c where (c.type = ? or c.type = ?) and c.customerId = ?";
        List<Contact> results = dao.findByQuery(hsql, type1.name(), type2.name(), customerId);
        List<ContactDTO> contactDTOList = new ArrayList<ContactDTO>(); 
        if (results != null) {
            for (Contact entity : results) {
                contactDTOList.add(converter.convertEntityToDto(entity));
            }
            return contactDTOList;
        }
        return null;
    }

    @Override
    public ContactDTO createOrUpdateHomePhone(ContactDTO homePhoneContactDTO) {
        homePhoneContactDTO.setType(ContactType.HomePhone.name());
        return createOrUpdate(homePhoneContactDTO);
    }

    @Override
    public ContactDTO createOrUpdateMobilePhone(ContactDTO mobilePhoneContactDTO) {
        mobilePhoneContactDTO.setType(ContactType.MobilePhone.name());
        return createOrUpdate(mobilePhoneContactDTO);
    }
}
