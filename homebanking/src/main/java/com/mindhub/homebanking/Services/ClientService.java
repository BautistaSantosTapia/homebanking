package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.modals.Client;

import java.util.List;

public interface ClientService {
    public List<Client> getAllClients();

    public Client getClientId(Long id);

    public Client getClientMail(String mail);

    public void saveClient(Client client);
}

