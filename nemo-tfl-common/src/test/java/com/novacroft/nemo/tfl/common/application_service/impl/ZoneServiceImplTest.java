package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd3;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd4;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getCartItemCmd5;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndDate1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardEndZone3;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardInBetweenZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardInBetweenZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSame1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartDate1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartZone1;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardStartZone2;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSurrounding;
import static com.novacroft.nemo.test_support.ZoneServiceTestUtil.getTravelcardSurrounding2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.MessageSource;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.test_support.SelectListTestUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@RunWith(Parameterized.class)
public class ZoneServiceImplTest {

    private ZoneServiceImpl service;
    private CartItemCmdImpl cartItemCmd;
    private ProductItemDTO productItemDTO;
    private boolean isZoneOverlap;
    private MessageSource mockMessageSource;
    private ProductService mockProductService;
    private SelectListService mockSelectListService;

    public static final String DAY_7 = "7Day";
    public static final String OTHER = "Unknown";
    private static final String WATFORD = "Watford";

    @Before
    public void setup() {
        service = new ZoneServiceImpl();
        mockMessageSource = mock(MessageSource.class);
        mockProductService = mock(ProductService.class);
        mockSelectListService = mock(SelectListService.class);

        service.messageSource = mockMessageSource;
        service.productService = mockProductService;
        service.selectListService = mockSelectListService;
    }

    public ZoneServiceImplTest(String testName, CartItemCmdImpl cartItemCmd, ProductItemDTO productItemDTO, boolean isZoneOverlappingWithProductItem) {
        this.cartItemCmd = cartItemCmd;
        this.productItemDTO = productItemDTO;
        this.isZoneOverlap = isZoneOverlappingWithProductItem;
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<?> parameterisedTestDate() {
        return Arrays.asList(new Object[][] { { "isSurroundingZoneOverlap1", getCartItemCmd1(), getTravelcardSurrounding(), true },
                        { "isSurroundingZoneOverlap2", getCartItemCmd2(), getTravelcardSurrounding(), true },
                        { "isSurroundingZoneOverlap3", getCartItemCmd3(), getTravelcardSurrounding(), true },
                        { "isSurroundingZoneOverlap4", getCartItemCmd5(), getTravelcardSurrounding(), true },
                        { "isNotSurroundingZoneOverlap1", getCartItemCmd4(), getTravelcardSurrounding(), false },
                        { "isNotSurroundingZoneOverlap2", getCartItemCmd2(), getTravelcardSurrounding2(), true },
                        { "isSameZoneOverlap1", getCartItemCmd1(), getTravelcardSame1(), true },
                        { "isNotSameZoneOverlap", getCartItemCmd5(), getTravelcardSame1(), true },
                        { "isStartZoneOverlap1", getCartItemCmd1(), getTravelcardStartZone1(), true },
                        { "isStartZoneOverlap2", getCartItemCmd1(), getTravelcardStartZone2(), true },
                        { "isEndZoneOverlap1", getCartItemCmd1(), getTravelcardEndZone1(), true },
                        { "isEndZoneOverlap2", getCartItemCmd1(), getTravelcardEndZone2(), true },
                        { "isNotEndZoneOverlap1", getCartItemCmd2(), getTravelcardEndZone3(), false },
                        { "isInBetweenZoneOverlap1", getCartItemCmd1(), getTravelcardInBetweenZone1(), true },
                        { "isInBetweenZoneOverlap2", getCartItemCmd2(), getTravelcardInBetweenZone2(), true },
                        { "isNotStartDateOverlap1", getCartItemCmd1(), getTravelcardStartDate1(), false },
                        { "isNotEndDateOverlap1", getCartItemCmd1(), getTravelcardEndDate1(), false } });
    }

    @Test
    public void isZonesOverlapWithTravelCardZones() {
        boolean isOverlap = service.isZonesOverlapWithTravelCardZones(this.cartItemCmd, productItemDTO.getStartDate(), productItemDTO.getEndDate(),
                        productItemDTO.getStartZone(), productItemDTO.getEndZone());

        assertEquals(this.isZoneOverlap, isOverlap);
    }

    @Test
    public void isZonesOverlapWithProductItemDTOZones() {
        boolean isOverlap = service.isZonesOverlapWithProductItemDTOZones(this.cartItemCmd.getStartZone(), this.cartItemCmd.getEndZone(),
                        productItemDTO, parse(this.cartItemCmd.getStartDate()), parse(this.cartItemCmd.getEndDate()));

        assertEquals(this.isZoneOverlap, isOverlap);
    }

    @Test
    public void testGetAvailableZonesForTravelCardTypeAndStartZone() {
        SelectListDTO dto = new SelectListDTO();
        dto.getOptions().add(SelectListTestUtil.getTestSelectListOptionDTO(1l, "5", 10));
        when(mockSelectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES)).thenReturn(dto);
        List<ProductDTO> productList = new ArrayList<>();
        productList.add(ProductTestUtil.getTestProduct2(26L, 3, 5, 2520, DAY_7));
        when(mockProductService.getProductsByLikeDurationAndStartZone(anyString(), anyInt())).thenReturn(productList);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), any(java.util.Locale.class))).thenReturn(WATFORD);
        service.getAvailableZonesForTravelCardTypeAndStartZone("test", 3, "7_DAY");
        assertEquals(WATFORD, service.getAvailableZonesForTravelCardTypeAndStartZone("test", 3, "7_DAY").getOptions().get(0).getMeaning());
    }

    @Test
    public void testGetAvailableZonesForOtherTravelCardTypeAndStartZone() {
        SelectListDTO dto = new SelectListDTO();
        dto.getOptions().add(SelectListTestUtil.getTestSelectListOptionDTO(1l, "5", 10));
        when(mockSelectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES)).thenReturn(dto);
        List<ProductDTO> productList = new ArrayList<>();
        productList.add(ProductTestUtil.getTestProduct2(26L, 3, 5, 2520, OTHER));
        when(mockProductService.getProductsByStartZoneForOddPeriod(anyInt(), anyString())).thenReturn(productList);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), any(java.util.Locale.class))).thenReturn(WATFORD);
        service.getAvailableZonesForTravelCardTypeAndStartZone("test", 3, OTHER);
        assertEquals(WATFORD, service.getAvailableZonesForTravelCardTypeAndStartZone("test", 3, OTHER).getOptions().get(0).getMeaning());
    }

    @Test
    public void testGetAvailableZonesForOtherTravelCardTypeAndStartZoneNullProducts() {
        SelectListDTO dto = new SelectListDTO();
        dto.getOptions().add(SelectListTestUtil.getTestSelectListOptionDTO(1l, "5", 10));
        when(mockSelectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES)).thenReturn(dto);
        List<ProductDTO> productList = new ArrayList<>();
        productList.add(ProductTestUtil.getTestProduct2(26L, 3, 5, 2520, OTHER));
        when(mockProductService.getProductsByStartZoneForOddPeriod(anyInt(), anyString())).thenReturn(null);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), any(java.util.Locale.class))).thenReturn(WATFORD);
        SelectListDTO options = service.getAvailableZonesForTravelCardTypeAndStartZone("test", 3, OTHER);
        assertNotNull(options);
        assertFalse(options.getOptions().size() > 0);
    }
}
