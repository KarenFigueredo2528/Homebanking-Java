package com.mindhub.homebankingUno;

import com.mindhub.homebankingUno.models.*;
import com.mindhub.homebankingUno.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HomebankingUnoApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
                                      TransactionRepository transactionRepository, LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
        return (args) -> {

            // Crear clientes
            Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123456"));
            Client client2 = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("456789"));

            //Crear cuenta
            Account account1 = new Account("VIN001", this.currentDate, 5000,true, TypeAccount.CHECKINGACCOUNT);
            Account account2 = new Account("VIN002", this.tomorrowDate, 7500,true, TypeAccount.SAVINGACCOUNT);

            //Crear préstamo
            Loan mortgage = new Loan("Mortgage", 500000, Arrays.asList(12, 24, 36, 48, 60), 5);
            Loan personal = new Loan("Personal", 100000, Arrays.asList(6, 12, 24),10);
            Loan automotive = new Loan("Automotive", 300000, Arrays.asList(6, 12, 24, 36),15);

            //Crear préstamo a cliente
            ClientLoan mortgage1 = new ClientLoan(400000, 60);
            ClientLoan personal1 = new ClientLoan(50000, 12);

            //Crear tarjeta
            Card goldCard = new Card("Melba Morel", CardType.DEBIT, CardColor.GOLD, "123-456-789-012", 564, this.currentDate, this.fiveYears, true);
            Card titaniumCard = new Card("Melba Morel", CardType.CREDIT, CardColor.TITANIUM, "987-654-321-456", 978, this.currentDate, this.fiveYears, true);
            Card silver = new Card("Santiago Peréz", CardType.CREDIT, CardColor.SILVER, "456987123789", 754, this.currentDate, this.fiveYears, true);

            //Agregar cuenta a cliente
            client.addAccounts(account1);
            client.addAccounts(account2);

            //Agregar préstamo a cliente
            client.addLoan(mortgage1);
            client.addLoan(personal1);

            //Agregar cliente a préstamo
            mortgage.addClientLoan(mortgage1);
            personal.addClientLoan(personal1);

            //Agregar tarjetas a cliente
            client.addCards(goldCard);
            client.addCards(titaniumCard);
            client2.addCards(silver);

            //Guardar por medio del repositorio al cliente
            clientRepository.save(client);
            clientRepository.save(client2);

            //Guardar por medio del repositorio la cuenta
            accountRepository.save(account1);
            accountRepository.save(account2);

            //Guardar por medio del repositorio el préstamo
            loanRepository.save(mortgage);
            loanRepository.save(personal);
            loanRepository.save(automotive);

            //Guardar por medio del repositorio el préstamo del cliente
            clientLoanRepository.save(mortgage1);
            clientLoanRepository.save(personal1);

            //Guardar por medio del repositorio la tarjeta
            cardRepository.save(goldCard);
            cardRepository.save(titaniumCard);
            cardRepository.save(silver);

        };
    }*/
}


