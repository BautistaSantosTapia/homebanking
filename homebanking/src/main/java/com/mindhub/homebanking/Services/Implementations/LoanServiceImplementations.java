package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.modals.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImplementations implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanId(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Loan getLoanName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
