package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import org.springframework.stereotype.Repository;

/**
 * Settlement (for Auto Load Change) data access
 */
@Repository("autoLoadChangeSettlementDAO")
public class AutoLoadChangeSettlementDAO extends BaseDAOImpl<AutoLoadChangeSettlement> {
}
