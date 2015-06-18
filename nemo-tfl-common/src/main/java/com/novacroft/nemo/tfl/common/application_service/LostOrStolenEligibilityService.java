package com.novacroft.nemo.tfl.common.application_service;

public interface LostOrStolenEligibilityService {
    Boolean isCardEligibleToBeReportedLostOrStolen(Long cardId);
}
