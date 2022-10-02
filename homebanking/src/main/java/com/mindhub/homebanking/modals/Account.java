package com.mindhub.homebanking.modals;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String numero;
    private LocalDateTime diaCreacion;
    private double balance;

    private AccountType accountType;
    private Boolean activeAccount;

//para los clientes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id") /////
    private Client cliente;


//para las transacciones
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transacciones = new HashSet<>();


    public Account(){}

    public Account(String numero, LocalDateTime diaCreacion, double balance){
        this.numero = numero;
        this.diaCreacion = diaCreacion;
        this.balance = balance;
    }

    public Account(String numero, LocalDateTime diaCreacion, double balance, Client cliente) {
        this.numero = numero;
        this.diaCreacion = diaCreacion;
        this.balance = balance;
        this.cliente = cliente;
    }

    public Account(String numero, LocalDateTime diaCreacion, double balance, Client cliente, AccountType accountType) {
        this.numero = numero;
        this.diaCreacion = diaCreacion;
        this.balance = balance;
        this.cliente = cliente;
        this.accountType = accountType;
    }

    public Account(String numero, LocalDateTime diaCreacion, double balance, Client cliente, AccountType accountType,Boolean activeAccount) {
        this.numero = numero;
        this.diaCreacion = diaCreacion;
        this.balance = balance;
        this.cliente = cliente;
        this.accountType = accountType;
        this.activeAccount = activeAccount;
    }

    public long getId() {return id;}

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}

    public LocalDateTime getDiaCreacion() {return diaCreacion;}

    public void setDiaCreacion(LocalDateTime diaCreacion) {this.diaCreacion = diaCreacion;}

    public double getBalance() {return balance;}

    public void setBalance(double balance) {this.balance = balance;}


//para los clientes
    public Client getCliente() {return cliente;}

    public void setCliente(Client cliente) {this.cliente = cliente;}


//para las transacciones
    public Set<Transaction> getTransacciones() {return transacciones;}

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transacciones.add(transaction);
    }

    public AccountType getAccountType() {return accountType;}
    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

    public Boolean getActiveAccount() {return activeAccount;}
    public void setActiveAccount(Boolean activeAccount) {this.activeAccount = activeAccount;}
}
