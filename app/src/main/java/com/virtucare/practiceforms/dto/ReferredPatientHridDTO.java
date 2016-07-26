package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by AJITI on 6/30/2016.
 */
public class ReferredPatientHridDTO {

    private String lastName;
    private String emailId;
    private String userName;
    private String hrid;
    private String firstName;
    private String mobileNo;
    private List<String> caseRecordNoCount;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHrid() {
        return hrid;
    }

    public void setHrid(String hrid) {
        this.hrid = hrid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<String> getCaseRecordNoCount() {
        return caseRecordNoCount;
    }

    public void setCaseRecordNoCount(List<String> caseRecordNoCount) {
        this.caseRecordNoCount = caseRecordNoCount;
    }
}
