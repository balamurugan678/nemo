package com.novacroft.nemo.tfl.services.application_service.impl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.test_support.PayAsYouGoTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ItemType;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.form_validator.CommonTravelCardValidator;
import com.novacroft.nemo.tfl.common.form_validator.PayAsYouGoValidator;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.constant.CommandAttribute;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.PayAsYouGoConverter;
import com.novacroft.nemo.tfl.services.converter.ProductDataItemConverter;
import com.novacroft.nemo.tfl.services.converter.impl.ItemConverterImpl;
import com.novacroft.nemo.tfl.services.converter.impl.PayAsYouGoConverterImpl;
import com.novacroft.nemo.tfl.services.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.services.test_support.ProductDataTestUtil;
import com.novacroft.nemo.tfl.services.test_support.TestSupportUtilities;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListContainer;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public class ProductServiceImplTest {

    private static final String PASSENGER_TYPE = "Adult";
	private static final String DISCOUNT_TYPE = "No Discount";
	private static final String TYPE = "Travelcard";
	private ProductServiceImpl service;
    private ProductDataService mockProductDataService;
    private ProductDataItemConverter mockProductDataConverter;
    private CommonTravelCardValidator commonTravelCardValidator;
    private CartAdministrationService mockCartAdminService;
    private SelectListService mockSelectListService;
    private PayAsYouGoValidator mockPayAsYouGoValidator;
    private PayAsYouGoConverter payAsYouGoWSConverter;
    private ApplicationContext mockApplicationContext;
    private PayAsYouGoDataService mockPayAsYouGoDataService;
    private ItemConverterImpl mockItemConverter;
    private GetCardService mockGetCardService;
    private com.novacroft.nemo.tfl.common.application_service.impl.ProductServiceImpl mockProductService;

    @Before
    public void setUp() throws Exception {
        service = new ProductServiceImpl();
        mockProductDataService = mock(ProductDataService.class);
        commonTravelCardValidator = new CommonTravelCardValidator();
        mockProductDataConverter = mock(ProductDataItemConverter.class);
        mockCartAdminService = mock(CartAdministrationService.class);
        mockSelectListService = mock(SelectListService.class);
        mockPayAsYouGoValidator = mock(PayAsYouGoValidator.class);
        payAsYouGoWSConverter = new PayAsYouGoConverterImpl();
        mockApplicationContext = mock(ApplicationContext.class);
        mockPayAsYouGoDataService = mock(PayAsYouGoDataService.class);
        mockItemConverter = mock(ItemConverterImpl.class);
        mockGetCardService = mock(GetCardService.class);
        mockProductService = mock(com.novacroft.nemo.tfl.common.application_service.impl.ProductServiceImpl.class);
        assignMockedServicesToServiceUnderTest();
        setUpStaticContent();
    }
    
    private void assignMockedServicesToServiceUnderTest(){
        service.productDataConverter = mockProductDataConverter;
        service.productDataService = mockProductDataService;
        service.commonTravelCardValidator = commonTravelCardValidator;
        service.cartAdminService = mockCartAdminService;
        service.selectListService = mockSelectListService;
        service.payAsYouGoValidator = mockPayAsYouGoValidator;
        service.payAsYouGoWSConverter = payAsYouGoWSConverter;
        service.itemConverter = mockItemConverter;
        ReflectionTestUtils.setField(service, "applicationContext", mockApplicationContext);
        service.payAsYouGoDataService = mockPayAsYouGoDataService;
        service.getCardService = mockGetCardService;
        service.productService = mockProductService;
    }

    private void setUpStaticContent(){
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.contentCode());
        when(mockApplicationContext.getMessage(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode(), null, null)).thenReturn(WebServiceResultAttribute.RECORD_NOT_FOUND.contentCode());
    }

    @Test
    public void getTravelCardNoErrors() {
        when(mockProductDataConverter.convertToItem((ProductDTO) any(), (ProductItemDTO) any())).thenReturn(ItemTestUtil.getTestTravelCardItem1());
        when(mockProductDataConverter.convertToProductItemDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductItemDTO());
        when(mockProductDataConverter.convertToProductDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductDTO());
        Item item = service.getTravelCard(getPrePaidTicketWithPassengerAndDiscountType());
        assertNotNull(item);
        assertNull(item.getErrors());
    }
    
    @Test
    public void getTravelCardShouldConvertErrors() {
        when(mockProductDataConverter.convertToItem((ProductDTO) any(), (ProductItemDTO) any())).thenReturn(ItemTestUtil.getTestTravelCardItem1());
        when(mockProductDataConverter.convertToProductItemDTO((PrePaidTicket) any())).thenReturn(new ProductItemDTO());
        when(mockProductDataConverter.convertToProductDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductDTO());
        when(mockProductDataConverter.convertToErrorResult(any(Errors.class))).thenReturn(ProductDataTestUtil.getItemErrorResult());
        Item item = service.getTravelCard(getPrePaidTicketWithPassengerAndDiscountType());
        assertNotNull(item);
        assertNotNull(item.getErrors());
        verify(mockProductDataConverter).convertToErrorResult(any(Errors.class));
    }

    @Test
    public void getOddPeriodTravelCardNoErrors() {
    	when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductDataTestUtil.getTestOddPeriodProductDTO());
        when(mockProductDataConverter.convertToItem((ProductDTO) any(), (ProductItemDTO) any())).thenReturn(ItemTestUtil.getTestTravelCardItem1());
        when(mockProductDataConverter.convertToProductItemDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestOddPeriodProductItemDTO());
        when(mockProductDataConverter.convertToProductDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestOddPeriodProductDTO());
        Item item = service.getTravelCard(getPrePaidTicketWithPassengerAndDiscountType());
        assertNotNull(item);
        assertNull(item.getErrors());
    }

	private PrePaidTicket getPrePaidTicketWithPassengerAndDiscountType() {
		PrePaidTicket travelcard = ProductDataTestUtil.getTestOddPeriodTravelCard();
        travelcard.setPassengerType("PTYPE");
        travelcard.setDiscountType("DTYPE");
		return travelcard;
	}

    
    @Test
    public void getTravelCardHasErrors() {
        when(mockProductDataConverter.convertToProductItemDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductItemDTO());
        when(mockProductDataConverter.convertToProductDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductDTO());
        when(mockProductDataConverter.convertToErrorResult((Errors) any())).thenReturn(ProductDataTestUtil.getItemErrorResult());
        Item item = service.getTravelCard(ProductDataTestUtil.getTestTravelCard());
        assertNotNull(item);
        assertNotNull(item.getErrors());
    }
    
    @Test 
    public void getTravelcardNullStartAndEndZoneAndPassengerAndDiscountType(){
        service = mock(ProductServiceImpl.class);
        assignMockedServicesToServiceUnderTest();
        
        when(service.getContent(anyString())).thenReturn(ProductDataTestUtil.GENERIC_ERROR);
        
        when(service.getTravelCard((PrePaidTicket)any())).thenCallRealMethod();
        when(service.getPrePaidTicket(any(PrePaidTicket.class), any(ProductItemType.class))).thenReturn(ItemTestUtil.getTestTravelCardItemWithErrors());
        when(service.validateZonesPassengerAndDiscountType(anyString(), anyString(), anyString(), anyString())).thenCallRealMethod();
        PrePaidTicket travelcard = new PrePaidTicket();
        travelcard.setStartZone(null);
        Item item = service.getTravelCard(travelcard);
        assertNotNull(item);
        assertNotNull(item.getErrors());
        assertTrue(item.getErrors().getErrors().size()>0);
    }
    
    @Test
    public void getTravelCardWithIntegerParseError(){
        service = mock(ProductServiceImpl.class);
        assignMockedServicesToServiceUnderTest();
        
        when(service.getContent(anyString())).thenReturn(ProductDataTestUtil.GENERIC_ERROR);
        when(service.validateZonesPassengerAndDiscountType(anyString(), anyString(), anyString(), anyString())).thenCallRealMethod();
        when(service.getPrePaidTicket(any(PrePaidTicket.class), any(ProductItemType.class))).thenReturn(ItemTestUtil.getTestTravelCardItemWithErrors());
        when(service.getTravelCard((PrePaidTicket)any())).thenCallRealMethod();
        PrePaidTicket travelcard = getPrePaidTicketWithPassengerAndDiscountType();
        travelcard.setStartZone(ItemTestUtil.INVALID_ZONE);
        Item item = service.getTravelCard(travelcard);
        assertNotNull(item);
        assertNotNull(item.getErrors());
        assertTrue(item.getErrors().getErrors().size()>0);
    }

    @Test
    public void testGetReferenceData() {
        service = mock(ProductServiceImpl.class);
        assignMockedServicesToServiceUnderTest();
        when(mockSelectListService.getSelectList(any(String.class))).thenReturn(TestSupportUtilities.getTestSelectListDTO1());
        when(mockCartAdminService.getUserProductStartDateList()).thenReturn(TestSupportUtilities.getTestSelectListDTO1());
        when(mockProductService.getDiscountTypeList()).thenReturn(TestSupportUtilities.getTestSelectListDTO1());
        when(mockProductService.getPassengerTypeList()).thenReturn(TestSupportUtilities.getTestSelectListDTO1());
        when(service.getContent(anyString(), anyString())).thenReturn("Test");
        when(service.getReferenceData()).thenCallRealMethod().thenReturn(new ArrayList<ListContainer>());
        List<ListContainer> containerList = service.getReferenceData();
        assertNotNull(containerList);
    }

    @Test
    public void getPayAsYouGoShouldFailValidationWithNullInput(){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.rejectValue(PageCommandAttribute.FIELD_CREDIT_BALANCE, "Mock Invalid Amount Error");
                return null;
            }
        }).when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        
        Item item = service.getPayAsYouGo(null);
        assertEquals(item.getErrors().getErrors().size(), 1);
        com.novacroft.nemo.tfl.services.transfer.Error error = item.getErrors().getErrors().get(0);
        assertEquals(error.getField(), CommandAttribute.FIELD_PAY_AS_YOU_GO_AMOUNT);
        
    }

    @Test
    public void getPayAsYouGoShouldFailValidation(){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Errors errors = (Errors) args[1];
                errors.rejectValue(PageCommandAttribute.FIELD_CREDIT_BALANCE, "Mock Invalid Amount Error");
                return null;
            }
        }).when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        
        Item item = service.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith12Amount());
        assertEquals(item.getErrors().getErrors().size(), 1);
        com.novacroft.nemo.tfl.services.transfer.Error error = item.getErrors().getErrors().get(0);
        assertEquals(error.getField(), CommandAttribute.FIELD_PAY_AS_YOU_GO_AMOUNT);
        
    }

    @Test
    public void getPayAsYouGoShouldFailRecordNotFound(){
        doNothing().when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockPayAsYouGoDataService.findByTicketPrice(any(Integer.class))).thenReturn(PayAsYouGoTestUtil.getTestPayAsYouGoDTO1());
        Item item = service.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith12Amount());
        assertEquals(item.getErrors().getErrors().size(), 1);
        com.novacroft.nemo.tfl.services.transfer.Error error = item.getErrors().getErrors().get(0);
        assertEquals(error.getDescription(), WebServiceResultAttribute.RECORD_NOT_FOUND.name());
    }

    @Test
    public void getPayAsYouGoShouldFailWithLookupException(){
        doNothing().when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        when(mockPayAsYouGoDataService.findByTicketPrice(any(Integer.class))).thenThrow(new RuntimeException("Mock exception") );
        Item item = service.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith12Amount());
        assertEquals(item.getErrors().getErrors().size(), 1);
        com.novacroft.nemo.tfl.services.transfer.Error error = item.getErrors().getErrors().get(0);
        assertEquals(error.getDescription(), WebServiceResultAttribute.RECORD_LOOKUP_FAILED_DUE_TO_EXCEPTION.name());        
    }

    @Test
    public void getPayAsYouGoShouldCompleteSuccessfully(){
        doNothing().when(mockPayAsYouGoValidator).validate(any(CartItemCmdImpl.class), any(Errors.class));
        PayAsYouGoDTO mockPayAsYouGoDTO = PayAsYouGoTestUtil.getTestPayAsYouGoDTO1();
        when(mockPayAsYouGoDataService.findByTicketPrice(any(Integer.class))).thenReturn(mockPayAsYouGoDTO);
        Item item = service.getPayAsYouGo(ProductDataTestUtil.getPayAsYouGoInputWith10Amount());
        assertNull(item.getErrors());
        assertEquals(item.getName(), mockPayAsYouGoDTO.getPayAsYouGoName());
        assertEquals(item.getPrice(), mockPayAsYouGoDTO.getTicketPrice());
        assertEquals(item.getProductType(), ItemType.PAY_AS_YOU_GO.code());
    }
    
    @Test
    public void getOddPeriodProductShouldCompleteSuccessfully(){
    	when(mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(ProductDataTestUtil.getTestOddPeriodProductDTO());
    	PrePaidTicket travelCard = ProductDataTestUtil.getTestOddPeriodTravelCard();
		ProductDTO productDTO = service.getOddPeriodProduct(travelCard, PASSENGER_TYPE, DISCOUNT_TYPE, TYPE);
		assertEquals(ItemTestUtil.DURATION_OTHER, productDTO.getDuration());
		assertEquals(ItemTestUtil.START_ZONE_1, productDTO.getStartZone());
		assertEquals(ItemTestUtil.PRICE_1, productDTO.getTicketPrice());
    }
    
    @Test 
    public void shoudNotValidateZonesPassengerAndDiscountType(){
        service = mock(ProductServiceImpl.class);
        assignMockedServicesToServiceUnderTest();
        
        when(service.validateZonesPassengerAndDiscountType(anyString(), anyString(), anyString(), anyString())).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(ProductDataTestUtil.GENERIC_ERROR);
        ErrorResult errorResult = service.validateZonesPassengerAndDiscountType(null, null, null, null);
        assertNotNull(errorResult);
    }
    
    @Test
    public void shouldGetBusPass() {
        when(mockProductDataConverter.convertToItem((ProductDTO) any(), (ProductItemDTO) any())).thenReturn(ItemTestUtil.getTestBusPassItem());
        when(mockProductDataConverter.convertToProductItemDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductItemDTO());
        when(mockProductDataConverter.convertToProductDTO((PrePaidTicket) any())).thenReturn(ProductDataTestUtil.getTestProductDTO());
        Item item = service.getBusPass(getPrePaidTicketWithPassengerAndDiscountType());
        assertNotNull(item);
        assertNull(item.getErrors());
    }

    
}
