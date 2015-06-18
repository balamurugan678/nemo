package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.domain.Settlement;
import com.novacroft.nemo.tfl.common.domain.WebAccountCreditSettlement;
import com.novacroft.nemo.tfl.common.transfer.SettlementDTO;

import org.junit.Test;

import static com.novacroft.nemo.test_support.WebAccountCreditSettlementTestUtil.getTestWebAccountCreditSettlement1;
import static com.novacroft.nemo.test_support.WebAccountCreditSettlementTestUtil.getTestWebAccountCreditSettlementDTO1;
import static org.junit.Assert.assertEquals;

/**
 * WebAccountCreditSettlementConverter unit tests
 */
public class WebCreditSettlementConverterImplTest {

    @Test
    public void shouldConvertEntityToDto() {
        WebCreditSettlementConverterImpl converter = new WebCreditSettlementConverterImpl();
        SettlementDTO result = converter.convertEntityToDto(getTestWebAccountCreditSettlement1());
        assertEquals(getTestWebAccountCreditSettlementDTO1().getOrderId(), result.getOrderId());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        SettlementConverterImpl converter = new SettlementConverterImpl();
        SettlementDTO settlementDTO = getTestWebAccountCreditSettlementDTO1();
        Settlement result =
                converter.convertDtoToEntity(settlementDTO, new WebAccountCreditSettlement());
        assertEquals(getTestWebAccountCreditSettlement1().getOrderId(), result.getOrderId());
    }
}
