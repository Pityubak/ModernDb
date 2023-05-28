package com.issues.model;

import lombok.Getter;

@Getter
public enum Priority {
	LOW("low"),
	MEDIUM("medium"),
	HIGH("high");
	
	private final String priority;
	
	Priority(String priority){
		this.priority = priority;
	}
}
