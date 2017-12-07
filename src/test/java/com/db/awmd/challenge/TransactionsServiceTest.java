package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferRequest;
import com.db.awmd.challenge.domain.TransferResponse;
import com.db.awmd.challenge.exception.AccountIdInvalidException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.NotEnoughFundException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.TransactionsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsServiceTest {

	@Autowired
	TransactionsService transactionService;

	@Autowired
	AccountsService accountsService;

	@Test
	public void deposite() throws Exception {
		Account account = this.accountsService.getAccount("Id-123");
		assertThat(this.transactionService
				.deposite(account, new BigDecimal(10)).compareTo(
						new BigDecimal(10)));
	}
	
	@Test
	public void deposite_invalidAccount() throws Exception {
		Account account = null;
		try {
			this.transactionService.deposite(account, new BigDecimal(10));
		} catch (AccountIdInvalidException ex) {
			assertThat(ex.getMessage()).isEqualTo("Accout id is invalid");
		}
	}

	@Test
	public void withdraw() throws Exception {
		Account account = this.accountsService.getAccount("Id-123");
		assertThat(this.transactionService
				.withdraw(account, new BigDecimal(10)).compareTo(
						new BigDecimal(10)));
	}

	@Test
	public void withdraw_failOnNegativeBalance() throws Exception {
		Account account = this.accountsService.getAccount("Id-123");
		try {
			this.transactionService.withdraw(account, new BigDecimal(10));
		} catch (NotEnoughFundException ex) {
			assertThat(ex.getMessage()).isEqualTo(
					"Account Id-123 doesn't have enough fund to transfer");
		}
	}

	@Test
	public void withdraw_invalidAccount() throws Exception {
		Account account = null;
		try {
			this.transactionService.withdraw(account, new BigDecimal(10));
		} catch (AccountIdInvalidException ex) {
			assertThat(ex.getMessage()).isEqualTo("Accout id is invalid");
		}
	}
	
	@Test
	public void transferTo(){
		TransferRequest request = new TransferRequest("Id-123", "Id-123", new BigDecimal(10));
		TransferResponse response = this.transactionService.transferTo(request);
		assertThat(response.isSuccess());
	}

}
