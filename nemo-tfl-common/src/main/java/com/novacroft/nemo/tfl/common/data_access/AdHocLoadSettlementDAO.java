package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AdHocLoadSettlement;
import org.springframework.stereotype.Repository;

/**
 * Settlement (by Ad-Hoc Load) data access
 */
@Repository("adHocLoadSettlementDAO")
public class AdHocLoadSettlementDAO extends BaseDAOImpl<AdHocLoadSettlement> {
}
