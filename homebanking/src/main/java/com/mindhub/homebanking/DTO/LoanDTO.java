package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.Loan;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;
    private String name;
    private int maxAmount;
    private List<Integer> payments;


    public LoanDTO() {}
    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public int getMaxAmount() {return maxAmount;}

    public List<Integer> getPayments() {return payments;}
}
