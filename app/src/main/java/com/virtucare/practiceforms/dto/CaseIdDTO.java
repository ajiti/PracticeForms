package com.virtucare.practiceforms.dto;

/**
 * Created by KH1748 on 06-Mar-16.
 */
public class CaseIdDTO {

    private String patientName ;
    private String finalDiagnosis ;
    private String regnLinkId;
    private String provisionalDiagnosis;
    private String caseRecordNo;
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getFinalDiagnosis() {
        return finalDiagnosis;
    }

    public void setFinalDiagnosis(String finalDiagnosis) {
        this.finalDiagnosis = finalDiagnosis;
    }

    public String getRegnLinkId() {
        return regnLinkId;
    }

    public void setRegnLinkId(String regnLinkId) {
        this.regnLinkId = regnLinkId;
    }

    public String getProvisionalDiagnosis() {
        return provisionalDiagnosis;
    }

    public void setProvisionalDiagnosis(String provisionalDiagnosis) {
        this.provisionalDiagnosis = provisionalDiagnosis;
    }

    public String getCaseRecordNo() {
        return caseRecordNo;
    }

    public void setCaseRecordNo(String caseRecordNo) {
        this.caseRecordNo = caseRecordNo;
    }

}
