package com.gmail.michall36.exchangecalculator.rates;

import java.util.concurrent.*;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetExchangeRatesService {
	
	private ScheduledExecutorService thread = null;
	
	@Autowired
	private GetRates rates;
	
	@PostConstruct
	public void setup() {
		thread = Executors.newScheduledThreadPool(1);
		thread.scheduleAtFixedRate(rates, 0, 60 * 60 * 24, TimeUnit.SECONDS);
	}
	
	public boolean isRunning() {
		return !thread.isTerminated();
	}
	
	public void stop() {
		thread.shutdown();
		
		while(isRunning()) {
			try {
				Thread.sleep(200);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
