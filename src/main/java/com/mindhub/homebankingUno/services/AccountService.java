package com.mindhub.homebankingUno.services;


import com.mindhub.homebankingUno.dtos.AccountDTO;
import com.mindhub.homebankingUno.models.Account;

import java.util.List;

public interface AccountService {

	List<AccountDTO> getAccountsDTO();
	Account findById(long id);
}
