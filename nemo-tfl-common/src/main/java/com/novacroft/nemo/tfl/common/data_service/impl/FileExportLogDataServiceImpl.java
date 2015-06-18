package com.novacroft.nemo.tfl.common.data_service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.FileExportLogConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FileExportLogDAO;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;
import com.novacroft.nemo.tfl.common.domain.FileExportLog;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;

/**
 * File Export Log data service
 */
@Service(value = "fileExportLogDataService")
@Transactional(readOnly = true)
public class FileExportLogDataServiceImpl extends BaseDataServiceImpl<FileExportLog, FileExportLogDTO>
        implements FileExportLogDataService {
    @Override
    public Long getNextFinancialServicesCentreExportFileSequenceNumber() {
        return ((FileExportLogDAO) this.dao).getNextFinancialServicesCentreExportFileSequenceNumber();
    }

    @Override
    public FileExportLog getNewEntity() {
        return new FileExportLog();
    }

    @Autowired
    public void setDao(FileExportLogDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(FileExportLogConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Long getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest() {
        return ((FileExportLogDAO) this.dao).getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest();
    }

    @Override
    public Long getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest() {
        return ((FileExportLogDAO) this.dao).getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest();
    }

}
