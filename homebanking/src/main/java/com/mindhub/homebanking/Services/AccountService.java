package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.Account;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    public List<Account> getAllAccounts();

    public Account getAccountId(Long id);

    public Account getAccountNumber(String numero);

    public void saveAccount(Account account);
}
