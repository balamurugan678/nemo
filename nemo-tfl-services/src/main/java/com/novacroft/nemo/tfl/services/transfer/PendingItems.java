package com.novacroft.nemo.tfl.services.transfer;

import java.util.List;

public class PendingItems {

	private List<PrePayValue> prePayValues;
	private List<Ticket> tickets;
	
	public List<PrePayValue> getPrePayValues() {
		return prePayValues;
	}

	public void setPrePayValues(List<PrePayValue> prePayValues) {
		this.prePayValues = prePayValues;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
