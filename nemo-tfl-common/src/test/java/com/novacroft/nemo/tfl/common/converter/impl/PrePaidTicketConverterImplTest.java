package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestDiscountTypeDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestDurationDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPassengerTypeDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicket;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicketDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrePaidTicketDTO4;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPrice;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPriceDTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPriceDTO2;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestPriceDTOWithoutId;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestZone1DTO;
import static com.novacroft.nemo.test_support.PrePaidTicketTestUtil.getTestZone2DTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.tfl.common.data_access.DiscountTypeDAO;
import com.novacroft.nemo.tfl.common.data_access.DurationDAO;
import com.novacroft.nemo.tfl.common.data_access.PassengerTypeDAO;
import com.novacroft.nemo.tfl.common.data_access.PriceDAO;
import com.novacroft.nemo.tfl.common.data_access.ZoneDAO;
import com.novacroft.nemo.tfl.common.domain.DiscountType;
import com.novacroft.nemo.tfl.common.domain.Duration;
import com.novacroft.nemo.tfl.common.domain.PassengerType;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.domain.Price;
import com.novacroft.nemo.tfl.common.domain.Zone;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

public class PrePaidTicketConverterImplTest {
    private PrePaidTicketConverterImpl converter;
    private ZoneConverterImpl mockZoneConverter;
    private PassengerTypeConverterImpl mockPassengerTypeConverter;
    private DurationConverterImpl mockDurationConverter;
    private DiscountTypeConverterImpl mockDiscountTypeConverter;
    private PriceConverterImpl mockPriceConverter;
    private DiscountTypeDAO mockDiscountTypeDAO;
    private PassengerTypeDAO mockPassengerTypeDAO;
    private DurationDAO mockDurationDAO;
    private ZoneDAO mockZoneDAO;
    private PriceDAO mockPriceDAO;

    @Before
    public void setUp() {
        converter = new PrePaidTicketConverterImpl();
        mockZoneConverter = mock(ZoneConverterImpl.class);
        mockPassengerTypeConverter = mock(PassengerTypeConverterImpl.class);
        mockDurationConverter = mock(DurationConverterImpl.class);
        mockDiscountTypeConverter = mock(DiscountTypeConverterImpl.class);
        mockPriceConverter = mock(PriceConverterImpl.class);
        mockDiscountTypeDAO = mock(DiscountTypeDAO.class);
        mockPassengerTypeDAO = mock(PassengerTypeDAO.class);
        mockDurationDAO = mock(DurationDAO.class);
        mockZoneDAO = mock(ZoneDAO.class);
        mockPriceDAO = mock(PriceDAO.class);

        converter.zoneConverter = mockZoneConverter;
        converter.passengerTypeConverter = mockPassengerTypeConverter;
        converter.durationConverter = mockDurationConverter;
        converter.discountTypeConverter = mockDiscountTypeConverter;
        converter.priceConverter = mockPriceConverter;
        converter.discountTypeDAO = mockDiscountTypeDAO;
        converter.passengerTypeDAO = mockPassengerTypeDAO;
        converter.durationDAO = mockDurationDAO;
        converter.zoneDAO = mockZoneDAO;
        converter.priceDAO = mockPriceDAO;
    }

    @Test
    public void getNewDtoNotNull() {
        assertNotNull(converter.getNewDto());
    }

    @Test
    public void shouldConvertEntityToDto() {
        when(mockZoneConverter.convertEntityToDto(any(Zone.class))).thenReturn(getTestZone1DTO(), getTestZone2DTO());
        when(mockPassengerTypeConverter.convertEntityToDto(any(PassengerType.class))).thenReturn(getTestPassengerTypeDTO());
        when(mockDurationConverter.convertEntityToDto(any(Duration.class))).thenReturn(getTestDurationDTO());
        when(mockDiscountTypeConverter.convertEntityToDto(any(DiscountType.class))).thenReturn(getTestDiscountTypeDTO());
        when(mockPriceConverter.convertEntityToDto(any(Price.class))).thenReturn(getTestPriceDTO());

        PrePaidTicket input = getTestPrePaidTicket();
        PrePaidTicketDTO actualResult = converter.convertEntityToDto(input);

        assertNotNull(actualResult);
        assertEquals(input.getAdHocPrePaidTicketCode(), actualResult.getAdHocPrePaidTicketCode());
        assertEquals(input.getCubicReference(), actualResult.getCubicReference());
        assertEquals(input.getEffectiveFrom(), actualResult.getEffectiveFrom());
        assertEquals(input.getEffectiveTo(), actualResult.getEffectiveTo());
        assertEquals(input.getStartZone().getCode(), actualResult.getStartZoneDTO().getCode());
        assertEquals(input.getEndZone().getCode(), actualResult.getEndZoneDTO().getCode());
        assertEquals(input.getPassengerType().getCode(), actualResult.getPassengerTypeDTO().getCode());
        assertEquals(input.getFromDuration().getCode(), actualResult.getFromDurationDTO().getCode());
        assertEquals(input.getDiscountType().getCode(), actualResult.getDiscountTypeDTO().getCode());
    }

