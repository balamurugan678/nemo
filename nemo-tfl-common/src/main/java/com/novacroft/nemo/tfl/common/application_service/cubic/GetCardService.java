package com.novacroft.nemo.tfl.common.application_service.cubic;

import java.util.Date;

import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.common.transfer.PassengerAndDiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Get card service specification
 */

public interface GetCardService {
    CardInfoResponseV2DTO getCard(String cardNumber);
    CardInfoResponseV2DTO checkAndPopulateNodesExcludingLeafNodes(CardInfoResponseV2DTO cardInfoResponseV2DTO);
    JobCentrePlusDiscountDTO getJobCentrePlusDiscountDetails(String cardNumber);
    JobCentrePlusDiscountDTO getJobCentrePlusDiscountDetails(Long cardId);
    PassengerAndDiscountTypeDTO getPassengerAndDiscountType(String cardNumber, Date effectiveDate);
}
