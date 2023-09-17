package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.CardDTO;
import com.mindhub.homebankingUno.dtos.ClientDTO;
import com.mindhub.homebankingUno.models.*;
import com.mindhub.homebankingUno.repositories.CardRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.services.CardService;
import com.mindhub.homebankingUno.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CardService cardService;

	@Autowired
	private ClientService clientService;

	@PostMapping("/clients/current/cards")
	public ResponseEntity<Object> newCard(
		  @RequestParam CardColor color, @RequestParam CardType type, Authentication authentication) {
		Client outClient = clientService.findByEmail(authentication.getName());
		List <Card> statusFilterCard = cardRepository.findAllByClientAndCardStatusTrue(outClient);

		if (color == null || type == null) {
			return new ResponseEntity<>("Spaces cannot be empty", HttpStatus.FORBIDDEN);
		}

		String cardNumber;
		do {
			cardNumber = NumerosAleatorios.CardNumber();
		} while (cardService.existByNumber(cardNumber));

		int cardCvv = NumerosAleatorios.getCardCVV();

		for (Card card : statusFilterCard) {
			if (card.getType().equals(type) && card.getColor().equals(color)) {
				return new ResponseEntity<>("The card is already exist", HttpStatus.FORBIDDEN);
			}
		}

		Card newCard = new Card(outClient.getFirstName() + " " + outClient.getLastName(), type, color, cardNumber, cardCvv,
			  LocalDate.now(), LocalDate.now().plusYears(5),true);
		outClient.addCards(newCard);
		cardService.saveCard(newCard);

		return new ResponseEntity<>("Card created", HttpStatus.CREATED);
	}

	@GetMapping("/clients/current/cards")
	public List<CardDTO> getCards(Authentication authentication) {
		return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getCards().stream().collect(toList());
	}

	@PatchMapping("/clients/current/cards")
	public ResponseEntity<Object> cardStatus(@RequestParam String cardNumber, Authentication authentication){
		Client client = clientService.findByEmail(authentication.getName());


		if(cardNumber.isBlank()){
			return new ResponseEntity<>("Please enter the number of the card that you want to delete", HttpStatus.FORBIDDEN);
		}

		Card card = cardService.findByNumber(cardNumber);

		if(card == null){
			return new ResponseEntity<>("The card was not found",HttpStatus.FORBIDDEN);
		}

		if(!client.getCards().contains(card)){
			return new ResponseEntity<>("The card doesn't exist", HttpStatus.FORBIDDEN);
		}

		if(card.getCardStatus() == false){
			return new ResponseEntity<>("You already deactivate the card",HttpStatus.FORBIDDEN);
		}

		card.setCardStatus(false);
		cardService.saveCard(card);
		return new ResponseEntity<>("Card was delete", HttpStatus.OK);

	}
}
