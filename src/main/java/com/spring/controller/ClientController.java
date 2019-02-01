package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.model.Client;
import com.spring.model.Product;
import com.spring.service.ClientServiceImpl;
import com.spring.util.Error;

@RestController
@RequestMapping("/api")
public class ClientController {

	private ClientServiceImpl clientService;

	@Autowired
	public ClientController(ClientServiceImpl clientService) {
		this.clientService = clientService;
	}
	
	// Retrieves All Clients
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public ResponseEntity<List<Client>> listAllProducts() {
		List<Client> clients = clientService.findAllClients();
		if (clients.isEmpty()) {
			return new ResponseEntity<>(clients, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}
	
	// Retrieve Single Client
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClient(@PathVariable("id") long id) {
		Client client = clientService.findById(id);
		if (client == null) {
			return new ResponseEntity<>(new Error("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(client, HttpStatus.OK);
	}
	
	// Add Client
	@RequestMapping(value = "/clients/", method = RequestMethod.POST)
	public ResponseEntity<?> createClient(@RequestBody Client client) {
		if (clientService.doesExist(client)) {
			return new ResponseEntity<>(
					new Error("Unable to create. A Client with name " + client.getFirstName() + "  "+ client.getLastName()+" "+" already exist."),
					HttpStatus.CONFLICT);
		}
		clientService.saveClient(client);

		return new ResponseEntity<>(client, HttpStatus.CREATED);
	}
	
	// Update a Client
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
		Client currentClient = clientService.findById(id);

		if (currentClient == null) {
			return new ResponseEntity<>(new Error("Unable to upate. Client with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentClient.setFirstName(client.getFirstName());
		currentClient.setLastName(client.getLastName());
		
		clientService.updateClient(currentClient);
		return new ResponseEntity<>(currentClient, HttpStatus.OK);
	}
	
	// Delete a Product
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {
		Client client = clientService.findById(id);
		if (client == null) {
			return new ResponseEntity<>(new Error("Unable to delete. Client with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		clientService.deleteProductById(id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}

	// Delete All Products
	@RequestMapping(value = "/clients/", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteAllProducts() {
		clientService.deleteAllProducts();
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}	

	
}