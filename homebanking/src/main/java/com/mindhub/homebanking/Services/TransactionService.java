package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.modals.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getAllTransactions();

    public Transaction getTransactionId(Long id);

    public void saveTransaction(Transaction transaction);
}
