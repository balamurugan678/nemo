package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.FileExportLogConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FileExportLogDAO;

public class FileExportLogDataServiceImplTest {
    private static final Long EXPECTED_SEQUENCE_NUMBER = 222L;
    
    private FileExportLogDataServiceImpl service;
    private FileExportLogDAO mockDAO;
    private FileExportLogConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        service = new FileExportLogDataServiceImpl();
        mockDAO = mock(FileExportLogDAO.class);
        mockConverter = mock(FileExportLogConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void shouldGetNextFinancialServicesCentreExportFileSequenceNumber() {
        when(mockDAO.getNextFinancialServicesCentreExportFileSequenceNumber()).thenReturn(EXPECTED_SEQUENCE_NUMBER);
        
        assertEquals(EXPECTED_SEQUENCE_NUMBER, 
                        service.getNextFinancialServicesCentreExportFileSequenceNumber());
        verify(mockDAO).getNextFinancialServicesCentreExportFileSequenceNumber();
    }
    
    @Test
    public void shouldGetNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest() {
        when(mockDAO.getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest()).thenReturn(EXPECTED_SEQUENCE_NUMBER);
        
        assertEquals(EXPECTED_SEQUENCE_NUMBER, 
                        service.getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest());
        verify(mockDAO).getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest();
    }
    
    @Test
    public void shouldGetNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest() {
        when(mockDAO.getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest()).thenReturn(EXPECTED_SEQUENCE_NUMBER);
        
        assertEquals(EXPECTED_SEQUENCE_NUMBER, 
                        service.getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest());
        verify(mockDAO).getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest();
    }
}
