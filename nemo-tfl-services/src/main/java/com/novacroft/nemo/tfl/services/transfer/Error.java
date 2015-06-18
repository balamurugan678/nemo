package com.novacroft.nemo.tfl.services.transfer;

/**
 * 
 * This class is used by the ErrorResult class to hold details about any errors
 * returned by making a call to the web service for getting a customer's orders
 * and all items associated with those orders. The url to invoke the web service
 * is http://nemo-tfl-services/orders/customer/{customerId}
 * 
 */

public class Error {

	private Long id;
	private String field;
	private String description;

	public Error() {

	}

	public Error(Long id, String field, String description) {
		super();
		this.id = id;
		this.field = field;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
