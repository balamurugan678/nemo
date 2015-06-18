package com.novacroft.nemo.tfl.common.converter;

import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

import java.util.List;

/**
 * WebAccountCreditStatementLine Entity/DTO converter
 */
public interface WebCreditStatementLineConverter {
    WebCreditStatementLineDTO convertEntityToDto(Object[] row);

    List<WebCreditStatementLineDTO> convertEntityListToDtoList(List results);
}
