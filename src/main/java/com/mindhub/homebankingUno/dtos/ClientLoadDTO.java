package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.ClientLoan;
import com.mindhub.homebankingUno.models.Loan;

public class ClientLoadDTO {
    private long loan;
    private  long idClientLoan;
    private String name;
    private double amount;
    private double payments;

    public ClientLoadDTO() {
    }
    public ClientLoadDTO(ClientLoan clientLoan) {
        this.loan = clientLoan.getLoan().getId();
        this.idClientLoan = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getLoan() {
        return loan;
    }

    public long getIdClientLoan() {
        return idClientLoan;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getPayments() {
        return payments;
    }
}
