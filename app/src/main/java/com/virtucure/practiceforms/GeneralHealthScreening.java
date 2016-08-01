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

public class GeneralHealthScreening extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean difficultiesInSeeingObjects;
    private boolean hearingTrouble ;
    private boolean sufferWithFrequentEpisodesOfCold ;
    private boolean skinAllergies;
    private boolean learningDifficulties;
    private boolean historyOfFits;
    private boolean historyOfBurningChest;
    private boolean highBloodPressure;
    private boolean diabetes;
    private boolean dentalProblems;
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

    public String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/caserecordinsert";
    private BroadcastReceiver logoutReceiver;

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
        setContentView(R.layout.activity_general_health_screening);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        Button saveBtn = (Button) findViewById(R.id.gensave);
        if(saveBtn != null){
            saveBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try{

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
                        formName = getIntent().getExtras().getString("formName");

                        int rg1 = ((RadioGroup) findViewById(R.id.ghsradiogroup1)).getCheckedRadioButtonId();
                        int rg2 = ((RadioGroup) findViewById(R.id.ghsradiogroup2)).getCheckedRadioButtonId();
                        int rg3 = ((RadioGroup) findViewById(R.id.ghsradiogroup3)).getCheckedRadioButtonId();
                        int rg4 = ((RadioGroup) findViewById(R.id.ghsradiogroup4)).getCheckedRadioButtonId();
                        int rg5 = ((RadioGroup) findViewById(R.id.ghsradiogroup5)).getCheckedRadioButtonId();
                        int rg6 = ((RadioGroup) findViewById(R.id.ghsradiogroup6)).getCheckedRadioButtonId();
                        int rg7 = ((RadioGroup) findViewById(R.id.ghsradiogroup7)).getCheckedRadioButtonId();
                        int rg8 = ((RadioGroup) findViewById(R.id.ghsradiogroup8)).getCheckedRadioButtonId();
                        int rg9 = ((RadioGroup) findViewById(R.id.ghsradiogroup9)).getCheckedRadioButtonId();
                        int rg10 = ((RadioGroup) findViewById(R.id.ghsradiogroup10)).getCheckedRadioButtonId();

                        if(rg1 != -1)
                            difficultiesInSeeingObjects = getCheckedValue((String) ((RadioButton) findViewById(rg1)).getText());
                        if(rg2 != -1)
                            hearingTrouble = getCheckedValue((String) ((RadioButton) findViewById(rg2)).getText());
                        if(rg3 != -1)
                            sufferWithFrequentEpisodesOfCold = getCheckedValue((String) ((RadioButton) findViewById(rg3)).getText());
                        if(rg4 != -1)
                            skinAllergies = getCheckedValue((String) ((RadioButton) findViewById(rg4)).getText());
                        if(rg5 != -1)
                            learningDifficulties = getCheckedValue((String) ((RadioButton) findViewById(rg5)).getText());
                        if(rg6 != -1)
                            historyOfFits = getCheckedValue((String) ((RadioButton) findViewById(rg6)).getText());
                        if(rg7 != -1)
                            historyOfBurningChest = getCheckedValue((String) ((RadioButton) findViewById(rg7)).getText());
                        if(rg8 != -1)
                            highBloodPressure = getCheckedValue((String) ((RadioButton) findViewById(rg8)).getText());
                        if(rg9 != -1)
                            diabetes = getCheckedValue((String) ((RadioButton) findViewById(rg9)).getText());
                        if(rg10 != -1)
                            dentalProblems = getCheckedValue((String) ((RadioButton) findViewById(rg10)).getText());

                    /*finalDiagnosis = ((EditText) findViewById(R.id.finalDiagnosis)).getText().toString().trim();
                    provisionalDiagnosis = ((EditText) findViewById(R.id.provisionalDiagnosis)).getText().toString().trim();*/

                        ArrayList<String> formsList = new ArrayList<String>();
                        formsList.add(formName);

                        final Map<String, Object> insertparams = new HashMap<String, Object>();
                        insertparams.put("hlthScrnFormHaveDifficultiesInSeeingObjects", difficultiesInSeeingObjects);
                        insertparams.put("hlthScrnFormHaveHearingTrouble", hearingTrouble);
                        insertparams.put("hlthScrnFormSufferWithFrequentEpisodesOfCold", sufferWithFrequentEpisodesOfCold);
                        insertparams.put("hlthScrnFormHaveSkinAllergies", skinAllergies);
                        insertparams.put("hlthScrnFormHaveLearningDifficulties", learningDifficulties);
                        insertparams.put("hlthScrnFormHaveHistoryOfFits", historyOfFits);
                        insertparams.put("hlthScrnFormHaveHistoryOfBurningChest", historyOfBurningChest);
                        insertparams.put("hlthScrnFormHaveHighBloodPressure", highBloodPressure);
                        insertparams.put("hlthScrnFormHaveDiabetes", diabetes);
                        insertparams.put("hlthScrnFormHaveDentalProblems", dentalProblems);
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
                                    .setMessage(R.string.empty_save_record_msg).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    saveCaseRecord(jsonparams, context);
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                        }
                        else {
                            new AlertDialog.Builder(context).setTitle("Alert")
                                    .setMessage(R.string.save_record_msg).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
/*                    new AlertDialog.Builder(GeneralHealthScreening.this)
                            .setTitle("Alert")
                            .setMessage("Please fill all the fields!").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent generalhs = new Intent(
                                    getApplicationContext(),
                                    GeneralHealthScreening.class);
                            generalhs.putExtra("formName",formName);
                            generalhs.putExtra("caseid",caseid);
                            generalhs.putExtra("regid",healthRegistrationId);
                            generalhs.putExtra("name" ,patientName);
                            generalhs.putExtra("regLinkId", regLinkId);
                            generalhs.putExtra("proof", proof);
                            generalhs.putExtra("dob", dob);
                            generalhs.putExtra("phone", phone);
                            startActivity(generalhs);
                        }
                    }).show();*/
                    }
                }
            });
        }
    }

    private boolean getCheckedValue(String selection){
        return ("Yes".equalsIgnoreCase(selection)) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general_health_screening, menu);
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
                    GeneralHealthScreening.this, new ActionCallback() {
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

    private void saveCaseRecord(String jsonparams, final Context ctx)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serverUrl", serverUrl);
        params.put("operationType", "5");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                GeneralHealthScreening.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty() ) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if(resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {

                            if("".equalsIgnoreCase(caseid))
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
                                    caseRecordsActivity.putExtra("email", email);
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", gender);
                                    caseRecordsActivity.putExtra("dob", dob);
                                    caseRecordsActivity.putExtra("regLinkId", regLinkId);
                                    caseRecordsActivity.putExtra("caserecordno", caseid);
                                    caseRecordsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(caseRecordsActivity);
                                    finish();
                                }
                            }).show();
                        }
                    }
                    catch (Exception e ){
                        Toast.makeText(getApplicationContext(),
                                "Some error has occurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(GeneralHealthScreening.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Saving Case Record");
        worker.execute(params);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

/*    @Override
    protected void onStop(){
        super.onStop();
    }*/

}
