package com.novacroft.nemo.mock_cubic.rest.controller;

import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_AUTO_LOAD_STATE_2;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_PASSWORD_1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_PRESTIGE_ID_1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.TEST_USER_ID_1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.getTestAutoLoadRequest1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.getTestAutoLoadResponseXml1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.getTestRequestPrepayValueXml1;
import static com.novacroft.nemo.test_support.AutoLoadConfigurationChangeTestUtil.getTestRequestXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestRequestPrePayTicketXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestRemoveUpdateRequestXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardRemoveUpdateRequest1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdatePrePayValueRequest1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getCardUpdatePrePayTicketRequest1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdatePrePayTicketResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdateRemoveResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PICKUP_LOCATION;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.USER_ID;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PASSWORD;
import static com.novacroft.nemo.test_support.LocationTestUtil.LOCATION_ID_1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.service.EditAutoLoadChangeService;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;

public class MainControllerTest {
    private transient MainController controller;
    private transient MainController mockController;
    private transient CardResponseService cardResponseService;
    private transient EditAutoLoadChangeService editAutoLoadChangeService;
    private transient UpdateCardService updateCardService;
    private transient XmlModelConverter<CardInfoRequestV2> cardInfoRequestV2Converter;
    private transient XmlModelConverter<RequestFailure> requestFailureConverter;

    private static final String TEST_REQUEST_BODY = String.format("<AutoLoadRequest>" + "<RealTimeFlag>N</RealTimeFlag>" + "<PrestigeID>" + "%s"
                    + "</PrestigeID>" + "<AutoLoadState>" + "%s" + "</AutoLoadState>" + "<PaymentMethod>32</PaymentMethod>" + "<PickupLocation>"
                    + "%s" + "</PickupLocation>" + "<OriginatorInfo><UserID>" + "%s" + "</UserID>" + "<Password>" + "%s"
                    + "</Password></OriginatorInfo>" + "</AutoLoadRequest>", TEST_PRESTIGE_ID_1, TEST_AUTO_LOAD_STATE_2, LOCATION_ID_1,
                    TEST_USER_ID_1, TEST_PASSWORD_1);

    private static final String TEST_PRE_PAY_VALUE_REQUEST_BODY = String.format("<CardUpdateRequest>" + "<RealTimeFlag>N</RealTimeFlag>"
                    + "<PrestigeID>" + "%s" + "</PrestigeID>" + "<Action>" + "%s" + "</Action>" + "<PPV><PrepayValue>" + "%s" + "</PrepayValu>"
                    + "<Currency>" + "%s" + "</Currency></PPV>" + "<PaymentMethod>32</PaymentMethod>" + "<PickupLocation>" + "%s"
                    + "</PickupLocation>" + "<OriginatorInfo><UserID>" + "%s" + "</UserID>" + "<Password>" + "%s" + "</Password></OriginatorInfo>"
                    + "</CardUpdateRequest>", TEST_PRESTIGE_ID_1, "ADD", "2000", "0", LOCATION_ID_1, TEST_USER_ID_1, TEST_PASSWORD_1);

    private static final String TEST_PRE_PAY_TICKET_REQUEST_BODY = String.format("<CardUpdateRequest>" + "<RealTimeFlag>N</RealTimeFlag>"
                    + "<PrestigeID>" + "%s" + "</PrestigeID>" + "<Action>" + "%s" + "</Action>" + "<PPT><ProductCode>" + "%s" + "</ProductCode>"
                    + "<StartDate>" + "%s" + "</StartDate>" + "<ExpiryDate>" + "%s" + "</ExpiryDate>" + "<ProductPrice>" + "%s" + "</ProductPrice>"
                    + "<Currency>" + "%s" + "</Currency></PPV>" + "<PaymentMethod>32</PaymentMethod>" + "<PickupLocation>" + "%s"
                    + "</PickupLocation>" + "<OriginatorInfo><UserID>" + "%s" + "</UserID>" + "<Password>" + "%s" + "</Password></OriginatorInfo>"
                    + "</CardUpdateRequest>", PRESTIGE_ID, "ADD", "123", "10/10/2002", "11/10/2002", "360", "0", PICKUP_LOCATION, USER_ID, PASSWORD);

    private static final String TEST_REMOVE_REQUEST_BODY = String.format("<CardUpdateRequest>" + "<PrestigeID>" + "%s" + "</PrestigeID>" + "<Action>"
                    + "%s" + "</Action>" + "<OriginalRequestSequenceNumber>" + "%s" + "</OriginalRequestSequenceNumber>" + "<OriginatorInfo><UserID>"
                    + "%s" + "</UserID>" + "<Password>" + "%s" + "</Password></OriginatorInfo>" + "</CardUpdateRequest>", PRESTIGE_ID, "REMOVE", "5",
                    USER_ID, PASSWORD);

