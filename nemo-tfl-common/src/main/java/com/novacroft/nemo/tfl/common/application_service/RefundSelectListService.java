package com.novacroft.nemo.tfl.common.application_service;

import org.springframework.ui.Model;

public interface RefundSelectListService {
    Model getSelectListModel(Model model);
    Model getStandaloneGoodwillSelectListModel(Model model);
	Model getAnonymousGoodwillSelectListModel(Model model);
	void populateRefundCalculationBasisSelectList(Model model);
	void populateTravelCardTypesSelectList(Model model);
	void populateTravelCardZonesSelectList(Model model);
	void populateTravelCardRatesSelectList(Model model);
	void populateStationSelectList(Model model);
	void populateGoodwillRefundTypes(Model model, String code);
	void populateBackdatedRefundTypes(Model model);
	void populatePaymentTypes(Model model);
	void populateCountrySelectList(Model model);
	void populateTravelCardDiscountTypeSelectList(Model model);
}
