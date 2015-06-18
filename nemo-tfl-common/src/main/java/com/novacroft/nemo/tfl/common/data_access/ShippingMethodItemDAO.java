package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import org.springframework.stereotype.Repository;

/**
 * TfL shipping method item data access class implementation.
 */
@Repository("shippingMethodItemDAO")
public class ShippingMethodItemDAO extends BaseDAOImpl<ShippingMethodItem> {

}
