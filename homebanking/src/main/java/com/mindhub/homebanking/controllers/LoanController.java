package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.modals.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.modals.CardType.credito;

@RestController
public class LoanController {

    /*@Autowired
    public LoanRepository loanRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public TransactionRepository transactionRepository;
    @Autowired
    public ClientLoanRepository clientLoanRepository;*/

    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;


    @GetMapping("/api/loans")
    public List<LoanDTO> getLoansDTO() {
        return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @GetMapping("/api/loans/{id}")
    public LoanDTO getLoansDTOId(@PathVariable Long id) {
        return new LoanDTO(loanService.getLoanId(id));
    }

    @Transactional
    @PostMapping("/api/loans") //////en vez de path, value y en vez de Object, String
    public ResponseEntity<String> addPrestamo(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){


        String name = loanApplicationDTO.getName();
        Integer payments = loanApplicationDTO.getPayments();
        int amount = loanApplicationDTO.getAmount();
        String numero = loanApplicationDTO.getNumero();

        Client client = clientService.getClientMail(authentication.getName());
        Account accountDestino = accountService.getAccountNumber(numero);
        Loan loan = loanService.getLoanName(name);


        List<ClientLoan> prestamoPedidoNombre = client.getClientloans().stream().filter(nome -> nome.getLoan().getName().equals(name)).collect(Collectors.toList());

        if (name.isEmpty() || amount <=0 || payments<=0 || numero.isEmpty()){
            return new ResponseEntity<>("Hay parametros vacios",HttpStatus.FORBIDDEN); //
        }
        if(loanService.getLoanName(name) == null){
            return new ResponseEntity<>("No se encontro el prestamo", HttpStatus.FORBIDDEN); //
        }
        if (amount> loan.getMaxAmount()){
            return new ResponseEntity<>("El monto debe ser menor al maximo del prestamo",HttpStatus.FORBIDDEN); //
        }
        if (!loan.getPayments().contains(payments)){
            return new ResponseEntity<>("El pago debe estar entre los disponibles del prestamo",HttpStatus.FORBIDDEN); //
        }
        if (accountDestino == null){
            return new ResponseEntity<>("No existe la cuenta de destino",HttpStatus.FORBIDDEN); //
        }
        if (!client.getCuentas().contains(accountDestino)){
            return new ResponseEntity<>("La cuenta de destino no te pertenece",HttpStatus.FORBIDDEN); //
        }

        if (prestamoPedidoNombre.stream().count()>=1){
            return new ResponseEntity<>("No puedes tener mas de un prestamo "+name,HttpStatus.FORBIDDEN); //
        }


        accountDestino.setBalance(accountDestino.getBalance() + amount);
        accountService.saveAccount(accountDestino);

        Transaction transactionDestino = new Transaction(+amount,name + " prestamo aprovado", LocalDateTime.now(), TransactionType.credito, accountDestino,accountDestino.getBalance());

        ClientLoan prestamoPedido = new ClientLoan(amount, payments, client, loan);


        transactionService.saveTransaction(transactionDestino);
        clientLoanService.saveClientLoan(prestamoPedido);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}