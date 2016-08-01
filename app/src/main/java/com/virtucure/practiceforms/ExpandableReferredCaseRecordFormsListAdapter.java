package com.virtucure.practiceforms;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.FormDataDTO;
import com.virtucare.practiceforms.dto.FormDetailsDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableReferredCaseRecordFormsListAdapter extends BaseExpandableListAdapter{

    private static final String TAG = ExpandableReferredCaseRecordFormsListAdapter.class.getSimpleName();
    private Context context;
    private Toolbar toolbar;
    private Map<String, Integer> formLayoutIds;
    private List<FormDataDTO> groupList;
    private FormDataDTO formDataDTO;
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10;
    private TextView txtValView1, txtValView2, txtValView3, txtValView4, txtValView5, txtValView6, txtValView7, txtValView8, txtValView9, txtValView10;
    private Button saveBtn;
    private RadioGroup radioGroups[];

    public ExpandableReferredCaseRecordFormsListAdapter(Context context, List<FormDataDTO> groupList) {
        this.context = context;
        this.groupList = groupList;
        formLayoutIds = loadFormLayoutIds();
    }

    @Override
    public int getGroupCount() {
        return (groupList != null) ? groupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return (groupList != null) ? groupList.get(groupPosition) : 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getCaseRecordData();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.caserecordforms_list_group, null);
            convertView.findViewById(R.id.formShareCheckBox).setVisibility(View.GONE);
        }

        formDataDTO = (FormDataDTO) getGroup(groupPosition);

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(formDataDTO.getCaseRecordData().getDisplayFormName());
        lblListHeader.setBackgroundColor(Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String formName = ((FormDataDTO) getGroup(groupPosition)).getPracticeFormName();
        FormDetailsDTO formDetailsDTO = (FormDetailsDTO) getChild(groupPosition, childPosition);
        View v = null;

        try {
            v = View.inflate(context, getFormLayoutId(formName), null);

            if(v != null && formDetailsDTO != null && ("dentalassessment".equals(formName) || "generalhealthscreening".equals(formName))){
                txtValView1 = (TextView) v.findViewById(R.id.ques1);
                txtValView2 = (TextView) v.findViewById(R.id.ques2);
                txtValView3 = (TextView) v.findViewById(R.id.ques3);
                txtValView4 = (TextView) v.findViewById(R.id.ques4);
                txtValView5 = (TextView) v.findViewById(R.id.ques5);
                txtValView6 = (TextView) v.findViewById(R.id.ques6);
                txtValView7 = (TextView) v.findViewById(R.id.ques7);
                txtValView8 = (TextView) v.findViewById(R.id.ques8);
                txtValView9 = (TextView) v.findViewById(R.id.ques9);
                txtValView10 = (TextView) v.findViewById(R.id.ques10);
                txtValView1.setVisibility(View.VISIBLE);
                txtValView2.setVisibility(View.VISIBLE);
                txtValView3.setVisibility(View.VISIBLE);
                txtValView4.setVisibility(View.VISIBLE);
                txtValView5.setVisibility(View.VISIBLE);
                txtValView6.setVisibility(View.VISIBLE);
                txtValView7.setVisibility(View.VISIBLE);
                txtValView8.setVisibility(View.VISIBLE);
                txtValView9.setVisibility(View.VISIBLE);
                txtValView10.setVisibility(View.VISIBLE);
            }

            if("historytaking".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                TextView txtView = (TextView) v.findViewById(R.id.presentIllness);
                preventTextViewClick(txtView);
                txtView.setText(formDetailsDTO.getHistFormPresentIllNess());
                TextView txtView2 = (TextView) v.findViewById(R.id.previousIlliness);
                preventTextViewClick(txtView2);
                txtView2.setText(formDetailsDTO.getHistFormPreviousIllness());
                TextView txtView3 = (TextView) v.findViewById(R.id.drugHistory);
                preventTextViewClick(txtView3);
                txtView3.setText(formDetailsDTO.getHistFormDrug());
                TextView txtView4 = (TextView) v.findViewById(R.id.allergies);
                preventTextViewClick(txtView4);
                txtView4.setText(formDetailsDTO.getHistFormAllergiesandReactions());
                TextView txtView5 = (TextView) v.findViewById(R.id.personalHistory);
                preventTextViewClick(txtView5);
                txtView5.setText(formDetailsDTO.getHistFormPersonal());
                TextView txtView6 = (TextView) v.findViewById(R.id.menstrualhistory);
                preventTextViewClick(txtView6);
                txtView6.setText(formDetailsDTO.getHistFormMenstrual());
                TextView txtView7 = (TextView) v.findViewById(R.id.socialhistory);
                preventTextViewClick(txtView7);
                txtView7.setText(formDetailsDTO.getHistFormSocial());
                TextView txtView8 = (TextView) v.findViewById(R.id.familyhistory);
                preventTextViewClick(txtView8);
                txtView8.setText(formDetailsDTO.getHistFormFamily());
                TextView txtView9 = (TextView) v.findViewById(R.id.systemreview);
                preventTextViewClick(txtView9);
                txtView9.setText(formDetailsDTO.getHistFormSystemsReview());
                TextView txtView10 = (TextView) v.findViewById(R.id.summary);
                preventTextViewClick(txtView10);
                txtView10.setText(formDetailsDTO.getHistFormSummary());
                TextView txtView11 = (TextView) v.findViewById(R.id.htpdlbl);
                txtView11.setVisibility(View.GONE);
                txtView11.setText(formDetailsDTO.getHistFormSummary());
                TextView txtView12 = (TextView) v.findViewById(R.id.provisionalDiagnosis);
                txtView12.setVisibility(View.GONE);
                txtView12.setText(formDetailsDTO.getHistFormPresentIllNess());
                TextView txtView13 = (TextView) v.findViewById(R.id.htfdlbl);
                txtView13.setVisibility(View.GONE);
                TextView txtView14 = (TextView) v.findViewById(R.id.finalDiagnosis);
                txtView14.setVisibility(View.GONE);
                saveBtn = (Button) v.findViewById(R.id.button);
            }
            else if("dentalassessment".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                rg1 = (RadioGroup) v.findViewById(R.id.denradiogroup1);
                rg2 = (RadioGroup) v.findViewById(R.id.denradiogroup2);
                rg3 = (RadioGroup) v.findViewById(R.id.denradiogroup3);
                rg4 = (RadioGroup) v.findViewById(R.id.denradiogroup4);
                rg5 = (RadioGroup) v.findViewById(R.id.denradiogroup5);
                rg6 = (RadioGroup) v.findViewById(R.id.denradiogroup6);
                rg7 = (RadioGroup) v.findViewById(R.id.denradiogroup7);
                rg8 = (RadioGroup) v.findViewById(R.id.denradiogroup8);
                rg9 = (RadioGroup) v.findViewById(R.id.denradiogroup9);
                rg10 = (RadioGroup) v.findViewById(R.id.denradiogroup10);

                radioGroups = new RadioGroup[]{rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10};
                hideRadioGroup(radioGroups);

                txtValView1.setText(getBooleanInText(formDetailsDTO.getDentFormPainInTeeth()));
                txtValView2.setText(getBooleanInText(formDetailsDTO.getDentFormSevereSensitivityOfTeethOnDrinks()));
                txtValView3.setText(getBooleanInText(formDetailsDTO.getDentFormPainorBleedingInGums()));
                txtValView4.setText(getBooleanInText(formDetailsDTO.getDentFormHaveLooseTeethWithGaps()));
                txtValView5.setText(getBooleanInText(formDetailsDTO.getDentFormHaveUnpleasantTaste()));
                txtValView6.setText(getBooleanInText(formDetailsDTO.getDentFormDoGrindYourTeethWhenSleep()));
                txtValView7.setText(getBooleanInText(formDetailsDTO.getDentFormHaveSwellingInGumsWithFluid()));
                txtValView8.setText(getBooleanInText(formDetailsDTO.getDentFormSeverePainInTeethAtNight()));
                txtValView9.setText(getBooleanInText(formDetailsDTO.getDentFormHavePainnAndSwellingInGums()));
                txtValView10.setText(getBooleanInText(formDetailsDTO.getDentFormHaveIrregularPlacedTeeth()));
                saveBtn = (Button) v.findViewById(R.id.dentalsave);
            }
            else if("generalhealthscreening".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                rg1 = (RadioGroup) v.findViewById(R.id.ghsradiogroup1);
                rg2 = (RadioGroup) v.findViewById(R.id.ghsradiogroup2);
                rg3 = (RadioGroup) v.findViewById(R.id.ghsradiogroup3);
                rg4 = (RadioGroup) v.findViewById(R.id.ghsradiogroup4);
                rg5 = (RadioGroup) v.findViewById(R.id.ghsradiogroup5);
                rg6 = (RadioGroup) v.findViewById(R.id.ghsradiogroup6);
                rg7 = (RadioGroup) v.findViewById(R.id.ghsradiogroup7);
                rg8 = (RadioGroup) v.findViewById(R.id.ghsradiogroup8);
                rg9 = (RadioGroup) v.findViewById(R.id.ghsradiogroup9);
                rg10 = (RadioGroup) v.findViewById(R.id.ghsradiogroup10);

                radioGroups = new RadioGroup[]{rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10};
                hideRadioGroup(radioGroups);

                txtValView1.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveDifficultiesInSeeingObjects()));
                txtValView2.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveHearingTrouble()));
                txtValView3.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormSufferWithFrequentEpisodesOfCold()));
                txtValView4.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveSkinAllergies()));
                txtValView5.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveLearningDifficulties()));
                txtValView6.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveHistoryOfFits()));
                txtValView7.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveHistoryOfBurningChest()));
                txtValView8.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveHighBloodPressure()));
                txtValView9.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveDiabetes()));
                txtValView10.setText(getBooleanInText(formDetailsDTO.getHlthScrnFormHaveDentalProblems()));
                saveBtn = (Button) v.findViewById(R.id.gensave);
            }
            else if("generalphysicalexamination".equals(formName) && formDetailsDTO != null && v != null){
                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                TextView textView = (TextView)v.findViewById(R.id.consciousness);
                preventTextViewClick(textView);
                textView.setText(formDetailsDTO.getGenFormConseiousness());
                TextView textView1 = (TextView)v.findViewById(R.id.mental_state);
                preventTextViewClick(textView1);
                textView1.setText(formDetailsDTO.getGenFormMentalState());
                TextView textView2 = (TextView)v.findViewById(R.id.general_appearance);
                preventTextViewClick(textView2);
                textView2.setText(formDetailsDTO.getGenFormGeneralAppearance());
                TextView textView3 = (TextView)v.findViewById(R.id.pallor);
                preventTextViewClick(textView3);
                textView3.setText(formDetailsDTO.getGenFormPallor());
                TextView textView4 = (TextView)v.findViewById(R.id.icterus);
                preventTextViewClick(textView4);
                textView4.setText(formDetailsDTO.getGenFormIcterus());
                TextView textView5 = (TextView)v.findViewById(R.id.cyanosis);
                preventTextViewClick(textView5);
                textView5.setText(formDetailsDTO.getGenFormCyanosis());
                TextView textView6 = (TextView)v.findViewById(R.id.clubbing);
                preventTextViewClick(textView6);
                textView6.setText(formDetailsDTO.getGenFormClubbing());
                TextView textView7 = (TextView)v.findViewById(R.id.lymph_adenopathy);
                preventTextViewClick(textView7);
                textView7.setText(formDetailsDTO.getGenFormLymphAdenopathy());
                TextView textView8 = (TextView)v.findViewById(R.id.edema);
                preventTextViewClick(textView8);
                textView8.setText(formDetailsDTO.getGenFormEdema());
                TextView textView9 = (TextView)v.findViewById(R.id.temperature);
                preventTextViewClick(textView9);
                textView9.setText(formDetailsDTO.getGenFormTemparature());
                TextView textView10 = (TextView)v.findViewById(R.id.pulse);
                preventTextViewClick(textView10);
                textView10.setText(formDetailsDTO.getGenFormPulse());
                TextView textView11 = (TextView)v.findViewById(R.id.respiration);
                preventTextViewClick(textView11);
                textView11.setText(formDetailsDTO.getGenFormRepisration());
                TextView textView12 = (TextView)v.findViewById(R.id.blood_pressure);
                preventTextViewClick(textView12);
                textView12.setText(formDetailsDTO.getGenFormBloodPressure());
                TextView textView13 = (TextView)v.findViewById(R.id.height);
                preventTextViewClick(textView13);
                textView13.setText(formDetailsDTO.getGenFormHeight());
                TextView textView14 = (TextView)v.findViewById(R.id.weight);
                preventTextViewClick(textView14);
                textView14.setText(formDetailsDTO.getGenFormWeight());
                TextView textView15 = (TextView)v.findViewById(R.id.bmi);
                preventTextViewClick(textView15);
                textView15.setText(formDetailsDTO.getGenFormBodyMassIndex());
                TextView textView16 = (TextView)v.findViewById(R.id.mac);
                preventTextViewClick(textView16);
                textView16.setText(formDetailsDTO.getGenFormChildMidArmCircumference());
                TextView textView17 = (TextView)v.findViewById(R.id.hc);
                preventTextViewClick(textView17);
                textView17.setText(formDetailsDTO.getGenFormChildHeadCircumference());
            }
            else if("dischargesheet".equals(formName) && formDetailsDTO != null && v != null){
                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                TextView textView = (TextView)v.findViewById(R.id.method_Admission);
                preventTextViewClick(textView);
                textView.setText(formDetailsDTO.getDischFormMethodofAdmission());
                TextView textView1 = (TextView)v.findViewById(R.id.source_Admission);
                preventTextViewClick(textView1);
                textView1.setText(formDetailsDTO.getDischFormSourceofAdmission());
                TextView textView2 = (TextView)v.findViewById(R.id.hospital_Site);
                preventTextViewClick(textView2);
                textView2.setText(formDetailsDTO.getDischFormHospitalSite());
                TextView textView3 = (TextView)v.findViewById(R.id.responsible_Trust);
                preventTextViewClick(textView3);
                textView3.setText(formDetailsDTO.getDischFormResponsibleTrust());
                TextView textView4 = (TextView)v.findViewById(R.id.date_Admission);
                preventTextViewClick(textView4);
                textView4.setText(formDetailsDTO.getDischFormAdmissionDate());
                TextView textView5 = (TextView)v.findViewById(R.id.time_Admission);
                preventTextViewClick(textView5);
                textView5.setText(formDetailsDTO.getDischFormAdmissionTime());
                TextView textView6 = (TextView)v.findViewById(R.id.date_Discharge);
                preventTextViewClick(textView6);
                textView6.setText(formDetailsDTO.getDischFormDischargeDate());
                TextView textView7 = (TextView)v.findViewById(R.id.time_Discharge);
                preventTextViewClick(textView7);
                textView7.setText(formDetailsDTO.getDischFormDischargeTime());
                TextView textView8 = (TextView)v.findViewById(R.id.discharge_Method);
                preventTextViewClick(textView8);
                textView8.setText(formDetailsDTO.getDischFormDischargeMethod());
                TextView textView9 = (TextView)v.findViewById(R.id.discharge_Destination);
                preventTextViewClick(textView9);
                textView9.setText(formDetailsDTO.getDischFormDischargeDestination());
                TextView textView10 = (TextView)v.findViewById(R.id.discharge_Consultant);
                preventTextViewClick(textView10);
                textView10.setText(formDetailsDTO.getDischFormDischargingConsultant());
                TextView textView11 = (TextView)v.findViewById(R.id.dischargeSpecialty_department);
                preventTextViewClick(textView11);
                textView11.setText(formDetailsDTO.getDischFormDischargingSpeciality());
                TextView textView12 = (TextView)v.findViewById(R.id.diagnosis_discharge);
                preventTextViewClick(textView12);
                textView12.setText(formDetailsDTO.getDischFormDiagnosisAtDischarge());
                TextView textView13 = (TextView)v.findViewById(R.id.operations_procedures);
                preventTextViewClick(textView13);
                textView13.setText(formDetailsDTO.getDischFormOperationsandProcedures());
                TextView textView14 = (TextView)v.findViewById(R.id.admission_complaints);
                preventTextViewClick(textView14);
                textView14.setText(formDetailsDTO.getDischFormAdmissionReasonandComplaints());
                TextView textView15 = (TextView)v.findViewById(R.id.mental_capacity);
                preventTextViewClick(textView15);
                textView15.setText(formDetailsDTO.getDischFormMentalCapacity());
                TextView textView16 = (TextView)v.findViewById(R.id.treatment_resuscitation);
                preventTextViewClick(textView16);
                textView16.setText(formDetailsDTO.getDischFormRefuseTreatmentandStatus());
                TextView textView17 = (TextView)v.findViewById(R.id.allergies);
                preventTextViewClick(textView17);
                textView17.setText(formDetailsDTO.getDischFormAllergies());
                TextView textView18 = (TextView)v.findViewById(R.id.risks_warnings);
                preventTextViewClick(textView18);
                textView18.setText(formDetailsDTO.getDischFormRisksandWarnings());
                TextView textView19 = (TextView)v.findViewById(R.id.clinical_narrative);
                preventTextViewClick(textView19);
                textView19.setText(formDetailsDTO.getDischFormClinicalNarrative());
                TextView textView20 = (TextView)v.findViewById(R.id.investigations_results);
                preventTextViewClick(textView20);
                textView20.setText(formDetailsDTO.getDischFormInvestigationsandResults());
                TextView textView21 = (TextView)v.findViewById(R.id.treatment_treatments);
                preventTextViewClick(textView21);
                textView21.setText(formDetailsDTO.getDischFormTreatmentsandChanges());
                TextView textView22 = (TextView)v.findViewById(R.id.physical_cognitive);
                preventTextViewClick(textView22);
                textView22.setText(formDetailsDTO.getDischFormMeasuresofPhysicalAbility());
                TextView textView23 = (TextView)v.findViewById(R.id.medication_changes);
                preventTextViewClick(textView23);
                textView23.setText(formDetailsDTO.getDischFormMedicationChanges());
                TextView textView24 = (TextView)v.findViewById(R.id.discharge_medications);
                preventTextViewClick(textView24);
                textView24.setText(formDetailsDTO.getDischFormDischargeMedications());
                TextView textView25 = (TextView)v.findViewById(R.id.medication_recommendations);
                preventTextViewClick(textView25);
                textView25.setText(formDetailsDTO.getDischFormMedicationRecommendations());
                TextView textView26 = (TextView)v.findViewById(R.id.hospital_actions);
                preventTextViewClick(textView26);
                textView26.setText(formDetailsDTO.getDischFormHospitalActionsRequired());
                TextView textView27 = (TextView)v.findViewById(R.id.gp_actions);
                preventTextViewClick(textView27);
                textView27.setText(formDetailsDTO.getDischFormGPActionsRequired());
                TextView textView28 = (TextView)v.findViewById(R.id.community_specialist);
                preventTextViewClick(textView28);
                textView28.setText(formDetailsDTO.getDischFormSpecialistServices());
                TextView textView29 = (TextView)v.findViewById(R.id.information_patient_authorized);
                preventTextViewClick(textView29);
                textView29.setText(formDetailsDTO.getDischFormInfoGivenToPatient());
                TextView textView30 = (TextView)v.findViewById(R.id.patient_con_exp_wishes);
                preventTextViewClick(textView30);
                textView30.setText(formDetailsDTO.getDischFormPatientConcernsandExpectations());
                TextView textView31 = (TextView)v.findViewById(R.id.results_awaited);
                preventTextViewClick(textView31);
                textView31.setText(formDetailsDTO.getDischFormAwaitedResults());
            }
            else if("investigationmicrobiology".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                CheckBox ch1 = (CheckBox) v.findViewById(R.id.mrch1);
                CheckBox ch2 = (CheckBox) v.findViewById(R.id.mrch2);
                CheckBox ch3 = (CheckBox) v.findViewById(R.id.mrch3);
                CheckBox ch4 = (CheckBox) v.findViewById(R.id.mrch4);
                CheckBox ch5 = (CheckBox) v.findViewById(R.id.mrch5);
                CheckBox ch6 = (CheckBox) v.findViewById(R.id.mrch6);
                CheckBox ch7 = (CheckBox) v.findViewById(R.id.mrch7);
                CheckBox ch8 = (CheckBox) v.findViewById(R.id.mrch8);
                CheckBox ch9 = (CheckBox) v.findViewById(R.id.mrch9);
                CheckBox ch10 = (CheckBox)v.findViewById(R.id.mrch10);
                CheckBox ch11 = (CheckBox) v.findViewById(R.id.mrch11);
                CheckBox ch12 = (CheckBox) v.findViewById(R.id.mrch12);
                CheckBox ch13 = (CheckBox) v.findViewById(R.id.mrch13);
                CheckBox ch14 = (CheckBox) v.findViewById(R.id.mrch14);
                CheckBox ch15 = (CheckBox) v.findViewById(R.id.mrch15);
                CheckBox ch16 = (CheckBox) v.findViewById(R.id.mrch16);
                CheckBox ch17 = (CheckBox) v.findViewById(R.id.mrch17);
                CheckBox ch18 = (CheckBox) v.findViewById(R.id.mrch18);
                CheckBox ch19 = (CheckBox) v.findViewById(R.id.mrch19);
                CheckBox ch20 = (CheckBox) v.findViewById(R.id.mrch20);
                CheckBox ch21 = (CheckBox) v.findViewById(R.id.mrch21);
                CheckBox ch22 = (CheckBox) v.findViewById(R.id.mrch22);
                CheckBox ch23 = (CheckBox) v.findViewById(R.id.mrch23);
                CheckBox ch24 = (CheckBox) v.findViewById(R.id.mrch24);
                CheckBox ch25 = (CheckBox) v.findViewById(R.id.mrch25);
                CheckBox ch26 = (CheckBox) v.findViewById(R.id.mrch26);
                CheckBox ch27 = (CheckBox) v.findViewById(R.id.mrch27);
                CheckBox ch28 = (CheckBox) v.findViewById(R.id.mrch28);
                CheckBox ch29 = (CheckBox) v.findViewById(R.id.mrch29);
                CheckBox ch30 = (CheckBox) v.findViewById(R.id.mrch30);

                ch1.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormGramsStaining()));
                ch2.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormZNStaining()));
                ch3.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormAlbertsStaining()));
                ch4.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormSalineSmear()));
                ch5.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormKOHSmear()));
                ch6.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormLactophenoSmearl()));
                ch7.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormIndiaInkStaining()));
                ch8.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormStoolForParasites()));
                ch9.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormSmearOfGonococci()));
                ch10.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormSmearOfFungi()));
                ch11.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormBloodSmearParaSites()));
                ch12.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormSmearOfHansens()));
                ch13.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormWidalTest()));
                ch14.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormRF()));
                ch15.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormASO()));
                ch16.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormCRP()));
                ch17.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormVDRL()));
                ch18.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormHBsag()));
                ch19.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormHCV()));
                ch20.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormHIV()));
                ch21.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormANA()));
                ch22.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormAntiTBIgA()));
                ch23.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormAntiTBIgM()));
                ch24.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormAntiTBIgG()));
                ch25.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormAntiLeptoSpiraIgM()));
                ch26.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormFungalCulture()));
                ch27.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormBacterialCulture()));
                ch28.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormBloodCulture()));
                ch29.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormEntricFever()));
                ch30.setChecked(getBoolean(formDetailsDTO.getInvstMicroFormTuberculosisCulture()));

                ch1.setClickable(Boolean.FALSE);
                ch2.setClickable(Boolean.FALSE);
                ch3.setClickable(Boolean.FALSE);
                ch4.setClickable(Boolean.FALSE);
                ch5.setClickable(Boolean.FALSE);
                ch6.setClickable(Boolean.FALSE);
                ch7.setClickable(Boolean.FALSE);
                ch8.setClickable(Boolean.FALSE);
                ch9.setClickable(Boolean.FALSE);
                ch10.setClickable(Boolean.FALSE);
                ch11.setClickable(Boolean.FALSE);
                ch12.setClickable(Boolean.FALSE);
                ch13.setClickable(Boolean.FALSE);
                ch14.setClickable(Boolean.FALSE);
                ch15.setClickable(Boolean.FALSE);
                ch16.setClickable(Boolean.FALSE);
                ch17.setClickable(Boolean.FALSE);
                ch18.setClickable(Boolean.FALSE);
                ch19.setClickable(Boolean.FALSE);
                ch20.setClickable(Boolean.FALSE);
                ch21.setClickable(Boolean.FALSE);
                ch22.setClickable(Boolean.FALSE);
                ch23.setClickable(Boolean.FALSE);
                ch24.setClickable(Boolean.FALSE);
                ch25.setClickable(Boolean.FALSE);
                ch26.setClickable(Boolean.FALSE);
                ch27.setClickable(Boolean.FALSE);
                ch28.setClickable(Boolean.FALSE);
                ch29.setClickable(Boolean.FALSE);
                ch30.setClickable(Boolean.FALSE);

                ch1.setVisibility(ch1.isChecked() ? View.VISIBLE : View.GONE);
                ch2.setVisibility(ch2.isChecked() ? View.VISIBLE : View.GONE);
                ch3.setVisibility(ch3.isChecked() ? View.VISIBLE : View.GONE);
                ch4.setVisibility(ch4.isChecked() ? View.VISIBLE : View.GONE);
                ch5.setVisibility(ch5.isChecked() ? View.VISIBLE : View.GONE);
                ch6.setVisibility(ch6.isChecked() ? View.VISIBLE : View.GONE);
                ch7.setVisibility(ch7.isChecked() ? View.VISIBLE : View.GONE);
                ch8.setVisibility(ch8.isChecked() ? View.VISIBLE : View.GONE);
                ch9.setVisibility(ch9.isChecked() ? View.VISIBLE : View.GONE);
                ch10.setVisibility(ch10.isChecked() ? View.VISIBLE : View.GONE);
                ch11.setVisibility(ch11.isChecked() ? View.VISIBLE : View.GONE);
                ch12.setVisibility(ch12.isChecked() ? View.VISIBLE : View.GONE);
                ch13.setVisibility(ch13.isChecked() ? View.VISIBLE : View.GONE);
                ch14.setVisibility(ch14.isChecked() ? View.VISIBLE : View.GONE);
                ch15.setVisibility(ch15.isChecked() ? View.VISIBLE : View.GONE);
                ch16.setVisibility(ch16.isChecked() ? View.VISIBLE : View.GONE);
                ch17.setVisibility(ch17.isChecked() ? View.VISIBLE : View.GONE);
                ch18.setVisibility(ch18.isChecked() ? View.VISIBLE : View.GONE);
                ch19.setVisibility(ch19.isChecked() ? View.VISIBLE : View.GONE);
                ch20.setVisibility(ch20.isChecked() ? View.VISIBLE : View.GONE);
                ch21.setVisibility(ch21.isChecked() ? View.VISIBLE : View.GONE);
                ch22.setVisibility(ch22.isChecked() ? View.VISIBLE : View.GONE);
                ch23.setVisibility(ch23.isChecked() ? View.VISIBLE : View.GONE);
                ch24.setVisibility(ch24.isChecked() ? View.VISIBLE : View.GONE);
                ch25.setVisibility(ch25.isChecked() ? View.VISIBLE : View.GONE);
                ch26.setVisibility(ch26.isChecked() ? View.VISIBLE : View.GONE);
                ch27.setVisibility(ch27.isChecked() ? View.VISIBLE : View.GONE);
                ch28.setVisibility(ch28.isChecked() ? View.VISIBLE : View.GONE);
                ch29.setVisibility(ch29.isChecked() ? View.VISIBLE : View.GONE);
                ch30.setVisibility(ch30.isChecked() ? View.VISIBLE : View.GONE);

                saveBtn = (Button) v.findViewById(R.id.imbsave);

            }else if("investigationbiochemistry".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                CheckBox ch1 = (CheckBox) v.findViewById(R.id.rch1);
                CheckBox ch2 = (CheckBox) v.findViewById(R.id.rch2);
                CheckBox ch3 = (CheckBox) v.findViewById(R.id.rch3);
                CheckBox ch4 = (CheckBox) v.findViewById(R.id.rch4);
                CheckBox ch5 = (CheckBox) v.findViewById(R.id.rch5);
                CheckBox ch6 = (CheckBox) v.findViewById(R.id.rch6);
                CheckBox ch7 = (CheckBox) v.findViewById(R.id.rch7);
                CheckBox ch8 = (CheckBox) v.findViewById(R.id.rch8);
                CheckBox ch9 = (CheckBox) v.findViewById(R.id.rch9);
                CheckBox ch10 = (CheckBox)v.findViewById(R.id.rch10);
                CheckBox ch11 = (CheckBox) v.findViewById(R.id.rch11);
                CheckBox ch12 = (CheckBox) v.findViewById(R.id.rch12);
                CheckBox ch13 = (CheckBox) v.findViewById(R.id.rch13);
                CheckBox ch14 = (CheckBox) v.findViewById(R.id.rch14);
                CheckBox ch15 = (CheckBox) v.findViewById(R.id.rch15);
                CheckBox ch16 = (CheckBox) v.findViewById(R.id.rch16);
                CheckBox ch17 = (CheckBox) v.findViewById(R.id.rch17);
                CheckBox ch18 = (CheckBox) v.findViewById(R.id.rch18);
                CheckBox ch19 = (CheckBox) v.findViewById(R.id.rch19);
                CheckBox ch20 = (CheckBox) v.findViewById(R.id.rch20);
                CheckBox ch21 = (CheckBox) v.findViewById(R.id.rch21);
                CheckBox ch22 = (CheckBox) v.findViewById(R.id.rch22);
                CheckBox ch23 = (CheckBox) v.findViewById(R.id.rch23);
                CheckBox ch24 = (CheckBox) v.findViewById(R.id.rch24);
                CheckBox ch25 = (CheckBox) v.findViewById(R.id.rch25);
                CheckBox ch26 = (CheckBox) v.findViewById(R.id.rch26);
                CheckBox ch27 = (CheckBox) v.findViewById(R.id.rch27);
                CheckBox ch28 = (CheckBox) v.findViewById(R.id.rch28);
                CheckBox ch29 = (CheckBox) v.findViewById(R.id.rch29);
                CheckBox ch30 = (CheckBox) v.findViewById(R.id.rch30);
                CheckBox ch31 = (CheckBox) v.findViewById(R.id.rch31);
                CheckBox ch32 = (CheckBox) v.findViewById(R.id.pch1);
                CheckBox ch33 = (CheckBox) v.findViewById(R.id.pch2);
                CheckBox ch34 = (CheckBox) v.findViewById(R.id.pch3);
                CheckBox ch35 = (CheckBox) v.findViewById(R.id.pch4);
                CheckBox ch36 = (CheckBox) v.findViewById(R.id.pch5);
                CheckBox ch37 = (CheckBox) v.findViewById(R.id.pch6);
                CheckBox ch38 = (CheckBox) v.findViewById(R.id.pch7);
                CheckBox ch39 = (CheckBox) v.findViewById(R.id.pch8);
                CheckBox ch40 = (CheckBox) v.findViewById(R.id.pch9);
                CheckBox ch41 = (CheckBox) v.findViewById(R.id.pch10);
                CheckBox ch42 = (CheckBox) v.findViewById(R.id.pch11);
                CheckBox ch43 = (CheckBox) v.findViewById(R.id.imch1);
                CheckBox ch44 = (CheckBox) v.findViewById(R.id.imch2);
                CheckBox ch45 = (CheckBox) v.findViewById(R.id.imch3);
                CheckBox ch46 = (CheckBox) v.findViewById(R.id.imch4);
                CheckBox ch47 = (CheckBox) v.findViewById(R.id.imch5);
                CheckBox ch48 = (CheckBox) v.findViewById(R.id.imch6);
                CheckBox ch49 = (CheckBox) v.findViewById(R.id.imch7);
                CheckBox ch50 = (CheckBox) v.findViewById(R.id.imch8);
                CheckBox ch51 = (CheckBox) v.findViewById(R.id.imch9);
                CheckBox ch52 = (CheckBox) v.findViewById(R.id.imch10);
                CheckBox ch53 = (CheckBox) v.findViewById(R.id.imch11);
                CheckBox ch54 = (CheckBox) v.findViewById(R.id.imch12);
                CheckBox ch55 = (CheckBox) v.findViewById(R.id.fch1);
                CheckBox ch56 = (CheckBox) v.findViewById(R.id.fch2);
                CheckBox ch57 = (CheckBox) v.findViewById(R.id.fch3);
                CheckBox ch58 = (CheckBox) v.findViewById(R.id.fch5);
                CheckBox ch59 = (CheckBox) v.findViewById(R.id.fch6);
                CheckBox ch60 = (CheckBox) v.findViewById(R.id.fch7);
                CheckBox ch61 = (CheckBox) v.findViewById(R.id.fch8);
                CheckBox ch62 = (CheckBox) v.findViewById(R.id.uch1);
                CheckBox ch63 = (CheckBox) v.findViewById(R.id.uch2);
                CheckBox ch64 = (CheckBox) v.findViewById(R.id.uch3);
                CheckBox ch65 = (CheckBox) v.findViewById(R.id.uch4);
                CheckBox ch66 = (CheckBox) v.findViewById(R.id.uch5);
                CheckBox ch67 = (CheckBox) v.findViewById(R.id.uch6);
                CheckBox ch68 = (CheckBox) v.findViewById(R.id.uch7);

                ch1.setChecked(getBoolean(formDetailsDTO.getInvstBioFormRandomSerumGlucose()));
                ch2.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFastingSerumGlucose()));
                ch3.setChecked(getBoolean(formDetailsDTO.getInvstBioFormPostprandialSerumGlucose()));
                ch4.setChecked(getBoolean(formDetailsDTO.getInvstBioFormOralGlucoseTest()));
                ch5.setChecked(getBoolean(formDetailsDTO.getInvstBioFormGlucoseChallengetest()));
                ch6.setChecked(getBoolean(formDetailsDTO.getInvstBioFormGlycatedHaemoglobin()));
                ch7.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumUrea()));
                ch8.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumCreatinine()));
                ch9.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumCholesterol()));
                ch10.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumUricAcid()));
                ch11.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumTotalProtein()));
                ch12.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumAlbumin()));
                ch13.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumTotalBiliruin()));
                ch14.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumDirectBilurubin()));
                ch15.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAST()));
                ch16.setChecked(getBoolean(formDetailsDTO.getInvstBioFormALT()));
                ch17.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAlkalinePhosphatase()));
                ch18.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAmulase()));
                ch19.setChecked(getBoolean(formDetailsDTO.getInvstBioFormLipase()));
                ch20.setChecked(getBoolean(formDetailsDTO.getInvstBioFormGammaGlutamyl()));
                ch21.setChecked(getBoolean(formDetailsDTO.getInvstBioFormLactateDehydrogenase()));
                ch22.setChecked(getBoolean(formDetailsDTO.getInvstBioFormCreatineKinase()));
                ch23.setChecked(getBoolean(formDetailsDTO.getInvstBioFormCKMB()));
                ch24.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTroponin()));
                ch25.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumElectrolytes()));
                ch26.setChecked(getBoolean(formDetailsDTO.getInvstBioFormPTwithINR()));
                ch27.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAPTT()));
                ch28.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumCalcium()));
                ch29.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumPhosphorous()));
                ch30.setChecked(getBoolean(formDetailsDTO.getInvstBioFormSerumIron()));
                ch31.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTotalIronBinding()));
                ch32.setChecked(getBoolean(formDetailsDTO.getInvstBioFormLipidProfile()));
                ch33.setChecked(getBoolean(formDetailsDTO.getInvstBioFormLiverProfile()));
                ch34.setChecked(getBoolean(formDetailsDTO.getInvstBioFormRenalProfile()));
                ch35.setChecked(getBoolean(formDetailsDTO.getInvstBioFormThyroidProfile()));
                ch36.setChecked(getBoolean(formDetailsDTO.getInvstBioFormThyroidMiniProfile()));
                ch37.setChecked(getBoolean(formDetailsDTO.getInvstBioFormThyroidExtendedProfile()));
                ch38.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAnemiaCapsule()));
                ch39.setChecked(getBoolean(formDetailsDTO.getInvstBioFormDiabetesCapsule()));
                ch40.setChecked(getBoolean(formDetailsDTO.getInvstBioFormDiabetesMiniCapsule ()));
                ch41.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUrobilinogen ()));
                ch42.setChecked(getBoolean(formDetailsDTO.getInvstBioFormMicroscopy()));
                ch43.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTotalT3()));
                ch44.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTotalT4()));
                ch45.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTSH()));
                ch46.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFreeT3()));
                ch47.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFreeT4()));
                ch48.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAntiTPO()));
                ch49.setChecked(getBoolean(formDetailsDTO.getInvstBioFormLH()));
                ch50.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFSH()));
                ch51.setChecked(getBoolean(formDetailsDTO.getInvstBioFormProlactin()));
                ch52.setChecked(getBoolean(formDetailsDTO.getInvstBioFormTestosterone()));
                ch53.setChecked(getBoolean(formDetailsDTO.getInvstBioFormPSA()));
                ch54.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFerritin()));
                ch55.setChecked(getBoolean(formDetailsDTO.getInvstBioFormCSFAnalysis()));
                ch56.setChecked(getBoolean(formDetailsDTO.getInvstBioFormPleuralFluidAnalysis()));
                ch57.setChecked(getBoolean(formDetailsDTO.getInvstBioFormFluidRenalProfile()));
                TextView fluidsTextView  = (TextView) v.findViewById(R.id.f4);
                ch58.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAsciticProteins()));
                ch59.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAsciticGlucose()));
                ch60.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAsciticAlbumin()));
                ch61.setChecked(getBoolean(formDetailsDTO.getInvstBioFormAmylase()));
                ch62.setChecked(getBoolean(formDetailsDTO.getInvstBioFormElectrolytes ()));
                ch63.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUMicroAlbumin()));
                ch64.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUPhosphates()));
                ch65.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUUricAcid()));
                ch66.setChecked(getBoolean(formDetailsDTO.getInvstBioForm24hrsUrinaryProteins()));
                ch67.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUCreatinine()));
                ch68.setChecked(getBoolean(formDetailsDTO.getInvstBioFormUCalcium()));

                ch1.setClickable(Boolean.FALSE);
                ch2.setClickable(Boolean.FALSE);
                ch3.setClickable(Boolean.FALSE);
                ch4.setClickable(Boolean.FALSE);
                ch5.setClickable(Boolean.FALSE);
                ch6.setClickable(Boolean.FALSE);
                ch7.setClickable(Boolean.FALSE);
                ch8.setClickable(Boolean.FALSE);
                ch9.setClickable(Boolean.FALSE);
                ch10.setClickable(Boolean.FALSE);
                ch11.setClickable(Boolean.FALSE);
                ch12.setClickable(Boolean.FALSE);
                ch13.setClickable(Boolean.FALSE);
                ch14.setClickable(Boolean.FALSE);
                ch15.setClickable(Boolean.FALSE);
                ch16.setClickable(Boolean.FALSE);
                ch17.setClickable(Boolean.FALSE);
                ch18.setClickable(Boolean.FALSE);
                ch19.setClickable(Boolean.FALSE);
                ch20.setClickable(Boolean.FALSE);
                ch21.setClickable(Boolean.FALSE);
                ch22.setClickable(Boolean.FALSE);
                ch23.setClickable(Boolean.FALSE);
                ch24.setClickable(Boolean.FALSE);
                ch25.setClickable(Boolean.FALSE);
                ch26.setClickable(Boolean.FALSE);
                ch27.setClickable(Boolean.FALSE);
                ch28.setClickable(Boolean.FALSE);
                ch29.setClickable(Boolean.FALSE);
                ch30.setClickable(Boolean.FALSE);
                ch31.setClickable(Boolean.FALSE);
                ch32.setClickable(Boolean.FALSE);
                ch33.setClickable(Boolean.FALSE);
                ch34.setClickable(Boolean.FALSE);
                ch35.setClickable(Boolean.FALSE);
                ch36.setClickable(Boolean.FALSE);
                ch37.setClickable(Boolean.FALSE);
                ch38.setClickable(Boolean.FALSE);
                ch39.setClickable(Boolean.FALSE);
                ch40.setClickable(Boolean.FALSE);
                ch41.setClickable(Boolean.FALSE);
                ch42.setClickable(Boolean.FALSE);
                ch43.setClickable(Boolean.FALSE);
                ch44.setClickable(Boolean.FALSE);
                ch45.setClickable(Boolean.FALSE);
                ch46.setClickable(Boolean.FALSE);
                ch47.setClickable(Boolean.FALSE);
                ch48.setClickable(Boolean.FALSE);
                ch49.setClickable(Boolean.FALSE);
                ch50.setClickable(Boolean.FALSE);
                ch51.setClickable(Boolean.FALSE);
                ch52.setClickable(Boolean.FALSE);
                ch53.setClickable(Boolean.FALSE);
                ch54.setClickable(Boolean.FALSE);
                ch55.setClickable(Boolean.FALSE);
                ch56.setClickable(Boolean.FALSE);
                ch57.setClickable(Boolean.FALSE);
                ch58.setClickable(Boolean.FALSE);
                ch59.setClickable(Boolean.FALSE);
                ch60.setClickable(Boolean.FALSE);
                ch61.setClickable(Boolean.FALSE);
                ch62.setClickable(Boolean.FALSE);
                ch63.setClickable(Boolean.FALSE);
                ch64.setClickable(Boolean.FALSE);
                ch65.setClickable(Boolean.FALSE);
                ch66.setClickable(Boolean.FALSE);
                ch67.setClickable(Boolean.FALSE);
                ch68.setClickable(Boolean.FALSE);

                if(!ch58.isChecked() && !ch59.isChecked() && !ch60.isChecked() && !ch61.isChecked())
                {
                    fluidsTextView.setVisibility(View.GONE);
                }

                ch1.setVisibility(ch1.isChecked() ? View.VISIBLE : View.GONE);
                ch2.setVisibility(ch2.isChecked() ? View.VISIBLE : View.GONE);
                ch3.setVisibility(ch3.isChecked() ? View.VISIBLE : View.GONE);
                ch4.setVisibility(ch4.isChecked() ? View.VISIBLE : View.GONE);
                ch5.setVisibility(ch5.isChecked() ? View.VISIBLE : View.GONE);
                ch6.setVisibility(ch6.isChecked() ? View.VISIBLE : View.GONE);
                ch7.setVisibility(ch7.isChecked() ? View.VISIBLE : View.GONE);
                ch8.setVisibility(ch8.isChecked() ? View.VISIBLE : View.GONE);
                ch9.setVisibility(ch9.isChecked() ? View.VISIBLE : View.GONE);
                ch10.setVisibility(ch10.isChecked() ? View.VISIBLE : View.GONE);
                ch11.setVisibility(ch11.isChecked() ? View.VISIBLE : View.GONE);
                ch12.setVisibility(ch12.isChecked() ? View.VISIBLE : View.GONE);
                ch13.setVisibility(ch13.isChecked() ? View.VISIBLE : View.GONE);
                ch14.setVisibility(ch14.isChecked() ? View.VISIBLE : View.GONE);
                ch15.setVisibility(ch15.isChecked() ? View.VISIBLE : View.GONE);
                ch16.setVisibility(ch16.isChecked() ? View.VISIBLE : View.GONE);
                ch17.setVisibility(ch17.isChecked() ? View.VISIBLE : View.GONE);
                ch18.setVisibility(ch18.isChecked() ? View.VISIBLE : View.GONE);
                ch19.setVisibility(ch19.isChecked() ? View.VISIBLE : View.GONE);
                ch20.setVisibility(ch20.isChecked() ? View.VISIBLE : View.GONE);
                ch21.setVisibility(ch21.isChecked() ? View.VISIBLE : View.GONE);
                ch22.setVisibility(ch22.isChecked() ? View.VISIBLE : View.GONE);
                ch23.setVisibility(ch23.isChecked() ? View.VISIBLE : View.GONE);
                ch24.setVisibility(ch24.isChecked() ? View.VISIBLE : View.GONE);
                ch25.setVisibility(ch25.isChecked() ? View.VISIBLE : View.GONE);
                ch26.setVisibility(ch26.isChecked() ? View.VISIBLE : View.GONE);
                ch27.setVisibility(ch27.isChecked() ? View.VISIBLE : View.GONE);
                ch28.setVisibility(ch28.isChecked() ? View.VISIBLE : View.GONE);
                ch29.setVisibility(ch29.isChecked() ? View.VISIBLE : View.GONE);
                ch30.setVisibility(ch30.isChecked() ? View.VISIBLE : View.GONE);
                ch31.setVisibility(ch31.isChecked() ? View.VISIBLE : View.GONE);
                ch32.setVisibility(ch32.isChecked() ? View.VISIBLE : View.GONE);
                ch33.setVisibility(ch33.isChecked() ? View.VISIBLE : View.GONE);
                ch34.setVisibility(ch34.isChecked() ? View.VISIBLE : View.GONE);
                ch35.setVisibility(ch35.isChecked() ? View.VISIBLE : View.GONE);
                ch36.setVisibility(ch36.isChecked() ? View.VISIBLE : View.GONE);
                ch37.setVisibility(ch37.isChecked() ? View.VISIBLE : View.GONE);
                ch38.setVisibility(ch38.isChecked() ? View.VISIBLE : View.GONE);
                ch39.setVisibility(ch39.isChecked() ? View.VISIBLE : View.GONE);
                ch40.setVisibility(ch40.isChecked() ? View.VISIBLE : View.GONE);
                ch41.setVisibility(ch41.isChecked() ? View.VISIBLE : View.GONE);
                ch42.setVisibility(ch42.isChecked() ? View.VISIBLE : View.GONE);
                ch43.setVisibility(ch43.isChecked() ? View.VISIBLE : View.GONE);
                ch44.setVisibility(ch44.isChecked() ? View.VISIBLE : View.GONE);
                ch45.setVisibility(ch45.isChecked() ? View.VISIBLE : View.GONE);
                ch46.setVisibility(ch46.isChecked() ? View.VISIBLE : View.GONE);
                ch47.setVisibility(ch47.isChecked() ? View.VISIBLE : View.GONE);
                ch48.setVisibility(ch48.isChecked() ? View.VISIBLE : View.GONE);
                ch49.setVisibility(ch49.isChecked() ? View.VISIBLE : View.GONE);
                ch50.setVisibility(ch50.isChecked() ? View.VISIBLE : View.GONE);
                ch51.setVisibility(ch51.isChecked() ? View.VISIBLE : View.GONE);
                ch52.setVisibility(ch52.isChecked() ? View.VISIBLE : View.GONE);
                ch53.setVisibility(ch53.isChecked() ? View.VISIBLE : View.GONE);
                ch54.setVisibility(ch54.isChecked() ? View.VISIBLE : View.GONE);
                ch55.setVisibility(ch55.isChecked() ? View.VISIBLE : View.GONE);
                ch56.setVisibility(ch56.isChecked() ? View.VISIBLE : View.GONE);
                ch57.setVisibility(ch57.isChecked() ? View.VISIBLE : View.GONE);
                ch58.setVisibility(ch58.isChecked() ? View.VISIBLE : View.GONE);
                ch59.setVisibility(ch59.isChecked() ? View.VISIBLE : View.GONE);
                ch60.setVisibility(ch60.isChecked() ? View.VISIBLE : View.GONE);
                ch61.setVisibility(ch61.isChecked() ? View.VISIBLE : View.GONE);
                ch62.setVisibility(ch62.isChecked() ? View.VISIBLE : View.GONE);
                ch63.setVisibility(ch63.isChecked() ? View.VISIBLE : View.GONE);
                ch64.setVisibility(ch64.isChecked() ? View.VISIBLE : View.GONE);
                ch65.setVisibility(ch65.isChecked() ? View.VISIBLE : View.GONE);
                ch66.setVisibility(ch66.isChecked() ? View.VISIBLE : View.GONE);
                ch67.setVisibility(ch67.isChecked() ? View.VISIBLE : View.GONE);
                ch68.setVisibility(ch68.isChecked() ? View.VISIBLE : View.GONE);
                saveBtn = (Button) v.findViewById(R.id.inbsave);

            }else if("investigationpathology".equals(formName) && formDetailsDTO != null && v != null) {

                toolbar = (Toolbar) v.findViewById(R.id.toolbar);
                CheckBox ch1 = (CheckBox) v.findViewById(R.id.hch1);
                CheckBox ch2 = (CheckBox) v.findViewById(R.id.hch2);
                CheckBox ch3 = (CheckBox) v.findViewById(R.id.hch3);
                CheckBox ch4 = (CheckBox) v.findViewById(R.id.hch4);
                CheckBox ch5 = (CheckBox) v.findViewById(R.id.hch5);
                CheckBox ch6 = (CheckBox) v.findViewById(R.id.hch6);
                CheckBox ch7 = (CheckBox) v.findViewById(R.id.hch7);
                CheckBox ch8 = (CheckBox) v.findViewById(R.id.hch8);
                CheckBox ch9 = (CheckBox) v.findViewById(R.id.hch9);
                CheckBox ch10 = (CheckBox) v.findViewById(R.id.hch10);
                CheckBox ch11 = (CheckBox) v.findViewById(R.id.hch11);
                CheckBox ch12 = (CheckBox) v.findViewById(R.id.hch12);
                CheckBox ch13 = (CheckBox) v.findViewById(R.id.hch13);
                CheckBox ch14 = (CheckBox) v.findViewById(R.id.hch14);
                CheckBox ch15 = (CheckBox) v.findViewById(R.id.hch15);
                CheckBox ch16 = (CheckBox) v.findViewById(R.id.hch16);
                CheckBox ch17 = (CheckBox) v.findViewById(R.id.hch17);
                CheckBox ch18 = (CheckBox) v.findViewById(R.id.hch18);
                CheckBox ch19 = (CheckBox) v.findViewById(R.id.hch19);
                CheckBox ch20 = (CheckBox) v.findViewById(R.id.hch20);
                CheckBox ch21 = (CheckBox) v.findViewById(R.id.hch21);
                CheckBox ch22 = (CheckBox) v.findViewById(R.id.hch22);
                TextView absouluteindices  = (TextView) v.findViewById(R.id.h23);
                CheckBox ch23 = (CheckBox) v.findViewById(R.id.hch24);
                CheckBox ch24 = (CheckBox) v.findViewById(R.id.hch25);
                CheckBox ch25 = (CheckBox) v.findViewById(R.id.hch26);
                CheckBox ch26 = (CheckBox) v.findViewById(R.id.hch27);
                CheckBox ch27 = (CheckBox) v.findViewById(R.id.hch28);
                CheckBox ch28 = (CheckBox) v.findViewById(R.id.hch29);
                CheckBox ch29 = (CheckBox) v.findViewById(R.id.cch1);
                CheckBox ch30 = (CheckBox) v.findViewById(R.id.cch2);
                CheckBox ch31 = (CheckBox) v.findViewById(R.id.cch3);
                CheckBox ch32 = (CheckBox) v.findViewById(R.id.cch4);
                CheckBox ch33 = (CheckBox) v.findViewById(R.id.cch5);
                CheckBox ch34 = (CheckBox) v.findViewById(R.id.cch6);
                CheckBox ch35 = (CheckBox) v.findViewById(R.id.cch7);
                CheckBox ch36 = (CheckBox) v.findViewById(R.id.cch8);
                CheckBox ch37 = (CheckBox) v.findViewById(R.id.cch9);
                CheckBox ch38 = (CheckBox) v.findViewById(R.id.cch10);
                CheckBox ch39 = (CheckBox) v.findViewById(R.id.cch11);
                TextView urineSpecialTests  = (TextView) v.findViewById(R.id.c12);
                CheckBox ch40 = (CheckBox) v.findViewById(R.id.cch13);
                v.findViewById(R.id.c15).setVisibility(View.GONE);
                v.findViewById(R.id.c16).setVisibility(View.GONE);
                TextView bodyfluids = (TextView) v.findViewById(R.id.c17);
                CheckBox ch41 = (CheckBox) v.findViewById(R.id.cch14);
                CheckBox ch42 = (CheckBox) v.findViewById(R.id.cch18);
                CheckBox ch43 = (CheckBox) v.findViewById(R.id.cch19);
                CheckBox ch44 = (CheckBox) v.findViewById(R.id.cch20);
                CheckBox ch45 = (CheckBox) v.findViewById(R.id.cch21);
                CheckBox ch46 = (CheckBox) v.findViewById(R.id.cch22);
                v.findViewById(R.id.c23).setVisibility(View.GONE);

                ch1.setChecked(getBoolean(formDetailsDTO.getInvstPathFormHaemogram()));
                ch2.setChecked(getBoolean(formDetailsDTO.getInvstPathFormCBC()));
                ch3.setChecked(getBoolean(formDetailsDTO.getInvstPathFormCBP()));
                ch4.setChecked(getBoolean(formDetailsDTO.getInvstPathFormHaemoglobin()));
                ch5.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPCV()));
                ch6.setChecked(getBoolean(formDetailsDTO.getInvstPathFormTotalWBCCount()));
                ch7.setChecked(getBoolean(formDetailsDTO.getInvstPathFormDifferentialCount()));
                ch8.setChecked(getBoolean(formDetailsDTO.getInvstPathFormESR()));
                ch9.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAbnormalCells()));
                ch10.setChecked(getBoolean(formDetailsDTO.getInvstPathFormRBCCount ()));
                ch11.setChecked(getBoolean(formDetailsDTO.getInvstPathFormRDW()));
                ch12.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPlateletCount()));
                ch13.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPDW()));
                ch14.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPMW()));
                ch15.setChecked(getBoolean(formDetailsDTO.getInvstPathFormBleedingTime()));
                ch16.setChecked(getBoolean(formDetailsDTO.getInvstPathFormClottingTime()));
                ch17.setChecked(getBoolean(formDetailsDTO.getInvstPathFormReticulocyteCount()));
                ch18.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPeripheralSmear()));
                ch19.setChecked(getBoolean(formDetailsDTO.getInvstPathFormSickleCellTest()));
                ch20.setChecked(getBoolean(formDetailsDTO.getInvstPathFormOsmoticFragilityTest()));
                ch21.setChecked(getBoolean(formDetailsDTO.getInvstPathFormLECellTest()));
                ch22.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAEC()));
                ch23.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAbsoluteMCV()));
                ch24.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAbsoluteMCH ()));
                ch25.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAbsoluteMCHC()));
                ch26.setChecked(getBoolean(formDetailsDTO.getInvstPathFormSmearForMP()));
                ch27.setChecked(getBoolean(formDetailsDTO.getInvstPathFormRapidTestMP()));
                ch28.setChecked(getBoolean(formDetailsDTO.getInvstPathFormOtherInvestigations()));
                ch29.setChecked(getBoolean(formDetailsDTO.getInvstPathFormRoutineUrineAnalysis()));
                ch30.setChecked(getBoolean(formDetailsDTO.getInvstPathFormCompleteUrineExam()));
                ch31.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPH()));
                ch32.setChecked(getBoolean(formDetailsDTO.getInvstPathFormSpecificGravity()));
                ch33.setChecked(getBoolean(formDetailsDTO.getInvstPathFormProteins()));
                ch34.setChecked(getBoolean(formDetailsDTO.getInvstPathFormSugar()));
                ch35.setChecked(getBoolean(formDetailsDTO.getInvstPathFormKetoneBodies()));
                ch36.setChecked(getBoolean(formDetailsDTO.getInvstPathFormBileSalts()));
                ch37.setChecked(getBoolean(formDetailsDTO.getInvstPathFormBilePigments()));
                ch38.setChecked(getBoolean(formDetailsDTO.getInvstPathFormUrobilinogen()));
                ch39.setChecked(getBoolean(formDetailsDTO.getInvstPathFormMicroscopy()));
                ch40.setChecked(getBoolean(formDetailsDTO.getInvstPathFormbenceHonesProtien()));
                ch41.setChecked(getBoolean(formDetailsDTO.getInvstPathFormHmosiderinPigment()));
                ch42.setChecked(getBoolean(formDetailsDTO.getInvstPathFormCSF()));
                ch43.setChecked(getBoolean(formDetailsDTO.getInvstPathFormAsciticFluid()));
                ch44.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPleuralFluid()));
                ch45.setChecked(getBoolean(formDetailsDTO.getInvstPathFormSynovialFluid()));
                ch46.setChecked(getBoolean(formDetailsDTO.getInvstPathFormPeriCardialFluid()));

                if(!ch23.isChecked() && !ch24.isChecked() && !ch25.isChecked()) {
                    absouluteindices .setVisibility(View.GONE);
                }

                if(!ch40.isChecked() && !ch41.isChecked()) {
                    urineSpecialTests.setVisibility(View.GONE);
                }

                if(!ch42.isChecked() && !ch43.isChecked() && !ch44.isChecked() && !ch45.isChecked()) {
                    bodyfluids.setVisibility(View.GONE);
                }

                ch1.setClickable(Boolean.FALSE);
                ch2.setClickable(Boolean.FALSE);
                ch3.setClickable(Boolean.FALSE);
                ch4.setClickable(Boolean.FALSE);
                ch5.setClickable(Boolean.FALSE);
                ch6.setClickable(Boolean.FALSE);
                ch7.setClickable(Boolean.FALSE);
                ch8.setClickable(Boolean.FALSE);
                ch9.setClickable(Boolean.FALSE);
                ch10.setClickable(Boolean.FALSE);
                ch11.setClickable(Boolean.FALSE);
                ch12.setClickable(Boolean.FALSE);
                ch13.setClickable(Boolean.FALSE);
                ch14.setClickable(Boolean.FALSE);
                ch15.setClickable(Boolean.FALSE);
                ch16.setClickable(Boolean.FALSE);
                ch17.setClickable(Boolean.FALSE);
                ch18.setClickable(Boolean.FALSE);
                ch19.setClickable(Boolean.FALSE);
                ch20.setClickable(Boolean.FALSE);
                ch21.setClickable(Boolean.FALSE);
                ch22.setClickable(Boolean.FALSE);
                ch23.setClickable(Boolean.FALSE);
                ch24.setClickable(Boolean.FALSE);
                ch25.setClickable(Boolean.FALSE);
                ch26.setClickable(Boolean.FALSE);
                ch27.setClickable(Boolean.FALSE);
                ch28.setClickable(Boolean.FALSE);
                ch29.setClickable(Boolean.FALSE);
                ch30.setClickable(Boolean.FALSE);
                ch31.setClickable(Boolean.FALSE);
                ch32.setClickable(Boolean.FALSE);
                ch33.setClickable(Boolean.FALSE);
                ch34.setClickable(Boolean.FALSE);
                ch35.setClickable(Boolean.FALSE);
                ch36.setClickable(Boolean.FALSE);
                ch37.setClickable(Boolean.FALSE);
                ch38.setClickable(Boolean.FALSE);
                ch39.setClickable(Boolean.FALSE);
                ch40.setClickable(Boolean.FALSE);
                ch41.setClickable(Boolean.FALSE);
                ch42.setClickable(Boolean.FALSE);
                ch43.setClickable(Boolean.FALSE);
                ch44.setClickable(Boolean.FALSE);
                ch45.setClickable(Boolean.FALSE);
                ch46.setClickable(Boolean.FALSE);

                ch1.setVisibility(ch1.isChecked() ? View.VISIBLE : View.GONE);
                ch2.setVisibility(ch2.isChecked() ? View.VISIBLE : View.GONE);
                ch3.setVisibility(ch3.isChecked() ? View.VISIBLE : View.GONE);
                ch4.setVisibility(ch4.isChecked() ? View.VISIBLE : View.GONE);
                ch5.setVisibility(ch5.isChecked() ? View.VISIBLE : View.GONE);
                ch6.setVisibility(ch6.isChecked() ? View.VISIBLE : View.GONE);
                ch7.setVisibility(ch7.isChecked() ? View.VISIBLE : View.GONE);
                ch8.setVisibility(ch8.isChecked() ? View.VISIBLE : View.GONE);
                ch9.setVisibility(ch9.isChecked() ? View.VISIBLE : View.GONE);
                ch10.setVisibility(ch10.isChecked() ? View.VISIBLE : View.GONE);
                ch11.setVisibility(ch11.isChecked() ? View.VISIBLE : View.GONE);
                ch12.setVisibility(ch12.isChecked() ? View.VISIBLE : View.GONE);
                ch13.setVisibility(ch13.isChecked() ? View.VISIBLE : View.GONE);
                ch14.setVisibility(ch14.isChecked() ? View.VISIBLE : View.GONE);
                ch15.setVisibility(ch15.isChecked() ? View.VISIBLE : View.GONE);
                ch16.setVisibility(ch16.isChecked() ? View.VISIBLE : View.GONE);
                ch17.setVisibility(ch17.isChecked() ? View.VISIBLE : View.GONE);
                ch18.setVisibility(ch18.isChecked() ? View.VISIBLE : View.GONE);
                ch19.setVisibility(ch19.isChecked() ? View.VISIBLE : View.GONE);
                ch20.setVisibility(ch20.isChecked() ? View.VISIBLE : View.GONE);
                ch21.setVisibility(ch21.isChecked() ? View.VISIBLE : View.GONE);
                ch22.setVisibility(ch22.isChecked() ? View.VISIBLE : View.GONE);
                ch23.setVisibility(ch23.isChecked() ? View.VISIBLE : View.GONE);
                ch24.setVisibility(ch24.isChecked() ? View.VISIBLE : View.GONE);
                ch25.setVisibility(ch25.isChecked() ? View.VISIBLE : View.GONE);
                ch26.setVisibility(ch26.isChecked() ? View.VISIBLE : View.GONE);
                ch27.setVisibility(ch27.isChecked() ? View.VISIBLE : View.GONE);
                ch28.setVisibility(ch28.isChecked() ? View.VISIBLE : View.GONE);
                ch29.setVisibility(ch29.isChecked() ? View.VISIBLE : View.GONE);
                ch30.setVisibility(ch30.isChecked() ? View.VISIBLE : View.GONE);
                ch31.setVisibility(ch31.isChecked() ? View.VISIBLE : View.GONE);
                ch32.setVisibility(ch32.isChecked() ? View.VISIBLE : View.GONE);
                ch33.setVisibility(ch33.isChecked() ? View.VISIBLE : View.GONE);
                ch34.setVisibility(ch34.isChecked() ? View.VISIBLE : View.GONE);
                ch35.setVisibility(ch35.isChecked() ? View.VISIBLE : View.GONE);
                ch36.setVisibility(ch36.isChecked() ? View.VISIBLE : View.GONE);
                ch37.setVisibility(ch37.isChecked() ? View.VISIBLE : View.GONE);
                ch38.setVisibility(ch38.isChecked() ? View.VISIBLE : View.GONE);
                ch39.setVisibility(ch39.isChecked() ? View.VISIBLE : View.GONE);
                ch40.setVisibility(ch40.isChecked() ? View.VISIBLE : View.GONE);
                ch41.setVisibility(ch41.isChecked() ? View.VISIBLE : View.GONE);
                ch42.setVisibility(ch42.isChecked() ? View.VISIBLE : View.GONE);
                ch43.setVisibility(ch43.isChecked() ? View.VISIBLE : View.GONE);
                ch44.setVisibility(ch44.isChecked() ? View.VISIBLE : View.GONE);
                ch45.setVisibility(ch45.isChecked() ? View.VISIBLE : View.GONE);
                ch46.setVisibility(ch46.isChecked() ? View.VISIBLE : View.GONE);

                saveBtn = (Button) v.findViewById(R.id.inpsave);
            }

            if(saveBtn != null){
                saveBtn.setVisibility(View.GONE);
            }
            if(toolbar != null){
                toolbar.setVisibility(View.GONE);
            }

            if(v != null)
                v.invalidate();
        }
        catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private int getFormLayoutId(String formName) {
        return formLayoutIds.get(formName);
    }

    private Map<String,Integer> loadFormLayoutIds() {
        Map<String, Integer> formLayoutIds = new HashMap<>();
        formLayoutIds.put("generalhealthscreening", R.layout.activity_general_health_screening);
        formLayoutIds.put("dentalassessment", R.layout.activity_dental_assessment);
        formLayoutIds.put("historytaking", R.layout.activity_health_history);
        formLayoutIds.put("generalphysicalexamination",R.layout.activity_general_examination);
        formLayoutIds.put("dischargesheet",R.layout.activity_discharge_sheet);
        formLayoutIds.put("investigationbiochemistry", R.layout.activity_investigation_biochemistry);
        formLayoutIds.put("investigationmicrobiology", R.layout.activity_investigation_microbiology);
        formLayoutIds.put("investigationpathology", R.layout.activity_investigation_pathlogy);

        return formLayoutIds;
    }

    private void hideRadioGroup(RadioGroup radioGroups[]){
        for(RadioGroup rg : radioGroups){
            rg.setVisibility(View.GONE);
        }
    }

    private String getBooleanInText(String value){
        return ("1".equals(value)) ? "Yes" : "No";
    }

    private void preventTextViewClick(TextView textView) {
        textView.setMinLines(1);
        textView.setClickable(false);
        textView.setFocusable(false);
    }

    private Boolean getBoolean(String value){
        return ("1".equals(value)) ? Boolean.TRUE : Boolean.FALSE;
    }

}