    @Test
    public void shouldConvertToProductDtoWithNullPrice() {
        PrePaidTicketDTO input = getTestPrePaidTicketDTO();
        ProductDTO actualResult = converter.convertToProductDto(input, new Date());

        assertNotNull(actualResult);
        assertEquals(input.getAdHocPrePaidTicketCode(), actualResult.getProductCode());
        assertEquals(input.getId(), actualResult.getId());
        assertEquals(input.getDescription(), actualResult.getProductName());
        assertEquals(input.getStartZoneDTO().getCode(), actualResult.getStartZone().toString());
        assertEquals(input.getEndZoneDTO().getCode(), actualResult.getEndZone().toString());
        assertEquals(input.getPassengerTypeDTO().getCode(), actualResult.getRate());
        assertEquals(input.getFromDurationDTO().getCode(), actualResult.getDuration());
    }

    @Test
    public void shouldConvertToProductDtoWithCurrentPrice() {
        PrePaidTicketDTO input = getTestPrePaidTicketDTO4();
        ProductDTO actualResult = converter.convertToProductDto(input, new Date());

        assertNotNull(actualResult);
        assertEquals(input.getAdHocPrePaidTicketCode(), actualResult.getProductCode());
        assertEquals(input.getId(), actualResult.getId());
        assertEquals(input.getDescription(), actualResult.getProductName());
        assertEquals(input.getStartZoneDTO().getCode(), actualResult.getStartZone().toString());
        assertEquals(input.getEndZoneDTO().getCode(), actualResult.getEndZone().toString());
        assertEquals(input.getPassengerTypeDTO().getCode(), actualResult.getRate());
        assertEquals(input.getFromDurationDTO().getCode(), actualResult.getDuration());
        assertEquals(getTestPriceDTO2().getpriceInPence(), actualResult.getTicketPrice());
    }

    @Test
    public void isNotCurrentPriceIfEffectDateIsBeforePriceEffectiveFromDate() {
        PriceDTO priceDTO = getTestPriceDTO2();

        assertFalse(converter.isCurrentPrice(DateUtil.parse("01/01/2011"), priceDTO));
    }

    @Test
    public void isNotCurrentPriceIfEffectiveDateIsAfterPriceEffectiveToDate() {
        PriceDTO priceDTO = getTestPriceDTO();

        assertFalse(converter.isCurrentPrice(DateUtil.parse("01/01/2016"), priceDTO));
    }

    @Test
    public void iscurrentPrice() {
        assertTrue(converter.isCurrentPrice(DateTestUtil.get1Jan(), getTestPriceDTO()));
    }

    @Test
    public void testConvertDTOToEntity() {
        PrePaidTicketDTO input = getTestPrePaidTicketDTO4();
        mockDAOAndConverterCalls();
        PrePaidTicket actualResult = converter.convertDtoToEntity(input, new PrePaidTicket());

        assertNotNull(actualResult);
        verify(mockDurationDAO, atLeastOnce()).findById(anyLong());
        verify(mockDiscountTypeDAO).findById(anyLong());
        verify(mockPassengerTypeDAO).findById(anyLong());
        verify(mockZoneDAO, atLeastOnce()).findById(anyLong());
        verify(mockPriceDAO, atLeastOnce()).findById(anyLong());
    }

