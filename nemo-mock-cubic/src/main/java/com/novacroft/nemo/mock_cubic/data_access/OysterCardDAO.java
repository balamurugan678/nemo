package com.novacroft.nemo.mock_cubic.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCard;


/**
* TfL Oystercard data access class implementation.
*/

@Repository("oysterCardDAO")
public class OysterCardDAO extends BaseDAOImpl<OysterCard> {
}
