package com.mindhub.homebankingUno.controllers;


import com.mindhub.homebankingUno.dtos.LoanApplicationDTO;
import com.mindhub.homebankingUno.dtos.LoanDTO;
import com.mindhub.homebankingUno.models.*;
import com.mindhub.homebankingUno.repositories.*;
import com.mindhub.homebankingUno.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoansController {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientLoanRepository clientLoanRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@RequestMapping(path = "/loans")
	public List<LoanDTO> getLoans() {
		return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
	}

	@Transactional
	@RequestMapping(path = "/loans", method = RequestMethod.POST)
	public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

		Client client = clientRepository.findByEmail(authentication.getName());
		Account account = accountRepository.findByNumber(loanApplicationDTO.getNumberAccountDestination());
		Loan loan = loanRepository.findById(loanApplicationDTO.getId());

		if (client == null) {
			return new ResponseEntity<>("The client wasn't found", HttpStatus.FORBIDDEN);
		}

		if (loan.getPayments() == null) {
			return new ResponseEntity<>("Payments not found", HttpStatus.FORBIDDEN);
		}

		if (loan == null) {
			return new ResponseEntity<>("Loan doesn't exit", HttpStatus.FORBIDDEN);
		}

		if (loan.getMaxAmount() <= 0) {
			return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
		}

		if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
			return new ResponseEntity<>("You don't have enough money", HttpStatus.FORBIDDEN);
		}

		if (!(loan.getPayments().contains(loanApplicationDTO.getPayments()))) {
			return new ResponseEntity<>("The payment doesn't exist", HttpStatus.FORBIDDEN);
		}

		if (account == null) {
			return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
		}

		if(account.getClient().getId() != client.getId()){
			return new ResponseEntity<>("This account doesn't match",HttpStatus.UNAUTHORIZED);
		}

		account.setBalance(account.getBalance()+ loanApplicationDTO.getAmount());
		ClientLoan clientLoan = new ClientLoan (loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayments());
		clientLoan.setClient(client);
		clientLoan.setLoan(loan);
		clientLoanRepository.save(clientLoan);
		String transactionStatus = loan.getName() + "Loan Approved";
		Transaction transaction = new Transaction(loanApplicationDTO.getAmount(),transactionStatus, LocalDateTime.now(), TransactionType.CREDIT);
		transactionRepository.save(transaction);
		accountRepository.save(account);
		return new ResponseEntity<>("The loan was created",HttpStatus.ACCEPTED);
	}


}
