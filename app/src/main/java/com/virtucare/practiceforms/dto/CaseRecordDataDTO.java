package com.virtucare.practiceforms.dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AJITI on 5/9/2016.
 */
public class CaseRecordDataDTO {

    private int count;
    private ArrayList<Object> caseRecordsList;
    private HashMap<String, String> commonData;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Object> getCaseRecordsList() {
        return caseRecordsList;
    }

    public void setCaseRecordsList(ArrayList<Object> caseRecordsList) {
        this.caseRecordsList = caseRecordsList;
    }

    public HashMap<String, String> getCommonData() {
        return commonData;
    }

    public void setCommonData(HashMap<String, String> commonData) {
        this.commonData = commonData;
    }
}
