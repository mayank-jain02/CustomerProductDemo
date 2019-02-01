package com.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {


	Client findByFirstNameAndLastName(String firstName, String lastName);


}
