package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByNumber(String number);
}
