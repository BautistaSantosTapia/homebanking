package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.modals.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplementations implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionId(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
