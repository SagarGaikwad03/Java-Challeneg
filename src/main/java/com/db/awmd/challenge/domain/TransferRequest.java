package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class TransferRequest {

	@NotNull
	@NotEmpty
	private final String sourceAccountId;

	@NotNull
	@NotEmpty
	private final String destinationAccountId;

	@NotNull
	private BigDecimal amount;

	@JsonCreator
	public TransferRequest(@JsonProperty("sourceAccountId") String sourceAccountId,
			@JsonProperty("destinationAccountId") String destinationAccountId,
			@JsonProperty("amount") BigDecimal amount) {
		this.sourceAccountId = sourceAccountId;
		this.destinationAccountId = destinationAccountId;
		this.amount = amount;
	}

}
