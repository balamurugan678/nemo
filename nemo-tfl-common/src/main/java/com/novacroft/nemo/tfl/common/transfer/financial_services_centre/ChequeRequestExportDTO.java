package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

/**
 * Transfer class to represent a record in the Financial Service Centre (FSC) cheque request export file
 */
public class ChequeRequestExportDTO extends BasePayeeExportDTO {
    public ChequeRequestExportDTO() {
    }

    public ChequeRequestExportDTO(Integer documentId, Date documentDate, Date postingDate, String documentType,
                                  String companyCode, String currency, String referenceNumber, String documentHeaderText,
                                  String accountType, Integer accountNumber, Date baselineDate, Float netAmount, String taxCode,
                                  String customerName, AddressExportDTO addressExportDTO, String paymentMethod) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.postingDate = postingDate;
        this.documentType = documentType;
        this.companyCode = companyCode;
        this.currency = currency;
        this.referenceNumber = referenceNumber;
        this.documentHeaderText = documentHeaderText;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.baselineDate = baselineDate;
        this.netAmount = netAmount;
        this.taxCode = taxCode;
        this.customerName = customerName;
        this.addressExportDTO = addressExportDTO;
        this.paymentMethod = paymentMethod;
    }

    public ChequeRequestExportDTO(Integer documentId, Date documentDate, Date postingDate, String documentType,
                                  String companyCode, String currency, String referenceNumber, String documentHeaderText,
                                  String accountType, Integer accountNumber, Date baselineDate, Float netAmount, String taxCode,
                                  String paymentMethod) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.postingDate = postingDate;
        this.documentType = documentType;
        this.companyCode = companyCode;
        this.currency = currency;
        this.referenceNumber = referenceNumber;
        this.documentHeaderText = documentHeaderText;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.baselineDate = baselineDate;
        this.netAmount = netAmount;
        this.taxCode = taxCode;
        this.paymentMethod = paymentMethod;
    }
}
