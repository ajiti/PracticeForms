package com.virtucure.practiceforms;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtucare.practiceforms.dto.ReferredPatientHridCaseRecordDTO;
import com.virtucare.practiceforms.dto.ReferredPatientHridDTO;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientCaseRecordsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BroadcastReceiver logoutReceiver;
    private LinearLayoutManager linearLayoutManager;
    private List<ReferredPatientHridCaseRecordDTO> referredCaseRecordsList;
    private ReferredPatientsCaseRecordsAdapter adapter;
    private int pageNumber = 0;
    private int pageSize = 1000;

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
        String projectType = getIntent().getExtras().getString("projectType");
        String brandorBranchName = getIntent().getExtras().getString("brandorBranchName");
        String hrid = getIntent().getExtras().getString("hrid");
        setContentView(R.layout.activity_referred_patient_administrations_list);
        getHridsList(projectType, brandorBranchName, hrid);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        recyclerView = (RecyclerView) findViewById(R.id.administrations_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getHridsList(final String projectType, final String brandorBranchName, final String hrid) {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getReferredPatientDetails";

        Map<String,String> nameValues = new HashMap<String,String>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "13");

        Map<String, String> params = new HashMap<>();
        params.put("requiredType", "caseRecord");
        params.put("hrid", hrid);
        params.put("brandorBranchName", brandorBranchName);
        params.put("projectType", projectType);
        params.put("pageNumber", pageNumber+"");
        params.put("pageSize", pageSize+"");

        String json = new Gson().toJson(params);
        nameValues.put("retrieveparams", json);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))) {
                            referredCaseRecordsList = new Gson().fromJson(jsonObject.getString("result"), new TypeToken<List<ReferredPatientHridCaseRecordDTO>>(){}.getType());
                            adapter = new ReferredPatientsCaseRecordsAdapter(referredCaseRecordsList, new OnRecyclerItemClickListener() {
                                @Override
                                public void onRecyclerItemClick(Object obj) {
                                    ReferredPatientHridCaseRecordDTO item = (ReferredPatientHridCaseRecordDTO) obj;
                                    Intent intent = new Intent(getApplicationContext(), ReferredPatientReferredTimeActivity.class);
                                    intent.putExtra("projectType", projectType);
                                    intent.putExtra("brandorBranchName", brandorBranchName);
                                    intent.putExtra("hrid", hrid);
                                    intent.putExtra("caseRecordNo", item.getCaseRecordNo());
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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
        }, "Retrieving Case Records");
        worker.execute(nameValues);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_referred_branches, menu);
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
                    ReferredPatientCaseRecordsActivity.this, new ActionCallback() {
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
