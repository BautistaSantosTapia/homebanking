package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.modals.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementations implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountId(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account getAccountNumber(String numero) {
        return accountRepository.findByNumero(numero);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }


}
