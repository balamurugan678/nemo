package com.novacroft.nemo.tfl.batch.application_service.impl.product_fare_loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.batch.constant.PassengerType;
import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;
import com.novacroft.nemo.tfl.batch.job.HibernateBatchJobUpdateStrategyServiceImplTest;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.DurationDataService;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ZoneDataService;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.PriceDTO;
import com.novacroft.nemo.tfl.common.transfer.ZoneDTO;

@RunWith(MockitoJUnitRunner.class)
public class PrePaidTicketUploadServiceImplTest {

    @Mock
    protected PrePaidTicketUploadServiceImpl mockPaidTicketUploadServiceImpl;
    @Mock
    protected ZoneDataService mockZoneDataService;

    @Mock
    protected DurationDataService mockDurationDataService;

    @Mock
    protected DiscountTypeDataService mockDiscountTypeDataService;

    @Mock
    protected PassengerTypeDataService mockPassengerTypeDataService;

    @Mock
    protected PrePaidTicketDataService mockPrePaidTicketDataService;

    protected PrePaidTicketRecordServiceImpl paidTicketRecordServiceImpl = new PrePaidTicketRecordServiceImpl();

    @Before
    public void setUp() {
        mockPaidTicketUploadServiceImpl.discountTypeDataService = mockDiscountTypeDataService;
        mockPaidTicketUploadServiceImpl.passengerTypeDataService = mockPassengerTypeDataService;
        mockPaidTicketUploadServiceImpl.durationDataService = mockDurationDataService;
        mockPaidTicketUploadServiceImpl.prePaidTicketDataService = mockPrePaidTicketDataService;
        mockPaidTicketUploadServiceImpl.zoneDataService = mockZoneDataService;
    }

