package com.mindhub.homebankingUno.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    //Anotaciones
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //Especifíca como se generaran los valores
    @GenericGenerator(name = "native", strategy = "native") //Especifica la estrategia de generación a utilizar

    //Propiedades
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //Relaciones y propiedades
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>(); //Nueva colección para que se guarde y no se repita

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    //Método constructor
    public Client(){
    }

    //Método constructor - sobrecarga  de métodos
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //Métodos Accesores (getters and setters)
    public long getId() {
        return id;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Account> getAccount() {
        return accounts;
    }
    public void setAccount(Set<Account> account) {
        this.accounts = account;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public List<String> getLoans() {
        return clientLoans.stream()
                .map(clientLoan -> clientLoan.getLoan().getName())
                .collect(Collectors.toList());
    }

    //Método para asignar el préstamo al cliente
    public void addLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    //Método para asignar la cuenta al cliente
    public void addAccounts(Account account1) {
        account1.setClient(this);
        this.accounts.add(account1);
    }

    //Método para asignar las tarjetas al cliente
    public void addCards(Card card){
        card.setClient(this);
        this.cards.add(card);
    }

}
