package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPenceToPounds;
import static com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD;
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
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.BACSRequestExportConverter;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.FileExportLogDataService;
import com.novacroft.nemo.tfl.common.transfer.FileExportLogDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSRequestExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;

/**
 * Financial Services Centre (FSC) bacs request file service
 */
@Service(value = "bacsRequestFileGeneratorService")
public class BacsRequestFileGeneratorServiceImpl extends PayRquestFileGeneratorService implements ExportFileGenerator {

	   @Autowired
	    protected NemoUserContext nemoUserContext;
	    @Autowired
	    protected FileExportLogDataService fileExportLogDataService;
	    @Autowired
	    protected BACSSettlementDataService bascsSettlementDataService;
	    @Autowired
	    protected BACSRequestExportConverter bacsRequestExportConverter;
	    @Transactional
	    @Override
	    public byte[] generateExportFile(String fileName) {
	        FileExportLogDTO fileExportLogDTO = createFileExportLog(fileName);
	        List<BACSSettlementDTO> chequeSettlementDTOs = getBACSSettlementsToExport();
	        markSettlementsAsExported(chequeSettlementDTOs, fileExportLogDTO);
	        List<BACSRequestExportDTO> exportRecords = transformSettlementsForExport(chequeSettlementDTOs, fileName);
	        return prepareCsv(exportRecords);
	    }

	    protected FileExportLogDTO createFileExportLog(String fileName) {
	        FileExportLogDTO fileExportLogDTO = new FileExportLogDTO(fileName, this.nemoUserContext.getUserName());
	        return this.fileExportLogDataService.createOrUpdate(fileExportLogDTO);
	    }

	    protected  List<BACSSettlementDTO> getBACSSettlementsToExport() {
	        return this.bascsSettlementDataService.findByStatus(SettlementStatus.NEW.code());
	    }

	    protected void markSettlementsAsExported(List<BACSSettlementDTO> settlementDTOList,
	                                             FileExportLogDTO fileExportLogDTO) {
	        for (BACSSettlementDTO  bacsSettlementDTO : settlementDTOList) {
	        	bacsSettlementDTO.setStatus(SettlementStatus.REQUESTED.code());
	        	bacsSettlementDTO.setFileExportLogId(fileExportLogDTO.getId());
	            this.bascsSettlementDataService.createOrUpdate(bacsSettlementDTO);
	        }
	    }

	    protected List<BACSRequestExportDTO> transformSettlementsForExport(List<BACSSettlementDTO> bacsSettlementList,
	                                                                         String fileName) {
	        Date now = new Date();
	        String documentType = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_DOCUMENT_TYPE.code());
	        String companyCode = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_COMPANY_CODE.code());
	        String currency = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_CURRENCY.code());
	        Integer vendorAccountNumber = getIntegerParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_VENDOR_ACCOUNT_NUMBER.code());
	        Integer glAccountNumber = getIntegerParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_GL_ACCOUNT_NUMBER.code());
	        String taxCode = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_TAX_CODE.code());
	        String payeeReferencePrefix = getStringParameter(SystemParameterCode.FSC_EXPORT_FILE_BACS_REQUEST_PAYEE_REF_PREFIX.code());

	        List<BACSRequestExportDTO> bascRequestDTOList = new ArrayList<BACSRequestExportDTO>();
	        int documentId = 1;
	        for (BACSSettlementDTO bascSettlementDTO : bacsSettlementList) {

	            final BACSRequestExportDTO bacsRequestExportDTO = new BACSRequestExportDTO(documentId, now, now, documentType, companyCode, currency,
	                    String.valueOf(bascSettlementDTO.getSettlementNumber()), fileName,
	                    FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_VENDOR_ACCOUNT_TYPE, vendorAccountNumber, now,
	                    convertPenceToPounds(bascSettlementDTO.getAmount()), taxCode, bascSettlementDTO.getPayeeName(),
	                    transformAddressForExport(bascSettlementDTO),
	                    FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD);
	            bacsRequestExportDTO.setCustomerReference(payeeReferencePrefix + bascSettlementDTO.getId());
	            bacsRequestExportDTO.setCustomerSortCode(bascSettlementDTO.getSortCode());
	            bacsRequestExportDTO.setCustomerBnakAccount(bascSettlementDTO.getBankAccount());
	            bascRequestDTOList.add(bacsRequestExportDTO);
	            
	            final BACSRequestExportDTO glAccountTO = new BACSRequestExportDTO(documentId, now, now, documentType, companyCode, currency,
	                    String.valueOf(bascSettlementDTO.getSettlementNumber()), fileName, FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_GL_ACCOUNT_TYPE,
	                    glAccountNumber, now, convertPenceToPounds(bascSettlementDTO.getAmount()) * -1, taxCode,
	                    FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD);

	            bascRequestDTOList.add(glAccountTO);
	            documentId++;
	        }
	        return bascRequestDTOList;
	    }

	    protected byte[] prepareCsv(final List<BACSRequestExportDTO> bacsRequestExportDTOList) {
	        StringWriter stringWriter = new StringWriter();
	        CSVWriter csvWriter =
	                new CSVWriter(stringWriter, DEFAULT_SEPARATOR, NO_ESCAPE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, "\r\n");
	        for (BACSRequestExportDTO bascRequestExportDTO : bacsRequestExportDTOList) {
	            csvWriter.writeNext(this.bacsRequestExportConverter.convert(bascRequestExportDTO));
	        }
	        return stringWriter.toString().getBytes();
	    }
}
