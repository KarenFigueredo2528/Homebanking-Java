package com.mindhub.homebankingUno.services;

import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.ClientLoan;
import com.mindhub.homebankingUno.models.Loan;

public interface ClientLoanService {

	void save(ClientLoan clientLoan);
	boolean existsByClientAndLoan(Client client, Loan loan);
}
