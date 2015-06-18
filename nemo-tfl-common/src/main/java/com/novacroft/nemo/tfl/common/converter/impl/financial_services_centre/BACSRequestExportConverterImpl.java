package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPoundsAndPenceWithoutCurrencySymbolOrCommas;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.common.util.financial_services_centre.ExportFileUtil.formatDateForFinancialServicesCentreExport;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.converter.financial_services_centre.BACSRequestExportConverter;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSRequestExportDTO;

@Component(value="bacsRequestExportConverter")
public class BACSRequestExportConverterImpl implements	BACSRequestExportConverter {

	@Override
    public String[] convert(BACSRequestExportDTO bacsRequestExportDTO) {
        return new String[]{getDocumentId(bacsRequestExportDTO), getDocumentDate(bacsRequestExportDTO),
                getPostingDate(bacsRequestExportDTO), getDocumentType(bacsRequestExportDTO),
                getCompanyCode(bacsRequestExportDTO), getCurrency(bacsRequestExportDTO),
                getReferenceNumber(bacsRequestExportDTO), getDocumentHeaderText(bacsRequestExportDTO),
                getAccountType(bacsRequestExportDTO), getAccountNumber(bacsRequestExportDTO), EMPTY_STRING,
                getBaselineDate(bacsRequestExportDTO), getNetAmount(bacsRequestExportDTO), EMPTY_STRING,
                getTaxCode(bacsRequestExportDTO), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING,
                getCustomerName(bacsRequestExportDTO), EMPTY_STRING, getCustomerAddressLine1(bacsRequestExportDTO),
                getCustomerAddressLine2(bacsRequestExportDTO), getCustomerAddressLine3(bacsRequestExportDTO),
                getCustomerAddressLine4(bacsRequestExportDTO), getCustomerAddressPostCode(bacsRequestExportDTO),getCustomerBankAccount(bacsRequestExportDTO), 
                getCustomerReference(bacsRequestExportDTO), getCustomerSortCode(bacsRequestExportDTO),
                getCustomerVatRegistrationNumber(bacsRequestExportDTO), getPaymentMethod(bacsRequestExportDTO)};
    }
	
	protected String getDocumentId(BACSRequestExportDTO bacsRequestExportDTO) {
	        return String.valueOf(bacsRequestExportDTO.getDocumentId());
    }

    protected String getDocumentDate(BACSRequestExportDTO bacsRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(bacsRequestExportDTO.getDocumentDate());
    }

    protected String getPostingDate(BACSRequestExportDTO bacsRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(bacsRequestExportDTO.getPostingDate());
    }

    protected String getDocumentType(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getDocumentType();
    }

    protected String getCompanyCode(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCompanyCode();
    }

    protected String getCurrency(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCurrency();
    }

    protected String getReferenceNumber(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getReferenceNumber();
    }

    protected String getDocumentHeaderText(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getDocumentHeaderText();
    }

    protected String getAccountType(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getAccountType();
    }

    protected String getAccountNumber(BACSRequestExportDTO bacsRequestExportDTO) {
        return String.valueOf(bacsRequestExportDTO.getAccountNumber());
    }

    protected String getBaselineDate(BACSRequestExportDTO bacsRequestExportDTO) {
        return formatDateForFinancialServicesCentreExport(bacsRequestExportDTO.getBaselineDate());
    }

    protected String getNetAmount(BACSRequestExportDTO bacsRequestExportDTO) {
        return formatPoundsAndPenceWithoutCurrencySymbolOrCommas(bacsRequestExportDTO.getNetAmount());
    }

    protected String getTaxCode(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getTaxCode();
    }

    protected String getCustomerName(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCustomerName();
    }

    protected String getCustomerAddressLine1(BACSRequestExportDTO bacsRequestExportDTO) {
        return isAddressNotNull(bacsRequestExportDTO) ?
                bacsRequestExportDTO.getAddressExportDTO().getCustomerAddressLine1() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine2(BACSRequestExportDTO bacsRequestExportDTO) {
        return isAddressNotNull(bacsRequestExportDTO) ?
                bacsRequestExportDTO.getAddressExportDTO().getCustomerAddressLine2() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine3(BACSRequestExportDTO bacsRequestExportDTO) {
        return isAddressNotNull(bacsRequestExportDTO) ?
                bacsRequestExportDTO.getAddressExportDTO().getCustomerAddressStreet() : EMPTY_STRING;
    }

    protected String getCustomerAddressLine4(BACSRequestExportDTO bacsRequestExportDTO) {
        return isAddressNotNull(bacsRequestExportDTO) ?
                bacsRequestExportDTO.getAddressExportDTO().getCustomerAddressTown() : EMPTY_STRING;
    }

    protected String getCustomerAddressPostCode(BACSRequestExportDTO bacsRequestExportDTO) {
        return isAddressNotNull(bacsRequestExportDTO) ?
                bacsRequestExportDTO.getAddressExportDTO().getCustomerAddressPostCode() : EMPTY_STRING;
    }

    protected String getCustomerReference(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCustomerReference() ==  null ? EMPTY_STRING : bacsRequestExportDTO.getCustomerReference();
    }

    protected String getCustomerVatRegistrationNumber(BACSRequestExportDTO bacsRequestExportDTO) {
         return EMPTY_STRING;
    }

    protected String getPaymentMethod(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getPaymentMethod();
    }

    protected boolean isAddressNotNull(BACSRequestExportDTO bacsRequestExportDTO) {
        return null != bacsRequestExportDTO.getAddressExportDTO();
    }

    protected String getCustomerBankAccount(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCustomerBnakAccount() == null ? EMPTY_STRING : bacsRequestExportDTO.getCustomerBnakAccount();
    }
    
    protected String getCustomerSortCode(BACSRequestExportDTO bacsRequestExportDTO) {
        return bacsRequestExportDTO.getCustomerSortCode() ==  null ? EMPTY_STRING : bacsRequestExportDTO.getCustomerSortCode();
    }
}
