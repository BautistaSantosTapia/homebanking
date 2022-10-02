package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.ClientLoan;

import java.util.List;

public interface ClientLoanService {

    public List<ClientLoan> getAllClientLoans();
    public ClientLoan getClientLoanId(Long id);
    public void saveClientLoan(ClientLoan clientLoan);
}
