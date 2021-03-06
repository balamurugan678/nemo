package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;

/**
 * TfL auto top-up item data access class implementation.
 */
@Repository("autoTopUpItemDAO")
public class AutoTopUpItemDAO extends BaseDAOImpl<AutoTopUpConfigurationItem> {

}
