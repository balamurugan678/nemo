package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_CHEQUE_PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_GL_ACCOUNT_TYPE;
import static com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_VENDOR_ACCOUNT_TYPE;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVWriter;

import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.application_service.financial_services_centre.ExportFileGenerator;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.ChequeRequestExportConverter;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeRequestExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

/**
 * Financial Services Centre (FSC) cheque request file service
 */
@Service("chequeRequestFileGeneratorService")
public class ChequeRequestFileGeneratorServiceImpl extends PayRquestFileGeneratorService implements ExportFileGenerator {

    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired
    protected FileExportLogDataService fileExportLogDataService;
    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected ChequeRequestExportConverter chequeRequestExportConverter;
   

    @Transactional
    @Override
    public byte[] generateExportFile(String fileName) {
        FileExportLogDTO fileExportLogDTO = createFileExportLog(fileName);
        List<ChequeSettlementDTO> chequeSettlementDTOs = getChequeSettlementsToExport();
        markSettlementsAsExported(chequeSettlementDTOs, fileExportLogDTO);
        List<ChequeRequestExportDTO> exportRecords = transformSettlementsForExport(chequeSettlementDTOs, fileName);
        return prepareCsv(exportRecords);
    }

    protected FileExportLogDTO createFileExportLog(String fileName) {
        FileExportLogDTO fileExportLogDTO = new FileExportLogDTO(fileName, this.nemoUserContext.getUserName());
        return this.fileExportLogDataService.createOrUpdate(fileExportLogDTO);
    }

    protected List<ChequeSettlementDTO> getChequeSettlementsToExport() {
        return this.chequeSettlementDataService.findByStatus(SettlementStatus.NEW.code());
    }

    protected void markSettlementsAsExported(List<ChequeSettlementDTO> chequeSettlementDTOs,
                                             FileExportLogDTO fileExportLogDTO) {
        for (ChequeSettlementDTO chequeSettlementDTO : chequeSettlementDTOs) {
            chequeSettlementDTO.setStatus(SettlementStatus.REQUESTED.code());
            chequeSettlementDTO.setFileExportLogId(fileExportLogDTO.getId());
            this.chequeSettlementDataService.createOrUpdate(chequeSettlementDTO);
        }
    }

    protected List<ChequeRequestExportDTO> transformSettlementsForExport(List<ChequeSettlementDTO> chequeSettlementDTOs,
                                                                         String fileName) {
        Date now = new Date();
        String documentType = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_DOCUMENT_TYPE.code());
        String companyCode = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_COMPANY_CODE.code());
        String currency = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_CURRENCY.code());
        Integer vendorAccountNumber = getIntegerParameter(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_VENDOR_ACCOUNT_NUMBER.code());
        Integer generalLedgerlAccountNumber = getIntegerParameter(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_GL_ACCOUNT_NUMBER.code());
        String taxCode = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_CHEQUE_REQUEST_TAX_CODE.code());

        List<ChequeRequestExportDTO> chequeRequestExportDTOs = new ArrayList<ChequeRequestExportDTO>();
        int documentId = 1;
        for (ChequeSettlementDTO chequeSettlementDTO : chequeSettlementDTOs) {


            chequeRequestExportDTOs.add(new ChequeRequestExportDTO(documentId, now, now, documentType, companyCode, currency,
                    String.valueOf(chequeSettlementDTO.getSettlementNumber()), fileName,
                    FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_VENDOR_ACCOUNT_TYPE, vendorAccountNumber, now,
                    convertPenceToPounds(chequeSettlementDTO.getAmount()), taxCode, chequeSettlementDTO.getPayeeName(),
                    transformAddressForExport(chequeSettlementDTO),
                    FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_CHEQUE_PAYMENT_METHOD));
            
            chequeRequestExportDTOs.add(new ChequeRequestExportDTO(documentId, now, now, documentType, companyCode, currency,
                    String.valueOf(chequeSettlementDTO.getSettlementNumber()), fileName, FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_GL_ACCOUNT_TYPE,
                    generalLedgerlAccountNumber, now, convertPenceToPounds(chequeSettlementDTO.getAmount())* -1, taxCode,
                    EMPTY_STRING));
            documentId++;
        }
        return chequeRequestExportDTOs;
    }

    protected byte[] prepareCsv(List<ChequeRequestExportDTO> chequeRequestExportDTOs) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter =
                new CSVWriter(stringWriter, DEFAULT_SEPARATOR, NO_ESCAPE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, "\r\n");
        for (ChequeRequestExportDTO chequeRequestExportDTO : chequeRequestExportDTOs) {
            csvWriter.writeNext(this.chequeRequestExportConverter.convert(chequeRequestExportDTO));
        }
        return stringWriter.toString().getBytes();
    }
   
}
