package com.issues.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Document
@Getter
@Setter
@Accessors(chain = true)
public class Project {
	
	 @MongoId(FieldType.OBJECT_ID)
	 private String projectId;
	 
	 private String title;
	 
	 private String description;
	 
	 @JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "Y.m.d")
	 private Date deadline;
	 
	 @JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "Y.m.d")
	 private Date startTime;
	 
	 @Indexed
	 @Field(targetType = FieldType.STRING)
	 private Status status;
	 
	 @DBRef
	 private Set<Task> tasks = new HashSet<Task>();

}
