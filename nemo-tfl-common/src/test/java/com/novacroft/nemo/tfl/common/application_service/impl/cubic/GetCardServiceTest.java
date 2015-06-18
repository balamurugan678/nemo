package com.novacroft.nemo.tfl.common.application_service.impl.cubic;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.BALANCE_0;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.BALANCE_1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO4;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithDiscountEntitlement2;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithDiscountEntitlement3;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithEmptyPPV;
import static com.novacroft.nemo.test_support.GetCardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.GetCardTestUtil.CARD_NUMBER_1;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.DISCOUNT_EXPIRY_DATE_1;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.JOB_CENTRE_PLUS;
import static com.novacroft.nemo.test_support.JobCentrePlusDiscountTestUtil.getTestJobCentrePlusDiscountDTO1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.common.transfer.PassengerAndDiscountTypeDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.cubic.GetCardRequestDataService;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoRequestV2DTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * AutoLoadConfigurationChangePushToGateService unit tests
 */
public class GetCardServiceTest {
    private GetCardServiceImpl service;
    private GetCardServiceImpl mockService;
    private GetCardRequestDataService mockGetCardRequestDataService;
    private SystemParameterService mockSystemParameterService;
    private CardDataService mockCardDataService;
    
    protected final static String DUMMY_PARAMETER_VALUE = "dummy-parameter-value";
    
