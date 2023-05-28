package com.issues.model;

import java.util.Set;
import java.util.HashSet;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Document
@Getter
@Setter
@Accessors(chain = true)
public class Task {

	 @MongoId(FieldType.OBJECT_ID)
	 private String taskId;
	 
	 private String title;
	 
	 @Indexed
	 @Field(targetType = FieldType.STRING)
	 private Status status;
	 
	 @Indexed
	 @Field(targetType = FieldType.STRING)
	 private Priority priority;
	 
	 @DBRef
	  private Set<Staff> staffs;

	  public Task() {
	    this.staffs = new HashSet<Staff>();
	  }
}
