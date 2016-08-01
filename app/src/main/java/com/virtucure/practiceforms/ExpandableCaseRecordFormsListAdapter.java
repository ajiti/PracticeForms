package com.virtucure.practiceforms;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.FileAttachmentDTO;
import com.virtucare.practiceforms.dto.Form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 5/9/2016.
 */
public class ExpandableCaseRecordFormsListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Toolbar toolbar;
    private FileAttachmentAdapter fileAttachmentAdapter;
    private List<Form> listDataHeader;
    private Map<String, Integer> formLayoutIds;
    private Button saveBtn;
    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10;
    private TextView txtValView1, txtValView2, txtValView3, txtValView4, txtValView5, txtValView6, txtValView7, txtValView8, txtValView9, txtValView10;
    private String createdByUserName, createdUserType, createdByFullName, createdLinkType, createdByCid, displayProjName, formMainCategoryName;
    private RadioGroup radioGroups[];

    public ExpandableCaseRecordFormsListAdapter(Context context, List<Form> listDataHeader)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        formLayoutIds = loadFormLayoutIds();
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final Form form = (Form) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.caserecordforms_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(form.getDisplayFormName());
        lblListHeader.setBackgroundColor(Color.TRANSPARENT);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.formShareCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                form.setIsSelected(isChecked);
            }
        });
        checkBox.setChecked(form.isSelected());

        if("uploadfiles".equals(form.getFormName())) {
            checkBox.setVisibility(View.GONE);
        }
        else {
            checkBox.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Form form = listDataHeader.get(groupPosition);
        String headTitle = form.getFormName();
        Map<String, String> formMap = null;
        List<FileAttachmentDTO> attachments = null;

        View v = View.inflate(context, getFormLayoutId(headTitle), null);

        if("uploadfiles".equals(headTitle)) {
            attachments = (List<FileAttachmentDTO>) form.getFormData();
        } else {
            formMap = (Map<String, String>) form.getFormData();
        }

        if(formMap!=null) {
            createdByUserName = formMap.get("createdByUserName");
            createdUserType = formMap.get("createdUserType");
            createdByFullName = formMap.get("createdByFullName");
            createdLinkType = formMap.get("createdLinkType");
            createdByCid = formMap.get("createdByCid");
            displayProjName = formMap.get("displayProjName");
            formMainCategoryName = formMap.get("formMainCategoryName");
        }
        if(formMap!=null && v!=null && ("dentalassessment".equals(headTitle) || "generalhealthscreening".equals(headTitle))){
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

        if(formMap!=null && v!=null && "historytaking".equals(headTitle)) {
            toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            TextView txtView = (TextView) v.findViewById(R.id.presentIllness);
            txtView.setMinLines(1);
            txtView.setClickable(false);
            txtView.setFocusable(false);
            txtView.setText(formMap.get("histFormPresentIllNess"));
            TextView txtView2 = (TextView) v.findViewById(R.id.previousIlliness);
            txtView2.setMinLines(1);
            txtView2.setClickable(false);
            txtView2.setFocusable(false);
            txtView2.setText(formMap.get("histFormPreviousIllness"));
            TextView txtView3 = (TextView) v.findViewById(R.id.drugHistory);
            txtView3.setMinLines(1);
            txtView3.setClickable(false);
            txtView3.setFocusable(false);
            txtView3.setText(formMap.get("histFormDrug"));
            TextView txtView4 = (TextView) v.findViewById(R.id.allergies);
            txtView4.setMinLines(1);
            txtView4.setClickable(false);
            txtView4.setFocusable(false);
            txtView4.setText(formMap.get("histFormAllergiesandReactions"));
            TextView txtView5 = (TextView) v.findViewById(R.id.personalHistory);
            txtView5.setMinLines(1);
            txtView5.setClickable(false);
            txtView5.setFocusable(false);
            txtView5.setText(formMap.get("histFormPersonal"));
            TextView txtView6 = (TextView) v.findViewById(R.id.menstrualhistory);
            txtView6.setMinLines(1);
            txtView6.setClickable(false);
            txtView6.setFocusable(false);
            txtView6.setText(formMap.get("histFormMenstrual"));
            TextView txtView7 = (TextView) v.findViewById(R.id.socialhistory);
            txtView7.setMinLines(1);
            txtView7.setClickable(false);
            txtView7.setFocusable(false);
            txtView7.setText(formMap.get("histFormSocial"));
            TextView txtView8 = (TextView) v.findViewById(R.id.familyhistory);
            txtView8.setMinLines(1);
            txtView8.setClickable(false);
            txtView8.setFocusable(false);
            txtView8.setText(formMap.get("histFormFamily"));
            TextView txtView9 = (TextView) v.findViewById(R.id.systemreview);
            txtView9.setMinLines(1);
            txtView9.setClickable(false);
            txtView9.setFocusable(false);
            txtView9.setText(formMap.get("histFormSystemsReview"));
            TextView txtView10 = (TextView) v.findViewById(R.id.summary);
            txtView10.setMinLines(1);
            txtView10.setClickable(false);
            txtView10.setFocusable(false);
            txtView10.setText(formMap.get("histFormSummary"));
            TextView txtView11 = (TextView) v.findViewById(R.id.htpdlbl);
            txtView11.setVisibility(View.GONE);
            txtView11.setClickable(false);
            txtView11.setFocusable(false);
            txtView11.setText(formMap.get("histFormSummary"));
            TextView txtView12 = (TextView) v.findViewById(R.id.provisionalDiagnosis);
            txtView12.setVisibility(View.GONE);
            txtView12.setClickable(false);
            txtView12.setFocusable(false);
            txtView12.setText(formMap.get("histFormPresentIllNess"));
            TextView txtView13 = (TextView) v.findViewById(R.id.htfdlbl);
            txtView13.setVisibility(View.GONE);
            TextView txtView14 = (TextView) v.findViewById(R.id.finalDiagnosis);
            txtView14.setVisibility(View.GONE);
            saveBtn = (Button) v.findViewById(R.id.button);

        } else if(formMap!=null && v!=null && "dentalassessment".equals(headTitle)) {
            toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            String dentFormPainInTeeth = formMap.get("dentFormPainInTeeth");
            String dentFormSevereSensitivityOfTeethOnDrinks = formMap.get("dentFormSevereSensitivityOfTeethOnDrinks");
            String dentFormPainorBleedingInGums = formMap.get("dentFormPainorBleedingInGums");
            String dentFormHaveLooseTeethWithGaps = formMap.get("dentFormHaveLooseTeethWithGaps");
            String dentFormHaveUnpleasantTaste = formMap.get("dentFormHaveUnpleasantTaste");
            String dentFormDoGrindYourTeethWhenSleep = formMap.get("dentFormDoGrindYourTeethWhenSleep");
            String dentFormHaveSwellingInGumsWithFluid = formMap.get("dentFormHaveSwellingInGumsWithFluid");
            String dentFormSeverePainInTeethAtNight = formMap.get("dentFormSeverePainInTeethAtNight");
            String dentFormHavePainnAndSwellingInGums = formMap.get("dentFormHavePainnAndSwellingInGums");
            String dentFormHaveIrregularPlacedTeeth = formMap.get("dentFormHaveIrregularPlacedTeeth");

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

            txtValView1.setText(getValueInText(dentFormPainInTeeth));
            txtValView2.setText(getValueInText(dentFormSevereSensitivityOfTeethOnDrinks));
            txtValView3.setText(getValueInText(dentFormPainorBleedingInGums));
            txtValView4.setText(getValueInText(dentFormHaveLooseTeethWithGaps));
            txtValView5.setText(getValueInText(dentFormHaveUnpleasantTaste));
            txtValView6.setText(getValueInText(dentFormDoGrindYourTeethWhenSleep));
            txtValView7.setText(getValueInText(dentFormHaveSwellingInGumsWithFluid));
            txtValView8.setText(getValueInText(dentFormSeverePainInTeethAtNight));
            txtValView9.setText(getValueInText(dentFormHavePainnAndSwellingInGums));
            txtValView10.setText(getValueInText(dentFormHaveIrregularPlacedTeeth));
            saveBtn = (Button) v.findViewById(R.id.dentalsave);

        } else if(formMap!=null && v!=null && "generalhealthscreening".equals(headTitle)) {
            toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            String hlthScrnFormHaveDifficultiesInSeeingObjects = formMap.get("hlthScrnFormHaveDifficultiesInSeeingObjects");
            String hlthScrnFormHaveHearingTrouble = formMap.get("hlthScrnFormHaveHearingTrouble");
            String hlthScrnFormSufferWithFrequentEpisodesOfCold = formMap.get("hlthScrnFormSufferWithFrequentEpisodesOfCold");
            String hlthScrnFormHaveSkinAllergies = formMap.get("hlthScrnFormHaveSkinAllergies");
            String hlthScrnFormHaveLearningDifficulties = formMap.get("hlthScrnFormHaveLearningDifficulties");
            String hlthScrnFormHaveHistoryOfFits = formMap.get("hlthScrnFormHaveHistoryOfFits");
            String hlthScrnFormHaveHistoryOfBurningChest = formMap.get("hlthScrnFormHaveHistoryOfBurningChest");
            String hlthScrnFormHaveHighBloodPressure = formMap.get("hlthScrnFormHaveHighBloodPressure");
            String hlthScrnFormHaveDiabetes = formMap.get("hlthScrnFormHaveDiabetes");
            String hlthScrnFormHaveDentalProblems = formMap.get("hlthScrnFormHaveDentalProblems");

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

            txtValView1.setText(getValueInText(hlthScrnFormHaveDifficultiesInSeeingObjects));
            txtValView2.setText(getValueInText(hlthScrnFormHaveHearingTrouble));
            txtValView3.setText(getValueInText(hlthScrnFormSufferWithFrequentEpisodesOfCold));
            txtValView4.setText(getValueInText(hlthScrnFormHaveSkinAllergies));
            txtValView5.setText(getValueInText(hlthScrnFormHaveLearningDifficulties));
            txtValView6.setText(getValueInText(hlthScrnFormHaveHistoryOfFits));
            txtValView7.setText(getValueInText(hlthScrnFormHaveHistoryOfBurningChest));
            txtValView8.setText(getValueInText(hlthScrnFormHaveHighBloodPressure));
            txtValView9.setText(getValueInText(hlthScrnFormHaveDiabetes));
            txtValView10.setText(getValueInText(hlthScrnFormHaveDentalProblems));
            saveBtn = (Button) v.findViewById(R.id.gensave);

        } else if(attachments!=null && v!=null && "uploadfiles".equals(headTitle)) {
            ListView uploadFileList = (ListView) v.findViewById(R.id.attachmentList);
            fileAttachmentAdapter = new FileAttachmentAdapter(context, attachments);
            uploadFileList.setAdapter(fileAttachmentAdapter);
            Util.setListViewHeightBasedOnChildren(uploadFileList);

        } else if("generalphysicalexamination".equals(headTitle) && formMap!=null && v!=null){
            toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            TextView textView = (TextView)v.findViewById(R.id.consciousness);
            preventTextViewClick(textView);
            textView.setText(formMap.get("genFormConseiousness"));
            TextView textView1 = (TextView)v.findViewById(R.id.mental_state);
            preventTextViewClick(textView1);
            textView1.setText(formMap.get("genFormMentalState"));
            TextView textView2 = (TextView)v.findViewById(R.id.general_appearance);
            preventTextViewClick(textView2);
            textView2.setText(formMap.get("genFormGeneralAppearance"));
            TextView textView3 = (TextView)v.findViewById(R.id.pallor);
            preventTextViewClick(textView3);
            textView3.setText(formMap.get("genFormPallor"));
            TextView textView4 = (TextView)v.findViewById(R.id.icterus);
            preventTextViewClick(textView4);
            textView4.setText(formMap.get("genFormIcterus"));
            TextView textView5 = (TextView)v.findViewById(R.id.cyanosis);
            preventTextViewClick(textView5);
            textView5.setText(formMap.get("genFormCyanosis"));
            TextView textView6 = (TextView)v.findViewById(R.id.clubbing);
            preventTextViewClick(textView6);
            textView6.setText(formMap.get("genFormClubbing"));
            TextView textView7 = (TextView)v.findViewById(R.id.lymph_adenopathy);
            preventTextViewClick(textView7);
            textView7.setText(formMap.get("genFormLymphAdenopathy"));
            TextView textView8 = (TextView)v.findViewById(R.id.edema);
            preventTextViewClick(textView8);
            textView8.setText(formMap.get("genFormEdema"));
            TextView textView9 = (TextView)v.findViewById(R.id.temperature);
            preventTextViewClick(textView9);
            textView9.setText(formMap.get("genFormTemparature"));
            TextView textView10 = (TextView)v.findViewById(R.id.pulse);
            preventTextViewClick(textView10);
            textView10.setText(formMap.get("genFormPulse"));
            TextView textView11 = (TextView)v.findViewById(R.id.respiration);
            preventTextViewClick(textView11);
            textView11.setText(formMap.get("genFormRepisration"));
            TextView textView12 = (TextView)v.findViewById(R.id.blood_pressure);
            preventTextViewClick(textView12);
            textView12.setText(formMap.get("genFormBloodPressure"));
            TextView textView13 = (TextView)v.findViewById(R.id.height);
            preventTextViewClick(textView13);
            textView13.setText(formMap.get("genFormHeight"));
            TextView textView14 = (TextView)v.findViewById(R.id.weight);
            preventTextViewClick(textView14);
            textView14.setText(formMap.get("genFormWeight"));
            TextView textView15 = (TextView)v.findViewById(R.id.bmi);
            preventTextViewClick(textView15);
            textView15.setText(formMap.get("genFormBodyMassIndex"));
            TextView textView16 = (TextView)v.findViewById(R.id.mac);
            preventTextViewClick(textView16);
            textView16.setText(formMap.get("genFormChildMidArmCircumference"));
            TextView textView17 = (TextView)v.findViewById(R.id.hc);
            preventTextViewClick(textView17);
            textView17.setText(formMap.get("genFormChildHeadCircumference"));

        } else if("dischargesheet".equals(headTitle) && formMap!=null && v!=null){
            toolbar = (Toolbar) v.findViewById(R.id.toolbar);
            TextView textView = (TextView)v.findViewById(R.id.method_Admission);
            preventTextViewClick(textView);
            textView.setText(formMap.get("dischFormMethodofAdmission"));
            TextView textView1 = (TextView)v.findViewById(R.id.source_Admission);
            preventTextViewClick(textView1);
            textView1.setText(formMap.get("dischFormSourceofAdmission"));
            TextView textView2 = (TextView)v.findViewById(R.id.hospital_Site);
            preventTextViewClick(textView2);
            textView2.setText(formMap.get("dischFormHospitalSite"));
            TextView textView3 = (TextView)v.findViewById(R.id.responsible_Trust);
            preventTextViewClick(textView3);
            textView3.setText(formMap.get("dischFormResponsibleTrust"));
            TextView textView4 = (TextView)v.findViewById(R.id.date_Admission);
            preventTextViewClick(textView4);
            textView4.setText(formMap.get("dischFormAdmissionDate"));
            TextView textView5 = (TextView)v.findViewById(R.id.time_Admission);
            preventTextViewClick(textView5);
            textView5.setText(formMap.get("dischFormAdmissionTime"));
            TextView textView6 = (TextView)v.findViewById(R.id.date_Discharge);
            preventTextViewClick(textView6);
            textView6.setText(formMap.get("dischFormDischargeDate"));
            TextView textView7 = (TextView)v.findViewById(R.id.time_Discharge);
            preventTextViewClick(textView7);
            textView7.setText(formMap.get("dischFormDischargeTime"));
            TextView textView8 = (TextView)v.findViewById(R.id.discharge_Method);
            preventTextViewClick(textView8);
            textView8.setText(formMap.get("dischFormDischargeMethod"));
            TextView textView9 = (TextView)v.findViewById(R.id.discharge_Destination);
            preventTextViewClick(textView9);
            textView9.setText(formMap.get("dischFormDischargeDestination"));
            TextView textView10 = (TextView)v.findViewById(R.id.discharge_Consultant);
            preventTextViewClick(textView10);
            textView10.setText(formMap.get("dischFormDischargingConsultant"));
            TextView textView11 = (TextView)v.findViewById(R.id.dischargeSpecialty_department);
            preventTextViewClick(textView11);
            textView11.setText(formMap.get("dischFormDischargingSpeciality"));
            TextView textView12 = (TextView)v.findViewById(R.id.diagnosis_discharge);
            preventTextViewClick(textView12);
            textView12.setText(formMap.get("dischFormDiagnosisAtDischarge"));
            TextView textView13 = (TextView)v.findViewById(R.id.operations_procedures);
            preventTextViewClick(textView13);
            textView13.setText(formMap.get("dischFormOperationsandProcedures"));
            TextView textView14 = (TextView)v.findViewById(R.id.admission_complaints);
            preventTextViewClick(textView14);
            textView14.setText(formMap.get("dischFormAdmissionReasonandComplaints"));
            TextView textView15 = (TextView)v.findViewById(R.id.mental_capacity);
            preventTextViewClick(textView15);
            textView15.setText(formMap.get("dischFormMentalCapacity"));
            TextView textView16 = (TextView)v.findViewById(R.id.treatment_resuscitation);
            preventTextViewClick(textView16);
            textView16.setText(formMap.get("dischFormRefuseTreatmentandStatus"));
            TextView textView17 = (TextView)v.findViewById(R.id.allergies);
            preventTextViewClick(textView17);
            textView17.setText(formMap.get("dischFormAllergies"));
            TextView textView18 = (TextView)v.findViewById(R.id.risks_warnings);
            preventTextViewClick(textView18);
            textView18.setText(formMap.get("dischFormRisksandWarnings"));
            TextView textView19 = (TextView)v.findViewById(R.id.clinical_narrative);
            preventTextViewClick(textView19);
            textView19.setText(formMap.get("dischFormClinicalNarrative"));
            TextView textView20 = (TextView)v.findViewById(R.id.investigations_results);
            preventTextViewClick(textView20);
            textView20.setText(formMap.get("dischFormInvestigationsandResults"));
            TextView textView21 = (TextView)v.findViewById(R.id.treatment_treatments);
            preventTextViewClick(textView21);
            textView21.setText(formMap.get("dischFormTreatmentsandChanges"));
            TextView textView22 = (TextView)v.findViewById(R.id.physical_cognitive);
            preventTextViewClick(textView22);
            textView22.setText(formMap.get("dischFormMeasuresofPhysicalAbility"));
            TextView textView23 = (TextView)v.findViewById(R.id.medication_changes);
            preventTextViewClick(textView23);
            textView23.setText(formMap.get("dischFormMedicationChanges"));
            TextView textView24 = (TextView)v.findViewById(R.id.discharge_medications);
            preventTextViewClick(textView24);
            textView24.setText(formMap.get("dischFormDischargeMedications"));
            TextView textView25 = (TextView)v.findViewById(R.id.medication_recommendations);
            preventTextViewClick(textView25);
            textView25.setText(formMap.get("dischFormMedicationRecommendations"));
            TextView textView26 = (TextView)v.findViewById(R.id.hospital_actions);
            preventTextViewClick(textView26);
            textView26.setText(formMap.get("dischFormHospitalActionsRequired"));
            TextView textView27 = (TextView)v.findViewById(R.id.gp_actions);
            preventTextViewClick(textView27);
            textView27.setText(formMap.get("dischFormGPActionsRequired"));
            TextView textView28 = (TextView)v.findViewById(R.id.community_specialist);
            preventTextViewClick(textView28);
            textView28.setText(formMap.get("dischFormSpecialistServices"));
            TextView textView29 = (TextView)v.findViewById(R.id.information_patient_authorized);
            preventTextViewClick(textView29);
            textView29.setText(formMap.get("dischFormInfoGivenToPatient"));
            TextView textView30 = (TextView)v.findViewById(R.id.patient_con_exp_wishes);
            preventTextViewClick(textView30);
            textView30.setText(formMap.get("dischFormPatientConcernsandExpectations"));
            TextView textView31 = (TextView)v.findViewById(R.id.results_awaited);
            preventTextViewClick(textView31);
            textView31.setText(formMap.get("dischFormAwaitedResults"));

        } else if("investigationmicrobiology".equals(headTitle) && formMap!=null && v!=null) {

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

            ch1.setChecked(getBoolean(formMap.get("invstMicroFormGramsStaining")));
            ch2.setChecked(getBoolean(formMap.get("invstMicroFormZNStaining")));
            ch3.setChecked(getBoolean(formMap.get("invstMicroFormAlbertsStaining")));
            ch4.setChecked(getBoolean(formMap.get("invstMicroFormSalineSmear")));
            ch5.setChecked(getBoolean(formMap.get("invstMicroFormKOHSmear")));
            ch6.setChecked(getBoolean(formMap.get("invstMicroFormLactophenoSmearl")));
            ch7.setChecked(getBoolean(formMap.get("invstMicroFormIndiaInkStaining")));
            ch8.setChecked(getBoolean(formMap.get("invstMicroFormStoolForParasites")));
            ch9.setChecked(getBoolean(formMap.get("invstMicroFormSmearOfGonococci")));
            ch10.setChecked(getBoolean(formMap.get("invstMicroFormSmearOfFungi")));
            ch11.setChecked(getBoolean(formMap.get("invstMicroFormBloodSmearParaSites")));
            ch12.setChecked(getBoolean(formMap.get("invstMicroFormSmearOfHansens")));
            ch13.setChecked(getBoolean(formMap.get("invstMicroFormWidalTest")));
            ch14.setChecked(getBoolean(formMap.get("invstMicroFormRF")));
            ch15.setChecked(getBoolean(formMap.get("invstMicroFormASO")));
            ch16.setChecked(getBoolean(formMap.get("invstMicroFormCRP")));
            ch17.setChecked(getBoolean(formMap.get("invstMicroFormVDRL")));
            ch18.setChecked(getBoolean(formMap.get("invstMicroFormHBsag")));
            ch19.setChecked(getBoolean(formMap.get("invstMicroFormHCV")));
            ch20.setChecked(getBoolean(formMap.get("invstMicroFormHIV")));
            ch21.setChecked(getBoolean(formMap.get("invstMicroFormANA")));
            ch22.setChecked(getBoolean(formMap.get("invstMicroFormAntiTBIgA")));
            ch23.setChecked(getBoolean(formMap.get("invstMicroFormAntiTBIgM")));
            ch24.setChecked(getBoolean(formMap.get("invstMicroFormAntiTBIgG")));
            ch25.setChecked(getBoolean(formMap.get("invstMicroFormAntiLeptoSpiraIgM")));
            ch26.setChecked(getBoolean(formMap.get("invstMicroFormFungalCulture")));
            ch27.setChecked(getBoolean(formMap.get("invstMicroFormBacterialCulture")));
            ch28.setChecked(getBoolean(formMap.get("invstMicroFormBloodCulture")));
            ch29.setChecked(getBoolean(formMap.get("invstMicroFormEntricFever")));
            ch30.setChecked(getBoolean(formMap.get("invstMicroFormTuberculosisCulture")));

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

        } else if("investigationbiochemistry".equals(headTitle) && formMap!=null && v!=null) {

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
            TextView fluidsTextView  = (TextView) v.findViewById(R.id.f4);
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

            ch1.setChecked(getBoolean(formMap.get("invstBioFormRandomSerumGlucose")));
            ch2.setChecked(getBoolean(formMap.get("invstBioFormFastingSerumGlucose")));
            ch3.setChecked(getBoolean(formMap.get("invstBioFormPostprandialSerumGlucose")));
            ch4.setChecked(getBoolean(formMap.get("invstBioFormOralGlucoseTest")));
            ch5.setChecked(getBoolean(formMap.get("invstBioFormGlucoseChallengetest")));
            ch6.setChecked(getBoolean(formMap.get("invstBioFormGlycatedHaemoglobin")));
            ch7.setChecked(getBoolean(formMap.get("invstBioFormSerumUrea")));
            ch8.setChecked(getBoolean(formMap.get("invstBioFormSerumCreatinine")));
            ch9.setChecked(getBoolean(formMap.get("invstBioFormSerumCholesterol")));
            ch10.setChecked(getBoolean(formMap.get("invstBioFormSerumUricAcid")));
            ch11.setChecked(getBoolean(formMap.get("invstBioFormSerumTotalProtein")));
            ch12.setChecked(getBoolean(formMap.get("invstBioFormSerumAlbumin")));
            ch13.setChecked(getBoolean(formMap.get("invstBioFormSerumTotalBiliruin")));
            ch14.setChecked(getBoolean(formMap.get("invstBioFormSerumDirectBilurubin")));
            ch15.setChecked(getBoolean(formMap.get("invstBioFormAST")));
            ch16.setChecked(getBoolean(formMap.get("invstBioFormALT")));
            ch17.setChecked(getBoolean(formMap.get("invstBioFormAlkalinePhosphatase")));
            ch18.setChecked(getBoolean(formMap.get("invstBioFormAmulase")));
            ch19.setChecked(getBoolean(formMap.get("invstBioFormLipase")));
            ch20.setChecked(getBoolean(formMap.get("invstBioFormGammaGlutamyl")));
            ch21.setChecked(getBoolean(formMap.get("invstBioFormLactateDehydrogenase")));
            ch22.setChecked(getBoolean(formMap.get("invstBioFormCreatineKinase")));
            ch23.setChecked(getBoolean(formMap.get("invstBioFormCKMB")));
            ch24.setChecked(getBoolean(formMap.get("invstBioFormTroponin")));
            ch25.setChecked(getBoolean(formMap.get("invstBioFormSerumElectrolytes")));
            ch26.setChecked(getBoolean(formMap.get("invstBioFormPTwithINR")));
            ch27.setChecked(getBoolean(formMap.get("invstBioFormAPTT")));
            ch28.setChecked(getBoolean(formMap.get("invstBioFormSerumCalcium")));
            ch29.setChecked(getBoolean(formMap.get("invstBioFormSerumPhosphorous")));
            ch30.setChecked(getBoolean(formMap.get("invstBioFormSerumIron")));
            ch31.setChecked(getBoolean(formMap.get("invstBioFormTotalIronBinding")));
            ch32.setChecked(getBoolean(formMap.get("invstBioFormLipidProfile")));
            ch33.setChecked(getBoolean(formMap.get("invstBioFormLiverProfile")));
            ch34.setChecked(getBoolean(formMap.get("invstBioFormRenalProfile")));
            ch35.setChecked(getBoolean(formMap.get("invstBioFormThyroidProfile")));
            ch36.setChecked(getBoolean(formMap.get("invstBioFormThyroidMiniProfile")));
            ch37.setChecked(getBoolean(formMap.get("invstBioFormThyroidExtendedProfile")));
            ch38.setChecked(getBoolean(formMap.get("invstBioFormAnemiaCapsule")));
            ch39.setChecked(getBoolean(formMap.get("invstBioFormDiabetesCapsule")));
            ch40.setChecked(getBoolean(formMap.get("invstBioFormDiabetesMiniCapsule")));
            ch41.setChecked(getBoolean(formMap.get("invstBioFormUrobilinogen")));
            ch42.setChecked(getBoolean(formMap.get("invstBioFormMicroscopy")));
            ch43.setChecked(getBoolean(formMap.get("invstBioFormTotalT3")));
            ch44.setChecked(getBoolean(formMap.get("invstBioFormTotalT4")));
            ch45.setChecked(getBoolean(formMap.get("invstBioFormTSH")));
            ch46.setChecked(getBoolean(formMap.get("invstBioFormFreeT3")));
            ch47.setChecked(getBoolean(formMap.get("invstBioFormFreeT4")));
            ch48.setChecked(getBoolean(formMap.get("invstBioFormAntiTPO")));
            ch49.setChecked(getBoolean(formMap.get("invstBioFormLH")));
            ch50.setChecked(getBoolean(formMap.get("invstBioFormFSH")));
            ch51.setChecked(getBoolean(formMap.get("invstBioFormProlactin")));
            ch52.setChecked(getBoolean(formMap.get("invstBioFormTestosterone")));
            ch53.setChecked(getBoolean(formMap.get("invstBioFormPSA")));
            ch54.setChecked(getBoolean(formMap.get("invstBioFormFerritin")));
            ch55.setChecked(getBoolean(formMap.get("invstBioFormCSFAnalysis")));
            ch56.setChecked(getBoolean(formMap.get("invstBioFormPleuralFluidAnalysis")));
            ch57.setChecked(getBoolean(formMap.get("invstBioFormFluidRenalProfile")));
            ch58.setChecked(getBoolean(formMap.get("invstBioFormAsciticProteins")));
            ch59.setChecked(getBoolean(formMap.get("invstBioFormAsciticGlucose")));
            ch60.setChecked(getBoolean(formMap.get("invstBioFormAsciticAlbumin")));
            ch61.setChecked(getBoolean(formMap.get("invstBioFormAmylase")));
            ch62.setChecked(getBoolean(formMap.get("invstBioFormElectrolytes")));
            ch63.setChecked(getBoolean(formMap.get("invstBioFormUMicroAlbumin")));
            ch64.setChecked(getBoolean(formMap.get("invstBioFormUPhosphates")));
            ch65.setChecked(getBoolean(formMap.get("invstBioFormUUricAcid")));
            ch66.setChecked(getBoolean(formMap.get("invstBioForm24hrsUrinaryProteins")));
            ch67.setChecked(getBoolean(formMap.get("invstBioFormUCreatinine")));
            ch68.setChecked(getBoolean(formMap.get("invstBioFormUCalcium")));

            if(!ch58.isChecked() && !ch59.isChecked() && !ch60.isChecked() && !ch61.isChecked()) {
                fluidsTextView.setVisibility(View.GONE);
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

        } else if("investigationpathology".equals(headTitle) && formMap!=null && v!=null) {

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
            CheckBox ch41 = (CheckBox) v.findViewById(R.id.cch14);
            v.findViewById(R.id.c15).setVisibility(View.GONE);
            v.findViewById(R.id.c16).setVisibility(View.GONE);
            TextView bodyfluids = (TextView) v.findViewById(R.id.c17);
            CheckBox ch42 = (CheckBox) v.findViewById(R.id.cch18);
            CheckBox ch43 = (CheckBox) v.findViewById(R.id.cch19);
            CheckBox ch44 = (CheckBox) v.findViewById(R.id.cch20);
            CheckBox ch45 = (CheckBox) v.findViewById(R.id.cch21);
            CheckBox ch46 = (CheckBox) v.findViewById(R.id.cch22);
            v.findViewById(R.id.c23).setVisibility(View.GONE);

            ch1.setChecked(getBoolean(formMap.get("invstPathFormHaemogram")));
            ch2.setChecked(getBoolean(formMap.get("invstPathFormCBC")));
            ch3.setChecked(getBoolean(formMap.get("invstPathFormCBP")));
            ch4.setChecked(getBoolean(formMap.get("invstPathFormHaemoglobin")));
            ch5.setChecked(getBoolean(formMap.get("invstPathFormPCV")));
            ch6.setChecked(getBoolean(formMap.get("invstPathFormTotalWBCCount")));
            ch7.setChecked(getBoolean(formMap.get("invstPathFormDifferentialCount")));
            ch8.setChecked(getBoolean(formMap.get("invstPathFormESR")));
            ch9.setChecked(getBoolean(formMap.get("invstPathFormAbnormalCells")));
            ch10.setChecked(getBoolean(formMap.get("invstPathFormRBCCount")));
            ch11.setChecked(getBoolean(formMap.get("invstPathFormRDW")));
            ch12.setChecked(getBoolean(formMap.get("invstPathFormPlateletCount")));
            ch13.setChecked(getBoolean(formMap.get("invstPathFormPDW")));
            ch14.setChecked(getBoolean(formMap.get("invstPathFormPMW")));
            ch15.setChecked(getBoolean(formMap.get("invstPathFormBleedingTime")));
            ch16.setChecked(getBoolean(formMap.get("invstPathFormClottingTime")));
            ch17.setChecked(getBoolean(formMap.get("invstPathFormReticulocyteCount")));
            ch18.setChecked(getBoolean(formMap.get("invstPathFormPeripheralSmear")));
            ch19.setChecked(getBoolean(formMap.get("invstPathFormSickleCellTest")));
            ch20.setChecked(getBoolean(formMap.get("invstPathFormOsmoticFragilityTest")));
            ch21.setChecked(getBoolean(formMap.get("invstPathFormLECellTest")));
            ch22.setChecked(getBoolean(formMap.get("invstPathFormAEC")));
            ch23.setChecked(getBoolean(formMap.get("invstPathFormAbsoluteMCV")));
            ch24.setChecked(getBoolean(formMap.get("invstPathFormAbsoluteMCH")));
            ch25.setChecked(getBoolean(formMap.get("invstPathFormAbsoluteMCHC")));
            ch26.setChecked(getBoolean(formMap.get("invstPathFormSmearForMP")));
            ch27.setChecked(getBoolean(formMap.get("invstPathFormRapidTestMP")));
            ch28.setChecked(getBoolean(formMap.get("invstPathFormOtherInvestigations")));
            ch29.setChecked(getBoolean(formMap.get("invstPathFormRoutineUrineAnalysis")));
            ch30.setChecked(getBoolean(formMap.get("invstPathFormCompleteUrineExam")));
            ch31.setChecked(getBoolean(formMap.get("invstPathFormPH")));
            ch32.setChecked(getBoolean(formMap.get("invstPathFormSpecificGravity")));
            ch33.setChecked(getBoolean(formMap.get("invstPathFormProteins")));
            ch34.setChecked(getBoolean(formMap.get("invstPathFormSugar")));
            ch35.setChecked(getBoolean(formMap.get("invstPathFormKetoneBodies")));
            ch36.setChecked(getBoolean(formMap.get("invstPathFormBileSalts")));
            ch37.setChecked(getBoolean(formMap.get("invstPathFormBilePigments")));
            ch38.setChecked(getBoolean(formMap.get("invstPathFormUrobilinogen")));
            ch39.setChecked(getBoolean(formMap.get("invstPathFormMicroscopy")));
            ch40.setChecked(getBoolean(formMap.get("invstPathFormbenceHonesProtien")));
            ch41.setChecked(getBoolean(formMap.get("invstPathFormHmosiderinPigment")));
            ch42.setChecked(getBoolean(formMap.get("invstPathFormCSF")));
            ch43.setChecked(getBoolean(formMap.get("invstPathFormAsciticFluid")));
            ch44.setChecked(getBoolean(formMap.get("invstPathFormPleuralFluid")));
            ch45.setChecked(getBoolean(formMap.get("invstPathFormSynovialFluid")));
            ch46.setChecked(getBoolean(formMap.get("invstPathFormPeriCardialFluid")));

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

        v.invalidate();
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
        formLayoutIds.put("uploadfiles", R.layout.file_attachment_list);
        formLayoutIds.put("generalphysicalexamination",R.layout.activity_general_examination);
        formLayoutIds.put("dischargesheet",R.layout.activity_discharge_sheet);
        formLayoutIds.put("investigationbiochemistry", R.layout.activity_investigation_biochemistry);
        formLayoutIds.put("investigationmicrobiology", R.layout.activity_investigation_microbiology);
        formLayoutIds.put("investigationpathology", R.layout.activity_investigation_pathlogy);
        return formLayoutIds;
    }

    private String getValueInText(String value){
        return ("1".equals(value)) ? "Yes" : "No";
    }

    private Boolean getBoolean(String value) {
        return ("1".equals(value)) ? Boolean.TRUE : Boolean.FALSE;
    }

    private void hideRadioGroup(RadioGroup radioGroups[]){
        for(RadioGroup rg : radioGroups)
            rg.setVisibility(View.GONE);
    }

    private void preventTextViewClick(TextView textView){
        textView.setMinLines(1);
        textView.setClickable(false);
        textView.setFocusable(false);
    }
}
