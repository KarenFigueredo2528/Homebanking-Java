package com.mindhub.homebankingUno.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    //Anotaciones
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    //Propiedades
    private long id;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;
    private double currentBalance;

    //Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    //Método constructor
    public Transaction() {
    }

    //Método constructor -> Sobrecarga de métodos
    public Transaction(double amount, String description, LocalDateTime date, TransactionType type, double currentBalance) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.currentBalance = currentBalance;
    }

    //Métodos accesores (getters y setters)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
