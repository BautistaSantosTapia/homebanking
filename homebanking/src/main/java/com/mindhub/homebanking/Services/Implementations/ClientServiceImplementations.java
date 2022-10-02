package com.mindhub.homebanking.Services.Implementations;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplementations implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientId(Long id) {
        return clientRepository.findById(id).get();
    }//le decimos que busque el cliente y si lo encuentra que me lo de/obtenga/devuelva sino arroja una excepcion
    /*otra forma
* public Optional<Client> getClientId(Long id)
* return clientRepository.findById(id);
* (y tambien poner que te retorne un option el la declaracion del metodo en ClientService)
* */
    /* y la forma mas facil es con un orElse(null)*/

    @Override
    public Client getClientMail(String mail) {
        return clientRepository.findByMail(mail);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }


}
