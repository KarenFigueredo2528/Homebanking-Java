package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.ClientLoan;
import com.mindhub.homebankingUno.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
	boolean existsByClientAndLoan(Client client, Loan loan);
}
