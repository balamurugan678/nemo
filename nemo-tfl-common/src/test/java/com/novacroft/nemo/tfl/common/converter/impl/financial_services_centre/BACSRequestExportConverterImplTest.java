package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import static org.junit.Assert.*;
import static com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant.*;

import java.util.Date;

import org.junit.Test;

import com.novacroft.nemo.tfl.common.constant.financial_services_centre.ExportFileConstant;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSRequestExportDTO;


public class BACSRequestExportConverterImplTest {
    
    
   protected static final String CUSTOMER_BANK_ACCOUNT = "11111111";

protected static final String SORT_CODE = "400000";

protected static final String PREFIX = "prefix";

private static final String CUSTOMER_ADDRESS_LINE1 = "customerAddressLine1";

protected static final String CUSTOMER_ADDRESS_LINE2 = "customerAddressLine2";

protected static final String CUSTOMER_ADDRESS_STREET = "customerAddressStreet";

protected static final String ADDRESS_TOWN = "northampTon";

protected static final String POST_CODE = "NN3 6UR";

AddressExportDTO  addressExportDTO = new AddressExportDTO(CUSTOMER_ADDRESS_LINE1, CUSTOMER_ADDRESS_LINE2, CUSTOMER_ADDRESS_STREET, ADDRESS_TOWN, POST_CODE);
    
   Date documentDate = new Date();
   Date postingDate  = new Date();
   String documentType = "BO";
   Integer accountNumber = 88888888;
   String referenceNumber = "999";
   String documentHeaderText = "HEADER";
   Date baselineDate       = new Date();
   String accountType     = ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_VENDOR_ACCOUNT_TYPE;
   String currency        = "GBP";
   String companyCode     = "1004";
   Float netAmount        =  new Float("3333");
   
   @Test
   public void shouldConvert(){
       
    BACSRequestExportDTO bacsRequestExportDTO = new BACSRequestExportDTO(1, documentDate, postingDate  , documentType , companyCode, currency, referenceNumber, documentHeaderText, accountType, accountNumber, baselineDate, netAmount, "V3", "customerName", addressExportDTO, ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD);
    BACSRequestExportConverterImpl bacsRequestExportConverterImpl = new BACSRequestExportConverterImpl();
    assertTrue(bacsRequestExportConverterImpl.convert(bacsRequestExportDTO).length == 39);
   }
   
   @Test
   public void shouldReturnValuesBetween34to39(){
       
    BACSRequestExportDTO bacsRequestExportDTO = new BACSRequestExportDTO(1, documentDate, postingDate  , documentType , companyCode, currency, referenceNumber, documentHeaderText, accountType, accountNumber, baselineDate, netAmount, "V3", "customerName", addressExportDTO, ExportFileConstant.FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD);
    bacsRequestExportDTO.setCustomerReference(PREFIX + referenceNumber);
    bacsRequestExportDTO.setCustomerSortCode(SORT_CODE); 
    bacsRequestExportDTO.setCustomerBnakAccount(CUSTOMER_BANK_ACCOUNT);
    BACSRequestExportConverterImpl bacsRequestExportConverterImpl = new BACSRequestExportConverterImpl();
    String[] valuesArray = bacsRequestExportConverterImpl.convert(bacsRequestExportDTO);

    assertEquals(POST_CODE, valuesArray[33]);
    
    assertEquals(CUSTOMER_BANK_ACCOUNT, valuesArray[34]);
    
 
    
    assertEquals(PREFIX + referenceNumber, valuesArray[35]);
    
    assertEquals(SORT_CODE, valuesArray[36]);
    assertEquals(FINANCIAL_SERVICES_CENTRE_EXPORT_FILE_BACS_PAYMENT_METHOD, valuesArray[38]);
    
   }
}
