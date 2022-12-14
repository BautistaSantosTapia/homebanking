package com.mindhub.homebanking.modals;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private String cardholder;
    private LocalDateTime fromDate;     //(inicio)       LocalDate
    private LocalDateTime thruDate;     //(final)        LocalDate
    private int cvv;


    private Boolean activeCard;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;


    public Card() {}

    public Card(CardType type, CardColor color, String number, String cardholder, LocalDateTime fromDate, LocalDateTime thruDate, int cvv) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.cardholder = cardholder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cvv = cvv;
    }

    public Card(CardType type, CardColor color, String number, String cardholder, LocalDateTime fromDate, LocalDateTime thruDate, int cvv,Client client) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.cardholder = cardholder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cvv = cvv;
        this.client = client;
    }

    public Card(CardType type, CardColor color, String number, String cardholder, LocalDateTime fromDate, LocalDateTime thruDate, int cvv,Client client, Boolean activeCard) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.cardholder = cardholder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cvv = cvv;
        this.client = client;
        this.activeCard = activeCard;
    }


    public long getId() {return id;}

    public CardType getType() {return type;}
    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}
    public void setColor(CardColor color) {this.color = color;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public String getCardholder() {return cardholder;}
    public void setCardholder(String cardholder) {this.cardholder = cardholder;}

    public LocalDateTime getFromDate() {return fromDate;}
    public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}

    public LocalDateTime getThruDate() {return thruDate;}
    public void setThruDate(LocalDateTime thruDate) {this.thruDate = thruDate;}

    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}


    public boolean isActiveCard() {return activeCard;}
    public void setActiveCard(Boolean activeCard) {this.activeCard = activeCard;}
}
