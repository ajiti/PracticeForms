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
 * Created by AJITI on 7/4/2016.
 */
public class GeneralExaminationActivity extends AppCompatActivity {
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
    private String consciousness;
    private String mState;
    private String generalAppearance;
    private String pallor;
    private String icterus;
    private String cyanosis;
    private String clubbing;
    private String lymphAdenopathy;
    private String edema;

    private String temperature;
    private String pulse;
    private String respiration;
    private String bloodPressure;

    private String height;
    private String weight;
    private String bmi;
    private String mACircumference;
    private String headCircumference;

    private boolean isEmptyForm;

    List<String> listDataHeader;
    private int lastExpandedPosition = -1;

    private Context context = this;

    private GeneralExaminationAdapter listAdapter;

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

        setContentView(R.layout.activity_general_examination_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        expListView = (ExpandableListView) findViewById(R.id.exam_block);
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
        listDataHeader.add(getResources().getString(R.string.title_activity_general));
        listDataHeader.add(getResources().getString(R.string.title_activity_vital));
        listDataHeader.add(getResources().getString(R.string.title_activity_amthropometry));

        listAdapter = new GeneralExaminationAdapter(this, listDataHeader, new GeneralExaminationAdapter.formListener(){

            @Override
            public void onFormSaveListener(View view) {
                formView = view;
            }
        });
        expListView.setAdapter(listAdapter);

        final Button btn = (Button) findViewById(R.id.insave);
        if(btn != null){
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        if(formView != null){
                            consciousness = ((EditText)formView.findViewById(R.id.consciousness)).getText().toString().trim();
                            mState = ((EditText)formView.findViewById(R.id.mental_state)).getText().toString().trim();
                            generalAppearance = ((EditText)formView.findViewById(R.id.general_appearance)).getText().toString().trim();
                            pallor = ((EditText)formView.findViewById(R.id.pallor)).getText().toString().trim();
                            icterus = ((EditText)formView.findViewById(R.id.icterus)).getText().toString().trim();
                            cyanosis = ((EditText)formView.findViewById(R.id.cyanosis)).getText().toString().trim();
                            clubbing = ((EditText)formView.findViewById(R.id.clubbing)).getText().toString().trim();
                            lymphAdenopathy = ((EditText)formView.findViewById(R.id.lymph_adenopathy)).getText().toString().trim();
                            edema = ((EditText)formView.findViewById(R.id.edema)).getText().toString().trim();
                            temperature = ((EditText)formView.findViewById(R.id.temperature)).getText().toString().trim();
                            pulse = ((EditText)formView.findViewById(R.id.pulse)).getText().toString().trim();
                            respiration = ((EditText)formView.findViewById(R.id.respiration)).getText().toString().trim();
                            bloodPressure = ((EditText)formView.findViewById(R.id.blood_pressure)).getText().toString().trim();
                            height = ((EditText)formView.findViewById(R.id.height)).getText().toString().trim();
                            weight = ((EditText)formView.findViewById(R.id.weight)).getText().toString().trim();
                            bmi = ((EditText)formView.findViewById(R.id.bmi)).getText().toString().trim();
                            mACircumference = ((EditText)formView.findViewById(R.id.mac)).getText().toString().trim();
                            headCircumference = ((EditText)formView.findViewById(R.id.hc)).getText().toString().trim();

                            isEmptyForm = true;
                            formFieldData = new String[]{consciousness, mState, generalAppearance, pallor, icterus, cyanosis, clubbing, lymphAdenopathy, edema, temperature, pulse, respiration, bloodPressure, height, weight, bmi, mACircumference, headCircumference};
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
                            insertparams.put("genFormConseiousness", consciousness);
                            insertparams.put("genFormMentalState", mState);
                            insertparams.put("genFormGeneralAppearance", generalAppearance);
                            insertparams.put("genFormPallor", pallor);
                            insertparams.put("genFormIcterus", icterus);
                            insertparams.put("genFormCyanosis", cyanosis);
                            insertparams.put("genFormClubbing", clubbing);
                            insertparams.put("genFormLymphAdenopathy", lymphAdenopathy);
                            insertparams.put("genFormEdema", edema);
                            insertparams.put("genFormTemparature", temperature);
                            insertparams.put("genFormPulse", pulse);
                            insertparams.put("genFormRepisration", respiration);
                            insertparams.put("genFormBloodPressure", bloodPressure);
                            insertparams.put("genFormHeight", height);
                            insertparams.put("genFormWeight", weight);
                            insertparams.put("genFormBodyMassIndex", bmi);
                            insertparams.put("genFormChildMidArmCircumference", mACircumference);
                            insertparams.put("genFormChildHeadCircumference", headCircumference);
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
                    } catch(Exception e){
                        Log.e("error", "exception occurs:" + e);
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
                GeneralExaminationActivity.this, new ActionCallback() {
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
                    new AlertDialog.Builder(GeneralExaminationActivity.this)
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
        getMenuInflater().inflate(R.menu.menu_general_examination, menu);
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
                    GeneralExaminationActivity.this, new ActionCallback() {
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
