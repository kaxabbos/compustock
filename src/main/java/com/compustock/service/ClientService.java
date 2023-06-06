package com.compustock.service;

import com.compustock.model.Client;
import com.compustock.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void add(Client client) {
        clientRepo.save(client);
    }

    public void update(Client client) {
        clientRepo.save(client);
    }

    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    public void delete(Client client) {
        clientRepo.delete(client);
    }

    public Client find(Long id) {
        return clientRepo.findClientById(id);
    }

    public List<Client> findAllByOrderByIdDesc() {
        return clientRepo.findAllByOrderByIdDesc();
    }
}
