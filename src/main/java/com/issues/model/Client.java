package com.issues.model;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Document(collection="clients")
public class Client {

	 @MongoId(FieldType.OBJECT_ID)
	 private String clientId;
	 
	 private Boolean active;
	 
	 @Indexed
	 private String name;
	 
	 private ContactInfo contactInfo;
	 
	 @DBRef
	 private Set<Project> projects = new HashSet<Project>();
}
