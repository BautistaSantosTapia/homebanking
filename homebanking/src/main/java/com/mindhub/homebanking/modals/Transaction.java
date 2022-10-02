package com.mindhub.homebanking.modals;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int amount;
    private String description;
    private LocalDateTime date;

    //private enum TransactionType{credito(positivo),debito(negativo)};
    private TransactionType type;


    private double sobrante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;


    public Transaction(){}

    public Transaction(int amount, String description, LocalDateTime date, TransactionType type){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public Transaction(int amount, String description, LocalDateTime date, TransactionType type, Account account) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
    }

    public Transaction(int amount, String description, LocalDateTime date, TransactionType type, Account account, double sobrante) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
        this.sobrante = sobrante;
    }

    public long getId() {return id;}

    public int getAmount() {return amount;}

    public void setAmount(int amount) {this.amount = amount;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getDate() {return date;}

    public void setDate(LocalDateTime date) {this.date = date;}

    public TransactionType getType() {return type;}

    public void setType(TransactionType type) {this.type = type;}


    public Account getAccount(){return account;}

    public void setAccount(Account account) {this.account = account;}


    public double getSobrante() {return sobrante;}

    public void setSobrante(double sobrante) {this.sobrante = sobrante;}
}