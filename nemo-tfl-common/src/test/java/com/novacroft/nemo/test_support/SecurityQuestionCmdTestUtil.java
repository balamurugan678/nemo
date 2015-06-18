package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;

/**
 * Utilities for security question tests
 */
public final class SecurityQuestionCmdTestUtil {

    public static final Long CARD_ID_1 = 8L;
    public static final String CARD_NUMBER_1 = "123456789101";
    public static final String SECURITY_QUESTION_1 = "test-question-1";
    public static final String SECURITY_ANSWER_1 = "test-answer-1";
    public static final String CONFIRM_SECURITY_ANSWER_1 = "test-confirm-answer-1";
    public static final String SECURITY_QUESTION_PLACE = "test-question-1";
    public static final String SECURITY_QUESTION_MEMORABLE_PLACE = "memorableplace";
    public static final String SECURITY_ANSWER_ALPHABETIC = "Clacton";
    public static final String CONFIRM_SECURITY_ANSWER_ALPHABETIC = "Clacton";
    public static final String SECURITY_ANSWER_NOT_ALPHABETIC = "Spl0tt";
    public static final String CONFIRM_SECURITY_ANSWER_NOT_ALPHABETIC = "Spl0tt";
    public static final String SECURITY_ANSWER_NOT_ALPHABETIC_NOT_MINIMUM_LENGTH = "X";
    public static final String CONFIRM_SECURITY_ANSWER_NOT_ALPHABETIC_NOT_MINIMUM_LENGTH = "X";

    public static SecurityQuestionCmdImpl getTestSecurityQuestionCmd1() {
        return getTestSecurityQuestionCmd(CARD_ID_1, CARD_NUMBER_1, SECURITY_QUESTION_1, SECURITY_ANSWER_1,
                CONFIRM_SECURITY_ANSWER_1);
    }

    public static SecurityQuestionCmdImpl getTestSecurityQuestionCmdMemorablePlaceAlphabetic() {
        return getTestSecurityQuestionCmd(CARD_ID_1, CARD_NUMBER_1, SECURITY_QUESTION_MEMORABLE_PLACE, SECURITY_ANSWER_ALPHABETIC,
                        CONFIRM_SECURITY_ANSWER_ALPHABETIC);
    }

    public static SecurityQuestionCmdImpl getTestSecurityQuestionCmdMemorablePlaceNotAlphabetic() {
        return getTestSecurityQuestionCmd(CARD_ID_1, CARD_NUMBER_1, SECURITY_QUESTION_MEMORABLE_PLACE, SECURITY_ANSWER_NOT_ALPHABETIC,
                        CONFIRM_SECURITY_ANSWER_NOT_ALPHABETIC);
    }

    public static SecurityQuestionCmdImpl getTestSecurityQuestionCmdMemorablePlaceNotAlphabeticAndNotMinimumFieldlength() {
        return getTestSecurityQuestionCmd(CARD_ID_1, CARD_NUMBER_1, SECURITY_QUESTION_MEMORABLE_PLACE, SECURITY_ANSWER_NOT_ALPHABETIC_NOT_MINIMUM_LENGTH,
                        CONFIRM_SECURITY_ANSWER_NOT_ALPHABETIC_NOT_MINIMUM_LENGTH);
    }

    public static SecurityQuestionCmdImpl getTestSecurityQuestionCmd(Long cardId, String cardNumber, String securityQuestion,
                                                                     String securityAnswer, String confirmSecurityAnswer) {
        SecurityQuestionCmdImpl cmd = new SecurityQuestionCmdImpl();
        cmd.setCardId(cardId);
        cmd.setCardNumber(cardNumber);
        cmd.setSecurityQuestion(securityQuestion);
        cmd.setSecurityAnswer(securityAnswer);
        cmd.setConfirmSecurityAnswer(confirmSecurityAnswer);
        return cmd;
    }
}
