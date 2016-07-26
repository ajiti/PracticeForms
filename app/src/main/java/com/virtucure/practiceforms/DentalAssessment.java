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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DentalAssessment extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean painInTeeth;
    private boolean severeSensitivityOfTeethOnDrinks ;
    private boolean painorBleedingInGums ;
    private boolean looseTeethWithGaps;
    private boolean unpleasantTaste;
    private boolean grindYourTeethWhenSleep;
    private boolean swellingInGumsWithFluid;
    private boolean severePainInTeethAtNight;
    private boolean painnAndSwellingInGums;
    private boolean irregularPlacedTeeth;
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
    private String formName;
    private String jsonparams;

    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/caserecordinsert";
    private BroadcastReceiver logoutReceiver = null;

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
                        getBaseContext(),
                        loginactivity.class);
                startActivity(login);
                finish();
            }
        };
        registerReceiver(logoutReceiver, intentFilter);
        setContentView(R.layout.activity_dental_assessment);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        final Button saveBtn = (Button) findViewById(R.id.dentalsave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    healthRegistrationId = getIntent().getExtras().getString("regid");
                    regLinkId = getIntent().getExtras().getString("regLinkId");
                    patientName = getIntent().getExtras().getString("name");
                    phone = getIntent().getExtras().getString("phone");
                    proofType = getIntent().getExtras().getString("proofType");
                    proofNumber = getIntent().getExtras().getString("proofNumber");
                    email = getIntent().getExtras().getString("email");
                    gender = getIntent().getExtras().getString("gender");
                    dob = getIntent().getExtras().getString("dob");
                    caseid = getIntent().getExtras().getString("caseid");
                    formName = getIntent().getExtras().getString("formName");

                    int rg1 = ((RadioGroup) findViewById(R.id.denradiogroup1)).getCheckedRadioButtonId();
                    int rg2 = ((RadioGroup) findViewById(R.id.denradiogroup2)).getCheckedRadioButtonId();
                    int rg3 = ((RadioGroup) findViewById(R.id.denradiogroup3)).getCheckedRadioButtonId();
                    int rg4 = ((RadioGroup) findViewById(R.id.denradiogroup4)).getCheckedRadioButtonId();
                    int rg5 = ((RadioGroup) findViewById(R.id.denradiogroup5)).getCheckedRadioButtonId();
                    int rg6 = ((RadioGroup) findViewById(R.id.denradiogroup6)).getCheckedRadioButtonId();
                    int rg7 = ((RadioGroup) findViewById(R.id.denradiogroup7)).getCheckedRadioButtonId();
                    int rg8 = ((RadioGroup) findViewById(R.id.denradiogroup8)).getCheckedRadioButtonId();
                    int rg9 = ((RadioGroup) findViewById(R.id.denradiogroup9)).getCheckedRadioButtonId();
                    int rg10 = ((RadioGroup) findViewById(R.id.denradiogroup10)).getCheckedRadioButtonId();

                    if(rg1 != -1)
                        painInTeeth = getCheckedValue((String) ((RadioButton) findViewById(rg1)).getText());
                    if(rg2 != -1)
                        severeSensitivityOfTeethOnDrinks = getCheckedValue((String) ((RadioButton) findViewById(rg2)).getText());
                    if(rg3 != -1)
                        painorBleedingInGums = getCheckedValue((String) ((RadioButton) findViewById(rg3)).getText());
                    if(rg4 != -1)
                        looseTeethWithGaps = getCheckedValue((String) ((RadioButton) findViewById(rg4)).getText());
                    if(rg5 != -1)
                        unpleasantTaste = getCheckedValue((String) ((RadioButton) findViewById(rg5)).getText());
                    if(rg6 != -1)
                        grindYourTeethWhenSleep = getCheckedValue((String) ((RadioButton) findViewById(rg6)).getText());
                    if(rg7 != -1)
                        swellingInGumsWithFluid = getCheckedValue((String) ((RadioButton) findViewById(rg7)).getText());
                    if(rg8 != -1)
                        severePainInTeethAtNight = getCheckedValue((String) ((RadioButton) findViewById(rg8)).getText());
                    if(rg9 != -1)
                        painnAndSwellingInGums = getCheckedValue((String) ((RadioButton) findViewById(rg9)).getText());
                    if(rg10 != -1)
                        irregularPlacedTeeth = getCheckedValue((String) ((RadioButton) findViewById(rg10)).getText());

                    /*finalDiagnosis = ((EditText) findViewById(R.id.finalDiagnosis)).getText().toString().trim();
                    provisionalDiagnosis = ((EditText) findViewById(R.id.provisionalDiagnosis)).getText().toString().trim();*/

                    ArrayList<String> formsList = new ArrayList<>();
                    formsList.add(formName);

                    final Map<String, Object> insertparams = new HashMap<>();
                    insertparams.put("dentFormPainInTeeth", painInTeeth);
                    insertparams.put("dentFormSevereSensitivityOfTeethOnDrinks", severeSensitivityOfTeethOnDrinks);
                    insertparams.put("dentFormPainorBleedingInGums", painorBleedingInGums);
                    insertparams.put("dentFormHaveLooseTeethWithGaps", looseTeethWithGaps);
                    insertparams.put("dentFormHaveUnpleasantTaste", unpleasantTaste);
                    insertparams.put("dentFormDoGrindYourTeethWhenSleep", grindYourTeethWhenSleep);
                    insertparams.put("dentFormHaveSwellingInGumsWithFluid", swellingInGumsWithFluid);
                    insertparams.put("dentFormSeverePainInTeethAtNight", severePainInTeethAtNight);
                    insertparams.put("dentFormHavePainnAndSwellingInGums", painnAndSwellingInGums);
                    insertparams.put("dentFormHaveIrregularPlacedTeeth", irregularPlacedTeeth);
                    insertparams.put("practiceFormNameDataIndex", "1");
                    insertparams.put("practiceFormNames", formsList);
                    insertparams.put("actionType", "save");
                    insertparams.put("healthRegistrationId", healthRegistrationId);
                    insertparams.put("patientName", patientName);
                    insertparams.put("regnLinkId", regLinkId);

                    /*insertparams.put("provisionalDiagnosis", provisionalDiagnosis);
                    insertparams.put("finalDiagnosis", finalDiagnosis);*/

                    if(caseid != null){
                        insertparams.put("caseRecordNo",caseid);
                    }

                    jsonparams = new Gson().toJson(insertparams);

                    if(rg1 == -1 && rg2 == -1 && rg3 == -1 && rg4 == -1 && rg5 == -1 && rg6 == -1 && rg7 == -1 && rg8 == -1 && rg9 == -1 && rg10 == -1)
                    {
                        new AlertDialog.Builder(context).setTitle("Alert")
                        .setMessage("Do you want to continue with empty record").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveCaseRecord(jsonparams, context);
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                    else{
                        new AlertDialog.Builder(context).setTitle("Alert")
                                .setMessage("Do you want to save record").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                saveCaseRecord(jsonparams, context);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                    }
                }
                catch (Exception e){
                }
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
                DentalAssessment.this, new ActionCallback() {
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
                                    caseRecordsActivity.putExtra("email", email);
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", gender);
                                    caseRecordsActivity.putExtra("dob", dob);
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
                                "Some error has occurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(DentalAssessment.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Saving Case Record");
        worker.execute(params);
    }

    private boolean getCheckedValue(String selection){
        if("Yes".equalsIgnoreCase(selection))
            return true;
        return false;
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
        getMenuInflater().inflate(R.menu.menu_dental_assessment, menu);
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
                    DentalAssessment.this, new ActionCallback() {
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
