package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

/**
 * Export to file service
 */
public interface ExportFileService {
    byte[] exportFile(String exportFileType, String exportFileName);
}
