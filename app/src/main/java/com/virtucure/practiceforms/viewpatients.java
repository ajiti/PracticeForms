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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText searchInput;
    private ArrayList<PatientDTO> oldList;
    private ArrayList<PatientDTO> finalList;
    private List<String> newList;
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

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                finalList = new ArrayList<>();
/*                if(newText.toString().length() > 0){
                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, R.drawable.clear, 0);
                }
                else {
                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                }*/

                if (newText.length() != 0 && newText.toString().length() > 4) {
                    for (int i = 0; (newList != null && i < newList.size()); i++) {
                        if (newList.get(i).toLowerCase().contains(newText.toString().toLowerCase())) {
                            finalList.add(oldList.get(i));
                        }
                    }
                    if (finalList.size() == 0) {
                        emptyTxtView.setVisibility(View.VISIBLE);
                    } else emptyTxtView.setVisibility(View.GONE);

                    adapter = new PatientsAdaptor(viewpatients.this, finalList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (newText.toString().length() <= 4) {
                    emptyTxtView.setVisibility(View.GONE);
                    adapter = new PatientsAdaptor(viewpatients.this, oldList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
//                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                }
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
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
//        searchView.setCursorDrawable(R.drawable.custom_cursor);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        listView = (ListView) findViewById(R.id.patients_list);
        listView.setFastScrollEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            listView.setFastScrollAlwaysVisible(true);
        }
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

        emptyTxtView = (TextView) findViewById(R.id.noPatientRecords);
/*        searchInput = (EditText) findViewById(R.id.searchBox);
        searchInput.setVisibility(View.GONE);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                finalList = new ArrayList<>();
                if(searchInput.getText().toString().length() > 0){
                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, R.drawable.clear, 0);
                }
                else {
                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                }

                if (searchInput.getText().length() != 0 && viewpatients.this.searchInput.getText().toString().length() > 4) {
                    for (int i = 0; (newList != null && i < newList.size()); i++) {
                        if (newList.get(i).toLowerCase().contains(searchInput.getText().toString().toLowerCase())) {
                            finalList.add(oldList.get(i));
                        }
                    }
                    if (finalList.size() == 0) {
                        emptyTxtView.setVisibility(View.VISIBLE);
                    } else emptyTxtView.setVisibility(View.GONE);

                    adapter = new PatientsAdaptor(viewpatients.this, finalList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else if (searchInput.getText().toString().length() <= 4) {
                    emptyTxtView.setVisibility(View.GONE);
                    adapter = new PatientsAdaptor(viewpatients.this, oldList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    searchInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        searchInput.setOnTouchListener(new View.OnTouchListener() {
            //            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (searchInput.getCompoundDrawables()[2] != null) {
                        if (event.getX() >= (searchInput.getRight() - searchInput.getLeft() - searchInput.getCompoundDrawables()[2].getBounds().width())) {
                            searchInput.setText("");
                        }
                    }
                }
                return false;
            }
        });*/

        int width = displayMetrics.widthPixels;
        width = width*3/4;

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
                        if (patientsListDTO.getTotalCount() > 0){
                            emptyTxtView.setVisibility(View.GONE);

                            newList = new ArrayList<>();
                            oldList = (ArrayList<PatientDTO>) patientsListDTO.getHealthRegistrationList();

                            Collections.sort(oldList, new NameComparator());

                            String details = "";
                            for(PatientDTO patient : oldList){
                                details = patient.getFirstName() + " " + patient.getLastName() +
                                        patient.getRegId() + patient.getMobileNo() +
                                        patient.getSearchCid() + patient.getProofNumber();
                                newList.add(details);
                            }

                            adapter = new PatientsAdaptor(viewpatients.this, oldList);
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

/*    @Override
    protected void onStop(){
        super.onStop();
    }*/

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
/*        if (id == R.id.action_search) {
            // Change visibilty of your RelativeLayout here
            if(oldList!=null && oldList.size() > 0){
                if (searchInput.getVisibility() == View.VISIBLE) {
                    searchInput.setVisibility(View.GONE);
                } else {
                    searchInput.setVisibility(View.VISIBLE);
                    searchInput.requestFocus();
                }
            }
            else Toast.makeText(this, "No Patients To Search", Toast.LENGTH_SHORT).show();
        }*/
        //noinspection SimplifiableIfStatement
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
}
