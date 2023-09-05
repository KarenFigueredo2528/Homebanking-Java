package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.LoanDTO;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.repositories.CardRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class LoansController {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ClientRepository clientRepository;

/*	@Transactional
	@PostMapping("/loans")
	public ResponseEntity<Object> createLoan(
		  Authentication authetication, @RequestBody LoanDTO loanDTO) {
		Client clientAuth = clientRepository.findByEmail(authetication.getName());*/





}
