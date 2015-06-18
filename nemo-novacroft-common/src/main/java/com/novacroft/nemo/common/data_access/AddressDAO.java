package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.Address;
import org.springframework.stereotype.Repository;

/**
 * TfL Address data access class implementation.
 */
@Repository("addressDAO")
public class AddressDAO extends BaseDAOImpl<Address> {

}
