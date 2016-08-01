package com.virtucure.practiceforms;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AJITI on 20-Jul-16.
 */
public class CaseRecordFormsMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentTransaction transaction;
    private BroadcastReceiver logoutReceiver;
    private static final String TAG = CaseRecordFormsMainActivity.class.getSimpleName();

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

        setContentView(R.layout.case_record_forms_frame_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setUpNavDrawerBody(navigationView);

        if(savedInstanceState == null){
            selectDrawerItem(navigationView.getMenu().findItem(R.id.nav_case_record));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavDrawerBody(NavigationView navView) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Intent intent = null;
        Bundle args;
        switch (item.getItemId()) {
            case R.id.nav_home :
                intent = new Intent(this, Home.class);
                break;

            case R.id.nav_case_record :
                fragment = new CaseRecordFormsFragment();
                args = new Bundle();
                args.putCharSequence("gender", getIntent().getExtras().getString("gender"));
                args.putCharSequence("email", getIntent().getExtras().getString("email"));
                args.putCharSequence("dob", getIntent().getExtras().getString("dob"));
                args.putCharSequence("phone", getIntent().getExtras().getString("phone"));
                args.putCharSequence("caserecordno", getIntent().getExtras().getString("caserecordno"));
                args.putCharSequence("regid", getIntent().getExtras().getString("regid"));
                args.putCharSequence("name", getIntent().getExtras().getString("name"));
                args.putCharSequence("regLinkId", getIntent().getExtras().getString("regLinkId"));
                args.putCharSequence("proofType", getIntent().getExtras().getString("proofType"));
                args.putCharSequence("proofNumber", getIntent().getExtras().getString("proofNumber"));
                fragment.setArguments(args);
                break;

            case R.id.nav_inbox :
                intent = new Intent(this, ReferredPatientAdministrationsActivity.class);
                break;

            case R.id.nav_sent :
                intent = new Intent(this, SharedRecordDetailsActivity.class);
                break;

            case R.id.nav_logout :
                String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/logout";
                Map<String, String> nameValues = new HashMap<String, String>();
                nameValues.put("serverUrl", serverUrl);
                nameValues.put("operationType", "6");

                AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                        this, new ActionCallback() {
                    public void onCallback(Map<String, String> result) {
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        sendBroadcast(broadcastIntent);
                    }
                }, "Logging off");
                worker.execute(nameValues);
                break;

            default:
//                className = RecyclerViewHomeFragment.class;
                break;
        }
        try {
            if(!item.isChecked()) {
                if(R.id.nav_case_record == item.getItemId()) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.view_fragment, fragment).commit();
                    item.setChecked(true);
                } else if(intent != null) {
                    startActivity(intent);
                    finish();
                }
            }
            drawerLayout.closeDrawers();
        } catch (Exception e) {
            Log.e(TAG, "Drawer Item Menu", e);
        }
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            Intent patientDetail = new Intent(getApplicationContext(), PatientDetail.class);
            patientDetail.putExtra("regid", getIntent().getExtras().getString("regid"));
            patientDetail.putExtra("name", getIntent().getExtras().getString("name"));
            patientDetail.putExtra("phone", getIntent().getExtras().getString("phone"));
            patientDetail.putExtra("email", getIntent().getExtras().getString("email"));
            patientDetail.putExtra("proofType", getIntent().getExtras().getString("proofType"));
            patientDetail.putExtra("proofNumber", getIntent().getExtras().getString("proofNumber"));
            patientDetail.putExtra("dob", getIntent().getExtras().getString("dob"));
            patientDetail.putExtra("gender", getIntent().getExtras().getString("gender"));
            patientDetail.putExtra("regLinkId", getIntent().getExtras().getString("regLinkId"));
            startActivity(patientDetail);
            finish();
        }
    }
}
