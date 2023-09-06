package com.mindhub.homebankingUno.services;

import com.mindhub.homebankingUno.dtos.LoanApplicationDTO;
import com.mindhub.homebankingUno.dtos.LoanDTO;
import com.mindhub.homebankingUno.models.Loan;

import java.util.List;

public interface LoanService {

	List<LoanDTO> getLoansDTO();

	List <LoanDTO> getAll();

}
