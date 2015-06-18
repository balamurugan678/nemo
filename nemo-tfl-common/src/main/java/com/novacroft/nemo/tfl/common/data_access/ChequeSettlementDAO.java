package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import org.springframework.stereotype.Repository;

/**
 * Cheque settlement data access
 */
@Repository("chequeSettlementDAO")
public class ChequeSettlementDAO extends BaseDAOImpl<ChequeSettlement> {
}
