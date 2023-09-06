package com.mindhub.homebankingUno.services;


import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {

	List<AccountDTO> getAccountsDTO();

	List <AccountDTO> getAll(Authentication authentication);
	Account findById(long id);

	void saveAccount(Account account);
}
