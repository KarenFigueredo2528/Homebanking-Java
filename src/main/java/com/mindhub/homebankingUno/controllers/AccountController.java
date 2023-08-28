package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.NumerosAleatorios;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
	@Autowired
	private AccountRepository repoAccount;

	@Autowired
	private ClientRepository clientRepository;

	@RequestMapping("/api/accounts")
	public List<AccountDTO> getAccounts() {
		return repoAccount.findAll().stream().map(AccountDTO::new).collect(toList());
	}

	@RequestMapping("/api/accounts/{id}")
	public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication) {
		Client authClient = clientRepository.findByEmail(authentication.getName());
		Account getAccount = repoAccount.findById(id).orElse(null);

        if(authClient.getId() == getAccount.getClient().getId()){
            return new ResponseEntity<>(new AccountDTO(getAccount), HttpStatus.ACCEPTED);
        }else{
			return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
        }
	}

	@PostMapping("/api/clients/current/accounts")
	public ResponseEntity<Object> newAccount (Authentication authentication){
		Client authClient = clientRepository.findByEmail(authentication.getName());

		if(authClient.getAccounts().size() < 3 ){
			int numRandom = NumerosAleatorios.getRandomNumber(100000,10000000);
			Account newAccount = new Account("VIN-"+numRandom, LocalDate.now(),0);
			authClient.addAccounts(newAccount);
			repoAccount.save(newAccount);
		}else{
			return new ResponseEntity<>("You have reached the maximum number of accounts", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


}
