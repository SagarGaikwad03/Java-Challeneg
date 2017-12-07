package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.domain.TransferResponse;
import com.db.awmd.challenge.exception.AccountIdInvalidException;
import com.db.awmd.challenge.exception.NotEnoughFundException;
import com.db.awmd.challenge.repository.AccountsRepository;

@Service
public class TransactionsService {

	@Getter
	private final AccountsRepository accountsRepository;

	private NotificationService notificationService;
	
	private static final String URL = "www.db.com";

	@Autowired
	public TransactionsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public BigDecimal deposite(Account account, BigDecimal amount)
			throws AccountIdInvalidException {
		return accountsRepository.deposite(account, amount);
	}

	public BigDecimal withdraw(Account account, BigDecimal amount)
			throws NotEnoughFundException, AccountIdInvalidException {
		return accountsRepository.withdraw(account, amount);
	}

	public TransferResponse transferTo(TransferRequest request) {
		TransferResponse response = new TransferResponse();
		response.setSourceAccountId(request.getSourceAccountId());
		response.setDestinationAccountId(request.getDestinationAccountId());
		response.setAmount(request.getAmount());
		response.setBankHomeUrl(URL);
		try {

			Account fromAccount = accountsRepository.getAccount(request
					.getSourceAccountId());
			Account toAccount = accountsRepository.getAccount(request
					.getDestinationAccountId());

			BigDecimal debitAmount = withdraw(fromAccount, request.getAmount());
			BigDecimal creditAmount = deposite(toAccount, request.getAmount());

			StringBuilder message = new StringBuilder(debitAmount.toString())
					.append(" debited.");
			notificationService.notifyAboutTransfer(fromAccount,
					message.toString());

			message = new StringBuilder(creditAmount.toString())
					.append(" credited.");

			notificationService.notifyAboutTransfer(toAccount,
					message.toString());
			response.setSuccess(true);
			response.setMessage("Fund transferd successfuly.");
		} catch (NotEnoughFundException | AccountIdInvalidException e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage());
		}
		return response;
	}
}
