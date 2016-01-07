package com.briargate.shipmanager;

public class PacklistItem {
	private String upc, title;
	private Integer quantity;
	
	public PacklistItem(String upc, String title, String quantity) {
		this.upc = upc;
		this.title = title;
		this.quantity = Integer.valueOf(quantity);
	}

	public String getTitle() {
		return title;
	}

	public String getUpc() {
		return upc;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
