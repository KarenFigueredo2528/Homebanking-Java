package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
	Loan findById(long id);
	Boolean existsByName(String name);
}
