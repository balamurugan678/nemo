package com.novacroft.nemo.tfl.common.application_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CartItemTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.test_support.ProductItemTestUtil;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.converter.impl.PrePaidTicketConverterImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.DiscountTypeDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.PassengerTypeDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.PrePaidTicketDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

public class ProductServiceImplTest {

    public static final String TRAVEL_START_DATE_1 = "11/11/2013 12:00:00";
    public static final String TRAVEL_END_DATE_1 = "17/12/2013 10:00:00";
    public static final Integer START_ZONE_1 = 1;

    private ProductServiceImpl productService;
    private PrePaidTicketDataServiceImpl mockPrePaidTicketDataServiceImpl;

    private ProductDataService mockProductDataService;
    private SystemParameterService mockSystemParamsService;
    private TravelCardServiceImpl mockTravelCardService;
    private GetCardService mockGetCardService;
    private CardDataService mockCardDataService;
    private PrePaidTicketConverterImpl mockPrePaidTicketConverter;

    private ProductItemDTO mockOtherTravelProductItemDTO;

    private final String mockSystemSubstitutionTravelCardTypeValue = "Substitution Card";
    private final String mockSystemAnnualTravelCardValue = "Annual Pass Card";
    private PassengerTypeDataServiceImpl mockPassengerTypeDataService;
    private DiscountTypeDataServiceImpl mockDiscountTypeDataService;

    @Before
    public void setUp() {
        mockOtherTravelProductItemDTO = ProductItemTestUtil.getTestOtherTravelCardProductDTO1();
        mockPrePaidTicketDataServiceImpl = mock(PrePaidTicketDataServiceImpl.class);
        mockProductDataService = mock(ProductDataService.class);
        mockTravelCardService = mock(TravelCardServiceImpl.class);
        mockSystemParamsService = mock(SystemParameterService.class);
        when(mockSystemParamsService.getParameterValue(CartAttribute.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE)).thenReturn(
                        mockSystemSubstitutionTravelCardTypeValue);
        when(mockSystemParamsService.getParameterValue(CartAttribute.OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD)).thenReturn(
                        mockSystemAnnualTravelCardValue);
        mockGetCardService = mock(GetCardService.class);
        mockCardDataService = mock(CardDataService.class);
        mockPrePaidTicketConverter = mock(PrePaidTicketConverterImpl.class);
        mockPassengerTypeDataService = mock(PassengerTypeDataServiceImpl.class);
        mockDiscountTypeDataService = mock(DiscountTypeDataServiceImpl.class);

        productService = new ProductServiceImpl();
        productService.productDataService = mockProductDataService;
        productService.systemParameterService = mockSystemParamsService;
        productService.travelCardService = mockTravelCardService;
        productService.prePaidTicketDataService = mockPrePaidTicketDataServiceImpl;
        productService.getCardService = mockGetCardService;
        productService.cardDataService = mockCardDataService;
        productService.prePaidTicketConverter = mockPrePaidTicketConverter;
        productService.passengerTypeDataService = mockPassengerTypeDataService;
        productService.discountTypeDataService = mockDiscountTypeDataService;
    }

    @Test
    public void shouldGetProductNameForNonOtherTravelCard() {
        when(mockPrePaidTicketDataServiceImpl.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO1());
        assertEquals(ProductTestUtil.PRODUCT_NAME_1, productService.getProductName(new ProductItemDTO()));
    }

    @Test
    public void shouldGetProductNameForOtherTravelCardIfNullSubstitutionCard() {
        when(mockPrePaidTicketDataServiceImpl.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO3());
        assertNull(productService.getProductName(mockOtherTravelProductItemDTO));
    }

    @Test
    public void shouldGetProductNameForOtherTravelCardIfLongerThanMaxDay() {
        when(mockPrePaidTicketDataServiceImpl.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO2());
        when(mockTravelCardService.isTicketLongerThanTenMonthsTwelveDays(anyInt(), anyInt(), anyLong())).thenReturn(true);
        assertEquals(mockSystemAnnualTravelCardValue, productService.getProductName(mockOtherTravelProductItemDTO));
    }

