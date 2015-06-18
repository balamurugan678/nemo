package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.CustomerPreferences;
import org.springframework.stereotype.Repository;

/**
 * TfL Customer Preferences DAO implementation
 */
@Repository("customerPreferencesDAO")
public class CustomerPreferencesDAO extends BaseDAOImpl<CustomerPreferences> {
}
