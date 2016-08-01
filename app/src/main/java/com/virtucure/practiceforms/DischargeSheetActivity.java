package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 7/12/2016.
 */
public class DischargeSheetActivity extends AppCompatActivity
{
    private String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/caserecordinsert";
    private BroadcastReceiver logoutReceiver = null;
    ExpandableListView expListView;
    private Toolbar toolbar;
    private String healthRegistrationId;
    private String regLinkId;
    private String patientName;
    private String caseid;
    private String phone;
    private String proofType;
    private String proofNumber;
    private String email;
    private String dob;
    private String formName;
    private String gender;

    private String methodAdmisson;
    private String sourceAdmission;
    private String hospitalSite;
    private String responsibleTrust;
    private String dateAdmission;
    private String timeAdmission;

    private String dateDischarge;
    private String timeDischarge;
    private String dischargeMethod;
    private String dischargeDestination;
    private String dischargeConsultant;
    private String dischargeDepartment;

    private String diagnosisDischarge;
    private String operations;
    private String admissionComplaints;
    private String mentalCapacity;
    private String treatment;
    private String allergies;
    private String risksWarnings;
    private String clinicalNarrative;
    private String investigations;
    private String relevantTreatment;
    private String cognitive;
    private String medication;
    private String dischargeMedications;
    private String medicationRecommendations;

    private String hospitalActions;
    private String gpActions;
    private String communityServices;
    private String patientAuthentication;
    private String patientWishes;
    private String resultsAwaited;

    private boolean isEmptyForm;

    List<String> listDataHeader;
    private int lastExpandedPosition = -1;

    private Context context = this;

    private DischargeSheetAdapter listAdapter;

    private String formFieldData[];

    private String jsonparams;

