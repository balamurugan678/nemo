package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.Card;
import org.springframework.stereotype.Repository;

/**
 * TfL card data access class implementation.
 */
@Repository("cardDAO")
public class CardDAO extends BaseDAOImpl<Card> {

}
