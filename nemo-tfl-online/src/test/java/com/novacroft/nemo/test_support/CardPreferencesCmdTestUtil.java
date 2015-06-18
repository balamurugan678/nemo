package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.ATTACHMENT_TYPE_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.CARD_PREFERENCES_ID_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.EMAIL_FREQUENCY_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.STATEMENT_TERMS_ACCEPTED_1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.STATION_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.EMAIL_ADDRESS_1;

import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;

/**
 * CardPreferencesCmd utilities to support unit tests
 */
public class CardPreferencesCmdTestUtil {

    public static CardPreferencesCmdImpl getTestCardPreferencesCmd1() {
        return getTestCardPreferencesCmd(CARD_PREFERENCES_ID_1, CARD_ID_1, STATION_ID_1, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1,
                STATEMENT_TERMS_ACCEPTED_1, EMAIL_ADDRESS_1);
    }

    public static CardPreferencesCmdImpl getTestCardPreferencesCmd(Long cardPreferencesId, Long cardId, Long stationId,
                                                                   String emailFrequency, String attachmentType,
                                                                   Boolean statementTermsAccepted, String emailAddress) {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setCardPreferencesId(cardPreferencesId);
        cmd.setCardId(cardId);
        cmd.setStationId(stationId);
        cmd.setEmailFrequency(emailFrequency);
        cmd.setAttachmentType(attachmentType);
        cmd.setStatementTermsAccepted(statementTermsAccepted);
        cmd.setEmailAddress(emailAddress);
        return cmd;
    }
}
