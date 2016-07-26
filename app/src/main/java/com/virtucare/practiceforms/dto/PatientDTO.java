package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by KH1748 on 04-Mar-16.
 */
public class PatientDTO {

    private String lastName;

    private String country;

    private String address;

    private String gender;

    private String proofType;

    private String proofNumber;

    private String emailId;

    private String searchCid;

    private String mobileNo;

    private String dob;

    private String brandorBranchName;

    private String regId;

    private String state;


    private String relationship;


    private List<CaseIdDTO> caseIdsLIst;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getProofNumber() {
        return proofNumber;
    }

    public void setProofNumber(String proofNumber) {
        this.proofNumber = proofNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSearchCid() {
        return searchCid;
    }

    public void setSearchCid(String searchCid) {
        this.searchCid = searchCid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBrandorBranchName() {
        return brandorBranchName;
    }

    public void setBrandorBranchName(String brandorBranchName) {
        this.brandorBranchName = brandorBranchName;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<CaseIdDTO> getCaseIdsLIst() {
        return caseIdsLIst;
    }

    public void setCaseIdsLIst(List<CaseIdDTO> caseIdsLIst) {
        this.caseIdsLIst = caseIdsLIst;
    }




}
