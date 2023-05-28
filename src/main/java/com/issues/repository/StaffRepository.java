package com.issues.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.issues.model.Staff;

public interface StaffRepository extends MongoRepository<Staff,String>{

}
