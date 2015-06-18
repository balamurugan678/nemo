package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.domain.PayAsYouGo;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoDTO;

/**
 * Utilities for pay as you go product item tests
 */
public final class PayAsYouGoTestUtil {
    public static final String PAY_AS_YOU_GO_NAME_1 = "Pay as you go credit";
    public static final Integer TICKET_PAY_AS_YOU_GO_PRICE_1 = 1000;
    public static final Integer TICKET_PAY_AS_YOU_GO_PRICE_ZERO = 0;
    public static final Integer NEGATIVE_PAY_AS_YOU_GO_PRICE = -1000;
    public static final Long EXTERNAL_ID_1 = 1000L;
    public static final Long ID_1 = 1L;
    
    public static PayAsYouGoDTO getTestPayAsYouGoDTO1() {
        return getTestPayAsYouGoDTO(PAY_AS_YOU_GO_NAME_1, TICKET_PAY_AS_YOU_GO_PRICE_1);
    }

    public static PayAsYouGoDTO getTestPayAsYouGoDTO(String payAsYouGoName, Integer ticketPrice) {
        PayAsYouGoDTO dto = new PayAsYouGoDTO();
        dto.setId(ID_1);
        dto.setExternalId(EXTERNAL_ID_1);
        dto.setPayAsYouGoName(payAsYouGoName);
        dto.setTicketPrice(ticketPrice);
        return dto;
    }

    public static PayAsYouGo getTestPayAsYouGo1() {
        return getTestPayAsYouGo(PAY_AS_YOU_GO_NAME_1, TICKET_PAY_AS_YOU_GO_PRICE_1);
    }

    public static PayAsYouGo getTestPayAsYouGo(String payAsYouGoName, Integer ticketPrice) {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setId(ID_1);
        payAsYouGo.setExternalId(EXTERNAL_ID_1);
        payAsYouGo.setPayAsYouGoName(payAsYouGoName);
        payAsYouGo.setTicketPrice(ticketPrice);
        return payAsYouGo;
    }

}
