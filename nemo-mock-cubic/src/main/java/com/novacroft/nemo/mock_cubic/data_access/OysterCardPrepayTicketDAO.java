package com.novacroft.nemo.mock_cubic.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayTicket;


/**
* TfL Oystercardprepayticket data access class implementation.
*/

@Repository("oysterCardPrepayTicketDAO")
public class OysterCardPrepayTicketDAO extends BaseDAOImpl<OysterCardPrepayTicket> {
}
