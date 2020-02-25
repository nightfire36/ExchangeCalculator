package com.gmail.michall36.exchangecalculator.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.michall36.exchangecalculator.rates.GetRates;
import com.gmail.michall36.exchangecalculator.rates.ExchangeRate;
import com.gmail.michall36.exchangecalculator.sql.Request;
import com.gmail.michall36.exchangecalculator.sql.RequestRepository;
import com.gmail.michall36.exchangecalculator.sql.RequestType;

@Transactional
@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private RequestRepository requestsDao;
	
	@Autowired
	private GetRates rates;
	
	private String[] pairs = {"PLN", "USD", "AUD", "CAD", "NZD", "EUR", "CHF", "GBP", "JPY", "BRL", "RUB"};
	
	private void saveRequestsToDb(RequestType type, String currency1, String currency2, BigDecimal amount) {
		Request request = new Request(type, currency1, currency2, amount);
		requestsDao.save(request);
	}
	
	@GetMapping("/available_rates")
	public String[] availableRates() {
		saveRequestsToDb(RequestType.AVAILABLE, null, null, null);
		return pairs;
	}
	
	@GetMapping("/all_rates")
	public List<ExchangeRate> allRates() {
		saveRequestsToDb(RequestType.ALL, null, null, null);
		return rates.getRates();
	}
	
	private BigDecimal pairRate(String currency1, String currency2) {
		if(currency2.equals("PLN")) {
			return rates.getPairRate(currency1 + currency2).getRate();
		}
		else if(currency1.equals("PLN")) {
			BigDecimal rate2 = rates.getPairRate(currency2 + currency1).getRate();
			if(rate2 != null) {
				return new BigDecimal("1").divide(rate2, 20, RoundingMode.HALF_UP);
			}
			else {
				return null;
			}
		}
		else {
			BigDecimal rate1 = rates.getPairRate(currency1 + "PLN").getRate();
			BigDecimal rate2 = rates.getPairRate(currency2 + "PLN").getRate();
			if(rate1 != null && rate2 != null) {
				return rate1.divide(rate2, 20, RoundingMode.HALF_UP);
			}
			else {
				return null;
			}
		}
	}
	
	@GetMapping("/rate/{currency1}/{currency2}")
	public BigDecimal getPairRate(@PathVariable("currency1") String currency1, 
			@PathVariable("currency2") String currency2) {
		if(currency1.length() == 3 && currency2.length() == 3) {
			saveRequestsToDb(RequestType.FOR_PAIR, currency1.toUpperCase(), currency2.toUpperCase(), null);
			return pairRate(currency1.toUpperCase(), currency2.toUpperCase()).setScale(4, RoundingMode.HALF_UP);
		}
		return null;
	}
	
	@GetMapping("/calculate/{currency1}/{currency2}/{amount}")
	public BigDecimal calculate(@PathVariable("currency1") String currency1, 
			@PathVariable("currency2") String currency2, @PathVariable("amount") BigDecimal amount) {
		if(currency1.length() == 3 && currency2.length() == 3) {
			saveRequestsToDb(RequestType.CALCULATE, currency1.toUpperCase(), currency2.toUpperCase(), amount);
			return amount.multiply(pairRate(currency1.toUpperCase(), currency2.toUpperCase()))
					.setScale(4, RoundingMode.HALF_UP);
		}
		return null;
	}
}
