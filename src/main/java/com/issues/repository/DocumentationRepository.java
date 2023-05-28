package com.issues.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.issues.model.Documentation;

public interface DocumentationRepository extends MongoRepository<Documentation,String>{

}
