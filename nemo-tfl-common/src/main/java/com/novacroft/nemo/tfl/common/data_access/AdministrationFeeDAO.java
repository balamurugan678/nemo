package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import org.springframework.stereotype.Repository;

/**
 * TfL administration fee data access class implementation.
 */
@Repository("administrationFeeDAO")
public class AdministrationFeeDAO extends BaseDAOImpl<AdministrationFee> {

}
