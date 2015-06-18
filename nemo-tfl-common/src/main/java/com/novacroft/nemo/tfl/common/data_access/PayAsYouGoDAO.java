package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGo;
import org.springframework.stereotype.Repository;

/**
 * TfL pay as you go data access class implementation.
 */
@Repository("payAsYouGoDAO")
public class PayAsYouGoDAO extends BaseDAOImpl<PayAsYouGo> {

}
