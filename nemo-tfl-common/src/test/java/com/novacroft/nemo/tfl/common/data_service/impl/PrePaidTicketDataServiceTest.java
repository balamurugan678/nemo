package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.PrePaidTicketConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PrePaidTicketDAO;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

public class PrePaidTicketDataServiceTest {

    private static final String EXPECTED_RESULT =
            " AND p.effectiveFrom <= CURRENT_DATE() and (p.effectiveTo is null or p.effectiveTo >= CURRENT_DATE())  AND p" +
                    ".startZone.effectiveFrom <= CURRENT_DATE() and (p.startZone.effectiveTo is null or p.startZone" +
                    ".effectiveTo >= CURRENT_DATE()) ";
    private PrePaidTicketDataServiceImpl dataService;
    private PrePaidTicketDAO mockDao;
    private PrePaidTicketConverterImpl mockConverter;

    @Before
    public void setUp() {
        dataService = new PrePaidTicketDataServiceImpl();
        mockDao = mock(PrePaidTicketDAO.class);
        mockConverter = mock(PrePaidTicketConverterImpl.class);
        dataService.setConverter(mockConverter);
        dataService.setDao(mockDao);
    }

    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(getTestPrePaidTicket());
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        PrePaidTicketDTO resultDTO = dataService
                .findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(CODE_1, CODE_1, ONE, TWO, null,
                        PASSENGER_TYPE, DISCOUNT_TYPE, TYPE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTO.getAdHocPrePaidTicketCode());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotFindByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(null);

        PrePaidTicketDTO resultDTO = dataService
                .findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(CODE_1, CODE_1, ONE, TWO, new Date(),
                        PASSENGER_TYPE, DISCOUNT_TYPE, TYPE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        assertNull(resultDTO);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindByFromDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(getTestPrePaidTicket());
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        PrePaidTicketDTO resultDTO = dataService
                .findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(CODE_1, ONE, TWO, new Date(), PASSENGER_TYPE,
                        DISCOUNT_TYPE, TYPE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTO.getAdHocPrePaidTicketCode());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotFindByFromDurationAndZonesAndPassengerTypeAndDiscountType() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(null);

        PrePaidTicketDTO resultDTO = dataService
                .findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(CODE_1, ONE, TWO, new Date(), PASSENGER_TYPE,
                        DISCOUNT_TYPE, TYPE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        assertNull(resultDTO);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindByProductCodeWithEffectiveDate() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(getTestPrePaidTicket());
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        PrePaidTicketDTO resultDTO = dataService.findByProductCode(PRODUCT_CODE, new Date());
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTO.getAdHocPrePaidTicketCode());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotFindByProductCodeWithEffectiveDate() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(null);

        PrePaidTicketDTO resultDTO = dataService.findByProductCode(PRODUCT_CODE, new Date());
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        assertNull(resultDTO);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindByProductCode() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(getTestPrePaidTicket());
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        PrePaidTicketDTO resultDTO = dataService.findByProductCode(PRODUCT_CODE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTO.getAdHocPrePaidTicketCode());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotFindByProductCode() {
        when(mockDao.findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap())).thenReturn(null);

        PrePaidTicketDTO resultDTO = dataService.findByProductCode(PRODUCT_CODE);
        verify(mockDao).findByQueryUniqueResultUsingNamedParameters(anyString(), anyMap());
        assertNull(resultDTO);
    }

    @Test
    public void shouldFindAllByExample() {
        List<PrePaidTicket> mockList = new ArrayList<PrePaidTicket>();
        mockList.add(getTestPrePaidTicket());
        when(mockDao.findByExample(any(PrePaidTicket.class))).thenReturn(mockList);
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        List<PrePaidTicketDTO> resultDTOs = dataService.findAllByExample(getTestPrePaidTicket());
        verify(mockDao).findByExample(any(PrePaidTicket.class));
        verify(mockConverter, atLeastOnce()).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTOs.get(0).getAdHocPrePaidTicketCode());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindProductsByLikeNameAndStartZone() {
        List<PrePaidTicket> mockList = new ArrayList<PrePaidTicket>();
        mockList.add(getTestPrePaidTicket());
        when(mockDao.findByQueryUsingNamedParameters(anyString(), anyMap())).thenReturn(mockList);
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        List<PrePaidTicketDTO> resultDTOs =
                dataService.findProductsByDurationAndStartZone(TICKET_DESCRIPTION, ONE, new Date());
        verify(mockDao).findByQueryUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter, atLeastOnce()).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTOs.get(0).getAdHocPrePaidTicketCode());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindProductsByStartZoneForOddPeriod() {
        List<PrePaidTicket> mockList = new ArrayList<PrePaidTicket>();
        mockList.add(getTestPrePaidTicket());
        when(mockDao.findByQueryUsingNamedParameters(anyString(), anyMap())).thenReturn(mockList);
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());

        List<PrePaidTicketDTO> resultDTOs = dataService.findProductsByStartZoneAndTypeForOddPeriod(ONE, new Date(), TYPE);
        verify(mockDao).findByQueryUsingNamedParameters(anyString(), anyMap());
        verify(mockConverter, atLeastOnce()).convertEntityToDto(any(PrePaidTicket.class));
        assertEquals(PRODUCT_CODE, resultDTOs.get(0).getAdHocPrePaidTicketCode());
    }

    @Test
    public void getPeriodClauseShouldAddCurrentDateIfEffectiveDateIsNull() {
        String result = dataService.getPeriodClause(null, "p", "p.startZone");
        assertEquals(EXPECTED_RESULT, result);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(){
        List<PrePaidTicket> mockList = new ArrayList<PrePaidTicket>();
        mockList.add(getTestPrePaidTicket());
        when(mockDao.findByQueryUsingNamedParameters(anyString(), anyMap())).thenReturn(mockList);
        when(mockConverter.convertEntityToDto(any(PrePaidTicket.class))).thenReturn(getTestPrePaidTicketDTO());
        List<PrePaidTicketDTO> prePaidTicketDTOs = dataService.findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ONE_MONTH, ONE_MONTH, ONE, TWO, PASSENGER_TYPE, DISCOUNT_TYPE, TYPE);
        assertNotNull(prePaidTicketDTOs);
        
    }
}
