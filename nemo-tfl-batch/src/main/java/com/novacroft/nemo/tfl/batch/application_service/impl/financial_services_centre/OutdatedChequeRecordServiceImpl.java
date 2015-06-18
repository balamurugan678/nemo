package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.OutdatedChequeRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToFloat;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToLong;

/**
 * Service to retrieve fields from outdated cheque records
 */
@Service("outdatedChequeRecordService")
public class OutdatedChequeRecordServiceImpl implements OutdatedChequeRecordService {
    public static final int REFERENCE_NUMBER_INDEX = 0;
    public static final int AMOUNT_INDEX = 1;
    public static final int CUSTOMER_NAME_INDEX = 2;
    public static final int CHEQUE_SERIAL_NUMBER_INDEX = 3;
    public static final int OUTDATED_ON_INDEX = 4;

    @Override
    public String getReferenceNumber(String[] record) {
        return record[REFERENCE_NUMBER_INDEX];
    }

    @Override
    public Long getReferenceNumberAsLong(String[] record) {
        return convertStringToLong(getReferenceNumber(record));
    }

    @Override
    public String getAmount(String[] record) {
        return record[AMOUNT_INDEX];
    }

    @Override
    public Float getAmountAsFloat(String[] record) {
        return convertStringToFloat(getAmount(record));
    }

    @Override
    public String getCustomerName(String[] record) {
        return record[CUSTOMER_NAME_INDEX];
    }

    @Override
    public String getChequeSerialNumber(String[] record) {
        return record[CHEQUE_SERIAL_NUMBER_INDEX];
    }

    @Override
    public Long getChequeSerialNumberAsLong(String[] record) {
        return convertStringToLong(getChequeSerialNumber(record));
    }

    @Override
    public String getOutdatedOn(String[] record) {
        return record[OUTDATED_ON_INDEX];
    }

    @Override
    public Date getOutdatedOnAsDate(String[] record) {
        return parse(getOutdatedOn(record), DateConstant.SHORT_DATE_PATTERN);
    }
}
