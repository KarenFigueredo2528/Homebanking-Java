package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.NumerosAleatorios;
import com.mindhub.homebankingUno.models.TypeAccount;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.services.AccountService;
import com.mindhub.homebankingUno.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
	@Autowired
	private AccountRepository repoAccount;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientService clientService;

	@GetMapping("/api/accounts/{id}")
	public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication) {
		Client authClient = clientService.findByEmail(authentication.getName());
		Account getAccount = accountService.findById(id);

		if (authClient.getId() == getAccount.getClient().getId()) {
			return new ResponseEntity<>(new AccountDTO(getAccount), HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping("/api/clients/current/accounts")
	public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam String type) {
		Client authClient = clientService.findByEmail(authentication.getName());
		List<Account> accountList = repoAccount.findByClientAndAccountStatusIsTrue(authClient);
		if (accountList.size() < 3) {
			int numRandom = NumerosAleatorios.getRandomNumber(100000, 10000000);
			Account newAccount = new Account("VIN-" + numRandom, LocalDate.now(), 0, true, TypeAccount.valueOf(type));
			authClient.addAccounts(newAccount);
			accountService.saveAccount(newAccount);
		} else {
			return new ResponseEntity<>("You have reached the maximum number of accounts", HttpStatus.FORBIDDEN);
		}

		if(type.isBlank()){

		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/api/clients/current/accounts")
	public List<AccountDTO> getAccounts(){
		return accountService.getAccountsDTO();
	}

	@PatchMapping("/api/clients/current/accounts")
	public ResponseEntity<Object> accountStatus (@RequestParam String numberAccount, Authentication authentication){
		Client client = clientService.findByEmail(authentication.getName());

		if(numberAccount.isBlank()){
			return new ResponseEntity<>("Please enter number account", HttpStatus.FORBIDDEN);
		}

		Account account = repoAccount.findByNumber(numberAccount);

		if(account == null){
			return new ResponseEntity<>("The account was not found", HttpStatus.FORBIDDEN);
		}

		if(!client.getAccounts().contains(account)){
			return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
		}

		if(account.getAccountStatus()==false){
			return new ResponseEntity<>("You already delete the account", HttpStatus.FORBIDDEN);
		}

		/*Si la cuenta tiene dinero, no puede ser eliminada*/
		if(account.getBalance() > 0 ){
			return new ResponseEntity<>("You have money in your account" , HttpStatus.FORBIDDEN);
		}
		/*Validar que minimo se tenga una cuenta*/

		if(client.getAccounts().isEmpty()){
			return new ResponseEntity<>("You can not delete this account, at least you should have one account", HttpStatus.FORBIDDEN);
		}



		account.setAccountStatus(false);
		accountService.saveAccount(account);
		return new ResponseEntity<>("The account was delete", HttpStatus.OK);

	}

}
