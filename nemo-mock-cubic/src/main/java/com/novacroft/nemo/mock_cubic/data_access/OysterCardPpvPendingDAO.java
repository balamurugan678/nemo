package com.novacroft.nemo.mock_cubic.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPpvPending;


/**
* TfL Oystercardppvpending data access class implementation.
*/

@Repository("oysterCardPpvPendingDAO")
public class OysterCardPpvPendingDAO extends BaseDAOImpl<OysterCardPpvPending> {
}
