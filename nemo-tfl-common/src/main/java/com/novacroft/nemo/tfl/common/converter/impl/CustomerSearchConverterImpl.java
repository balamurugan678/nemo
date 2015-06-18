package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.converter.CustomerSearchConverter;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component(value = "customerSearchConverter")
public class CustomerSearchConverterImpl implements CustomerSearchConverter {

    protected final static int CUSTOMER_ID_INDEX = 0;
    protected final static int FIRST_NAME_INDEX = 1;
    protected final static int LAST_NAME_INDEX = 2;
    protected final static int OYSTER_NUMBER_INDEX = 3;
    protected final static int STATUS_INDEX = 4;
    protected final static int HOUSE_NAME_NUMBER_INDEX = 5;
    protected final static int STREET_INDEX = 6;
    protected final static int TOWN_INDEX = 7;
    protected final static int COUNTY_INDEX = 8;
    protected final static int COUNTRY_INDEX = 9;
    protected final static int POSTCODE_INDEX = 10;

    @Override
    public List<CustomerSearchResultDTO> convert(List<?> results) {
        List<CustomerSearchResultDTO> customerSearchResultDTOs = new ArrayList<CustomerSearchResultDTO>();
        for (Object result : results) {
            customerSearchResultDTOs.add(convert((Object[]) result));
        }
        return customerSearchResultDTOs;
    }

    protected CustomerSearchResultDTO convert(Object[] record) {
        return new CustomerSearchResultDTO(getCustomerId(record), getFirstName(record), getLastName(record),
                getOysterNumber(record), getStatus(record), getHouseNameNumber(record), getStreet(record), getTown(record),
                getCounty(record), getCountry(record), getPostcode(record));
    }

    protected Long getCustomerId(Object[] record) {
        return convertToLong(record[CUSTOMER_ID_INDEX]);
    }

    protected String getFirstName(Object[] record) {
        return convertToString(record[FIRST_NAME_INDEX]);
    }

    protected String getLastName(Object[] record) {
        return convertToString(record[LAST_NAME_INDEX]);
    }

    protected String getOysterNumber(Object[] record) {
        return convertToString(record[OYSTER_NUMBER_INDEX]);
    }

    protected String getStatus(Object[] record) {
        return convertToString(record[STATUS_INDEX]);
    }

    protected String getHouseNameNumber(Object[] record) {
        return convertToString(record[HOUSE_NAME_NUMBER_INDEX]);
    }

    protected String getStreet(Object[] record) {
        return convertToString(record[STREET_INDEX]);
    }

    protected String getTown(Object[] record) {
        return convertToString(record[TOWN_INDEX]);
    }

    protected String getCounty(Object[] record) {
        return convertToString(record[COUNTY_INDEX]);
    }

    protected String getCountry(Object[] record) {
        return convertToString(record[COUNTRY_INDEX]);
    }

    protected String getPostcode(Object[] record) {
        return convertToString(record[POSTCODE_INDEX]);
    }

    protected String convertToString(Object value) {
        return (null != value) ? (String) value : StringUtils.EMPTY;
    }

    protected Long convertToLong(Object value) {
        return (null != value) ? ((BigDecimal) value).longValueExact() : null;
    }
}
