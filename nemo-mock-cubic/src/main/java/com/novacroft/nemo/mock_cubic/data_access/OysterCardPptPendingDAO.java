package com.novacroft.nemo.mock_cubic.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPptPending;


/**
* TfL OysterCardPptPending data access class implementation.
*/

@Repository("oysterCardPptPendingDAO")
public class OysterCardPptPendingDAO extends BaseDAOImpl<OysterCardPptPending> {
}
