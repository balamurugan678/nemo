package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.ChequeSettlementRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToFloat;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToLong;

/**
 * Service to retrieve fields from cheque settlement records
 */
@Service("chequeSettlementRecordService")
public class ChequeSettlementRecordServiceImpl implements ChequeSettlementRecordService {
    public static final int CHEQUE_SERIAL_NUMBER_INDEX = 0;
    public static final int PAYMENT_REFERENCE_NUMBER_INDEX = 1;
    public static final int CUSTOMER_NAME_INDEX = 2;
    public static final int CLEARED_ON_INDEX = 3;
    public static final int CURRENCY_INDEX = 4;
    public static final int AMOUNT_INDEX = 5;

    @Override
    public String getChequeSerialNumber(String[] record) {
        return record[CHEQUE_SERIAL_NUMBER_INDEX];
    }

    @Override
    public Long getChequeSerialNumberAsLong(String[] record) {
        return convertStringToLong(getChequeSerialNumber(record));
    }

    @Override
    public String getPaymentReferenceNumber(String[] record) {
        return record[PAYMENT_REFERENCE_NUMBER_INDEX];
    }

    @Override
    public Long getPaymentReferenceNumberAsLong(String[] record) {
        return convertStringToLong(getPaymentReferenceNumber(record));
    }

    @Override
    public String getCustomerName(String[] record) {
        return record[CUSTOMER_NAME_INDEX];
    }

    @Override
    public String getClearedOn(String[] record) {
        return record[CLEARED_ON_INDEX];
    }

    @Override
    public Date getClearedOnAsDate(String[] record) {
        return parse(getClearedOn(record), DateConstant.SHORT_DATE_PATTERN);
    }

    @Override
    public String getCurrency(String[] record) {
        return record[CURRENCY_INDEX];
    }

    @Override
    public String getAmount(String[] record) {
        return record[AMOUNT_INDEX];
    }

    @Override
    public Float getAmountAsFloat(String[] record) {
        return convertStringToFloat(getAmount(record)) * -1;
    }
}
