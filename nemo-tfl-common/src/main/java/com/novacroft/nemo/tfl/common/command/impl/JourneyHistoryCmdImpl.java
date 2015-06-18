package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import com.novacroft.nemo.tfl.common.command.JourneyHistoryCmd;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

/**
 * Command class for journey History
 */
public class JourneyHistoryCmdImpl implements JourneyHistoryCmd {

    protected Long cardId;
    protected String cardNumber;
    protected Integer weekNumberFromToday;
    protected Date startDate;
    protected Date endDate;
    protected String emailStatementPreference;
    protected JourneyHistoryDTO journeyHistory;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    
	public Integer getWeekNumberFromToday() {
		return weekNumberFromToday;
	}

	public void setWeekNumberFromToday(Integer weekNumberFromToday) {
		this.weekNumberFromToday = weekNumberFromToday;
	}

	public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmailStatementPreference() {
        return emailStatementPreference;
    }

    public void setEmailStatementPreference(String emailStatementPreference) {
        this.emailStatementPreference = emailStatementPreference;
    }
    
    public JourneyHistoryDTO getJourneyHistory() {
        return journeyHistory;
    }

    public void setJourneyHistory(JourneyHistoryDTO journeyHistory) {
        this.journeyHistory = journeyHistory;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    
}
