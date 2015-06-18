package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileNameService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileType;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;

/**
 * Create names for export files
 */
@Service("exportFileNameService")
public class ExportFileNameServiceImpl implements ExportFileNameService {
    protected static Map<String, String> exportFileTypeCreatorMethodMap = new HashMap<String, String>();
    @Autowired
    protected FileExportLogDataService fileExportLogDataService;
    @Autowired
    protected SystemParameterService systemParameterService;

    static {
        exportFileTypeCreatorMethodMap.put(ExportFileType.FINANCIAL_SERVICES_CENTRE_CHEQUE_REQUEST_FILE.code(),
                "getFinancialServicesCentreChequeRequestFileName");
        exportFileTypeCreatorMethodMap.put(ExportFileType.FINANCIAL_SERVICES_CENTRE_BACS_REQUEST_FILE.code(),
                "getFinancialServicesCentreBacsRequestFileName");
    }

    @Override
    public String getExportFileName(String exportFileType) {
        return exportFileTypeCreatorMethodMap.containsKey(exportFileType) ?
                callMethodOnThisObject(exportFileTypeCreatorMethodMap.get(exportFileType)) : null;
    }

    @Override
    public String getFinancialServicesCentreChequeRequestFileName() {
        String prefix =
                this.systemParameterService.getParameterValue(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_PREFIX.code());
        return String.format("%s%s%s", prefix, getFinancialServicesCentreExportFileSequenceNumberForChequeRequest(),
                getFinancialServicesCentreExportFileSuffix());
    }

    
	public String getFinancialServicesCentreBacsRequestFileName() {
    	String prefix =
                this.systemParameterService.getParameterValue(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_PREFIX.code());
        return String.format("%s%s%s", prefix, getFinancialServicesCentreExportFileSequenceNumberForBACSRequest(),
                getFinancialServicesCentreExportFileSuffix());
	}
    
    protected String getFinancialServicesCentreExportFileSuffix() {
        return this.systemParameterService.getParameterValue(SystemParameterCode.FSC_EXPORT_FILE_SUFFIX.code());
    }

    protected Long getFinancialServicesCentreExportFileSequenceNumber() {
        return this.fileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumber();
    }

    protected Long getFinancialServicesCentreExportFileSequenceNumberForBACSRequest() {
        return this.fileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumberForBACSRequest();
    }
    
    protected Long getFinancialServicesCentreExportFileSequenceNumberForChequeRequest() {
        return this.fileExportLogDataService.getNextFinancialServicesCentreExportFileSequenceNumberForChequeRequest();
    }
    
    protected String callMethodOnThisObject(String methodName) {
        try {
            return (String) this.getClass().getDeclaredMethod(methodName, null).invoke(this, null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

	
}
