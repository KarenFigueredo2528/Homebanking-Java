package com.mindhub.homebankingUno.dtos;

import com.mindhub.homebankingUno.models.Card;

import java.time.LocalDate;

public class CardDTO {

    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public CardDTO() {
    }

    public CardDTO(Card card){
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
