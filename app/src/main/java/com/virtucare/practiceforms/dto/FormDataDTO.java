package com.virtucare.practiceforms.dto;

/**
 * Created by AJITI on 7/1/2016.
 */
public class FormDataDTO {

    private String formCreatedDate;
    private String caseRecordNo;
    private String formId;
    private String practiceFormName;
    private String userName;
    private String cId;
    private String doctorName;
    private FormDetailsDTO caseRecordData;

    public String getFormCreatedDate() {
        return formCreatedDate;
    }

    public void setFormCreatedDate(String formCreatedDate) {
        this.formCreatedDate = formCreatedDate;
    }

    public String getCaseRecordNo() {
        return caseRecordNo;
    }

    public void setCaseRecordNo(String caseRecordNo) {
        this.caseRecordNo = caseRecordNo;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getPracticeFormName() {
        return practiceFormName;
    }

    public void setPracticeFormName(String practiceFormName) {
        this.practiceFormName = practiceFormName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public FormDetailsDTO getCaseRecordData() {
        return caseRecordData;
    }

    public void setCaseRecordData(FormDetailsDTO caseRecordData) {
        this.caseRecordData = caseRecordData;
    }
}
