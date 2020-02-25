package com.gmail.michall36.exchangecalculator.sql;

public enum RequestType {
	
	AVAILABLE(0),
	ALL(1),
	FOR_PAIR(2),
	CALCULATE(3);
	
	private int type;

	RequestType(int type) {
		this.type = type;
	}
}
