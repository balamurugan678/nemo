package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

public class SingleSignOnSecurityQuestionDTO {

    private Long id;
    private String question;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
