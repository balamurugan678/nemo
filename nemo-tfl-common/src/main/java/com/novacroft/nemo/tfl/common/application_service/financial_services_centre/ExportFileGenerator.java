package com.novacroft.nemo.tfl.common.application_service.financial_services_centre;

/**
 * Generate export file specification
 */
public interface ExportFileGenerator {
    byte[] generateExportFile(String exportFileName);
}