    @Test
    public void shouldCallDataServiceForGetProductByFromDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(
                        mockProductDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                                        any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        PrePaidTicketTestUtil.getMatchingTestProductDTO());
        CartItemCmdImpl cmd = CartItemTestUtil.getTestTravelCard1();
        cmd.setTicketType("");
        productService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(cmd);
        verify(mockProductDataService).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(),
                        any(Date.class), anyString(), anyString(), anyString());
    }

    @Test
    public void shouldCallFindByIdWhenPrePaidTicketIdIsPresentForGetProductByFromDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockPrePaidTicketDataServiceImpl.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(
                        PrePaidTicketTestUtil.getMatchingTestProductDTO());
        productService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(CartItemTestUtil.getTravelcardWithPrePaidTicketId());
        verify(mockPrePaidTicketDataServiceImpl).findById(anyLong());
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }
    
    @Test
    public void shouldSetZonesIntoBusPass() {
    	CartItemCmdImpl cartItem = mock(CartItemCmdImpl.class);
    	productService.setZonesIntoBusPass(cartItem);
    	verify(cartItem).setStartZone(any(Integer.class));
    	verify(cartItem).setEndZone(any(Integer.class));
    }

    @Test
    public void shouldCallFindByIdWhenPrePaidTicketIdIsPresentForGetProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockPrePaidTicketDataServiceImpl.findById(anyLong())).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(
                        PrePaidTicketTestUtil.getMatchingTestProductDTO());
        productService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemTestUtil.getTravelcardWithPrePaidTicketId());
        verify(mockPrePaidTicketDataServiceImpl).findById(anyLong());
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void shouldCallDataServiceForGetProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(
                        mockProductDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(),
                                        anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(
                        PrePaidTicketTestUtil.getMatchingTestProductDTO());
        CartItemCmdImpl cartItem = CartItemTestUtil.getTestTravelCard1();
        cartItem.setStartDate(TRAVEL_START_DATE_1);
        cartItem.setEndDate(TRAVEL_END_DATE_1);
        productService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(cartItem);
        verify(mockProductDataService).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(),
                        anyInt(), any(Date.class), anyString(), anyString(), anyString());
    }

    @Test
    public void shouldGetOtherDurationFromPrePaidTicketDTO() {
        assertEquals(Durations.OTHER.getDurationType(),
                        productService.getDurationFromPrePaidTicketDTO(PrePaidTicketTestUtil.getTestPrePaidTicketDTOOther()));
    }

    @Test
    public void shouldGetDurationFromPrePaidTicketDTO() {
        assertEquals(Durations.MONTH.getDurationType(),
                        productService.getDurationFromPrePaidTicketDTO(PrePaidTicketTestUtil.getTestPrePaidTicketDTO()));
    }

    @Test
    public void shouldGetProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        productService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemTestUtil.getTravelcardWithPrePaidTicketId(),
                        Durations.OTHER.getDurationType());
        verify(mockProductDataService).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(),
                        anyInt(), any(Date.class), anyString(), anyString(), anyString());
    }

    @Test
    public void shouldGetPassengerTypes() {
        when(mockPassengerTypeDataService.findAll()).thenReturn(PrePaidTicketTestUtil.getTestPassengerTypeDTOList());
        SelectListDTO passengerTypeSelectList = productService.getPassengerTypeList();
        assertNotNull(passengerTypeSelectList);
        assertNotEquals(passengerTypeSelectList.getOptions().size(), 0);
    }

    @Test
    public void shouldGetDiscountTypes() {
        when(mockDiscountTypeDataService.findAll()).thenReturn(PrePaidTicketTestUtil.getTestDiscountTypeDTOList());
        SelectListDTO discountTypeSelectList = productService.getDiscountTypeList();
        assertNotNull(discountTypeSelectList);
        assertNotEquals(discountTypeSelectList.getOptions().size(), 0);
    }

    @Test
    public void shouldGetProductsByLikeDurationAndStartZone() {
        productService.getProductsByLikeDurationAndStartZone(Durations.SEVEN_DAYS.getDurationType(), START_ZONE_1);
        verify(mockProductDataService).findProductsByLikeDurationAndStartZone(anyString(), anyInt(), (Date) any());
    }

    @Test
    public void shouldGetProductsByStartZoneForOddPeriod() {
        productService.getProductsByStartZoneForOddPeriod(START_ZONE_1, ProductItemType.TRAVEL_CARD.databaseCode());
        verify(mockProductDataService).findProductsByStartZoneAndTypeForOddPeriod(anyInt(), (Date) any(), anyString());
    }
}