    private static final String CARD_DETAILS = "012345678902";
    private static final String STRING_WITHOUT_TRAILING_EQUALS = "ABC";
    private static final String STRING_WITH_TRAILING_EQUALS = STRING_WITHOUT_TRAILING_EQUALS + "=";

    @Before
    public void setUp() throws Exception {
        controller = new MainController();

        cardResponseService = mock(CardResponseService.class);
        controller.cardResponseService = cardResponseService;

        editAutoLoadChangeService = mock(EditAutoLoadChangeService.class);
        controller.editAutoLoadChangeService = editAutoLoadChangeService;

        updateCardService = mock(UpdateCardService.class);
        controller.updateCardService = updateCardService;

        cardInfoRequestV2Converter = mock(XmlModelConverter.class);
        controller.cardInfoRequestV2Converter = cardInfoRequestV2Converter;

        requestFailureConverter = mock(XmlModelConverter.class);

        mockController = mock(MainController.class);
    }

    @Test
    public void stripTrailingEqualsWithTrailingEquals() {
        assertEquals(STRING_WITHOUT_TRAILING_EQUALS, controller.stripTrailingEquals(STRING_WITH_TRAILING_EQUALS));
    }

    @Test
    public void stripTrailingEqualsWithoutTrailingEquals() {
        assertEquals(STRING_WITHOUT_TRAILING_EQUALS, controller.stripTrailingEquals(STRING_WITHOUT_TRAILING_EQUALS));
    }

    @Test
    public void shouldGetXmlAsStringFromRequestBody() {
        assertEquals(getTestRequestXml1(), controller.getXmlAsStringFromRequestBody(TEST_REQUEST_BODY));
    }

    @Test(expected = ControllerException.class)
    public void throwsErrorGetXmlAsStringFromRequestBody() {
        when(mockController.stripTrailingEquals(anyString())).thenThrow(UnsupportedEncodingException.class);
        when(mockController.getXmlAsStringFromRequestBody(anyString())).thenCallRealMethod();
        mockController.getXmlAsStringFromRequestBody(TEST_REQUEST_BODY);
    }

    @Test
    public void shouldCallServiceForCardInfoRequestV2() {
        when(cardResponseService.getCardDetails(any(CardInfoRequestV2.class))).thenReturn(CARD_DETAILS);
        assertEquals(CARD_DETAILS, controller.callService(new CardInfoRequestV2()));
        verify(cardResponseService).getCardDetails(any(CardInfoRequestV2.class));
    }

    @Test
    public void shouldCallServiceForAutoLoadRequest() {
        when(editAutoLoadChangeService.getAutoLoadChangeResponse(any(AutoLoadRequest.class))).thenReturn(CARD_DETAILS);
        assertEquals(CARD_DETAILS, controller.callService(getTestAutoLoadRequest1()));
        verify(editAutoLoadChangeService).getAutoLoadChangeResponse(any(AutoLoadRequest.class));
    }

