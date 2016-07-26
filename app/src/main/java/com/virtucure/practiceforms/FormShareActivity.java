package com.virtucure.practiceforms;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by AJITI on 6/8/2016.
 */
public class FormShareActivity extends AppCompatActivity {

    private BroadcastReceiver logoutReceiver;
    private Toolbar toolbar;
    private String regLinkId;
    private String proofType;
    private String proofNumber;
    private String healthRegistrationId;
    private String patientName;
    private String caseRecordNo;
    private Map<String, String> cidMap = null;
    private String ccString = "";
    private Context context = this;
    private String sharedTo;
    private boolean isEmptyGhid;
    private boolean isEmptyProof;
    private MultiAutoCompleteTextView inputCidTxtView;
    private String[] cids;
    private List<String> tempList;
    private String details;
    private  Map<String,String> m;
    private List<FormshareDetailsDTO> cidList;
    private List<String> originalList;
    ImageView addCidBtn;
    ArrayAdapter adapter;
    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getVerifiedAccountStatus";
    private String serverShareUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/sharedrecordinsert";
    private String serverCidUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getAdminmemberIddata";

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
        setContentView(R.layout.form_share_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        healthRegistrationId = getIntent().getExtras().getString("healthRegistrationId");
        regLinkId = getIntent().getExtras().getString("regnLinkId");
        proofType = getIntent().getExtras().getString("proofType");
        proofNumber = getIntent().getExtras().getString("proofNumber");
        patientName = getIntent().getExtras().getString("patientName");
        caseRecordNo = getIntent().getExtras().getString("caseRecordNo");

        tempList = new ArrayList<>();
        final List<Map<String, String>> ccList = new ArrayList<>();
        final TextView ccListTxtView = (TextView) findViewById(R.id.cclistdata);
//        final EditText cidTxt = (EditText) findViewById(R.id.cclist);
        final EditText descTxt = (EditText) findViewById(R.id.desc);
        Button sendBtn = (Button) findViewById(R.id.sendShareBtn);
        addCidBtn = (ImageView) findViewById(R.id.addCidBtn);
        final TextView patientNameTxtView = (TextView) findViewById(R.id.patientName);
        TextView hridTxtView = (TextView) findViewById(R.id.hrid);
        TextView caseRecordTxtView = (TextView) findViewById(R.id.crno);
        patientNameTxtView.setText(patientName);
        hridTxtView.setText(healthRegistrationId);
        caseRecordTxtView.setText(caseRecordNo);
        final CheckBox ghidcb = (CheckBox) findViewById(R.id.ghidcb);
        final CheckBox proofcb = (CheckBox) findViewById(R.id.proofcb);
        if(regLinkId==null || "".equals(regLinkId)){
            ghidcb.setText("No GHID Found");
            isEmptyGhid = true;
        }
        if(proofNumber==null || "".equals(proofNumber)){
            proofcb.setText("No Proof Found");
            isEmptyProof = true;
        }
        if(!isEmptyGhid || !isEmptyProof){
            getAccountVerificationStatus(ghidcb, proofcb);
            getAdminMemberIds();
        }

        addCidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cids = inputCidTxtView.getText().toString().trim().split(",");
                for (String cid : cids) {
                    cid = cid.trim();
                    if (cid != null && !cid.isEmpty() && !tempList.contains(cid) && m.get(cid) != null) {
                        cidMap = new HashMap<>();
                        cidMap.put("userName", "");
                        cidMap.put("cid", m.get(cid));
                        tempList.add(cid);
                        ccList.add(cidMap);

                        if(ccString.length() > 0)
                            ccString = ccString + "; " + m.get(cid);
                        else ccString = m.get(cid);

                        ccListTxtView.setText(ccString);
                    }
                }
                inputCidTxtView.setText("");
                inputCidTxtView.requestFocus();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ghidcb.isChecked() && !proofcb.isChecked()){
                    new AlertDialog.Builder(context).setTitle("Alert").setMessage("No GHID / Proof Found to share")
                            .setCancelable(false).setPositiveButton(R.string.ok, null).show();
                    return;
                }
                if(!inputCidTxtView.getText().toString().isEmpty())
                {
                    performButtonClick(addCidBtn);
/*                    new AlertDialog.Builder(context).setTitle("Alert").setMessage("Add ID to CC-List")
                            .setCancelable(false).setPositiveButton(R.string.ok, null).show();
                    return;*/
                }
                String shareDescription = descTxt.getText().toString();
                Map<String, Object> shareParams = new HashMap<String, Object>();
                shareParams.put("caseRecordNo", caseRecordNo);
                shareParams.put("cc", ccList);
                shareParams.put("formIdArray", getIntent().getStringArrayListExtra("formIdArray"));
                shareParams.put("freeTempFormText", shareDescription);
                shareParams.put("hrid", healthRegistrationId);
                shareParams.put("patientName", patientName);
                shareParams.put("sharedTo", sharedTo);

