package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by AJITI on 6/30/2016.
 */
public class ReferredPatientHridCaseRecordDTO {

    private String caseRecordNo;
    private String provisionalDiagnosis;
    private List<String> sharedDateCount;

    public String getCaseRecordNo() {
        return caseRecordNo;
    }

    public void setCaseRecordNo(String caseRecordNo) {
        this.caseRecordNo = caseRecordNo;
    }

    public String getProvisionalDiagnosis() {
        return provisionalDiagnosis;
    }

    public void setProvisionalDiagnosis(String provisionalDiagnosis) {
        this.provisionalDiagnosis = provisionalDiagnosis;
    }

    public List<String> getSharedDateCount() {
        return sharedDateCount;
    }

    public void setSharedDateCount(List<String> sharedDateCount) {
        this.sharedDateCount = sharedDateCount;
    }
}
