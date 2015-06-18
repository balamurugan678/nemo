package com.novacroft.nemo.mock_cubic.application_service;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayTicketTestUtil.getTestOysterCardPrepayTicketDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayTicketTestUtil.getTestOysterCardPrepayTicketDTO2;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayTicketTestUtil.getTestOysterCardPrepayTicketDTO3;
import static com.novacroft.nemo.mock_cubic.test_support.OysterCardPrepayValueTestUtil.getTestOysterCardPrepayValueDTO1;
import static com.novacroft.nemo.mock_cubic.test_support.PendingItemsTestUtil.getTestOysterCardPendingDTOWithPrePayTicket;
import static com.novacroft.nemo.mock_cubic.test_support.PendingItemsTestUtil.getTestOysterCardPendingDTOWithPrePayValue;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.CURRENCY;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.EXPIRY_DATE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PICKUP_LOCATION;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRESTIGE_ID;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRE_PAY_VALUE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRODUCT_CODE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.PRODUCT_PRICE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.REAL_TIME_FLAG;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.REQUEST_SEQUENCE_NUMBER;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.START_DATE;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml2;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getRequestFailureXml3;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdatePrePayTicketResponse;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdateRemoveResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.mock_cubic.constant.PptSlot;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPptPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPpvPendingDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayTicketDataService;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayValueDataService;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.PrePaidTicketTestUtil;
import com.novacroft.nemo.test_support.SettlementTestUtil;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

public class UpdateCardServiceImplTest {

    private final static Integer TEST_ERROR_CODE1 = 40;
    private final static String TEST_ERROR_DESCRIPTION1 = "CARD NOT FOUND";
    private final static Integer TEST_ERROR_CODE2 = 60;
    private final static String TEST_ERROR_DESCRIPTION2 = "NO AVAILABLE PPT SLOT";
    private final static Integer TEST_ERROR_CODE3 = 140;
    private final static String TEST_ERROR_DESCRIPTION3 = "REMOVAL REQUEST SEQUENCE NUMBER NOT FOUND";

    private final static String MESSAGE_NO_PENDING_ITEMS = "No Pending Items";
    private final static String MESSAGE_PRE_PAY_VALUE = "Pre Pay Value added";
    private final static String MESSAGE_PRE_PAY_TICKET = "Pre Pay Ticket Added";
    private final static String MESSAGE_PRODUCT_NOT_FOUND = "Pre Pay Ticket Product Not Added as not found";
    private final static String SLOT_1 = "Slot 1";
    private final static String SLOT_2 = "Slot 2";
    private final static String SLOT_3 = "Slot 3";

    private UpdateCardServiceImpl service;
    private UpdateCardServiceImpl mockService;
    private OysterCardPptPendingDataService pendingPptDataService;
    private OysterCardPpvPendingDataService pendingPpvDataService;
    private OysterCardDataService oysterCardDataService;
    private UpdateResponseService updateResponseService;
    private CubicCardResponseDAO mockCubicCardResponseDAO;
    private OysterCardPendingDataService pendingDataService;
    private OysterCardPrepayTicketDataService oysterCardPrepayTicketDataService;
    private OysterCardPrepayValueDataService oysterCardPrepayValueDataService;
    private PrePaidTicketDataService prePaidTicketDataService;

    private AddRequestCmd addCmd;
    private RemoveRequestCmd removeCmd;

    @Before
    public void setUp() throws Exception {
        service = new UpdateCardServiceImpl();
        mockService = mock(UpdateCardServiceImpl.class);

        pendingPptDataService = mock(OysterCardPptPendingDataService.class);
        service.pendingPptDataService = pendingPptDataService;
        this.mockService.pendingPptDataService = this.pendingPptDataService;

        pendingPpvDataService = mock(OysterCardPpvPendingDataService.class);
        service.pendingPpvDataService = pendingPpvDataService;
        this.mockService.pendingPptDataService = this.pendingPptDataService;

        oysterCardDataService = mock(OysterCardDataService.class);
        service.oysterCardDataService = oysterCardDataService;
        this.mockService.oysterCardDataService = this.oysterCardDataService;

        updateResponseService = mock(UpdateResponseService.class);
        service.updateResponseService = updateResponseService;
        this.mockService.updateResponseService = this.updateResponseService;

        this.mockCubicCardResponseDAO = mock(CubicCardResponseDAO.class);
        this.mockService.cubicCardResponseDAO = this.mockCubicCardResponseDAO;

        pendingDataService = mock(OysterCardPendingDataService.class);
        this.mockService.pendingDataService = this.pendingDataService;
        service.pendingDataService = pendingDataService;

        oysterCardPrepayTicketDataService = mock(OysterCardPrepayTicketDataService.class);
        service.oysterCardPrepayTicketDataService = oysterCardPrepayTicketDataService;
        this.mockService.oysterCardPrepayTicketDataService = oysterCardPrepayTicketDataService;

        oysterCardPrepayValueDataService = mock(OysterCardPrepayValueDataService.class);
        service.oysterCardPrepayValueDataService = oysterCardPrepayValueDataService;
        this.mockService.oysterCardPrepayValueDataService = oysterCardPrepayValueDataService;

        prePaidTicketDataService = mock(PrePaidTicketDataService.class);
        service.prePaidTicketDataService = prePaidTicketDataService;
        mockService.prePaidTicketDataService = prePaidTicketDataService;

        addCmd = mock(AddRequestCmd.class);
        removeCmd = mock(RemoveRequestCmd.class);
    }

