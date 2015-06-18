package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileGenerator;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileService;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

/**
 * Export to file service
 */
@Service("exportFileService")
public class ExportFileServiceImpl implements ExportFileService {
    protected Map<String, ExportFileGenerator> fileTypeGeneratorMap = null;

    @Autowired
    @Qualifier(value="chequeRequestFileGeneratorService")
    protected ExportFileGenerator chequeRequestFileGeneratorService;
    
    @Autowired
    @Qualifier(value="bacsRequestFileGeneratorService")
    protected ExportFileGenerator bacsRequestFileGeneratorService;

    @PostConstruct
    public void initialiseFileTypeGeneratorMap() {
        this.fileTypeGeneratorMap = new HashMap<String, ExportFileGenerator>();
        this.fileTypeGeneratorMap.put(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code(),
                this.chequeRequestFileGeneratorService);
        this.fileTypeGeneratorMap.put(ExportFileType.FINANCIAL_SERVICES_CENTRE_BACS_REQUEST_FILE.code(),
                this.bacsRequestFileGeneratorService);
    }
 
    @Override
    public byte[] exportFile(String exportFileType, String exportFileName) {
        if (!fileTypeGeneratorMap.containsKey(exportFileType)) {
            throw new ApplicationServiceException(
                    String.format(PrivateError.UNSUPPORTED_EXPORT_FILE_TYPE.message(), exportFileType));
        }
        return fileTypeGeneratorMap.get(exportFileType).generateExportFile(exportFileName);
    }
}
