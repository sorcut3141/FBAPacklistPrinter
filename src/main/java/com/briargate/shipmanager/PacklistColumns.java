package com.briargate.shipmanager;

public enum PacklistColumns {
	SKU(0),
	TITLE(1),
	ASIN(2),
	FNSKU(3),
	BARCODE(4),
	CONDITION(5),
	WHO_PREPS(6),
	PREP_TYPE(7),
	WHO_LABELS(8),
	QUANTITY(9);
	
	private int position;
	private PacklistColumns(int position) {
		this.position = position;
	}
	
	public int position() {
		return position;
	}
}