                final String jsonShareParams = new Gson().toJson(shareParams);

                new AlertDialog.Builder(context).setTitle("Alert")
                        .setMessage("Do you want to share record").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        shareForms(jsonShareParams, context);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
    }

    private void getAccountVerificationStatus(final CheckBox ghidcb, final CheckBox proofcb) {
        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "9");
        nameValues.put("healthRegistrationId", healthRegistrationId);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                try{
                    if (result.get("result") != null && !result.isEmpty() ) {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))){
/*                            String mcid = jsonObject.getString("mCid");
                            if(ghid.equalsIgnoreCase(mcid));*/
                            ghidcb.setChecked(true);
                            ghidcb.setText(regLinkId + " ( Verified )");
                            ghidcb.setTextColor(Color.GREEN);
                            proofcb.setVisibility(View.GONE);
                            sharedTo = regLinkId;
                        } else {
                            checkGhidProof(ghidcb, proofcb);
                        }
                    }
                    else {
                        checkGhidProof(ghidcb, proofcb);
                    }
                }
                catch (Exception e) {
                    Log.d("Form Share", e.getLocalizedMessage());
                }
            }
        }, "Verifying Patient");
        worker.execute(nameValues);
    }

    private void shareForms(String jsonShareParams, final Context ctx) {
        Map<String, String> params = new HashMap<>();
        params.put("serverUrl", serverShareUrl);
        params.put("operationType", "10");
        params.put("insertparams", jsonShareParams);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                FormShareActivity.this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    JSONObject resultJson = null;
                    try {
                        resultJson = new JSONObject(result.get("result"));

                        if (resultJson != null && "success".equalsIgnoreCase(resultJson.getString("status"))) {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("Form Shared Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getApplicationContext(),
                                            CaseRecordFormsActivity.class);
                                    caseRecordsActivity.putExtra("regid", healthRegistrationId);
                                    caseRecordsActivity.putExtra("name", patientName);
                                    caseRecordsActivity.putExtra("phone", getIntent().getExtras().getString("phone"));
                                    caseRecordsActivity.putExtra("email", getIntent().getExtras().getString("email"));
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", getIntent().getExtras().getString("gender"));
                                    caseRecordsActivity.putExtra("dob", getIntent().getExtras().getString("dob"));
                                    caseRecordsActivity.putExtra("reqLinkId", regLinkId);
                                    caseRecordsActivity.putExtra("caserecordno", caseRecordNo);
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
                    new AlertDialog.Builder(FormShareActivity.this)
                            .setTitle("Error")
                            .setMessage("Failed to share record")
                            .show();
                }
            }
        }, "Sharing Form");
        worker.execute(params);
    }

    private void checkGhidProof(CheckBox ghidcb, CheckBox proofcb){
        if(!isEmptyGhid){
            ghidcb.setText(regLinkId + " ( Not Verified)");
            ghidcb.setTextColor(Color.RED);
        }
        if(!isEmptyProof) {
            proofcb.setChecked(true);
            proofcb.setText(proofNumber + " - " + proofType);
            sharedTo = proofNumber;
        }
    }

    private void getAdminMemberIds() {
        Map<String, String> params= new HashMap<>();
        params.put("serverUrl", serverCidUrl);
        params.put("operationType", "12");
        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(FormShareActivity.this, new ActionCallback() {
            public void onCallback(Map<String, String> result){
                if (result.get("result") != null && !result.isEmpty() ) {
                    try{
                        FormshareDTO formshareDTO = new Gson().fromJson(result.get("result"), FormshareDTO.class);
                        cidList = new ArrayList<>();
                        originalList = new ArrayList<>();
                        if(formshareDTO  != null && formshareDTO .getCidList() != null
                                && formshareDTO.getCidList().size()>0 ) {
                            cidList= formshareDTO.getCidList();
                            m = new ConcurrentHashMap<>();
                            for(FormshareDetailsDTO dto : cidList){
                                details=dto.getUserName()+"-"+dto.getcId();
                                originalList.add(details);
                                m.put(details, dto.getcId());
                            }
                        }
                        inputCidTxtView=(MultiAutoCompleteTextView)findViewById(R.id.cclist);
                        adapter = new CustomAdapter(getApplicationContext(), R.layout.spinner_item, R.id.multi,originalList);
                        inputCidTxtView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                        inputCidTxtView.setAdapter(adapter);
                        inputCidTxtView.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if(keyCode == KeyEvent.KEYCODE_ENTER && KeyEvent.ACTION_DOWN == event.getAction()) {
                                    performButtonClick(addCidBtn);
                                    return true;
                                }
                                return false;
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog.Builder(FormShareActivity.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }

        },"Retrieving IDs");

        worker.execute(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formshare, menu);
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
                    FormShareActivity.this, new ActionCallback() {
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

    private void performButtonClick(ImageView btn) {
        btn.performClick();
        btn.setPressed(true);
        btn.invalidate();
        btn.setPressed(false);
        btn.invalidate();
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

}
