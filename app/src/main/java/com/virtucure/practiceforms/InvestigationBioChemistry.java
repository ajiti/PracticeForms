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
 * Created by pc on 6/27/2016.
 */
public class InvestigationBioChemistry extends AppCompatActivity {

    private String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/caserecordinsert";
    private static final String TAG = InvestigationBioChemistry.class.getSimpleName();
    private Toolbar toolbar;
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
    IBCExpandable listAdapter;
    private String jsonparams;
    final Context context = this;
    private View formView;
    private boolean isEmptyForm;
    private CheckBox formFields[];

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

        setContentView(R.layout.layout_investigation_biochemistry);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        expListView = (ExpandableListView) findViewById(R.id.in_bioch);
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
        listDataHeader.add(getResources().getString(R.string.subhead_a_investigation_biochemistry));
        listDataHeader.add(getResources().getString(R.string.subhead_b_investigation_biochemistry));
        listDataHeader.add(getResources().getString(R.string.subhead_c_investigation_biochemistry));
        listDataHeader.add(getResources().getString(R.string.subhead_d_investigation_biochemistry));
        listDataHeader.add(getResources().getString(R.string.subhead_e_investigation_biochemistry));
        listAdapter = new IBCExpandable(this, listDataHeader, new IBCExpandable.FormListener() {
            @Override
            public void onFormSaveListener(View view) {
                formView = view;
            }
        });
        expListView.setAdapter(listAdapter);