    @Test
    public void shouldCallServiceForPPVRequest() {
        when(updateCardService.update(any(CardUpdatePrePayValueRequest.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        assertEquals(getTestCardUpdatePrePayTicketResponse(), controller.callService(getCardUpdatePrePayValueRequest1()));
        verify(updateCardService).update(any(CardUpdatePrePayValueRequest.class));
    }

    @Test
    public void shouldCallServiceForPPTRequest() {
        when(updateCardService.update(any(CardUpdatePrePayTicketRequest.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        assertEquals(getTestCardUpdatePrePayTicketResponse(), controller.callService(getCardUpdatePrePayTicketRequest1()));
        verify(updateCardService).update(any(CardUpdatePrePayTicketRequest.class));
    }

    @Test
    public void shouldCallServiceForRemoveRequest() {
        when(updateCardService.remove(any(CardRemoveUpdateRequest.class))).thenReturn(getTestCardUpdateRemoveResponse());
        assertEquals(getTestCardUpdateRemoveResponse(), controller.callService(getCardRemoveUpdateRequest1()));
        verify(updateCardService).remove(any(CardRemoveUpdateRequest.class));
    }

    @Test
    public void shouldRespondCardNotFoundIfTheRequestDoesNotMatch() {
        when(mockController.callService(anyObject())).thenCallRealMethod();
        when(mockController.getCardNotFoundResponse()).thenReturn(getRequestFailureXml1());
        assertEquals(getRequestFailureXml1(), mockController.callService(anyObject()));
    }

    @Test
    public void shouldHandleRequest() {
        when(cardInfoRequestV2Converter.convertXmlToObject(anyString())).thenReturn(getTestAutoLoadRequest1());
        MainController mockController = mock(MainController.class);
        when(mockController.handleRequest(anyString())).thenCallRealMethod();
        when(mockController.getXmlAsStringFromRequestBody(anyString())).thenReturn(getTestRequestXml1());
        when(mockController.callService(anyObject())).thenReturn(getTestAutoLoadResponseXml1());
        mockController.cardInfoRequestV2Converter = cardInfoRequestV2Converter;

        assertEquals(getTestAutoLoadResponseXml1(), mockController.handleRequest(TEST_REQUEST_BODY));
        verify(mockController).handleRequest(anyString());
        verify(mockController).getXmlAsStringFromRequestBody(anyString());
        verify(cardInfoRequestV2Converter).convertXmlToObject(anyString());
        verify(mockController).callService(anyObject());
    }

    @Test
    public void shouldHandleRequestPrepayValue() {
        when(cardInfoRequestV2Converter.convertXmlToObject(anyString())).thenReturn(getCardUpdatePrePayValueRequest1());
        MainController mockController = mock(MainController.class);
        when(mockController.handleRequest(anyString())).thenCallRealMethod();
        when(mockController.getXmlAsStringFromRequestBody(anyString())).thenReturn(getTestRequestPrepayValueXml1());
        when(mockController.callService(anyObject())).thenReturn(getTestCardUpdatePrePayTicketResponse());
        mockController.cardInfoRequestV2Converter = cardInfoRequestV2Converter;

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockController.handleRequest(TEST_PRE_PAY_VALUE_REQUEST_BODY));
        verify(mockController).handleRequest(anyString());
        verify(mockController).getXmlAsStringFromRequestBody(anyString());
        verify(cardInfoRequestV2Converter).convertXmlToObject(anyString());
        verify(mockController).callService(anyObject());
    }

    @Test
    public void shouldHandleRequestPrepayTicket() {
        when(cardInfoRequestV2Converter.convertXmlToObject(anyString())).thenReturn(getCardUpdatePrePayTicketRequest1());
        MainController mockController = mock(MainController.class);
        when(mockController.handleRequest(anyString())).thenCallRealMethod();
        when(mockController.getXmlAsStringFromRequestBody(anyString())).thenReturn(getTestRequestPrePayTicketXml1());
        when(mockController.callService(anyObject())).thenReturn(getTestCardUpdatePrePayTicketResponse());
        mockController.cardInfoRequestV2Converter = cardInfoRequestV2Converter;

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockController.handleRequest(TEST_PRE_PAY_TICKET_REQUEST_BODY));
        verify(mockController).handleRequest(anyString());
        verify(mockController).getXmlAsStringFromRequestBody(anyString());
        verify(cardInfoRequestV2Converter).convertXmlToObject(anyString());
        verify(mockController).callService(anyObject());
    }

    @Test
    public void shouldHandleRequestRemovePending() {
        when(cardInfoRequestV2Converter.convertXmlToObject(anyString())).thenReturn(getCardRemoveUpdateRequest1());
        MainController mockController = mock(MainController.class);
        when(mockController.handleRequest(anyString())).thenCallRealMethod();
        when(mockController.getXmlAsStringFromRequestBody(anyString())).thenReturn(getTestRemoveUpdateRequestXml1());
        when(mockController.callService(anyObject())).thenReturn(getTestCardUpdateRemoveResponse());
        mockController.cardInfoRequestV2Converter = cardInfoRequestV2Converter;

        assertEquals(getTestCardUpdateRemoveResponse(), mockController.handleRequest(TEST_REMOVE_REQUEST_BODY));
        verify(mockController).handleRequest(anyString());
        verify(mockController).getXmlAsStringFromRequestBody(anyString());
        verify(cardInfoRequestV2Converter).convertXmlToObject(anyString());
        verify(mockController).callService(anyObject());

    }

    @Test
    public void shouldIssueCardNotFoundResponse() {
        controller.requestFailureConverter = requestFailureConverter;
        when(requestFailureConverter.convertModelToXml(any(RequestFailure.class))).thenReturn(getRequestFailureXml1());
        assertEquals(getRequestFailureXml1(), controller.getCardNotFoundResponse());
    }

}
