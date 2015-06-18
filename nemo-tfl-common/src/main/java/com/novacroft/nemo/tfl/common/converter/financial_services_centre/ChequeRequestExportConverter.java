package com.novacroft.nemo.tfl.common.converter.financial_services_centre;

import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeRequestExportDTO;

/**
 * Cheque request export converter
 */
public interface ChequeRequestExportConverter {
    String[] convert(ChequeRequestExportDTO chequeRequestExportDTO);
}
