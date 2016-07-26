package com.virtucare.practiceforms.dto;

/**
 * Created by AJITI on 6/30/2016.
 */
public class ReferredPatientReferredTimeDTO {

    private String id;
    private String sharedType;
    private String sharedDate;
    private String sharedId;
    private String formIdsList;
    private Integer[] formIdsArray;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSharedType() {
        return sharedType;
    }

    public void setSharedType(String sharedType) {
        this.sharedType = sharedType;
    }

    public String getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(String sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getSharedId() {
        return sharedId;
    }

    public void setSharedId(String sharedId) {
        this.sharedId = sharedId;
    }

    public String getFormIdsList() {
        return formIdsList;
    }

    public void setFormIdsList(String formIdsList) {
        this.formIdsList = formIdsList;
    }

    public Integer[] getFormIdsArray() {
        return formIdsArray;
    }

    public void setFormIdsArray(Integer[] formIdsArray) {
        this.formIdsArray = formIdsArray;
    }
}
