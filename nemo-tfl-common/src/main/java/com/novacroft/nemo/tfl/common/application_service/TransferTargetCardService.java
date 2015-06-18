package com.novacroft.nemo.tfl.common.application_service;

import org.springframework.ui.Model;

public interface TransferTargetCardService {
	Boolean isEligibleAsTargetCard(String sourceCardNumber, String targetCardNumber);
	void populateCardsSelectList(String sourceCardNumber, Model model);
}
