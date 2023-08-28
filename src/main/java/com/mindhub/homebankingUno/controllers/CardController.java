package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {

	@Autowired
	private CardRepository cardRepository;

}
