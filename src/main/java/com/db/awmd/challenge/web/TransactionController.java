package com.db.awmd.challenge.web;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.domain.TransferResponse;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransactionsService;

@RestController
@RequestMapping("/v1/transactions")
@Slf4j
public class TransactionController {

	private final TransactionsService transactionService;

	@Autowired
	public TransactionController(TransactionsService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public TransferResponse transferFund(@RequestBody TransferRequest request) {
		log.info("Transfering amount {} from account {} to account {}",
				request.getAmount(), request.getSourceAccountId(),
				request.getDestinationAccountId());
		return this.transactionService.transferTo(request);

	}

}
