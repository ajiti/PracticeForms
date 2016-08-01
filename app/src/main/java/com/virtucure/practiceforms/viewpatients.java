package com.virtucure.practiceforms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.virtucare.practiceforms.dto.PatientDTO;
import com.virtucare.practiceforms.dto.PatientsListDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewpatients extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    private PatientsAdaptor adapter;
    public static int width = 0;
    private String current_page="1";
    private String page_size = "1000";
    private String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/view";
    private BroadcastReceiver logoutReceiver;
    private TextView emptyTxtView;
    private ArrayList<PatientDTO> serverSidePatientList;
    private ArrayList<PatientDTO> searchList;
    private List<String> concatedPatientList;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_viewpatients);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        emptyTxtView = (TextView) findViewById(R.id.noPatientRecords);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList = new ArrayList<>();
                if (newText.length() != 0 && newText.toString().length() > 4) {
                    for (int i = 0; concatedPatientList != null && i < concatedPatientList.size(); i++) {
                        if (concatedPatientList.get(i).toLowerCase().contains(newText.toString().toLowerCase())) {
                            searchList.add(serverSidePatientList.get(i));
                        }
                    }
                    emptyTxtView.setVisibility(searchList.isEmpty() ? View.VISIBLE : View.GONE);
                    adapter = new PatientsAdaptor(viewpatients.this, searchList);
                } else {
                    emptyTxtView.setVisibility((serverSidePatientList == null || serverSidePatientList.isEmpty()) ? View.VISIBLE : View.GONE);
                    adapter = new PatientsAdaptor(viewpatients.this, serverSidePatientList);
                }
                toggleListViewScrollbar(emptyTxtView.getVisibility() == View.VISIBLE ? Boolean.FALSE : Boolean.TRUE);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        searchView.setVoiceSearch(false);
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//        searchView.setCursorDrawable(R.drawable.custom_cursor);
        listView = (ListView) findViewById(R.id.patients_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatientDTO patientDTO = (PatientDTO) listView.getItemAtPosition(position);
                Intent patientDetail = new Intent(
                        getApplicationContext(),
                        PatientDetail.class);
                patientDetail.putExtra("regid", patientDTO.getRegId());
                patientDetail.putExtra("name", patientDTO.getFirstName() + " " + patientDTO.getLastName());
                patientDetail.putExtra("phone", patientDTO.getMobileNo());
                patientDetail.putExtra("email", patientDTO.getEmailId());
                patientDetail.putExtra("proofType", patientDTO.getProofType());
                patientDetail.putExtra("proofNumber", patientDTO.getProofNumber());
                patientDetail.putExtra("dob", patientDTO.getDob());
                patientDetail.putExtra("gender", patientDTO.getGender());
                patientDetail.putExtra("regLinkId", patientDTO.getSearchCid());
                startActivity(patientDetail);
            }
        });

        getPatientsData();
    }

    private void getPatientsData() {
        Map<String,String> nameValues = new HashMap<String,String>();

        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "2");

        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", current_page);
        params.put("pageSize", page_size);

        String json = new Gson().toJson(params);
        nameValues.put("retrieveparams", json);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                viewpatients.this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        TextView emptyTxtView = (TextView) findViewById(R.id.noPatientRecords);
                        PatientsListDTO patientsListDTO = new Gson().fromJson(result.get("result"), PatientsListDTO.class);
                        if (patientsListDTO != null && patientsListDTO.getTotalCount() > 0) {
                            emptyTxtView.setVisibility(View.GONE);
                            toggleListViewScrollbar(emptyTxtView.getVisibility() == View.VISIBLE ? Boolean.FALSE : Boolean.TRUE);

                            concatedPatientList = new ArrayList<>();
                            serverSidePatientList = (ArrayList<PatientDTO>) patientsListDTO.getHealthRegistrationList();

                            Collections.sort(serverSidePatientList, new NameComparator());

                            String details = "";
                            if(serverSidePatientList != null && !serverSidePatientList.isEmpty()){
                                for(PatientDTO patient : serverSidePatientList){
                                    details = patient.getFirstName() + " " + patient.getLastName() +
                                            patient.getRegId() + patient.getMobileNo() +
                                            patient.getSearchCid() + patient.getProofNumber();
                                    concatedPatientList.add(details);
                                }
                            }

                            adapter = new PatientsAdaptor(viewpatients.this, serverSidePatientList);
                            listView.setAdapter(adapter);
                        }
                        else emptyTxtView.setVisibility(View.VISIBLE);
                    }
                    catch (Exception e){
                        Log.d("Exception occurred", e.getLocalizedMessage());
                    }
                } else {
                    new AlertDialog.Builder(viewpatients.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Retrieving Patients");
        worker.execute(nameValues);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewpatients, menu);
        searchView.setMenuItem(menu.findItem(R.id.action_search));
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
            Map<String, String> nameValues = new HashMap<String, String>();
            nameValues.put("serverUrl", serverUrl);
            nameValues.put("operationType", "6");

            AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                    viewpatients.this, new ActionCallback() {
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

    private static void showKeyboard(Activity activity) {
        if (activity != null) {
            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static void hideKeyboard(Activity activity) {
        if (activity != null) {
            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void toggleListViewScrollbar(boolean val){
        listView.setFastScrollEnabled(val);
        listView.setFastScrollAlwaysVisible(val);
    }
}
