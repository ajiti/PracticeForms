package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.virtucare.practiceforms.dto.SharedCaseRecordsDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 15-Jul-16.
 */
public class SharedRecordDetailsActivity extends AppCompatActivity{

    private static final String TAG = SharedRecordDetailsActivity.class.getSimpleName();
    private Toolbar toolbar;
    private BroadcastReceiver logoutReceiver;
    private RecyclerView recyclerView;
    private TextView emptyRecordsView;
    private SharedRecordDetailsAdapter adapter;
    private List<SharedCaseRecordsDTO> sharedRecords;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

/*        setContentView(R.layout.frame_layout);
        if(savedInstanceState == null) {
            SharedRecordDetailsFragment fragment = new SharedRecordDetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, fragment).commit();
        }*/

        getSharedRecords();
        setContentView(R.layout.activity_referred_patient_administrations_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        emptyRecordsView = (TextView) findViewById(R.id.emptyRecords);
        emptyRecordsView.setText(getResources().getString(R.string.info_share_empty_records));
        emptyRecordsView.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.administrations_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getSharedRecords() {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getSharedRecords";

        final Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "14");

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        sharedRecords = new Gson().fromJson(result.get("result"), new TypeToken<List<SharedCaseRecordsDTO>>(){}.getType());
                        if(sharedRecords != null && !sharedRecords.isEmpty()) {
                            emptyRecordsView.setVisibility(View.GONE);
                            adapter = new SharedRecordDetailsAdapter(context, sharedRecords);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("Failed To Get Records")
                            .show();
                }
            }
        }, "Retrieving Shared Records");
        worker.execute(nameValues);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/logout";
            Map<String, String> nameValues = new HashMap<>();
            nameValues.put("serverUrl", serverUrl);
            nameValues.put("operationType", "6");

            AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                    SharedRecordDetailsActivity.this, new ActionCallback() {
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

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }
}
