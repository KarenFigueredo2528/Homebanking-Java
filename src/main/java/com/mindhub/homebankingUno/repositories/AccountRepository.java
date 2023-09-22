package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByNumber(String number);
	List<Account> findByClientAndAccountStatusIsTrue(Client client);
	boolean existsByNumber(String number);
}