    private View formView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        logoutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                Intent login = new Intent(
                        getBaseContext(),
                        loginactivity.class);
                startActivity(login);
                finish();
            }
        };
        registerReceiver(logoutReceiver, intentFilter);

        healthRegistrationId = getIntent().getExtras().getString("regid");
        regLinkId = getIntent().getExtras().getString("regLinkId");
        patientName = getIntent().getExtras().getString("name");
        phone = getIntent().getExtras().getString("phone");
        proofType = getIntent().getExtras().getString("proofType");
        proofNumber = getIntent().getExtras().getString("proofNumber");
        email = getIntent().getExtras().getString("email");
        dob = getIntent().getExtras().getString("dob");
        caseid = getIntent().getExtras().getString("caseid");
        formName = getIntent().getExtras().getString("formName");
        gender = getIntent().getExtras().getString("gender");

        setContentView(R.layout.activity_discharge_sheet_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        expListView = (ExpandableListView) findViewById(R.id.discharge_block);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition)
                    expListView.collapseGroup(lastExpandedPosition);
                lastExpandedPosition = groupPosition;
            }
        });
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                } else {
                    parent.collapseGroup(groupPosition);
                }
                parent.setSelectedGroup(groupPosition);
                return true;
            }
        });

        listDataHeader = new ArrayList<>();
        listDataHeader.add(getResources().getString(R.string.title_activity_admission));
        listDataHeader.add(getResources().getString(R.string.title_activity_discharge_details));
        listDataHeader.add(getResources().getString(R.string.title_activity_clinic));
        listDataHeader.add(getResources().getString(R.string.title_activity_advice_plan));

        listAdapter = new DischargeSheetAdapter(this, listDataHeader, new DischargeSheetAdapter.FormListener() {
            @Override
            public void onFormSaveListener(View view) {
                formView = view;
            }
        });
        expListView.setAdapter(listAdapter);

        Button btn = (Button) findViewById(R.id.save_dischargesheet);
        if(btn != null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        if(formView != null) {
                            methodAdmisson = ((EditText)formView.findViewById(R.id.method_Admission)).getText().toString().trim();
                            sourceAdmission = ((EditText)formView.findViewById(R.id.source_Admission)).getText().toString().trim();
                            hospitalSite = ((EditText)formView.findViewById(R.id.hospital_Site)).getText().toString().trim();
                            responsibleTrust = ((EditText)formView.findViewById(R.id.responsible_Trust)).getText().toString().trim();
                            dateAdmission = ((EditText)formView.findViewById(R.id.date_Admission)).getText().toString().trim();
                            timeAdmission = ((EditText)formView.findViewById(R.id.time_Admission)).getText().toString().trim();
                            dateDischarge = ((EditText)formView.findViewById(R.id.date_Discharge)).getText().toString().trim();
                            timeDischarge = ((EditText)formView.findViewById(R.id.time_Discharge)).getText().toString().trim();
                            dischargeMethod = ((EditText)formView.findViewById(R.id.discharge_Method)).getText().toString().trim();
                            dischargeDestination = ((EditText)formView.findViewById(R.id.discharge_Destination)).getText().toString().trim();
                            dischargeConsultant = ((EditText)formView.findViewById(R.id.discharge_Consultant)).getText().toString().trim();
                            dischargeDepartment = ((EditText)formView.findViewById(R.id.dischargeSpecialty_department)).getText().toString().trim();
                            diagnosisDischarge = ((EditText)formView.findViewById(R.id.diagnosis_discharge)).getText().toString().trim();
                            operations = ((EditText)formView.findViewById(R.id.operations_procedures)).getText().toString().trim();
                            admissionComplaints = ((EditText)formView.findViewById(R.id.admission_complaints)).getText().toString().trim();
                            mentalCapacity = ((EditText)formView.findViewById(R.id.mental_capacity)).getText().toString().trim();
                            treatment = ((EditText)formView.findViewById(R.id.treatment_resuscitation)).getText().toString().trim();
                            allergies = ((EditText)formView.findViewById(R.id.allergies)).getText().toString().trim();
                            risksWarnings = ((EditText)formView.findViewById(R.id.risks_warnings)).getText().toString().trim();
                            clinicalNarrative = ((EditText)formView.findViewById(R.id.clinical_narrative)).getText().toString().trim();
                            investigations = ((EditText)formView.findViewById(R.id.investigations_results)).getText().toString().trim();
                            relevantTreatment = ((EditText)formView.findViewById(R.id.treatment_treatments)).getText().toString().trim();
                            cognitive = ((EditText)formView.findViewById(R.id.physical_cognitive)).getText().toString().trim();
                            medication = ((EditText)formView.findViewById(R.id.medication_changes)).getText().toString().trim();
                            dischargeMedications = ((EditText)formView.findViewById(R.id.discharge_medications)).getText().toString().trim();
                            medicationRecommendations = ((EditText)formView.findViewById(R.id.medication_recommendations)).getText().toString().trim();
                            hospitalActions = ((EditText)formView.findViewById(R.id.hospital_actions)).getText().toString().trim();
                            gpActions = ((EditText)formView.findViewById(R.id.gp_actions)).getText().toString().trim();
                            communityServices = ((EditText)formView.findViewById(R.id.community_specialist)).getText().toString().trim();
                            patientAuthentication = ((EditText)formView.findViewById(R.id.information_patient_authorized)).getText().toString().trim();
                            patientWishes = ((EditText)formView.findViewById(R.id.patient_con_exp_wishes)).getText().toString().trim();
                            resultsAwaited = ((EditText)formView.findViewById(R.id.results_awaited)).getText().toString().trim();

                            isEmptyForm = true;
                            formFieldData = new String[]{methodAdmisson, sourceAdmission, hospitalSite, responsibleTrust, dateAdmission, timeAdmission, dateDischarge, timeDischarge, dischargeMethod, dischargeDestination, dischargeConsultant, dischargeDepartment, diagnosisDischarge, operations, admissionComplaints, mentalCapacity, treatment, allergies, risksWarnings, clinicalNarrative, investigations, relevantTreatment, cognitive, medication, dischargeMedications, medicationRecommendations, hospitalActions, gpActions, communityServices, patientAuthentication, patientWishes, resultsAwaited};
                            for (String field : formFieldData) {
                                if (!field.isEmpty()) {
                                    isEmptyForm = false;
                                    break;
                                }
                            }

                            if(isEmptyForm){
                                Toast.makeText(context, R.string.fill_form_msg, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            ArrayList<String> formsList = new ArrayList<String>();
                            formsList.add(formName);

                            final Map<String, Object> insertparams = new HashMap<>();
                            insertparams.put("dischFormMethodofAdmission", methodAdmisson);
                            insertparams.put("dischFormSourceofAdmission", sourceAdmission);
                            insertparams.put("dischFormHospitalSite", hospitalSite);
                            insertparams.put("dischFormResponsibleTrust", responsibleTrust);
                            insertparams.put("dischFormAdmissionDate", dateAdmission);
                            insertparams.put("dischFormAdmissionTime", timeAdmission);
                            insertparams.put("dischFormDischargeDate", dateDischarge);
                            insertparams.put("dischFormDischargeTime", timeDischarge);
                            insertparams.put("dischFormDischargeMethod", dischargeMethod);
                            insertparams.put("dischFormDischargeDestination", dischargeDestination);
                            insertparams.put("dischFormDischargingConsultant", dischargeConsultant);
                            insertparams.put("dischFormDischargingSpeciality", dischargeDepartment);
                            insertparams.put("dischFormDiagnosisAtDischarge", diagnosisDischarge);
                            insertparams.put("dischFormOperationsandProcedures", operations);
                            insertparams.put("dischFormAdmissionReasonandComplaints", admissionComplaints);
                            insertparams.put("dischFormMentalCapacity", mentalCapacity);
                            insertparams.put("dischFormRefuseTreatmentandStatus", treatment);
                            insertparams.put("dischFormAllergies", allergies);
                            insertparams.put("dischFormRisksandWarnings", risksWarnings);
                            insertparams.put("dischFormClinicalNarrative", clinicalNarrative);
                            insertparams.put("dischFormInvestigationsandResults", investigations);
                            insertparams.put("dischFormTreatmentsandChanges", relevantTreatment);
                            insertparams.put("dischFormMeasuresofPhysicalAbility", cognitive);
                            insertparams.put("dischFormMedicationChanges", medication);
                            insertparams.put("dischFormDischargeMedications", dischargeMedications);
                            insertparams.put("dischFormMedicationRecommendations", medicationRecommendations);
                            insertparams.put("dischFormHospitalActionsRequired", hospitalActions);
                            insertparams.put("dischFormGPActionsRequired", gpActions);
                            insertparams.put("dischFormSpecialistServices", communityServices);
                            insertparams.put("dischFormInfoGivenToPatient", patientAuthentication);
                            insertparams.put("dischFormPatientConcernsandExpectations", patientWishes);
                            insertparams.put("dischFormAwaitedResults", resultsAwaited);
                            insertparams.put("practiceFormNameDataIndex", "1");
                            insertparams.put("practiceFormNames", formsList);
                            insertparams.put("actionType", "save");
                            insertparams.put("healthRegistrationId", healthRegistrationId);
                            insertparams.put("patientName", patientName);
                            insertparams.put("regnLinkId", regLinkId);

                            if (caseid != null) {
                                insertparams.put("caseRecordNo", caseid);
                            } else {
                                insertparams.put("caseRecordNo", "");
                            }
                            jsonparams = new Gson().toJson(insertparams);

                            new AlertDialog.Builder(context).setTitle("Alert")
                                    .setMessage(R.string.save_record_msg).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    saveCaseRecord(jsonparams, context);
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                        }
                    }catch (Exception e){
                        Log.e("error","exception occurs:"+e);
                    }
                }

            });
        }
    }

    private void saveCaseRecord(String jsonparams, final Context ctx) {
        Map<String, String> params = new HashMap<>();
        params.put("serverUrl", serverUrl);
        params.put("operationType", "5");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                DischargeSheetActivity.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if (resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {

                            if ("".equalsIgnoreCase(caseid))
                                caseid = resultJson.getString("caseRecordNo");

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);

                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("Case Record Saved Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getApplicationContext(),
                                            CaseRecordFormsMainActivity.class);
                                    caseRecordsActivity.putExtra("regid", healthRegistrationId);
                                    caseRecordsActivity.putExtra("name", patientName);
                                    caseRecordsActivity.putExtra("phone", phone);
                                    caseRecordsActivity.putExtra("dob", dob);
                                    caseRecordsActivity.putExtra("email", email);
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", gender);
                                    caseRecordsActivity.putExtra("regLinkId", regLinkId);
                                    caseRecordsActivity.putExtra("caserecordno", caseid);
                                    caseRecordsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(caseRecordsActivity);
                                    finish();
                                }
                            }).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Some error has occcurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(DischargeSheetActivity.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Saving Case Record");
        worker.execute(params);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discharge_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/logout";
            Map<String, String> nameValues = new HashMap<String, String>();
            nameValues.put("serverUrl", serverUrl);
            nameValues.put("operationType", "6");

            AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                    DischargeSheetActivity.this, new ActionCallback() {
                public void onCallback(Map<String, String> result) {
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                    sendBroadcast(broadcastIntent);
                }

            }, "Logging off");

            worker.execute(nameValues);
        }
        return super.onOptionsItemSelected(item);
    }
}
