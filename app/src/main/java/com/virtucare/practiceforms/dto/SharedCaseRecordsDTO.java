package com.virtucare.practiceforms.dto;

import java.io.Serializable;
import java.util.List;

public class SharedCaseRecordsDTO implements Serializable{

	private static final long serialVersionUID = 0L;
	
	private String caseRecordNo;
	private List<SharedIdDTO> membersList;
	
	public SharedCaseRecordsDTO(String caseRecordNo, List<SharedIdDTO> membersList){
		this.caseRecordNo = caseRecordNo;
		this.membersList = membersList;
	}
	
	public String getCaseRecordNo() {
		return caseRecordNo;
	}
	
	public void setCaseRecordNo(String caseRecordNo) {
		this.caseRecordNo = caseRecordNo;
	}

	public List<SharedIdDTO> getMembersList() {
		return membersList;
	}

	public void setMembersList(List<SharedIdDTO> membersList) {
		this.membersList = membersList;
	}
}
