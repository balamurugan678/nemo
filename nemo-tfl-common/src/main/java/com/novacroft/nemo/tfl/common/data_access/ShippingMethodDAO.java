package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.ShippingMethod;
import org.springframework.stereotype.Repository;

/**
 * TfL shipping method data access class implementation.
 */
@Repository("shippingMethodDAO")
public class ShippingMethodDAO extends BaseDAOImpl<ShippingMethod> {

}
