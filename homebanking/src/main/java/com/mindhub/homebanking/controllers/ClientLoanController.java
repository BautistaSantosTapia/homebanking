package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.Services.LoanService;
import com.mindhub.homebanking.modals.Loan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientLoanController {

    /*@Autowired
    public ClientLoanRepository clientLoanRepository;*/
    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private LoanService loanService;


    @GetMapping("/api/clientLoans")
    public List<ClientLoanDTO> getClintLoansDTO(){
        return clientLoanService.getAllClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
    }

    @GetMapping("/api/clientLoans/{id}")
    public ClientLoanDTO getClientLoansDTOId(@PathVariable Long id){
        return new ClientLoanDTO(clientLoanService.getClientLoanId(id));
    }



    @PostMapping("/api/clientLoans")
    public ResponseEntity<Object> createLoan(@RequestParam String name, @RequestParam int maxAmount, @RequestParam List<Integer> payments) {


        if (name.isEmpty() || maxAmount<=0 || payments.isEmpty()){
            return new ResponseEntity<>("Hay parametros vacios",HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(name,maxAmount,payments);

        loanService.saveLoan(loan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
