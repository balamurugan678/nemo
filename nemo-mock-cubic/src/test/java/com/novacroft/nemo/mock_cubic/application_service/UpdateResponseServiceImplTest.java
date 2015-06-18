package com.novacroft.nemo.mock_cubic.application_service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdatePrePayTicketResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdateRemoveResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml1;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdateResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;

public class UpdateResponseServiceImplTest {

    final static Integer TEST_ERROR_CODE1 = 40;
    final static String TEST_ERROR_DESCRIPTION1 = "CARD NOT FOUND";

    private UpdateResponseServiceImpl service;
    private XmlModelConverter<CardUpdateResponse> updateResponseConverter;
    private XmlModelConverter<CardRemoveUpdateResponse> removeUpdateResponseConverter;
    private XmlModelConverter<RequestFailure> requestFailureConverter;

    private AddRequestCmd addCmd;
    private RemoveRequestCmd removeCmd;

    @Before
    public void setUp() throws Exception {
        service = new UpdateResponseServiceImpl();
        updateResponseConverter = mock(XmlModelConverter.class);
        removeUpdateResponseConverter = mock(XmlModelConverter.class);
        requestFailureConverter = mock(XmlModelConverter.class);

        service.cardUpdateResponseConverter = updateResponseConverter;
        service.cardRemoveUpdateResponseConverter = removeUpdateResponseConverter;
        service.requestFailureConverter = requestFailureConverter;

        addCmd = mock(AddRequestCmd.class);
        removeCmd = mock(RemoveRequestCmd.class);
    }

    @Test
    public void shouldIssueUpdateSuccessResponse() {
        when(updateResponseConverter.convertModelToXml(any(CardUpdateResponse.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        assertEquals(getTestCardUpdatePrePayTicketResponse(), service.generateAddSuccessResponse(addCmd));
    }

    @Test
    public void shouldIssueRemoveSuccessReponse() {
        when(removeUpdateResponseConverter.convertModelToXml(any(CardRemoveUpdateResponse.class))).thenReturn(getTestCardUpdateRemoveResponse());
        assertEquals(getTestCardUpdateRemoveResponse(), service.generateRemoveSuccessResponse(removeCmd));
    }

    @Test
    public void shouldIssueFailureResponse() {
        when(requestFailureConverter.convertModelToXml(any(RequestFailure.class))).thenReturn(getRequestFailureXml1());
        assertEquals(getRequestFailureXml1(), service.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1));
    }
}
