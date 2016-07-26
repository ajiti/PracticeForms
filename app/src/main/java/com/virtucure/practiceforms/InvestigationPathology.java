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
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 7/5/2016.
 */

public class InvestigationPathology extends AppCompatActivity {
    private static final String TAG = InvestigationPathology.class.getSimpleName();
    private Toolbar toolbar;
    private String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/caserecordinsert";
    private BroadcastReceiver logoutReceiver = null;
    ExpandableListView expListView;
    List<String> listDataHeader;
    private int lastExpandedPosition = -1;
    private String healthRegistrationId;
    private String regLinkId;
    private String patientName;
    private String caseid;
    private String phone;
    private String proofType;
    private String proofNumber;
    private String email;
    private String gender;
    private String dob;
    private String formName;
    private IPCExpandable listAdapter;
    private String jsonparams;
    private Context context=this;
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
        gender = getIntent().getExtras().getString("gender");
        dob = getIntent().getExtras().getString("dob");
        caseid = getIntent().getExtras().getString("caseid");
        formName = getIntent().getExtras().getString("formName");

        setContentView(R.layout.layout_investigation_pathology);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        expListView = (ExpandableListView) findViewById(R.id.in_path);
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
        listDataHeader.add(getResources().getString(R.string.subhead_a_investigation_pathology));
        listDataHeader.add(getResources().getString(R.string.subhead_b_investigation_pathology));
        listAdapter = new IPCExpandable(this, listDataHeader,new IPCExpandable.FormListener() {
            @Override
            public void onFormSaveListener(View view) {
                formView = view;
            }
        });
        expListView.setAdapter(listAdapter);

