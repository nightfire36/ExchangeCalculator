package com.gmail.michall36.exchangecalculator.sql;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Table(name = "requests")
@EntityListeners(AuditingEntityListener.class)
public class Request implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rid;
	
	private RequestType type;
	
	@Column(name = "currency1")
	private String currency1;
	
	@Column(name = "currency2")
	private String currency2;
	
	private BigDecimal amount;
	
	@Column(name = "timestamp", insertable = false)
	private Timestamp timestamp;

	public Request(RequestType type, String currency1, String currency2, BigDecimal amount) {
		this.type = type;
		this.currency1 = currency1;
		this.currency2 = currency2;
		this.amount = amount;
	}

	public Long getRid() {
		return rid;
	}

	public RequestType getType() {
		return type;
	}

	public String getCurrency1() {
		return currency1;
	}

	public String getCurrency2() {
		return currency2;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
}
