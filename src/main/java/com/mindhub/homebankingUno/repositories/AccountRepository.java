package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
