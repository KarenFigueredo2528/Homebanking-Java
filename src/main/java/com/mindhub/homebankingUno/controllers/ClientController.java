package com.mindhub.homebankingUno.controllers;

import com.mindhub.homebankingUno.dtos.ClientDTO;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository repoClient;

    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return repoClient.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return repoClient.findById(id).map(ClientDTO::new).orElse(null);
    }
}

