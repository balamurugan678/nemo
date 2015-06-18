package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Settlement;

/**
 * Settlement data access
 */
@Repository("settlementDAO")
public class SettlementDAO extends BaseDAOImpl<Settlement> {
}
