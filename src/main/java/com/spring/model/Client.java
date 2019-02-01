package com.spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {

	@Id
	private long id;

	private String firstName;

	private String lastName;
	
	private String productID;
	
	public Client() {
		
	}

	public Client(long id, String firstName, String lastName, String productID) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.productID = productID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

}
