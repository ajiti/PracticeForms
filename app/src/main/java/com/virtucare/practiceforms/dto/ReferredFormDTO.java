package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by AJITI on 7/1/2016.
 */
public class ReferredFormDTO {

    private String status;
    private CommonDataDTO commonData;
    private List<FormDataDTO> caseRecordsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommonDataDTO getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonDataDTO commonData) {
        this.commonData = commonData;
    }

    public List<FormDataDTO> getCaseRecordsList() {
        return caseRecordsList;
    }

    public void setCaseRecordsList(List<FormDataDTO> caseRecordsList) {
        this.caseRecordsList = caseRecordsList;
    }
}
