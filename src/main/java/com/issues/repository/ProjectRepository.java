package com.issues.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.issues.model.Project;

public interface ProjectRepository extends MongoRepository<Project,String>{

}
