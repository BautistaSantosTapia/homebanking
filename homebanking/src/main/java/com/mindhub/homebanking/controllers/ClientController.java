package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.modals.Loan;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    /*@Autowired
    private ClientRepository repository;*/
    @Autowired
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/api/clients")//asocia una peticion(lo de abajo) a una ruta(url), osea que cuendo escriban esa ruta se va a ejecutar el metodo de abajo
    public List<ClientDTO> getClientsDTO() {//va a retornar una lista de clientes
        return clientService.getAllClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClientsDTOId(@PathVariable Long id){//va a retornar un cliente segun el id que le pase
        return new ClientDTO(clientService.getClientId(id));
        //el ClientDTO::new seria lo mismo que esto: client -> new ClientDTO(client)
    }



    @PostMapping(/*path = */"/api/clients"/*, method = RequestMethod.POST*/)
    public ResponseEntity<Object> register(@RequestParam String nombre, @RequestParam String apellido,
                                           @RequestParam String mail, @RequestParam String password) {

        if (nombre.isEmpty() || apellido.isEmpty() || mail.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Hay parametros vacios", HttpStatus.FORBIDDEN); //
        }

        if (clientService.getClientMail(mail) !=  null) {
            return new ResponseEntity<>("El mail ya esta en uso", HttpStatus.FORBIDDEN); //
        }

        Client client = new Client(nombre, apellido, mail, passwordEncoder.encode(password));

        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/api/clients/current")
    public ClientDTO getCurrentClientDTO(Authentication authentication){
        return new ClientDTO(clientService.getClientMail(authentication.getName()));
    } // es para obtener la info del cliente autenticado (el que inincio sesion)

    /*@GetMapping("/api/clients/current/loans")
    public List<LoanDTO> getCurrentLoans(Authentication authentication){
        Client client = clientService.getClientMail(authentication.getName());
        return client.getLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }*/


}
