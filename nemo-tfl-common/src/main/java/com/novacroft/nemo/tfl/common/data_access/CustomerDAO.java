package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Customer;
import org.springframework.stereotype.Repository;

/**
 * TfL Customer data access class implementation.
 */
@Repository("customerDAO")
public class CustomerDAO extends BaseDAOImpl<Customer> {

}
