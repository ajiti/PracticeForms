package com.virtucure.practiceforms;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.virtucare.practiceforms.dto.FormDataDTO;
import com.virtucare.practiceforms.dto.ReferredFormDTO;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientReferredRecordsActivity extends AppCompatActivity {

    private static final String TAG = ReferredPatientReferredRecordsActivity.class.getSimpleName();
    private Toolbar toolbar;
    private BroadcastReceiver logoutReceiver;
    private int lastExpandedPosition = -1;
    private ReferredFormDTO referredFormDTO;
    private ExpandableListView expListView;
    private Context context = this;
    private TextView crTxtView, hridTxtView, ghidTxtView, dateTimeTxtView, patientNameTxtView, findHridTxtView, findCaseIdTxtView, pdTxtView, fdTxtView;
    private Button saveBtn;
    private String brandorBranchName;
    private String projectType;
    private String hrid;
    private String jsonparams;
    private String findHrid;
    private String findCaseId;
    ExpandableReferredCaseRecordFormsListAdapter expListAdapter;
    private List<String> formList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        logoutReceiver = new BroadcastReceiver() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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

        initForms();
        projectType = getIntent().getExtras().getString("projectType");
        brandorBranchName = getIntent().getExtras().getString("brandorBranchName");
        hrid = getIntent().getExtras().getString("hrid");
        final String caseRecordNo = getIntent().getExtras().getString("caseRecordNo");
        final String sharedId = getIntent().getExtras().getString("sharedId");
        final String id = getIntent().getExtras().getString("id");
        String sharedType = getIntent().getExtras().getString("sharedType");
        setContentView(R.layout.referred_caserecordsform_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        saveBtn = (Button) findViewById(R.id.saveRefRecBtn);
        saveBtn.setVisibility(View.GONE);
        checkHridAndCaseId(hrid, caseRecordNo, sharedId, sharedType);

        crTxtView = (TextView) findViewById(R.id.crno);
        hridTxtView = (TextView) findViewById(R.id.hrid);
        ghidTxtView = (TextView) findViewById(R.id.ghid);
        dateTimeTxtView = (TextView) findViewById(R.id.dateTime);
        patientNameTxtView = (TextView) findViewById(R.id.patientName);
        findHridTxtView = (TextView) findViewById(R.id.findHrid);
        findCaseIdTxtView = (TextView) findViewById(R.id.findCaseId);
        pdTxtView = (TextView) findViewById(R.id.prvdiag);
        fdTxtView = (TextView) findViewById(R.id.fnldiag);

        expListView = (ExpandableListView) findViewById(R.id.formsExp);
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
                parent.setSmoothScrollbarEnabled(true);
                parent.setSelectedGroup(groupPosition);
                return true;
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> insertparams = new HashMap<>();
                insertparams.put("orgCaseId", caseRecordNo);
                insertparams.put("orgHrid", hrid);
                insertparams.put("findHrid", findHrid);
                insertparams.put("findCaseId", findCaseId);
                insertparams.put("id", id);
                insertparams.put("sharedId", sharedId);
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

    private void checkHridAndCaseId(String hrid, String caseRecordNo, final String sharedId, final String sharedType) {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/checkHridAndCaseid";

        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "2");

        Map<String, String> params = new HashMap<>();
        params.put("hrId", hrid);
        params.put("caseRecordNo", caseRecordNo);

        final String json = new Gson().toJson(params);
        nameValues.put("retrieveparams", json);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))) {
                            jsonObject = new JSONObject(jsonObject.getString("hridAndCaseIdCheckResult"));
                            findHrid = jsonObject.getString("findHrid").trim();
                            findCaseId = jsonObject.getString("findCaseId").trim();
                            findHridTxtView.setText(checkIsEmpty(findHrid));
                            findCaseIdTxtView.setText(checkIsEmpty(findCaseId));
                            getSharedRecords(sharedId, sharedType);
                        }
                    }
                    catch (Exception e){
                        Log.d("Exception occurred", e.getLocalizedMessage());
                    }
                } else {
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Checking Record");

        worker.execute(nameValues);
    }

    private void getSharedRecords(String sharedId, final String sharedType) {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getSharedRecordBySharedId";

        Map<String,String> nameValues = new HashMap<String,String>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "13");

        Map<String, String> params = new HashMap<>();
        params.put("sharedId", sharedId);

        String json = new Gson().toJson(params);
        nameValues.put("retrieveparams", json);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))) {
                            referredFormDTO = new Gson().fromJson(result.get("result"), ReferredFormDTO.class);
                            if(referredFormDTO != null) {
                                crTxtView.setText(referredFormDTO.getCommonData().getCaseRecordNo());
                                hridTxtView.setText(referredFormDTO.getCommonData().getHealthRegistrationId());
                                ghidTxtView.setText(referredFormDTO.getCommonData().getRegnLinkId());
                                dateTimeTxtView.setText(Util.convertUnixTimeToDate(referredFormDTO.getCommonData().getCreatedDate()));
                                patientNameTxtView.setText(referredFormDTO.getCommonData().getPatientName());
                                pdTxtView.setText(checkIsEmpty(referredFormDTO.getCommonData().getProvisionalDiagnosis()));
                                fdTxtView.setText(checkIsEmpty(referredFormDTO.getCommonData().getFinalDiagnosis()));
                                if("cc".equalsIgnoreCase(sharedType)) {
                                    saveBtn.setVisibility(View.VISIBLE);
                                }

                                String practiceFormName = "";
                                List<FormDataDTO> originalList = referredFormDTO.getCaseRecordsList();
                                List<FormDataDTO> dumpList = new ArrayList<>();
                                for(int i=0; originalList != null && i < originalList.size(); i++) {
                                    practiceFormName = originalList.get(i).getPracticeFormName();
                                    if(formList != null && formList.contains(practiceFormName)){
                                        dumpList.add(originalList.get(i));
                                    }
                                }

                                expListAdapter = new ExpandableReferredCaseRecordFormsListAdapter(context, dumpList);
                                expListView.setAdapter(expListAdapter);
                            }
                        }
                    }
                    catch (Exception e){
                       Log.d("Exception occurred", e.getLocalizedMessage());
                    }
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("No Record Found!")
                            .show();
                }
            }
        }, "Retrieving Shared Records");

        worker.execute(nameValues);
    }

    private void saveCaseRecord(String jsonparams, final Context ctx)
    {
        final String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/addReferredPatient";
        Map<String, String> params = new HashMap<>();
        params.put("serverUrl", serverUrl);
        params.put("operationType", "10");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                ReferredPatientReferredRecordsActivity.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if (resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {

                            final String healthRegistrationId = resultJson.getString("hrid");
                            final String caseid = resultJson.getString("caseRecordNo");

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);

                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("Case Record Saved Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getApplicationContext(),
                                            CaseRecordFormsActivity.class);
                                    caseRecordsActivity.putExtra("regid", healthRegistrationId);
                                    caseRecordsActivity.putExtra("name", referredFormDTO.getCommonData().getPatientName());
                                    caseRecordsActivity.putExtra("phone", referredFormDTO.getCommonData().getMobileNo());
                                    caseRecordsActivity.putExtra("dob", referredFormDTO.getCommonData().getDob());
                                    caseRecordsActivity.putExtra("email", referredFormDTO.getCommonData().getEmailId());
                                    caseRecordsActivity.putExtra("proofType", referredFormDTO.getCommonData().getProofType());
                                    caseRecordsActivity.putExtra("proofNumber", referredFormDTO.getCommonData().getProofNumber());
                                    caseRecordsActivity.putExtra("gender", referredFormDTO.getCommonData().getGender());
                                    caseRecordsActivity.putExtra("reqLinkId", referredFormDTO.getCommonData().getRegnLinkId());
                                    caseRecordsActivity.putExtra("caserecordno", caseid);
                                    caseRecordsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(caseRecordsActivity);
                                    finish();
                                }
                            }).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Exception", e);
                        Toast.makeText(getApplicationContext(),
                                "Some error has occurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    new AlertDialog.Builder(ReferredPatientReferredRecordsActivity.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Generating Record");
        worker.execute(params);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, ReferredPatientCaseRecordsActivity.class);
        intent.putExtra("brandorBranchName", brandorBranchName);
        intent.putExtra("projectType", projectType);
        intent.putExtra("hrid", hrid);
        startActivity(intent);
    }

    private String checkIsEmpty(String string) {
        return (string != null && !string.isEmpty()) ? string : "-";
    }

    private void initForms(){
        formList = new ArrayList<>();
        formList.add("dentalassessment");
        formList.add("generalhealthscreening");
        formList.add("historytaking");
        formList.add("generalphysicalexamination");
        formList.add("dischargesheet");
        formList.add("investigationmicrobiology");
        formList.add("investigationbiochemistry");
        formList.add("investigationpathology");
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_referred_records, menu);
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
                    ReferredPatientReferredRecordsActivity.this, new ActionCallback() {
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
