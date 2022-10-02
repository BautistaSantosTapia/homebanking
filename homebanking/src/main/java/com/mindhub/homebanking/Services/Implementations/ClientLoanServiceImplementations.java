package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.modals.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientLoanServiceImplementations implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @Override
    public List<ClientLoan> getAllClientLoans() {
        return clientLoanRepository.findAll();
    }

    @Override
    public ClientLoan getClientLoanId(Long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }


}
