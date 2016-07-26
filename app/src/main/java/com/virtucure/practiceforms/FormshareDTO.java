package com.virtucure.practiceforms;


import com.virtucare.practiceforms.dto.FormFieldsDTO;

import java.util.List;

/**
 * Created by Ajiti on 6/16/2016.
 */
public class FormshareDTO {
    private String status;
    private List<FormshareDetailsDTO> cidList;

    public List<FormshareDetailsDTO> getCidList() {

        return cidList;
    }
    public void setCidList(List<FormshareDetailsDTO> cidList) {
        this.cidList = cidList;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