        Button  saveBtn = (Button) findViewById(R.id.inbsave);
        if(saveBtn != null){
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (formView != null) {
                            CheckBox ch1 = ((CheckBox) formView.findViewById(R.id.rch1));
                            CheckBox ch2 = ((CheckBox) formView.findViewById(R.id.rch2));
                            CheckBox ch3 = ((CheckBox) formView.findViewById(R.id.rch3));
                            CheckBox ch4 = ((CheckBox) formView.findViewById(R.id.rch4));
                            CheckBox ch5 = ((CheckBox) formView.findViewById(R.id.rch5));
                            CheckBox ch6 = ((CheckBox) formView.findViewById(R.id.rch6));
                            CheckBox ch7 = ((CheckBox) formView.findViewById(R.id.rch7));
                            CheckBox ch8 = ((CheckBox) formView.findViewById(R.id.rch8));
                            CheckBox ch9 = ((CheckBox) formView.findViewById(R.id.rch9));
                            CheckBox ch10 = ((CheckBox) formView.findViewById(R.id.rch10));
                            CheckBox ch11 = ((CheckBox) formView.findViewById(R.id.rch11));
                            CheckBox ch12 = ((CheckBox) formView.findViewById(R.id.rch12));
                            CheckBox ch13 = ((CheckBox) formView.findViewById(R.id.rch13));
                            CheckBox ch14 = ((CheckBox) formView.findViewById(R.id.rch14));
                            CheckBox ch15 = ((CheckBox) formView.findViewById(R.id.rch15));
                            CheckBox ch16 = ((CheckBox) formView.findViewById(R.id.rch16));
                            CheckBox ch17 = ((CheckBox) formView.findViewById(R.id.rch17));
                            CheckBox ch18 = ((CheckBox) formView.findViewById(R.id.rch18));
                            CheckBox ch19 = ((CheckBox) formView.findViewById(R.id.rch19));
                            CheckBox ch20 = ((CheckBox) formView.findViewById(R.id.rch20));
                            CheckBox ch21 = ((CheckBox) formView.findViewById(R.id.rch21));
                            CheckBox ch22 = ((CheckBox) formView.findViewById(R.id.rch22));
                            CheckBox ch23 = ((CheckBox) formView.findViewById(R.id.rch23));
                            CheckBox ch24 = ((CheckBox) formView.findViewById(R.id.rch24));
                            CheckBox ch25 = ((CheckBox) formView.findViewById(R.id.rch25));
                            CheckBox ch26 = ((CheckBox) formView.findViewById(R.id.rch26));
                            CheckBox ch27 = ((CheckBox) formView.findViewById(R.id.rch27));
                            CheckBox ch28 = ((CheckBox) formView.findViewById(R.id.rch28));
                            CheckBox ch29 = ((CheckBox) formView.findViewById(R.id.rch29));
                            CheckBox ch30 = ((CheckBox) formView.findViewById(R.id.rch30));
                            CheckBox ch31 = ((CheckBox) formView.findViewById(R.id.rch31));
                            CheckBox ch32 = ((CheckBox) formView.findViewById(R.id.pch1));
                            CheckBox ch33 = ((CheckBox) formView.findViewById(R.id.pch2));
                            CheckBox ch34 = ((CheckBox) formView.findViewById(R.id.pch3));
                            CheckBox ch35 = ((CheckBox) formView.findViewById(R.id.pch4));
                            CheckBox ch36 = ((CheckBox) formView.findViewById(R.id.pch5));
                            CheckBox ch37 = ((CheckBox) formView.findViewById(R.id.pch6));
                            CheckBox ch38 = ((CheckBox) formView.findViewById(R.id.pch7));
                            CheckBox ch39 = ((CheckBox) formView.findViewById(R.id.pch8));
                            CheckBox ch40 = ((CheckBox) formView.findViewById(R.id.pch9));
                            CheckBox ch41 = ((CheckBox) formView.findViewById(R.id.pch10));
                            CheckBox ch42 = ((CheckBox) formView.findViewById(R.id.pch11));
                            CheckBox ch43 = ((CheckBox) formView.findViewById(R.id.imch1));
                            CheckBox ch44 = ((CheckBox) formView.findViewById(R.id.imch2));
                            CheckBox ch45 = ((CheckBox) formView.findViewById(R.id.imch3));
                            CheckBox ch46 = ((CheckBox) formView.findViewById(R.id.imch4));
                            CheckBox ch47 = ((CheckBox) formView.findViewById(R.id.imch5));
                            CheckBox ch48 = ((CheckBox) formView.findViewById(R.id.imch6));
                            CheckBox ch49 = ((CheckBox) formView.findViewById(R.id.imch7));
                            CheckBox ch50 = ((CheckBox) formView.findViewById(R.id.imch8));
                            CheckBox ch51 = ((CheckBox) formView.findViewById(R.id.imch9));
                            CheckBox ch52 = ((CheckBox) formView.findViewById(R.id.imch10));
                            CheckBox ch53 = ((CheckBox) formView.findViewById(R.id.imch11));
                            CheckBox ch54 = ((CheckBox) formView.findViewById(R.id.imch12));
                            CheckBox ch55 = ((CheckBox) formView.findViewById(R.id.fch1));
                            CheckBox ch56 = ((CheckBox) formView.findViewById(R.id.fch2));
                            CheckBox ch57 = ((CheckBox) formView.findViewById(R.id.fch3));
                            CheckBox ch58 = ((CheckBox) formView.findViewById(R.id.fch5));
                            CheckBox ch59 = ((CheckBox) formView.findViewById(R.id.fch6));
                            CheckBox ch60 = ((CheckBox) formView.findViewById(R.id.fch7));
                            CheckBox ch61 = ((CheckBox) formView.findViewById(R.id.fch8));
                            CheckBox ch62 = ((CheckBox) formView.findViewById(R.id.uch1));
                            CheckBox ch63 = ((CheckBox) formView.findViewById(R.id.uch2));
                            CheckBox ch64 = ((CheckBox) formView.findViewById(R.id.uch3));
                            CheckBox ch65 = ((CheckBox) formView.findViewById(R.id.uch4));
                            CheckBox ch66 = ((CheckBox) formView.findViewById(R.id.uch5));
                            CheckBox ch67 = ((CheckBox) formView.findViewById(R.id.uch6));
                            CheckBox ch68 = ((CheckBox) formView.findViewById(R.id.uch7));

                            isEmptyForm = Boolean.TRUE;

                            formFields = new CheckBox[]{
                                    ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8, ch9, ch10, ch11, ch12, ch13, ch14, ch15, ch16, ch17, ch18,
                                    ch19, ch20, ch21, ch22, ch23, ch24, ch25, ch26, ch27, ch28, ch29, ch30, ch31, ch32, ch33, ch34, ch35,
                                    ch36, ch37, ch38, ch39, ch40, ch41, ch42, ch43, ch44, ch45, ch46, ch47, ch48, ch49, ch50, ch51, ch52,
                                    ch53, ch54, ch55, ch56, ch57, ch58, ch59, ch60, ch61, ch62, ch63, ch64, ch65, ch66, ch67, ch68
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
                            insertparams.put("invstBioFormRandomSerumGlucose", ch1.isChecked());
                            insertparams.put("invstBioFormFastingSerumGlucose", ch2.isChecked());
                            insertparams.put("invstBioFormPostprandialSerumGlucose", ch3.isChecked());
                            insertparams.put("invstBioFormOralGlucoseTest", ch4.isChecked());
                            insertparams.put("invstBioFormGlucoseChallengetest", ch5.isChecked());
                            insertparams.put("invstBioFormGlycatedHaemoglobin", ch6.isChecked());
                            insertparams.put("invstBioFormSerumUrea", ch7.isChecked());
                            insertparams.put("invstBioFormSerumCreatinine", ch8.isChecked());
                            insertparams.put("invstBioFormSerumCholesterol", ch9.isChecked());
                            insertparams.put("invstBioFormSerumUricAcid", ch10.isChecked());
                            insertparams.put("invstBioFormSerumTotalProtein", ch11.isChecked());
                            insertparams.put("invstBioFormSerumAlbumin", ch12.isChecked());
                            insertparams.put("invstBioFormSerumTotalBiliruin", ch13.isChecked());
                            insertparams.put("invstBioFormSerumDirectBilurubin", ch14.isChecked());
                            insertparams.put("invstBioFormAST", ch15.isChecked());
                            insertparams.put("invstBioFormALT", ch16.isChecked());
                            insertparams.put("invstBioFormAlkalinePhosphatase", ch17.isChecked());
                            insertparams.put("invstBioFormAmulase", ch18.isChecked());
                            insertparams.put("invstBioFormLipase", ch19.isChecked());
                            insertparams.put("invstBioFormGammaGlutamyl", ch20.isChecked());
                            insertparams.put("invstBioFormLactateDehydrogenase", ch21.isChecked());
                            insertparams.put("invstBioFormCreatineKinase", ch22.isChecked());
                            insertparams.put("invstBioFormCKMB", ch23.isChecked());
                            insertparams.put("invstBioFormTroponin", ch24.isChecked());
                            insertparams.put("invstBioFormSerumElectrolytes", ch25.isChecked());
                            insertparams.put("invstBioFormPTwithINR", ch26.isChecked());
                            insertparams.put("invstBioFormAPTT", ch27.isChecked());
                            insertparams.put("invstBioFormSerumCalcium", ch28.isChecked());
                            insertparams.put("invstBioFormSerumPhosphorous", ch29.isChecked());
                            insertparams.put("invstBioFormSerumIron", ch30.isChecked());
                            insertparams.put("invstBioFormTotalIronBinding", ch31.isChecked());
                            insertparams.put("invstBioFormLipidProfile", ch32.isChecked());
                            insertparams.put("invstBioFormLiverProfile", ch33.isChecked());
                            insertparams.put("invstBioFormRenalProfile", ch34.isChecked());
                            insertparams.put("invstBioFormThyroidProfile", ch35.isChecked());
                            insertparams.put("invstBioFormThyroidMiniProfile", ch36.isChecked());
                            insertparams.put("invstBioFormThyroidExtendedProfile", ch37.isChecked());
                            insertparams.put("invstBioFormAnemiaCapsule", ch38.isChecked());
                            insertparams.put("invstBioFormDiabetesCapsule", ch39.isChecked());
                            insertparams.put("invstBioFormDiabetesMiniCapsule", ch40.isChecked());
                            insertparams.put("invstBioFormUrobilinogen", ch41.isChecked());
                            insertparams.put("invstBioFormMicroscopy", ch42.isChecked());
                            insertparams.put("invstBioFormTotalT3", ch43.isChecked());
                            insertparams.put("invstBioFormTotalT4", ch44.isChecked());
                            insertparams.put("invstBioFormTSH", ch45.isChecked());
                            insertparams.put("invstBioFormFreeT3", ch46.isChecked());
                            insertparams.put("invstBioFormFreeT4", ch47.isChecked());
                            insertparams.put("invstBioFormAntiTPO", ch48.isChecked());
                            insertparams.put("invstBioFormLH", ch49.isChecked());
                            insertparams.put("invstBioFormFSH", ch50.isChecked());
                            insertparams.put("invstBioFormProlactin", ch51.isChecked());
                            insertparams.put("invstBioFormTestosterone", ch52.isChecked());
                            insertparams.put("invstBioFormPSA", ch53.isChecked());
                            insertparams.put("invstBioFormFerritin", ch54.isChecked());
                            insertparams.put("invstBioFormCSFAnalysis", ch55.isChecked());
                            insertparams.put("invstBioFormPleuralFluidAnalysis", ch56.isChecked());
                            insertparams.put("invstBioFormFluidRenalProfile", ch57.isChecked());
                            insertparams.put("invstBioFormAsciticProteins", ch58.isChecked());
                            insertparams.put("invstBioFormAsciticGlucose", ch59.isChecked());
                            insertparams.put("invstBioFormAsciticAlbumin", ch60.isChecked());
                            insertparams.put("invstBioFormAmylase", ch61.isChecked());
                            insertparams.put("invstBioFormElectrolytes", ch62.isChecked());
                            insertparams.put("invstBioFormUMicroAlbumin", ch63.isChecked());
                            insertparams.put("invstBioFormUPhosphates", ch64.isChecked());
                            insertparams.put("invstBioFormUUricAcid", ch65.isChecked());
                            insertparams.put("invstBioForm24hrsUrinaryProteins", ch66.isChecked());
                            insertparams.put("invstBioFormUCreatinine", ch67.isChecked());
                            insertparams.put("invstBioFormUCalcium", ch68.isChecked());
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
                InvestigationBioChemistry.this, new ActionCallback() {
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
                    new AlertDialog.Builder(InvestigationBioChemistry.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Saving Case Record");
        worker.execute(params);
    }

    @Override
    protected void onDestroy () {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_biochemistry_form, menu);
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
                    InvestigationBioChemistry.this, new ActionCallback() {
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