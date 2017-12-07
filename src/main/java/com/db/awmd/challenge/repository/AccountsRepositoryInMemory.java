package com.db.awmd.challenge.repository;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.exception.AccountIdInvalidException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NotEnoughFundException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account)
			throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(),
				account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException("Account id "
					+ account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}


	@Override
	public BigDecimal deposite(Account account, BigDecimal amount)
			throws AccountIdInvalidException {
		if (account != null) {
			if (amount.compareTo((new BigDecimal(0))) != 0) {
				account.setBalance(account.getBalance().add(amount));
				accounts.put(account.getAccountId(), account);
			}
		} else {
			throw new AccountIdInvalidException("Accout id is invalid");
		}
		return amount;
	}

	@Override
	public BigDecimal withdraw(Account account, BigDecimal amount)
			throws NotEnoughFundException, AccountIdInvalidException {
		if (account != null) {
			BigDecimal balance = account.getBalance();
			balance = balance.subtract(amount);
			if (balance.compareTo(BigDecimal.ZERO) >= 0) {
				account.setBalance(account.getBalance().subtract(amount));
			} else {
				throw new NotEnoughFundException("Account " + account.getAccountId()
						+ " doesn't have enough fund to transfer");
			}
		} else {
			throw new AccountIdInvalidException("Accout id is invalid");
		}
		return amount;
	}
}
