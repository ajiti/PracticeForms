package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolHealthcareScreening extends AppCompatActivity {

    private static final String TAG = "SchoolHealthcareForm";
    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/caserecordinsert";
    private Toolbar toolbar;
    private BroadcastReceiver logoutReceiver = null;
    ExpandableListView expListView;
    SHSExpandable listAdapter;
    List<String> listDataHeader;
    private TextView headTextView;
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
    private String jsonparams;
    private Context context = this;
    private int lastExpandedPosition = -1;
    private String selectedSubForm;
    private View basicView;
    private String shsFormOneAOne1, shsFormOneAOne2, shsFormOneAOne3, shsFormOneAOne4, shsFormOneAOne5, shsFormOneAOne6, shsFormOneAOne7, shsFormOneAOne8, shsFormOneAOne9,
            shsFormOneATwo10a, shsFormOneATwo10b, shsFormOneATwo10c, shsFormOneATwo10d, shsFormOneATwo10e, shsFormOneATwo11, shsFormOneBOne1, shsFormOneBOne2, shsFormOneBOne3,
            shsFormOneBOne4, shsFormOneBOne5, shsFormOneCOne1, shsFormOneCOne2, shsFormOneCOne3, shsFormOneCOne4, shsFormOneCOne5, shsFormOneDOne1, shsFormOneDOne2, shsFormOneDOne3,
            shsFormOneDOne4, shsFormOneDOne5, shsFormOneDOne6, shsFormOneDOne7, shsFormOneDOne8, shsFormOneDTwo1, shsFormOneDTwo2, shsFormOneDTwo3, shsFormOneDTwo4, shsFormOneDTwo5,
            shsFormOneDTwo6, shsFormOneDThree1, shsFormOneDThree2, shsFormOneDThree3, shsFormOneDThree4, shsFormOneDThree5, shsFormOneDThree6, shsFormOneDThree7, shsFormOneDFour1,
            shsFormOneDFour2, shsFormOneDFour3, shsFormOneDFour4, shsFormOneDFour5, shsFormOneDFour6, shsFormOneDFive1, shsFormOneDFive2, shsFormOneDFive3, shsFormOneDFive4,
            shsFormOneDFive5, shsFormOneDFive6, shsFormOneDFive7, shsFormOneDSix1, shsFormOneDSix2, shsFormOneDSix3, shsFormOneDSix4, shsFormOneDSix5, shsFormOneDSix6,
            shsFormOneDSeven1, shsFormOneDSeven2, shsFormOneDSeven3, shsFormOneDSeven4, shsFormOneDSeven5, shsFormOneDEight1, shsFormOneDEight2, shsFormOneDEight3, shsFormOneDEight4,
            shsFormOneDEight5, shsFormOneDNine1, shsFormOneDTenOne1, shsFormOneDTenOne2, shsFormOneDTenOne3, shsFormOneDTenTwo1, shsFormOneDTenTwo2, shsFormOneDTenTwo3,
            shsFormOneDEleven1, shsFormOneDEleven2, shsFormOneDEleven3, shsFormOneDEleven4, shsFormOneDEleven5, shsFormOneDEleven6, shsFormOneDEleven7, shsFormOneDEleven8,
            shsFormOneDEleven9, shsFormOneDEleven10, shsFormOneDEleven11, shsFormOnePFR1, shsFormOnePFR2, shsFormOnePFR3, shsFormOnePFR4, shsFormOnePFR5, shsFormOnePFR6,
            shsFormOnePFR7, shsFormOnePFR8, shsFormOnePFR9, shsFormOnePFR10, shsFormOnePFR11, shsFormOnePFR12, shsFormOnePFR13, shsFormOnePFR15, shsFormOnePFR16, shsFormOnePFR17,
            shsFormOnePFR18, shsFormOnePFR19, shsFormOnePFR20, shsFormOnePFR21, shsFormOnePFR22, shsFormOnePFR23, shsFormOnePFR24, shsFormOnePFR25, shsFormOnePFR26, shsFormOnePFR27,
            shsFormOnePFR28, shsFormOnePFR29, shsFormOnePFR30;
    private String shsFormTwoAOne1, shsFormTwoAOne2, shsFormTwoAOne3, shsFormTwoAOne4, shsFormTwoAOne5, shsFormTwoAOne6, shsFormTwoAOne7, shsFormTwoAOne8, shsFormTwoAOne9,
            SHSFormTwoATwo10a, SHSFormTwoATwo10b, SHSFormTwoATwo10c, SHSFormTwoATwo10d, SHSFormTwoATwo10e, SHSFormTwoATwo11, shsFormTwoBOne1, shsFormTwoBOne2, shsFormTwoBOne3,
            shsFormTwoBOne4, shsFormTwoBOne5, shsFormTwoCOne1, shsFormTwoCOne2, shsFormTwoCOne3, shsFormTwoCOne4, shsFormTwoCOne5, shsFormTwoCOne6, shsFormTwoDOne1, shsFormTwoDOne2,
            shsFormTwoDOne3, shsFormTwoDOne4, shsFormTwoDOne5, shsFormTwoDOne6, shsFormTwoDOne7, shsFormTwoDOne8, shsFormTwoDOne9, shsFormTwoEOne1, shsFormTwoEOne2, shsFormTwoEOne3,
            shsFormTwoEOne4, shsFormTwoEOne5, shsFormTwoEOne6, shsFormTwoEOne7, shsFormTwoEOne8, shsFormTwoPFR1, shsFormTwoPFR2, shsFormTwoPFR3, shsFormTwoPFR4, shsFormTwoPFR5,
            shsFormTwoPFR6, shsFormTwoPFR7, shsFormTwoPFR8, shsFormTwoPFR9, shsFormTwoPFR10, shsFormTwoPFR11, shsFormTwoPFR12, shsFormTwoPFR13, shsFormTwoPFR14, shsFormTwoPFR15,
            shsFormTwoPFR16, shsFormTwoPFR17, shsFormTwoPFR18, shsFormTwoPFR19, shsFormTwoPFR20, shsFormTwoPFR21, shsFormTwoPFR22, shsFormTwoPFR23, shsFormTwoPFR24, shsFormTwoPFR25,
            shsFormTwoPFR26, shsFormTwoPFR27, shsFormTwoPFR28, shsFormTwoPFR29, shsFormTwoPFR30, shsFormTwoPFR31, shsFormTwoPFR32, shsFormTwoPFR33, shsFormTwoPFR34, shsFormTwoPFR35,
            shsFormTwoPFR36, shsFormTwoPFR37, shsFormTwoPFR38;
    private RadioGroup rga1, rga2, rga3, rga4, rga5, rga6, rga7, rga8, rga9, rga10a, rga10b, rga10c, rga10d, rga10e, rga11, rgb1, rgb2, rgb3, rgb4, rgb5, rgc1, rgc2, rgc3, rgc4, rgc5,
            rgc6, rgd1_1, rgd1_2, rgd1_3, rgd1_4, rgd1_5, rgd1_6, rgd1_7, rgd1_8, rgd1_9, rgd2_1, rgd2_2, rgd2_3, rgd2_4, rgd2_5, rgd2_6, rgd3_1, rgd3_2, rgd3_3, rgd3_4, rgd3_5, rgd3_6,
            rgd3_7, rgd4_1, rgd4_2, rgd4_3, rgd4_4, rgd4_5, rgd4_6, rgd5_1, rgd5_2, rgd5_3, rgd5_4, rgd5_5, rgd5_6, rgd5_7, rgd6_1, rgd6_2, rgd6_3, rgd6_4, rgd6_5, rgd6_6, rgd7_1,
            rgd7_2, rgd7_3, rgd7_4, rgd7_5, rgd8_1, rgd8_2, rgd8_3, rgd8_4, rgd8_5, rgd9_1, rgd10_1_1, rgd10_1_2, rgd10_1_3, rgd10_2_1, rgd10_2_2, rgd10_2_3, rgd11_1, rgd11_2, rgd11_3,
            rgd11_4, rgd11_5, rgd11_6, rgd11_7, rgd11_8, rgd11_9, rgd11_10, rgd11_11, rge1, rge2, rge3, rge4, rge5, rge6, rge7, rge8;
    private CheckBox cbpf1, cbpf2, cbpf3, cbpf4, cbpf5, cbpf6, cbpf7, cbpf8, cbpf9, cbpf10, cbpf11, cbpf12, cbpf13, cbpf14, cbpf15, cbpf16, cbpf17, cbpf18, cbpf19, cbpf20,
            cbpf21, cbpf22, cbpf23, cbpf24, cbpf25, cbpf26, cbpf27, cbpf28, cbpf29, cbpf31, cbpf32, cbpf33, cbpf34, cbpf35, cbpf36, cbpf37, cbpf38;
    private CheckBox checkBoxes[];
    private EditText et30;
    private String othersSpecify;
    private static final String DEFAULT_VALUE = "-1";
    private List<String> cb2List, cb21List, cb22List, cb24List, cb25List, cb26List, cb27List;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        final int ageInMonths = getIntent().getExtras().getInt("ageInMonths");
        final String gender = getIntent().getExtras().getString("gender");

        setContentView(R.layout.layout_school_health_screening);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        
        expListView = (ExpandableListView) findViewById(R.id.shsFormExp);
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
        if(ageInMonths <= 72) {
            selectedSubForm = "formOne";
            headTextView = (TextView) findViewById(R.id.head0to6);
            headTextView.setVisibility(View.VISIBLE);
            listDataHeader.add(getResources().getString(R.string.subhead_a_school_screening));
            listDataHeader.add(getResources().getString(R.string.subhead_b_school_screening));
            listDataHeader.add(getResources().getString(R.string.subhead_c_school_screening));
            listDataHeader.add(getResources().getString(R.string.subhead_d_school_screening));
            if(ageInMonths > 15 && ageInMonths <= 24)
                listDataHeader.add(getResources().getString(R.string.subhead_d10_school_screening));
        }
        else if(ageInMonths <= 216) {
            selectedSubForm = "formTwo";
            headTextView = (TextView) findViewById(R.id.head6to18);
            headTextView.setVisibility(View.VISIBLE);
            listDataHeader.add(getResources().getString(R.string.second_subhead_a_school_screening));
            listDataHeader.add(getResources().getString(R.string.second_subhead_b_school_screening));
            listDataHeader.add(getResources().getString(R.string.second_subhead_c_school_screening));
            listDataHeader.add(getResources().getString(R.string.second_subhead_d_school_screening));
            if(ageInMonths > 120)
                listDataHeader.add(getResources().getString(R.string.second_subhead_e_school_screening));
        }
        listDataHeader.add(getResources().getString(R.string.third_head_school_screening));

        listAdapter = new SHSExpandable(this, listDataHeader, ageInMonths, gender);
        expListView.setAdapter(listAdapter);

        Button btn = (Button) findViewById(R.id.shssave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> formsList = new ArrayList<>();
                formsList.add(formName);

                final Map<String, Object> insertparams = new HashMap<>();
                insertparams.put("shsFormData", new Gson().toJson(getFormValues(ageInMonths)));
                insertparams.put("shsFormSubName", selectedSubForm);
                insertparams.put("shsFormGender", getShortGender(gender));
                insertparams.put("shsFormDob", dob);
                insertparams.put("shsFormSkipParse", true);
                insertparams.put("shsFormAgeInMonth", String.valueOf(ageInMonths));
                insertparams.put("practiceFormNameDataIndex", "1");
                insertparams.put("practiceFormNames", formsList);
                insertparams.put("actionType", "save");
                insertparams.put("healthRegistrationId", healthRegistrationId);
                insertparams.put("patientName", patientName);
                insertparams.put("regnLinkId", regLinkId);
                if(caseid != null){
                    insertparams.put("caseRecordNo", caseid);
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
                SchoolHealthcareScreening.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if (resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {

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
                                    caseRecordsActivity.putExtra("dob", dob);
                                    caseRecordsActivity.putExtra("reqLinkId", regLinkId);
                                    caseRecordsActivity.putExtra("caserecordno", caseid);
                                    caseRecordsActivity.putExtra("gender", getIntent().getExtras().getString("gender"));
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
                    new AlertDialog.Builder(SchoolHealthcareScreening.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Saving Case Record");
        worker.execute(params);
    }

    private Map<String, String> getFormValues(int ageInMonths) {
        Map<String, String> insertParams = new HashMap<>();
        try {
            setInputValues(ageInMonths, basicView);
            setPreliminaryCheckedValues(ageInMonths);
            if(ageInMonths <= 72){
                insertParams.put("SHSFormOneAOne1",shsFormOneAOne1);
                insertParams.put("SHSFormOneAOne2",shsFormOneAOne2);
                insertParams.put("SHSFormOneAOne3",shsFormOneAOne3);
                insertParams.put("SHSFormOneAOne4",shsFormOneAOne4);
                insertParams.put("SHSFormOneAOne5",shsFormOneAOne5);
                insertParams.put("SHSFormOneAOne6",shsFormOneAOne6);
                insertParams.put("SHSFormOneAOne7",shsFormOneAOne7);
                insertParams.put("SHSFormOneAOne8",shsFormOneAOne8);
                insertParams.put("SHSFormOneAOne9",shsFormOneAOne9);
                insertParams.put("SHSFormOneATwo10a",shsFormOneATwo10a);
                insertParams.put("SHSFormOneATwo10b",shsFormOneATwo10b);
                insertParams.put("SHSFormOneATwo10c",shsFormOneATwo10c);
                insertParams.put("SHSFormOneATwo10d",shsFormOneATwo10d);
                insertParams.put("SHSFormOneATwo10e",shsFormOneATwo10e);
                insertParams.put("SHSFormOneATwo11",shsFormOneATwo11);
                insertParams.put("SHSFormOneBOne1",shsFormOneBOne1);
                insertParams.put("SHSFormOneBOne2",shsFormOneBOne2);
                insertParams.put("SHSFormOneBOne3",shsFormOneBOne3);
                insertParams.put("SHSFormOneBOne4",shsFormOneBOne4);
                insertParams.put("SHSFormOneBOne5",shsFormOneBOne5);
                insertParams.put("SHSFormOneCOne1",shsFormOneCOne1);
                insertParams.put("SHSFormOneCOne2",shsFormOneCOne2);
                insertParams.put("SHSFormOneCOne3",shsFormOneCOne3);
                insertParams.put("SHSFormOneCOne4",shsFormOneCOne4);
                insertParams.put("SHSFormOneCOne5",shsFormOneCOne5);
                insertParams.put("SHSFormOneDNine1",shsFormOneDNine1);
                if(ageInMonths>2 && ageInMonths<=4){
                    insertParams.put("SHSFormOneDOne1",shsFormOneDOne1);
                    insertParams.put("SHSFormOneDOne2",shsFormOneDOne2);
                    insertParams.put("SHSFormOneDOne3",shsFormOneDOne3);
                    insertParams.put("SHSFormOneDOne4",shsFormOneDOne4);
                    insertParams.put("SHSFormOneDOne5",shsFormOneDOne5);
                    insertParams.put("SHSFormOneDOne6",shsFormOneDOne6);
                    insertParams.put("SHSFormOneDOne7",shsFormOneDOne7);
                    insertParams.put("SHSFormOneDOne8",shsFormOneDOne8);
                }
                else if(ageInMonths>4 && ageInMonths<=6){
                    insertParams.put("SHSFormOneDTwo1",shsFormOneDTwo1);
                    insertParams.put("SHSFormOneDTwo2",shsFormOneDTwo2);
                    insertParams.put("SHSFormOneDTwo3",shsFormOneDTwo3);
                    insertParams.put("SHSFormOneDTwo4",shsFormOneDTwo4);
                    insertParams.put("SHSFormOneDTwo5",shsFormOneDTwo5);
                    insertParams.put("SHSFormOneDTwo6",shsFormOneDTwo6);
                }
                else if(ageInMonths>6 && ageInMonths<=9){
                    insertParams.put("SHSFormOneDThree1",shsFormOneDThree1);
                    insertParams.put("SHSFormOneDThree2",shsFormOneDThree2);
                    insertParams.put("SHSFormOneDThree3",shsFormOneDThree3);
                    insertParams.put("SHSFormOneDThree4",shsFormOneDThree4);
                    insertParams.put("SHSFormOneDThree5",shsFormOneDThree5);
                    insertParams.put("SHSFormOneDThree6",shsFormOneDThree6);
                    insertParams.put("SHSFormOneDThree7",shsFormOneDThree7);
                }
                else if(ageInMonths>9 && ageInMonths<=12){
                    insertParams.put("SHSFormOneDFour1",shsFormOneDFour1);
                    insertParams.put("SHSFormOneDFour2",shsFormOneDFour2);
                    insertParams.put("SHSFormOneDFour3",shsFormOneDFour3);
                    insertParams.put("SHSFormOneDFour4",shsFormOneDFour4);
                    insertParams.put("SHSFormOneDFour5",shsFormOneDFour5);
                    insertParams.put("SHSFormOneDFour6",shsFormOneDFour6);
                }
                else if(ageInMonths>12 && ageInMonths<=15){
                    insertParams.put("SHSFormOneDFive1",shsFormOneDFive1);
                    insertParams.put("SHSFormOneDFive2",shsFormOneDFive2);
                    insertParams.put("SHSFormOneDFive3",shsFormOneDFive3);
                    insertParams.put("SHSFormOneDFive4",shsFormOneDFive4);
                    insertParams.put("SHSFormOneDFive5",shsFormOneDFive5);
                    insertParams.put("SHSFormOneDFive6",shsFormOneDFive6);
                    insertParams.put("SHSFormOneDFive7",shsFormOneDFive7);
                }
                else if(ageInMonths>15 && ageInMonths<=18){
                    insertParams.put("SHSFormOneDSix1",shsFormOneDSix1);
                    insertParams.put("SHSFormOneDSix2",shsFormOneDSix2);
                    insertParams.put("SHSFormOneDSix3",shsFormOneDSix3);
                    insertParams.put("SHSFormOneDSix4",shsFormOneDSix4);
                    insertParams.put("SHSFormOneDSix5",shsFormOneDSix5);
                    insertParams.put("SHSFormOneDSix6",shsFormOneDSix6);
                    insertParams.put("SHSFormOneDTenOne1",shsFormOneDTenOne1);
                    insertParams.put("SHSFormOneDTenOne2",shsFormOneDTenOne2);
                    insertParams.put("SHSFormOneDTenOne3",shsFormOneDTenOne3);
                }
                else if(ageInMonths>18 && ageInMonths<=24){
                    insertParams.put("SHSFormOneDSeven1",shsFormOneDSeven1);
                    insertParams.put("SHSFormOneDSeven2",shsFormOneDSeven2);
                    insertParams.put("SHSFormOneDSeven3",shsFormOneDSeven3);
                    insertParams.put("SHSFormOneDSeven4",shsFormOneDSeven4);
                    insertParams.put("SHSFormOneDSeven5",shsFormOneDSeven5);
                    insertParams.put("SHSFormOneDTenOne1",shsFormOneDTenTwo1);
                    insertParams.put("SHSFormOneDTenOne2",shsFormOneDTenTwo2);
                    insertParams.put("SHSFormOneDTenOne3",shsFormOneDTenTwo3);
                }
                else if(ageInMonths>24 && ageInMonths<=30){
                    insertParams.put("SHSFormOneDEight1",shsFormOneDEight1);
                    insertParams.put("SHSFormOneDEight2",shsFormOneDEight2);
                    insertParams.put("SHSFormOneDEight3",shsFormOneDEight3);
                    insertParams.put("SHSFormOneDEight4",shsFormOneDEight4);
                    insertParams.put("SHSFormOneDEight5",shsFormOneDEight5);
                }
                else if(ageInMonths>30){
                    insertParams.put("SHSFormOneDEleven1",shsFormOneDEleven1);
                    insertParams.put("SHSFormOneDEleven2",shsFormOneDEleven2);
                    insertParams.put("SHSFormOneDEleven3",shsFormOneDEleven3);
                    insertParams.put("SHSFormOneDEleven4",shsFormOneDEleven4);
                    insertParams.put("SHSFormOneDEleven5",shsFormOneDEleven5);
                    insertParams.put("SHSFormOneDEleven6",shsFormOneDEleven6);
                    insertParams.put("SHSFormOneDEleven7",shsFormOneDEleven7);
                    insertParams.put("SHSFormOneDEleven8",shsFormOneDEleven8);
                    insertParams.put("SHSFormOneDEleven9",shsFormOneDEleven9);
                    insertParams.put("SHSFormOneDEleven10",shsFormOneDEleven10);
                    insertParams.put("SHSFormOneDEleven11",shsFormOneDEleven11);
                }
                insertParams.put("SHSFormOnePFR1", getPreliminaryFindingCheckedStringValue(cbpf1.isChecked()));
                insertParams.put("SHSFormOnePFR2", getPreliminaryFindingCheckedStringValue(cbpf2.isChecked()));
                insertParams.put("SHSFormOnePFR3", getPreliminaryFindingCheckedStringValue(cbpf3.isChecked()));
                insertParams.put("SHSFormOnePFR4", getPreliminaryFindingCheckedStringValue(cbpf4.isChecked()));
                insertParams.put("SHSFormOnePFR5", getPreliminaryFindingCheckedStringValue(cbpf5.isChecked()));
                insertParams.put("SHSFormOnePFR6", getPreliminaryFindingCheckedStringValue(cbpf6.isChecked()));
                insertParams.put("SHSFormOnePFR7", getPreliminaryFindingCheckedStringValue(cbpf7.isChecked()));
                insertParams.put("SHSFormOnePFR8", getPreliminaryFindingCheckedStringValue(cbpf8.isChecked()));
                insertParams.put("SHSFormOnePFR9", getPreliminaryFindingCheckedStringValue(cbpf9.isChecked()));
                insertParams.put("SHSFormOnePFR10", getPreliminaryFindingCheckedStringValue(cbpf10.isChecked()));
                insertParams.put("SHSFormOnePFR11", getPreliminaryFindingCheckedStringValue(cbpf11.isChecked()));
                insertParams.put("SHSFormOnePFR12", getPreliminaryFindingCheckedStringValue(cbpf12.isChecked()));
                insertParams.put("SHSFormOnePFR13", getPreliminaryFindingCheckedStringValue(cbpf13.isChecked()));
                insertParams.put("SHSFormOnePFR15", getPreliminaryFindingCheckedStringValue(cbpf15.isChecked()));
                insertParams.put("SHSFormOnePFR16", getPreliminaryFindingCheckedStringValue(cbpf16.isChecked()));
                insertParams.put("SHSFormOnePFR17", getPreliminaryFindingCheckedStringValue(cbpf17.isChecked()));
                insertParams.put("SHSFormOnePFR18", getPreliminaryFindingCheckedStringValue(cbpf18.isChecked()));
                insertParams.put("SHSFormOnePFR19", getPreliminaryFindingCheckedStringValue(cbpf19.isChecked()));
                insertParams.put("SHSFormOnePFR20", getPreliminaryFindingCheckedStringValue(cbpf20.isChecked()));
                insertParams.put("SHSFormOnePFR21", getPreliminaryFindingCheckedStringValue(cbpf21.isChecked()));
                insertParams.put("SHSFormOnePFR22", getPreliminaryFindingCheckedStringValue(cbpf22.isChecked()));
                insertParams.put("SHSFormOnePFR23", getPreliminaryFindingCheckedStringValue(cbpf23.isChecked()));
                insertParams.put("SHSFormOnePFR24", getPreliminaryFindingCheckedStringValue(cbpf24.isChecked()));
                insertParams.put("SHSFormOnePFR25", getPreliminaryFindingCheckedStringValue(cbpf25.isChecked()));
                insertParams.put("SHSFormOnePFR26", getPreliminaryFindingCheckedStringValue(cbpf26.isChecked()));
                insertParams.put("SHSFormOnePFR27", getPreliminaryFindingCheckedStringValue(cbpf27.isChecked()));
                insertParams.put("SHSFormOnePFR28", getPreliminaryFindingCheckedStringValue(cbpf28.isChecked()));
                insertParams.put("SHSFormOnePFR29", getPreliminaryFindingCheckedStringValue(cbpf29.isChecked()));
                insertParams.put("SHSFormOnePFRNote", othersSpecify);
            }
            else if(ageInMonths > 72){
                insertParams.put("SHSFormTwoAOne1",shsFormOneAOne1);
                insertParams.put("SHSFormTwoAOne2",shsFormOneAOne2);
                insertParams.put("SHSFormTwoAOne3",shsFormOneAOne3);
                insertParams.put("SHSFormTwoAOne4",shsFormOneAOne4);
                insertParams.put("SHSFormTwoAOne5",shsFormOneAOne5);
                insertParams.put("SHSFormTwoAOne6",shsFormOneAOne6);
                insertParams.put("SHSFormTwoAOne7",shsFormOneAOne7);
                insertParams.put("SHSFormTwoAOne8",shsFormOneAOne8);
                insertParams.put("SHSFormTwoAOne9",shsFormOneAOne9);
                insertParams.put("SHSFormTwoATwo10a",shsFormOneATwo10a);
                insertParams.put("SHSFormTwoATwo10b",shsFormOneATwo10b);
                insertParams.put("SHSFormTwoATwo10c",shsFormOneATwo10c);
                insertParams.put("SHSFormTwoATwo10d",shsFormOneATwo10d);
                insertParams.put("SHSFormTwoATwo10e",shsFormOneATwo10e);
                insertParams.put("SHSFormTwoATwo11",shsFormOneATwo11);
                insertParams.put("SHSFormTwoBOne1",shsFormOneBOne1);
                insertParams.put("SHSFormTwoBOne2",shsFormOneBOne2);
                insertParams.put("SHSFormTwoBOne3",shsFormOneBOne3);
                insertParams.put("SHSFormTwoBOne4",shsFormOneBOne4);
                insertParams.put("SHSFormTwoBOne5",shsFormOneBOne5);
                insertParams.put("SHSFormTwoCOne1",shsFormOneCOne1);
                insertParams.put("SHSFormTwoCOne2",shsFormOneCOne2);
                insertParams.put("SHSFormTwoCOne3",shsFormOneCOne3);
                insertParams.put("SHSFormTwoCOne4",shsFormOneCOne4);
                insertParams.put("SHSFormTwoCOne5",shsFormOneCOne5);
                insertParams.put("SHSFormTwoCOne6",shsFormTwoCOne6);
                insertParams.put("SHSFormTwoDOne1",shsFormTwoDOne1);
                insertParams.put("SHSFormTwoDOne2",shsFormTwoDOne2);
                insertParams.put("SHSFormTwoDOne3",shsFormTwoDOne3);
                insertParams.put("SHSFormTwoDOne4",shsFormTwoDOne4);
                insertParams.put("SHSFormTwoDOne5",shsFormTwoDOne5);
                insertParams.put("SHSFormTwoDOne6",shsFormTwoDOne6);
                insertParams.put("SHSFormTwoDOne7",shsFormTwoDOne7);
                insertParams.put("SHSFormTwoDOne8",shsFormTwoDOne8);
                insertParams.put("SHSFormTwoDOne9",shsFormTwoDOne9);
                if(ageInMonths > 120){
                    insertParams.put("SHSFormTwoEOne1",shsFormTwoEOne1);
                    insertParams.put("SHSFormTwoEOne2",shsFormTwoEOne2);
                    insertParams.put("SHSFormTwoEOne3",shsFormTwoEOne3);
                    insertParams.put("SHSFormTwoEOne4",shsFormTwoEOne4);
                    insertParams.put("SHSFormTwoEOne5",shsFormTwoEOne5);
                    insertParams.put("SHSFormTwoEOne6",shsFormTwoEOne6);
                    insertParams.put("SHSFormTwoEOne7",shsFormTwoEOne7);
                    insertParams.put("SHSFormTwoEOne8",shsFormTwoEOne8);
                }
                insertParams.put("SHSFormTwoPFR1", getPreliminaryFindingCheckedStringValue(cbpf1.isChecked()));
                insertParams.put("SHSFormTwoPFR2", getPreliminaryFindingCheckedStringValue(cbpf2.isChecked()));
                insertParams.put("SHSFormTwoPFR3", getPreliminaryFindingCheckedStringValue(cbpf3.isChecked()));
                insertParams.put("SHSFormTwoPFR4", getPreliminaryFindingCheckedStringValue(cbpf4.isChecked()));
                insertParams.put("SHSFormTwoPFR5", getPreliminaryFindingCheckedStringValue(cbpf5.isChecked()));
                insertParams.put("SHSFormTwoPFR6", getPreliminaryFindingCheckedStringValue(cbpf6.isChecked()));
                insertParams.put("SHSFormTwoPFR7", getPreliminaryFindingCheckedStringValue(cbpf7.isChecked()));
                insertParams.put("SHSFormTwoPFR8", getPreliminaryFindingCheckedStringValue(cbpf8.isChecked()));
                insertParams.put("SHSFormTwoPFR9", getPreliminaryFindingCheckedStringValue(cbpf9.isChecked()));
                insertParams.put("SHSFormTwoPFR10", getPreliminaryFindingCheckedStringValue(cbpf10.isChecked()));
                insertParams.put("SHSFormTwoPFR11", getPreliminaryFindingCheckedStringValue(cbpf11.isChecked()));
                insertParams.put("SHSFormTwoPFR12", getPreliminaryFindingCheckedStringValue(cbpf12.isChecked()));
                insertParams.put("SHSFormTwoPFR13", getPreliminaryFindingCheckedStringValue(cbpf13.isChecked()));
                insertParams.put("SHSFormTwoPFR14", getPreliminaryFindingCheckedStringValue(cbpf14.isChecked()));
                insertParams.put("SHSFormTwoPFR15", getPreliminaryFindingCheckedStringValue(cbpf15.isChecked()));
                insertParams.put("SHSFormTwoPFR16", getPreliminaryFindingCheckedStringValue(cbpf16.isChecked()));
                insertParams.put("SHSFormTwoPFR17", getPreliminaryFindingCheckedStringValue(cbpf17.isChecked()));
                insertParams.put("SHSFormTwoPFR18", getPreliminaryFindingCheckedStringValue(cbpf18.isChecked()));
                insertParams.put("SHSFormTwoPFR19", getPreliminaryFindingCheckedStringValue(cbpf19.isChecked()));
                insertParams.put("SHSFormTwoPFR20", getPreliminaryFindingCheckedStringValue(cbpf20.isChecked()));
                insertParams.put("SHSFormTwoPFR21", getPreliminaryFindingCheckedStringValue(cbpf21.isChecked()));
                insertParams.put("SHSFormTwoPFR22", getPreliminaryFindingCheckedStringValue(cbpf22.isChecked()));
                insertParams.put("SHSFormTwoPFR23", getPreliminaryFindingCheckedStringValue(cbpf23.isChecked()));
                insertParams.put("SHSFormTwoPFR24", getPreliminaryFindingCheckedStringValue(cbpf24.isChecked()));
                insertParams.put("SHSFormTwoPFR25", getPreliminaryFindingCheckedStringValue(cbpf25.isChecked()));
                insertParams.put("SHSFormTwoPFR26", getPreliminaryFindingCheckedStringValue(cbpf26.isChecked()));
                insertParams.put("SHSFormTwoPFR27", getPreliminaryFindingCheckedStringValue(cbpf27.isChecked()));
                insertParams.put("SHSFormTwoPFR28", getPreliminaryFindingCheckedStringValue(cbpf28.isChecked()));
                insertParams.put("SHSFormTwoPFR29", getPreliminaryFindingCheckedStringValue(cbpf29.isChecked()));
                insertParams.put("SHSFormTwoPFRNote", othersSpecify);
                insertParams.put("SHSFormTwoPFR31", getPreliminaryFindingCheckedStringValue(cbpf31.isChecked()));
                insertParams.put("SHSFormTwoPFR32", getPreliminaryFindingCheckedStringValue(cbpf32.isChecked()));
                insertParams.put("SHSFormTwoPFR33", getPreliminaryFindingCheckedStringValue(cbpf33.isChecked()));
                insertParams.put("SHSFormTwoPFR34", getPreliminaryFindingCheckedStringValue(cbpf34.isChecked()));
                insertParams.put("SHSFormTwoPFR35", getPreliminaryFindingCheckedStringValue(cbpf35.isChecked()));
                insertParams.put("SHSFormTwoPFR36", getPreliminaryFindingCheckedStringValue(cbpf36.isChecked()));
                insertParams.put("SHSFormTwoPFR37", getPreliminaryFindingCheckedStringValue(cbpf37.isChecked()));
                insertParams.put("SHSFormTwoPFR38", getPreliminaryFindingCheckedStringValue(cbpf38.isChecked()));
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Form Values", e);
        }
        return insertParams;
    }

    private String getCheckedValue(String text) {
        if("Yes".equalsIgnoreCase(text))
            return "1";
        else if("No".equalsIgnoreCase(text))
            return "0";
        return DEFAULT_VALUE;
    }

    private String getCheckedValueToAssign(View view, int rgid) {
        if (view!=null && !DEFAULT_VALUE.equals(String.valueOf(rgid)))
            return getCheckedValue((String) ((RadioButton) view.findViewById(rgid)).getText());
        return DEFAULT_VALUE;
    }

    private String getPreliminaryFindingCheckedStringValue(boolean isCheck){
        return (isCheck) ? "1" : "";
    }

    class SHSExpandable extends BaseExpandableListAdapter {

        private Context context;
        private List<String> listDataHeader;
        private int ageInMonths;
        private String gender;
        private LinearLayout aLayout,bLayout,cLayout,dLayout,d1Layout,d2Layout,eLayout;
        private LinearLayout prelimLayout,aPrelimLayout,bPrelimLayout,cPrelimLayout,dPrelimLayout,ePrelimLayout;
        private RelativeLayout code14Layout, e4Layout;

        public SHSExpandable(Context context, List<String> listDataHeader, int ageInMonths, String gender)
        {
            this.context = context;
            this.listDataHeader = listDataHeader;
            this.ageInMonths = ageInMonths;
            this.gender = gender;
            if(ageInMonths <= 72) {
                basicView = View.inflate(context, R.layout.activity_school_healthcare_screening_0to6, null);
                if(basicView != null){
                    aLayout = (LinearLayout) basicView.findViewById(R.id.a_block);
                    bLayout = (LinearLayout) basicView.findViewById(R.id.b_block);
                    cLayout = (LinearLayout) basicView.findViewById(R.id.c_block);
                    dLayout = (LinearLayout) basicView.findViewById(R.id.d_block);
                    if(ageInMonths > 2 && ageInMonths <= 4)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d1_block);
                    else if(ageInMonths > 4 && ageInMonths <= 6)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d2_block);
                    else if(ageInMonths > 6 && ageInMonths <= 9)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d3_block);
                    else if(ageInMonths > 9 && ageInMonths <= 12)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d4_block);
                    else if(ageInMonths > 12 && ageInMonths <= 15)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d5_block);
                    else if(ageInMonths > 15 && ageInMonths <= 18) {
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d6_block);
                        d2Layout = (LinearLayout) basicView.findViewById(R.id.d10_1_block);
                    }
                    else if(ageInMonths > 18 && ageInMonths <= 24) {
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d7_block);
                        d2Layout = (LinearLayout) basicView.findViewById(R.id.d10_2_block);
                    }
                    else if(ageInMonths > 24 && ageInMonths <= 30)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d8_block);
                    else if(ageInMonths > 30)
                        d1Layout = (LinearLayout) basicView.findViewById(R.id.d11_block);
                }
            }
            else if(ageInMonths <= 216) {
                basicView = View.inflate(context, R.layout.activity_school_healthcare_screening_6to18, null);
                if(basicView != null) {
                    aLayout = (LinearLayout) basicView.findViewById(R.id.sec_a_block);
                    bLayout = (LinearLayout) basicView.findViewById(R.id.sec_b_block);
                    cLayout = (LinearLayout) basicView.findViewById(R.id.sec_c_block);
                    dLayout = (LinearLayout) basicView.findViewById(R.id.sec_d_block);
                    if(ageInMonths > 120){
                        eLayout = (LinearLayout) basicView.findViewById(R.id.sec_e_block);
                        if("female".equalsIgnoreCase(gender))
                            e4Layout = (RelativeLayout) basicView.findViewById(R.id.sec_e4_block);
                    }
                }
            }
            if(basicView != null) {
                prelimLayout = (LinearLayout) basicView.findViewById(R.id.preliminaryFindings);
                aPrelimLayout = (LinearLayout) basicView.findViewById(R.id.thd_a_block);
                bPrelimLayout = (LinearLayout) basicView.findViewById(R.id.thd_b_block);
                cPrelimLayout = (LinearLayout) basicView.findViewById(R.id.thd_c_block);
                dPrelimLayout = (LinearLayout) basicView.findViewById(R.id.thd_d_block);
                if(ageInMonths > 72) {
                    ePrelimLayout = (LinearLayout) basicView.findViewById(R.id.thd_e_block);
                    code14Layout = (RelativeLayout) basicView.findViewById(R.id.b_14);
                }
                loadLayoutIds(ageInMonths, basicView);
            }
        }

        @Override
        public int getGroupCount() {
            return this.listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.listDataHeader.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.caserecordforms_list_group, null);
                convertView.findViewById(R.id.formShareCheckBox).setVisibility(View.GONE);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
            lblListHeader.setBackgroundColor(Color.TRANSPARENT);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            try {
                String headerTitle = (String) getGroup(groupPosition);
                if(groupPosition == 0){
                    aLayout.setVisibility(View.VISIBLE);
                    bLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.GONE);
                    if(dLayout != null)
                        dLayout.setVisibility(View.GONE);
                    if(d1Layout != null)
                        d1Layout.setVisibility(View.GONE);
                    if(d2Layout != null)
                        d2Layout.setVisibility(View.GONE);
                    if(eLayout != null)
                        eLayout.setVisibility(View.GONE);
                    prelimLayout.setVisibility(View.GONE);
                }
                else if(groupPosition == 1){
                    bLayout.setVisibility(View.VISIBLE);
                    aLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.GONE);
                    if(dLayout != null)
                        dLayout.setVisibility(View.GONE);
                    if(d1Layout != null)
                        d1Layout.setVisibility(View.GONE);
                    if(d2Layout != null)
                        d2Layout.setVisibility(View.GONE);
                    if(eLayout != null)
                        eLayout.setVisibility(View.GONE);
                    prelimLayout.setVisibility(View.GONE);
                }
                else if(groupPosition == 2){
                    cLayout.setVisibility(View.VISIBLE);
                    bLayout.setVisibility(View.GONE);
                    aLayout.setVisibility(View.GONE);
                    if(dLayout != null)
                        dLayout.setVisibility(View.GONE);
                    if(d1Layout != null)
                        d1Layout.setVisibility(View.GONE);
                    if(d2Layout != null)
                        d2Layout.setVisibility(View.GONE);
                    if(eLayout != null)
                        eLayout.setVisibility(View.GONE);
                    prelimLayout.setVisibility(View.GONE);
                }
                else if(groupPosition == 3 && !"Preliminary Findings".equalsIgnoreCase(headerTitle)){
                    if(dLayout != null)
                        dLayout.setVisibility(View.VISIBLE);
                    if(ageInMonths > 2 && d1Layout != null)
                        d1Layout.setVisibility(View.VISIBLE);

                    aLayout.setVisibility(View.GONE);
                    bLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.GONE);
                    if(d2Layout != null)
                        d2Layout.setVisibility(View.GONE);
                    if(eLayout != null)
                        eLayout.setVisibility(View.GONE);
                    prelimLayout.setVisibility(View.GONE);
                }
                else if(groupPosition == 4 && !"Preliminary Findings".equalsIgnoreCase(headerTitle)){
                    if("D. Autism Specific Questionnaire".equalsIgnoreCase(headerTitle)){
                        if(ageInMonths > 15 && ageInMonths <= 18)
                            d2Layout.setVisibility(View.VISIBLE);
                        else if(ageInMonths > 18 && ageInMonths <= 24)
                            d2Layout.setVisibility(View.VISIBLE);
                    }
                    else{
                        eLayout.setVisibility(View.VISIBLE);
                        if("female".equalsIgnoreCase(gender))
                            e4Layout.setVisibility(View.VISIBLE);
                    }
                    cLayout.setVisibility(View.GONE);
                    bLayout.setVisibility(View.GONE);
                    aLayout.setVisibility(View.GONE);
                    if(dLayout != null)
                        dLayout.setVisibility(View.GONE);
                    if(d1Layout != null)
                        d1Layout.setVisibility(View.GONE);
                    prelimLayout.setVisibility(View.GONE);
                }
                else if("Preliminary Findings".equalsIgnoreCase(headerTitle)){
                    prelimLayout.setVisibility(View.VISIBLE);
                    aPrelimLayout.setVisibility(View.VISIBLE);
                    bPrelimLayout.setVisibility(View.VISIBLE);
                    cPrelimLayout.setVisibility(View.VISIBLE);
                    dPrelimLayout.setVisibility(View.VISIBLE);
                    if(ageInMonths>72) {
                        code14Layout.setVisibility(View.VISIBLE);
                        ePrelimLayout.setVisibility(View.VISIBLE);
                    }
                    setInputValues(ageInMonths, basicView);
                    setPreliminaryCheckedValues(ageInMonths);
                    aLayout.setVisibility(View.GONE);
                    bLayout.setVisibility(View.GONE);
                    cLayout.setVisibility(View.GONE);
                    if(dLayout != null)
                        dLayout.setVisibility(View.GONE);
                    if(d1Layout != null)
                        d1Layout.setVisibility(View.GONE);
                    if(d2Layout != null)
                        d2Layout.setVisibility(View.GONE);
                    if(eLayout != null)
                        eLayout.setVisibility(View.GONE);
                }
            }
            catch (Exception e) {
                Log.e(TAG, "School Healthcare Expandable", e);
            }
            basicView.invalidate();
            return basicView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    private void setInputValues(int ageInMonths, View view){
        try {
            int rga1id = rga1.getCheckedRadioButtonId();
            int rga2id = rga2.getCheckedRadioButtonId();
            int rga3id = rga3.getCheckedRadioButtonId();
            int rga4id = rga4.getCheckedRadioButtonId();
            int rga5id = rga5.getCheckedRadioButtonId();
            int rga6id = rga6.getCheckedRadioButtonId();
            int rga7id = rga7.getCheckedRadioButtonId();
            int rga8id = rga8.getCheckedRadioButtonId();
            int rga9id = rga9.getCheckedRadioButtonId();
            int rga10aid = rga10a.getCheckedRadioButtonId();
            int rga10bid = rga10b.getCheckedRadioButtonId();
            int rga10cid = rga10c.getCheckedRadioButtonId();
            int rga10did = rga10d.getCheckedRadioButtonId();
            int rga10eid = rga10e.getCheckedRadioButtonId();
            int rga11id = rga11.getCheckedRadioButtonId();

            shsFormOneAOne1 = getCheckedValueToAssign(view, rga1id);
            shsFormOneAOne2 = getCheckedValueToAssign(view, rga2id);
            shsFormOneAOne3 = getCheckedValueToAssign(view, rga3id);
            shsFormOneAOne4 = getCheckedValueToAssign(view, rga4id);
            shsFormOneAOne5 = getCheckedValueToAssign(view, rga5id);
            shsFormOneAOne6 = getCheckedValueToAssign(view, rga6id);
            shsFormOneAOne7 = getCheckedValueToAssign(view, rga7id);
            shsFormOneAOne8 = getCheckedValueToAssign(view, rga8id);
            shsFormOneAOne9 = getCheckedValueToAssign(view, rga9id);
            shsFormOneATwo10a = getCheckedValueToAssign(view, rga10aid);
            shsFormOneATwo10b = getCheckedValueToAssign(view, rga10bid);
            shsFormOneATwo10c = getCheckedValueToAssign(view, rga10cid);
            shsFormOneATwo10d = getCheckedValueToAssign(view, rga10did);
            shsFormOneATwo10e = getCheckedValueToAssign(view, rga10eid);
            shsFormOneATwo11 = getCheckedValueToAssign(view, rga11id);

            int rgb1id = rgb1.getCheckedRadioButtonId();
            int rgb2id = rgb2.getCheckedRadioButtonId();
            int rgb3id = rgb3.getCheckedRadioButtonId();
            int rgb4id = rgb4.getCheckedRadioButtonId();
            int rgb5id = rgb5.getCheckedRadioButtonId();

            shsFormOneBOne1 = getCheckedValueToAssign(view, rgb1id);
            shsFormOneBOne2 = getCheckedValueToAssign(view, rgb2id);
            shsFormOneBOne3 = getCheckedValueToAssign(view, rgb3id);
            shsFormOneBOne4 = getCheckedValueToAssign(view, rgb4id);
            shsFormOneBOne5 = getCheckedValueToAssign(view, rgb5id);

            int rgc1id = rgc1.getCheckedRadioButtonId();
            int rgc2id = rgc2.getCheckedRadioButtonId();
            int rgc3id = rgc3.getCheckedRadioButtonId();
            int rgc4id = rgc4.getCheckedRadioButtonId();
            int rgc5id = rgc5.getCheckedRadioButtonId();

            shsFormOneCOne1 = getCheckedValueToAssign(view, rgc1id);
            shsFormOneCOne2 = getCheckedValueToAssign(view, rgc2id);
            shsFormOneCOne3 = getCheckedValueToAssign(view, rgc3id);
            shsFormOneCOne4 = getCheckedValueToAssign(view, rgc4id);
            shsFormOneCOne5 = getCheckedValueToAssign(view, rgc5id);

            if (ageInMonths <= 72) {
                int rgd9_1id = rgd9_1.getCheckedRadioButtonId();

                shsFormOneDNine1 = getCheckedValueToAssign(view, rgd9_1id);

                if (ageInMonths > 2 && ageInMonths <= 4) {
                    int rgd1_1id = rgd1_1.getCheckedRadioButtonId();
                    int rgd1_2id = rgd1_2.getCheckedRadioButtonId();
                    int rgd1_3id = rgd1_3.getCheckedRadioButtonId();
                    int rgd1_4id = rgd1_4.getCheckedRadioButtonId();
                    int rgd1_5id = rgd1_5.getCheckedRadioButtonId();
                    int rgd1_6id = rgd1_6.getCheckedRadioButtonId();
                    int rgd1_7id = rgd1_7.getCheckedRadioButtonId();
                    int rgd1_8id = rgd1_8.getCheckedRadioButtonId();

                    shsFormOneDOne1 = getCheckedValueToAssign(view, rgd1_1id);
                    shsFormOneDOne2 = getCheckedValueToAssign(view, rgd1_2id);
                    shsFormOneDOne3 = getCheckedValueToAssign(view, rgd1_3id);
                    shsFormOneDOne4 = getCheckedValueToAssign(view, rgd1_4id);
                    shsFormOneDOne5 = getCheckedValueToAssign(view, rgd1_5id);
                    shsFormOneDOne6 = getCheckedValueToAssign(view, rgd1_6id);
                    shsFormOneDOne7 = getCheckedValueToAssign(view, rgd1_7id);
                    shsFormOneDOne8 = getCheckedValueToAssign(view, rgd1_8id);

                } else if (ageInMonths > 4 && ageInMonths <= 6) {
                    int rgd2_1id = rgd2_1.getCheckedRadioButtonId();
                    int rgd2_2id = rgd2_2.getCheckedRadioButtonId();
                    int rgd2_3id = rgd2_3.getCheckedRadioButtonId();
                    int rgd2_4id = rgd2_4.getCheckedRadioButtonId();
                    int rgd2_5id = rgd2_5.getCheckedRadioButtonId();
                    int rgd2_6id = rgd2_6.getCheckedRadioButtonId();

                    shsFormOneDTwo1 = getCheckedValueToAssign(view, rgd2_1id);
                    shsFormOneDTwo2 = getCheckedValueToAssign(view, rgd2_2id);
                    shsFormOneDTwo3 = getCheckedValueToAssign(view, rgd2_3id);
                    shsFormOneDTwo4 = getCheckedValueToAssign(view, rgd2_4id);
                    shsFormOneDTwo5 = getCheckedValueToAssign(view, rgd2_5id);
                    shsFormOneDTwo6 = getCheckedValueToAssign(view, rgd2_6id);

                } else if (ageInMonths > 6 && ageInMonths <= 9) {
                    int rgd3_1id = rgd3_1.getCheckedRadioButtonId();
                    int rgd3_2id = rgd3_2.getCheckedRadioButtonId();
                    int rgd3_3id = rgd3_3.getCheckedRadioButtonId();
                    int rgd3_4id = rgd3_4.getCheckedRadioButtonId();
                    int rgd3_5id = rgd3_5.getCheckedRadioButtonId();
                    int rgd3_6id = rgd3_6.getCheckedRadioButtonId();
                    int rgd3_7id = rgd3_7.getCheckedRadioButtonId();

                    shsFormOneDThree1 = getCheckedValueToAssign(view, rgd3_1id);
                    shsFormOneDThree2 = getCheckedValueToAssign(view, rgd3_2id);
                    shsFormOneDThree3 = getCheckedValueToAssign(view, rgd3_3id);
                    shsFormOneDThree4 = getCheckedValueToAssign(view, rgd3_4id);
                    shsFormOneDThree5 = getCheckedValueToAssign(view, rgd3_5id);
                    shsFormOneDThree6 = getCheckedValueToAssign(view, rgd3_6id);
                    shsFormOneDThree7 = getCheckedValueToAssign(view, rgd3_7id);

                } else if (ageInMonths > 9 && ageInMonths <= 12) {
                    int rgd4_1id = rgd4_1.getCheckedRadioButtonId();
                    int rgd4_2id = rgd4_2.getCheckedRadioButtonId();
                    int rgd4_3id = rgd4_3.getCheckedRadioButtonId();
                    int rgd4_4id = rgd4_4.getCheckedRadioButtonId();
                    int rgd4_5id = rgd4_5.getCheckedRadioButtonId();
                    int rgd4_6id = rgd4_6.getCheckedRadioButtonId();
                    shsFormOneDFour1 = getCheckedValueToAssign(view, rgd4_1id);
                    shsFormOneDFour2 = getCheckedValueToAssign(view, rgd4_2id);
                    shsFormOneDFour3 = getCheckedValueToAssign(view, rgd4_3id);
                    shsFormOneDFour4 = getCheckedValueToAssign(view, rgd4_4id);
                    shsFormOneDFour5 = getCheckedValueToAssign(view, rgd4_5id);
                    shsFormOneDFour6 = getCheckedValueToAssign(view, rgd4_6id);

                } else if (ageInMonths > 12 && ageInMonths <= 15) {
                    int rgd5_1id = rgd5_1.getCheckedRadioButtonId();
                    int rgd5_2id = rgd5_2.getCheckedRadioButtonId();
                    int rgd5_3id = rgd5_3.getCheckedRadioButtonId();
                    int rgd5_4id = rgd5_4.getCheckedRadioButtonId();
                    int rgd5_5id = rgd5_5.getCheckedRadioButtonId();
                    int rgd5_6id = rgd5_6.getCheckedRadioButtonId();
                    int rgd5_7id = rgd5_7.getCheckedRadioButtonId();

                    shsFormOneDFive1 = getCheckedValueToAssign(view, rgd5_1id);
                    shsFormOneDFive2 = getCheckedValueToAssign(view, rgd5_2id);
                    shsFormOneDFive3 = getCheckedValueToAssign(view, rgd5_3id);
                    shsFormOneDFive4 = getCheckedValueToAssign(view, rgd5_4id);
                    shsFormOneDFive5 = getCheckedValueToAssign(view, rgd5_5id);
                    shsFormOneDFive6 = getCheckedValueToAssign(view, rgd5_6id);
                    shsFormOneDFive7 = getCheckedValueToAssign(view, rgd5_7id);

                } else if (ageInMonths > 15 && ageInMonths <= 18) {
                    int rgd6_1id = rgd6_1.getCheckedRadioButtonId();
                    int rgd6_2id = rgd6_2.getCheckedRadioButtonId();
                    int rgd6_3id = rgd6_3.getCheckedRadioButtonId();
                    int rgd6_4id = rgd6_4.getCheckedRadioButtonId();
                    int rgd6_5id = rgd6_5.getCheckedRadioButtonId();
                    int rgd6_6id = rgd6_6.getCheckedRadioButtonId();
                    int rgd10_1_1id = rgd10_1_1.getCheckedRadioButtonId();
                    int rgd10_1_2id = rgd10_1_2.getCheckedRadioButtonId();
                    int rgd10_1_3id = rgd10_1_3.getCheckedRadioButtonId();

                    shsFormOneDSix1 = getCheckedValueToAssign(view, rgd6_1id);
                    shsFormOneDSix2 = getCheckedValueToAssign(view, rgd6_2id);
                    shsFormOneDSix3 = getCheckedValueToAssign(view, rgd6_3id);
                    shsFormOneDSix4 = getCheckedValueToAssign(view, rgd6_4id);
                    shsFormOneDSix5 = getCheckedValueToAssign(view, rgd6_5id);
                    shsFormOneDSix6 = getCheckedValueToAssign(view, rgd6_6id);
                    shsFormOneDTenOne1 = getCheckedValueToAssign(view, rgd10_1_1id);
                    shsFormOneDTenOne2 = getCheckedValueToAssign(view, rgd10_1_2id);
                    shsFormOneDTenOne3 = getCheckedValueToAssign(view, rgd10_1_3id);

                } else if (ageInMonths > 18 && ageInMonths <= 24) {
                    int rgd7_1id = rgd7_1.getCheckedRadioButtonId();
                    int rgd7_2id = rgd7_2.getCheckedRadioButtonId();
                    int rgd7_3id = rgd7_3.getCheckedRadioButtonId();
                    int rgd7_4id = rgd7_4.getCheckedRadioButtonId();
                    int rgd7_5id = rgd7_5.getCheckedRadioButtonId();
                    int rgd10_2_1id = rgd10_2_1.getCheckedRadioButtonId();
                    int rgd10_2_2id = rgd10_2_2.getCheckedRadioButtonId();
                    int rgd10_2_3id = rgd10_2_3.getCheckedRadioButtonId();

                    shsFormOneDSeven1 = getCheckedValueToAssign(view, rgd7_1id);
                    shsFormOneDSeven2 = getCheckedValueToAssign(view, rgd7_2id);
                    shsFormOneDSeven3 = getCheckedValueToAssign(view, rgd7_3id);
                    shsFormOneDSeven4 = getCheckedValueToAssign(view, rgd7_4id);
                    shsFormOneDSeven5 = getCheckedValueToAssign(view, rgd7_5id);
                    shsFormOneDTenTwo1 = getCheckedValueToAssign(view, rgd10_2_1id);
                    shsFormOneDTenTwo2 = getCheckedValueToAssign(view, rgd10_2_2id);
                    shsFormOneDTenTwo3 = getCheckedValueToAssign(view, rgd10_2_3id);

                } else if (ageInMonths > 24 && ageInMonths <= 30) {
                    int rgd8_1id = rgd8_1.getCheckedRadioButtonId();
                    int rgd8_2id = rgd8_2.getCheckedRadioButtonId();
                    int rgd8_3id = rgd8_3.getCheckedRadioButtonId();
                    int rgd8_4id = rgd8_4.getCheckedRadioButtonId();
                    int rgd8_5id = rgd8_5.getCheckedRadioButtonId();

                    shsFormOneDEight1 = getCheckedValueToAssign(view, rgd8_1id);
                    shsFormOneDEight2 = getCheckedValueToAssign(view, rgd8_2id);
                    shsFormOneDEight3 = getCheckedValueToAssign(view, rgd8_3id);
                    shsFormOneDEight4 = getCheckedValueToAssign(view, rgd8_4id);
                    shsFormOneDEight5 = getCheckedValueToAssign(view, rgd8_5id);

                } else if (ageInMonths > 30) {
                    int rgd11_1id = rgd11_1.getCheckedRadioButtonId();
                    int rgd11_2id = rgd11_2.getCheckedRadioButtonId();
                    int rgd11_3id = rgd11_3.getCheckedRadioButtonId();
                    int rgd11_4id = rgd11_4.getCheckedRadioButtonId();
                    int rgd11_5id = rgd11_5.getCheckedRadioButtonId();
                    int rgd11_6id = rgd11_6.getCheckedRadioButtonId();
                    int rgd11_7id = rgd11_7.getCheckedRadioButtonId();
                    int rgd11_8id = rgd11_8.getCheckedRadioButtonId();
                    int rgd11_9id = rgd11_9.getCheckedRadioButtonId();
                    int rgd11_10id = rgd11_10.getCheckedRadioButtonId();
                    int rgd11_11id = rgd11_11.getCheckedRadioButtonId();

                    shsFormOneDEleven1 = getCheckedValueToAssign(view, rgd11_1id);
                    shsFormOneDEleven2 = getCheckedValueToAssign(view, rgd11_2id);
                    shsFormOneDEleven3 = getCheckedValueToAssign(view, rgd11_3id);
                    shsFormOneDEleven4 = getCheckedValueToAssign(view, rgd11_4id);
                    shsFormOneDEleven5 = getCheckedValueToAssign(view, rgd11_5id);
                    shsFormOneDEleven6 = getCheckedValueToAssign(view, rgd11_6id);
                    shsFormOneDEleven7 = getCheckedValueToAssign(view, rgd11_7id);
                    shsFormOneDEleven8 = getCheckedValueToAssign(view, rgd11_8id);
                    shsFormOneDEleven9 = getCheckedValueToAssign(view, rgd11_9id);
                    shsFormOneDEleven10 = getCheckedValueToAssign(view, rgd11_10id);
                    shsFormOneDEleven11 = getCheckedValueToAssign(view, rgd11_11id);

                }
            } else if (ageInMonths > 72) {
                int rgc6id = rgc6.getCheckedRadioButtonId();
                int rgd1_1id = rgd1_1.getCheckedRadioButtonId();
                int rgd1_2id = rgd1_2.getCheckedRadioButtonId();
                int rgd1_3id = rgd1_3.getCheckedRadioButtonId();
                int rgd1_4id = rgd1_4.getCheckedRadioButtonId();
                int rgd1_5id = rgd1_5.getCheckedRadioButtonId();
                int rgd1_6id = rgd1_6.getCheckedRadioButtonId();
                int rgd1_7id = rgd1_7.getCheckedRadioButtonId();
                int rgd1_8id = rgd1_8.getCheckedRadioButtonId();
                int rgd1_9id = rgd1_9.getCheckedRadioButtonId();

                shsFormTwoCOne6 = getCheckedValueToAssign(view, rgc6id);
                shsFormTwoDOne1 = getCheckedValueToAssign(view, rgd1_1id);
                shsFormTwoDOne2 = getCheckedValueToAssign(view, rgd1_2id);
                shsFormTwoDOne3 = getCheckedValueToAssign(view, rgd1_3id);
                shsFormTwoDOne4 = getCheckedValueToAssign(view, rgd1_4id);
                shsFormTwoDOne5 = getCheckedValueToAssign(view, rgd1_5id);
                shsFormTwoDOne6 = getCheckedValueToAssign(view, rgd1_6id);
                shsFormTwoDOne7 = getCheckedValueToAssign(view, rgd1_7id);
                shsFormTwoDOne8 = getCheckedValueToAssign(view, rgd1_8id);
                shsFormTwoDOne9 = getCheckedValueToAssign(view, rgd1_9id);

                if (ageInMonths > 120) {
                    int rge1id = rge1.getCheckedRadioButtonId();
                    int rge2id = rge2.getCheckedRadioButtonId();
                    int rge3id = rge3.getCheckedRadioButtonId();
                    int rge4id = rge4.getCheckedRadioButtonId();
                    int rge5id = rge5.getCheckedRadioButtonId();
                    int rge6id = rge6.getCheckedRadioButtonId();
                    int rge7id = rge7.getCheckedRadioButtonId();
                    int rge8id = rge8.getCheckedRadioButtonId();

                    shsFormTwoEOne1 = getCheckedValueToAssign(view, rge1id);
                    shsFormTwoEOne2 = getCheckedValueToAssign(view, rge2id);
                    shsFormTwoEOne3 = getCheckedValueToAssign(view, rge3id);
                    shsFormTwoEOne4 = getCheckedValueToAssign(view, rge4id);
                    shsFormTwoEOne5 = getCheckedValueToAssign(view, rge5id);
                    shsFormTwoEOne6 = getCheckedValueToAssign(view, rge6id);
                    shsFormTwoEOne7 = getCheckedValueToAssign(view, rge7id);
                    shsFormTwoEOne8 = getCheckedValueToAssign(view, rge8id);
                }
            }
            othersSpecify = et30.getText().toString();
        }
        catch (Exception e) {
            Log.e(TAG, "Form check values", e);
        }
    }

    private void setPreliminaryCheckedValues(int ageInMonths) {
        try {
            cb2List = new ArrayList<>();
            cb21List = new ArrayList<>();
            cb22List = new ArrayList<>();
            cb24List = new ArrayList<>();
            cb25List = new ArrayList<>();
            cb26List = new ArrayList<>();
            cb27List = new ArrayList<>();

            if(checkBoxes != null)
                for(CheckBox cb : checkBoxes)
                    cb.setChecked(false);

            if(cb2List != null) {
                cb2List.add(shsFormOneAOne1);
                cb2List.add(shsFormOneAOne6);
                cb2List.add(shsFormOneATwo10a);
                cb2List.add(shsFormOneATwo10b);
                cb2List.add(shsFormOneATwo10c);
                cb2List.add(shsFormOneATwo10d);
                cb2List.add(shsFormOneATwo10e);

                if(cb2List.contains("1"))
                    cbpf2.setChecked(true);
            }

            if("1".equals(shsFormOneAOne5) || "1".equals(shsFormOneATwo11))
                cbpf8.setChecked(true);

            checkBoxCheck("1", shsFormOneAOne9, cbpf1);
            checkBoxCheck("1", shsFormOneAOne4, cbpf3);
            checkBoxCheck("1", shsFormOneAOne8, cbpf4);
            checkBoxCheck("1", shsFormOneAOne7, cbpf5);
            checkBoxCheck("1", shsFormOneAOne2, cbpf6);
            checkBoxCheck("1", shsFormOneAOne3, cbpf7);
            checkBoxCheck("1", shsFormOneAOne2, cbpf9);
            checkBoxCheck("1", shsFormOneCOne4, cbpf15);
            checkBoxCheck("1", shsFormOneCOne2, cbpf16);
            checkBoxCheck("1", shsFormOneCOne3, cbpf19);

            if(ageInMonths <= 72){

                if("1".equals(shsFormOneBOne1) || "1".equals(shsFormOneBOne2))
                    cbpf13.setChecked(true);

                if(cb21List != null) {
                    cb21List.add(shsFormOneDOne6);
                    cb21List.add(shsFormOneDTwo5);
                    cb21List.add(shsFormOneDThree5);
                    cb21List.add(shsFormOneDThree7);
                    cb21List.add(shsFormOneDFour5);

                    if(cb21List.contains("0"))
                        cbpf21.setChecked(true);
                }

                checkBoxCheck("1", shsFormOneBOne3, cbpf10);
                checkBoxCheck("1", shsFormOneBOne4, cbpf11);
                checkBoxCheck("1", shsFormOneBOne5, cbpf12);
                checkBoxCheck("1", shsFormOneCOne5, cbpf18);
                checkBoxCheck("1", shsFormOneCOne1, cbpf20);
                checkBoxCheck("1", shsFormOneDEleven1, cbpf21);

                if(cb22List != null) {
                    cb22List.add(shsFormOneDOne4);
                    cb22List.add(shsFormOneDTwo3);
                    cb22List.add(shsFormOneDThree3);
                    cb22List.add(shsFormOneDFour3);
                    cb22List.add(shsFormOneDFive3);
                    cb22List.add(shsFormOneDSix4);
                    cb22List.add(shsFormOneDSeven5);

                    if(cb22List.contains("0"))
                        cbpf22.setChecked(true);
                }

                checkBoxCheck("1", shsFormOneDEleven9, cbpf22);

                if("1".equals(shsFormOneDEleven4) || "1".equals(shsFormOneDEleven5) || "1".equals(shsFormOneDNine1))
                    cbpf23.setChecked(true);

                if(cb24List != null) {
                    cb24List.add(shsFormOneDOne1);
                    cb24List.add(shsFormOneDOne2);
                    cb24List.add(shsFormOneDOne3);
                    cb24List.add(shsFormOneDTwo1);
                    cb24List.add(shsFormOneDTwo2);
                    cb24List.add(shsFormOneDThree1);
                    cb24List.add(shsFormOneDThree2);
                    cb24List.add(shsFormOneDFour1);
                    cb24List.add(shsFormOneDFour2);
                    cb24List.add(shsFormOneDFive1);
                    cb24List.add(shsFormOneDFive2);
                    cb24List.add(shsFormOneDSix1);
                    cb24List.add(shsFormOneDSix2);
                    cb24List.add(shsFormOneDSix3);
                    cb24List.add(shsFormOneDSeven1);
                    cb24List.add(shsFormOneDSeven2);
                    cb24List.add(shsFormOneDEight1);
                    cb24List.add(shsFormOneDEight2);

                    if(cb24List.contains("0"))
                        cbpf24.setChecked(true);
                }

                if("1".equals(shsFormOneDEleven2) || "1".equals(shsFormOneDEleven3))
                    cbpf24.setChecked(true);

                if(cb25List != null) {
                    cb25List.add(shsFormOneDTwo6);
                    cb25List.add(shsFormOneDThree7);
                    cb25List.add(shsFormOneDFour3);
                    cb25List.add(shsFormOneDFive3);
                    cb25List.add(shsFormOneDFive6);
                    cb25List.add(shsFormOneDFive7);
                    cb25List.add(shsFormOneDSix4);
                    cb25List.add(shsFormOneDSix6);
                    cb25List.add(shsFormOneDSeven4);
                    cb25List.add(shsFormOneDSeven5);
                    cb25List.add(shsFormOneDEight5);

                    if(cb25List.contains("0"))
                        cbpf25.setChecked(true);
                }

                if("1".equals(shsFormOneDEleven6) || "1".equals(shsFormOneDEleven10) || "1".equals(shsFormOneDEleven11))
                    cbpf25.setChecked(true);

                if(cb26List != null) {
                    cb26List.add(shsFormOneDOne5);
                    cb26List.add(shsFormOneDOne8);
                    cb26List.add(shsFormOneDTwo4);
                    cb26List.add(shsFormOneDThree4);
                    cb26List.add(shsFormOneDFour4);
                    cb26List.add(shsFormOneDFive4);
                    cb26List.add(shsFormOneDSix5);
                    cb26List.add(shsFormOneDSeven3);
                    cb26List.add(shsFormOneDEight3);

                    if(cb26List.contains("0"))
                        cbpf26.setChecked(true);
                }

                if("1".equals(shsFormOneDEleven7) || "1".equals(shsFormOneDEleven8))
                    cbpf26.setChecked(true);

                if(cb27List != null) {
                    cb27List.add(shsFormOneDOne7);
                    cb27List.add(shsFormOneDThree6);
                    cb27List.add(shsFormOneDFour6);
                    cb27List.add(shsFormOneDFive5);
                    cb27List.add(shsFormOneDFive6);
                    cb27List.add(shsFormOneDTenOne1);
                    cb27List.add(shsFormOneDTenOne2);
                    cb27List.add(shsFormOneDTenTwo1);
                    cb27List.add(shsFormOneDTenTwo3);
                    cb27List.add(shsFormOneDEight4);

                    if(cb27List.contains("0"))
                        cbpf27.setChecked(true);
                }

                if("1".equals(shsFormOneDTenOne3) || "1".equals(shsFormOneDTenTwo2))
                    cbpf27.setChecked(true);

            }
            else if(ageInMonths > 72){
                checkBoxCheck("1", shsFormOneBOne1, cbpf10);
                checkBoxCheck("1", shsFormOneBOne2, cbpf11);
                checkBoxCheck("1", shsFormOneBOne3, cbpf12);
                checkBoxCheck("1", shsFormOneBOne5, cbpf13);
                checkBoxCheck("1", shsFormOneBOne4, cbpf14);
                checkBoxCheck("1", shsFormOneCOne5, cbpf17);
                checkBoxCheck("1", shsFormTwoCOne6, cbpf18);
                checkBoxCheck("1", shsFormTwoDOne1, cbpf21);
                checkBoxCheck("1", shsFormTwoDOne7, cbpf22);
                checkBoxCheck("1", shsFormTwoDOne3, cbpf23);
                checkBoxCheck("1", shsFormTwoDOne8, cbpf25);
                checkBoxCheck("1", shsFormTwoDOne6, cbpf26);
                checkBoxCheck("1", shsFormTwoDOne9, cbpf29);
                checkBoxCheck("1", shsFormTwoEOne1, cbpf31);
                checkBoxCheck("0", shsFormTwoEOne2, cbpf32);
                checkBoxCheck("1", shsFormTwoEOne3, cbpf33);
                checkBoxCheck("0", shsFormTwoEOne5, cbpf35);
                checkBoxCheck("1", shsFormTwoEOne6, cbpf36);
                checkBoxCheck("1", shsFormTwoEOne7, cbpf37);
                checkBoxCheck("1", shsFormTwoEOne8, cbpf38);

                if("1".equals(shsFormTwoDOne4) || "1".equals(shsFormOneCOne1))
                    cbpf20.setChecked(true);

                if("1".equals(shsFormTwoDOne2) || "1".equals(shsFormTwoDOne3))
                    cbpf24.setChecked(true);

                if("1".equals(shsFormTwoDOne5) || "1".equals(shsFormTwoDOne8))
                    cbpf28.setChecked(true);

                if(ageInMonths>192 && "0".equals(shsFormTwoEOne4))
                    cbpf34.setChecked(true);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Preliminary Form Values", e);
        }
    }

    private void loadLayoutIds(int ageInMonths, View basicView) {
        try {
            cbpf1 = (CheckBox) basicView.findViewById(R.id.check_1);
            cbpf2 = (CheckBox) basicView.findViewById(R.id.check_2);
            cbpf3 = (CheckBox) basicView.findViewById(R.id.check_3);
            cbpf4 = (CheckBox) basicView.findViewById(R.id.check_4);
            cbpf5 = (CheckBox) basicView.findViewById(R.id.check_5);
            cbpf6 = (CheckBox) basicView.findViewById(R.id.check_6);
            cbpf7 = (CheckBox) basicView.findViewById(R.id.check_7);
            cbpf8 = (CheckBox) basicView.findViewById(R.id.check_8);
            cbpf9 = (CheckBox) basicView.findViewById(R.id.check_9);
            cbpf10 = (CheckBox) basicView.findViewById(R.id.check_10);
            cbpf11 = (CheckBox) basicView.findViewById(R.id.check_11);
            cbpf12 = (CheckBox) basicView.findViewById(R.id.check_12);
            cbpf13 = (CheckBox) basicView.findViewById(R.id.check_13);
            cbpf15 = (CheckBox) basicView.findViewById(R.id.check_15);
            cbpf16 = (CheckBox) basicView.findViewById(R.id.check_16);
            cbpf17 = (CheckBox) basicView.findViewById(R.id.check_17);
            cbpf18 = (CheckBox) basicView.findViewById(R.id.check_18);
            cbpf19 = (CheckBox) basicView.findViewById(R.id.check_19);
            cbpf20 = (CheckBox) basicView.findViewById(R.id.check_20);
            cbpf21 = (CheckBox) basicView.findViewById(R.id.check_21);
            cbpf22 = (CheckBox) basicView.findViewById(R.id.check_22);
            cbpf23 = (CheckBox) basicView.findViewById(R.id.check_23);
            cbpf24 = (CheckBox) basicView.findViewById(R.id.check_24);
            cbpf25 = (CheckBox) basicView.findViewById(R.id.check_25);
            cbpf26 = (CheckBox) basicView.findViewById(R.id.check_26);
            cbpf27 = (CheckBox) basicView.findViewById(R.id.check_27);
            cbpf28 = (CheckBox) basicView.findViewById(R.id.check_28);
            cbpf29 = (CheckBox) basicView.findViewById(R.id.check_29);
            et30 = (EditText) basicView.findViewById(R.id.othersText);
            rga1 = (RadioGroup) basicView.findViewById(R.id.a1_rg);
            rga2 = (RadioGroup) basicView.findViewById(R.id.a2_rg);
            rga3 = (RadioGroup) basicView.findViewById(R.id.a3_rg);
            rga4 = (RadioGroup) basicView.findViewById(R.id.a4_rg);
            rga5 = (RadioGroup) basicView.findViewById(R.id.a5_rg);
            rga6 = (RadioGroup) basicView.findViewById(R.id.a6_rg);
            rga7 = (RadioGroup) basicView.findViewById(R.id.a7_rg);
            rga8 = (RadioGroup) basicView.findViewById(R.id.a8_rg);
            rga9 = (RadioGroup) basicView.findViewById(R.id.a9_rg);
            rga10a = (RadioGroup) basicView.findViewById(R.id.a10a_rg);
            rga10b = (RadioGroup) basicView.findViewById(R.id.a10b_rg);
            rga10c = (RadioGroup) basicView.findViewById(R.id.a10c_rg);
            rga10d = (RadioGroup) basicView.findViewById(R.id.a10d_rg);
            rga10e = (RadioGroup) basicView.findViewById(R.id.a10e_rg);
            rga11 = (RadioGroup) basicView.findViewById(R.id.a11_rg);
            if(ageInMonths <= 72){
                rgb1 = (RadioGroup) basicView.findViewById(R.id.b1_rg);
                rgb2 = (RadioGroup) basicView.findViewById(R.id.b2_rg);
                rgb3 = (RadioGroup) basicView.findViewById(R.id.b3_rg);
                rgb4 = (RadioGroup) basicView.findViewById(R.id.b4_rg);
                rgb5 = (RadioGroup) basicView.findViewById(R.id.b5_rg);
                rgc1 = (RadioGroup) basicView.findViewById(R.id.c1_rg);
                rgc2 = (RadioGroup) basicView.findViewById(R.id.c2_rg);
                rgc3 = (RadioGroup) basicView.findViewById(R.id.c3_rg);
                rgc4 = (RadioGroup) basicView.findViewById(R.id.c4_rg);
                rgc5 = (RadioGroup) basicView.findViewById(R.id.c5_rg);
                rgd9_1 = (RadioGroup) basicView.findViewById(R.id.d9_1_rg);
                if(ageInMonths>2 && ageInMonths<=4)
                {
                    rgd1_1 = (RadioGroup) basicView.findViewById(R.id.d1_1_rg);
                    rgd1_2 = (RadioGroup) basicView.findViewById(R.id.d1_2_rg);
                    rgd1_3 = (RadioGroup) basicView.findViewById(R.id.d1_3_rg);
                    rgd1_4 = (RadioGroup) basicView.findViewById(R.id.d1_4_rg);
                    rgd1_5 = (RadioGroup) basicView.findViewById(R.id.d1_5_rg);
                    rgd1_6 = (RadioGroup) basicView.findViewById(R.id.d1_6_rg);
                    rgd1_7 = (RadioGroup) basicView.findViewById(R.id.d1_7_rg);
                    rgd1_8 = (RadioGroup) basicView.findViewById(R.id.d1_8_rg);
                }
                else if(ageInMonths>4 && ageInMonths<=6)
                {
                    rgd2_1 = (RadioGroup) basicView.findViewById(R.id.d2_1_rg);
                    rgd2_2 = (RadioGroup) basicView.findViewById(R.id.d2_2_rg);
                    rgd2_3 = (RadioGroup) basicView.findViewById(R.id.d2_3_rg);
                    rgd2_4 = (RadioGroup) basicView.findViewById(R.id.d2_4_rg);
                    rgd2_5 = (RadioGroup) basicView.findViewById(R.id.d2_5_rg);
                    rgd2_6 = (RadioGroup) basicView.findViewById(R.id.d2_6_rg);
                }
                else if(ageInMonths>6 && ageInMonths<=9)
                {
                    rgd3_1 = (RadioGroup) basicView.findViewById(R.id.d3_1_rg);
                    rgd3_2 = (RadioGroup) basicView.findViewById(R.id.d3_2_rg);
                    rgd3_3 = (RadioGroup) basicView.findViewById(R.id.d3_3_rg);
                    rgd3_4 = (RadioGroup) basicView.findViewById(R.id.d3_4_rg);
                    rgd3_5 = (RadioGroup) basicView.findViewById(R.id.d3_5_rg);
                    rgd3_6 = (RadioGroup) basicView.findViewById(R.id.d3_6_rg);
                    rgd3_7 = (RadioGroup) basicView.findViewById(R.id.d3_7_rg);
                }
                else if(ageInMonths>9 && ageInMonths<=12)
                {
                    rgd4_1 = (RadioGroup) basicView.findViewById(R.id.d4_1_rg);
                    rgd4_2 = (RadioGroup) basicView.findViewById(R.id.d4_2_rg);
                    rgd4_3 = (RadioGroup) basicView.findViewById(R.id.d4_3_rg);
                    rgd4_4 = (RadioGroup) basicView.findViewById(R.id.d4_4_rg);
                    rgd4_5 = (RadioGroup) basicView.findViewById(R.id.d4_5_rg);
                    rgd4_6 = (RadioGroup) basicView.findViewById(R.id.d4_6_rg);
                }
                else if(ageInMonths>12 && ageInMonths<=15)
                {
                    rgd5_1 = (RadioGroup) basicView.findViewById(R.id.d5_1_rg);
                    rgd5_2 = (RadioGroup) basicView.findViewById(R.id.d5_2_rg);
                    rgd5_3 = (RadioGroup) basicView.findViewById(R.id.d5_3_rg);
                    rgd5_4 = (RadioGroup) basicView.findViewById(R.id.d5_4_rg);
                    rgd5_5 = (RadioGroup) basicView.findViewById(R.id.d5_5_rg);
                    rgd5_6 = (RadioGroup) basicView.findViewById(R.id.d5_6_rg);
                    rgd5_7 = (RadioGroup) basicView.findViewById(R.id.d5_7_rg);
                }
                else if(ageInMonths>15 && ageInMonths<=18)
                {
                    rgd6_1 = (RadioGroup) basicView.findViewById(R.id.d6_1_rg);
                    rgd6_2 = (RadioGroup) basicView.findViewById(R.id.d6_2_rg);
                    rgd6_3 = (RadioGroup) basicView.findViewById(R.id.d6_3_rg);
                    rgd6_4 = (RadioGroup) basicView.findViewById(R.id.d6_4_rg);
                    rgd6_5 = (RadioGroup) basicView.findViewById(R.id.d6_5_rg);
                    rgd6_6 = (RadioGroup) basicView.findViewById(R.id.d6_6_rg);
                    rgd10_1_1 = (RadioGroup) basicView.findViewById(R.id.d10_1_1_rg);
                    rgd10_1_2 = (RadioGroup) basicView.findViewById(R.id.d10_1_2_rg);
                    rgd10_1_3 = (RadioGroup) basicView.findViewById(R.id.d10_1_3_rg);
                }
                else if(ageInMonths>18 && ageInMonths<=24)
                {
                    rgd7_1 = (RadioGroup) basicView.findViewById(R.id.d7_1_rg);
                    rgd7_2 = (RadioGroup) basicView.findViewById(R.id.d7_2_rg);
                    rgd7_3 = (RadioGroup) basicView.findViewById(R.id.d7_3_rg);
                    rgd7_4 = (RadioGroup) basicView.findViewById(R.id.d7_4_rg);
                    rgd7_5 = (RadioGroup) basicView.findViewById(R.id.d7_5_rg);
                    rgd10_2_1 = (RadioGroup) basicView.findViewById(R.id.d10_2_1_rg);
                    rgd10_2_2 = (RadioGroup) basicView.findViewById(R.id.d10_2_2_rg);
                    rgd10_2_3 = (RadioGroup) basicView.findViewById(R.id.d10_2_3_rg);
                }
                else if(ageInMonths>24 && ageInMonths<=30)
                {
                    rgd8_1 = (RadioGroup) basicView.findViewById(R.id.d8_1_rg);
                    rgd8_2 = (RadioGroup) basicView.findViewById(R.id.d8_2_rg);
                    rgd8_3 = (RadioGroup) basicView.findViewById(R.id.d8_3_rg);
                    rgd8_4 = (RadioGroup) basicView.findViewById(R.id.d8_4_rg);
                    rgd8_5 = (RadioGroup) basicView.findViewById(R.id.d8_5_rg);
                }
                else if(ageInMonths>30)
                {
                    rgd11_1 = (RadioGroup) basicView.findViewById(R.id.d11_1_rg);
                    rgd11_2 = (RadioGroup) basicView.findViewById(R.id.d11_2_rg);
                    rgd11_3 = (RadioGroup) basicView.findViewById(R.id.d11_3_rg);
                    rgd11_4 = (RadioGroup) basicView.findViewById(R.id.d11_4_rg);
                    rgd11_5 = (RadioGroup) basicView.findViewById(R.id.d11_5_rg);
                    rgd11_6 = (RadioGroup) basicView.findViewById(R.id.d11_6_rg);
                    rgd11_7 = (RadioGroup) basicView.findViewById(R.id.d11_7_rg);
                    rgd11_8 = (RadioGroup) basicView.findViewById(R.id.d11_8_rg);
                    rgd11_9 = (RadioGroup) basicView.findViewById(R.id.d11_9_rg);
                    rgd11_10 = (RadioGroup) basicView.findViewById(R.id.d11_10_rg);
                    rgd11_11 = (RadioGroup) basicView.findViewById(R.id.d11_11_rg);
                }
                checkBoxes = new CheckBox[]{cbpf1, cbpf2, cbpf3, cbpf4, cbpf5, cbpf6, cbpf7, cbpf8, cbpf9, cbpf10, cbpf11, cbpf12, cbpf13, cbpf15, cbpf16, cbpf17,
                        cbpf18, cbpf19, cbpf20, cbpf21, cbpf22, cbpf23, cbpf24, cbpf25, cbpf26, cbpf27, cbpf28, cbpf29};
            }
            else if(ageInMonths <= 216)
            {
                rgb1 = (RadioGroup) basicView.findViewById(R.id.sec_b1_rg);
                rgb2 = (RadioGroup) basicView.findViewById(R.id.sec_b2_rg);
                rgb3 = (RadioGroup) basicView.findViewById(R.id.sec_b3_rg);
                rgb4 = (RadioGroup) basicView.findViewById(R.id.sec_b4_rg);
                rgb5 = (RadioGroup) basicView.findViewById(R.id.sec_b5_rg);
                rgc1 = (RadioGroup) basicView.findViewById(R.id.sec_c1_rg);
                rgc2 = (RadioGroup) basicView.findViewById(R.id.sec_c2_rg);
                rgc3 = (RadioGroup) basicView.findViewById(R.id.sec_c3_rg);
                rgc4 = (RadioGroup) basicView.findViewById(R.id.sec_c4_rg);
                rgc5 = (RadioGroup) basicView.findViewById(R.id.sec_c5_rg);
                rgc6 = (RadioGroup) basicView.findViewById(R.id.sec_c6_rg);
                rgd1_1 = (RadioGroup) basicView.findViewById(R.id.sec_d1_rg);
                rgd1_2 = (RadioGroup) basicView.findViewById(R.id.sec_d2_rg);
                rgd1_3 = (RadioGroup) basicView.findViewById(R.id.sec_d3_rg);
                rgd1_4 = (RadioGroup) basicView.findViewById(R.id.sec_d4_rg);
                rgd1_5 = (RadioGroup) basicView.findViewById(R.id.sec_d5_rg);
                rgd1_6 = (RadioGroup) basicView.findViewById(R.id.sec_d6_rg);
                rgd1_7 = (RadioGroup) basicView.findViewById(R.id.sec_d7_rg);
                rgd1_8 = (RadioGroup) basicView.findViewById(R.id.sec_d8_rg);
                rgd1_9 = (RadioGroup) basicView.findViewById(R.id.sec_d9_rg);
                if(ageInMonths > 120)
                {
                    rge1 = (RadioGroup) basicView.findViewById(R.id.sec_e1_rg);
                    rge2 = (RadioGroup) basicView.findViewById(R.id.sec_e2_rg);
                    rge3 = (RadioGroup) basicView.findViewById(R.id.sec_e3_rg);
                    rge4 = (RadioGroup) basicView.findViewById(R.id.sec_e4_rg);
                    rge5 = (RadioGroup) basicView.findViewById(R.id.sec_e5_rg);
                    rge6 = (RadioGroup) basicView.findViewById(R.id.sec_e6_rg);
                    rge7 = (RadioGroup) basicView.findViewById(R.id.sec_e7_rg);
                    rge8 = (RadioGroup) basicView.findViewById(R.id.sec_e8_rg);
                }
                cbpf14 = (CheckBox) basicView.findViewById(R.id.check_14);
                cbpf31 = (CheckBox) basicView.findViewById(R.id.check_31);
                cbpf32 = (CheckBox) basicView.findViewById(R.id.check_32);
                cbpf33 = (CheckBox) basicView.findViewById(R.id.check_33);
                cbpf34 = (CheckBox) basicView.findViewById(R.id.check_34);
                cbpf35 = (CheckBox) basicView.findViewById(R.id.check_35);
                cbpf36 = (CheckBox) basicView.findViewById(R.id.check_36);
                cbpf37 = (CheckBox) basicView.findViewById(R.id.check_37);
                cbpf38 = (CheckBox) basicView.findViewById(R.id.check_38);
                checkBoxes = new CheckBox[]{cbpf1, cbpf2, cbpf3, cbpf4, cbpf5, cbpf6, cbpf7, cbpf8, cbpf9, cbpf10, cbpf11, cbpf12, cbpf13, cbpf14, cbpf15, cbpf16, cbpf17, cbpf18,
                        cbpf19, cbpf20, cbpf21, cbpf22, cbpf23, cbpf24, cbpf25, cbpf26, cbpf27, cbpf28, cbpf29, cbpf31, cbpf32, cbpf33, cbpf34, cbpf35, cbpf36, cbpf37, cbpf38};
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Check Box Id", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_school_healthcare_screening, menu);
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
                    SchoolHealthcareScreening.this, new ActionCallback() {
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

    private String getShortGender(String gender){
        if("female".equalsIgnoreCase(gender))
            return "f";
        return "m";
    }

    private void checkBoxCheck(String value, String checkBoxValue, CheckBox checkBox) {
        if(value != null && value.equalsIgnoreCase(checkBoxValue))
            checkBox.setChecked(true);
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
}
