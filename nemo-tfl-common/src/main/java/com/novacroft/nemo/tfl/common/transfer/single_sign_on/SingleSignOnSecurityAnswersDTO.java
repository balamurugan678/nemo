package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

public class SingleSignOnSecurityAnswersDTO {
    private Long id;
    private String answer;
    private SingleSignOnSecurityQuestionDTO securityQuestion;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public SingleSignOnSecurityQuestionDTO getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SingleSignOnSecurityQuestionDTO securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    
}
