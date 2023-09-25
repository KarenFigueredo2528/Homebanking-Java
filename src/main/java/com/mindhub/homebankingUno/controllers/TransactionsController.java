package com.mindhub.homebankingUno.controllers;


import com.lowagie.text.Document;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.Transaction;
import com.mindhub.homebankingUno.models.TransactionType;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.repositories.TransactionRepository;
import com.mindhub.homebankingUno.services.AccountService;
import com.mindhub.homebankingUno.services.ClientService;
import com.mindhub.homebankingUno.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class TransactionsController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ClientService clientService;


	@Transactional
	@PostMapping("/api/transactions")
	public ResponseEntity<Object> createTransaction(
		  Authentication authetication, @RequestParam double amount,
		  @RequestParam String description, @RequestParam String originAccount,
		  @RequestParam String finalAccount) {

		if(finalAccount.isBlank()){
			return new ResponseEntity<>("Ups! you have to fill the account number to complete de transaction" , HttpStatus.FORBIDDEN);
		}


		Client clientAuth = clientService.findByEmail(authetication.getName());
		Account accOrigin = accountRepository.findByNumber(originAccount);
		Account accDestination = accountRepository.findByNumber(finalAccount);
		Set<Account> accountSet = clientAuth.getAccounts().stream().filter(acc -> acc.getAccountStatus()).collect(Collectors.toSet());


		if (amount <= 0 ){
			return new ResponseEntity<>("The amount must be greater than 0", HttpStatus.FORBIDDEN);
		}

		if(description.isBlank()){
			return new ResponseEntity<>("It looks like you don't have a shipping description, try again" , HttpStatus.FORBIDDEN);
		}

		if(accOrigin == null){
			return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
		}

		if(accDestination == null){
			return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
		}


		if(originAccount.equals(finalAccount)){
			return new ResponseEntity<>("Origin account and destination account cannot be the same", HttpStatus.FORBIDDEN);
		}

		if(accOrigin.getBalance() < amount){
			return  new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
		}
		else{
			accOrigin.setBalance(accOrigin.getBalance() - amount);
			accDestination.setBalance(accDestination.getBalance() + amount);

			Transaction transfer1 = new Transaction(amount, description + " " + accOrigin.getNumber(), LocalDateTime.now(), TransactionType.DEBIT, accOrigin.getBalance());
			accOrigin.addTransfer(transfer1);
			transactionService.saveTransaction(transfer1);
			Transaction transfer2 = new Transaction(amount, description+" "+ accDestination.getNumber(), LocalDateTime.now(), TransactionType.CREDIT, accDestination.getBalance());
			accDestination.addTransfer(transfer2);
			transactionService.saveTransaction(transfer2);

			accountService.saveAccount(accOrigin);
			accountService.saveAccount(accDestination);

		}

		return new ResponseEntity<>("the transaction was successful", HttpStatus.CREATED);
	}


	@GetMapping("/api/transactions/findDate")
	public ResponseEntity<Object> getTransactionsbyDateTime(@RequestParam String dateStart,
															@RequestParam String dateEnd,
															@RequestParam String numberAcc,
															Authentication authentication) throws DocumentException, IOException, IOException {
		Client authClient = clientService.findByEmail(authentication.getName());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		if (!accountRepository.existsByNumber(numberAcc)){
			return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
		}
		if (authClient == null){
			return new ResponseEntity<>("Sorry, you are not allowed to see this information", HttpStatus.FORBIDDEN);
		}
		if (dateStart == null){
			return new ResponseEntity<>("Please, fill the start date requeriment", HttpStatus.FORBIDDEN);
		}else if (dateEnd == null){
			new ResponseEntity<>("Please, fill the end date requeriment", HttpStatus.ACCEPTED);
		}
		if (dateStart.equals(dateEnd)){
			return new ResponseEntity<>("You cannot enter the same dates, please try again", HttpStatus.FORBIDDEN);
		}
		LocalDateTime localDateTime = LocalDateTime.parse(dateStart, formatter);
		LocalDateTime localDateTime2 = LocalDateTime.parse(dateEnd, formatter);
		List<Transaction> transfer = transactionRepository.findByDateBetweenAndAccountNumber(localDateTime, localDateTime2, numberAcc);

		Document doc = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(doc, out);
		doc.open();
		Image logo = Image.getInstance("C:\\Users\\karen\\IdeaProjects\\Homebanking-Java\\src\\main\\resources\\static\\web\\images\\Mindhub Brothers-logos.jpeg");
		logo.scaleToFit(100, 100);
		logo.setAlignment(Image.LEFT | Image.TEXTWRAP);

		PdfPTable table = new PdfPTable(4);
		table.addCell("Type");
		table.addCell("Description");
		table.addCell("Amount");
		table.addCell("Date");
		doc.add(logo);
		for (Transaction transaction : transfer) {
			table.addCell(transaction.getType().toString());
			table.addCell(transaction.getDescription());
			table.addCell(String.valueOf(transaction.getAmount()));
			table.addCell(transaction.getDate().format(formatter));
		}
		doc.add(table);
		doc.close();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=transactions_brothers_mindhub.pdf");
		byte[] pdf = out.toByteArray();
		return new ResponseEntity<>(pdf,headers, HttpStatus.OK);
	}
}
