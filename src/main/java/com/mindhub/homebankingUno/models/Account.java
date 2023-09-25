package com.mindhub.homebankingUno.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    //Anotaciones
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    //Propiedades
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Boolean accountStatus;
    private TypeAccount typeAccount;

    //Relaciones y propiedades
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany( mappedBy = "account",fetch = FetchType.EAGER)
    private Set<Transaction> transfer = new HashSet<>();

    //Método constructor
    public Account() {
    }

    //Método constructor -> Sobrecarga de métodos
    public Account(String number, LocalDate currentDate, long balance, Boolean accountStatus, TypeAccount typeAccount) {
        this.number = number;
        this.creationDate = currentDate;
        this.balance = balance;
        this.accountStatus = accountStatus;
        this.typeAccount = typeAccount;
    }

    //Métodos accesores (getters y setters)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransfer() {
        return transfer;
    }
    public void setTransfer(Set<Transaction> transfer) {
        this.transfer = transfer;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(Boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }
    public void setTypeAccount(TypeAccount typeAccount) {
        this.typeAccount = typeAccount;
    }

    //Método para asignar transferencia a la cuenta
    public void addTransfer(Transaction transaction) {
        transaction.setAccount(this);
        transfer.add(transaction);
    }
}

