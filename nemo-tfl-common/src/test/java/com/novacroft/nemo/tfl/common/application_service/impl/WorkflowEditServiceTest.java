package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.StringUtil.SPACE;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.*;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.test_support.LocationTestUtil;
import com.novacroft.nemo.test_support.RefundWorkflowTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowEditServiceTest {
    private static final String ADDED_REFUND_ITEM = "";
    private static final String REMOVED_REFUND_ITEM = "";
    private static final String BLANK = "BLANK";
    private WorkflowEditServiceImpl service;
    private WorkflowEditServiceImpl mockService;
    private RuntimeService mockRuntimeService;
    private CaseHistoryNoteServiceImpl mockCaseHistoryNoteService;
    private ContentDataService mockContentService;
    private WorkflowItemDTO mockWorkflowItem;
    private RefundDetailDTO mockRefundDetail;
    private Refund mockRefundEntity;
    private Task mockTask;
    private List<CaseHistoryNote> mockCaseNotes;
    private WorkflowCmd workflowCmd;
    private WorkflowEditCmd cmd;
    private List<String> newAddress;
    private ContentDTO mockContentDTO;
    private WorkflowItemConverter mockWorkflowConverter;
    private LocationDataService mockLocationDataService;
    private CountryDataService mockCountryDataService;
    
    private CountryDTO testCountryDTO;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        service = new WorkflowEditServiceImpl();
        mockService = mock(WorkflowEditServiceImpl.class);
        mockRuntimeService = mock(RuntimeService.class);
        mockCaseHistoryNoteService = mock(CaseHistoryNoteServiceImpl.class);
        mockContentService = mock(ContentDataService.class);
        mockWorkflowConverter = mock(WorkflowItemConverter.class);
        mockLocationDataService = mock(LocationDataService.class);
        mockCountryDataService = mock(CountryDataService.class);

        mockWorkflowItem = new WorkflowItemDTO();
        mockCaseNotes = new ArrayList<CaseHistoryNote>();
        mockTask = mock(Task.class);

        mockContentDTO = mock(ContentDTO.class);

        mockCaseHistoryNoteService.content = mockContentService;

        service.runtimeService = mockRuntimeService;
        service.caseHistoryNoteService = mockCaseHistoryNoteService;
        service.content = mockContentService;
        service.workflowItemConverter = mockWorkflowConverter;
        service.locationDataService = mockLocationDataService;

        mockService.runtimeService = mockRuntimeService;
        mockService.caseHistoryNoteService = mockCaseHistoryNoteService;
        mockService.content = mockContentService;
        mockService.workflowItemConverter = mockWorkflowConverter;
        mockService.locationDataService = mockLocationDataService;
        mockService.countryDataService = mockCountryDataService;
        
        testCountryDTO = getTestCountryDTO(COUNTRY);

        when(mockCountryDataService.findCountryByCode(anyString())).thenReturn(testCountryDTO);
        when(mockLocationDataService.getActiveLocationById(anyInt())).thenReturn(LocationTestUtil.getTestLocationDTO1(),
                        LocationTestUtil.getTestLocationDTO2());

        when(mockContentService.findByLocaleAndCode(anyString(), anyString())).thenReturn(mockContentDTO);

        doNothing().when(mockRuntimeService).setVariable(anyString(), anyString(), anyObject());
        when(mockCaseHistoryNoteService.getCaseNotesExcludingHistoric(mockWorkflowItem)).thenReturn(mockCaseNotes);

        doCallRealMethod().when(mockCaseHistoryNoteService).addChangesCaseNote(any(List.class), any(WorkflowCmd.class), anyString(), anyString(),
                        anyString(), anyString());
        doCallRealMethod().when(mockCaseHistoryNoteService).addRefundItemAddedCaseNote(any(List.class), any(WorkflowCmd.class), anyString(),
                        anyString());
        doCallRealMethod().when(mockCaseHistoryNoteService).addRefundItemRemovedCaseNote(any(List.class), any(WorkflowCmd.class), anyString(),
                        anyString());
        ReflectionTestUtils.setField(mockCaseHistoryNoteService, "REASON_TEXT", "");
        ReflectionTestUtils.setField(mockCaseHistoryNoteService, "REASON_FOR_CHANGE_TEXT", "");
        ReflectionTestUtils.setField(mockCaseHistoryNoteService, "ADDED_REFUND_ITEM_TEXT", "");
        ReflectionTestUtils.setField(mockCaseHistoryNoteService, "REMOVED_REFUND_ITEM_TEXT", "");

        mockRefundDetail = new RefundDetailDTO();
        mockRefundEntity = new Refund();
        mockRefundDetail.setRefundEntity(mockRefundEntity);
        mockWorkflowItem.setRefundDetails(mockRefundDetail);
        mockWorkflowItem.setAgent("Smith");
        mockWorkflowItem.setPaymentMethod(PaymentType.BACS.code());
        when(mockTask.getExecutionId()).thenReturn("1");

        workflowCmd = new WorkflowCmd();
        cmd = getValidWorkflowEditCmd();

        workflowCmd.setWorkflowItem(mockWorkflowItem);

        newAddress = new ArrayList<String>();
        newAddress.add(HOUSENAMENUMBER);
        newAddress.add(STREET);
        newAddress.add(TOWN);
        newAddress.add(COUNTRY);
    }

    @Test
    public void shouldInitialise() {
        service.initialize();
        verify(mockContentService, atLeast(13)).findByLocaleAndCode(anyString(), anyString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void saveChangesShouldCallProcessMethods() {
        doCallRealMethod().when(mockService).saveChanges(any(WorkflowCmd.class), any(WorkflowEditCmd.class));
        when(mockService.workflowItemConverter.convert(mockWorkflowItem)).thenReturn(workflowCmd);

        mockService.saveChanges(workflowCmd, cmd);

        verify(mockService).processCustomerDetails(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));
        verify(mockService).processRefundScenario(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));
        verify(mockService).processRefundItemDetails(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void processCustomerDetailsShouldCallSubProcessMethods() {
        doCallRealMethod().when(mockService).processCustomerDetails(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(WorkflowEditCmd.class));

        mockService.processCustomerDetails(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        verify(mockService).processCustomerName(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));
        verify(mockService).processCustomerUsername(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyString(), anyString(),
                        anyString());
        verify(mockService).processCustomerAddress(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(List.class),
                        any(WorkflowEditCmd.class));
    }

    @Test
    public void shouldProcessCustomerName() {
        String oldValue = BLANK;
        String newValue = TITLE + SPACE + FIRSTNAME + SPACE + INITIALS + SPACE + LASTNAME;

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        workflowCmd.setPaymentName(oldValue);
        List<CaseHistoryNote> caseNotes = service.processCustomerName(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));

        CustomerDTO outputName = mockRefundDetail.getCustomer();
        assertEquals(newValue, outputName.getTitle() + SPACE + outputName.getFirstName() + SPACE + outputName.getInitials()
                        + SPACE + outputName.getLastName());
    }

    private void checkCaseNotes(CaseHistoryNote expectedCaseNote, CaseHistoryNote caseNote) {
        assertEquals(expectedCaseNote.getClass(), caseNote.getClass());
        assertEquals(expectedCaseNote.getAgent(), caseNote.getAgent());
        assertEquals(expectedCaseNote.getMessage(), caseNote.getMessage());
        assertEquals(expectedCaseNote.getStatus(), caseNote.getStatus());
    }

    @Test
    public void shouldProcessCustomerUsername() {
        String oldValue = "oldUser";
        String newValue = "newUser";

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processCustomerUsername(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newValue, mockRefundDetail.getName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessCustomerAddress() {
        List<String> oldAddress = new ArrayList<String>();
        oldAddress.add("H");
        oldAddress.add("S");
        oldAddress.add("T");
        oldAddress.add("C");

        String message = getCaseNoteMessageNull(oldAddress.toString(), newAddress.toString(), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        doCallRealMethod().when(mockService).processCustomerAddress(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(List.class), any(WorkflowEditCmd.class));
        doCallRealMethod().when(mockService).isAddressUpdated(any(List.class), any(List.class));
        List<CaseHistoryNote> caseNotes = mockService.processCustomerAddress(mockCaseNotes, workflowCmd, mockWorkflowItem, oldAddress, cmd);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        AddressDTO outputAddress = mockRefundDetail.getAddress();
        assertEquals(newAddress.toString(),
                        "[" + outputAddress.getHouseNameNumber() + ", " + outputAddress.getStreet() + ", " + outputAddress.getTown() + ", "
                                        + outputAddress.getCountryName() + "]");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void processRefundScenarioShouldCallSubProcessMethods() {
        doCallRealMethod().when(mockService).processRefundScenario(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(WorkflowEditCmd.class));

        mockService.processRefundScenario(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        verify(mockService).processRefundScenarioType(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyString(), anyString(),
                        anyString());
        verify(mockService).processRefundScenarioSubType(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyString(),
                        anyString(), anyString());
        verify(mockService).processRefundablePeriodStartDate(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyString(),
                        anyString(), anyString());
    }

    @Test
    public void shouldProcessScenarioType() {
        String oldValue = RefundDepartmentEnum.CCS.code();
        String newValue = RefundDepartmentEnum.OYSTER.code();

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundScenarioType(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(RefundDepartmentEnum.OYSTER, mockRefundDetail.getRefundDepartment());
    }

    @Test
    public void shouldProcessScenarioSubType() {
        String oldValue = RefundScenarioEnum.LOST.code();
        String newValue = RefundScenarioEnum.GOODWILL.code();

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundScenarioSubType(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue,
                        REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(RefundScenarioEnum.GOODWILL, mockRefundDetail.getRefundScenario());
    }

    @Test
    public void shouldProcessRefundablePeriodStartDate() {
        String oldValue = "01/02/03";
        String newValue = "08/08/08";

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundablePeriodStartDate(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue,
                        REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(mockRefundDetail.getRefundDate(), service.formatter.parseDateTime(newValue));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void processRefundItemShouldCallSubProcessMethods() {
        doCallRealMethod().when(mockService).processRefundItemDetails(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(WorkflowEditCmd.class));

        mockService.processRefundItemDetails(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        verify(mockService).processTotalTicketAmount(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyLong(), anyInt(),
                        anyString());
        verify(mockService).processRefundItemAdminFee(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyLong(), anyInt(),
                        anyString());
        verify(mockService).processRefundItemPayAsyougoCredit(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyInt(),
                        anyInt(), anyString());
    }

    @Test
    public void shouldProcessRefundItemsWhenNewItemAdded() {
        List<RefundItemCmd> oldRefundItems = new ArrayList<RefundItemCmd>();
        List<RefundItemCmd> newRefundItems = new ArrayList<RefundItemCmd>();
        RefundItemCmd newRefundItem = new RefundItemCmd("Test", "1", "2", "Pro Rata", "£10", 1020L);
        newRefundItems.add(newRefundItem);

        String message = ADDED_REFUND_ITEM + newRefundItem.toString() + REASON;

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundItems(mockCaseNotes, workflowCmd, mockWorkflowItem, oldRefundItems, newRefundItems,
                        REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newRefundItems, mockRefundDetail.getRefundItems());
    }

    @Test
    public void shouldProcessGoodwillItemsWhenNewItemAdded() {
        List<GoodwillPaymentItemCmd> oldGoodwillItems = new ArrayList<GoodwillPaymentItemCmd>();
        List<GoodwillPaymentItemCmd> newGoodwillItems = new ArrayList<GoodwillPaymentItemCmd>();
        GoodwillPaymentItemCmd newGoodwillPaymentItemCmd = new GoodwillPaymentItemCmd(
                        (GoodwillPaymentItemDTO) RefundWorkflowTestUtil.getNewGoodwillPaymentItemDTO());
        newGoodwillItems.add(newGoodwillPaymentItemCmd);
        GoodwillPaymentItemCmd oldGoodwillPaymentItemCmd = new GoodwillPaymentItemCmd(
                        (GoodwillPaymentItemDTO) RefundWorkflowTestUtil.getNewGoodwillPaymentItemDTO2());
        oldGoodwillItems.add(oldGoodwillPaymentItemCmd);

        String message = ADDED_REFUND_ITEM + newGoodwillPaymentItemCmd.toString() + REASON;

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processGoodwillPaymentItems(mockCaseNotes, workflowCmd, mockWorkflowItem, oldGoodwillItems,
                        newGoodwillItems, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newGoodwillItems, mockRefundDetail.getGoodwillItems());
    }

    @Test
    public void shouldProcessRefundItemsWhenOldItemRemoved() {
        List<RefundItemCmd> oldRefundItems = new ArrayList<RefundItemCmd>();
        List<RefundItemCmd> newRefundItems = new ArrayList<RefundItemCmd>();
        RefundItemCmd oldRefundItem = new RefundItemCmd("Test", "1", "2", "Pro Rata", "£10", 1020L);
        oldRefundItems.add(oldRefundItem);

        String message = REMOVED_REFUND_ITEM + oldRefundItem.toString() + REASON;

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundItems(mockCaseNotes, workflowCmd, mockWorkflowItem, oldRefundItems, newRefundItems,
                        REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newRefundItems, mockRefundDetail.getRefundItems());
    }

    @Test
    public void shouldProcessRefundItemAmount() {
        Long oldValue = 0L;
        Integer newValue = 1;

        String message = getCaseNoteMessage(CurrencyUtil.formatPenceWithHtmlCurrencySymbol(oldValue.intValue()),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(newValue), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processTotalTicketAmount(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(new Long(newValue), mockRefundDetail.getTotalTicketPrice());
    }

    @Test
    public void shouldProcessRefundItemAdminFee() {
        Long oldValue = 0L;
        Integer newValue = 1;

        String message = getCaseNoteMessage(CurrencyUtil.formatPenceWithHtmlCurrencySymbol(oldValue.intValue()),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(newValue), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundItemAdminFee(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(new Long(newValue), mockRefundDetail.getAdministrationFee());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void processPaymentDetailsShouldCallSubProcessMethods() {
        doCallRealMethod().when(mockService).processPaymentDetails(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(WorkflowEditCmd.class));

        mockService.processPaymentDetails(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        verify(mockService).processPaymentMethod(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyString(), anyString(),
                        anyString());
        verify(mockService).processPaymentName(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(WorkflowEditCmd.class));
        verify(mockService).processPaymentAddress(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), any(List.class),
                        any(WorkflowEditCmd.class));
        verify(mockService).processPaymentAmount(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class), anyLong(), anyInt(),
                        anyString());
    }

    @Test
    public void shouldProcessPaymentMethod() {

        String oldValue = PaymentType.CHEQUE.code();
        String newValue[] = { WEB_CREDIT, ADHOC_LOAD, PAYMENT_CARD };
        for (int i = 0; i < newValue.length; i++) {
            String message = getCaseNoteMessage(oldValue, newValue[i], REASON);
            CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
            List<CaseHistoryNote> caseNotes = service.processPaymentMethod(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue[i],
                            REASON);
            checkCaseNotes(expectedCaseNote, caseNotes.get(i));
        }

    }

    @Test
    public void shouldProcessPaymentName() {
        String oldValue = "Mr John Smith";
        String newValue = TITLE + SPACE + FIRSTNAME + SPACE + INITIALS + SPACE + LASTNAME;

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        workflowCmd.setPaymentName(oldValue);
        List<CaseHistoryNote> caseNotes = service.processPaymentName(mockCaseNotes, workflowCmd, mockWorkflowItem, cmd);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        CustomerDTO outputPaymentName = mockRefundDetail.getAlternativeRefundPayee();
        assertEquals(newValue, outputPaymentName.getTitle() + SPACE + outputPaymentName.getFirstName() + SPACE
                        + outputPaymentName.getInitials() + SPACE + outputPaymentName.getLastName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldProcessPaymentAddress() {
        List<String> oldAddress = new ArrayList<String>();
        oldAddress.add("H");
        oldAddress.add("S");
        oldAddress.add("T");
        oldAddress.add("C");

        String message = getCaseNoteMessageNull(oldAddress.toString(), newAddress.toString(), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        doCallRealMethod().when(mockService).processPaymentAddress(any(List.class), any(WorkflowCmd.class), any(WorkflowItemDTO.class),
                        any(List.class), any(WorkflowEditCmd.class));
        doCallRealMethod().when(mockService).isAddressUpdated(any(List.class), any(List.class));
        List<CaseHistoryNote> caseNotes = mockService.processPaymentAddress(mockCaseNotes, workflowCmd, mockWorkflowItem, oldAddress, cmd);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        AddressDTO outputAddress = mockRefundDetail.getAlternativeAddress();
        assertEquals(newAddress.toString(),
                        "[" + outputAddress.getHouseNameNumber() + ", " + outputAddress.getStreet() + ", " + outputAddress.getTown() + ", "
                                        + outputAddress.getCountryName() + "]");
    }

    @Test
    public void shouldProcessPaymentAmount() {
        String oldValue = "&pound;150.00";
        String newValue = "&pound;180.00";
        Integer newValueInteger = 18000;

        String message = getCaseNoteMessage(oldValue, newValue, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processPaymentAmount(mockCaseNotes, workflowCmd, mockWorkflowItem, new Long(15000),
                        newValueInteger, REASON);

        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newValueInteger.longValue(), mockRefundDetail.getTotalRefundAmount().longValue());
    }

    @Test
    public void shouldProcessPayAsYouGoCredit() {

        Integer oldValue = 200;
        Integer newValue = 300;
        String message = getCaseNoteMessage(CurrencyUtil.formatPenceWithHtmlCurrencySymbol(oldValue.intValue()),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(newValue), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processRefundItemPayAsyougoCredit(mockCaseNotes, workflowCmd, mockWorkflowItem, oldValue, newValue,
                        REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newValue, mockRefundDetail.getPayAsYouGoCredit());

    }

    @Test
    public void shouldProcessPaymentSortCode() {

        String oldSortCode = "22-33-44";
        String newSortCode = "12-50-89";
        String message = getCaseNoteMessage(oldSortCode, newSortCode, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());

        List<CaseHistoryNote> caseNotes = service.processPaymentSortCode(mockCaseNotes, workflowCmd, mockWorkflowItem, oldSortCode, newSortCode,
                        REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newSortCode, workflowCmd.getPayeeSortCode());
    }

    @Test
    public void shouldProcessPaymentAccountNumber() {
        String oldAccountNumber = "1415161";
        String newAccountNumber = "1566723";
        String message = getCaseNoteMessage(oldAccountNumber, newAccountNumber, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
        List<CaseHistoryNote> caseNotes = service.processPaymentAccountNumber(mockCaseNotes, workflowCmd, mockWorkflowItem, oldAccountNumber,
                        newAccountNumber, REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newAccountNumber, workflowCmd.getPayeeAccountNumber());
    }

    @Test
    public void shouldProcessPaymentStation() {
        Long oldStationId = 1L;
        Long newStationId = 5L;
        String message = getCaseNoteMessage(LocationTestUtil.LOCATION_NAME_2, LocationTestUtil.LOCATION_NAME_1, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
        List<CaseHistoryNote> caseNotes = service.processPaymentStationId(mockCaseNotes, workflowCmd, mockWorkflowItem, oldStationId, newStationId,
                        REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(LocationTestUtil.LOCATION_NAME_1, workflowCmd.getStation());
    }

    @Test
    public void shouldProcessPaymentStationWithNull() {

        Long newStationId = 5L;
        String message = getCaseNoteMessage("BLANK", LocationTestUtil.LOCATION_NAME_1, REASON);
        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
        List<CaseHistoryNote> caseNotes = service.processPaymentStationId(mockCaseNotes, workflowCmd, mockWorkflowItem, null, newStationId, REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(LocationTestUtil.LOCATION_NAME_1, workflowCmd.getStation());
    }

    @Test
    public void shouldProcessTargetCardNumber() {
        String oldTargetCardNumber = "010819984445";
        String newTargetCardNumber = "010721722966";
        String message = getCaseNoteMessage(oldTargetCardNumber, newTargetCardNumber, REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
        List<CaseHistoryNote> caseNotes = service.processTargetCardNumber(mockCaseNotes, workflowCmd, mockWorkflowItem, oldTargetCardNumber,
                        newTargetCardNumber, REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newTargetCardNumber, workflowCmd.getTargetCardNumber());
    }

    @Test
    public void shouldProcessTicketDeposit() {

        Integer oldTicketDeposit = 4000;
        Integer newTicketDeposit = 5000;
        String message = getCaseNoteMessage(CurrencyUtil.formatPenceWithHtmlCurrencySymbol(oldTicketDeposit.intValue()),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(newTicketDeposit.intValue()), REASON);

        CaseHistoryNote expectedCaseNote = new CaseHistoryNote(message, mockWorkflowItem.getAgent(), mockWorkflowItem.getStatus());
        List<CaseHistoryNote> caseNotes = service.processRefundItemDeposit(mockCaseNotes, workflowCmd, mockWorkflowItem,
                        oldTicketDeposit.longValue(), newTicketDeposit, REASON);
        checkCaseNotes(expectedCaseNote, caseNotes.get(0));
        assertEquals(newTicketDeposit, mockRefundDetail.getDeposit());

    }

}
