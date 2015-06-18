package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Contact;
import org.springframework.stereotype.Repository;

/**
 * TfL Contact data access class implementation.
 */
@Repository("contactDAO")
public class ContactDAO extends BaseDAOImpl<Contact> {

}
