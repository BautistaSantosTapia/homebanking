package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.modals.Account;
import com.mindhub.homebanking.modals.AccountType;
import com.mindhub.homebanking.modals.Card;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    /*@Autowired
    private AccountRepository accountRepository;
    @Autowired
    public ClientRepository clientRepository;*/
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;


    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccountsDTO(){
        return accountService.getAllAccounts().stream().map(account-> new AccountDTO(account)).collect(Collectors.toList());
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getAccountsDTOId(@PathVariable Long id){
        return new AccountDTO(accountService.getAccountId(id));
    }



    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType accountType, Authentication authentication) {

        Client client = clientService.getClientMail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("No existe el cliente", HttpStatus.FORBIDDEN); //--no se como verificarlo
        }


        List<Account> cuentasActivas = client.getCuentas().stream().filter(account -> account.getActiveAccount()==true).collect(Collectors.toList());

        if (cuentasActivas.stream().count()>=3) {
            return new ResponseEntity<>("No podes tener mas de 3 cuentas", HttpStatus.FORBIDDEN); //
        }

        Account account = new Account("VIN" + (int) ((Math.random() * (9999999 - 100))+100),LocalDateTime.now(),0,client,accountType,true);

        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/api/clients/current/accounts/{id}")
    public ResponseEntity<Object> modificar(@PathVariable Long id){

        Account account = accountService.getAccountId(id);
        if (account.getBalance() >0){
            return new ResponseEntity<>("La cuenta tiene que estar vacia para borrarla", HttpStatus.FORBIDDEN); //
        }

        account.setActiveAccount(false);

        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.ACCEPTED); //HttpStatus.OK
    }
}
