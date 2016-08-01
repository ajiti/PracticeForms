package com.virtucure.practiceforms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.CaseIdDTO;
import com.virtucare.practiceforms.dto.PatientDTO;

import java.util.List;

public class CaseIdAdaptor extends BaseAdapter {

    private List<CaseIdDTO> caseIdDTOs;
    private Activity activity;
    private LayoutInflater inflater;
    private PatientDTO patientDTO;

    public List<CaseIdDTO> getPatientDTOs() {
        return caseIdDTOs;
    }

    public void setPatientDTOs(List<CaseIdDTO> patientDTOs) {
        this.caseIdDTOs = patientDTOs;
    }

    @Override
    public int getCount() {
        return caseIdDTOs.size() ;
    }

    @Override
    public Object getItem(int position) {
        return caseIdDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.caseidlayout, null);
        }
        final CaseIdDTO caseIdDTO = caseIdDTOs.get(position);
        TextView patientName = (TextView) convertView.findViewById(R.id.patientName);
        patientName.setText(caseIdDTO.getPatientName());
        TextView finalDiagnosis = (TextView) convertView.findViewById(R.id.finaldiagnosis);
        finalDiagnosis.setText(caseIdDTO.getFinalDiagnosis());
        final TextView caserecord = (TextView) convertView.findViewById(R.id.caserecordno);
        caserecord.setText(caseIdDTO.getCaseRecordNo());
        TextView provisional = (TextView) convertView.findViewById(R.id.provisionalanalysis);
        provisional.setText(caseIdDTO.getProvisionalDiagnosis());
        Button viewCrFrms = (Button) convertView.findViewById(R.id.caserecordfrms);
        viewCrFrms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caserecordforms = new Intent(
                        v.getContext(),
                        CaseRecordFormsMainActivity.class);
                caserecordforms.putExtra("caserecordno",caseIdDTO.getCaseRecordNo());
                caserecordforms.putExtra("regid",patientDTO.getRegId());
                caserecordforms.putExtra("name" ,caseIdDTO.getPatientName());
                caserecordforms.putExtra("regLinkId", patientDTO.getSearchCid());
                caserecordforms.putExtra("proofType", patientDTO.getProofType());
                caserecordforms.putExtra("proofNumber", patientDTO.getProofNumber());
                caserecordforms.putExtra("dob", patientDTO.getDob());
                caserecordforms.putExtra("email", patientDTO.getEmailId());
                caserecordforms.putExtra("phone", patientDTO.getMobileNo());
                caserecordforms.putExtra("gender", patientDTO.getGender());
                v.getContext().startActivity(caserecordforms);
            }
        });
        return convertView;
    }

    public CaseIdAdaptor(Activity act, PatientDTO patientDTO) {
        this.activity = act;
        this.patientDTO = patientDTO;
        this.caseIdDTOs = patientDTO.getCaseIdsLIst();
    }

}
