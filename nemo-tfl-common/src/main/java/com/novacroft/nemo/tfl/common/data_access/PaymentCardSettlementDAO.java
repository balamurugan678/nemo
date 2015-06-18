package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.PaymentCardSettlement;
import org.springframework.stereotype.Repository;

/**
 * Settlement (by Payment Card) data access
 */
@Repository("paymentCardSettlementDAO")
public class PaymentCardSettlementDAO extends BaseDAOImpl<PaymentCardSettlement> {
}
