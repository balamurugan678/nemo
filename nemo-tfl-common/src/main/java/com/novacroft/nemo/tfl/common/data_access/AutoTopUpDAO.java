package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import org.springframework.stereotype.Repository;

/**
 * TfL auto top-up data access class implementation.
 */
@Repository("autoTopUpDAO")
public class AutoTopUpDAO extends BaseDAOImpl<AutoTopUp> {

}
