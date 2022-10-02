package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.modals.Account;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.modals.Transaction;
import com.mindhub.homebanking.modals.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

   /* @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;*/
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;


    @GetMapping("/api/transactions")
    public List<TransactionDTO> getTransactionsDTO(){
        return transactionService.getAllTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    @GetMapping("/api/transactions/{id}")
    public TransactionDTO getTransactionsDTOId(@PathVariable Long id){
        return new TransactionDTO(transactionService.getTransactionId(id));
    }



    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> transaction(@RequestParam int amount, @RequestParam String description, @RequestParam String numero,
                                              @RequestParam String numero2, Authentication authentication) {

        Client client = clientService.getClientMail(authentication.getName());

        Account accountOrigen = accountService.getAccountNumber(numero);
        Account accountDestino = accountService.getAccountNumber(numero2);


        if (amount <= 0 || description.isEmpty() || numero.isEmpty() || numero2.isEmpty()) {
            return new ResponseEntity<>("Hay parametros vacios", HttpStatus.FORBIDDEN); //
        }


        if (numero.equals(numero2)) {
            return new ResponseEntity<>("La cuenta de origen y destino no puede ser iguales", HttpStatus.FORBIDDEN); //
        }

        if (accountService.getAccountNumber(numero) == null || accountService.getAccountNumber(numero).getActiveAccount()==false){
            return new ResponseEntity<>("No existe la cuenta de origen", HttpStatus.FORBIDDEN);  //
        }

        if (!client.getCuentas().contains(accountService.getAccountNumber(numero))){
            return new ResponseEntity<>("Pone una de tus cuentas",HttpStatus.FORBIDDEN); //
        }

        if (accountService.getAccountNumber(numero2) ==  null || accountService.getAccountNumber(numero2).getActiveAccount()==false){
            return new ResponseEntity<>("No existe la cuenta de destino", HttpStatus.FORBIDDEN); //
        }

        if (amount > accountService.getAccountNumber(numero).getBalance()){
            return new ResponseEntity<>("El monto no puede ser mayor al de la cuenta", HttpStatus.FORBIDDEN); //
        }



        accountOrigen.setBalance(accountOrigen.getBalance() - amount);
        accountDestino.setBalance(accountDestino.getBalance() + amount);
        accountService.saveAccount(accountOrigen);
        accountService.saveAccount(accountDestino);


        Transaction transactionOrigen = new Transaction(-amount,description, LocalDateTime.now(), TransactionType.debito, accountOrigen, accountOrigen.getBalance());
        Transaction transactionDestino = new Transaction(+amount,description, LocalDateTime.now(), TransactionType.credito, accountDestino, accountDestino.getBalance());

        /*transactionOrigen.setSobrante(accountOrigen.getBalance()-amount);
        transactionDestino.setSobrante(accountDestino.getBalance()+amount);*/

        transactionService.saveTransaction(transactionOrigen);
        transactionService.saveTransaction(transactionDestino);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
