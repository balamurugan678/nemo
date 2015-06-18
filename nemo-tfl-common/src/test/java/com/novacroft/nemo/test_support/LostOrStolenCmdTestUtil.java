package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.HotlistReasonTypes;
import com.novacroft.nemo.tfl.common.constant.LostStolenOptionType;

/**
 * Utilities for tests that use LostOrStolenCmd
 */
public final class LostOrStolenCmdTestUtil {

    public static final Integer HOTLIST_REASON_ID_0 = 0;
    public static final Integer HOTLIST_REASON_ID_1 = 1;
    public static final String HOTLIST_REASON_DATE_1 = DateTestUtil.AUG_19;
        
    public static LostOrStolenCardCmdImpl getTestLostOrStolenCardCmdLostTransfer() {
        return getTestLostOrStolenCardCmd(CommonCardTestUtil.OYSTER_NUMBER_1, HotlistReasonTypes.LOST_CARD.getCode(), HOTLIST_REASON_DATE_1, LostStolenOptionType.TRANSFER_CARD.code());
    }
    
    public static LostOrStolenCardCmdImpl getTestLostOrStolenCardCmdLostRefund() {
        return getTestLostOrStolenCardCmd(CommonCardTestUtil.OYSTER_NUMBER_1, HotlistReasonTypes.LOST_CARD.getCode(), HOTLIST_REASON_DATE_1, LostStolenOptionType.REFUND_CARD.code());
    }

    public static LostOrStolenCardCmdImpl getTestLostOrStolenCardCmdStolenRefund() {
        return getTestLostOrStolenCardCmd(CommonCardTestUtil.OYSTER_NUMBER_1, HotlistReasonTypes.STOLEN_CARD.getCode(), HOTLIST_REASON_DATE_1, LostStolenOptionType.REFUND_CARD.code());
    }

    public static LostOrStolenCardCmdImpl getTestLostOrStolenCardCmd(String cardNumber, Integer hotlistReasonId, String hotlistDate, String lostStolenOptions) {
        LostOrStolenCardCmdImpl cmd = new LostOrStolenCardCmdImpl();
        cmd.setCardNumber(cardNumber);
        cmd.setHotlistReasonId(hotlistReasonId);
        cmd.setHotlistDateTime(hotlistDate);
        cmd.setLostStolenOptions(lostStolenOptions);
        return cmd;
    }

    private LostOrStolenCmdTestUtil() {
    }
}
