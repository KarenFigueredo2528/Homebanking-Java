package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

    private ClientRepository clientRepository;
    private LocalDate currentDate = LocalDate.now();
    private LocalDate tomorrowDate = LocalDate.now().plusDays(1);

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository) {
        return (args) -> {
            // save a couple of customers
            Client client = new Client("Melba", "Morel", "melba@mindhub.com");
            Account account1 = new Account("VIN001", this.currentDate, 5000);
            Account account2 = new Account("VIN002", this.tomorrowDate,7500);
            client.addAccounts(account1);
            client.addAccounts(account2);
            clientRepository.save(client);
            accountRepository.save(account1);
            accountRepository.save(account2);
        };
    }
}
