package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.domain.CardPreferences;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.EMAIL_ADDRESS_1;

/**
 * Utilities for Card Preferences tests
 */
public final class CardPreferencesTestUtil {

    public static final Long CARD_PREFERENCES_ID_1 = 4L;
    public static final Long STATION_ID_1 = 16L;
    public static final String EMAIL_FREQUENCY_1 = "Weekly";
    public static final String ATTACHMENT_TYPE_1 = "pdf";
    public static final Boolean STATEMENT_TERMS_ACCEPTED_1 = Boolean.FALSE;
    public static final String STATION_ID = "16";
    public static final String PENDING_STATION_ID = "450";
    public static final String PENDING_STATION_ID_1 = "567";
    
    public static CardPreferencesDTO getTestCardPreferencesDTO1() {
        return getTestCardPreferencesDTO(CARD_PREFERENCES_ID_1, CARD_ID_1, STATION_ID_1, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1,
                STATEMENT_TERMS_ACCEPTED_1);
    }

    public static CardPreferencesDTO getTestCardPreferencesDTOWithNullStationId() {
        return getTestCardPreferencesDTO(CARD_PREFERENCES_ID_1, CARD_ID_1, null, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1,
                STATEMENT_TERMS_ACCEPTED_1);
    }

    public static List<CardPreferencesDTO> getTestCardPreferencesDTOList1() {
        List<CardPreferencesDTO> list = new ArrayList<CardPreferencesDTO>();
        list.add(getTestCardPreferencesDTO1());
        return list;
    }

    public static CardPreferencesDTO getTestCardPreferencesDTO(Long id, Long cardId, Long stationId, String emailFrequency,
                                                               String attachmentType, Boolean statementTermsAccepted) {
        CardPreferencesDTO cardPreferencesDTO = new CardPreferencesDTO();
        cardPreferencesDTO.setId(id);
        cardPreferencesDTO.setCardId(cardId);
        cardPreferencesDTO.setEmailFrequency(emailFrequency);
        cardPreferencesDTO.setAttachmentType(attachmentType);
        cardPreferencesDTO.setStationId(stationId);
        cardPreferencesDTO.setStatementTermsAccepted(statementTermsAccepted);
        return cardPreferencesDTO;
    }

    public static CardPreferencesCmdImpl getTestCardPreferencesCmd1() {
        return getTestCardPreferencesCmd(CARD_ID_1, STATION_ID_1, EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1,
                STATEMENT_TERMS_ACCEPTED_1, EMAIL_ADDRESS_1, CARD_PREFERENCES_ID_1);
    }

    public static CardPreferencesCmdImpl getTestCardPreferencesCmd(Long cardId, Long stationId, String emailFrequency,
                                                                   String attachmentType, Boolean statementTermsAccepted,
                                                                   String emailAddress, Long cardPreferencesId) {
        CardPreferencesCmdImpl cmd = new CardPreferencesCmdImpl();
        cmd.setCardId(cardId);
        cmd.setStationId(stationId);
        cmd.setEmailFrequency(emailFrequency);
        cmd.setAttachmentType(attachmentType);
        cmd.setStatementTermsAccepted(statementTermsAccepted);
        cmd.setEmailAddress(emailAddress);
        cmd.setCardPreferencesId(cardPreferencesId);
        return cmd;
    }
    
    private static CardPreferences getTestCardPreferences(Long id, Long cardId, Long stationId, 
                    String emailFrequency, String attachmentType, Boolean statementTermsAccepted) {
        CardPreferences cardPreferences = new CardPreferences();
        cardPreferences.setId(id);
        cardPreferences.setCardId(cardId);
        cardPreferences.setStationId(stationId);
        cardPreferences.setEmailFrequency(emailFrequency);
        cardPreferences.setAttachmentType(attachmentType);
        cardPreferences.setStatementTermsAccepted(statementTermsAccepted);
        return cardPreferences;
    }
    
    public static CardPreferences getTestCardPreferences1() {
        return getTestCardPreferences(CARD_PREFERENCES_ID_1, CARD_ID_1, STATION_ID_1, 
                        EMAIL_FREQUENCY_1, ATTACHMENT_TYPE_1, STATEMENT_TERMS_ACCEPTED_1);
    }
}
