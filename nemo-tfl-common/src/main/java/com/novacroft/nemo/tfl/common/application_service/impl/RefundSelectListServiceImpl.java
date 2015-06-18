package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.GoodwillReasonType.CONTACTLESS_PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.GoodwillReasonType.OYSTER;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.BACKDATED_REFUND_TYPES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.COUNTRIES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.FIELD_SELECT_PICKUP_STATION;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.GOODWILL_REFUND_TYPES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.PAYMENT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.REFUND_CALCULATION_BASIS;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.TRAVEL_CARD_RATES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.TRAVEL_CARD_TYPES;
import static com.novacroft.nemo.tfl.common.constant.PageAttribute.*;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.BackdatedRefundReasonService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundSelectListService;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;

@Service("refundSelectListService")
public class RefundSelectListServiceImpl implements RefundSelectListService {
    @Autowired
    protected SelectListService selectListService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected GoodwillService goodwillService;
    
    @Autowired
    protected BackdatedRefundReasonService backdatedRefundreasonService;
    
    @Autowired
    protected CountrySelectListService countrySelectListService;
 
    @Autowired
    protected LocationSelectListService locationSelectListService;
    
    @Autowired
    protected ProductService productService;

    @Override
    public Model getSelectListModel(Model model) {
        populateRefundCalculationBasisSelectList(model);
        populateTravelCardTypesSelectList(model);
        populateTravelCardZonesSelectList(model);
        populateTravelCardRatesSelectList(model);
        populateGoodwillRefundTypes(model, OYSTER.code());
        populateBackdatedRefundTypes(model);
        populatePaymentTypes(model);
        populateStationSelectList(model);
        populateTravelCardDiscountTypeSelectList(model);
        return model;
    }

    @Override
    public Model getStandaloneGoodwillSelectListModel(Model model) {
        populateGoodwillRefundTypes(model, CONTACTLESS_PAYMENT_CARD.code());
        populatePaymentTypes(model);
        populateStationSelectList(model);
        populateCountrySelectList(model);
        return model;
    }
        
    @Override
    public void populateCountrySelectList(Model model) {
    	model.addAttribute(COUNTRIES, countrySelectListService.getSelectList());
		 
		
	}

	@Override
    public Model getAnonymousGoodwillSelectListModel(Model model) {
        populateAnonymousGoodwillRefundTypes(model, OYSTER.code());
        populateAnonymousGoodWillPaymentTypes(model);
        populateStationSelectList(model);
        return model;
    }

    @Override
    public void populateRefundCalculationBasisSelectList(Model model) {
        model.addAttribute(REFUND_CALCULATION_BASIS, selectListService.getSelectList(PageSelectList.REFUND_CALCULATION_BASIS));
    }
    
    @Override
    public void populateStationSelectList(Model model) {
        model.addAttribute(FIELD_SELECT_PICKUP_STATION, locationSelectListService.getLocationSelectList());
    }

    @Override
    public void populateTravelCardTypesSelectList(Model model) {
        model.addAttribute(TRAVEL_CARD_TYPES, selectListService.getSelectList(PageSelectList.REFUND_TRAVEL_CARD_TYPES));
    }

    @Override
    public void populateTravelCardZonesSelectList(Model model) {
        model.addAttribute(TRAVEL_CARD_ZONES, selectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES));
    }

    @Override
    public void populateTravelCardRatesSelectList(Model model) {
        model.addAttribute(TRAVEL_CARD_RATES, productService.getPassengerTypeList());
    }
    
    @Override
    public void populateGoodwillRefundTypes(Model model, String goodwillReasonType) {
        model.addAttribute(GOODWILL_REFUND_TYPES, goodwillService.getGoodwillRefundTypes(goodwillReasonType));
        model.addAttribute(GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES, goodwillService.getGoodwillRefundExtraValidationMessages(
                goodwillReasonType));
    }
    
    public void populateAnonymousGoodwillRefundTypes(Model model, String goodwillReasonType) {
        model.addAttribute(GOODWILL_REFUND_TYPES, goodwillService.getAnonymousGoodwillRefundTypes(goodwillReasonType));
        model.addAttribute(GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES, goodwillService.getGoodwillRefundExtraValidationMessages(
                goodwillReasonType));
    }
    
    @Override
    public void populateBackdatedRefundTypes(Model model) {
        model.addAttribute(BACKDATED_REFUND_TYPES, backdatedRefundreasonService.getBackdatedRefundTypes());
    }
    
    @Override
    public void populatePaymentTypes(Model model) {
        model.addAttribute(PAYMENT_TYPE, selectListService.getSelectList(PageSelectList.PAYMENT_TYPE));
    }
    
    protected void populateAnonymousGoodWillPaymentTypes(Model model){
        SelectListDTO selectListDTO = selectListService.getSelectList(PageSelectList.PAYMENT_TYPE);
        List<SelectListOptionDTO> selectListOptionDTOList = selectListDTO.getOptions();
        Iterator<SelectListOptionDTO> iterator = selectListOptionDTOList.iterator();
        while (iterator.hasNext()) {
            SelectListOptionDTO selectListOptionDTO = iterator.next();
            if (!(PaymentType.AD_HOC_LOAD.code()).equals(selectListOptionDTO.getValue())) {
                iterator.remove();
            }
        }
        model.addAttribute(PAYMENT_TYPE, selectListDTO);
    }

    @Override
    public void populateTravelCardDiscountTypeSelectList(Model model) {
        model.addAttribute(TRAVEL_CARD_DISCOUNT_TYPES, productService.getDiscountTypeList());
        
    }

}
