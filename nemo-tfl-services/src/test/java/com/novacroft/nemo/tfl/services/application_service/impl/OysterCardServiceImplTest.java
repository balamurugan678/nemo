package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.impl.CardConverterImpl;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;
import com.novacroft.nemo.tfl.services.util.WebServiceResultUtil;

public class OysterCardServiceImplTest {

    private OysterCardServiceImpl service;
    private OysterCardServiceImpl mockService;
    private GetCardService mockGetCardService;
    private CardConverterImpl mockCardConverter;
    private CustomerService mockCustomerService; 
    private CustomerDataService mockCustomerDataService; 
    private CardDataService mockCardDataService;

    @Before
    public void setUp() throws Exception {
        service = new OysterCardServiceImpl();
        mockGetCardService = mock(GetCardService.class);
        mockCardConverter = mock(CardConverterImpl.class);
        mockCustomerService = mock(CustomerService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        service.getCardService = mockGetCardService;
        service.cardConverter = mockCardConverter;
        service.customerService = mockCustomerService;
        service.customerDataService = mockCustomerDataService;
        service.cardDataService = mockCardDataService;
        
        mockService = mock(OysterCardServiceImpl.class);
        mockService.customerDataService = mockCustomerDataService;
        mockService.getCardService = mockGetCardService;
        mockService.customerService = mockCustomerService;
        mockService.customerDataService = mockCustomerDataService;
        mockService.cardDataService = mockCardDataService;
    }

    @Test
    public void testGetCardSuccessScenario() {
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCardConverter.convert(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        Card response = service.getCard(CardInfoResponseV2TestUtil.OYSTER_NUMBER_1);
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    public void testGetCardWithIdSuccessScenario() {
        when(mockCardConverter.convert(any(CardDTO.class))).thenCallRealMethod();
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(new CardDTO());
        Card response = service.getCard(CardTestUtil.EXTERNAL_CARD_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetCardWithIdSuccessScenarioIfCardNumberIsNotNull() {
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCardConverter.convert(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(CardTestUtil.getTestCardDTO1());
        Card response = service.getCard(CardTestUtil.EXTERNAL_CARD_ID);
        assertNotNull(response);
    }
    
    @Test
    public void testGetCardWithIdFailureScenario() {
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCardConverter.convert(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(null);
        when(mockService.getContent(anyString())).thenReturn("");
        doCallRealMethod().when(mockService).getCard(anyLong());
        Card response = mockService.getCard(CardTestUtil.EXTERNAL_CARD_ID);
        assertNotNull(response);
    }

    @Test
    public void testGetCardExceptionScenario() {
        BDDMockito.willThrow(new ApplicationServiceException()).given(mockGetCardService).getCard(anyString());
        Card response = service.getCard(CardInfoResponseV2TestUtil.OYSTER_NUMBER_1);
        assertNotNull(response);
    }

    @Test
    public void shouldCreateOysterCard() {
        when(mockCustomerDataService.findByExternalId((Long)any())).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(getTestCardDTO1());
        WebServiceResult result = service.createOysterCard(CUSTOMER_ID_1);
        assertNotNull(result);   
        assertEquals(WebServiceResultAttribute.SUCCESS.name(), result.getResult());
        verify(mockCustomerService, atLeastOnce()).createCard((Long)any(), anyString());
        
    }
    
    @Test
    public void shouldNotCreateOysterCard() {
        when(mockCustomerDataService.findByExternalId((Long)any())).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(null);
        WebServiceResult result = service.createOysterCard(CUSTOMER_ID_1);
        assertNotNull(result);   
        assertEquals(WebServiceResultAttribute.CREATE_CARD_FAILURE.name(), result.getResult());
        verify(mockCustomerService, atLeastOnce()).createCard((Long)any(), anyString());
        
    }

    @Test
    public void shouldNotCreateOysterCardIfCustomerNotFound() {
        when(mockService.createOysterCard((Long)any())).thenCallRealMethod();
        when(mockCustomerDataService.findByExternalId((Long)any())).thenReturn(null);
        when(mockService.getContent(anyString())).thenReturn(" ");
        WebServiceResult result = mockService.createOysterCard(CUSTOMER_ID_1);
        assertNotNull(result);        
        verify(mockCustomerService, never()).createCard((Long)any(), anyString());
    }

    @Test
    public void shoudCreateOysterCardWithCardNumber(){
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockService.checkIfCardAlreadyExists(anyString(), anyLong())).thenCallRealMethod();
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CUSTOMER_ID_1);
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(null);
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.SUCCESS.name(), result.getResult());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotCreateOysterCardIfCubicCallReturnsNull() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockGetCardService.getCard(anyString())).thenReturn(null);
        when(mockService.getContent(anyString())).thenReturn(" ");
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.CARD_NOT_FOUND.name(), result.getResult());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotCreateOysterCardIfCubicCallReturnsErrorCall() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(null);
        CardInfoResponseV2DTO cardInfo = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1();
        cardInfo.setErrorDescription(CardInfoResponseV2TestUtil.UNEXPECTED_SERVER_ERROR_DESCRIPTION);
        when(mockGetCardService.getCard(anyString())).thenReturn(cardInfo);
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CUSTOMER_ID_1);
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.UNEXPECTED_SERVER_ERROR.name(), result.getResult());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotCreateOysterCardIfInvalidCustomer() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(null);
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.name(), result.getResult());
        assertNotNull(result.getId());
    }

    @Test
    public void shouldNotCreatOysterCardIfAnExceptionIsThrown() {
        BDDMockito.willThrow(Exception.class).given(mockGetCardService).getCard(anyString());
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.FAILURE.name(), result.getResult());
    }

    @Test
    public void shouldNotCreateOysterCardIfCardIsNull() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(null);
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CUSTOMER_ID_1);
        when(mockCustomerService.createCard(anyLong(), anyString())).thenReturn(null);
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.CARD_NOT_FOUND.name(), result.getResult());
    }

    @Test
    public void shouldNotCreateOysterCardIfCardIsAlreadyAssociated() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CUSTOMER_ID_1);
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(mockService.checkIfCardAlreadyExists(anyString(), anyLong())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_ERROR.name(), result.getResult());
        assertNotNull(result.getId());

        verify(mockCustomerService, never()).createCard(anyLong(), anyString());
    }

    @Test
    public void shouldNotCreateOysterCardIfCardIsAlreadyAssociatedToAnotherCustomer() {
        when(mockService.createOysterCard(anyLong(), anyString())).thenCallRealMethod();
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1());
        when(mockCustomerDataService.getInternalIdFromExternalId(anyLong())).thenReturn(CUSTOMER_ID_1);
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockService.rejectIfErrorWithCubicCall(any(CardInfoResponseV2DTO.class), anyLong())).thenCallRealMethod();
        when(mockService.rejectIfInvalidCustomerId(anyLong(), anyLong())).thenCallRealMethod();
        when(mockService.checkIfCardAlreadyExists(anyString(), anyLong())).thenCallRealMethod();
        when(mockCardDataService.findByCustomerIdAndCardNumber(anyLong(), anyString())).thenReturn(null);
        when(mockCardDataService.findByCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        WebServiceResult result = mockService.createOysterCard(CustomerTestUtil.EXTERNAL_CUSTOMER_ID, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(result);
        assertEquals(WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_TO_ANOTHER_CUSTOMER_ERROR.name(), result.getResult());
        assertNotNull(result.getId());

        verify(mockCustomerService, never()).createCard(anyLong(), anyString());
    }
    
    @Test
    public void shouldUpdateCard(){
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(new CardDTO());
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(new CardDTO());
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO19());
        when(mockCardConverter.convert(any(CardInfoResponseV2DTO.class))).thenCallRealMethod();
        Card card = new Card();
        card.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        Card updatedCard = service.updateCard(CardTestUtil.EXTERNAL_CARD_ID, card);
        assertNotNull(updatedCard);
        assertNotNull(updatedCard.getPrestigeId());
    }
    
    @Test
    public void shouldNotUpdateCard(){
        when(mockService.updateCard(anyLong(), any(Card.class))).thenCallRealMethod();
        when(mockService.getContent(anyString())).thenReturn("");
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(null);
        Card card = new Card();
        card.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        Card updatedCard = mockService.updateCard(CardTestUtil.EXTERNAL_CARD_ID, card);
        assertNotNull(updatedCard);
        assertNotNull(card.getErrorCode());
    }
    
    @Test
    public void shouldNotUpdateCardIfCardNumberAlreadyAssociatedToAnotherAccount(){
        when(mockCardDataService.findByExternalId(anyLong())).thenReturn(getTestCardDTO1());
        when(mockService.checkIfCardAlreadyExists(anyString(), anyLong())).thenReturn(WebServiceResultUtil.generateResult(CardTestUtil.EXTERNAL_CARD_ID, null, WebServiceResultAttribute.CARD_ALREADY_ASSOCIATED_ERROR.name(), null));
        when(mockService.updateCard(anyLong(), any(Card.class))).thenCallRealMethod();
        Card card = new Card();
        card.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        Card updatedCard = mockService.updateCard(CardTestUtil.EXTERNAL_CARD_ID, card);
        assertNotNull(updatedCard);
        assertNotNull(card.getErrorCode());
    }
}
