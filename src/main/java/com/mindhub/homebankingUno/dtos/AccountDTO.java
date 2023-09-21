package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.Account;
import com.mindhub.homebankingUno.models.Transaction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();
    private Boolean accountStatus;
    private String typeAccount;

    public AccountDTO() {
    }

    public AccountDTO(Account acc) {
        this.id = acc.getId();
        this.number = acc.getNumber();
        this.creationDate = acc.getCreationDate();
        this.balance = acc.getBalance();
        this.transactions = new HashSet<>();
        for(Transaction transfer: acc.getTransfer()){
            this.transactions.add(new TransactionDTO(transfer));
        };
        this.accountStatus = acc.getAccountStatus();
        this.typeAccount = acc.getTypeAccount();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }


    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public Boolean getAccountStatus() {
        return accountStatus;
    }

    public String getTypeAccount() {
        return typeAccount;
    }
}
