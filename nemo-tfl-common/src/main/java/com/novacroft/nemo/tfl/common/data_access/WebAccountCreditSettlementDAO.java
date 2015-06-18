package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import org.springframework.stereotype.Repository;

/**
 * Settlement (by Web Account Credit) data access
 */
@Repository("webAccountCreditSettlementDAO")
public class WebAccountCreditSettlementDAO extends BaseDAOImpl<WebAccountCreditSettlement> {
}
