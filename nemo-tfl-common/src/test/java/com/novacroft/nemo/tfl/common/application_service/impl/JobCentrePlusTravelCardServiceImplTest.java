package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CardRefundableDepositItemTestUtil.CARD_ID_2;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.EXPIRY_DATE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.START_DATE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.STATUS_MESSAGE;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestJobCentrePlusTravelCardCmd1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestPayAsYouGo1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.getTestTravelCard1;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.getTestJobCentrePlusDiscountDTO1;
import static com.novacroft.nemo.test_support.ProductTestUtil.TICKET_PRICE_1;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.ZoneIdDescriptionDataService;

/**
 *  JobCentrePlusTravelCardServiceImpl unit tests
 */
public class JobCentrePlusTravelCardServiceImplTest {
    
    private JobCentrePlusTravelCardServiceImpl service;
    private JobCentrePlusTravelCardServiceImpl mockService;
    private CardDataService mockCardDataService;
    private SystemParameterService mockSystemParameterService;
    private ProductDataService mockProductDataService;
    private RefundCalculationBasisService mockRefundCalculationBasisService;
    private ZoneIdDescriptionDataService mockZoneIdDescriptionDataService;
    private GetCardService mockGetCardService;
    private JobCentrePlusInvestigationDataService mockJobCentrePlusInvestigationDataService;
    private CartItemCmdImpl mockCartItemCmdImpl;
    private JobCentrePlusDiscountDTO mockJobCentrePlusDiscountDTO;
    
