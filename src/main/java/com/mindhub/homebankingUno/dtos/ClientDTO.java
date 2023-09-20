package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    //Properties
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts;
    private Set<ClientLoadDTO> loans;
    private Set<CardDTO> cards;

    //Constructor Methods
    public ClientDTO() {
    }
    public ClientDTO(Client client) {

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = new HashSet<>();
        for (Account acc: client.getAccount()) {
            this.accounts.add(new AccountDTO(acc));
        }
       this.loans = client.getClientLoans().stream()
               .map(clientLoan -> new ClientLoadDTO(clientLoan))
               .collect(Collectors.toSet());

        this.cards = client.getCards().stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());
    }

    //Accessor methods (getters)
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
    public Set<ClientLoadDTO> getLoans() {
        return loans;
    }
    public Set<CardDTO> getCards(){return cards;}
}
