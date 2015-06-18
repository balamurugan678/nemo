package com.novacroft.nemo.mock_single_sign_on.domain;

public class SecurityQuestion
{

    private Long id;
    private String question;

    public SecurityQuestion(Long id, String question) {
        super();
        this.id = id;
        this.question = question;
    }

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