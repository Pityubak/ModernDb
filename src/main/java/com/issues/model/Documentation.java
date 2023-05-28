package com.issues.model;

import java.util.Date;

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
public class Documentation {
	
	 @MongoId(FieldType.OBJECT_ID)
	 private String documentationId;
	 
	 @JsonFormat
     (shape = JsonFormat.Shape.STRING, pattern = "Y.m.d")
	 private Date date;
	 
	 @Indexed
	 @Field(targetType = FieldType.STRING)
	 private ApprovalStatus approved;
	 
	 private String title;
	 
	 private String content;
	 
	 @DBRef
	 private Task task;
}
