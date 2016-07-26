package com.virtucare.practiceforms.dto;

import java.io.Serializable;

public class SharedToMemberDetailsDTO implements Serializable{

	private static final long serialVersionUID = 0L;
	
	private String sharedTo;
	private String sharedType;
	private String fullName;

	public SharedToMemberDetailsDTO(String fullName, String sharedTo, String sharedType){
		this.fullName = fullName;
		this.sharedTo = sharedTo;
		this.sharedType = sharedType;
	}
	
	public String getSharedTo() {
		return sharedTo;
	}
	
	public void setSharedTo(String sharedTo) {
		this.sharedTo = sharedTo;
	}
	
	public String getSharedType() {
		return sharedType;
	}
	
	public void setSharedType(String sharedType) {
		this.sharedType = sharedType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
