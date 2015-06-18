package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.FileExportLog;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;

/**
 * File Export Log data service
 */
public interface FileExportLogDataService extends BaseDataService<FileExportLog, FileExportLogDTO> {
    Long getNextFinancialServicesCentreExportFileSequenceNumber();
    
    Long getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest();
    
    Long getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest();
}
