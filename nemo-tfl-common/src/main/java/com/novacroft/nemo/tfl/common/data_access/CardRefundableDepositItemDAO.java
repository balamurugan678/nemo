package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;

/**
 * TfL card refundable deposit data access class implementation.
 */
@Repository("cardRefundableDepositItemDAO")
public class CardRefundableDepositItemDAO extends BaseDAOImpl<CardRefundableDepositItem> {

}
