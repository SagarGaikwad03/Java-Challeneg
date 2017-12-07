package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdInvalidException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NotEnoughFundException;

public interface AccountsRepository {

	void createAccount(Account account) throws DuplicateAccountIdException;

	Account getAccount(String accountId);

	void clearAccounts();

	BigDecimal deposite(Account account, BigDecimal amount)
			throws AccountIdInvalidException;

	BigDecimal withdraw(Account account, BigDecimal amount)
			throws NotEnoughFundException, AccountIdInvalidException;

}
