package com.gmail.michall36.exchangecalculator.rates;

import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gmail.michall36.exchangecalculator.sql.Rate;
import com.gmail.michall36.exchangecalculator.sql.RateRepository;


@Component
public class GetRates implements Runnable {
	
	@Autowired
	private RateRepository ratesDao;
	
	private List<ExchangeRate> rates;
	
	private Date effectiveRatesDate;
	
	private HttpURLConnection connect(String url) {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	private String getHttpContent(HttpURLConnection connection) {
		String content = "";
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = reader.readLine()) != null) {
				content += line;
			}
			reader.close();
			
			return content;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public ExchangeRate jsonToXchgRate(JsonNode record) {
		ExchangeRate rate = null;

		switch(record.get("code").asText()) {
			case "USD":
				rate = new ExchangeRate("USDPLN", record.get("mid").decimalValue());
				break;
			case "AUD":
				rate = new ExchangeRate("AUDPLN", record.get("mid").decimalValue());
				break;
			case "CAD":
				rate = new ExchangeRate("CADPLN", record.get("mid").decimalValue());
				break;
			case "NZD":
				rate = new ExchangeRate("NZDPLN", record.get("mid").decimalValue());
				break;
			case "EUR":
				rate = new ExchangeRate("EURPLN", record.get("mid").decimalValue());
				break;
			case "CHF":
				rate = new ExchangeRate("CHFPLN", record.get("mid").decimalValue());
				break;
			case "GBP":
				rate = new ExchangeRate("GBPPLN", record.get("mid").decimalValue());
				break;
			case "JPY":
				rate = new ExchangeRate("JPYPLN", record.get("mid").decimalValue());
				break;
			case "BRL":
				rate = new ExchangeRate("BRLPLN", record.get("mid").decimalValue());
				break;
			case "RUB":
				rate = new ExchangeRate("RUBPLN", record.get("mid").decimalValue());
				break;
			default:
				break;
		}
		return rate;
	}
	
	public List<ExchangeRate> parseJsonToXchgRates(String jsonString) {
		List<ExchangeRate> rates = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();

		JsonNode node = null;

		try {
			node = mapper.readValue(jsonString, JsonNode.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		node = node.get(0);

		if(node != null) {
			String effectiveRatesDateStr = node.get("effectiveDate").asText();

			try {
				this.effectiveRatesDate = new SimpleDateFormat("yyyy-MM-dd")
						.parse(effectiveRatesDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Iterator<JsonNode> ratesIterator = node.get("rates").elements();

			while (ratesIterator.hasNext()) {
				ExchangeRate exchangeRate = jsonToXchgRate(ratesIterator.next());
				if(exchangeRate != null) {
					rates.add(exchangeRate);
				}
			}
			return rates;
		}
		return null;
	}
	
	public List<ExchangeRate> getRatesFromProvider() {
		String url = "http://api.nbp.pl/api/exchangerates/tables/a?format=json";
		
		HttpURLConnection connection = connect(url);
		
		if(connection != null) {
			String content = getHttpContent(connection);
			return parseJsonToXchgRates(content);
		}
		return null;
	}
	
	private void saveRatesToDb() {
		for(ExchangeRate rate : rates) {
			Rate rateToSave = new Rate(rate.getCurrencyPair(), rate.getRate(),
					this.effectiveRatesDate);
			ratesDao.save(rateToSave);
		}
	}
	
	public void run() {
		this.rates = getRatesFromProvider();
		saveRatesToDb();
	}

	public List<ExchangeRate> getRates() {
		return this.rates;
	}
	
	public ExchangeRate getPairRate(String pair) {
		for(ExchangeRate rate : rates) {
			if(rate.getCurrencyPair().equals(pair)) {
				return rate;
			}
		}
		return null;
	}
}
