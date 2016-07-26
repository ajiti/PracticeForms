package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by KH1748 on 04-Mar-16.
 */
public class PatientsListDTO {
    private int memcount  ;
    private int totalCount;
    private String status;
    private List<PatientDTO> HealthRegistrationList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PatientDTO> getHealthRegistrationList() {
        return HealthRegistrationList;
    }

    public void setHealthRegistrationList(List<PatientDTO> healthRegistrationList) {
        HealthRegistrationList = healthRegistrationList;
    }

    public int getMemcount() {
        return memcount;
    }

    public void setMemcount(int memcount) {
        this.memcount = memcount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
