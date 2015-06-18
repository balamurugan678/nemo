package com.novacroft.nemo.tfl.batch.application_service.impl.financial_services_centre;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.tfl.batch.application_service.financial_services_centre.BacsFailureRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToFloat;
import static com.novacroft.nemo.tfl.batch.util.ConvertUtil.convertStringToLong;

@Service(value = "bacsFailureRecordService")
public class BacsFailureRecordServiceImpl implements BacsFailureRecordService {
    public static final int FINANCIAL_SERVICES_REFERENCE = 0;
    public static final int PAYMENT_FAILURE_DATE = 3;
    public static final int AMOUNT_INDEX = 1;
    public static final int BACS_REJECT_CODE_INDEX = 2;

    @Override
    public String getAmount(String[] record) {
        return record[AMOUNT_INDEX];
    }

    @Override
    public String getFinancialServicesReferenceNumber(String[] record) {
        return record[FINANCIAL_SERVICES_REFERENCE];
    }

    @Override
    public Date getPaymentFailureDateAsDate(String[] record) {
        return parse(getPaymentFailureDate(record), DateConstant.SHORT_DATE_PATTERN);
    }

    @Override
    public Float getAmountAsFloat(String[] record) {
        final String amount = getAmount(record);
        return convertStringToFloat(amount);
    }

    @Override
    public String getPaymentFailureDate(String[] record) {
        return record[PAYMENT_FAILURE_DATE];
    }

    @Override
    public Long getFinancialServicesReferenceNumberAsLong(String[] record) {
        return convertStringToLong(getFinancialServicesReferenceNumber(record));
    }

    @Override
    public String getBacsRejectCode(String[] record) {
        return record[BACS_REJECT_CODE_INDEX];
    }
}
