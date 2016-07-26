package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.virtucare.practiceforms.dto.CaseIdDTO;
import com.virtucare.practiceforms.dto.FormFieldsDTO;
import com.virtucare.practiceforms.dto.FormNamesDTO;
import com.virtucare.practiceforms.dto.PatientDTO;
import com.virtucare.practiceforms.dto.PatientsListDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PatientDetail extends AppCompatActivity implements GLSurfaceView.Renderer {

    private Toolbar toolbar;
    private ListView listView;
    private CaseIdAdaptor adapter;
    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/viewhridinfo";
    private BroadcastReceiver logoutReceiver = null;
    private String patientDob;
    private String patientGender;

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
        setContentView(R.layout.activity_patient_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        TextView name = (TextView) findViewById(R.id.name1);
        name.setText(getIntent().getExtras().getString("name"));
        TextView mobile_email = (TextView) findViewById(R.id.phone_email1);
        mobile_email.setText(getIntent().getExtras().getString("phone")+"/"+getIntent().getExtras().getString("email"));
        TextView dob_gender = (TextView) findViewById(R.id.dobgender1);
        patientDob = getIntent().getExtras().getString("dob");
        patientGender = getIntent().getExtras().getString("gender");
        dob_gender.setText(patientDob + "/" + patientGender);
        TextView proof = (TextView) findViewById(R.id.proof1);
        proof.setText(getIntent().getExtras().getString("proofType")+":"+getIntent().getExtras().getString("proofNumber"));
        Button b = (Button) findViewById(R.id.addfrm1);
        listView = (ListView) findViewById(R.id.caseidlist);
        TextView caseRecordTitle = (TextView) findViewById(R.id.caserecordsttl);
        caseRecordTitle.setPaintFlags(caseRecordTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        getPatientsData();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newcase = new Intent(
                        getApplicationContext(),
                        HealthHistory.class);
                newcase.putExtra("caseid","");
                newcase.putExtra("formName","historytaking");
                newcase.putExtra("regid",getIntent().getExtras().getString("regid"));
                newcase.putExtra("name" ,getIntent().getExtras().getString("name"));
                newcase.putExtra("regLinkId", getIntent().getExtras().getString("regLinkId"));
                newcase.putExtra("proofType", getIntent().getExtras().getString("proofType"));
                newcase.putExtra("proofNumber", getIntent().getExtras().getString("proofNumber"));
                newcase.putExtra("dob", getIntent().getExtras().getString("dob"));
                newcase.putExtra("gender", getIntent().getExtras().getString("gender"));
                newcase.putExtra("phone", getIntent().getExtras().getString("phone"));
                newcase.putExtra("email", getIntent().getExtras().getString("email"));
                startActivity(newcase);
            }
        });

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), viewpatients.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient_detail, menu);
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
                    PatientDetail.this, new ActionCallback() {
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

    private void getPatientsData() {
        Map<String,String> nameValues = new HashMap<String,String>();

        nameValues.put("serverUrl",serverUrl);
        nameValues.put("operationType","4");
        nameValues.put("regid", getIntent().getExtras().getString("regid"));

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                PatientDetail.this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (result.get("result") != null && !result.isEmpty() ) {
                    try {
                        PatientsListDTO patientsListDTO = new Gson().fromJson(result.get("result"), PatientsListDTO.class);
                        TextView emptyTxtView = (TextView) findViewById(R.id.noCaseRecords);
                        if(patientsListDTO != null && patientsListDTO.getHealthRegistrationList() != null
                                && patientsListDTO.getHealthRegistrationList().size()>0 ) {
                            if(patientsListDTO.getHealthRegistrationList().get(0).getCaseIdsLIst().size() > 0) {
                                emptyTxtView.setVisibility(View.GONE);
                                PatientDTO patientDTO = patientsListDTO.getHealthRegistrationList().get(0);
                                patientDTO.setRegId(getIntent().getExtras().getString("regid"));
                                //adapter = new CaseIdAdaptor(PatientDetail.this, patientsListDTO.getHealthRegistrationList().get(0).getCaseIdsLIst());
                                adapter = new CaseIdAdaptor(PatientDetail.this, patientDTO);
                            }
                            else emptyTxtView.setVisibility(View.VISIBLE);
                            listView.setAdapter(adapter);
                        }
                    }
                    catch (Exception e){

                    }
                } else {
                    new AlertDialog.Builder(PatientDetail.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Retrieving Case Records");
        worker.execute(nameValues);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

/*    @Override
    public void onResume(){
        super.onResume();
        listView.setAdapter(null);
        getPatientsData();
        adapter.notifyDataSetChanged();
        finish();
    }*/

}