    private void mockDAOAndConverterCalls() {
        when(mockDiscountTypeDAO.findById(anyLong())).thenReturn(null);
        when(mockDiscountTypeConverter.convertEntityToDto(any(DiscountType.class))).thenReturn(getTestDiscountTypeDTO());

        when(mockPassengerTypeDAO.findById(anyLong())).thenReturn(null);
        when(mockPassengerTypeConverter.convertEntityToDto(any(PassengerType.class))).thenReturn(getTestPassengerTypeDTO());

        when(mockDurationDAO.findById(anyLong())).thenReturn(null);
        when(mockDurationConverter.convertEntityToDto(any(Duration.class))).thenReturn(getTestDurationDTO());

        when(mockZoneDAO.findById(anyLong())).thenReturn(null);
        when(mockZoneConverter.convertEntityToDto(any(Zone.class))).thenReturn(PrePaidTicketTestUtil.getTestZone1DTO());

        when(mockPriceDAO.findById(anyLong())).thenReturn(getTestPrice());
        when(mockPriceConverter.convertDtoToEntity(any(PriceDTO.class), any(Price.class))).thenReturn(getTestPrice());
    }

    @Test
    public void testSetUpNewPrice() {
        ArrayList<PriceDTO> prices = new ArrayList<PriceDTO>();
        prices.add(getTestPriceDTOWithoutId());
        when(mockPriceConverter.convertDtoToEntity(any(PriceDTO.class), any(Price.class))).thenReturn(getTestPrice());

        converter.setUpPrices(getTestPrePaidTicket(), prices);
        verify(mockPriceDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewDiscountType() {
        DiscountTypeDTO discountTypeDTO = getTestDiscountTypeDTO();
        discountTypeDTO.setId(null);
        converter.setDiscountType(getTestPrePaidTicket(), discountTypeDTO);
        verify(mockDiscountTypeDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewPassengerType() {
        PassengerTypeDTO passengerTypeDTO = getTestPassengerTypeDTO();
        passengerTypeDTO.setId(null);
        converter.setPassengerType(getTestPrePaidTicket(), passengerTypeDTO);
        verify(mockDiscountTypeDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewToDuration() {
        DurationDTO toDurationDTO = getTestDurationDTO();
        toDurationDTO.setId(null);
        DurationDTO fromDurationDTO = getTestDurationDTO();
        fromDurationDTO.setCode("2Month");
        converter.setToDuration(getTestPrePaidTicket(), fromDurationDTO, toDurationDTO);
        verify(mockDurationDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewFromDuration() {
        DurationDTO durationDTO = getTestDurationDTO();
        durationDTO.setId(null);
        converter.setFromDuration(getTestPrePaidTicket(), durationDTO);
        verify(mockDurationDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewEndZone() {
        ZoneDTO toZoneDTO = getTestZone1DTO();
        toZoneDTO.setId(null);
        ZoneDTO fromZoneDTO = getTestZone1DTO();
        fromZoneDTO.setCode("3");
        converter.setEndZone(getTestPrePaidTicket(), fromZoneDTO, toZoneDTO);
        verify(mockZoneDAO, never()).findById(anyLong());
    }

    @Test
    public void testSetNewStartZone() {
        ZoneDTO zoneDTO = getTestZone1DTO();
        zoneDTO.setId(null);
        converter.setStartZone(getTestPrePaidTicket(), zoneDTO);
        verify(mockZoneDAO, never()).findById(anyLong());
    }
    
    @Test
    public void testSetEndZoneIfTheSameAsStartZone(){
        ZoneDTO zoneDTO = getTestZone1DTO();
        converter.setEndZone(getTestPrePaidTicket(), zoneDTO, zoneDTO);
        verify(mockZoneDAO, never()).findById(anyLong());
    }
    
    @Test
    public void testSetToDurationIfDruationAlreadyExists() {
        DurationDTO toDurationDTO = getTestDurationDTO();
        DurationDTO fromDurationDTO = getTestDurationDTO();
        fromDurationDTO.setCode("2Month");
        when(mockDurationDAO.findById(anyLong())).thenReturn(null);
        when(mockDurationConverter.convertEntityToDto(any(Duration.class))).thenReturn(getTestDurationDTO());
        converter.setToDuration(getTestPrePaidTicket(), fromDurationDTO, toDurationDTO);
        verify(mockDurationDAO).findById(anyLong());
    }
}
