package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.Transaction;
import com.mindhub.homebankingUno.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private double amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    public TransactionDTO() {
    }
    public TransactionDTO(Transaction transfer){
        this.id = transfer.getId();
        this.amount = transfer.getAmount();
        this.description = transfer.getDescription();
        this.date = transfer.getDate();
        this.type = transfer.getType();

    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
