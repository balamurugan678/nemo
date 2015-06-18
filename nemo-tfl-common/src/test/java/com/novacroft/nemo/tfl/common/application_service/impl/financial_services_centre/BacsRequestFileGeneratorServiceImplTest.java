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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.BACSRequestExportConverter;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSRequestExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

public class BacsRequestFileGeneratorServiceImplTest {

    private BacsRequestFileGeneratorServiceImpl service;

    private NemoUserContext mockNemoUserContext;
    private FileExportLogDataService mockFileExportLogDataService;
    private BACSSettlementDataService mockBacsSettlementDataService;
    private SystemParameterService mockSystemParameterService;
    private BACSRequestExportConverter mockbacsRequestExportConverter;
    private AddressExportConverter mockAddressExportConverter;

    private BACSSettlementDTO mockBACSSettlementDTO;
    private AddressDTO mockAddressDTO;
    private AddressExportDTO mockAddressExportDTO;
    private BACSRequestExportDTO mockBACSRequestExportDTO;
    private List<BACSRequestExportDTO> mockBacsRequestExportDTOList;
    private List<BACSSettlementDTO> mockBacsSettlementDTOList;
    private OrderDTO mockOrderDTO;
    private FileExportLogDTO mockFileExportLogDTO;

    @Before
    public void setUp() {
        this.service = mock(BacsRequestFileGeneratorServiceImpl.class);

        this.mockNemoUserContext = mock(NemoUserContext.class);
        this.service.nemoUserContext = this.mockNemoUserContext;
        this.mockFileExportLogDataService = mock(FileExportLogDataService.class);
        this.service.fileExportLogDataService = this.mockFileExportLogDataService;
        this.mockBacsSettlementDataService = mock(BACSSettlementDataService.class);
        this.service.bascsSettlementDataService = this.mockBacsSettlementDataService;
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.service.systemParameterService = this.mockSystemParameterService;
        this.mockbacsRequestExportConverter = mock(BACSRequestExportConverter.class);
        this.service.bacsRequestExportConverter = this.mockbacsRequestExportConverter;
        this.mockAddressExportConverter = mock(AddressExportConverter.class);
        this.service.addressExportConverter = this.mockAddressExportConverter;

        this.mockBACSSettlementDTO = mock(BACSSettlementDTO.class);
        this.mockAddressDTO = mock(AddressDTO.class);
        when(this.mockBACSSettlementDTO.getAddressDTO()).thenReturn(this.mockAddressDTO);
        this.mockAddressExportDTO = mock(AddressExportDTO.class);
        this.mockBACSRequestExportDTO = mock(BACSRequestExportDTO.class);
        this.mockOrderDTO = mock(OrderDTO.class);
        this.mockFileExportLogDTO = mock(FileExportLogDTO.class);

        this.mockBacsRequestExportDTOList = new ArrayList<BACSRequestExportDTO>();
        this.mockBacsRequestExportDTOList.add(this.mockBACSRequestExportDTO);

        this.mockBacsSettlementDTOList = new ArrayList<BACSSettlementDTO>();
        this.mockBacsSettlementDTOList.add(this.mockBACSSettlementDTO);
    }

    @Test
    public void shouldTransformAddressForExport() {
        when(this.service.transformAddressForExport(any(BACSSettlementDTO.class))).thenCallRealMethod();
        when(this.mockAddressExportConverter.convert(any(AddressDTO.class))).thenReturn(this.mockAddressExportDTO);
        this.service.transformAddressForExport(this.mockBACSSettlementDTO);
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
        when(this.mockbacsRequestExportConverter.convert(any(BACSRequestExportDTO.class))).thenReturn(TEST_EXPORT_RECORD);
        byte[] result = this.service.prepareCsv(this.mockBacsRequestExportDTOList);
        assertArrayEquals(TEST_EXPORT_RECORD_AS_STRING.getBytes(), result);
        verify(this.mockbacsRequestExportConverter).convert(any(BACSRequestExportDTO.class));
    }

    @Test
    public void shouldTransformSettlementsForExport() {
        when(this.service.transformSettlementsForExport(anyList(), anyString())).thenCallRealMethod();
        when(this.service.getIntegerParameter(anyString())).thenReturn(0);
        when(this.service.getStringParameter(anyString())).thenReturn(EMPTY_STRING);
        when(this.service.getIntegerParameter(anyString())).thenReturn(0);
        when(this.service.transformAddressForExport(any(BACSSettlementDTO.class))).thenReturn(this.mockAddressExportDTO);

        List<BACSRequestExportDTO> result =
                this.service.transformSettlementsForExport(this.mockBacsSettlementDTOList, TEST_FILE_NAME_1);
        assertEquals(2, result.size());

        verify(this.service, times(2)).getIntegerParameter(anyString());
        verify(this.service, atLeastOnce()).getStringParameter(anyString());
        verify(this.service).transformAddressForExport(any(BACSSettlementDTO.class));
    }
    
    @Test
    public void shouldMarkSettlementsAsExported() {
        doCallRealMethod().when(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        when(this.mockBacsSettlementDataService.createOrUpdate(any(BACSSettlementDTO.class)))
                .thenReturn(this.mockBACSSettlementDTO);
        when(this.mockFileExportLogDTO.getId()).thenReturn(0L);
        this.service.markSettlementsAsExported(this.mockBacsSettlementDTOList, this.mockFileExportLogDTO);
        verify(this.mockBacsSettlementDataService).createOrUpdate(any(BACSSettlementDTO.class));
        verify(this.mockFileExportLogDTO).getId();
    }
	
    
    @Test
    public void shouldGetBacsSettlementsToExport() {
        when(this.service.getBACSSettlementsToExport()).thenCallRealMethod();
        when(this.mockBacsSettlementDataService.findByStatus(anyString())).thenReturn(this.mockBacsSettlementDTOList);
        this.service.getBACSSettlementsToExport();
        verify(this.mockBacsSettlementDataService).findByStatus(anyString());
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
        when(this.service.getBACSSettlementsToExport()).thenReturn(this.mockBacsSettlementDTOList);
        doNothing().when(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        when(this.service.transformSettlementsForExport(anyList(), anyString()))
                .thenReturn(this.mockBacsRequestExportDTOList);
        when(this.service.prepareCsv(anyList())).thenReturn(new byte[0]);

        this.service.generateExportFile(TEST_FILE_NAME_1);

        verify(this.service).createFileExportLog(anyString());
        verify(this.service).getBACSSettlementsToExport();
        verify(this.service).markSettlementsAsExported(anyList(), any(FileExportLogDTO.class));
        verify(this.service).transformSettlementsForExport(anyList(), anyString());
        verify(this.service).prepareCsv(anyList());
    }
}
