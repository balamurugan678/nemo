package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;

/**
 * TfL card refundable deposit data access class implementation.
 */
@Repository("cardRefundableDepositDAO")
public class CardRefundableDepositDAO extends BaseDAOImpl<CardRefundableDeposit> {

}
