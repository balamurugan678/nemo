package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsSettlementRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToFloat;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToLong;

/**
 * Service to retrieve fields from cheque settlement records
 */
@Service("bacsSettlementRecordService")
public class BacsSettlementRecordServiceImpl implements BacsSettlementRecordService {
    public static final int PAYMENT_REFERENCE_NUMBER_INDEX = 0;
    public static final int CUSTOMER_NAME_INDEX = 2;
    public static final int FINANCIAL_SERVICES_REFERENCE = 3;
    public static final int PAYMENT_DATE = 4;
    public static final int AMOUNT_INDEX = 1;

    @Override
    public String getPaymentReferenceNumber(String[] record) {
        return record[PAYMENT_REFERENCE_NUMBER_INDEX];
    }

    @Override
    public String getAmount(String[] record) {
        return record[AMOUNT_INDEX];
    }

    @Override
    public String getCustomerName(String[] record) {
        return record[CUSTOMER_NAME_INDEX];
    }

    @Override
    public String getFinancialServicesReferenceNumber(String[] record) {
        return record[FINANCIAL_SERVICES_REFERENCE];
    }

    @Override
    public String getPaymentDate(String[] record) {
        return record[PAYMENT_DATE];
    }

    @Override
    public Long getPaymentReferenceNumberAsLong(String[] record) {
        return convertStringToLong(getPaymentReferenceNumber(record));
    }

    @Override
    public Date getPaymentDateAsDate(String[] record) {
        return parse(getPaymentDate(record), DateConstant.SHORT_DATE_PATTERN);
    }

    @Override
    public Float getAmountAsFloat(String[] record) {
        final String amount = getAmount(record);
        return convertStringToFloat(amount);
    }

    @Override
    public Long getFinancialServicesReferenceNumberAsLong(String[] record) {
        return convertStringToLong(getFinancialServicesReferenceNumber(record));
    }
}
