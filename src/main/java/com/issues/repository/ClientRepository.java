package com.issues.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.issues.model.Client;

public interface ClientRepository extends MongoRepository<Client,String> {

}
