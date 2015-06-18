package com.novacroft.nemo.mock_cubic.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayValue;


/**
* TfL Oystercardprepayvalue data access class implementation.
*/

@Repository("oysterCardPrepayValueDAO")
public class OysterCardPrepayValueDAO extends BaseDAOImpl<OysterCardPrepayValue> {
}
