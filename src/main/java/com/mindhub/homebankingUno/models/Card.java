package com.mindhub.homebankingUno.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Card {
    //Anotaciones
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    //Propiedades
    private String cardHolder;
    private String number;
    private CardColor color;
    private CardType type;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private Boolean cardStatus;

    //Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    //Método constructor
    public Card() {
    }

    //Método constructor -> sobrecarga de métodos
    public Card(String cardHolder, CardType cardType, CardColor cardColor, String number, int cvv, LocalDate fromDate, LocalDate thruDate, Boolean cardStatus) {
        this.cardHolder = cardHolder;
        this.type = cardType;
        this.color = cardColor;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardStatus = cardStatus;
    }

    //Getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
        this.type = type;
    }

    public Boolean getCardStatus() {
        return cardStatus;
    }
    public void setCardStatus(Boolean cardStatus) {
        this.cardStatus = cardStatus;
    }
}