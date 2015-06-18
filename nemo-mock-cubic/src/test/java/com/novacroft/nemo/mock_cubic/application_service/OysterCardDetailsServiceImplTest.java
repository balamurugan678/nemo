package com.novacroft.nemo.mock_cubic.application_service;

import static com.novacroft.nemo.mock_cubic.test_support.CardInfoResponseV2TestUtil.getCardInfoResponseV2;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardHotListReasonsTestUtil.getTestOysterCardHotListReasonsDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPptPendingTestUtil.getTestOysterCardPptPendingDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPpvPendingTestUtil.getTestOysterCardPpvPendingDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayTicketTestUtil.getTestOysterCardPrepayTicketDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayValueTestUtil.getTestOysterCardPrepayValueDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardTestUtil.getTestOysterCardDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.PendingItemsPptTestUtil.getPendingItemsPptTest;
import static com.novacroft.nemo.mock_cubic.test_support.PendingItemsTestUtil.getPendingItemsTest;
import static com.novacroft.nemo.mock_cubic.test_support.PrepayTicketSlotTestUtil.getPrePayTicketSlotTest;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml1;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardHotListReasonsDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPptPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPpvPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayTicketDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayValueDataService;
import com.novacroft.nemo.mock_cubic.data_service.impl.OysterCardDataServiceImpl;
import com.novacroft.nemo.mock_cubic.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.mock_cubic.test_support.PendingItemsTestUtil;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CommonCardTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;

public class OysterCardDetailsServiceImplTest {

    private OysterCardDetailsServiceImpl mockOysterCardDetailsService;
    private OysterCardDataServiceImpl mockOysterCardDataService;
    private OysterCardPptPendingDataService mockOysterCardPptPendingDataService;
    private OysterCardPpvPendingDataService mockOysterCardPpvPendingDataService;
    private OysterCardPrepayTicketDataService mockOysterCardPrepayTicketDataService;
    private OysterCardPrepayValueDataService mockOysterCardPrepayValueDataService;
    private OysterCardHotListReasonsDataService mockOysterCardHotListReasonsDataService;
    private AddCardResponseCmd cmd;
    private CardInfoResponseV2 mockCardInfoResponseV2;
    private OysterCardDTO oysterCardDTO;
    private OysterCardPptPendingDTO oysterCardPptPendingDTO;
    private OysterCardPpvPendingDTO oysterCardPpvPendingDTO;
    private OysterCardPrepayTicketDTO oysterCardPrepayTicketDTO;
    private OysterCardPrepayValueDTO oysterCardPrepayValueDTO;
    private OysterCardPrepayValueDTO mockOysterCardPrepayValueDTO;
    private AddCardResponseCmd mockCmd;
    private OysterCardPptPendingDTO mockCardDTO;
    private OysterCardDTO mockOysterCardDTO;
    private OysterCardHotListReasonsDTO mockOysterCardHotListReasonsDTO;
    private OysterCardPrepayTicketDTO mockOysterCardPrepayTicketDTO;
    private OysterCardPpvPendingDTO mockOysterCardPpvPendingDTO;
    private OysterCardPptPendingDTO mockOysterCardPptPendingDTO;
    private XmlModelConverter<CardInfoResponseV2> mockCardInfoResponseV2Converter;
    private XmlModelConverter<CardInfoResponseV2> mockCardInfoResponseV2EmptyTagConverter;
    private PendingItems mockPendingItems;
    private PrePayTicket mockPrePayTicket;
    private PrePayTicketSlot mockPrePayTicketSlot;
    private UpdateResponseService mockUpdateResponseService;
    private OysterCardPendingDataService mockOysterCardPendingDataService;

    private static final Integer TEST_NUM_1 = Integer.valueOf(1);
    private static final Integer TEST_NUM_2 = Integer.valueOf(2);
    private static final Integer TEST_NUM_3 = Integer.valueOf(3);
    private static final Long TEST_LONG_1 = new Long(100);
    private static final Integer TEST_ERROR_CODE1 = 40;
    private static final String TEST_ERROR_DESCRIPTION1 = "CARD NOT FOUND";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {

        mockOysterCardDetailsService = mock(OysterCardDetailsServiceImpl.class);
        cmd = new AddCardResponseCmd();
        oysterCardDTO = new OysterCardDTO();
        mockOysterCardDTO = mock(OysterCardDTO.class);
        oysterCardPptPendingDTO = new OysterCardPptPendingDTO();
        mockCardInfoResponseV2 = mock(CardInfoResponseV2.class);
        mockCardInfoResponseV2Converter = mock(XmlModelConverter.class);
        mockCardInfoResponseV2EmptyTagConverter = mock(XmlModelConverter.class);
        mockOysterCardHotListReasonsDTO = mock(OysterCardHotListReasonsDTO.class);
        mockOysterCardPrepayValueDTO = mock(OysterCardPrepayValueDTO.class);
        mockOysterCardPpvPendingDTO = mock(OysterCardPpvPendingDTO.class);
        mockOysterCardPptPendingDTO = mock(OysterCardPptPendingDTO.class);
        mockUpdateResponseService = mock(UpdateResponseService.class);
        mockOysterCardPendingDataService = mock(OysterCardPendingDataService.class);

        mockCmd = mock(AddCardResponseCmd.class);
        mockCardDTO = mock(OysterCardPptPendingDTO.class);
        mockOysterCardPrepayTicketDTO = mock(OysterCardPrepayTicketDTO.class);
        mockPrePayTicket = mock(PrePayTicket.class);
        mockPendingItems = mock(PendingItems.class);
        mockPrePayTicketSlot = mock(PrePayTicketSlot.class);

        mockOysterCardDataService = mock(OysterCardDataServiceImpl.class);
        mockOysterCardPptPendingDataService = mock(OysterCardPptPendingDataService.class);
        mockOysterCardPpvPendingDataService = mock(OysterCardPpvPendingDataService.class);
        mockOysterCardPrepayTicketDataService = mock(OysterCardPrepayTicketDataService.class);
        mockOysterCardPrepayValueDataService = mock(OysterCardPrepayValueDataService.class);
        mockOysterCardHotListReasonsDataService = mock(OysterCardHotListReasonsDataService.class);

        mockOysterCardDetailsService.oysterCardDataService = mockOysterCardDataService;
        mockOysterCardDetailsService.oysterCardPptPendingDataService = mockOysterCardPptPendingDataService;
        mockOysterCardDetailsService.oysterCardPpvPendingDataService = mockOysterCardPpvPendingDataService;
        mockOysterCardDetailsService.oysterCardPrepayTicketDataService = mockOysterCardPrepayTicketDataService;
        mockOysterCardDetailsService.oysterCardPrepayValueDataService = mockOysterCardPrepayValueDataService;
        mockOysterCardDetailsService.oysterCardHotListReasonsDataService = mockOysterCardHotListReasonsDataService;
        mockOysterCardDetailsService.cardInfoResponseV2Converter = mockCardInfoResponseV2Converter;
        mockOysterCardDetailsService.cardInfoResponseV2EmptyTagConverter = mockCardInfoResponseV2EmptyTagConverter;
        mockOysterCardDetailsService.updateResponseService = mockUpdateResponseService;
        mockOysterCardDetailsService.oysterCardPendingDataService = mockOysterCardPendingDataService;
    }

