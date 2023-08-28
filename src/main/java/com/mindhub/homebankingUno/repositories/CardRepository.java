package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
	Card findByNumber(String number);


}
