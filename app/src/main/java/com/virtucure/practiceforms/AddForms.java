package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.virtucare.practiceforms.dto.FormFieldsDTO;
import com.virtucare.practiceforms.dto.FormNamesDTO;
import com.virtucare.practiceforms.dto.PatientDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddForms extends AppCompatActivity {

    private ListView listView;
    private FormNamesAdapter adapter;
    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/assmntform";
    private List<FormFieldsDTO> formsList = new ArrayList<>();
    private BroadcastReceiver broadcastReceiver = null;
    private String formName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        broadcastReceiver = new BroadcastReceiver() {
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
        registerReceiver(broadcastReceiver, intentFilter);
        setContentView(R.layout.activity_addforms);
        getFormNames();
        listView = (ListView) findViewById(R.id.formnameList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);
                FormFieldsDTO formNameDTO = (FormFieldsDTO) listView.getItemAtPosition(position);
                String formName = formNameDTO.getFormName();
                if("historytaking".equals(formName)){

                    Intent historyTaking =   new Intent(
                            getApplicationContext(),
                            HealthHistory.class);
                    historyTaking.putExtra("formName",formName);
                    historyTaking = populateIntentwithExtras(historyTaking);
                    startActivity(historyTaking);
                }else if("dentalassessment".equals(formName)){

                    Intent dentalAssessment =   new Intent(
                            getApplicationContext(),
                            DentalAssessment.class);
                    dentalAssessment.putExtra("formName",formName);
                    dentalAssessment = populateIntentwithExtras(dentalAssessment);
                    startActivity(dentalAssessment);

                }else if("generalhealthscreening".equals(formName)){

                    Intent generalHealth =   new Intent(
                            getApplicationContext(),
                            GeneralHealthScreening.class);
                    generalHealth.putExtra("formName",formName);
                    generalHealth = populateIntentwithExtras(generalHealth);
                    startActivity(generalHealth);
                }
            }
        });
    }

/*    @Override
    protected void onStop(){
        if(broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
        super.onStop();;
    }*/

/*    @Override
    public void onBackPressed() {
        Intent patientDetail = new Intent(getApplicationContext(), PatientDetail.class);
        patientDetail.putExtra("regid",getIntent().getExtras().getString("regid"));
        patientDetail.putExtra("name" ,getIntent().getExtras().getString("name"));
        patientDetail.putExtra("phone",getIntent().getExtras().getString("phone"));
        patientDetail.putExtra("proof",getIntent().getExtras().getString("proof"));
        patientDetail.putExtra("dob",getIntent().getExtras().getString("dob"));
        patientDetail.putExtra("regLinkId",getIntent().getExtras().getString("regLinkId"));
        startActivity(patientDetail);
        finish();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addforms, menu);
        return true;
    }

    public Intent populateIntentwithExtras(Intent intent){
        intent.putExtra("caseid",getIntent().getExtras().getString("caseid"));
        intent.putExtra("regid",getIntent().getExtras().getString("regid"));
        intent.putExtra("name" ,getIntent().getExtras().getString("name"));
        intent.putExtra("regLinkId", getIntent().getExtras().getString("regLinkId"));
        intent.putExtra("proof", getIntent().getExtras().getString("proof"));
        intent.putExtra("dob", getIntent().getExtras().getString("dob"));
        intent.putExtra("phone", getIntent().getExtras().getString("phone"));
        return  intent;
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
                    AddForms.this, new ActionCallback() {
                public void onCallback(Map<String, String> result) {
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                    sendBroadcast(broadcastIntent);
                }

            }, "Logging off");

            worker.execute(nameValues);
        }

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void getFormNames() {
        Map<String,String> nameValues = new HashMap<String,String>();

        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "7");

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                AddForms.this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (result.get("result") != null && !result.isEmpty() ) {

                    try {
                        Gson gson = new Gson();
                        FormNamesDTO formNamesDTO = gson.fromJson(result.get("result"), FormNamesDTO.class);
                        if(formNamesDTO != null && formNamesDTO.getFormNamesList() != null
                                && formNamesDTO.getFormNamesList().size()>0 ) {
                            adapter = new FormNamesAdapter(AddForms.this, formNamesDTO.getFormNamesList());
                        }
                        listView.setAdapter(adapter);
                    }
                    catch (Exception e){

                    }
                } else {
                    new AlertDialog.Builder(AddForms.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Retrieving Forms");
        worker.execute(nameValues);
    }
    
}