        Button saveBtn = (Button) findViewById(R.id.inpsave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (formView != null) {

                        CheckBox ch1 = (CheckBox) formView.findViewById(R.id.hch1);
                        CheckBox ch2 = (CheckBox) formView.findViewById(R.id.hch2);
                        CheckBox ch3 = (CheckBox) formView.findViewById(R.id.hch3);
                        CheckBox ch4 = (CheckBox) formView.findViewById(R.id.hch4);
                        CheckBox ch5 = (CheckBox) formView.findViewById(R.id.hch5);
                        CheckBox ch6 = (CheckBox) formView.findViewById(R.id.hch6);
                        CheckBox ch7 = (CheckBox) formView.findViewById(R.id.hch7);
                        CheckBox ch8 = (CheckBox) formView.findViewById(R.id.hch8);
                        CheckBox ch9 = (CheckBox) formView.findViewById(R.id.hch9);
                        CheckBox ch10 = (CheckBox)formView.findViewById(R.id.hch10);
                        CheckBox ch11 = (CheckBox)formView.findViewById(R.id.hch11);
                        CheckBox ch12 = (CheckBox)formView.findViewById(R.id.hch12);
                        CheckBox ch13 = (CheckBox)formView.findViewById(R.id.hch13);
                        CheckBox ch14 = (CheckBox)formView.findViewById(R.id.hch14);
                        CheckBox ch15 = (CheckBox)formView.findViewById(R.id.hch15);
                        CheckBox ch16 = (CheckBox)formView.findViewById(R.id.hch16);
                        CheckBox ch17 = (CheckBox)formView.findViewById(R.id.hch17);
                        CheckBox ch18 = (CheckBox)formView.findViewById(R.id.hch18);
                        CheckBox ch19 = (CheckBox)formView.findViewById(R.id.hch19);
                        CheckBox ch20 = (CheckBox)formView.findViewById(R.id.hch20);
                        CheckBox ch21 = (CheckBox)formView.findViewById(R.id.hch21);
                        CheckBox ch22 = (CheckBox)formView.findViewById(R.id.hch22);
                        CheckBox ch23 = (CheckBox)formView.findViewById(R.id.hch24);
                        CheckBox ch24 = (CheckBox)formView.findViewById(R.id.hch25);
                        CheckBox ch25 = (CheckBox)formView.findViewById(R.id.hch26);
                        CheckBox ch26 = (CheckBox)formView.findViewById(R.id.hch27);
                        CheckBox ch27 = (CheckBox)formView.findViewById(R.id.hch28);
                        CheckBox ch28 = (CheckBox)formView.findViewById(R.id.hch29);
                        CheckBox ch29 = (CheckBox)formView.findViewById(R.id.cch1);
                        CheckBox ch30 = (CheckBox)formView.findViewById(R.id.cch2);
                        CheckBox ch31 = (CheckBox)formView.findViewById(R.id.cch3);
                        CheckBox ch32 = (CheckBox)formView.findViewById(R.id.cch4);
                        CheckBox ch33 = (CheckBox)formView.findViewById(R.id.cch5);
                        CheckBox ch34 = (CheckBox)formView.findViewById(R.id.cch6);
                        CheckBox ch35 = (CheckBox)formView.findViewById(R.id.cch7);
                        CheckBox ch36 = (CheckBox)formView.findViewById(R.id.cch8);
                        CheckBox ch37 = (CheckBox)formView.findViewById(R.id.cch9);
                        CheckBox ch38 = (CheckBox)formView.findViewById(R.id.cch10);
                        CheckBox ch39 = (CheckBox)formView.findViewById(R.id.cch11);
                        CheckBox ch40 = (CheckBox)formView.findViewById(R.id.cch13);
                        CheckBox ch41 = (CheckBox)formView.findViewById(R.id.cch14);
                        CheckBox ch42 = (CheckBox)formView.findViewById(R.id.cch18);
                        CheckBox ch43 = (CheckBox)formView.findViewById(R.id.cch19);
                        CheckBox ch44 = (CheckBox)formView.findViewById(R.id.cch20);
                        CheckBox ch45 = (CheckBox)formView.findViewById(R.id.cch21);
                        CheckBox ch46 = (CheckBox)formView.findViewById(R.id.cch22);

                        ArrayList<String> formsList = new ArrayList<>();
                        formsList.add(formName);

                        final Map<String, Object> insertparams = new HashMap<>();
                        insertparams.put("invstPathFormHaemogram", ch1.isChecked());
                        insertparams.put("invstPathFormCBC", ch2.isChecked());
                        insertparams.put("invstPathFormCBP", ch3.isChecked());
                        insertparams.put("invstPathFormHaemoglobin", ch4.isChecked());
                        insertparams.put("invstPathFormPCV", ch5.isChecked());
                        insertparams.put("invstPathFormTotalWBCCount", ch6.isChecked());
                        insertparams.put("invstPathFormDifferentialCount", ch7.isChecked());
                        insertparams.put("invstPathFormESR", ch8.isChecked());
                        insertparams.put("invstPathFormAbnormalCells", ch9.isChecked());
                        insertparams.put("invstPathFormRBCCount", ch10.isChecked());
                        insertparams.put("invstPathFormRDW", ch11.isChecked());
                        insertparams.put("invstPathFormPlateletCount", ch12.isChecked());
                        insertparams.put("invstPathFormPDW", ch13.isChecked());
                        insertparams.put("invstPathFormPMW", ch14.isChecked());
                        insertparams.put("invstPathFormBleedingTime", ch15.isChecked());
                        insertparams.put("invstPathFormClottingTime", ch16.isChecked());
                        insertparams.put("invstPathFormReticulocyteCount", ch17.isChecked());
                        insertparams.put("invstPathFormPeripheralSmear", ch18.isChecked());
                        insertparams.put("invstPathFormSickleCellTest", ch19.isChecked());
                        insertparams.put("invstPathFormOsmoticFragilityTest", ch20.isChecked());
                        insertparams.put("invstPathFormLECellTest", ch21.isChecked());
                        insertparams.put("invstPathFormAEC", ch22.isChecked());
                        insertparams.put("invstPathFormAbsoluteMCV", ch23.isChecked());
                        insertparams.put("invstPathFormAbsoluteMCH", ch24.isChecked());
                        insertparams.put("invstPathFormAbsoluteMCHC", ch25.isChecked());
                        insertparams.put("invstPathFormSmearForMP", ch26.isChecked());
                        insertparams.put("invstPathFormRapidTestMP", ch27.isChecked());
                        insertparams.put("invstPathFormOtherInvestigations", ch28.isChecked());
                        insertparams.put("invstPathFormRoutineUrineAnalysis", ch29.isChecked());
                        insertparams.put("invstPathFormCompleteUrineExam", ch30.isChecked());
                        insertparams.put("invstPathFormPH", ch31.isChecked());
                        insertparams.put("invstPathFormSpecificGravity", ch32.isChecked());
                        insertparams.put("invstPathFormProteins", ch33.isChecked());
                        insertparams.put("invstPathFormSugar", ch34.isChecked());
                        insertparams.put("invstPathFormKetoneBodies", ch35.isChecked());
                        insertparams.put("invstPathFormBileSalts", ch36.isChecked());
                        insertparams.put("invstPathFormBilePigments", ch37.isChecked());
                        insertparams.put("invstPathFormUrobilinogen", ch38.isChecked());
                        insertparams.put("invstPathFormMicroscopy", ch39.isChecked());
                        insertparams.put("invstPathFormbenceHonesProtien", ch40.isChecked());
                        insertparams.put("invstPathFormHmosiderinPigment", ch41.isChecked());
                        insertparams.put("invstPathFormCSF", ch42.isChecked());
                        insertparams.put("invstPathFormAsciticFluid", ch43.isChecked());
                        insertparams.put("invstPathFormPleuralFluid", ch44.isChecked());
                        insertparams.put("invstPathFormSynovialFluid", ch45.isChecked());
                        insertparams.put("invstPathFormPeriCardialFluid", ch46.isChecked());
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
                        if (ch1.isChecked() && ch2.isChecked() && ch3.isChecked() && ch4.isChecked() && ch5.isChecked() && ch6.isChecked() && ch7.isChecked()
                            && ch8.isChecked() && ch9.isChecked() && ch10.isChecked() && ch11.isChecked() && ch12.isChecked() && ch13.isChecked() && ch14.isChecked()
                            && ch15.isChecked() && ch16.isChecked() && ch17.isChecked() && ch18.isChecked() && ch19.isChecked() && ch20.isChecked() && ch21.isChecked()
                            && ch22.isChecked() && ch23.isChecked() && ch24.isChecked() && ch25.isChecked() && ch26.isChecked() && ch27.isChecked() && ch28.isChecked()
                            && ch29.isChecked() && ch30.isChecked() && ch31.isChecked() && ch32.isChecked() && ch33.isChecked() && ch34.isChecked() && ch35.isChecked()
                            && ch36.isChecked() && ch37.isChecked() && ch38.isChecked() && ch39.isChecked() && ch40.isChecked() && ch41.isChecked() && ch42.isChecked()
                            && ch43.isChecked() && ch44.isChecked() && ch45.isChecked() && ch46.isChecked()) {

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
                        } else {
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
                    }
                }
                catch (Exception e) {
                   Log.e(TAG, e.getLocalizedMessage(), e);
                }
            }
        });
    }
    private void saveCaseRecord(String jsonparams, final Context ctx)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("serverUrl",serverUrl);
        params.put("operationType", "5");
        params.put("insertparams", jsonparams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                InvestigationPathology.this, new ActionCallback() {
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
                    }
                    catch (Exception e ){
                        Toast.makeText(getApplicationContext(),
                                "Some error has occurred Please try after some time", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(InvestigationPathology.this)
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
        getMenuInflater().inflate(R.menu.menu_pathology_form, menu);
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
                    InvestigationPathology.this, new ActionCallback() {
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