    @Test
    public void testCreateOrUpdateOysterCard() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateOysterCard(any(AddCardResponseCmd.class));

        doNothing().when(mockOysterCardDetailsService).createOrUpdateCard(cmd);
        doNothing().when(mockOysterCardDetailsService).createOrUpdateCardPptPending(cmd);
        doNothing().when(mockOysterCardDetailsService).createOrUpdateCardPpvPending(cmd);
        doNothing().when(mockOysterCardDetailsService).createOrUpdateCardPrepayTicket(cmd);
        doNothing().when(mockOysterCardDetailsService).createOrUpdateCardPrepayValue(cmd);

        mockOysterCardDetailsService.createOrUpdateOysterCard(cmd);

        verify(mockOysterCardDetailsService).createOrUpdateCard(cmd);
        verify(mockOysterCardDetailsService).createOrUpdateCardPptPending(cmd);
        verify(mockOysterCardDetailsService).createOrUpdateCardPpvPending(cmd);
        verify(mockOysterCardDetailsService).createOrUpdateCardPrepayTicket(cmd);
        verify(mockOysterCardDetailsService).createOrUpdateCardPrepayValue(cmd);
    }

    @Test
    public void testCreateOrUpdateCardWhenNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCard(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardDetailsService.createOysterCardDTO()).thenReturn(oysterCardDTO);
        when(mockOysterCardDetailsService.populateOysterCard(oysterCardDTO, cmd)).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardDetailsService.oysterCardDataService.createOrUpdate(any(OysterCardDTO.class))).thenReturn(getTestOysterCardDTO1());

        mockOysterCardDetailsService.createOrUpdateCard(cmd);

        verify(mockOysterCardDetailsService.oysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).createOysterCardDTO();
        verify(mockOysterCardDetailsService).populateOysterCard(any(OysterCardDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardDataService).createOrUpdate(any(OysterCardDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardWhenNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCard(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardDetailsService.populateOysterCard(oysterCardDTO, cmd)).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardDetailsService.oysterCardDataService.createOrUpdate(any(OysterCardDTO.class))).thenReturn(getTestOysterCardDTO1());

        mockOysterCardDetailsService.createOrUpdateCard(cmd);

        verify(mockOysterCardDetailsService.oysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).populateOysterCard(any(OysterCardDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardDataService).createOrUpdate(any(OysterCardDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPptPendingWhenNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPptPending(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardDetailsService.createOysterCardPptPendingDTO()).thenReturn(oysterCardPptPendingDTO);
        when(mockOysterCardDetailsService.populateOysterCardPptPending(oysterCardPptPendingDTO, cmd)).thenReturn(getTestOysterCardPptPendingDTO1());
        when(mockOysterCardDetailsService.oysterCardPptPendingDataService.createOrUpdate(any(OysterCardPptPendingDTO.class))).thenReturn(
                        getTestOysterCardPptPendingDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPptPending(cmd);

        verify(mockOysterCardDetailsService.oysterCardPptPendingDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).createOysterCardPptPendingDTO();
        verify(mockOysterCardDetailsService).populateOysterCardPptPending(any(OysterCardPptPendingDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPptPendingDataService).createOrUpdate(any(OysterCardPptPendingDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPptPendingWhenNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPptPending(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPptPendingDataService.findByCardNumber(anyString()))
                        .thenReturn(getTestOysterCardPptPendingDTO1());
        when(mockOysterCardDetailsService.populateOysterCardPptPending(oysterCardPptPendingDTO, cmd)).thenReturn(getTestOysterCardPptPendingDTO1());
        when(mockOysterCardDetailsService.oysterCardPptPendingDataService.createOrUpdate(any(OysterCardPptPendingDTO.class))).thenReturn(
                        getTestOysterCardPptPendingDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPptPending(cmd);

        verify(mockOysterCardDetailsService.oysterCardPptPendingDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).populateOysterCardPptPending(any(OysterCardPptPendingDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPptPendingDataService).createOrUpdate(any(OysterCardPptPendingDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPpvPendingWhenNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPpvPending(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPpvPendingDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardDetailsService.createOysterCardPpvPendingDTO()).thenReturn(oysterCardPpvPendingDTO);
        when(mockOysterCardDetailsService.populateOysterCardPpvPending(oysterCardPpvPendingDTO, cmd)).thenReturn(getTestOysterCardPpvPendingDTO1());
        when(mockOysterCardDetailsService.oysterCardPpvPendingDataService.createOrUpdate(any(OysterCardPpvPendingDTO.class))).thenReturn(
                        getTestOysterCardPpvPendingDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPpvPending(cmd);

        verify(mockOysterCardDetailsService.oysterCardPpvPendingDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).createOysterCardPpvPendingDTO();
        verify(mockOysterCardDetailsService).populateOysterCardPpvPending(any(OysterCardPpvPendingDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPpvPendingDataService).createOrUpdate(any(OysterCardPpvPendingDTO.class));
    }


    @Test
    public void testCreateOrUpdateCardPpvPendingWhenNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPpvPending(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPpvPendingDataService.findByCardNumber(anyString()))
                        .thenReturn(getTestOysterCardPpvPendingDTO1());
        when(mockOysterCardDetailsService.populateOysterCardPpvPending(oysterCardPpvPendingDTO, cmd)).thenReturn(getTestOysterCardPpvPendingDTO1());
        when(mockOysterCardDetailsService.oysterCardPpvPendingDataService.createOrUpdate(any(OysterCardPpvPendingDTO.class))).thenReturn(
                        getTestOysterCardPpvPendingDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPpvPending(cmd);

        verify(mockOysterCardDetailsService.oysterCardPpvPendingDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).populateOysterCardPpvPending(any(OysterCardPpvPendingDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPpvPendingDataService).createOrUpdate(any(OysterCardPpvPendingDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPrepayTicketWhenNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPrepayTicket(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardDetailsService.createOysterCardPrepayTicketDTO()).thenReturn(oysterCardPrepayTicketDTO);
        when(mockOysterCardDetailsService.populateOysterCardPrepayTicket(oysterCardPrepayTicketDTO, cmd)).thenReturn(
                        getTestOysterCardPrepayTicketDTO1());
        when(mockOysterCardDetailsService.oysterCardPrepayTicketDataService.createOrUpdate(any(OysterCardPrepayTicketDTO.class))).thenReturn(
                        getTestOysterCardPrepayTicketDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPrepayTicket(cmd);

        verify(mockOysterCardDetailsService.oysterCardPrepayTicketDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).createOysterCardPrepayTicketDTO();
        verify(mockOysterCardDetailsService).populateOysterCardPrepayTicket(any(OysterCardPrepayTicketDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPrepayTicketDataService).createOrUpdate(any(OysterCardPrepayTicketDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPrepayTicketWhenNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPrepayTicket(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(
                        getTestOysterCardPrepayTicketDTO1());
        when(mockOysterCardDetailsService.populateOysterCardPrepayTicket(oysterCardPrepayTicketDTO, cmd)).thenReturn(
                        getTestOysterCardPrepayTicketDTO1());
        when(mockOysterCardDetailsService.oysterCardPrepayTicketDataService.createOrUpdate(any(OysterCardPrepayTicketDTO.class))).thenReturn(
                        getTestOysterCardPrepayTicketDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPrepayTicket(cmd);

        verify(mockOysterCardDetailsService.oysterCardPrepayTicketDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).populateOysterCardPrepayTicket(any(OysterCardPrepayTicketDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPrepayTicketDataService).createOrUpdate(any(OysterCardPrepayTicketDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPrepayValueWhenNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPrepayValue(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardDetailsService.createOysterCardPrepayValueDTO()).thenReturn(oysterCardPrepayValueDTO);
        when(mockOysterCardDetailsService.populateOysterCardPrepayValue(oysterCardPrepayValueDTO, cmd))
                        .thenReturn(getTestOysterCardPrepayValueDTO1());
        when(mockOysterCardDetailsService.oysterCardPrepayValueDataService.createOrUpdate(any(OysterCardPrepayValueDTO.class))).thenReturn(
                        getTestOysterCardPrepayValueDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPrepayValue(cmd);

        verify(mockOysterCardDetailsService.oysterCardPrepayValueDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).createOysterCardPrepayValueDTO();
        verify(mockOysterCardDetailsService).populateOysterCardPrepayValue(any(OysterCardPrepayValueDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPrepayValueDataService).createOrUpdate(any(OysterCardPrepayValueDTO.class));
    }

    @Test
    public void testCreateOrUpdateCardPrepayValueWhenNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOrUpdateCardPrepayValue(any(AddCardResponseCmd.class));
        when(mockOysterCardDetailsService.oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(
                        getTestOysterCardPrepayValueDTO1());
        when(mockOysterCardDetailsService.populateOysterCardPrepayValue(oysterCardPrepayValueDTO, cmd))
                        .thenReturn(getTestOysterCardPrepayValueDTO1());
        when(mockOysterCardDetailsService.oysterCardPrepayValueDataService.createOrUpdate(any(OysterCardPrepayValueDTO.class))).thenReturn(
                        getTestOysterCardPrepayValueDTO1());

        mockOysterCardDetailsService.createOrUpdateCardPrepayValue(cmd);

        verify(mockOysterCardDetailsService.oysterCardPrepayValueDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).populateOysterCardPrepayValue(any(OysterCardPrepayValueDTO.class), any(AddCardResponseCmd.class));
        verify(mockOysterCardDetailsService.oysterCardPrepayValueDataService).createOrUpdate(any(OysterCardPrepayValueDTO.class));
    }

    @Test
    public void shouldPopulateOysterCardPptPendingWhenRequestSequenceNumbers123NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPptPending(mockCardDTO, mockCmd);
        doNothing().when(mockCardDTO).setPrestigeId(anyString());

        when(mockCmd.getPendingPptRequestSequenceNumber1()).thenReturn(TEST_NUM_1);
        doNothing().when(mockCardDTO).setRequestSequenceNumber1(anyLong());

        when(mockCmd.getPendingPptRequestSequenceNumber2()).thenReturn(TEST_NUM_2);
        doNothing().when(mockCardDTO).setRequestSequenceNumber2(anyLong());

        when(mockCmd.getPendingPptRequestSequenceNumber3()).thenReturn(TEST_NUM_3);
        doNothing().when(mockCardDTO).setRequestSequenceNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPptPending(mockCardDTO, mockCmd);

        verify(mockCardDTO).setPrestigeId(anyString());
        verify(mockCardDTO).setRequestSequenceNumber1(anyLong());
        verify(mockCardDTO).setRequestSequenceNumber2(anyLong());
        verify(mockCardDTO).setRequestSequenceNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPptPendingWhenRequestSequenceNumber1IsNullAnd23NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPptPending(mockCardDTO, mockCmd);
        doNothing().when(mockCardDTO).setPrestigeId(anyString());

        when(mockCmd.getPendingPptRequestSequenceNumber1()).thenReturn(null);

        when(mockCmd.getPendingPptRequestSequenceNumber2()).thenReturn(TEST_NUM_2);
        doNothing().when(mockCardDTO).setRequestSequenceNumber2(anyLong());

        when(mockCmd.getPendingPptRequestSequenceNumber3()).thenReturn(TEST_NUM_3);
        doNothing().when(mockCardDTO).setRequestSequenceNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPptPending(mockCardDTO, mockCmd);

        verify(mockCardDTO).setPrestigeId(anyString());
        verify(mockCardDTO, never()).setRequestSequenceNumber1(anyLong());
        verify(mockCardDTO).setRequestSequenceNumber2(anyLong());
        verify(mockCardDTO).setRequestSequenceNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPptPendingWhenRequestSequenceNumber12IsNullAnd3NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPptPending(mockCardDTO, mockCmd);
        doNothing().when(mockCardDTO).setPrestigeId(anyString());

        when(mockCmd.getPendingPptRequestSequenceNumber1()).thenReturn(null);

        when(mockCmd.getPendingPptRequestSequenceNumber2()).thenReturn(null);

        when(mockCmd.getPendingPptRequestSequenceNumber3()).thenReturn(TEST_NUM_3);
        doNothing().when(mockCardDTO).setRequestSequenceNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPptPending(mockCardDTO, mockCmd);

        verify(mockCardDTO).setPrestigeId(anyString());
        verify(mockCardDTO, never()).setRequestSequenceNumber1(anyLong());
        verify(mockCardDTO, never()).setRequestSequenceNumber2(anyLong());
        verify(mockCardDTO).setRequestSequenceNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPptPendingWhenRequestSequenceNumber123AllNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPptPending(mockCardDTO, mockCmd);
        doNothing().when(mockCardDTO).setPrestigeId(anyString());

        when(mockCmd.getPendingPptRequestSequenceNumber1()).thenReturn(null);

        when(mockCmd.getPendingPptRequestSequenceNumber2()).thenReturn(null);

        when(mockCmd.getPendingPptRequestSequenceNumber3()).thenReturn(null);

        mockOysterCardDetailsService.populateOysterCardPptPending(mockCardDTO, mockCmd);

        verify(mockCardDTO).setPrestigeId(anyString());
        verify(mockCardDTO, never()).setRequestSequenceNumber1(anyLong());
        verify(mockCardDTO, never()).setRequestSequenceNumber2(anyLong());
        verify(mockCardDTO, never()).setRequestSequenceNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPrepayTicketWhenSlotNumbers012NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);
        doNothing().when(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());

        when(mockCmd.getSlotNumber()).thenReturn(TEST_NUM_1);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber1(anyLong());

        when(mockCmd.getSlotNumber1()).thenReturn(TEST_NUM_2);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());

        when(mockCmd.getSlotNumber2()).thenReturn(TEST_NUM_3);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);

        verify(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber1(anyLong());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPrepayTicketWhenSlotNumber0IsNullSlots12NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);
        doNothing().when(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());

        when(mockCmd.getSlotNumber()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber1(anyLong());

        when(mockCmd.getSlotNumber1()).thenReturn(TEST_NUM_2);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());

        when(mockCmd.getSlotNumber2()).thenReturn(TEST_NUM_3);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);

        verify(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber1(anyLong());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPrepayTicketWhenSlotNumber01IsNullSlots2NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);
        doNothing().when(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());

        when(mockCmd.getSlotNumber()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber1(anyLong());

        when(mockCmd.getSlotNumber1()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());

        when(mockCmd.getSlotNumber2()).thenReturn(TEST_NUM_3);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);

        verify(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber1(anyLong());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber2(anyLong());
        verify(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());
    }

    @Test
    public void shouldPopulateOysterCardPrepayTicketWhenSlotNumber012IsNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);
        doNothing().when(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());

        when(mockCmd.getSlotNumber()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber1(anyLong());

        when(mockCmd.getSlotNumber1()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber2(anyLong());

        when(mockCmd.getSlotNumber2()).thenReturn(null);
        doNothing().when(mockOysterCardPrepayTicketDTO).setSlotNumber3(anyLong());

        mockOysterCardDetailsService.populateOysterCardPrepayTicket(mockOysterCardPrepayTicketDTO, mockCmd);

        verify(mockOysterCardPrepayTicketDTO).setPrestigeId(anyString());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber1(anyLong());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber2(anyLong());
        verify(mockOysterCardPrepayTicketDTO, never()).setSlotNumber3(anyLong());
    }

    @Test
    public void testGetCardDetailsWhenOysterCardNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardDetailsService.getOysterCardDetails(mockOysterCardDTO, mockCardInfoResponseV2)).thenReturn(getCardInfoResponseV2());
        when(mockCardInfoResponseV2EmptyTagConverter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).getOysterCardDetails(any(OysterCardDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2EmptyTagConverter).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsWhenOysterCardNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockUpdateResponseService.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1)).thenReturn(getRequestFailureXml1());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockUpdateResponseService).generateErrorResponse(anyInt(), anyString());
        verify(mockOysterCardDetailsService, never()).getOysterCardDetails(any(OysterCardDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2Converter, never()).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsHotListReasons() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardHotListReasonsDTO1());
        when(mockOysterCardDetailsService.getOysterCardHotListReasonsDetails(mockOysterCardHotListReasonsDTO, mockCardInfoResponseV2)).thenReturn(
                        getCardInfoResponseV2());
        when(mockCardInfoResponseV2EmptyTagConverter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService)
                        .getOysterCardHotListReasonsDetails(any(OysterCardHotListReasonsDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2EmptyTagConverter).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsPrepayValue() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayValueDTO1());
        when(mockOysterCardDetailsService.getOysterCardPrepayValueDetails(mockOysterCardPrepayValueDTO, mockCardInfoResponseV2)).thenReturn(
                        getCardInfoResponseV2());
        when(mockCardInfoResponseV2EmptyTagConverter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService).findByCardNumber(anyString());
        verify(mockOysterCardPrepayValueDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).getOysterCardPrepayValueDetails(any(OysterCardPrepayValueDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2EmptyTagConverter).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsPrepayTicket() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(mockOysterCardDetailsService.getOysterCardPrepayTicketDetails(mockOysterCardPrepayTicketDTO, mockCardInfoResponseV2)).thenReturn(
                        getCardInfoResponseV2());
        when(mockCardInfoResponseV2EmptyTagConverter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService).findByCardNumber(anyString());
        verify(mockOysterCardPrepayValueDataService).findByCardNumber(anyString());
        verify(mockOysterCardPrepayTicketDataService).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService).getOysterCardPrepayTicketDetails(any(OysterCardPrepayTicketDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2EmptyTagConverter).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetCardDetailsPendingWhenPpvAndPptNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardDTO1());
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPpvPendingDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPpvPendingDTO1());
        when(mockOysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPptPendingDTO1());
        when(
                        mockOysterCardDetailsService.getOysterCardPendingDetails(mockOysterCardPpvPendingDTO, mockOysterCardPptPendingDTO,
                                        mockCardInfoResponseV2)).thenReturn(getCardInfoResponseV2());
        when(mockCardInfoResponseV2EmptyTagConverter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService).findByCardNumber(anyString());
        verify(mockOysterCardPrepayValueDataService).findByCardNumber(anyString());
        verify(mockOysterCardPrepayTicketDataService).findByCardNumber(anyString());
        verify(mockOysterCardPendingDataService).findByCardNumber(anyString());

        verify(mockOysterCardDetailsService).getOysterCardPendingDetails(any(List.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2EmptyTagConverter).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsPendingWhenPpvNullAndPptNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPpvPendingDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPptPendingDTO1());
        when(
                        mockOysterCardDetailsService.getOysterCardPendingDetails(mockOysterCardPpvPendingDTO, mockOysterCardPptPendingDTO,
                                        mockCardInfoResponseV2)).thenReturn(getCardInfoResponseV2());
        when(mockCardInfoResponseV2Converter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPrepayValueDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPrepayTicketDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPpvPendingDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPptPendingDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService, never()).getOysterCardPendingDetails(any(OysterCardPpvPendingDTO.class),
                        any(OysterCardPptPendingDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2Converter, never()).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetCardDetailsPendingWhenPpvNotNullAndPptNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getCardDetails(anyString());
        when(mockOysterCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardHotListReasonsDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPpvPendingDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPpvPendingDTO1());
        when(mockOysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(null);
        when(
                        mockOysterCardDetailsService.getOysterCardPendingDetails(mockOysterCardPpvPendingDTO, mockOysterCardPptPendingDTO,
                                        mockCardInfoResponseV2)).thenReturn(getCardInfoResponseV2());
        when(mockCardInfoResponseV2Converter.convertModelToXml(any(CardInfoResponseV2.class))).thenReturn(getCardInfoResponseV2Example());

        mockOysterCardDetailsService.getCardDetails(anyString());

        verify(mockOysterCardDataService).findByCardNumber(anyString());
        verify(mockOysterCardHotListReasonsDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPrepayValueDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPrepayTicketDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPpvPendingDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardPptPendingDataService, never()).findByCardNumber(anyString());
        verify(mockOysterCardDetailsService, never()).getOysterCardPendingDetails(any(OysterCardPpvPendingDTO.class),
                        any(OysterCardPptPendingDTO.class), any(CardInfoResponseV2.class));
        verify(mockCardInfoResponseV2Converter, never()).convertModelToXml(any(CardInfoResponseV2.class));
    }

    @Test
    public void testGetOysterCardPendingDetailsPpvNotNullAndPptNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getOysterCardPendingDetails(mockOysterCardPpvPendingDTO, null, mockCardInfoResponseV2);
        when(mockOysterCardDetailsService.getPendingItemsPpv(mockPendingItems, mockOysterCardPpvPendingDTO)).thenReturn(getPendingItemsTest());
        doNothing().when(mockCardInfoResponseV2).setPendingItems(mockPendingItems);

        mockOysterCardDetailsService.getOysterCardPendingDetails(mockOysterCardPpvPendingDTO, null, mockCardInfoResponseV2);

        verify(mockOysterCardDetailsService).getPendingItemsPpv(any(PendingItems.class), any(OysterCardPpvPendingDTO.class));
    }

    @Test
    public void testGetOysterCardPendingDetailsPpvNullAndPptNotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getOysterCardPendingDetails(null, mockOysterCardPptPendingDTO, mockCardInfoResponseV2);
        when(mockOysterCardDetailsService.getPendingItemsPpt(mockPendingItems, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsTest());
        doNothing().when(mockCardInfoResponseV2).setPendingItems(mockPendingItems);

        mockOysterCardDetailsService.getOysterCardPendingDetails(null, mockOysterCardPptPendingDTO, mockCardInfoResponseV2);

        verify(mockOysterCardDetailsService).getPendingItemsPpt(any(PendingItems.class), any(OysterCardPptPendingDTO.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPendingItemsPptWhenReqSeqNums123NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getPendingItemsPpt(mockPendingItems, mockOysterCardPptPendingDTO);
        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber1()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPptPendingSlot1(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber2()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPptPendingSlot2(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber3()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPptPendingSlot3(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        doNothing().when(mockPendingItems).setPpts(any(List.class));

        mockOysterCardDetailsService.getPendingItemsPpt(mockPendingItems, mockOysterCardPptPendingDTO);

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber1();
        verify(mockOysterCardDetailsService).getPptPendingSlot1(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber1();
        verify(mockOysterCardDetailsService).getPptPendingSlot2(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber2();
        verify(mockOysterCardDetailsService).getPptPendingSlot3(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetPendingItemsPptWhenReqSeqNums123Null() {
        doCallRealMethod().when(mockOysterCardDetailsService).getPendingItemsPpt(mockPendingItems, mockOysterCardPptPendingDTO);
        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber1()).thenReturn(null);
        when(mockOysterCardDetailsService.getPptPendingSlot1(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber2()).thenReturn(null);
        when(mockOysterCardDetailsService.getPptPendingSlot2(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        when(mockOysterCardPptPendingDTO.getRequestSequenceNumber3()).thenReturn(null);
        when(mockOysterCardDetailsService.getPptPendingSlot3(mockPrePayTicket, mockOysterCardPptPendingDTO)).thenReturn(getPendingItemsPptTest());

        doNothing().when(mockPendingItems).setPpts(any(List.class));

        mockOysterCardDetailsService.getPendingItemsPpt(mockPendingItems, mockOysterCardPptPendingDTO);

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber1();
        verify(mockOysterCardDetailsService, never()).getPptPendingSlot1(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber1();
        verify(mockOysterCardDetailsService, never()).getPptPendingSlot2(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));

        verify(mockOysterCardPptPendingDTO).getRequestSequenceNumber2();
        verify(mockOysterCardDetailsService, never()).getPptPendingSlot3(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetOysterCardPrepayTicketDetailsWhenSlotNums123NotNull() {
        doCallRealMethod().when(mockOysterCardDetailsService).getOysterCardPrepayTicketDetails(mockOysterCardPrepayTicketDTO, mockCardInfoResponseV2);
        when(mockOysterCardPrepayTicketDTO.getSlotNumber1()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPrePayTicketSlot1(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        when(mockOysterCardPrepayTicketDTO.getSlotNumber2()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPrePayTicketSlot2(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        when(mockOysterCardPrepayTicketDTO.getSlotNumber3()).thenReturn(TEST_LONG_1);
        when(mockOysterCardDetailsService.getPrePayTicketSlot3(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        doNothing().when(mockPendingItems).setPpts(any(List.class));

        mockOysterCardDetailsService.getOysterCardPrepayTicketDetails(mockOysterCardPrepayTicketDTO, mockCardInfoResponseV2);

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber1();
        verify(mockOysterCardDetailsService).getPrePayTicketSlot1(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber2();
        verify(mockOysterCardDetailsService).getPrePayTicketSlot2(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber3();
        verify(mockOysterCardDetailsService).getPrePayTicketSlot3(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetOysterCardPrepayTicketDetailsWhenSlotNums123Null() {
        doCallRealMethod().when(mockOysterCardDetailsService).getOysterCardPrepayTicketDetails(mockOysterCardPrepayTicketDTO, mockCardInfoResponseV2);
        when(mockOysterCardPrepayTicketDTO.getSlotNumber1()).thenReturn(null);
        when(mockOysterCardDetailsService.getPrePayTicketSlot1(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        when(mockOysterCardPrepayTicketDTO.getSlotNumber2()).thenReturn(null);
        when(mockOysterCardDetailsService.getPrePayTicketSlot2(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        when(mockOysterCardPrepayTicketDTO.getSlotNumber3()).thenReturn(null);
        when(mockOysterCardDetailsService.getPrePayTicketSlot3(mockPrePayTicketSlot, mockOysterCardPrepayTicketDTO)).thenReturn(
                        getPrePayTicketSlotTest());

        doNothing().when(mockPendingItems).setPpts(any(List.class));

        mockOysterCardDetailsService.getOysterCardPrepayTicketDetails(mockOysterCardPrepayTicketDTO, mockCardInfoResponseV2);

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber1();
        verify(mockOysterCardDetailsService, never()).getPrePayTicketSlot1(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber2();
        verify(mockOysterCardDetailsService, never()).getPrePayTicketSlot2(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));

        verify(mockOysterCardPrepayTicketDTO).getSlotNumber3();
        verify(mockOysterCardDetailsService, never()).getPrePayTicketSlot3(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class));
    }

    @Test
    public void testFreePrePayTicketSlots() {
        when(mockCmd.getPrestigeId()).thenReturn(CommonCardTestUtil.OYSTER_NUMBER_1);
        doCallRealMethod().when(mockOysterCardDetailsService).freePrePayTicketSlots(mockCmd);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(mockOysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPptPendingDTO1());
        mockOysterCardDetailsService.freePrePayTicketSlots(mockCmd);
        verify(mockOysterCardPrepayTicketDataService, atLeastOnce()).delete(any(OysterCardPrepayTicketDTO.class));
        verify(mockOysterCardPptPendingDataService, atLeastOnce()).delete(any(OysterCardPptPendingDTO.class));
    }

    @Test
    public void testNullFreePrePayTicketSlots() {
        when(mockCmd.getPrestigeId()).thenReturn(CommonCardTestUtil.OYSTER_NUMBER_1);
        doCallRealMethod().when(mockOysterCardDetailsService).freePrePayTicketSlots(mockCmd);
        when(mockOysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockOysterCardPptPendingDataService.findByCardNumber(anyString())).thenReturn(null);
        mockOysterCardDetailsService.freePrePayTicketSlots(mockCmd);
        verify(mockOysterCardPrepayTicketDataService, never()).delete(any(OysterCardPrepayTicketDTO.class));
        verify(mockOysterCardPptPendingDataService, never()).delete(any(OysterCardPptPendingDTO.class));
    }

    @Test
    public void createOysterCardDTO() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOysterCardDTO();
        assertNotNull(mockOysterCardDetailsService.createOysterCardDTO());
    }

    @Test
    public void createOysterCardPrepayValueDTO() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOysterCardPrepayValueDTO();
        assertNotNull(mockOysterCardDetailsService.createOysterCardPrepayValueDTO());
    }

    @Test
    public void populateOysterCardPpvPending() {
        doCallRealMethod().when(mockOysterCardDetailsService).populateOysterCardPpvPending(any(OysterCardPpvPendingDTO.class),
                        any(AddCardResponseCmd.class));
        doCallRealMethod().when(mockOysterCardDetailsService).getLongNumber(anyInt());
        oysterCardPpvPendingDTO = new OysterCardPpvPendingDTO();
        when(mockCmd.getPrestigeId()).thenReturn(CardTestUtil.OYSTER_NUMBER_1);
        when(mockCmd.getCurrency()).thenReturn(0);
        when(mockCmd.getPickupLocation()).thenReturn(CardInfoResponseV2TestUtil.PICKUP_LOCATION);
        when(mockCmd.getPrePayValue()).thenReturn(CardInfoResponseV2TestUtil.PREPAY_VALUE);
        when(mockCmd.getRealTimeFlag()).thenReturn("");
        when(mockCmd.getRequestSequenceNumber()).thenReturn(CardInfoResponseV2TestUtil.REQUEST_SEQUENCE_NUMBER);

        oysterCardPpvPendingDTO = mockOysterCardDetailsService.populateOysterCardPpvPending(oysterCardPpvPendingDTO, mockCmd);
        assertNotNull(oysterCardPpvPendingDTO);
        assertEquals(mockCmd.getPrestigeId(), oysterCardPpvPendingDTO.getPrestigeId());
    }

    @Test
    public void testGetOysterCardDetails() {
        when(mockOysterCardDetailsService.getOysterCardDetails(any(OysterCardDTO.class), any(CardInfoResponseV2.class))).thenCallRealMethod();
        when(mockOysterCardDetailsService.getIntegerNumber(anyLong())).thenCallRealMethod();
        OysterCardDTO dto = new OysterCardDTO();
        dto.setRegistered(TEST_LONG_1);
        dto.setAutoloadState(TEST_LONG_1);
        dto.setCardCapability(TEST_LONG_1);
        dto.setCardType(TEST_LONG_1);
        CardInfoResponseV2 response = mockOysterCardDetailsService.getOysterCardDetails(dto, new CardInfoResponseV2());
        assertNotNull(response);
        assertNotNull(response.getRegistered());
    }

    @Test
    public void testPopulateOysterCard() {
        when(mockOysterCardDetailsService.populateOysterCard(any(OysterCardDTO.class), any(AddCardResponseCmd.class))).thenCallRealMethod();
        when(mockOysterCardDetailsService.getLongNumber(anyInt())).thenCallRealMethod();
        OysterCardDTO dto = mockOysterCardDetailsService.populateOysterCard(new OysterCardDTO(), new AddCardResponseCmd());
        assertNotNull(dto);
    }

    @Test
    public void testCreateOysterCardPrepayTicketDTO() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOysterCardPrepayTicketDTO();
        assertNotNull(mockOysterCardDetailsService.createOysterCardPrepayTicketDTO());
    }

    @Test
    public void testCreateOysterCardPptPendingDTO() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOysterCardPptPendingDTO();
        assertNotNull(mockOysterCardDetailsService.createOysterCardPptPendingDTO());
    }

    @Test
    public void testCreateOysterCardPpvPendingDTO() {
        doCallRealMethod().when(mockOysterCardDetailsService).createOysterCardPpvPendingDTO();
        assertNotNull(mockOysterCardDetailsService.createOysterCardPpvPendingDTO());
    }

    @Test
    public void testGetCardDetailsFromCardInfoRequestV2() {
        when(mockOysterCardDetailsService.getCardDetails(any(CardInfoRequestV2.class))).thenCallRealMethod();
        when(mockOysterCardDetailsService.getCardDetails(anyString())).thenReturn("Test");
        assertEquals("Test", mockOysterCardDetailsService.getCardDetails(new CardInfoRequestV2()));
    }

    @Test
    public void testInstantiation() {
        assertNotNull(new OysterCardDetailsServiceImpl());
    }

    @Test
    public void testPopulateOysterCardPrepayValue() {
        when(mockOysterCardDetailsService.populateOysterCardPrepayValue(any(OysterCardPrepayValueDTO.class), any(AddCardResponseCmd.class)))
                        .thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.populateOysterCardPrepayValue(new OysterCardPrepayValueDTO(), new AddCardResponseCmd()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetOysterCardPendingDetailsNullPendingValues() {
        when(mockOysterCardDetailsService.getOysterCardPendingDetails(any(List.class), any(CardInfoResponseV2.class))).thenCallRealMethod();
        doCallRealMethod().when(mockOysterCardDetailsService).setPendingItemsPpv(any(PendingItems.class), any(List.class));
        doCallRealMethod().when(mockOysterCardDetailsService).setPendingItemsPpt(any(PendingItems.class), any(List.class));
        CardInfoResponseV2 response = mockOysterCardDetailsService.getOysterCardPendingDetails(new ArrayList<OysterCardPendingDTO>(),
                        new CardInfoResponseV2());
        assertNotNull(response);
        assertNotNull(response.getPendingItems());
        assertNotNull(response.getPendingItems().getPpvs());
        assertNotNull(response.getPendingItems().getPpts());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetOysterCardPendingDetailsWithPendingValues() {
        when(mockOysterCardDetailsService.getOysterCardPendingDetails(any(List.class), any(CardInfoResponseV2.class))).thenCallRealMethod();
        doCallRealMethod().when(mockOysterCardDetailsService).setPendingItemsPpv(any(PendingItems.class), any(List.class));
        doCallRealMethod().when(mockOysterCardDetailsService).setPendingItemsPpt(any(PendingItems.class), any(List.class));
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(PendingItemsTestUtil.getTestOysterCardPendingDTOWithPrePayTicket());
        pendingItems.add(PendingItemsTestUtil.getTestOysterCardPendingDTOWithPrePayValue());
        CardInfoResponseV2 response = mockOysterCardDetailsService.getOysterCardPendingDetails(pendingItems, new CardInfoResponseV2());
        assertNotNull(response);
        assertNotNull(response.getPendingItems());
        assertNotNull(response.getPendingItems().getPpvs());
        assertNotNull(response.getPendingItems().getPpts());
    }

    @Test
    public void testGetPendingItemsPpv() {
        when(mockOysterCardDetailsService.getPendingItemsPpv(any(PendingItems.class), any(OysterCardPpvPendingDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPendingItemsPpv(getPendingItemsTest(), getTestOysterCardPpvPendingDTO1()));
    }
    
    @Test 
    public void testGetOysterCardPrepayValueDetails() {
        when(mockOysterCardDetailsService.getOysterCardPrepayValueDetails(any(OysterCardPrepayValueDTO.class), any(CardInfoResponseV2.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getOysterCardPrepayValueDetails(new OysterCardPrepayValueDTO(), new CardInfoResponseV2()));
    }
    
    @Test
    public void testGetOysterCardHotListReasonsDetails() {
        when(mockOysterCardDetailsService.getOysterCardHotListReasonsDetails(any(OysterCardHotListReasonsDTO.class), any(CardInfoResponseV2.class))).thenCallRealMethod();
        CardInfoResponseV2 cardInfo = mockOysterCardDetailsService.getOysterCardHotListReasonsDetails(new OysterCardHotListReasonsDTO(), new CardInfoResponseV2());
        assertNotNull(cardInfo);
        assertNotNull(cardInfo.getHotListReasons());
    }
    
    @Test
    public void testGetPptPendingSlot1 (){
        when(mockOysterCardDetailsService.getPptPendingSlot1(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPptPendingSlot1(new PrePayTicket(), new OysterCardPptPendingDTO()));
    }
    
    @Test
    public void testGetPptPendingSlot2 (){
        when(mockOysterCardDetailsService.getPptPendingSlot2(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPptPendingSlot2(new PrePayTicket(), new OysterCardPptPendingDTO()));
    }
    
    @Test
    public void testGetPptPendingSlot3 (){
        when(mockOysterCardDetailsService.getPptPendingSlot3(any(PrePayTicket.class), any(OysterCardPptPendingDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPptPendingSlot3(new PrePayTicket(), new OysterCardPptPendingDTO()));
    }
    
    @Test
    public void testGetPrePayTicketSlot1() {
        when(mockOysterCardDetailsService.getPrePayTicketSlot1(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPrePayTicketSlot1(new PrePayTicketSlot(), new OysterCardPrepayTicketDTO()));
    }
    
    @Test
    public void testGetPrePayTicketSlot2() {
        when(mockOysterCardDetailsService.getPrePayTicketSlot2(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPrePayTicketSlot2(new PrePayTicketSlot(), new OysterCardPrepayTicketDTO()));
    }
    
    @Test
    public void testGetPrePayTicketSlot3() {
        when(mockOysterCardDetailsService.getPrePayTicketSlot3(any(PrePayTicketSlot.class), any(OysterCardPrepayTicketDTO.class))).thenCallRealMethod();
        assertNotNull(mockOysterCardDetailsService.getPrePayTicketSlot3(new PrePayTicketSlot(), new OysterCardPrepayTicketDTO()));
    }

    private String getCardInfoResponseV2Example() {
        final StringBuffer stringBuffer = new StringBuffer(220);
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<CardInfoResponseV2>" + "<CardCapability>1</CardCapability>"
                        + "<CardDeposit>10</CardDeposit>" + "<CardType>15</CardType>"
                        + "<CCCLostStolenDateTime>2014-04-01T00:00:00.000+01:00</CCCLostStolenDateTime>" + "<PrestigeID>100000098765</PrestigeID>"
                        + "<PhotocardNumber>764</PhotocardNumber>" + "<Registered>1</Registered>" + "<PassengerType>student</PassengerType>"
                        + "<AutoloadState>1</AutoloadState>" + "<DiscountExpiry1>01/04/2014</DiscountExpiry1>"
                        + "<DiscountExpiry2></DiscountExpiry2>" + "<DiscountExpiry3></DiscountExpiry3>" + "<HotlistReasons>"
                        + "<HotlistReasonCode>543</HotlistReasonCode>" + "</HotlistReasons>" + "<CardHolderDetails>" + "<Title>Lord</Title>"
                        + "<FirstName>Edmund</FirstName>" + "<MiddleInitial>P</MiddleInitial>" + "<LastName>Blackadder</LastName>"
                        + "<DayTimePhoneNumber>8907764545</DayTimePhoneNumber>" + "<HouseName>Adder House</HouseName>"
                        + "<Street>Canning St</Street>" + "<Town>London</Town>" + "<County>Middx</County>" + "<Postcode>BA13 7TG</Postcode>"
                        + "<EmailAddress>adder@black.co.ck</EmailAddress>" + "<Password>add123</Password>" + "</CardHolderDetails>" + "<PPVDetails>"
                        + "<Balance>10</Balance>" + "<Currency>1</Currency>" + "</PPVDetails>" + "<PPTDetails/>" + "<PendingItems>" + "<PPV>"
                        + "<RequestSequenceNumber>12</RequestSequenceNumber>" + "<RealTimeFlag>Y</RealTimeFlag>" + "<PrepayValue>65</PrepayValue>"
                        + "<Currency>1</Currency>" + "<PickupLocation>507</PickupLocation>" + "</PPV>" + "</PendingItems>" + "</CardInfoResponseV2>");
        return stringBuffer.toString();
    }

}
