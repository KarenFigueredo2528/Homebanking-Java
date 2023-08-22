package com.mindhub.homebankingUno;

import com.mindhub.homebankingUno.models.*;
import com.mindhub.homebankingUno.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingUnoApplication {
    private ClientRepository clientRepository;
    private LocalDate currentDate = LocalDate.now();
    private LocalDate tomorrowDate = LocalDate.now().plusDays(1);
    private LocalDate fiveYears = LocalDate.now().plusYears(5);
    private LocalDateTime dataTransfer = LocalDateTime.now().withNano(0);

    public static void main(String[] args) {
        SpringApplication.run(HomebankingUnoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
                                      TransactionRepository transactionRepository, LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {
            // save a couple of clients
            Client client = new Client("Melba", "Morel", "melba@mindhub.com","A1B2C3D4");
            Client client2 = new Client("Santiago", "Perez", "santiago@gmail.com","E5F6G7H8");
            Account account1 = new Account("VIN001", this.currentDate, 5000);
            Account account2 = new Account("VIN002", this.tomorrowDate, 7500);
            Account account3 = new Account("VIN003", this.currentDate, 5600);
            Transaction transfer1 = new Transaction(2500, "Buy christmas gifts", this.dataTransfer, TransactionType.CREDIT);
            Transaction transfer2 = new Transaction(-500, "Keyboard", this.dataTransfer, TransactionType.DEBIT);
            Transaction transfer3 = new Transaction(-3000, "Concert and parties", this.dataTransfer, TransactionType.CREDIT);
            Transaction transfer4 = new Transaction(1000, "rent", this.dataTransfer, TransactionType.DEBIT);
            Transaction transfer5 = new Transaction(-200, "Pet food", this.dataTransfer, TransactionType.CREDIT);
            Loan mortgage = new Loan("Mortgage", 500000, Arrays.asList(12, 24, 36, 48, 60));
            Loan personal = new Loan("Personal", 100000, Arrays.asList(6, 12, 24));
            Loan automotive = new Loan("Automotive", 300000, Arrays.asList(6, 12, 24, 36));
            ClientLoan mortgage1 = new ClientLoan(400000, 60);
            ClientLoan personal1 = new ClientLoan(50000, 12);
            Card goldCard = new Card("Melba Morel",CardType.DEBIT,CardColor.GOLD,"123-456-789-012", 564, this.currentDate, this.fiveYears);
            Card titaniumCard = new Card("Melba Morel",CardType.CREDIT,CardColor.TITANIUM,"987-654-321-456",978, this.currentDate,this.fiveYears);
            Card silver = new Card("Santiago Peréz",CardType.CREDIT, CardColor.SILVER,"456987123789", 754, this.currentDate, this.fiveYears);


            client.addAccounts(account1);
            client.addAccounts(account2);
            client2.addAccounts(account3);
            account1.addTransfer(transfer1);
            account1.addTransfer(transfer2);
            account2.addTransfer(transfer3);
            account3.addTransfer(transfer4);
            account3.addTransfer(transfer5);
            client.addLoan(mortgage1);
            client.addLoan(personal1);
            mortgage.addClientLoan(mortgage1);
            personal.addClientLoan(personal1);
            client.addCards(goldCard);
            client.addCards(titaniumCard);
            client2.addCards(silver);

            clientRepository.save(client);
            clientRepository.save(client2);
            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
            transactionRepository.save(transfer1);
            transactionRepository.save(transfer2);
            transactionRepository.save(transfer3);
            transactionRepository.save(transfer4);
            transactionRepository.save(transfer5);
            loanRepository.save(mortgage);
            loanRepository.save(personal);
            loanRepository.save(automotive);
            clientLoanRepository.save(mortgage1);
            clientLoanRepository.save(personal1);
            cardRepository.save(goldCard);
            cardRepository.save(titaniumCard);
            cardRepository.save(silver);

        };
    }
}
