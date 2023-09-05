package com.mindhub.homebankingUno.controllers;


import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.Transaction;
import com.mindhub.homebankingUno.models.TransactionType;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.repositories.TransactionRepository;
import com.mindhub.homebankingUno.services.AccountService;
import com.mindhub.homebankingUno.services.ClientService;
import com.mindhub.homebankingUno.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
public class TransactionsController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ClientService clientService;


	@Transactional
	@PostMapping("/api/transactions")
	public ResponseEntity<Object> createTransaction(
		  Authentication authetication, @RequestParam double amount,
		  @RequestParam String description, @RequestParam String originAccount,
		  @RequestParam String finalAccount) {

		if(finalAccount.isBlank()){
			return new ResponseEntity<>("Ups! you have to fill the account number to complete de transaction" , HttpStatus.FORBIDDEN);
		}


		Client clientAuth = clientService.findByEmail(authetication.getName());
		/*Pueden linea 63 y 64 cambiar su accountRepository*/
		Account accOrigin = accountRepository.findByNumber(originAccount);
		Account accDestination = accountRepository.findByNumber(finalAccount);


		if (amount <= 0 ){
			return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
		}

		if(description.isBlank()){
			return new ResponseEntity<>("It looks like you don't have a shipping description, try again" , HttpStatus.FORBIDDEN);
		}

		if(accOrigin == null){
			return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
		}

		if(accDestination == null){
			return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
		}


		if(originAccount.equals(finalAccount)){
			return new ResponseEntity<>("Origin account and destination account cannot be the same", HttpStatus.FORBIDDEN);
		}

		if(accOrigin.getBalance() < amount){
			return  new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
		}
		else{
			accOrigin.setBalance(accOrigin.getBalance() - amount);
			accDestination.setBalance(accDestination.getBalance() + amount);

			Transaction transfer1 = new Transaction(amount, description + " " + accOrigin.getNumber(), LocalDateTime.now(), TransactionType.DEBIT);
			accOrigin.addTransfer(transfer1);
			transactionService.saveTransaction(transfer1);
			Transaction transfer2 = new Transaction(amount, description+" "+ accDestination.getNumber(), LocalDateTime.now(), TransactionType.CREDIT);
			accDestination.addTransfer(transfer2);
			transactionService.saveTransaction(transfer2);

			accountService.saveAccount(accOrigin);
			accountService.saveAccount(accDestination);

		}

		return new ResponseEntity<>("the transaction was successful", HttpStatus.CREATED);
	}
}
