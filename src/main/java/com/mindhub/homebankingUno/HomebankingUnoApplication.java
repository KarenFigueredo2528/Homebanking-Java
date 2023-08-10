package com.mindhub.homebankingUno;

import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.Transaction;
import com.mindhub.homebankingUno.models.TransactionType;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.repositories.TransationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingUnoApplication {
    private ClientRepository clientRepository;
    private LocalDate currentDate = LocalDate.now();
    private LocalDate tomorrowDate = LocalDate.now().plusDays(1);
    private LocalDateTime dataTransfer = LocalDateTime.now().withNano(0);

    public static void main(String[] args) {
        SpringApplication.run(HomebankingUnoApplication.class, args);
    }
    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransationRepository transationRepository) {
        return (args) -> {
            // save a couple of customers
            Client client = new Client("Melba", "Morel", "melba@mindhub.com");
            Account account1 = new Account("VIN001", this.currentDate, 5000);
            Account account2 = new Account("VIN002", this.tomorrowDate, 7500);
            Transaction transfer1 = new Transaction(2500, "Buy christmas gifts", this.dataTransfer, TransactionType.CREDIT );
            Transaction transfer2 = new Transaction(-500, "Keyboard", this.dataTransfer, TransactionType.DEBIT);
            Transaction transfer3 = new Transaction(-3000, "Concert and parties", this.dataTransfer, TransactionType.CREDIT);
            client.addAccounts(account1);
            client.addAccounts(account2);
            clientRepository.save(client);
            accountRepository.save(account1);
            accountRepository.save(account2);
            account1.addTransfer(transfer1);
            account1.addTransfer(transfer2);
            account2.addTransfer(transfer3);
            transationRepository.save(transfer1);
            transationRepository.save(transfer2);
            transationRepository.save(transfer3);

        };
    }
}
