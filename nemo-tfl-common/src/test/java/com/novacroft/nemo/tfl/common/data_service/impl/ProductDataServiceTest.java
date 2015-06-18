package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.DISCOUNT_TYPE;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.ID;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.ONE;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.ONE_MONTH;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.PASSENGER_TYPE;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.PRODUCT_CODE;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.TWO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.TWO_MONTH;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.TYPE;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getMatchingTestProductDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicket;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicketDTO;
import static com.novacroft.nemo.test_support.ProductTestUtil.PRODUCT_CODE_1;
import static com.novacroft.nemo.test_support.ProductTestUtil.PRODUCT_NAME_1;
import static com.novacroft.nemo.test_support.ProductTestUtil.getTestProduct1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.tfl.common.converter.PrePaidTicketConverter;
import com.novacroft.nemo.tfl.common.converter.impl.ProductConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ProductDAO;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

public class ProductDataServiceTest {
    static final Logger logger = LoggerFactory.getLogger(ProductDataServiceTest.class);

    private ProductDataServiceImpl dataService;
    private ProductDAO mockDao;
    private PrePaidTicketDataService mockPrePaidTicketDataService;
    private PrePaidTicketConverter mockPrePaidTicketConverter;
    private List<PrePaidTicketDTO> mockPrePaidTicketList;

    @Before
    public void setUp() {
        dataService = new ProductDataServiceImpl();
        mockDao = mock(ProductDAO.class);
        dataService.setConverter(new ProductConverterImpl());
        dataService.setDao(mockDao);
        mockPrePaidTicketDataService = mock(PrePaidTicketDataService.class);
        mockPrePaidTicketConverter = mock(PrePaidTicketConverter.class);
        mockPrePaidTicketList = new ArrayList<PrePaidTicketDTO>();

        dataService.prePaidTicketDataService = mockPrePaidTicketDataService;
        dataService.prePaidTicketConverter = mockPrePaidTicketConverter;

        mockPrePaidTicketList.add(getTestPrePaidTicketDTO());
    }

