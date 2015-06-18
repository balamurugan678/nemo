package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.data_service.impl.OysterCardDataServiceImpl;
import com.novacroft.nemo.mock_cubic.test_support.OysterCardTestUtil;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * AutoLoadChangeService unit tests
 */
public class EditAutoLoadChangeServiceImplTest {
    protected static final String TEST_BLANK = "";
    protected static final String TEST_NULL = null;
    protected static final String TEST_NOT_VALID_PRESTIGE_ID = "9999";
    protected static final String TEST_VALID_PRESTIGE_ID = "100000015152";
    private EditAutoLoadChangeServiceImpl mockEditAutoLoadChangeServiceImpl;
    protected AutoLoadRequest request = new AutoLoadRequest();
    private CubicCardResponseDAO mockCubicCardResponseDAO;

    @Before
    public void setUp() {
        mockEditAutoLoadChangeServiceImpl = new EditAutoLoadChangeServiceImpl();
        mockEditAutoLoadChangeServiceImpl.oysterCardDataService = mock(OysterCardDataServiceImpl.class);
        mockEditAutoLoadChangeServiceImpl.autoLoadResponseConverter = mock(XmlModelConverter.class);
        mockEditAutoLoadChangeServiceImpl.requestFailureConverter = mock(XmlModelConverter.class);
        when(mockEditAutoLoadChangeServiceImpl.getCardNotFoundResponse()).thenReturn("RequestFailure");
        when(mockEditAutoLoadChangeServiceImpl.autoLoadResponseConverter.convertModelToXml((AutoLoadResponse) any()))
                .thenReturn("AutoLoadResponse");
        when(mockEditAutoLoadChangeServiceImpl.oysterCardDataService.createOrUpdate(any(OysterCardDTO.class)))
                .thenReturn(OysterCardTestUtil.getTestOysterCardDTO1());
        request.setAutoLoadState(1);

        this.mockCubicCardResponseDAO = mock(CubicCardResponseDAO.class);
        this.mockEditAutoLoadChangeServiceImpl.cubicCardResponseDAO = this.mockCubicCardResponseDAO;
        when(this.mockCubicCardResponseDAO.getNextRequestSequenceNumber())
                .thenReturn(Long.valueOf(SettlementTestUtil.REQUEST_SEQUENCE_NUMBER));
    }

    @Test
    public void nullRequestShouldReturnCardNotFoundResponse() {
        request.setPrestigeId(TEST_NULL);
        String response = mockEditAutoLoadChangeServiceImpl.getAutoLoadChangeResponseForCard(request);
        assertTrue(response.contains("RequestFailure"));
    }

    @Test
    public void emptyRequestShouldReturnCardNotFoundResponse() {
        request.setPrestigeId(TEST_BLANK);
        String response = mockEditAutoLoadChangeServiceImpl.getAutoLoadChangeResponseForCard(request);
        assertTrue(response.contains("RequestFailure"));
    }

    @Test
    public void invalidRequestShouldReturnCardNotFoundResponse() {
        request.setPrestigeId(TEST_NOT_VALID_PRESTIGE_ID);
        String response = mockEditAutoLoadChangeServiceImpl.getAutoLoadChangeResponseForCard(request);
        assertTrue(response.contains("RequestFailure"));
    }

    @Test
    public void validRequestShouldReturnAutoLoadResponse() {
        request.setPrestigeId(TEST_VALID_PRESTIGE_ID);
        when(mockEditAutoLoadChangeServiceImpl.oysterCardDataService.findByCardNumber(anyString()))
                .thenReturn(OysterCardTestUtil.getTestOysterCardDTO1());
        String response = mockEditAutoLoadChangeServiceImpl.getAutoLoadChangeResponseForCard(request);
        assertTrue(response.contains("AutoLoadResponse"));
        verify(this.mockCubicCardResponseDAO).getNextRequestSequenceNumber();
    }
}