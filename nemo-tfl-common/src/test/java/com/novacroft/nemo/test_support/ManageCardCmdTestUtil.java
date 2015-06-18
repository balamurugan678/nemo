package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.LostOrStolenCardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.ManageCardCmd;

/**
 * Utilities for tests that use LostOrStolenCmd
 */
public final class ManageCardCmdTestUtil {

    public static final Long CARD_ID_1 = CommonCardTestUtil.CARD_ID_1;
    public static final String CARD_NUMBER_1 = CommonCardTestUtil.OYSTER_NUMBER_1;
    public static final Long STATION_ID_1 = 507L;
    public static final Integer AUTO_TOP_UP_STATE_1 = 1;
    public static final Boolean AUTO_TOP_UP_ENABLED_1 = true;
    public static final Long CUSTOMER_ID_1 = CommonContactTestUtil.CONTACT_ID_1;
        
    public static ManageCardCmd getTestManageCardCmd1() {
        return getTestManageCardCmd(CARD_ID_1, CARD_NUMBER_1, STATION_ID_1, AUTO_TOP_UP_STATE_1, AUTO_TOP_UP_ENABLED_1, CUSTOMER_ID_1);
    }

    public static ManageCardCmd getTestManageCardCmd(Long cardId, String cardNumber, Long stationId, Integer autoTopupState, Boolean autoTopupEnabled, Long customerId) {
        ManageCardCmd cmd = new ManageCardCmd();
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardNumber);
        cmd.setStationId(stationId);
        cmd.setAutoTopUpState(autoTopupState);
        cmd.setAutoTopUpEnabled(autoTopupEnabled);
        cmd.setCustomerId(customerId);
        return cmd;
    }

    private ManageCardCmdTestUtil() {
    }
}