    @Test
    public void findByProductNameShouldFindProduct() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getTestProduct1());

        ProductDTO resultsDTO = dataService.findByProductName(PRODUCT_NAME_1, true);
        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertEquals(PRODUCT_CODE_1, resultsDTO.getProductCode());

        ProductDTO run2 = dataService.findByProductName(PRODUCT_NAME_1, false);
        assertEquals(PRODUCT_CODE_1, run2.getProductCode());
    }

    @Test
    public void findByProductNameShouldNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);

        ProductDTO resultsDTO = dataService.findByProductName(PRODUCT_NAME_1, false);
        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertNull(resultsDTO);
    }

    @Test
    public void shouldFindProductByCode() {
        when(mockPrePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), dataService.findByProductCode(PRODUCT_CODE, new Date()).getProductCode());
        verify(mockPrePaidTicketDataService).findByProductCode(anyString(), any(Date.class));
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void shouldFindAllByExample() {
        when(mockPrePaidTicketDataService.findAllByExample(any(PrePaidTicket.class))).thenReturn(mockPrePaidTicketList);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        List<ProductDTO> resultList = dataService.findAllByExample(getTestPrePaidTicket());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), resultList.get(0).getProductCode());
        verify(mockPrePaidTicketDataService).findAllByExample(any(PrePaidTicket.class));
        verify(mockPrePaidTicketConverter, atLeastOnce()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }
    
    @Test
    public void FindAllByExampleShouldNotCallConverterWithNoResults() {
        when(mockPrePaidTicketDataService.findAllByExample(any(PrePaidTicket.class))).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        dataService.findAllByExample(getTestPrePaidTicket());
        verify(mockPrePaidTicketDataService).findAllByExample(any(PrePaidTicket.class));
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void shouldFindProductsByLikeDurationAndStartZone() {
        when(mockPrePaidTicketDataService.findProductsByDurationAndStartZone(anyString(), anyInt(), any(Date.class))).thenReturn(
                        mockPrePaidTicketList);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        List<ProductDTO> resultList = dataService.findProductsByLikeDurationAndStartZone(ONE_MONTH, ONE, new Date());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), resultList.get(0).getProductCode());
        verify(mockPrePaidTicketDataService).findProductsByDurationAndStartZone(anyString(), anyInt(), any(Date.class));
        verify(mockPrePaidTicketConverter, atLeastOnce()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void findProductsByLikeDurationAndStartZoneShouldNotCallConverterWithNoResults() {
        when(mockPrePaidTicketDataService.findProductsByDurationAndStartZone(anyString(), anyInt(), any(Date.class))).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        dataService.findProductsByLikeDurationAndStartZone(ONE_MONTH, ONE, new Date());
        verify(mockPrePaidTicketDataService).findProductsByDurationAndStartZone(anyString(), anyInt(), any(Date.class));
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    } 

    @Test
    public void shouldFindByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockPrePaidTicketDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), dataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ONE_MONTH, TWO_MONTH, ONE, TWO, new Date(), PASSENGER_TYPE, DISCOUNT_TYPE, TYPE).getProductCode());
        verify(mockPrePaidTicketDataService).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeShouldReturnNullIfNoResultFound() {
        when(mockPrePaidTicketDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(null, dataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ONE_MONTH, TWO_MONTH, ONE, TWO, new Date(), PASSENGER_TYPE, DISCOUNT_TYPE, TYPE));
        verify(mockPrePaidTicketDataService).findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }
    
    @Test
    public void shouldFindByFromDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockPrePaidTicketDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), dataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ONE_MONTH, ONE, TWO, new Date(), PASSENGER_TYPE, DISCOUNT_TYPE, TYPE).getProductCode());
        verify(mockPrePaidTicketDataService).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeShouldReturnNullIfNoResultFound() {
        when(mockPrePaidTicketDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString())).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(null, dataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ONE_MONTH, ONE, TWO, new Date(), PASSENGER_TYPE, DISCOUNT_TYPE, TYPE));
        verify(mockPrePaidTicketDataService).findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(anyString(), anyInt(), anyInt(), any(Date.class), anyString(), anyString(), anyString());
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }
    
    @Test
    public void shouldFindProductsByStartZoneForOddPeriod() {
        when(mockPrePaidTicketDataService.findProductsByStartZoneAndTypeForOddPeriod(anyInt(), any(Date.class), anyString())).thenReturn(
                        mockPrePaidTicketList);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        List<ProductDTO> resultList = dataService.findProductsByStartZoneAndTypeForOddPeriod(ONE, new Date(), TYPE);
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), resultList.get(0).getProductCode());
        verify(mockPrePaidTicketDataService).findProductsByStartZoneAndTypeForOddPeriod(anyInt(), any(Date.class), anyString());
        verify(mockPrePaidTicketConverter, atLeastOnce()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void findProductsByStartZoneForOddPeriodShouldNotCallConverterWithNoResults() {
        when(mockPrePaidTicketDataService.findProductsByStartZoneAndTypeForOddPeriod(anyInt(), any(Date.class), anyString())).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        dataService.findProductsByStartZoneAndTypeForOddPeriod(ONE, new Date(), TYPE);
        verify(mockPrePaidTicketDataService).findProductsByStartZoneAndTypeForOddPeriod(anyInt(), any(Date.class), anyString());
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void shouldFindProductByPrePaidTicketId() {
        when(mockPrePaidTicketDataService.findById(anyLong())).thenReturn(getTestPrePaidTicketDTO());
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(getTestPrePaidTicketDTO().getAdHocPrePaidTicketCode(), dataService.findById(ID, new Date()).getProductCode());
        verify(mockPrePaidTicketDataService).findById(anyLong());
        verify(mockPrePaidTicketConverter).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }

    @Test
    public void findProductByPrePaidTicketIdShouldReturnNullIfNoResultFound() {
        when(mockPrePaidTicketDataService.findById(anyLong())).thenReturn(null);
        when(mockPrePaidTicketConverter.convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class))).thenReturn(getMatchingTestProductDTO());
        assertEquals(null, dataService.findById(ID, new Date()));
        verify(mockPrePaidTicketDataService).findById(anyLong());
        verify(mockPrePaidTicketConverter, never()).convertToProductDto(any(PrePaidTicketDTO.class), any(Date.class));
    }
}
