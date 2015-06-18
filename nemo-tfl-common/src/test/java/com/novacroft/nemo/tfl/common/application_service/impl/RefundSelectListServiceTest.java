package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.tfl.common.constant.GoodwillReasonType.OYSTER;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.tfl.common.application_service.BackdatedRefundReasonService;
import com.novacroft.nemo.tfl.common.application_service.GoodwillService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;

/**
 * Unit tests for RefundSelectListService
 */
public class RefundSelectListServiceTest {
	private RefundSelectListServiceImpl service;
	private SelectListService mockSelectListService;
	private ProductDataService mockProductDataService;
	private LocationSelectListService mockLocationSelectListService;
	private GoodwillService mockGoodwillService;
	private CountrySelectListService mockCountrySelectListService;
	private BackdatedRefundReasonService mockBackdatedRefundreasonService;
	private ProductService mockProductService;
	private final Model model = new ExtendedModelMap();
	
	@Before
	public void setUp() throws Exception {
		service = new RefundSelectListServiceImpl();
		mockSelectListService = mock(SelectListService.class);
		mockProductDataService = mock(ProductDataService.class);
		mockLocationSelectListService = mock(LocationSelectListService.class);
		mockGoodwillService = mock(GoodwillService.class);
		mockBackdatedRefundreasonService = mock(BackdatedRefundReasonService.class);
		mockCountrySelectListService = mock(CountrySelectListService.class);
		mockProductService = mock(ProductServiceImpl.class);
		
		ReflectionTestUtils.setField(service, "selectListService", mockSelectListService); 
		ReflectionTestUtils.setField(service, "productDataService", mockProductDataService);
		ReflectionTestUtils.setField(service, "locationSelectListService", mockLocationSelectListService);
		ReflectionTestUtils.setField(service, "goodwillService", mockGoodwillService);
		ReflectionTestUtils.setField(service, "backdatedRefundreasonService", mockBackdatedRefundreasonService);
		service.countrySelectListService = mockCountrySelectListService;
		service.productService = mockProductService;
		
		when(mockSelectListService.getSelectList(anyString())).thenReturn(SelectListTestUtil.getTestSelectListDTO());
		when(mockLocationSelectListService.getLocationSelectList()).thenReturn(SelectListTestUtil.getTestSelectListDTO());
		when(mockGoodwillService.getGoodwillRefundTypes(OYSTER.code())).thenReturn(SelectListTestUtil.getTestSelectListDTO());
		when(mockGoodwillService.getGoodwillRefundExtraValidationMessages(OYSTER.code())).thenReturn("");
		when(mockBackdatedRefundreasonService.getBackdatedRefundTypes()).thenReturn(SelectListTestUtil.getTestSelectListDTO());
		when(mockProductService.getPassengerTypeList()).thenReturn(SelectListTestUtil.getTestSelectListDTO());
	}

	@Test
	public void getSelectListModelShouldContainModelAttributes() {
		service.getSelectListModel(model);
		
		model.containsAttribute(PageAttribute.REFUND_CALCULATION_BASIS);
		model.containsAttribute(PageAttribute.TRAVEL_CARD_TYPES);
		model.containsAttribute(PageAttribute.TRAVEL_CARD_ZONES);
		model.containsAttribute(PageAttribute.TRAVEL_CARD_RATES);
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES);
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES);
		model.containsAttribute(PageAttribute.BACKDATED_REFUND_TYPES);
		model.containsAttribute(PageAttribute.PAYMENT_TYPE);
		model.containsAttribute(PageAttribute.FIELD_SELECT_PICKUP_STATION);
	}
	
	@Test
	public void getStandaloneGoodwillSelectListModelShouldContainModelAttributes() {
		service.getStandaloneGoodwillSelectListModel(model);
		
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES);
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES);
		model.containsAttribute(PageAttribute.PAYMENT_TYPE);
		model.containsAttribute(PageAttribute.FIELD_SELECT_PICKUP_STATION);
	}
	
	@Test
	public void getAnonymousGoodwillSelectListModelShouldContainModelAttributes() {
		service.getAnonymousGoodwillSelectListModel(model);
		
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES);
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES);
		model.containsAttribute(PageAttribute.PAYMENT_TYPE);
		model.containsAttribute(PageAttribute.FIELD_SELECT_PICKUP_STATION);
	}
	
	@Test
	public void populateRefundCalculationBasisSelectListShouldContainRefundCalculationBasis() {		
		service.populateRefundCalculationBasisSelectList(model);
		
		model.containsAttribute(PageAttribute.REFUND_CALCULATION_BASIS);
	}
	
	@Test
	public void populateStationSelectListShouldContainFieldSelectPickupStation() {
		service.populateStationSelectList(model);
		
		model.containsAttribute(PageAttribute.FIELD_SELECT_PICKUP_STATION);
	}

	
	@Test
	public void populateTravelCardTypesSelectListShouldContainTravelCardTypes() {
		service.populateTravelCardTypesSelectList(model);
		
		model.containsAttribute(PageAttribute.TRAVEL_CARD_TYPES);
	}
	
	@Test
	public void populateTravelCardZonesSelectListShouldContainTravelCardZones() {
		service.populateTravelCardZonesSelectList(model);
		
		model.containsAttribute(PageAttribute.TRAVEL_CARD_ZONES);
	}
	
	@Test
	public void populateTravelCardRatesSelectListShouldContainTravelCardRates() {
		service.populateTravelCardRatesSelectList(model);
		
		model.containsAttribute(PageAttribute.TRAVEL_CARD_RATES);
	}
	
	@Test
	public void populateGoodwillRefundTypesShouldContainGoodwillRefundTypesAndGoodwillRefundExtraValidationMessages() {
		service.populateGoodwillRefundTypes(model, OYSTER.code());
		
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_TYPES);
		model.containsAttribute(PageAttribute.GOODWILL_REFUND_EXTRA_VALIDATION_MESSAGES);
	}
	
	@Test
	public void populateBackdatedRefundTypesShouldContainBackdatedRefundTypes() {
		service.populateBackdatedRefundTypes(model);
		
		model.containsAttribute(PageAttribute.BACKDATED_REFUND_TYPES);
	}
	
	@Test
	public void populatePaymentTypesShouldContainPaymentType() {
		service.populatePaymentTypes(model);
		
		model.containsAttribute(PageAttribute.PAYMENT_TYPE);
	}
}
