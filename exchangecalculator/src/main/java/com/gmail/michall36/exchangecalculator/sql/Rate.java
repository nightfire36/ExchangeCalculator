package com.gmail.michall36.exchangecalculator.sql;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "rates")
@EntityListeners(AuditingEntityListener.class)
public class Rate implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long xid;
	
	@Column(name = "currency_pair")
	private String currencyPair;
	
	private BigDecimal rate;
	
	@Column(name = "effective_date")
	private Date effectiveDate;
	
	@Column(name = "timestamp", insertable = false)
	private Timestamp timestamp;

	public Rate(String currencyPair, BigDecimal rate, Date effectiveDate) {
		this.currencyPair = currencyPair;
		this.rate = rate;
		this.effectiveDate = effectiveDate;
	}

	public Long getXid() {
		return xid;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
}
