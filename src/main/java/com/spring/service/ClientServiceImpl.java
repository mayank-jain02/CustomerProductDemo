package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.model.Client;
import com.spring.repo.ClientRepo;

@Service
public class ClientServiceImpl {

	@Autowired
	private ClientRepo repository;
	
	public List<Client> findAllClients() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Client findById(long id) {
		return repository.getOne(id);
	}


	public void saveClient(Client client) {
		repository.save(client);
	}

	public boolean doesExist(Client client) {
		
		return repository.findByFirstNameAndLastName(client.getFirstName(), client.getLastName())!=null;
	}

	public void updateClient(Client currentClient) {
		saveClient(currentClient);
	}

	public void deleteProductById(long id) {
		repository.deleteById(id);
	}

	public void deleteAllProducts() {
		repository.deleteAll();
	}
	



}
