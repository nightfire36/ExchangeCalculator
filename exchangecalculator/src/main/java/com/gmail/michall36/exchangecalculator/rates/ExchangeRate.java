package com.gmail.michall36.exchangecalculator.rates;

import java.math.BigDecimal;

public class ExchangeRate {
	
	private String currencyPair;
	private BigDecimal rate;

	public ExchangeRate(String currencyPair, BigDecimal rate) {
		this.currencyPair = currencyPair;
		this.rate = rate;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public BigDecimal getRate() {
		return rate;
	}

}
