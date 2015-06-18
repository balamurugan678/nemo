package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import org.springframework.stereotype.Repository;

/**
 * TfL administration fee item data access class implementation.
 */
@Repository("administrationFeeItemDAO")
public class AdministrationFeeItemDAO extends BaseDAOImpl<AdministrationFeeItem> {

}
