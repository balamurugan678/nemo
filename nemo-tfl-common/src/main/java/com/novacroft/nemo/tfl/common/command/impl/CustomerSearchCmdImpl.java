package com.novacroft.nemo.tfl.common.command.impl;

import static com.novacroft.nemo.tfl.common.constant.CustomerSearchConstant.DEFAULT_END_COUNT;
import static com.novacroft.nemo.tfl.common.constant.CustomerSearchConstant.DEFAULT_RESULT_LENGTH;
import static com.novacroft.nemo.tfl.common.constant.CustomerSearchConstant.DEFAULT_START_COUNT;

/**
 * Command class for Innovator Customer search criteria
 */
public class CustomerSearchCmdImpl {

    protected Long customerId;
    protected String cardNumber;
    protected String emailAddress;
    protected String firstName;
    protected String lastName;
    protected String postcode;
    protected String exact;
    protected Integer resultLength = DEFAULT_RESULT_LENGTH;
    protected Integer startCount = DEFAULT_START_COUNT;
    protected Integer endCount = DEFAULT_END_COUNT;
    protected Long cardId;
    protected Long orderNumber;
    protected String userName;

    public CustomerSearchCmdImpl() {

    }

    public CustomerSearchCmdImpl(Long customerId, String cardNumber, String email, String firstName, String lastName, String postcode, String exact,
                    Integer resultLength, Integer startCount, Integer endCount, Long cardId) {
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.emailAddress = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postcode = postcode;
        this.exact = exact;
        this.resultLength = resultLength;
        this.startCount = startCount;
        this.endCount = endCount;
        this.cardId = cardId;
    }

    public CustomerSearchCmdImpl(Long customerId, String cardNumber, String email, String firstName, String lastName, String postcode, String exact,
                    Integer resultLength, Integer startCount, Integer endCount, Long cardId, String userName) {
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.emailAddress = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postcode = postcode;
        this.exact = exact;
        this.resultLength = resultLength;
        this.startCount = startCount;
        this.endCount = endCount;
        this.cardId = cardId;
        this.userName = userName;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
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

    public String getExact() {
        return exact;
    }

    public void setExact(String exact) {
        this.exact = exact;
    }

    public Integer getResultLength() {
        return resultLength;
    }

    public void setResultLength(Integer resultLength) {
        this.resultLength = resultLength;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
