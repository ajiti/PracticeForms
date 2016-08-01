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
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 6/29/2016.
 */
public class InvestigationMicrobiology extends AppCompatActivity {

    private BroadcastReceiver logoutReceiver = null;
    private Toolbar toolbar;
    private static final String TAG = InvestigationMicrobiology.class.getSimpleName();
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
    final Context context = this;
    private String jsonparams;
    private boolean isEmptyForm;
    private CheckBox formFields[];
    public String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/caserecordinsert";

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
                        getApplicationContext(),
                        loginactivity.class);
                startActivity(login);
                finish();
            }
        };
        registerReceiver(logoutReceiver, intentFilter);
        setContentView(R.layout.activity_investigation_microbiology);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        Button saveBtn = (Button) findViewById(R.id.imbsave);
        if(saveBtn != null){
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
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

                        CheckBox ch1 = (CheckBox) findViewById(R.id.mrch1);
                        CheckBox ch2 = (CheckBox) findViewById(R.id.mrch2);
                        CheckBox ch3 = (CheckBox) findViewById(R.id.mrch3);
                        CheckBox ch4 = (CheckBox) findViewById(R.id.mrch4);
                        CheckBox ch5 = (CheckBox) findViewById(R.id.mrch5);
                        CheckBox ch6 = (CheckBox) findViewById(R.id.mrch6);
                        CheckBox ch7 = (CheckBox) findViewById(R.id.mrch7);
                        CheckBox ch8 = (CheckBox) findViewById(R.id.mrch8);
                        CheckBox ch9 = (CheckBox) findViewById(R.id.mrch9);
                        CheckBox ch10 = (CheckBox) findViewById(R.id.mrch10);
                        CheckBox ch11 = (CheckBox) findViewById(R.id.mrch11);
                        CheckBox ch12 = (CheckBox) findViewById(R.id.mrch12);
                        CheckBox ch13 = (CheckBox) findViewById(R.id.mrch13);
                        CheckBox ch14 = (CheckBox) findViewById(R.id.mrch14);
                        CheckBox ch15 = (CheckBox) findViewById(R.id.mrch15);
                        CheckBox ch16 = (CheckBox) findViewById(R.id.mrch16);
                        CheckBox ch17 = (CheckBox) findViewById(R.id.mrch17);
                        CheckBox ch18 = (CheckBox) findViewById(R.id.mrch18);
                        CheckBox ch19 = (CheckBox) findViewById(R.id.mrch19);
                        CheckBox ch20 = (CheckBox) findViewById(R.id.mrch20);
                        CheckBox ch21 = (CheckBox) findViewById(R.id.mrch21);
                        CheckBox ch22 = (CheckBox) findViewById(R.id.mrch22);
                        CheckBox ch23 = (CheckBox) findViewById(R.id.mrch23);
                        CheckBox ch24 = (CheckBox) findViewById(R.id.mrch24);
                        CheckBox ch25 = (CheckBox) findViewById(R.id.mrch25);
                        CheckBox ch26 = (CheckBox) findViewById(R.id.mrch26);
                        CheckBox ch27 = (CheckBox) findViewById(R.id.mrch27);
                        CheckBox ch28 = (CheckBox) findViewById(R.id.mrch28);
                        CheckBox ch29 = (CheckBox) findViewById(R.id.mrch29);
                        CheckBox ch30 = (CheckBox) findViewById(R.id.mrch30);

                        isEmptyForm = Boolean.TRUE;

                        formFields = new CheckBox[]{
                                ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10, ch11, ch12, ch13, ch14, ch15, ch16, ch17, ch18,
                                ch19, ch20, ch21, ch22, ch23, ch24, ch25, ch26, ch27, ch28, ch29, ch30
                        };

                        for(CheckBox checkBox : formFields) {
                            if(checkBox.isChecked()) {
                                isEmptyForm = Boolean.FALSE;
                                break;
                            }
                        }

                        if (isEmptyForm) {
                            Toast.makeText(context, R.string.fill_form_msg, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ArrayList<String> formsList = new ArrayList<>();
                        formsList.add(formName);

                        final Map<String, Object> insertparams = new HashMap<>();

                        insertparams.put("invstMicroFormGramsStaining", ch1.isChecked());
                        insertparams.put("invstMicroFormZNStaining", ch2.isChecked());
                        insertparams.put("invstMicroFormAlbertsStaining", ch3.isChecked());
                        insertparams.put("invstMicroFormSalineSmear", ch4.isChecked());
                        insertparams.put("invstMicroFormKOHSmear", ch5.isChecked());
                        insertparams.put("invstMicroFormLactophenoSmearl", ch6.isChecked());
                        insertparams.put("invstMicroFormIndiaInkStaining", ch7.isChecked());
                        insertparams.put("invstMicroFormStoolForParasites", ch8.isChecked());
                        insertparams.put("invstMicroFormSmearOfGonococci", ch9.isChecked());
                        insertparams.put("invstMicroFormSmearOfFungi", ch10.isChecked());
                        insertparams.put("invstMicroFormBloodSmearParaSites",ch11.isChecked());
                        insertparams.put("invstMicroFormSmearOfHansens", ch12.isChecked());
                        insertparams.put("invstMicroFormWidalTest", ch13.isChecked());
                        insertparams.put("invstMicroFormRF", ch14.isChecked());
                        insertparams.put("invstMicroFormASO", ch15.isChecked());
                        insertparams.put("invstMicroFormCRP", ch16.isChecked());
                        insertparams.put("invstMicroFormVDRL", ch17.isChecked());
                        insertparams.put("invstMicroFormHBsag", ch18.isChecked());
                        insertparams.put("invstMicroFormHCV", ch19.isChecked());
                        insertparams.put("invstMicroFormHIV", ch20.isChecked());
                        insertparams.put("invstMicroFormANA", ch21.isChecked());
                        insertparams.put("invstMicroFormAntiTBIgA",ch22.isChecked());
                        insertparams.put("invstMicroFormAntiTBIgM", ch23.isChecked());
                        insertparams.put("invstMicroFormAntiTBIgG", ch24.isChecked());
                        insertparams.put("invstMicroFormAntiLeptoSpiraIgM", ch25.isChecked());
                        insertparams.put("invstMicroFormFungalCulture", ch26.isChecked());
                        insertparams.put("invstMicroFormBacterialCulture",ch27.isChecked());
                        insertparams.put("invstMicroFormBloodCulture", ch28.isChecked());
                        insertparams.put("invstMicroFormEntricFever", ch29.isChecked());
                        insertparams.put("invstMicroFormTuberculosisCulture", ch30.isChecked());
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
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
                }
            });
        }
    }

    private void saveCaseRecord(String jsonparams, final Context ctx)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serverUrl",serverUrl);
        params.put("operationType", "5");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                InvestigationMicrobiology.this, new ActionCallback() {
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
                        Log.e(TAG, e.getLocalizedMessage(), e);
                        Toast.makeText(getApplicationContext(),
                                "Some error has occurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(InvestigationMicrobiology.this)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_microbiology_form, menu);
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
                    InvestigationMicrobiology.this, new ActionCallback() {
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
