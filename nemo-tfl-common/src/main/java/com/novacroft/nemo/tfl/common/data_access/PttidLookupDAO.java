package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.PseudoTransactionTypeLookup;
import org.springframework.stereotype.Repository;

/**
 * TfL product data access class implementation.
 */
@Repository("PttidLookupDAO")
public class PttidLookupDAO extends BaseDAOImpl<PseudoTransactionTypeLookup> {

}
