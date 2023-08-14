package com.mindhub.homebankingUno.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany( mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Account>accounts = new HashSet<>();

    @OneToMany( mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public long getId() {
        return id;
    }

    public Client() {
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccount() {
        return accounts;
    }

    public void setAccount(Set<Account> account) {
        this.accounts = account;
    }
    public void addAccounts(Account account1) {
        account1.setClient(this);
        this.accounts.add(account1);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public List<String> getLoans() {
        return clientLoans.stream()
                .map(clientLoan -> clientLoan.getLoan().getName())
                .collect(Collectors.toList());
    }

    public void addLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }
}
