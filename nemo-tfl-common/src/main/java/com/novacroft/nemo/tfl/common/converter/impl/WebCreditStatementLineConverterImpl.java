package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.converter.WebCreditStatementLineConverter;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * WebAccountCreditStatementLine Entity/DTO converter
 */
@Component("webCreditStatementLineConverter")
public class WebCreditStatementLineConverterImpl implements WebCreditStatementLineConverter {
    protected static final Logger logger = LoggerFactory.getLogger(WebCreditStatementLineConverterImpl.class);
    protected static final int SETTLEMENT_ID_INDEX = 0;
    protected static final int SETTLEMENT_STATUS_INDEX = 1;
    protected static final int SETTLEMENT_DATE_INDEX = 2;
    protected static final int SETTLEMENT_AMOUNT_INDEX = 3;
    protected static final int ORDER_ID_INDEX = 4;
    protected static final int ORDER_NUMBER_INDEX = 5;
    protected static final int ORDER_DATE_INDEX = 6;
    protected static final int ORDER_TOTAL_AMOUNT_INDEX = 7;
    protected static final int ORDER_STATUS_INDEX = 8;

    @Override
    public WebCreditStatementLineDTO convertEntityToDto(Object[] row) {
        return new WebCreditStatementLineDTO((Long) row[SETTLEMENT_ID_INDEX], (String) row[SETTLEMENT_STATUS_INDEX],
                (Date) row[SETTLEMENT_DATE_INDEX], (Integer) row[SETTLEMENT_AMOUNT_INDEX], (Long) row[ORDER_ID_INDEX],
                (Long) row[ORDER_NUMBER_INDEX], (Date) row[ORDER_DATE_INDEX], (Integer) row[ORDER_TOTAL_AMOUNT_INDEX],
                (String) row[ORDER_STATUS_INDEX]);
    }

    @Override
    public List<WebCreditStatementLineDTO> convertEntityListToDtoList(List entityList) {
        List<WebCreditStatementLineDTO> dtoList = new ArrayList<WebCreditStatementLineDTO>();
        Iterator entityListIterator = entityList.iterator();
        while (entityListIterator.hasNext()) {
            Object[] row = (Object[]) entityListIterator.next();
            dtoList.add(convertEntityToDto(row));
        }
        return dtoList;
    }
}