    @Test
    public void shouldGenerateUniqueRequestSequenceNumber() {
        when(this.mockCubicCardResponseDAO.getNextRequestSequenceNumber()).thenReturn(Long.valueOf(SettlementTestUtil.REQUEST_SEQUENCE_NUMBER));
        when(this.mockService.generateUniqueRequestSequenceNumber()).thenCallRealMethod();
        assertEquals(Long.valueOf(SettlementTestUtil.REQUEST_SEQUENCE_NUMBER), this.mockService.generateUniqueRequestSequenceNumber());
        verify(this.mockService).generateUniqueRequestSequenceNumber();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldDetermineIfUpdatePptOrPpv() {
        when(mockService.update(any(AddRequestCmd.class))).thenCallRealMethod();

        AddRequestCmd pptRequest = mock(AddRequestCmd.class);
        when(pptRequest.getPrePayValue()).thenReturn(null);
        mockService.update(pptRequest);
        verify(mockService).updatePPT(pptRequest);

        AddRequestCmd ppvRequest = mock(AddRequestCmd.class);
        when(ppvRequest.getPrePayValue()).thenReturn(200L);
        mockService.update(ppvRequest);
        verify(mockService).updatePPV(ppvRequest);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldUpdatePPV() {
        OysterCardPpvPendingDataService dataService = mock(OysterCardPpvPendingDataService.class);
        mockService.pendingPpvDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        OysterCardPpvPendingDTO dto = mock(OysterCardPpvPendingDTO.class);
        when(dataService.findByCardNumber(anyString())).thenReturn(dto);
        when(mockService.populatePPV(any(OysterCardPpvPendingDTO.class), any(AddRequestCmd.class))).thenReturn(dto);
        when(dataService.createOrUpdate(dto)).thenReturn(dto);
        when(responseService.generateAddSuccessResponse(any(AddRequestCmd.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        when(mockService.updatePPV(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPV(addCmd));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldAddNewPPV() {
        OysterCardPpvPendingDataService dataService = mock(OysterCardPpvPendingDataService.class);
        mockService.pendingPpvDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;
        OysterCardDataService oysterCardDataService = mock(OysterCardDataService.class);
        mockService.oysterCardDataService = oysterCardDataService;

        OysterCardPpvPendingDTO dto = mock(OysterCardPpvPendingDTO.class);
        OysterCardDTO OysterDTO = mock(OysterCardDTO.class);

        when(dataService.findByCardNumber(anyString())).thenReturn(null);
        when(mockService.createOysterCardPpvPendingDTO()).thenReturn(dto);
        when(mockService.populatePPV(any(OysterCardPpvPendingDTO.class), any(AddRequestCmd.class))).thenReturn(dto);
        when(oysterCardDataService.findByCardNumber(anyString())).thenReturn(OysterDTO);
        when(dataService.createOrUpdate(dto)).thenReturn(dto);
        when(responseService.generateAddSuccessResponse(any(AddRequestCmd.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        when(mockService.updatePPV(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPV(addCmd));
        verify(mockService).createOysterCardPpvPendingDTO();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldRespondWithErrorIfTheCardIsNotFoundUpdatePPV() {
        OysterCardPpvPendingDataService dataService = mock(OysterCardPpvPendingDataService.class);
        mockService.pendingPpvDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;
        OysterCardDataService oysterCardDataService = mock(OysterCardDataService.class);
        mockService.oysterCardDataService = oysterCardDataService;

        when(dataService.findByCardNumber(anyString())).thenReturn(null);
        when(oysterCardDataService.findByCardNumber(anyString())).thenReturn(null);
        when(responseService.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1)).thenReturn(getRequestFailureXml1());
        when(mockService.updatePPV(addCmd)).thenCallRealMethod();

        assertEquals(getRequestFailureXml1(), mockService.updatePPV(addCmd));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldUpdateSlotPPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;
        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);

        when(dataService.findByCardNumber(anyString())).thenReturn(dto);
        when(mockService.findSlot(any(OysterCardPptPendingDTO.class), anyLong())).thenReturn(PptSlot.ONE, PptSlot.TWO, PptSlot.THREE);
        when(
                        mockService.updateSlot(any(com.novacroft.nemo.mock_cubic.constant.PptSlot.class), any(OysterCardPptPendingDTO.class),
                                        any(AddRequestCmd.class))).thenReturn(dto);
        when(dataService.createOrUpdate(dto)).thenReturn(dto);
        when(responseService.generateAddSuccessResponse(any(AddRequestCmd.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        when(mockService.updatePPT(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldAddToEmptySlotPPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;
        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);

        when(dataService.findByCardNumber(anyString())).thenReturn(dto);
        when(mockService.findSlot(any(OysterCardPptPendingDTO.class), anyLong())).thenReturn(PptSlot.NOT_FOUND);
        when(mockService.findEmptySlot(any(OysterCardPptPendingDTO.class))).thenReturn(PptSlot.ONE, PptSlot.TWO, PptSlot.THREE);
        when(mockService.updateSlot(any(PptSlot.class), any(OysterCardPptPendingDTO.class), any(AddRequestCmd.class))).thenReturn(dto);
        when(dataService.createOrUpdate(dto)).thenReturn(dto);
        when(responseService.generateAddSuccessResponse(any(AddRequestCmd.class))).thenReturn(getTestCardUpdatePrePayTicketResponse());
        when(mockService.updatePPT(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
        assertEquals(getTestCardUpdatePrePayTicketResponse(), mockService.updatePPT(addCmd));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldRespondWithErrorWhenNoEmptySlotsPPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;
        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);

        when(dataService.findByCardNumber(anyString())).thenReturn(dto);
        when(mockService.findSlot(any(OysterCardPptPendingDTO.class), anyLong())).thenReturn(PptSlot.NOT_FOUND);
        when(mockService.findEmptySlot(any(OysterCardPptPendingDTO.class))).thenReturn(PptSlot.NOT_FOUND);
        when(responseService.generateErrorResponse(TEST_ERROR_CODE2, TEST_ERROR_DESCRIPTION2)).thenReturn(getRequestFailureXml2());
        when(mockService.updatePPT(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getRequestFailureXml2(), mockService.updatePPT(addCmd));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldRespondWithErrorIfTheCardIsNotFoundUpdatePPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        when(dataService.findByCardNumber(anyString())).thenReturn(null);
        when(responseService.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1)).thenReturn(getRequestFailureXml1());
        when(mockService.updatePPT(any(AddRequestCmd.class))).thenCallRealMethod();

        assertEquals(getRequestFailureXml1(), mockService.updatePPT(addCmd));
    }

    @Test
    public void shouldRespondWithErrorIfCardNotFoundRemove() {
        OysterCardPptPendingDataService pptDataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = pptDataService;
        OysterCardPpvPendingDataService ppvDataService = mock(OysterCardPpvPendingDataService.class);
        mockService.pendingPpvDataService = ppvDataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        when(ppvDataService.findByCardNumberAndRequestSequenceNumber(anyString(), anyLong())).thenReturn(null);
        when(pptDataService.findByCardNumber(anyString())).thenReturn(null);
        when(responseService.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1)).thenReturn(getRequestFailureXml1());

        when(mockService.remove(any(RemoveRequestCmd.class))).thenCallRealMethod();
        assertEquals(getRequestFailureXml1(), mockService.remove(removeCmd));
    }

    @Test
    public void shouldHandleRemoveRequest() {
        CardRemoveUpdateRequest removeRequest = new CardRemoveUpdateRequest();
        when(mockService.remove(any(CardRemoveUpdateRequest.class))).thenCallRealMethod();
        mockService.remove(removeRequest);
        verify(mockService).remove(any(RemoveRequestCmd.class));
    }

    @Test
    public void shouldHandleRemovePendingRequest() {
        OysterCardPendingDTO pendingDTO = new OysterCardPendingDTO();
        RemoveRequestCmd cmd = new RemoveRequestCmd();
        when(mockService.removePending(any(OysterCardPendingDTO.class), any(RemoveRequestCmd.class))).thenCallRealMethod();
        mockService.removePending(pendingDTO, cmd);
        verify(mockService).removePending(any(OysterCardPendingDTO.class), any(RemoveRequestCmd.class));
    }

    @Test
    public void shouldRemovePPV() {
        OysterCardPpvPendingDataService ppvDataService = mock(OysterCardPpvPendingDataService.class);
        mockService.pendingPpvDataService = ppvDataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        doNothing().when(ppvDataService).delete(any(OysterCardPpvPendingDTO.class));
        when(mockService.removePPV(any(RemoveRequestCmd.class))).thenCallRealMethod();
        when(responseService.generateRemoveSuccessResponse(any(RemoveRequestCmd.class))).thenReturn(getTestCardUpdateRemoveResponse());
        assertEquals(getTestCardUpdateRemoveResponse(), mockService.removePPV(any(RemoveRequestCmd.class)));
    }

    @Test
    public void shouldRemovePPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        OysterCardPptPendingDTO testDTO1 = new OysterCardPptPendingDTO();
        testDTO1.setRequestSequenceNumber1(2L);
        OysterCardPptPendingDTO testDTO2 = new OysterCardPptPendingDTO();
        testDTO2.setRequestSequenceNumber1(5L);
        mockService.pendingPptDTO = testDTO1;

        RemoveRequestCmd testRemoveCmd = new RemoveRequestCmd();

        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);

        when(mockService.findSlot(any(OysterCardPptPendingDTO.class), anyLong())).thenReturn(PptSlot.ONE, PptSlot.TWO, PptSlot.THREE);
        when(mockService.clearSlot(any(PptSlot.class), any(OysterCardPptPendingDTO.class))).thenReturn(testDTO2, testDTO1, testDTO2);

        when(dataService.createOrUpdate(any(OysterCardPptPendingDTO.class))).thenReturn(dto);
        when(responseService.generateRemoveSuccessResponse(any(RemoveRequestCmd.class))).thenReturn(getTestCardUpdateRemoveResponse());

        when(mockService.removePPT(any(RemoveRequestCmd.class))).thenCallRealMethod();
        assertEquals(getTestCardUpdateRemoveResponse(), mockService.removePPT(testRemoveCmd));
        assertEquals(getTestCardUpdateRemoveResponse(), mockService.removePPT(testRemoveCmd));
        assertEquals(getTestCardUpdateRemoveResponse(), mockService.removePPT(testRemoveCmd));
    }

    @Test
    public void shouldRespondWithErrorIfRequestSequenceNumberNotFoundRemovePPT() {
        OysterCardPptPendingDataService dataService = mock(OysterCardPptPendingDataService.class);
        mockService.pendingPptDataService = dataService;
        UpdateResponseService responseService = mock(UpdateResponseService.class);
        mockService.updateResponseService = responseService;

        OysterCardPptPendingDTO testDTO1 = new OysterCardPptPendingDTO();
        testDTO1.setRequestSequenceNumber1(2L);
        mockService.pendingPptDTO = testDTO1;

        RemoveRequestCmd testRemoveCmd = new RemoveRequestCmd();

        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);

        when(mockService.findSlot(any(OysterCardPptPendingDTO.class), anyLong())).thenReturn(PptSlot.ONE, PptSlot.TWO, PptSlot.THREE);
        when(mockService.clearSlot(any(PptSlot.class), any(OysterCardPptPendingDTO.class))).thenReturn(testDTO1);

        when(dataService.createOrUpdate(any(OysterCardPptPendingDTO.class))).thenReturn(dto);
        when(responseService.generateErrorResponse(TEST_ERROR_CODE3, TEST_ERROR_DESCRIPTION3)).thenReturn(getRequestFailureXml3());

        when(mockService.removePPT(any(RemoveRequestCmd.class))).thenCallRealMethod();
        assertEquals(getRequestFailureXml3(), mockService.removePPT(testRemoveCmd));
    }

    @Test
    public void shouldFindRelevantSlot() {
        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);
        when(dto.getRequestSequenceNumber1()).thenReturn(1L);
        when(dto.getRequestSequenceNumber2()).thenReturn(2L);
        when(dto.getRequestSequenceNumber3()).thenReturn(3L);

        assertEquals(service.findSlot(dto, 1L), PptSlot.ONE);
        assertEquals(service.findSlot(dto, 2L), PptSlot.TWO);
        assertEquals(service.findSlot(dto, 3L), PptSlot.THREE);
        assertEquals(service.findSlot(dto, 4L), PptSlot.NOT_FOUND);
    }

    @Test
    public void shouldFindEmptySlot() {
        OysterCardPptPendingDTO dto = mock(OysterCardPptPendingDTO.class);
        when(dto.getRequestSequenceNumber1()).thenReturn(null, 1L, 1L, 1L);
        when(dto.getRequestSequenceNumber2()).thenReturn(null, 2L, 2L);
        when(dto.getRequestSequenceNumber3()).thenReturn(null, 3L);

        assertEquals(service.findEmptySlot(dto), PptSlot.ONE);
        assertEquals(service.findEmptySlot(dto), PptSlot.TWO);
        assertEquals(service.findEmptySlot(dto), PptSlot.THREE);
        assertEquals(service.findEmptySlot(dto), PptSlot.NOT_FOUND);
    }

    @Test
    public void shouldPopulatePpvDTO() {
        OysterCardPpvPendingDTO dto = new OysterCardPpvPendingDTO();
        AddRequestCmd testCmd = new AddRequestCmd(PRESTIGE_ID, REQUEST_SEQUENCE_NUMBER, REAL_TIME_FLAG, PRE_PAY_VALUE, CURRENCY, PICKUP_LOCATION);
        OysterCardPpvPendingDTO testDTO = getTestOysterCardPpvPendingDTO(testCmd);

        assertEquals(service.populatePPV(dto, testCmd), testDTO);
    }

    @Test
    public void shouldUpdateSlot() {
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);

        OysterCardPptPendingDTO testDTO1 = getTestOysterCardPptPendingDTO1(testCmd);
        OysterCardPptPendingDTO testDTO2 = getTestOysterCardPptPendingDTO2(testCmd);
        OysterCardPptPendingDTO testDTO3 = getTestOysterCardPptPendingDTO3(testCmd);

        OysterCardPptPendingDTO dto1 = new OysterCardPptPendingDTO();
        OysterCardPptPendingDTO dto2 = new OysterCardPptPendingDTO();
        OysterCardPptPendingDTO dto3 = new OysterCardPptPendingDTO();
        OysterCardPptPendingDTO dto4 = new OysterCardPptPendingDTO();

        assertEquals(service.updateSlot(PptSlot.ONE, dto1, testCmd), testDTO1);
        assertEquals(service.updateSlot(PptSlot.TWO, dto2, testCmd), testDTO2);
        assertEquals(service.updateSlot(PptSlot.THREE, dto3, testCmd), testDTO3);
        assertEquals(service.updateSlot(PptSlot.NOT_FOUND, dto4, testCmd), dto4);
    }

    @Test
    public void shouldClearSlot() {
        OysterCardPptPendingDTO testDTO1 = new OysterCardPptPendingDTO();
        OysterCardPptPendingDTO testDTO2 = new OysterCardPptPendingDTO();
        OysterCardPptPendingDTO testDTO3 = new OysterCardPptPendingDTO();
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);

        OysterCardPptPendingDTO dto1 = getTestOysterCardPptPendingDTO1(testCmd);
        OysterCardPptPendingDTO dto2 = getTestOysterCardPptPendingDTO2(testCmd);
        OysterCardPptPendingDTO dto3 = getTestOysterCardPptPendingDTO3(testCmd);
        OysterCardPptPendingDTO dto4 = getTestOysterCardPptPendingDTO1(testCmd);

        assertEquals(service.clearSlot(PptSlot.ONE, dto1), testDTO1);
        assertEquals(service.clearSlot(PptSlot.TWO, dto2), testDTO2);
        assertEquals(service.clearSlot(PptSlot.THREE, dto3), testDTO3);
        assertEquals(service.clearSlot(PptSlot.NOT_FOUND, dto4), dto4);
    }

    @Test
    public void testUpdatePendingWithPPV() {
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);
        testCmd.setPrestigeId(PRESTIGE_ID);
        testCmd.setPrePayValue(100L);
        List<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayValue());

        when(mockService.updatePending(any(AddRequestCmd.class))).thenCallRealMethod();
        when(pendingDataService.findByCardNumber(PRESTIGE_ID)).thenReturn(pendingItems);
        when(pendingDataService.isLimitExceededForPendingItems(anyLong())).thenReturn(Boolean.FALSE);
        when(updateResponseService.generateAddSuccessResponse(testCmd)).thenReturn("SUCCESS");
        when(pendingDataService.createOrUpdate(any(OysterCardPendingDTO.class))).thenReturn(mock(OysterCardPendingDTO.class));

        String response = mockService.updatePending(testCmd);
        assertNotNull(response);
    }

    @Test
    public void testUpdatePendingWithPPT() {
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);
        testCmd.setPrestigeId(PRESTIGE_ID);
        List<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());

        when(mockService.updatePending(any(AddRequestCmd.class))).thenCallRealMethod();
        when(pendingDataService.findByCardNumber(PRESTIGE_ID)).thenReturn(pendingItems);
        when(pendingDataService.isLimitExceededForPendingItems(anyLong())).thenReturn(Boolean.FALSE);
        when(updateResponseService.generateAddSuccessResponse(testCmd)).thenReturn("SUCCESS");
        when(pendingDataService.createOrUpdate(any(OysterCardPendingDTO.class))).thenReturn(mock(OysterCardPendingDTO.class));

        String response = mockService.updatePending(testCmd);
        assertNotNull(response);
    }

    @Test
    public void testUpdatePendingNullPending() {
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);
        testCmd.setPrestigeId(PRESTIGE_ID);
        testCmd.setPrePayValue(100L);
        List<OysterCardPendingDTO> pendingItems = null;

        when(mockService.updatePending(any(AddRequestCmd.class))).thenCallRealMethod();
        when(pendingDataService.findByCardNumber(PRESTIGE_ID)).thenReturn(pendingItems);
        when(oysterCardDataService.findByCardNumber(PRESTIGE_ID)).thenReturn(null);
        when(updateResponseService.generateErrorResponse(TEST_ERROR_CODE1, TEST_ERROR_DESCRIPTION1)).thenReturn(getRequestFailureXml1());
        assertEquals(getRequestFailureXml1(), mockService.updatePending(testCmd));

    }

    @Test
    public void testUpdatePendingLimitExceededForPendingItems() {
        AddRequestCmd testCmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);
        testCmd.setPrestigeId(PRESTIGE_ID);
        List<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());

        when(mockService.updatePending(any(AddRequestCmd.class))).thenCallRealMethod();
        when(pendingDataService.findByCardNumber(PRESTIGE_ID)).thenReturn(pendingItems);
        when(pendingDataService.isLimitExceededForPendingItems(anyLong())).thenReturn(Boolean.TRUE);

        when(updateResponseService.generateErrorResponse(TEST_ERROR_CODE2, TEST_ERROR_DESCRIPTION2)).thenReturn(getRequestFailureXml2());
        assertEquals(getRequestFailureXml2(), mockService.updatePending(testCmd));
    }

    @Test
    public void populatePendingTestPPV() {

        AddRequestCmd cmdPpv = new AddRequestCmd(PRESTIGE_ID, 1, REAL_TIME_FLAG, 1, 0, PICKUP_LOCATION);
        OysterCardPendingDTO dtoPPV = new OysterCardPendingDTO();
        when(mockService.populatePending(dtoPPV, cmdPpv)).thenCallRealMethod();
        mockService.populatePending(dtoPPV, cmdPpv);

        assertEquals(dtoPPV.getPrePayValue(), cmdPpv.getPrePayValue());
    }

    @Test
    public void populatePendingTestPPT() {

        OysterCardPendingDTO dto = new OysterCardPendingDTO();
        AddRequestCmd cmd = new AddRequestCmd(REAL_TIME_FLAG, PRODUCT_CODE, PRODUCT_PRICE, CURRENCY, START_DATE, EXPIRY_DATE, PICKUP_LOCATION);
        cmd.setPrestigeId(PRESTIGE_ID);
        when(mockService.populatePending(dto, cmd)).thenCallRealMethod();
        mockService.populatePending(dto, cmd);

        assertEquals(dto.getRealtimeFlag(), cmd.getRealTimeFlag());
        assertEquals(dto.getProductCode(), cmd.getProductCode());
        assertEquals(dto.getPickupLocation(), cmd.getPickupLocation());
        assertEquals(dto.getPrestigeId(), cmd.getPrestigeId());
    }

    @Test
    public void populatePrePayTicketFromPendingTicketSlot1() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(pendingItems);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_PRE_PAY_TICKET));
        assertTrue(message.contains(SLOT_1));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketSlot2() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(pendingItems);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO2());
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_PRE_PAY_TICKET));
        assertTrue(message.contains(SLOT_2));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketSlot3() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(pendingItems);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO3());
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_PRE_PAY_TICKET));
        assertTrue(message.contains(SLOT_3));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketNoProductFound() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(pendingItems);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO3());
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(null);
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_PRODUCT_NOT_FOUND));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketNoPendingItems() {
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(new ArrayList<OysterCardPendingDTO>());
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_NO_PENDING_ITEMS));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketNullPendingItems() {
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(null);
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_NO_PENDING_ITEMS));
    }

    @Test
    public void populatePrePayTicketFromPendingTicketPrePayValue() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayValue());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(pendingItems);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayValueDTO1());
        String message = service.populatePrePayTicketFromPendingTicket(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains(MESSAGE_PRE_PAY_VALUE));
    }

    private OysterCardPptPendingDTO getTestOysterCardPptPendingDTO1(AddRequestCmd testCmd) {
        OysterCardPptPendingDTO dto = new OysterCardPptPendingDTO();

        dto.setRealTimeFlag1(testCmd.getRealTimeFlag());
        dto.setProductCode1(testCmd.getProductCode());
        dto.setProductPrice1(testCmd.getProductPrice());
        dto.setCurrency1(testCmd.getCurrency());
        dto.setStartDate1(parse(testCmd.getStartDate()));
        dto.setExpiryDate1(parse(testCmd.getExpiryDate()));
        dto.setPickUpLocation1(testCmd.getPickupLocation());

        return dto;
    }

    private OysterCardPptPendingDTO getTestOysterCardPptPendingDTO2(AddRequestCmd testCmd) {
        OysterCardPptPendingDTO dto = new OysterCardPptPendingDTO();

        dto.setRealTimeFlag2(testCmd.getRealTimeFlag());
        dto.setProductCode2(testCmd.getProductCode());
        dto.setProductPrice2(testCmd.getProductPrice());
        dto.setCurrency2(testCmd.getCurrency());
        dto.setStartDate2(parse(testCmd.getStartDate()));
        dto.setExpiryDate2(parse(testCmd.getExpiryDate()));
        dto.setPickUpLocation2(testCmd.getPickupLocation());

        return dto;
    }

    private OysterCardPptPendingDTO getTestOysterCardPptPendingDTO3(AddRequestCmd testCmd) {
        OysterCardPptPendingDTO dto = new OysterCardPptPendingDTO();

        dto.setRealTimeFlag3(testCmd.getRealTimeFlag());
        dto.setProductCode3(testCmd.getProductCode());
        dto.setProductPrice3(testCmd.getProductPrice());
        dto.setCurrency3(testCmd.getCurrency());
        dto.setStartDate3(parse(testCmd.getStartDate()));
        dto.setExpiryDate3(parse(testCmd.getExpiryDate()));
        dto.setPickUpLocation3(testCmd.getPickupLocation());

        return dto;
    }

    private OysterCardPpvPendingDTO getTestOysterCardPpvPendingDTO(AddRequestCmd testCmd) {
        OysterCardPpvPendingDTO dto = new OysterCardPpvPendingDTO();

        dto.setPrestigeId(testCmd.getPrestigeId());
        dto.setRequestSequenceNumber(testCmd.getRequestSequenceNumber());
        dto.setRealtimeFlag(testCmd.getRealTimeFlag());
        dto.setPrePayValue(testCmd.getPrePayValue());
        dto.setCurrency(testCmd.getCurrency());
        dto.setPickupLocation(testCmd.getPickupLocation());

        return dto;
    }

    @Test
    public void addPPTToCardToSlot1() {
        ArrayList<OysterCardPendingDTO> pendingItems = new ArrayList<OysterCardPendingDTO>();
        pendingItems.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        String message = service.addPPTToCard(CardTestUtil.OYSTER_NUMBER_1, getTestOysterCardPendingDTOWithPrePayTicket(), pendingItems);
        assertNotNull(message);
    }

    @Test
    public void findEmptySlot2() {
        OysterCardPrepayTicketDTO dto = getTestOysterCardPrepayTicketDTO2();
        PptSlot slot = service.findEmptySlot(dto);
        assertNotNull(slot);
        assertEquals(PptSlot.TWO, slot);
    }

    @Test
    public void findEmptySlot3() {
        OysterCardPrepayTicketDTO dto = getTestOysterCardPrepayTicketDTO3();
        PptSlot slot = service.findEmptySlot(dto);
        assertNotNull(slot);
        assertEquals(PptSlot.THREE, slot);
    }

    @Test
    public void findEmptySlotNotFound() {
        OysterCardPrepayTicketDTO dto = getTestOysterCardPrepayTicketDTO3();
        dto.setStartDate3(new Date());
        PptSlot slot = service.findEmptySlot(dto);
        assertNotNull(slot);
        assertEquals(PptSlot.NOT_FOUND, slot);
    }

    @Test
    public void addPPVToCard() {
        when(oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayValueDTO1());
        when(oysterCardPrepayValueDataService.createOrUpdate(any(OysterCardPrepayValueDTO.class))).thenReturn(getTestOysterCardPrepayValueDTO1());
        String message = service.addPPVToCard(CardTestUtil.OYSTER_NUMBER_1, getTestOysterCardPendingDTOWithPrePayValue(),
                        new ArrayList<OysterCardPendingDTO>());
        assertNotNull(message);
        assertTrue(message.toLowerCase().contains(MESSAGE_PRE_PAY_VALUE.toLowerCase()));
    }

    @Test
    public void updateSlot1() {
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        OysterCardPrepayTicketDTO dto = service.updateSlot(PptSlot.ONE, getTestOysterCardPrepayTicketDTO1(),
                        getTestOysterCardPendingDTOWithPrePayTicket());
        assertNotNull(dto);
        assertEquals(1L, dto.getSlotNumber1().longValue());
    }

    @Test
    public void updateSlot2() {
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        OysterCardPrepayTicketDTO dto = service.updateSlot(PptSlot.TWO, getTestOysterCardPrepayTicketDTO1(),
                        getTestOysterCardPendingDTOWithPrePayTicket());
        assertNotNull(dto);
        assertEquals(2L, dto.getSlotNumber2().longValue());
    }

    @Test
    public void updateSlot3() {
        when(prePaidTicketDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(PrePaidTicketTestUtil.getTestPrePaidTicketDTO());
        OysterCardPrepayTicketDTO dto = service.updateSlot(PptSlot.THREE, getTestOysterCardPrepayTicketDTO1(),
                        getTestOysterCardPendingDTOWithPrePayTicket());
        assertNotNull(dto);
        assertEquals(3L, dto.getSlotNumber3().longValue());
    }

    @Test
    public void SlotOneIsNotAvailable() {
        Boolean isAvailable = service.isSlotOneAvailable(new OysterCardPrepayTicketDTO(), getTestOysterCardPrepayTicketDTO2());
        assertFalse(isAvailable);
    }

    @Test
    public void SlotTwoIsNotAvailable() {
        Boolean isAvailable = service.isSlotTwoAvailable(new OysterCardPrepayTicketDTO(), getTestOysterCardPrepayTicketDTO3());
        assertFalse(isAvailable);
    }

    @Test
    public void SlotThreeIsNotAvailable() {
        OysterCardPrepayTicketDTO dto = getTestOysterCardPrepayTicketDTO3();
        dto.setStartDate3(new Date());
        Boolean isAvailable = service.isSlotThreeAvailable(new OysterCardPrepayTicketDTO(), dto);
        assertFalse(isAvailable);

        isAvailable = service.isSlotThreeAvailable(dto, dto);
        assertFalse(isAvailable);
    }

    @Test
    public void createOysterCardPpvPendingDTO() {
        OysterCardPpvPendingDTO dto = service.createOysterCardPpvPendingDTO();
        assertNotNull(dto);
        assertTrue(dto instanceof OysterCardPpvPendingDTO);
    }

    @Test
    public void updatePendingShouldGeneratPpvAmountExeceededError() {
        AddRequestCmd addCmd = new AddRequestCmd();
        addCmd.setPrestigeId(PRESTIGE_ID);
        addCmd.setPrePayValue(12L);
        when(pendingDataService.isBalanceTotalExceededForPendingItems(anyLong(), anyLong())).thenReturn(true);
        when(updateResponseService.generateErrorResponse(anyInt(), anyString())).thenReturn("PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_DESCRIPTION");
        when(pendingDataService.isLimitExceededForPendingItems(anyLong())).thenReturn(false);
        String message = service.updatePending(addCmd);
        assertNotNull(message);
        assertEquals("PPV_AMOUNT_WOULD_EXCEED_MAX_ALLOWED_PPV_DESCRIPTION", message);
    }

    @Test
    public void populatePrePayTicketFromPendingTicket2() {
        List<OysterCardPendingDTO> dtos = new ArrayList<>();
        dtos.add(getTestOysterCardPendingDTOWithPrePayValue());
        dtos.add(getTestOysterCardPendingDTOWithPrePayTicket());
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(dtos);
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayValueDTO1());
        String message = service.populatePrePayTicketFromPendingTicket2(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertFalse(message.contains("No Pending Items"));
    }

    @Test
    public void doNotPopulatePrePayTicketFromPendingTicket2IfThereIsNoPendingItems() {
        when(pendingDataService.findByCardNumber(anyString())).thenReturn(new ArrayList<OysterCardPendingDTO>());
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayTicketDTO1());
        when(oysterCardPrepayValueDataService.findByCardNumber(anyString())).thenReturn(getTestOysterCardPrepayValueDTO1());
        String message = service.populatePrePayTicketFromPendingTicket2(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(message);
        assertTrue(message.contains("No Pending Items"));
    }

    @Test
    public void updatePrePayValue() {
        when(mockService.updatePending(any(AddRequestCmd.class))).thenReturn("SUCCESS");
        when(mockService.updateConvert(any(CardUpdatePrePayValueRequest.class))).thenCallRealMethod();
        when(mockService.update(any(CardUpdatePrePayValueRequest.class))).thenCallRealMethod();
        when(mockCubicCardResponseDAO.getNextRequestSequenceNumber()).thenReturn(12L);
        String message = mockService.update(new CardUpdatePrePayValueRequest());
        assertNotNull(message);
    }

    @Test
    public void updatePrePayTicket() {
        when(mockService.updatePending(any(AddRequestCmd.class))).thenReturn("SUCCESS");
        when(mockService.updateConvert(any(CardUpdatePrePayTicketRequest.class))).thenCallRealMethod();
        when(mockService.update(any(CardUpdatePrePayTicketRequest.class))).thenCallRealMethod();
        when(mockCubicCardResponseDAO.getNextRequestSequenceNumber()).thenReturn(12L);
        String message = mockService.update(new CardUpdatePrePayTicketRequest());
        assertNotNull(message);
    }

    @Test
    public void removePending() {
        when(mockService.remove(any(RemoveRequestCmd.class))).thenCallRealMethod();
        when(pendingDataService.findByExternalId(anyLong())).thenReturn(getTestOysterCardPendingDTOWithPrePayValue());
        when(mockService.removePending(any(OysterCardPendingDTO.class), any(RemoveRequestCmd.class))).thenReturn("SUCCESS");
        String message = mockService.remove(removeCmd);
        assertNotNull(message);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void addPPTToCardShouldReturnNoEmptySlots() {
        when(oysterCardPrepayTicketDataService.findByCardNumber(anyString())).thenReturn(new OysterCardPrepayTicketDTO());
        when(mockService.findEmptySlot(any(OysterCardPrepayTicketDTO.class))).thenReturn(PptSlot.NOT_FOUND);
        when(mockService.addPPTToCard(anyString(), any(OysterCardPendingDTO.class), any(ArrayList.class))).thenCallRealMethod();
        String message = mockService.addPPTToCard("", null, null);
        assertNotNull(message);
        assertTrue(message.contains(" No Empty Slots"));
    }
}
