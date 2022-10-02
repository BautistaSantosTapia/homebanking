package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.Account;
import com.mindhub.homebanking.modals.ClientLoan;
import com.mindhub.homebanking.modals.Loan;


public class LoanApplicationDTO {
    private long id;
    private String name;
    private Integer payments;
    private int amount;
    private String numero;

    public LoanApplicationDTO() {}

    public LoanApplicationDTO(Loan loan, Account account, ClientLoan clientLoan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.numero = account.getNumero();
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public Integer getPayments() {return payments;}

    public int getAmount() {return amount;}

    public String getNumero() {return numero;}

}