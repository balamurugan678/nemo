package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.PaymentCard;
import org.springframework.stereotype.Repository;

/**
 * TfL payment card data access class implementation.
 */
@Repository("paymentCardDAO")
public class PaymentCardDAO extends BaseDAOImpl<PaymentCard> {
}
