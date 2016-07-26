package com.virtucure.practiceforms;

import com.virtucare.practiceforms.dto.PatientDTO;

import java.util.Comparator;

/**
 * Created by AJITI on 6/25/2016.
 */
public class NameComparator implements Comparator<PatientDTO>
{
    @Override
    public int compare(PatientDTO p1, PatientDTO p2) {
        String p1Name=p1.getFirstName().length()>0?p1.getFirstName():p1.getLastName();
        String p2Name=p2.getFirstName().length()>0?p2.getFirstName():p2.getLastName();
        return p1Name.toUpperCase().compareTo(p2Name.toUpperCase());
    }
}