    @Before
    public void setUp() {
        service = new GetCardServiceImpl();
        mockService = mock(GetCardServiceImpl.class);
        
        mockGetCardRequestDataService = mock(GetCardRequestDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockCardDataService = mock(CardDataService.class);
        
        service.getCardRequestDataService = mockGetCardRequestDataService;
        service.systemParameterService = mockSystemParameterService;
        service.cardDataService = mockCardDataService;
        mockService.getCardRequestDataService = mockGetCardRequestDataService;
        mockService.systemParameterService = mockSystemParameterService;
        mockService.cardDataService = mockCardDataService;
    }
    
    @Test
    public void getCardShouldGetDetails() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);
        when(mockGetCardRequestDataService.getCard(any(CardInfoRequestV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO1());
        when(mockService.checkAndPopulateNodesExcludingLeafNodes(any(CardInfoResponseV2DTO.class))).thenReturn(new CardInfoResponseV2DTO());
        when(mockService.populateDefaultPPVValues(any(CardInfoResponseV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO1());
        when(mockService.getCard(anyString())).thenCallRealMethod();
        when(mockService.isErrorResponse(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.FALSE);
        when(mockService.isNotOrderCardFunctionality(CARD_NUMBER_1)).thenReturn(Boolean.TRUE);

        CardInfoResponseV2DTO resultDTO = mockService.getCard(CARD_NUMBER_1);
        
        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockGetCardRequestDataService, atLeastOnce()).getCard(any(CardInfoRequestV2DTO.class));
        verify(mockService, atLeastOnce()).isErrorResponse(any(CardInfoResponseV2DTO.class));
        assertEquals(BALANCE_1, resultDTO.getPpvDetails().getBalance());
    }
    
    @Test(expected = ApplicationServiceException.class)
    public void getCardShouldGetDetailsShouldError() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);
        when(mockGetCardRequestDataService.getCard(any(CardInfoRequestV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO1());
        when(mockService.checkAndPopulateNodesExcludingLeafNodes(any(CardInfoResponseV2DTO.class))).thenReturn(new CardInfoResponseV2DTO());
        when(mockService.populateDefaultPPVValues(any(CardInfoResponseV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO1());
        when(mockService.getCard(anyString())).thenCallRealMethod();
        when(mockService.isErrorResponse(any(CardInfoResponseV2DTO.class))).thenReturn(Boolean.TRUE);
        when(mockService.isNotOrderCardFunctionality(CARD_NUMBER_1)).thenReturn(Boolean.TRUE);

        CardInfoResponseV2DTO resultDTO = mockService.getCard(CARD_NUMBER_1);
        
        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockGetCardRequestDataService, atLeastOnce()).getCard(any(CardInfoRequestV2DTO.class));
        verify(mockService, atLeastOnce()).isErrorResponse(any(CardInfoResponseV2DTO.class));
        assertEquals(BALANCE_1, resultDTO.getPpvDetails().getBalance());
    }
    
    @Test
    public void shouldGetJobCentrePlusDiscountDetails() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);
        when(mockGetCardRequestDataService.getCard(any(CardInfoRequestV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO4());
        
        JobCentrePlusDiscountDTO  resultDTO = service.getJobCentrePlusDiscountDetails(CARD_NUMBER_1);
        
        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockGetCardRequestDataService, atLeastOnce()).getCard(any(CardInfoRequestV2DTO.class));
        assertTrue(resultDTO.getJobCentrePlusDiscountAvailable());
    }
    
    @Test
    public void shouldGetJobCentrePlusDiscountDetailsByCardId() {
        when(mockCardDataService.findById(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);
        when(mockGetCardRequestDataService.getCard(any(CardInfoRequestV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTO4());

        JobCentrePlusDiscountDTO resultDTO = service.getJobCentrePlusDiscountDetails(CARD_ID);

        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
        verify(mockCardDataService, atLeastOnce()).findById(anyLong());
        verify(mockGetCardRequestDataService, atLeastOnce()).getCard(any(CardInfoRequestV2DTO.class));
        assertTrue(resultDTO.getJobCentrePlusDiscountAvailable());
    }

    @Test(expected = NullPointerException.class)
    public void getJobCentrePlusDiscountDetailsShouldError() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(DUMMY_PARAMETER_VALUE);
        when(mockGetCardRequestDataService.getCard(any(CardInfoRequestV2DTO.class))).thenReturn(null);
        
        service.getJobCentrePlusDiscountDetails(CARD_NUMBER_1);  
    } 
    
    @Test
    public void shouldPopulateJobCentrePlusDiscountDetailsWithDiscountEntitlement2() {
        JobCentrePlusDiscountDTO resultDTO = service.populateJobCentrePlusDiscountDetails(getTestCardInfoResponseV2DTOWithDiscountEntitlement2());
        assertTrue(resultDTO.getJobCentrePlusDiscountAvailable());
        assertEquals(BALANCE_1, resultDTO.getPaygBalance());
    }

    @Test
    public void shouldPopulateJobCentrePlusDiscountDetailsWithDiscountEntitlement3() {
        JobCentrePlusDiscountDTO resultDTO = service.populateJobCentrePlusDiscountDetails(getTestCardInfoResponseV2DTOWithDiscountEntitlement3());
        assertTrue(resultDTO.getJobCentrePlusDiscountAvailable());
        assertEquals(BALANCE_1, resultDTO.getPaygBalance());
    }
    
    @Test
    public void populateJobCentrePlusDiscountDetailsShouldReturnNull() {
        JobCentrePlusDiscountDTO  resultDTO = service.populateJobCentrePlusDiscountDetails(null);
        assertNull(resultDTO);
    } 
    
    @Test
    public void isCardProductHasJobCentrePlusDiscountShouldReturnTrue() {
        assertTrue(service.isCardProductHasJobCentrePlusDiscount(JOB_CENTRE_PLUS));
    }
    
    @Test
    public void isCardProductHasJobCentrePlusDiscountShouldReturnFalse() {
        assertFalse(service.isCardProductHasJobCentrePlusDiscount(DISCOUNT_EXPIRY_DATE_1));
    } 
    
    @Test
    public void shouldSetDiscountExpiry() {
        JobCentrePlusDiscountDTO jobCentrePlusDiscountDTO = getTestJobCentrePlusDiscountDTO1();
        service.setDiscountExpiry(jobCentrePlusDiscountDTO, DISCOUNT_EXPIRY_DATE_1);
        assertTrue(jobCentrePlusDiscountDTO.getJobCentrePlusDiscountAvailable());
    }

    @Test
    public void shouldPopulateDefaultPPVValues() {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = service.populateDefaultPPVValues(getTestCardInfoResponseV2DTOWithEmptyPPV());
        assertEquals(BALANCE_0, cardInfoResponseV2DTO.getPpvDetails().getBalance());
    }
    
    @Test
    public void getPassengerAndDiscountType(){
        when(mockService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOWithDiscountEntitlement3());
        when(mockService.getPassengerAndDiscountType(anyString(), any(Date.class))).thenCallRealMethod();
        
        PassengerAndDiscountTypeDTO passengerAndDiscountType = mockService.getPassengerAndDiscountType(CARD_NUMBER_1, new Date());
        assertNotNull(passengerAndDiscountType);
        assertEquals("Adult", passengerAndDiscountType.getPassengerType());
        
    }
   
}
