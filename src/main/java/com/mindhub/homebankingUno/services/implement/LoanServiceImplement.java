package com.mindhub.homebankingUno.services.implement;

import com.mindhub.homebankingUno.dtos.LoanApplicationDTO;
import com.mindhub.homebankingUno.dtos.LoanDTO;
import com.mindhub.homebankingUno.models.Loan;
import com.mindhub.homebankingUno.repositories.LoanRepository;
import com.mindhub.homebankingUno.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplement implements LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Override
	public List<LoanDTO> getLoansDTO() {
		return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
	}

	@Override
	public List<LoanDTO> getAll() {
		return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
	}


}
