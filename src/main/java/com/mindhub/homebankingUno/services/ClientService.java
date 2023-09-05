package com.mindhub.homebankingUno.services;

import com.mindhub.homebankingUno.dtos.ClientDTO;
import com.mindhub.homebankingUno.models.Client;

import java.util.List;

public interface ClientService {

	List<ClientDTO> getClientsDTO();

	ClientDTO getCurrentClient(String email);

	Client findByEmail(String email);

	void saveClient(Client client);
}
