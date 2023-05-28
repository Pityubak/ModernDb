package com.issues.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.issues.model.Task;

public interface TaskRepository extends MongoRepository<Task,String> {

}
