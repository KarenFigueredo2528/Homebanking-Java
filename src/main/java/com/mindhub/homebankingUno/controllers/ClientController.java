package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.ClientDTO;
import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.models.NumerosAleatorios;
import com.mindhub.homebankingUno.repositories.AccountRepository;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{current}")
    public ClientDTO getClient(Authentication authentication) {
        return clientService.getCurrentClient(authentication.getName());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        int numberAccount = NumerosAleatorios.getRandomNumber(100000, 10000000);
        Account newAccount = new Account("VIN-" + numberAccount, LocalDate.now(), 0, true);
        client.addAccounts(newAccount);
        accountRepository.save(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

