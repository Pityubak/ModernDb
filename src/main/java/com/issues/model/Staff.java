package com.issues.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Document
@Getter
@Setter
@Accessors(chain = true)
public class Staff {

	 @MongoId(FieldType.OBJECT_ID)
	 private String staffId;
	 
	 private String displayName;
	 
	 private String userName;
	 
	 private String password;
	 
	 private String emailAddress;
}
