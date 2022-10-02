package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.Loan;

import java.util.List;

public interface LoanService {

    public List<Loan> getAllLoans();
    public Loan getLoanId(Long id);
    public Loan getLoanName(String name);
    public void saveLoan(Loan loan);
}
