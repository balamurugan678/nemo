package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.FileExportLog;

/**
 * File Export Log data access
 */
@Repository("fileExportLogDAO")
public class FileExportLogDAO extends BaseDAOImpl<FileExportLog> {
    public Long getNextFinancialServicesCentreExportFileSequenceNumber() {
        return getNextSequenceNumber("fin_serv_cen_export_file_seq");
    }
    
    public Long getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest(){
        return getNextSequenceNumber("export_file_seq_bacs");
    }
    
    public Long getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest(){
        return getNextSequenceNumber("export_file_seq_cheque");
    }
}
