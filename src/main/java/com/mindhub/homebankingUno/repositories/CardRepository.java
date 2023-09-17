package com.mindhub.homebankingUno.repositories;

import com.mindhub.homebankingUno.models.Card;
import com.mindhub.homebankingUno.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
	Card findByNumber(String number);
	boolean existsByNumber(String cardNumber);

	List<Card> findAllByClientAndCardStatusTrue(Client client);


}
