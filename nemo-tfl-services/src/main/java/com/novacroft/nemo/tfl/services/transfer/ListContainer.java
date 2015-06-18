package com.novacroft.nemo.tfl.services.transfer;

import java.util.List;

public class ListContainer {

	private Integer id;
	private String name;
	private List<ListOption> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ListOption> getItems() {
		return items;
	}
	public void setItems(List<ListOption> items) {
		this.items = items;
	}
	
}
