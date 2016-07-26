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
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtucare.practiceforms.dto.ReferredPatientsDTO;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 6/28/2016.
 */
public class ReferredPatientAdministrationsActivity extends AppCompatActivity {

    private BroadcastReceiver logoutReceiver;
    private Toolbar toolbar;
    private static final String TAG = ReferredPatientAdministrationsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ReferredPatientsAdministrationsAdapter adapter;
    private List<ReferredPatientsDTO> referredPatientsList;
    private TextView emptyRecordsTextView;
    private Context context = this;

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

        getAdministrationsList();
        setContentView(R.layout.activity_referred_patient_administrations_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        emptyRecordsTextView = (TextView) findViewById(R.id.emptyRecords);
        recyclerView = (RecyclerView) findViewById(R.id.administrations_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getAdministrationsList() {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getReferredPatientDetails";

        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "13");

        Map<String, String> params = new HashMap<>();
        params.put("requiredType", "project");

        String json = new Gson().toJson(params);
        nameValues.put("retrieveparams", json);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))) {
                            referredPatientsList = new Gson().fromJson(jsonObject.getString("result"), new TypeToken<List<ReferredPatientsDTO>>(){}.getType());
                            if(referredPatientsList != null && referredPatientsList.size() > 0) {
                                adapter = new ReferredPatientsAdministrationsAdapter(context, referredPatientsList, new OnRecyclerItemClickListener() {
                                    @Override
                                    public void onRecyclerItemClick(Object obj) {
                                        ReferredPatientsDTO item = (ReferredPatientsDTO)obj;
                                        Intent intent = new Intent(getApplicationContext(), ReferredPatientBranchesActivity.class);
                                        intent.putExtra("projectType", item.getProjectType());
                                        startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            else if(emptyRecordsTextView != null){
                                emptyRecordsTextView.setVisibility(View.VISIBLE);
                            }
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
        }, "Retrieving Administrations");
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
        getMenuInflater().inflate(R.menu.menu_referred_administrators, menu);
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
                    ReferredPatientAdministrationsActivity.this, new ActionCallback() {
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
