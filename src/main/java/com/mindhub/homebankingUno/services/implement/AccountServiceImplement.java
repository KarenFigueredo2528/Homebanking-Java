package com.mindhub.homebankingUno.services.implement;

import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<AccountDTO> getAccountsDTO() {
		return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
	}

	@Override
	public List<AccountDTO> getAll(Authentication authentication) {
		return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
	}

	@Override
	public Account findById(long id) {
		return accountRepository.findById(id).orElse(null);
	}

	@Override
	public void saveAccount(Account account) {
		accountRepository.save(account);
	}


}
