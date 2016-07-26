package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by AJITI on 6/28/2016.
 */
public class ReferredPatientsDTO {

    private List<String> brandorBranchNameCount;
    private String displayProjName;
    private String projectType;

    private String brandorBranchName;
    private String address;
    private String mobilenumber;
    private String state;
    private String userName;
    private String lastname;
    private String firstname;
    private String cid;
    private String country;
    private String emailid;
    private List<String> hridCount;

    public List<String> getBrandorBranchNameCount() {
        return brandorBranchNameCount;
    }

    public void setBrandorBranchNameCount(List<String> brandorBranchNameCount) {
        this.brandorBranchNameCount = brandorBranchNameCount;
    }

    public String getDisplayProjName() {
        return displayProjName;
    }

    public void setDisplayProjName(String displayProjName) {
        this.displayProjName = displayProjName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBrandorBranchName() {
        return brandorBranchName;
    }

    public void setBrandorBranchName(String brandorBranchName) {
        this.brandorBranchName = brandorBranchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public List<String> getHridCount() {
        return hridCount;
    }

    public void setHridCount(List<String> hridCount) {
        this.hridCount = hridCount;
    }
}