    @Before
    public void setup() {
        service = new JobCentrePlusTravelCardServiceImpl();
        mockCardDataService = mock(CardDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockProductDataService = mock(ProductDataService.class);
        mockRefundCalculationBasisService = mock(RefundCalculationBasisService.class);
        mockZoneIdDescriptionDataService = mock(ZoneIdDescriptionDataService.class);
        mockGetCardService = mock(GetCardService.class);
        mockJobCentrePlusInvestigationDataService = mock(JobCentrePlusInvestigationDataService.class);
        service.cardDataService = mockCardDataService;
        service.systemParameterService = mockSystemParameterService;
        service.productDataService = mockProductDataService;
        service.refundCalculationBasisService = mockRefundCalculationBasisService;
        service.zoneIdDescriptionDataService = mockZoneIdDescriptionDataService;
        service.getCardService = mockGetCardService;
        service.jobCentrePlusInvestigationDataService = mockJobCentrePlusInvestigationDataService;
        mockService = mock(JobCentrePlusTravelCardServiceImpl.class);
        mockService.getCardService = mockGetCardService;
        mockService.systemParameterService = mockSystemParameterService;
        mockCartItemCmdImpl = mock(CartItemCmdImpl.class);
        mockJobCentrePlusDiscountDTO = mock(JobCentrePlusDiscountDTO.class);
    }

    @Test
    public void shouldCheckJobCentrePlusDiscountAndUpdateTicketPrice() {
        doCallRealMethod().when(mockService).checkJobCentrePlusDiscountAndUpdateTicketPrice(mockCartItemCmdImpl, TICKET_PRICE_1);
        double testPrice = TICKET_PRICE_1;
        doNothing().when(mockCartItemCmdImpl).setStatusMessage(anyString());
        doNothing().when(mockService).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));
        when(mockService.getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble())).thenReturn(testPrice);
        mockService.checkJobCentrePlusDiscountAndUpdateTicketPrice(mockCartItemCmdImpl, TICKET_PRICE_1);        
        verify(mockCartItemCmdImpl, atLeastOnce()).setStatusMessage(anyString());        
        verify(mockService, atLeastOnce()).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));        
        verify(mockService, atLeastOnce()).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());        
    }
    
    @Test
    public void shouldNotCheckJobCentrePlusDiscountAndUpdateTicketPriceIfTicketPriceIsNull() {
        service.checkJobCentrePlusDiscountAndUpdateTicketPrice(getTestJobCentrePlusTravelCardCmd1(), null);        
        verify(mockCartItemCmdImpl, never()).setStatusMessage(anyString());        
        verify(mockService, never()).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));        
        verify(mockService, never()).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());        
    }
    
    @Test
    public void shouldNotCheckJobCentrePlusDiscountAndUpdateTicketPriceIfCardItemIsNull() {
        service.checkJobCentrePlusDiscountAndUpdateTicketPrice(null, TICKET_PRICE_1);        
        verify(mockCartItemCmdImpl, never()).setStatusMessage(anyString());        
        verify(mockService, never()).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));        
        verify(mockService, never()).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());        
    }
    
    @Test
    public void shouldNotCheckJobCentrePlusDiscountAndUpdateTicketPriceIfCardIdIsNull() {
        service.checkJobCentrePlusDiscountAndUpdateTicketPrice(getTestPayAsYouGo1(), TICKET_PRICE_1);        
        verify(mockCartItemCmdImpl, never()).setStatusMessage(anyString());        
        verify(mockService, never()).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));        
        verify(mockService, never()).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());        
    }
    
    @Test
    public void shouldGetStatusMessageBasedOnJobCentrePlusDiscountAvailableIfDiscountIsFalse() {
        doCallRealMethod().when(mockService).getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(false);
        mockService.getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);        
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());        
        verify(mockGetCardService, never()).getJobCentrePlusDiscountDetails(anyLong());        
        verify(mockService, never()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));        
    }

    @Test
    public void shouldGetTravelcardLengthStatusMessageBasedOnJobCentrePlusDiscountAvailableIfDiscountIsTrue() {
        doCallRealMethod().when(mockService).getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(true);
        mockService.getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);        
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());        
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());        
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));        
    }    
    
    @Test
    public void shouldGetTravelcardEndDateStatusMessageBasedOnJobCentrePlusDiscountAvailableIfDiscountIsTrue() {
        doCallRealMethod().when(mockService).getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(false);
        mockService.getStatusMessageBasedOnJobCentrePlusDiscountAvailable(CARD_ID_2, START_DATE, EXPIRY_DATE, STATUS_MESSAGE);        
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());        
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());        
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));        
    }

    @Test
    public void shouldAddJobCentrePlusInvestigationRecord() {
        doCallRealMethod().when(mockService).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));
        when(mockService.isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class))).thenReturn(true);
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        doNothing().when(mockService).addJobCentrePlusInvestigationRecord(any(JobCentrePlusDiscountDTO.class), anyLong());
        mockService.addJobCentrePlusInvestigationRecord(getTestTravelCard1());
        verify(mockService, atLeastOnce()).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockService, atLeastOnce()).addJobCentrePlusInvestigationRecord(any(JobCentrePlusDiscountDTO.class), anyLong());
    }
    
    @Test
    public void shouldNotAddJobCentrePlusInvestigationRecordIfInvestigationRecordNotRequired() {
        doCallRealMethod().when(mockService).addJobCentrePlusInvestigationRecord(any(CartItemCmdImpl.class));
        when(mockService.isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class))).thenReturn(false);
        mockService.addJobCentrePlusInvestigationRecord(getTestTravelCard1());
        verify(mockService, atLeastOnce()).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        verify(mockGetCardService, never()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockService, never()).addJobCentrePlusInvestigationRecord(any(JobCentrePlusDiscountDTO.class), anyLong());
    }

    @Test
    public void shouldGetJobCentrePlusDiscountPriceInputPrice() {
        double testDouble = 65L;
        doCallRealMethod().when(mockService).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockService.getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong())).thenReturn(testDouble);
        mockService.getJobCentrePlusDiscountPrice(getTestTravelCard1(), TICKET_PRICE_1);
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());        
        verify(mockService, atLeastOnce()).getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong());        
    }
    
    @Test
    public void shouldNotGetJobCentrePlusDiscountPriceInputPriceIfDiscountUnavailable() {
        doCallRealMethod().when(mockService).getJobCentrePlusDiscountPrice(any(CartItemCmdImpl.class), anyDouble());
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(false);
        mockService.getJobCentrePlusDiscountPrice(getTestTravelCard1(), TICKET_PRICE_1);
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());        
        verify(mockService, never()).getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong());        
    }
    
    @Test
    public void shouldGetJobCenterPlusDiscountAvailableForCardIdIfDiscountNotNull() {
        doCallRealMethod().when(mockService).isJobCenterPlusDiscountAvailableForCardId(anyLong());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(mockJobCentrePlusDiscountDTO);
        when(mockJobCentrePlusDiscountDTO.getJobCentrePlusDiscountAvailable()).thenReturn(any(Boolean.class));
        mockService.isJobCenterPlusDiscountAvailableForCardId(CARD_ID_2);
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockJobCentrePlusDiscountDTO, atLeastOnce()).getJobCentrePlusDiscountAvailable();
    }

    @Test
    public void shouldNotGetJobCenterPlusDiscountAvailableForCardIdIfDiscountIsNull() {
        doCallRealMethod().when(mockService).isJobCenterPlusDiscountAvailableForCardId(anyLong());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(null);
        mockService.isJobCenterPlusDiscountAvailableForCardId(CARD_ID_2);
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockJobCentrePlusDiscountDTO, never()).getJobCentrePlusDiscountAvailable();
    }
    
    @Test
    public void shouldGetTicketPriceWithJobCentrePlusDiscountRateApplied() {
        float testFloat = 65.23F;
        doCallRealMethod().when(mockService).getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(true);
        when(mockService.isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class))).thenReturn(false);
        when(mockSystemParameterService.getFloatParameterValue(anyString())).thenReturn(testFloat);
        mockService.getTicketPriceWithJobCentrePlusDiscountRate(DateTestUtil.APR_03, TICKET_PRICE_1, CARD_ID_2);
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class));
        verify(mockSystemParameterService, atLeastOnce()).getFloatParameterValue(anyString());
    }
    
    @Test
    public void shouldNotGetTicketPriceWithJobCentrePlusDiscountRateAppliedIfTravelCardEndDateNotLessThanDiscountExpiryDate() {
        doCallRealMethod().when(mockService).getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(false);
        mockService.getTicketPriceWithJobCentrePlusDiscountRate(DateTestUtil.APR_03, TICKET_PRICE_1, CARD_ID_2);
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
        verify(mockSystemParameterService, never()).getFloatParameterValue(anyString());
    }

    @Test
    public void shouldNotGetTicketPriceWithJobCentrePlusDiscountRateAppliedIfJobCentrePlusDiscountDurationIsGreaterThanMaximumAllowedMonths() {
        doCallRealMethod().when(mockService).getTicketPriceWithJobCentrePlusDiscountRate(anyString(), anyDouble(), anyLong());
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(getTestJobCentrePlusDiscountDTO1());
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(true);
        when(mockService.isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class))).thenReturn(true);
        mockService.getTicketPriceWithJobCentrePlusDiscountRate(DateTestUtil.APR_03, TICKET_PRICE_1, CARD_ID_2);
        verify(mockGetCardService, atLeastOnce()).getJobCentrePlusDiscountDetails(anyLong());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class));
    }
    
    @Test
    public void isAddingJobCentrePlusInvestigationRecordRequiredShouldReturnTrue() {
        doCallRealMethod().when(mockService).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(mockJobCentrePlusDiscountDTO);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(true);
        when(mockService.isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class))).thenReturn(true);
        mockService.isAddingJobCentrePlusInvestigationRecordRequired(getTestJobCentrePlusTravelCardCmd1());
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class));
    }

    @Test
    public void isAddingJobCentrePlusInvestigationRecordRequiredShouldReturnFalseIfJobCentrePlusDiscountNotAvailable() {
        doCallRealMethod().when(mockService).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(false);
        mockService.isAddingJobCentrePlusInvestigationRecordRequired(getTestJobCentrePlusTravelCardCmd1());
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());
    }

    @Test
    public void isAddingJobCentrePlusInvestigationRecordRequiredShouldReturnFalseIfTravelCardEndDateNotLessThanDiscountExpiryDate() {
        doCallRealMethod().when(mockService).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(mockJobCentrePlusDiscountDTO);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(false);
        mockService.isAddingJobCentrePlusInvestigationRecordRequired(getTestJobCentrePlusTravelCardCmd1());
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
    }

    @Test
    public void isAddingJobCentrePlusInvestigationRecordRequiredShouldReturnFalseIfJobCentrePlusDiscountDurationNotGreaterThanMaximumAllowedMonths() {
        doCallRealMethod().when(mockService).isAddingJobCentrePlusInvestigationRecordRequired(any(CartItemCmdImpl.class));
        when(mockGetCardService.getJobCentrePlusDiscountDetails(anyLong())).thenReturn(mockJobCentrePlusDiscountDTO);
        when(mockService.isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString())).thenReturn(true);
        when(mockService.isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class))).thenReturn(true);
        when(mockService.isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class))).thenReturn(false);
        mockService.isAddingJobCentrePlusInvestigationRecordRequired(getTestJobCentrePlusTravelCardCmd1());
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountAvailable(anyLong(), anyString(), anyString());
        verify(mockService, atLeastOnce()).isTravelCardEndDateLessThanDiscountExpiryDate(anyString(), any(Date.class));
        verify(mockService, atLeastOnce()).isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(any(Date.class));
    }

}

