package com.novacroft.nemo.mock_single_sign_on.domain;

public class SecurityAnswers
{
    private Long id;
    
    private String answer;

    private SecurityQuestion securityQuestion;


    public SecurityAnswers(Long id, String answer, com.novacroft.nemo.mock_single_sign_on.domain.SecurityQuestion securityQuestion) {
        super();
        this.id = id;
        this.answer = answer;
        this.securityQuestion = securityQuestion;
    }


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


    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }


    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

   
}