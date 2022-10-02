package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.modals.Card;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.modals.ClientLoan;
import com.mindhub.homebanking.modals.Loan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String nombre, apellido, mail;
    private Set<AccountDTO> cuentas;
    private Set<CardDTO> cards;

    private Set<ClientLoanDTO> loans;


    public ClientDTO(){}
    public ClientDTO(Client client){
        this.id = client.getId();
        this.nombre = client.getNombre();
        this.apellido = client.getApellido();
        this.mail = client.getMail();
        this.cuentas = client.getCuentas().stream().map(cuenta -> new AccountDTO(cuenta)).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
        this.loans = client.getClientloans().stream().map(clientloan -> new ClientLoanDTO(clientloan)).collect(Collectors.toSet());
    }

    public long getId() {return id;}

    public String getNombre() {return nombre;}

    public String getApellido() {return apellido;}

    public String getMail() {return mail;}

    public Set<AccountDTO> getCuentas() {return cuentas;}

    public Set<CardDTO> getCards() {return cards;}

    public Set<ClientLoanDTO> getLoans() {return loans;}

}
