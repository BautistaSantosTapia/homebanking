package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.Card;
import com.mindhub.homebanking.modals.CardColor;
import com.mindhub.homebanking.modals.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {

    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private String cardholder;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private int cvv;


    private Boolean activeCard;


    public CardDTO() {}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cardholder = card.getCardholder();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cvv = card.getCvv();
        this.activeCard = card.isActiveCard();
    }

    public long getId() {return id;}

    public CardType getType() {return type;}

    public CardColor getColor() {return color;}

    public String getNumber() {return number;}

    public String getCardholder() {return cardholder;}

    public LocalDateTime getFromDate() {return fromDate;}

    public LocalDateTime getThruDate() {return thruDate;}

    public int getCvv() {return cvv;}

    public Boolean getActiveCard() {return activeCard;}
}
