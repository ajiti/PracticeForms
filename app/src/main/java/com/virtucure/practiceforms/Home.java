package com.virtucure.practiceforms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    private ListView menuList;
    private BroadcastReceiver logoutReceiver;
    private Toolbar toolbar;

    String[] itemname ={
            "Register Patient",
            "View Patients",
            "Referred Patients",
            "Shared Records"
    };

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
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        menuList = (ListView) findViewById(R.id.menuList);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,R.layout.menulist, R.id.Itemname, itemname);
        menuList.setAdapter(listAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent registration = new Intent(
                                getApplicationContext(),
                                Registration.class);
                        registration.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(registration);
                        break;

                    case 1:
                        Intent viewactivity = new Intent(
                                getApplicationContext(),
                                viewpatients.class);
                        startActivity(viewactivity);
                        break;

                    case 2:
                        Intent referredActivity = new Intent(
                                getApplicationContext(),
                                ReferredPatientAdministrationsActivity.class);
                        startActivity(referredActivity);
                        break;

                    case 3:
                        Intent sharedRecordActivity = new Intent(getApplicationContext(), SharedRecordDetailsActivity.class);
                        startActivity(sharedRecordActivity);
                        break;
                }
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && KeyEvent.ACTION_DOWN == event.getAction()) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/logout";
            Map<String, String> nameValues = new HashMap<String, String>();
            nameValues.put("serverUrl", serverUrl);
            nameValues.put("operationType", "6");

            AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                    Home.this, new ActionCallback() {
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
