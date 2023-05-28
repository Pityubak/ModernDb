package com.issues.model;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
	APPROVED("approved"),
	PENDING("pending"),
	REJECTED("rejected");
	
	 private final String status;
	 
	 ApprovalStatus(String status){
		this.status = status; 
	 }
}
