package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.TEST_EXPORT_RECORD;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.TEST_EXPORT_RECORD_AS_STRING;
import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_NAME_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.AddressExportConverter;
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.ChequeRequestExportConverter;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeRequestExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class ChequeRequestFileGeneratorServiceImplTest {

    private ChequeRequestFileGeneratorServiceImpl service;

    private NemoUserContext mockNemoUserContext;
    private FileExportLogDataService mockFileExportLogDataService;
    private ChequeSettlementDataService mockChequeSettlementDataService;
    private SystemParameterService mockSystemParameterService;
    private OrderDataService mockOrderDataService;
    private ChequeRequestExportConverter mockChequeRequestExportConverter;
    private AddressExportConverter mockAddressExportConverter;

    private ChequeSettlementDTO mockChequeSettlementDTO;
    private AddressDTO mockAddressDTO;
    private AddressExportDTO mockAddressExportDTO;
    private ChequeRequestExportDTO mockChequeRequestExportDTO;
    private List<ChequeRequestExportDTO> mockChequeRequestExportDTOList;
    private List<ChequeSettlementDTO> mockChequeSettlementDTOList;
    private OrderDTO mockOrderDTO;
    private FileExportLogDTO mockFileExportLogDTO;

    @Before
    public void setUp() {
        this.service = mock(ChequeRequestFileGeneratorServiceImpl.class);

        this.mockNemoUserContext = mock(NemoUserContext.class);
        this.service.nemoUserContext = this.mockNemoUserContext;
        this.mockFileExportLogDataService = mock(FileExportLogDataService.class);
        this.service.fileExportLogDataService = this.mockFileExportLogDataService;
        this.mockChequeSettlementDataService = mock(ChequeSettlementDataService.class);
        this.service.chequeSettlementDataService = this.mockChequeSettlementDataService;
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.service.systemParameterService = this.mockSystemParameterService;
        this.mockOrderDataService = mock(OrderDataService.class);
        this.service.orderDataService = this.mockOrderDataService;
        this.mockChequeRequestExportConverter = mock(ChequeRequestExportConverter.class);
        this.service.chequeRequestExportConverter = this.mockChequeRequestExportConverter;
        this.mockAddressExportConverter = mock(AddressExportConverter.class);
        this.service.addressExportConverter = this.mockAddressExportConverter;

        this.mockChequeSettlementDTO = mock(ChequeSettlementDTO.class);
        this.mockAddressDTO = mock(AddressDTO.class);
        when(this.mockChequeSettlementDTO.getAddressDTO()).thenReturn(this.mockAddressDTO);
        this.mockAddressExportDTO = mock(AddressExportDTO.class);
        this.mockChequeRequestExportDTO = mock(ChequeRequestExportDTO.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockFileExportLogDTO = mock(FileExportLogDTO.class);

        this.mockChequeRequestExportDTOList = new ArrayList<ChequeRequestExportDTO>();
        this.mockChequeRequestExportDTOList.add(this.mockChequeRequestExportDTO);

        this.mockChequeSettlementDTOList = new ArrayList<ChequeSettlementDTO>();
        this.mockChequeSettlementDTOList.add(this.mockChequeSettlementDTO);
    }

    @Test
    public void shouldTransformAddressForExport() {
        when(this.service.transformAddressForExport(any(ChequeSettlementDTO.class))).thenCallRealMethod();
        when(this.mockAddressExportConverter.convert(any(AddressDTO.class))).thenReturn(this.mockAddressExportDTO);
        this.service.transformAddressForExport(this.mockChequeSettlementDTO);
        verify(this.mockAddressExportConverter).convert(any(AddressDTO.class));
    }

    @Test
    public void shouldGetIntegerParameter() {
        when(this.service.getIntegerParameter(anyString())).thenCallRealMethod();
        when(this.mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(0);
        this.service.getIntegerParameter(EMPTY_STRING);
        verify(this.mockSystemParameterService).getIntegerParameterValue(anyString());
    }

    @Test
    public void shouldGetStringParameter() {
        when(this.service.getStringParameter(anyString())).thenCallRealMethod();
        when(this.mockSystemParameterService.getParameterValue(anyString())).thenReturn(EMPTY_STRING);
        this.service.getStringParameter(EMPTY_STRING);
        verify(this.mockSystemParameterService).getParameterValue(anyString());
    }

    @Test
    public void shouldPrepareCsv() {
        when(this.service.prepareCsv(anyList())).thenCallRealMethod();
        when(this.mockChequeRequestExportConverter.convert(any(ChequeRequestExportDTO.class))).thenReturn(TEST_EXPORT_RECORD);
        byte[] result = this.service.prepareCsv(this.mockChequeRequestExportDTOList);
        assertArrayEquals(TEST_EXPORT_RECORD_AS_STRING.getBytes(), result);
        verify(this.mockChequeRequestExportConverter).convert(any(ChequeRequestExportDTO.class));
    }

    @Test
    public void shouldTransformSettlementsForExport() {
        when(this.service.transformSettlementsForExport(anyList(), anyString())).thenCallRealMethod();
        when(this.service.getIntegerParameter(anyString())).thenReturn(0);
        when(this.service.getStringParameter(anyString())).thenReturn(EMPTY_STRING);
        when(this.service.transformAddressForExport(any(ChequeSettlementDTO.class))).thenReturn(this.mockAddressExportDTO);
        when(this.mockOrderDataService.findById(anyLong())).thenReturn(this.mockOrderDTO);

        List<ChequeRequestExportDTO> result =
                this.service.transformSettlementsForExport(this.mockChequeSettlementDTOList, TEST_FILE_NAME_1);
        assertEquals(2, result.size());

        verify(this.service, atLeast(2)).getIntegerParameter(anyString());
        verify(this.service, atLeastOnce()).getStringParameter(anyString());
        verify(this.service).transformAddressForExport(any(ChequeSettlementDTO.class));
        
    }

    @Test
    public void shouldMarkSettlementsAsExported() {
        doCallRealMethod().when(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        when(this.mockChequeSettlementDataService.createOrUpdate(any(ChequeSettlementDTO.class)))
                .thenReturn(this.mockChequeSettlementDTO);
        when(this.mockFileExportLogDTO.getId()).thenReturn(0L);
        this.service.markSettlementsAsExported(this.mockChequeSettlementDTOList, this.mockFileExportLogDTO);
        verify(this.mockChequeSettlementDataService).createOrUpdate(any(ChequeSettlementDTO.class));
        verify(this.mockFileExportLogDTO).getId();
    }

    @Test
    public void shouldGetChequeSettlementsToExport() {
        when(this.service.getChequeSettlementsToExport()).thenCallRealMethod();
        when(this.mockChequeSettlementDataService.findByStatus(anyString())).thenReturn(this.mockChequeSettlementDTOList);
        this.service.getChequeSettlementsToExport();
        verify(this.mockChequeSettlementDataService).findByStatus(anyString());
    }

    @Test
    public void shouldCreateFileExportLog() {
        when(this.service.createFileExportLog(anyString())).thenCallRealMethod();
        when(this.mockFileExportLogDataService.createOrUpdate(any(FileExportLogDTO.class)))
                .thenReturn(this.mockFileExportLogDTO);
        when(this.mockNemoUserContext.getUserName()).thenReturn(USERNAME_1);
        this.service.createFileExportLog(TEST_FILE_NAME_1);
        verify(this.mockFileExportLogDataService).createOrUpdate(any(FileExportLogDTO.class));
        verify(this.mockNemoUserContext).getUserName();
    }

    @Test
    public void shouldGenerateExportFile() {
        when(this.service.generateExportFile(anyString())).thenCallRealMethod();
        when(this.service.createFileExportLog(anyString())).thenReturn(this.mockFileExportLogDTO);
        when(this.service.getChequeSettlementsToExport()).thenReturn(this.mockChequeSettlementDTOList);
        doNothing().when(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        when(this.service.transformSettlementsForExport(anyList(), anyString()))
                .thenReturn(this.mockChequeRequestExportDTOList);
        when(this.service.prepareCsv(anyList())).thenReturn(new byte[0]);

        this.service.generateExportFile(TEST_FILE_NAME_1);

        verify(this.service).createFileExportLog(anyString());
        verify(this.service).getChequeSettlementsToExport();
        verify(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        verify(this.service).transformSettlementsForExport(anyList(), anyString());
        verify(this.service).prepareCsv(anyList());
    }
}