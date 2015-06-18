package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import com.novacroft.nemo.tfl.common.converter.financial_services_centre.ChequeRequestExportConverter;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeRequestExportDTO;
import org.springframework.stereotype.Component;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPoundsAndPenceWithoutCurrencySymbolOrCommas;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.common.util.financial_services_centre.ExportFileUtil
        .formatDateForFinancialServicesCentreExport;

/**
 * Cheque request export converter
 */
@Component("chequeRequestExportConverter")
public class ChequeRequestExportConverterImpl implements ChequeRequestExportConverter {
    @Override
    public String[] convert(ChequeRequestExportDTO chequeRequestExportDTO) {
        return new String[]{getDocumentId(chequeRequestExportDTO), getDocumentDate(chequeRequestExportDTO),
                getPostingDate(chequeRequestExportDTO), getDocumentType(chequeRequestExportDTO),
                getCompanyCode(chequeRequestExportDTO), getCurrency(chequeRequestExportDTO),
                getReferenceNumber(chequeRequestExportDTO), getDocumentHeaderText(chequeRequestExportDTO),
                getAccountType(chequeRequestExportDTO), getAccountNumber(chequeRequestExportDTO), EMPTY_STRING,
                getBaselineDate(chequeRequestExportDTO), getNetAmount(chequeRequestExportDTO), EMPTY_STRING,
                getTaxCode(chequeRequestExportDTO), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                getCustomerName(chequeRequestExportDTO), EMPTY_STRING, getCustomerAddressLine1(chequeRequestExportDTO),
                getCustomerAddressLine2(chequeRequestExportDTO), getCustomerAddressLine3(chequeRequestExportDTO),
                getCustomerAddressLine4(chequeRequestExportDTO), getCustomerAddressPostCode(chequeRequestExportDTO),
                EMPTY_STRING, getCustomerReference(chequeRequestExportDTO), EMPTY_STRING,
                getCustomerVatRegistrationNumber(chequeRequestExportDTO), getPaymentMethod(chequeRequestExportDTO)};
    }

    protected String getDocumentId(ChequeRequestExportDTO chequeRequestExportDTO) {
        return String.valueOf(chequeRequestExportDTO.getDocumentId());
    }

    protected String getDocumentDate(ChequeRequestExportDTO chequeRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(chequeRequestExportDTO.getDocumentDate());
    }

    protected String getPostingDate(ChequeRequestExportDTO chequeRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(chequeRequestExportDTO.getPostingDate());
    }

    protected String getDocumentType(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getDocumentType();
    }

    protected String getCompanyCode(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getCompanyCode();
    }

    protected String getCurrency(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getCurrency();
    }

    protected String getReferenceNumber(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getReferenceNumber();
    }

    protected String getDocumentHeaderText(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getDocumentHeaderText();
    }

    protected String getAccountType(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getAccountType();
    }

    protected String getAccountNumber(ChequeRequestExportDTO chequeRequestExportDTO) {
        return String.valueOf(chequeRequestExportDTO.getAccountNumber());
    }

    protected String getBaselineDate(ChequeRequestExportDTO chequeRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(chequeRequestExportDTO.getBaselineDate());
    }

    protected String getNetAmount(ChequeRequestExportDTO chequeRequestExportDTO) {
        return formatPoundsAndPenceWithoutCurrencySymbolOrCommas(chequeRequestExportDTO.getNetAmount());
    }

    protected String getTaxCode(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getTaxCode();
    }

    protected String getCustomerName(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getCustomerName();
    }

    protected String getCustomerAddressLine1(ChequeRequestExportDTO chequeRequestExportDTO) {
        return isAddressNotNull(chequeRequestExportDTO) ?
                chequeRequestExportDTO.getAddressExportDTO().getCustomerAddressLine1() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine2(ChequeRequestExportDTO chequeRequestExportDTO) {
        return isAddressNotNull(chequeRequestExportDTO) ?
                chequeRequestExportDTO.getAddressExportDTO().getCustomerAddressLine2() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine3(ChequeRequestExportDTO chequeRequestExportDTO) {
        return isAddressNotNull(chequeRequestExportDTO) ?
                chequeRequestExportDTO.getAddressExportDTO().getCustomerAddressStreet() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine4(ChequeRequestExportDTO chequeRequestExportDTO) {
        return isAddressNotNull(chequeRequestExportDTO) ?
                chequeRequestExportDTO.getAddressExportDTO().getCustomerAddressTown() : EMPTY_STRING;
    }

    protected String getCustomerAddressPostCode(ChequeRequestExportDTO chequeRequestExportDTO) {
        return isAddressNotNull(chequeRequestExportDTO) ?
                chequeRequestExportDTO.getAddressExportDTO().getCustomerAddressPostCode() : EMPTY_STRING;
    }

    protected String getCustomerReference(ChequeRequestExportDTO chequeRequestExportDTO) {
        return EMPTY_STRING;
    }

    protected String getCustomerVatRegistrationNumber(ChequeRequestExportDTO chequeRequestExportDTO) {
        return EMPTY_STRING;
    }

    protected String getPaymentMethod(ChequeRequestExportDTO chequeRequestExportDTO) {
        return chequeRequestExportDTO.getPaymentMethod();
    }

    protected boolean isAddressNotNull(ChequeRequestExportDTO chequeRequestExportDTO) {
        return null != chequeRequestExportDTO.getAddressExportDTO();
    }
}
