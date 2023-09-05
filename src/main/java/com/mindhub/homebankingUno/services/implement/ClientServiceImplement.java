package com.mindhub.homebankingUno.services.implement;

import com.mindhub.homebankingUno.dtos.ClientDTO;
import com.mindhub.homebankingUno.models.Client;
import com.mindhub.homebankingUno.repositories.ClientRepository;
import com.mindhub.homebankingUno.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<ClientDTO> getClientsDTO() {
		return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
	}

	@Override
	public ClientDTO getCurrentClient(String email) {
		return new ClientDTO(this.findByEmail(email));
	}

	@Override
	public Client findByEmail(String email) {
		return clientRepository.findByEmail(email);
	}

	@Override
	public void saveClient(Client client) {
		clientRepository.save(client);
	}
}
