package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpPerformedSettlement;

/**
 * data access object to insert auto top-up performed event into settlement table
 */
@Repository("autoTopUpPerformedSettlementDAO")
public class AutoTopUpPerformedSettlementDAO extends BaseDAOImpl<AutoTopUpPerformedSettlement> {
}
