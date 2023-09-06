package com.mindhub.homebankingUno.services.implement;

import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.ClientLoan;
import com.mindhub.homebankingUno.models.Loan;
import com.mindhub.homebankingUno.repositories.ClientLoanRepository;
import com.mindhub.homebankingUno.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

	@Autowired
	private ClientLoanRepository clientLoanRepository;


	@Override
	public void save(ClientLoan clientLoan) {
		clientLoanRepository.save(clientLoan);
	}

	@Override
	public boolean existsByClientAndLoan(Client client, Loan loan) {
		return clientLoanRepository.existsByClientAndLoan(client, loan);
	}
}
