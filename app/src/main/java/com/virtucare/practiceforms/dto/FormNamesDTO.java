package com.virtucare.practiceforms.dto;

import java.util.List;

public class FormNamesDTO {

    private String status;
    private List<FormFieldsDTO> formNamesList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FormFieldsDTO> getFormNamesList() {
        return formNamesList;
    }

    public void setFormNamesList(List<FormFieldsDTO> formNamesList) {
        this.formNamesList = formNamesList;
    }
}
