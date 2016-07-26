package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HealthHistory extends AppCompatActivity {

    private Toolbar toolbar;
    private String previousIlliness;
    private String presentIlliness;
    private String socialHistory;
    private String familyHistory;
    private String drugHistory;
    private String allergies;
    private String systemreview;
    private String summary;
    private String menstrualHistory;
    private String personalHistory;
    private String finalDiagnosis;
    private String provisionalDiagnosis;
    private String healthRegistrationId;
    private String regLinkId;
    private String patientName;
    private String caseid;
    private String phone;
    private String proofType;
    private String proofNumber;
    private String gender;
    private String email;
    private String dob;

    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/caserecordinsert";
    private BroadcastReceiver logoutReceiver;
    private String jsonparams;
    private boolean isEmptyForm;
    private String formFieldData[];
    private Uri selectedImageUri;
    private String filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        logoutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                Intent login = new Intent(
                        getApplicationContext(),
                        loginactivity.class);
                startActivity(login);
                finish();
            }
        };
        registerReceiver(logoutReceiver, intentFilter);
        setContentView(R.layout.activity_health_history);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        Button saveBtn = (Button) findViewById(R.id.button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousIlliness = ((EditText) findViewById(R.id.previousIlliness)).getText().toString().trim();
                presentIlliness = ((EditText) findViewById(R.id.presentIllness)).getText().toString().trim();
                familyHistory = ((EditText) findViewById(R.id.familyhistory)).getText().toString().trim();
                socialHistory = ((EditText) findViewById(R.id.socialhistory)).getText().toString().trim();
                drugHistory = ((EditText) findViewById(R.id.drugHistory)).getText().toString().trim();
                allergies = ((EditText) findViewById(R.id.allergies)).getText().toString().trim();
                personalHistory = ((EditText) findViewById(R.id.personalHistory)).getText().toString().trim();
                menstrualHistory = ((EditText) findViewById(R.id.menstrualhistory)).getText().toString().trim();
                systemreview = ((EditText) findViewById(R.id.systemreview)).getText().toString().trim();
                summary = ((EditText) findViewById(R.id.summary)).getText().toString().trim();
                finalDiagnosis = ((EditText) findViewById(R.id.finalDiagnosis)).getText().toString().trim();
                provisionalDiagnosis = ((EditText) findViewById(R.id.provisionalDiagnosis)).getText().toString().trim();

                isEmptyForm = true;
                formFieldData = new String[]{previousIlliness, presentIlliness, familyHistory, socialHistory, drugHistory, allergies, personalHistory, menstrualHistory, systemreview, summary, provisionalDiagnosis, finalDiagnosis};
                for (String field : formFieldData) {
                    if (!field.isEmpty()) {
                        isEmptyForm = false;
                        break;
                    }
                }

                if (isEmptyForm) {
                    Toast.makeText(context, "Please Fill The Form", Toast.LENGTH_SHORT).show();
                    return;
                }

                healthRegistrationId = getIntent().getExtras().getString("regid");
                regLinkId = getIntent().getExtras().getString("regLinkId");
                patientName = getIntent().getExtras().getString("name");
                caseid = getIntent().getExtras().getString("caseid");
                phone = getIntent().getExtras().getString("phone");
                proofType = getIntent().getExtras().getString("proofType");
                proofNumber = getIntent().getExtras().getString("proofNumber");
                email = getIntent().getExtras().getString("email");
                gender = getIntent().getExtras().getString("gender");
                dob = getIntent().getExtras().getString("dob");

                ArrayList<String> formsList = new ArrayList<>();
                formsList.add(getIntent().getExtras().getString("formName"));

                final Map<String, Object> insertparams = new HashMap<>();
                insertparams.put("histFormPresentIllNess", presentIlliness);
                insertparams.put("histFormPreviousIllness", previousIlliness);
                insertparams.put("histFormDrug", drugHistory);
                insertparams.put("histFormAllergiesandReactions", allergies);
                insertparams.put("histFormPersonal", personalHistory);
                insertparams.put("histFormMenstrual", menstrualHistory);
                insertparams.put("histFormFamily", familyHistory);
                insertparams.put("histFormSocial", socialHistory);
                insertparams.put("histFormSystemsReview", systemreview);
                insertparams.put("histFormSummary", summary);
                insertparams.put("practiceFormNameDataIndex", "1");
                insertparams.put("practiceFormNames", formsList);
                insertparams.put("actionType", "save");
                insertparams.put("healthRegistrationId", healthRegistrationId);
                insertparams.put("patientName", patientName);
                insertparams.put("regnLinkId", regLinkId);
                insertparams.put("provisionalDiagnosis", provisionalDiagnosis);
                insertparams.put("finalDiagnosis", finalDiagnosis);

                if (caseid != null) {
                    insertparams.put("caseRecordNo", caseid);
                } else {
                    insertparams.put("caseRecordNo", "");
                }
                jsonparams = new Gson().toJson(insertparams);

                new AlertDialog.Builder(context).setTitle("Alert")
                        .setMessage("Do you want to save record").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            saveCaseRecord(jsonparams, context);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }

    private void saveCaseRecord(String jsonparams, final Context ctx)
    {
        Map<String, String> params = new HashMap<>();
        params.put("serverUrl", serverUrl);
        params.put("operationType", "5");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                HealthHistory.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if (resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {

                            if("".equalsIgnoreCase(caseid))
                                caseid = resultJson.getString("caseRecordNo");

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);

                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("Case Record Saved Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getApplicationContext(),
                                            CaseRecordFormsActivity.class);
                                    caseRecordsActivity.putExtra("regid", healthRegistrationId);
                                    caseRecordsActivity.putExtra("name", patientName);
                                    caseRecordsActivity.putExtra("phone", phone);
                                    caseRecordsActivity.putExtra("dob", dob);
                                    caseRecordsActivity.putExtra("email", email);
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", gender);
                                    caseRecordsActivity.putExtra("reqLinkId", regLinkId);
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
                    new AlertDialog.Builder(HealthHistory.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Saving Case Record");
        worker.execute(params);
    }

/*    @Override
    protected void onStop(){
        super.onStop();
    }*/

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_history, menu);
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
                    HealthHistory.this, new ActionCallback() {
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
