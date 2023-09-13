package com.mindhub.homebankingUno.services;

import com.mindhub.homebankingUno.models.Card;

public interface CardService {
	void saveCard(Card card);

	Boolean existByNumber(String cardNumber);

	Card findByNumber(String cardNumber);
}
