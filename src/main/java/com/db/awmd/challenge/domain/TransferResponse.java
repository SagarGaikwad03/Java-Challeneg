package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class TransferResponse {

	
	private String sourceAccountId;

	private String destinationAccountId;

	private BigDecimal amount;

	private String refId;

	private String bankHomeUrl;
	
	private boolean success;
	
	private String message;
	
	public TransferResponse(){}
	
	@JsonCreator
	public TransferResponse(@JsonProperty("sourceAccountId") String sourceAccountId,
			@JsonProperty("destinationAccountId") String destinationAccountId,
			@JsonProperty("amount") BigDecimal amount,
			@JsonProperty("refId") String refId,
			@JsonProperty("bankHomeUrl") String bankHomeUrl) {
		this.sourceAccountId = sourceAccountId;
		this.destinationAccountId = destinationAccountId;
		this.amount = amount;
		this.refId = refId;
		this.bankHomeUrl = bankHomeUrl;
	}
	
	
}
