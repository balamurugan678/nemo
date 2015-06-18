package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;

public final class GoodwillPaymentTestUtil {

    public static final Long CARDID = 1L;
    private static final Long CARTID = 1L;
    private static final String CREATEDUSERID = "TEST";
    private static final Long ID = 1L;
    protected static final Integer PRICE = 1010;
    public static final Long CARDID1 = 5L;
    private static final Long CARTID1 = 5L;
    private static final String CREATEDUSERID1 = "TEST4";
    private static final Long ID1 = 5L;
    private static final Long ID2 = 2L;
    protected static final Integer PRICE1 = 1510;

    public static List<GoodwillPaymentItemDTO> getGoodwillPaymentItemDTOList1() {
        List<GoodwillPaymentItemDTO> list = new ArrayList<GoodwillPaymentItemDTO>();
        list.add(buildDTO(CARDID, CARTID, CREATEDUSERID, ID, PRICE));
        return list;
    }

    public static List<GoodwillPaymentItem> getGoodwillPaymentItemList1() {
        List<GoodwillPaymentItem> list = new ArrayList<GoodwillPaymentItem>();
        list.add(getGoodwillPaymentItem1());
        return list;
    }

    public static GoodwillPaymentItemDTO getGoodwillPaymentItemDTOItem1() {
        return buildDTO(CARDID, CARTID, CREATEDUSERID, ID, PRICE);
    }

    public static GoodwillPaymentItemDTO getGoodwillPaymentItemDTOItem2() {
        return buildDTO(CARDID1, CARTID1, CREATEDUSERID1, ID1, PRICE1);
    }

    public static GoodwillPaymentItemDTO getGoodwillPaymentItemDTOItem3() {
        return buildDTO(CARDID, CARTID, CREATEDUSERID, ID2, PRICE);
    }

    public static GoodwillPaymentItem getGoodwillPaymentItem1() {
        return buildEntity(CARDID, CARTID, CREATEDUSERID, ID, PRICE);
    }

    public static GoodwillPaymentItem getGoodwillPaymentItem2() {
        return buildEntity2(CARDID, CREATEDUSERID, new Date(), PRICE);
    }

    private static GoodwillPaymentItemDTO buildDTO(Long cardId, Long cartId, String createdUserId, Long id, Integer price) {
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO(1L, "Test");
        return new GoodwillPaymentItemDTO(cardId, createdUserId, id, price, true, goodwillReasonDTO);
    }

    private static GoodwillPaymentItem buildEntity(Long cardId, Long cartId, String createdUserId, Long id, Integer price) {
        return new GoodwillPaymentItem(cardId, createdUserId, id, price, true);
    }

    private static GoodwillPaymentItem buildEntity2(Long cardId, String createdUserId, Date createdDateTime, Integer price) {
        return new GoodwillPaymentItem(cardId, createdUserId, createdDateTime, price, true);
    }
}