    @Test
    public void updatePrePaidDataForRecordCallsCreateWhenNoRecordForAdhocCodeFound() {

        PrePaidTicketRecord prePaidTicketRecord = paidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);

        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidDataForRecord(prePaidTicketRecord);
        when(mockPaidTicketUploadServiceImpl.getPrePaidTicketByCodeAndEffectiveDate(anyString(), any(Boolean.class))).thenReturn(
                        null);
        mockPaidTicketUploadServiceImpl.updatePrePaidDataForRecord(prePaidTicketRecord);
        verify(mockPaidTicketUploadServiceImpl).createPrePaidTicket(any(PrePaidTicketRecord.class));

    }

    @Test
    public void updatePrePaidDataForRecordCallsUpdateWhenRecordForAdhocCodeFound() {

        PrePaidTicketRecord prePaidTicketRecord = paidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        prePaidTicketDTO.setStartZoneDTO(new ZoneDTO());
        prePaidTicketDTO.setEndZoneDTO(new ZoneDTO());
        prePaidTicketDTO.setFromDurationDTO(new DurationDTO());
        prePaidTicketDTO.setToDurationDTO(new DurationDTO());
        prePaidTicketDTO.setPassengerTypeDTO(new PassengerTypeDTO());
        prePaidTicketDTO.setDiscountTypeDTO(new DiscountTypeDTO());

        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidDataForRecord(prePaidTicketRecord);
        when(mockPaidTicketUploadServiceImpl.getPrePaidTicketByCodeAndEffectiveDate(anyString(), any(Boolean.class))).thenReturn(prePaidTicketDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenReturn(null);
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(any(PrePaidTicketRecord.class), any(PrePaidTicketDTO.class));
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).checkAndResetExpiryDates(any(PrePaidTicketDTO.class));
        mockPaidTicketUploadServiceImpl.updatePrePaidDataForRecord(prePaidTicketRecord);
        verify(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(any(PrePaidTicketRecord.class), any(PrePaidTicketDTO.class));
        verify(mockPaidTicketUploadServiceImpl).checkAndResetExpiryDates(any(PrePaidTicketDTO.class));

    }

    @Test
    public void addNoPriceWhenPriceInPenceIsZero() {

        PrePaidTicketRecord prePaidTicketRecord = paidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        prePaidTicketRecord.setPriceInPence(null);
        ZoneDTO zoneDTO = new ZoneDTO();
        zoneDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setStartZoneDTO(zoneDTO);
        ZoneDTO endZoneDTO = new ZoneDTO();
        endZoneDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setEndZoneDTO(endZoneDTO);
        DurationDTO durationDTO = new DurationDTO();
        durationDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setFromDurationDTO(durationDTO);
        DurationDTO toDurationDTO = new DurationDTO();
        toDurationDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setToDurationDTO(toDurationDTO);
        PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO();
        passengerTypeDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setPassengerTypeDTO(passengerTypeDTO);
        DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO();
        discountTypeDTO.setEffectiveTo(new Date());
        prePaidTicketDTO.setDiscountTypeDTO(discountTypeDTO);
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).checkAndResetExpiryDates(any(PrePaidTicketDTO.class));
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        mockPaidTicketUploadServiceImpl.updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        assertTrue(prePaidTicketDTO.getPrices().isEmpty());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void addNewPriceWhenPriceInPenceIsNonZeroAndNoPriceExists() {

        PrePaidTicketRecord prePaidTicketRecord = paidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();

        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).createPriceDTO(prePaidTicketRecord.getPriceInPence(),
                        prePaidTicketRecord.getPriceEffectiveDate());
        when(mockPaidTicketUploadServiceImpl.getCurrentPriceFromList(any(List.class))).thenReturn(null);
        mockPaidTicketUploadServiceImpl.updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        assertTrue(prePaidTicketDTO.getPrices().get(0).getpriceInPence().equals(prePaidTicketRecord.getPriceInPence()));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void addNewPriceWhenPriceInPenceIsNonZeroAndDifferrentPriceExists() {

        PrePaidTicketRecord prePaidTicketRecord = paidTicketRecordServiceImpl
                        .createPrepaidRecord(HibernateBatchJobUpdateStrategyServiceImplTest.CSV_RECORD_1);
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();

        prePaidTicketRecord.setPriceEffectiveDate(new Date());
        PriceDTO existingPrice = new PriceDTO(1l, prePaidTicketRecord.getPriceInPence() - 100, DateConstant.TEMPORAL_PAST_START_DATE,
                        DateUtil.addDaysToDate(new Date(), -3), null);

        prePaidTicketDTO.getPrices().add(existingPrice);

        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        doCallRealMethod().when(mockPaidTicketUploadServiceImpl).createPriceDTO(prePaidTicketRecord.getPriceInPence(),
                        prePaidTicketRecord.getPriceEffectiveDate());
        when(mockPaidTicketUploadServiceImpl.getCurrentPriceFromList(any(List.class))).thenReturn(existingPrice);
        mockPaidTicketUploadServiceImpl.updatePrePaidTicket(prePaidTicketRecord, prePaidTicketDTO);
        assertTrue(prePaidTicketDTO.getPrices().get(1).getpriceInPence().equals(prePaidTicketRecord.getPriceInPence()));
        assertTrue(prePaidTicketDTO.getPrices().get(1).getEffectiveFrom().equals(prePaidTicketRecord.getPriceEffectiveDate()));
        assertTrue(existingPrice.getEffectiveTo().equals(DateUtil.getDayBefore(prePaidTicketDTO.getPrices().get(1).getEffectiveFrom())));
    }

    @Test
    public void testCreateNewPrePaidTicket() {

        final String[] CSV_RECORD_1 = "102,Annual Travelcard Zones 1 to 1,12/14/2002,Child,1,Zones 1 to 1,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,annual,Y,1256.00"
                        .split(",");

        PrePaidTicketUploadServiceImpl realPrePaidTicketUploadService = new PrePaidTicketUploadServiceImpl();
        realPrePaidTicketUploadService.discountTypeDataService = mockDiscountTypeDataService;
        realPrePaidTicketUploadService.passengerTypeDataService = mockPassengerTypeDataService;
        realPrePaidTicketUploadService.durationDataService = mockDurationDataService;
        realPrePaidTicketUploadService.prePaidTicketDataService = mockPrePaidTicketDataService;
        realPrePaidTicketUploadService.zoneDataService = mockZoneDataService;
        final DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(1l, "Child", "Child", DateConstant.TEMPORAL_PAST_START_DATE,
                        DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final DurationDTO durationDTO = new DurationDTO(1l, Durations.SEVEN_DAYS.getDurationValue(), null, Durations.SEVEN_DAYS.getDurationType(),
                        Durations.SEVEN_DAYS.getDurationType(), DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO(1l, PassengerType.Child.code(), PassengerType.Child.code(),
                        DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final ZoneDTO zoneDTO = new ZoneDTO(1l, "1", "1", DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(discountTypeDTO);
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(durationDTO);

        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(zoneDTO);
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(passengerTypeDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenAnswer(new Answer<PrePaidTicketDTO>() {
            @Override
            public PrePaidTicketDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (PrePaidTicketDTO) args[0];
            }
        });

        realPrePaidTicketUploadService.createPrePaidTicket(paidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1));

        verify(mockDiscountTypeDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockZoneDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockDurationDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockPassengerTypeDataService).findByCode(anyString(), any(Date.class));

    }

    @Test
    public void testCreateNewPrePaidTicketWithDifferentEndZoneAndEndDuration() {

        final String[] CSV_RECORD_1 = "102,Annual Travelcard Zones 1 to 2,12/14/2002,Child,1,Zones 1 to 2,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,1-2 months,Y, "
                        .split(",");

        PrePaidTicketUploadServiceImpl realPrePaidTicketUploadService = new PrePaidTicketUploadServiceImpl();
        realPrePaidTicketUploadService.discountTypeDataService = mockDiscountTypeDataService;
        realPrePaidTicketUploadService.passengerTypeDataService = mockPassengerTypeDataService;
        realPrePaidTicketUploadService.durationDataService = mockDurationDataService;
        realPrePaidTicketUploadService.prePaidTicketDataService = mockPrePaidTicketDataService;
        realPrePaidTicketUploadService.zoneDataService = mockZoneDataService;
        final DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(1l, "Child", "Child", DateConstant.TEMPORAL_PAST_START_DATE,
                        DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final DurationDTO durationDTO = new DurationDTO(1l, Durations.SEVEN_DAYS.getDurationValue(), null, Durations.SEVEN_DAYS.getDurationType(),
                        Durations.SEVEN_DAYS.getDurationType(), DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO(1l, PassengerType.Child.code(), PassengerType.Child.code(),
                        DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final ZoneDTO zoneDTO = new ZoneDTO(1l, "1", "1", DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(discountTypeDTO);
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(durationDTO);

        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(zoneDTO);
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(passengerTypeDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenAnswer(new Answer<PrePaidTicketDTO>() {
            @Override
            public PrePaidTicketDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (PrePaidTicketDTO) args[0];
            }
        });

        realPrePaidTicketUploadService.createPrePaidTicket(paidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1));

        verify(mockDiscountTypeDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockZoneDataService, times(2)).findByCode(anyString(), any(Date.class));
        verify(mockDurationDataService, times(2)).findByCode(anyString(), any(Date.class));
        verify(mockPassengerTypeDataService).findByCode(anyString(), any(Date.class));

    }

    @Test
    public void testCreateNewPrePaidTicketWith7Day() {

        final String[] CSV_RECORD_1 = "102,Annual Travelcard Zones 1 to 2,12/14/2002,Child,1,Zones 1 to 2,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,7 day,Y, "
                        .split(",");

        PrePaidTicketUploadServiceImpl realPrePaidTicketUploadService = new PrePaidTicketUploadServiceImpl();
        realPrePaidTicketUploadService.discountTypeDataService = mockDiscountTypeDataService;
        realPrePaidTicketUploadService.passengerTypeDataService = mockPassengerTypeDataService;
        realPrePaidTicketUploadService.durationDataService = mockDurationDataService;
        realPrePaidTicketUploadService.prePaidTicketDataService = mockPrePaidTicketDataService;
        realPrePaidTicketUploadService.zoneDataService = mockZoneDataService;
        final DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(1l, "Child", "Child", DateConstant.TEMPORAL_PAST_START_DATE,
                        DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final DurationDTO durationDTO = new DurationDTO(1l, Durations.SEVEN_DAYS.getDurationValue(), null, Durations.SEVEN_DAYS.getDurationType(),
                        Durations.SEVEN_DAYS.getDurationType(), DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO(1l, PassengerType.Child.code(), PassengerType.Child.code(),
                        DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final ZoneDTO zoneDTO = new ZoneDTO(1l, "1", "1", DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(discountTypeDTO);
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(durationDTO);

        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(zoneDTO);
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(passengerTypeDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenAnswer(new Answer<PrePaidTicketDTO>() {
            @Override
            public PrePaidTicketDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (PrePaidTicketDTO) args[0];
            }
        });

        realPrePaidTicketUploadService.createPrePaidTicket(paidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1));

        verify(mockDiscountTypeDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockZoneDataService, times(2)).findByCode(anyString(), any(Date.class));
        verify(mockDurationDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockPassengerTypeDataService).findByCode(anyString(), any(Date.class));

    }

    @Test
    public void testCreateNewPrePaidTicketWith1Month() {

        final String[] CSV_RECORD_1 = "102,Monthly Travelcard Zones 1 to 2,12/14/2002,Child,1,Zones 1 to 2,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,1 month,Y, "
                        .split(",");

        PrePaidTicketUploadServiceImpl realPrePaidTicketUploadService = new PrePaidTicketUploadServiceImpl();
        realPrePaidTicketUploadService.discountTypeDataService = mockDiscountTypeDataService;
        realPrePaidTicketUploadService.passengerTypeDataService = mockPassengerTypeDataService;
        realPrePaidTicketUploadService.durationDataService = mockDurationDataService;
        realPrePaidTicketUploadService.prePaidTicketDataService = mockPrePaidTicketDataService;
        realPrePaidTicketUploadService.zoneDataService = mockZoneDataService;
        final DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(1l, "Child", "Child", DateConstant.TEMPORAL_PAST_START_DATE,
                        DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final DurationDTO durationDTO = new DurationDTO(1l, Durations.SEVEN_DAYS.getDurationValue(), null, Durations.SEVEN_DAYS.getDurationType(),
                        Durations.SEVEN_DAYS.getDurationType(), DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO(1l, PassengerType.Child.code(), PassengerType.Child.code(),
                        DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final ZoneDTO zoneDTO = new ZoneDTO(1l, "1", "1", DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(discountTypeDTO);
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(durationDTO);

        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(zoneDTO);
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(passengerTypeDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenAnswer(new Answer<PrePaidTicketDTO>() {
            @Override
            public PrePaidTicketDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (PrePaidTicketDTO) args[0];
            }
        });

        realPrePaidTicketUploadService.createPrePaidTicket(paidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1));

        verify(mockDiscountTypeDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockZoneDataService, times(2)).findByCode(anyString(), any(Date.class));
        verify(mockDurationDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockPassengerTypeDataService).findByCode(anyString(), any(Date.class));

    }

    @Test
    public void testCreateNewPrePaidTicketWith3Months() {

        final String[] CSV_RECORD_1 = "102,3 Months Travelcard Zones 1 to 2,12/14/2002,Child,1,Zones 1 to 2,Y,Y,N,N,N,N,N,N,N,N,N,N,N,N,N,N,3 months,Y, "
                        .split(",");

        PrePaidTicketUploadServiceImpl realPrePaidTicketUploadService = new PrePaidTicketUploadServiceImpl();
        realPrePaidTicketUploadService.discountTypeDataService = mockDiscountTypeDataService;
        realPrePaidTicketUploadService.passengerTypeDataService = mockPassengerTypeDataService;
        realPrePaidTicketUploadService.durationDataService = mockDurationDataService;
        realPrePaidTicketUploadService.prePaidTicketDataService = mockPrePaidTicketDataService;
        realPrePaidTicketUploadService.zoneDataService = mockZoneDataService;
        final DiscountTypeDTO discountTypeDTO = new DiscountTypeDTO(1l, "Child", "Child", DateConstant.TEMPORAL_PAST_START_DATE,
                        DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final DurationDTO durationDTO = new DurationDTO(1l, Durations.SEVEN_DAYS.getDurationValue(), null, Durations.SEVEN_DAYS.getDurationType(),
                        Durations.SEVEN_DAYS.getDurationType(), DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final PassengerTypeDTO passengerTypeDTO = new PassengerTypeDTO(1l, PassengerType.Child.code(), PassengerType.Child.code(),
                        DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        final ZoneDTO zoneDTO = new ZoneDTO(1l, "1", "1", DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);

        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(discountTypeDTO);
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(durationDTO);

        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(zoneDTO);
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(passengerTypeDTO);
        when(mockPrePaidTicketDataService.createOrUpdate(any(PrePaidTicketDTO.class))).thenAnswer(new Answer<PrePaidTicketDTO>() {
            @Override
            public PrePaidTicketDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (PrePaidTicketDTO) args[0];
            }
        });

        realPrePaidTicketUploadService.createPrePaidTicket(paidTicketRecordServiceImpl.createPrepaidRecord(CSV_RECORD_1));
        verify(mockDiscountTypeDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockZoneDataService, times(2)).findByCode(anyString(), any(Date.class));
        verify(mockDurationDataService, times(1)).findByCode(anyString(), any(Date.class));
        verify(mockPassengerTypeDataService).findByCode(anyString(), any(Date.class));

    }

    @Test
    public void testGetPriceEffectiveOnDateReturnsAvailablePrice() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        final PriceDTO existingPriceDTO = new PriceDTO(1l, 1000, DateConstant.TEMPORAL_PAST_START_DATE, null, null);
        prePaidTicketDTO.getPrices().add(existingPriceDTO);
        assertTrue(existingPriceDTO == paidTicketUploadServiceImpl.getCurrentPriceFromList(prePaidTicketDTO.getPrices()));

    }

    @Test
    public void testGetPriceEffectiveOnDateReturnsNullWhenNoPriceAvailable() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        final PriceDTO existingPriceDTO = new PriceDTO(1l, 1000, DateConstant.TEMPORAL_PAST_START_DATE, DateConstant.TEMPORAL_FURURE_END_DATE, null);
        prePaidTicketDTO.getPrices().add(existingPriceDTO);
        assertTrue(null == paidTicketUploadServiceImpl.getCurrentPriceFromList(prePaidTicketDTO.getPrices()));

    }

    @Test
    public void testCreateRetrieveZoneByCodeCreatesAndReturnNewZone() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.zoneDataService = mockZoneDataService;
        when(mockZoneDataService.findByCode(anyString(), any(Date.class))).thenReturn(null);
        assertTrue(null != paidTicketUploadServiceImpl.getOrCreateZoneByCode("1", new Date()));

    }

    @Test
    public void testCreateRetrieveDurationByCodeCreatesAndReturnNewDuration() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.durationDataService = mockDurationDataService;
        when(mockDurationDataService.findByCode(anyString(), any(Date.class))).thenReturn(null);
        assertTrue(null != paidTicketUploadServiceImpl.getOrCreateDurationByCode("7Month", new Date()));

    }

    @Test
    public void testCreateRetrievePassengerTypeByCodeCreatesAndReturnNewPassengerType() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.passengerTypeDataService = mockPassengerTypeDataService;
        when(mockPassengerTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(null);
        assertTrue(null != paidTicketUploadServiceImpl.getOrCreatePassengerTypeByCode("Adult", new Date()));

    }

    @Test
    public void testCreateRetrievePassengerTypeByCodeCreatesAndReturnNewDiscountType() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.discountTypeDataService = mockDiscountTypeDataService;
        when(mockDiscountTypeDataService.findByCode(anyString(), any(Date.class))).thenReturn(null);
        assertTrue(null != paidTicketUploadServiceImpl.getOrCreateDiscountTypeByCode("18+", new Date()));

    }

    @Test
    public void testRetrievePrePaidTicketByCodeAndEffectiveDateReturnNullIfDateExpired() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.prePaidTicketDataService = mockPrePaidTicketDataService;
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        prePaidTicketDTO.setEffectiveTo(DateUtil.addDaysToDate(new Date(), -3));
        when(mockPrePaidTicketDataService.findByProductCode(anyString())).thenReturn(prePaidTicketDTO);
        PrePaidTicketDTO resultDTO = paidTicketUploadServiceImpl.getPrePaidTicketByCodeAndEffectiveDate("", true);
        assertNotNull(resultDTO);
        assertEquals(null, resultDTO.getEffectiveTo());
    }

    @Test
    public void testRetrievePrePaidTicketByCodeAndEffectiveDateReturnNull() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.prePaidTicketDataService = mockPrePaidTicketDataService;
        when(mockPrePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(null);
        assertNull(paidTicketUploadServiceImpl.getPrePaidTicketByCodeAndEffectiveDate("", true));
    }

    @Test
    public void testGetPrePaidTicketIfTickIsNotActive() {
        PrePaidTicketUploadServiceImpl paidTicketUploadServiceImpl = new PrePaidTicketUploadServiceImpl();
        paidTicketUploadServiceImpl.prePaidTicketDataService = mockPrePaidTicketDataService;
        PrePaidTicketDTO prePaidTicketDTO = new PrePaidTicketDTO();
        when(mockPrePaidTicketDataService.findByProductCode(anyString())).thenReturn(prePaidTicketDTO);
        PrePaidTicketDTO resultDTO = paidTicketUploadServiceImpl.getPrePaidTicketByCodeAndEffectiveDate("", false);
        assertNotNull(resultDTO);
        assertNotNull(resultDTO.getEffectiveTo());
    }

}
