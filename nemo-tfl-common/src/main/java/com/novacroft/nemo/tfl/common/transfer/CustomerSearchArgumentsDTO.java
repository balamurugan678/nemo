package com.novacroft.nemo.tfl.common.transfer;

public class CustomerSearchArgumentsDTO {
    protected Boolean useExactMatch = Boolean.FALSE;
    protected Long customerId;
    protected String cardNumber;
    protected String firstName;
    protected String lastName;
    protected String postcode;
    protected String email;
    protected Integer firstResult;
    protected Integer maxResults;
    protected String userName;

    public CustomerSearchArgumentsDTO() {
    }

    public CustomerSearchArgumentsDTO(Boolean useExactMatch, Long customerId, String cardNumber, String firstName, String lastName, String postcode,
                    String email, String userName, Integer firstResult, Integer maxResults) {
        this.useExactMatch = useExactMatch;
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postcode = postcode;
        this.email = email;
        this.userName = userName;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    public Boolean getUseExactMatch() {
        return useExactMatch;
    }

    public void setUseExactMatch(Boolean useExactMatch) {
        this.useExactMatch = useExactMatch;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
