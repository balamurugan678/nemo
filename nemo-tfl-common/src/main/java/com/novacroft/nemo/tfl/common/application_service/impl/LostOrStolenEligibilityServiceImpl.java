package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.LostOrStolenEligibilityService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.OysterCardDiscountType;
import com.novacroft.nemo.tfl.common.constant.OysterCardPassengerType;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

@Service("lostOrStolenEligibilityService")
public class LostOrStolenEligibilityServiceImpl implements LostOrStolenEligibilityService {
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public Boolean isCardEligibleToBeReportedLostOrStolen(Long cardId) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        CardInfoResponseV2DTO cardInfoResponseV2DTO = this.getCardService.getCard(cardDTO.getCardNumber());
        return isEligible(cardInfoResponseV2DTO);
    }

    protected boolean isEligible(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return isAdultCard(cardInfoResponseV2DTO) && isNotEighteenPlusDiscountOnCard(cardInfoResponseV2DTO)
                        && isNotApprenticeDiscountOnCard(cardInfoResponseV2DTO);
    }

    protected boolean isAdultCard(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return OysterCardPassengerType.ADULT.code().equalsIgnoreCase(cardInfoResponseV2DTO.getPassengerType());
    }

    protected boolean isDiscountOnCard(CardInfoResponseV2DTO cardInfoResponseV2DTO, OysterCardDiscountType discountType) {
        return (isDiscountEntitlement1(cardInfoResponseV2DTO, discountType)) || (isDiscountEntitlement2(cardInfoResponseV2DTO, discountType))
                        || (isDiscountEntitlement3(cardInfoResponseV2DTO, discountType));
    }

    protected boolean isEighteenPlusDiscountOnCard(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return isDiscountOnCard(cardInfoResponseV2DTO, OysterCardDiscountType.EIGHTEEN_PLUS);
    }

    protected boolean isNotEighteenPlusDiscountOnCard(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return !isEighteenPlusDiscountOnCard(cardInfoResponseV2DTO);
    }

    protected boolean isApprenticeDiscountOnCard(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return isDiscountOnCard(cardInfoResponseV2DTO, OysterCardDiscountType.APPRENTICE);
    }

    protected boolean isNotApprenticeDiscountOnCard(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return !isApprenticeDiscountOnCard(cardInfoResponseV2DTO);
    }

    protected boolean isDiscountEntitlement1(CardInfoResponseV2DTO cardInfoResponseV2DTO, OysterCardDiscountType discountType) {
        return isDiscountEntitlement(cardInfoResponseV2DTO.getDiscountEntitlement1(), discountType)
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry1());
    }

    protected boolean isDiscountEntitlement2(CardInfoResponseV2DTO cardInfoResponseV2DTO, OysterCardDiscountType discountType) {
        return isDiscountEntitlement(cardInfoResponseV2DTO.getDiscountEntitlement1(), discountType)
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry2());
    }

    protected boolean isDiscountEntitlement3(CardInfoResponseV2DTO cardInfoResponseV2DTO, OysterCardDiscountType discountType) {
        return isDiscountEntitlement(cardInfoResponseV2DTO.getDiscountEntitlement1(), discountType)
                        && isDiscountEntitlementExpired(cardInfoResponseV2DTO.getDiscountExpiry3());
    }

    protected boolean isDiscountEntitlement(String discountEntitlement, OysterCardDiscountType discountType) {
        return discountEntitlement.equalsIgnoreCase(discountType.code());
    }

    protected boolean isDiscountEntitlementExpired(String dateOfExpiry) {
        if (isNotBlank(dateOfExpiry)) {
            Date expiryDate = DateUtil.formatStringAsDate(dateOfExpiry);
            return DateUtil.isBeforeOrEqual(new Date(), expiryDate);
        }
        return Boolean.FALSE;
    }
}
