package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransationRepository extends JpaRepository<Transaction, Long> {

}
