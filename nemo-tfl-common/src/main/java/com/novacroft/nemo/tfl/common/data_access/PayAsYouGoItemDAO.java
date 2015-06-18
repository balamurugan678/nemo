package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import org.springframework.stereotype.Repository;

/**
 * TfL pay as you go item data access class implementation.
 */
@Repository("payAsYouGoItemDAO")
public class PayAsYouGoItemDAO extends BaseDAOImpl<PayAsYouGoItem> {

}
