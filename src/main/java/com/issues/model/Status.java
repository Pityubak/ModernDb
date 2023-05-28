package com.issues.model;

import lombok.Getter;

@Getter
public enum Status {
	BACKLOG("backlog"),
	STARTED("started"),
	PENDING("pending"),
	STUCK("stuck"),
	DONE("done");
	
	private final String status;
	
	Status(String status){
		this.status = status;
	}
}
