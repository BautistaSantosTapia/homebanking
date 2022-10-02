package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.Account;
import com.mindhub.homebanking.modals.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String numero;
    private LocalDateTime diaCreacion;
    private double balance;
    private Set<TransactionDTO> transacciones;

    private AccountType accountType;
    private Boolean activeAccount;



    public AccountDTO(){}
    public AccountDTO(Account account){
        this.id = account.getId();
        this.numero = account.getNumero();
        this.diaCreacion = account.getDiaCreacion();
        this.balance = account.getBalance();
        this.transacciones = account.getTransacciones().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());

        this.accountType = account.getAccountType();
        this.activeAccount = account.getActiveAccount();
    }

    public long getId() {return id;}

    public String getNumero() {return numero;}

    public LocalDateTime getDiaCreacion() {return diaCreacion;}

    public double getBalance() {return balance;}

    public Set<TransactionDTO> getTransacciones() {return transacciones;}


    public AccountType getAccountType() {return accountType;}

    public Boolean getActiveAccount() {return activeAccount;}
}

