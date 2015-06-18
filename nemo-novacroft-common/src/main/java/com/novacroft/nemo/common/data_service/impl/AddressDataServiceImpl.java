package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.AddressConverterImpl;
import com.novacroft.nemo.common.data_access.AddressDAO;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Address data service implementation
 */
@Service(value = "addressDataService")
@Transactional(readOnly = true)
public class AddressDataServiceImpl extends BaseDataServiceImpl<Address, AddressDTO> implements AddressDataService {
    static final Logger logger = LoggerFactory.getLogger(AddressDataServiceImpl.class);

    public AddressDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(AddressDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AddressConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Address getNewEntity() {
        return new Address();
    }

    @Override
    public AddressDTO findByEmailAddressAndPostcode(String username, String postcode) {
        final String hsql = "select a from Address a, Customer c, WebAccount w " +
                "where lower(a.postcode) = ? and c.addressId = a.id and w.customerId = c.id " +
                "and (lower(w.username) = ? or lower(w.emailAddress) = ?)";
        Address address =
                dao.findByQueryUniqueResult(hsql, postcode.toLowerCase(), username.toLowerCase(), username.toLowerCase());
        return (address != null) ? this.converter.convertEntityToDto(address) : null;
    }
}
