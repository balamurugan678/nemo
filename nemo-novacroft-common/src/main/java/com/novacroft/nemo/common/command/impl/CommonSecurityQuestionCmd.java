package com.novacroft.nemo.common.command.impl;

import com.novacroft.nemo.common.command.SecurityQuestionCmd;

/**
 * security question command (MVC "view") class common definition.
 */
public class CommonSecurityQuestionCmd implements SecurityQuestionCmd {
    protected Long cardId;
    protected String cardNumber;
    protected String securityQuestion;
    protected String securityAnswer;
    protected String confirmSecurityAnswer;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getConfirmSecurityAnswer() {
        return confirmSecurityAnswer;
    }

    public void setConfirmSecurityAnswer(String confirmSecurityAnswer) {
        this.confirmSecurityAnswer = confirmSecurityAnswer;
    }

}
