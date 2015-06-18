package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

/**
 * Create names for export files
 */
public interface ExportFileNameService {
    String getExportFileName(String exportFileType);

    String getFinancialServicesCentreChequeRequestFileName();
}
