package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.Transaction;
import com.mindhub.homebanking.modals.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private long id;
    private int amount;
    private String description;
    private LocalDateTime date;
    private TransactionType type;

    private double sobrante;


    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.type = transaction.getType();

        this.sobrante = transaction.getSobrante();
    }

    public long getId() {return id;}

    public int getAmount() {return amount;}

    public String getDescription() {return description;}

    public LocalDateTime getDate() {return date;}

    public TransactionType getType() {return type;}

    public double getSobrante() {return sobrante;}
}
