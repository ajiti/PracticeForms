package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private Toolbar toolbar;
    public String firstName = null;
    public String lastName = null;
    public String mobileNo =  null;
    public String email = null;
    public String gender =  null;
    public String dateOfBirth =  null;
    public String relationselected = null;
    public String country = null;
    public String state = null;
    public String proofTypeSelected = null;
    public String postalAddress = null;
    public String proofNo =  null;
    private Context context = this;
    private EditText state_field = null;
    private EditText proofnum_field = null;
    private int datediff;
    private Date selectedDate;
    public String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/register";
    private BroadcastReceiver logoutReceiver;
    private String [] list = new String[0];
    private String [] statesList = new String[0];
    private int groupId;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            String date = String.format("%02d", arg0.getDayOfMonth()) + "-" + String.format("%02d", (arg0.getMonth() + 1)) + "-" + arg0.getYear();
            EditText dob = (EditText) findViewById(R.id.reg_dob);
            dob.setText(date);
        }
    };

    public void SetDate(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        new DatePickerDialog(this, myDateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

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
        setContentView(R.layout.activity_registration);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        final EditText gender_field = (EditText) findViewById(R.id.gender);
        gender_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    registerForContextMenu(gender_field);
                    openContextMenu(gender_field);
                    return true;
                }
                return false;
            }
        });

        EditText dob_field = (EditText) findViewById(R.id.reg_dob);
        dob_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                    SetDate(v);
                return false;
            }
        });

        final EditText relation_field = (EditText) findViewById(R.id.rwp);
        relation_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    registerForContextMenu(relation_field);
                    openContextMenu(relation_field);
                    return true;
                }
                return false;
            }
        });

        state_field = (EditText) findViewById(R.id.states);

        final EditText country_field = (EditText) findViewById(R.id.country);
        country_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    state_field.setText("Select State");
                    registerForContextMenu(country_field);
                    openContextMenu(country_field);
                    return true;
                }
                return false;
            }
        });

        state_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    registerForContextMenu(state_field);
                    openContextMenu(state_field);
                    return true;
                }
                return false;
            }
        });

        proofnum_field = (EditText) findViewById(R.id.proofNo);

        final EditText prooftype_field = (EditText) findViewById(R.id.proofType);
        prooftype_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    proofnum_field.setText("");
                    registerForContextMenu(prooftype_field);
                    openContextMenu(prooftype_field);
                    return true;
                }
                return false;
            }
        });

        Button submit = (Button)findViewById(R.id.register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = ((EditText) findViewById(R.id.firstName)).getText().toString().trim();
                if (TextUtils.isEmpty(firstName)) {
                    findViewById(R.id.firstName).requestFocus();
                    ((EditText) findViewById(R.id.firstName)).setError("First Name is Required");
                    return;
                } else if (!isValidName(firstName)) {
                    findViewById(R.id.firstName).requestFocus();
                    ((EditText) findViewById(R.id.firstName)).setError("Only characters and space allowed");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.firstName)).setError(null);

                lastName = ((EditText) findViewById(R.id.lastName)).getText().toString().trim();
                if (TextUtils.isEmpty(lastName)) {
                    findViewById(R.id.lastName).requestFocus();
                    ((EditText) findViewById(R.id.lastName)).setError("Last Name  is Required");
                    return;
                } else if (!isValidName(lastName)) {
                    findViewById(R.id.lastName).requestFocus();
                    ((EditText) findViewById(R.id.lastName)).setError("Only characters and space allowed");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.lastName)).setError(null);

                mobileNo = ((EditText) findViewById(R.id.mobileNo)).getText().toString().trim();
                if (!isValidMobile(mobileNo)) {
                    findViewById(R.id.mobileNo).requestFocus();
                    ((EditText) findViewById(R.id.mobileNo)).setError("Invalid Mobile Number");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.mobileNo)).setError(null);

                email = ((EditText) findViewById(R.id.email_address)).getText().toString().trim();
                if (!isValidEmail(email)) {
                    findViewById(R.id.email_address).requestFocus();
                    ((EditText) findViewById(R.id.email_address)).setError("Invalid email address");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.email_address)).setError(null);

                gender = gender_field.getText().toString();
                if (TextUtils.isEmpty(gender) || "Gender".equalsIgnoreCase(gender)) {
                    findViewById(R.id.gender).requestFocus();
                    ((EditText) findViewById(R.id.gender)).setError("Gender is Required");
                    return;
                }
                else {
                    ((EditText) findViewById(R.id.gender)).setError(null);
                    if("Decline To Mention".equalsIgnoreCase(gender))
                        gender = "Decline";
                }

                dateOfBirth = ((EditText) findViewById(R.id.reg_dob)).getText().toString().trim();
                try {
                    selectedDate = sdf.parse(dateOfBirth);
                    datediff = new Date().compareTo(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(dateOfBirth)) {
                    findViewById(R.id.reg_dob).requestFocus();
                    ((EditText) findViewById(R.id.reg_dob)).setError("Date of Birth is Required");
                    return;
                }
                else if(datediff < 0)
                {
                    findViewById(R.id.reg_dob).requestFocus();
                    ((EditText) findViewById(R.id.reg_dob)).setError("Pleas select valid date.");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.reg_dob)).setError(null);

                relationselected = relation_field.getText().toString();
                if (TextUtils.isEmpty(relationselected) || "Relation With Patient".equalsIgnoreCase(relationselected)) {
                    findViewById(R.id.rwp).requestFocus();
                    ((EditText) findViewById(R.id.rwp)).setError("Relation is Required");
                    return;
                }
                else {
                    ((EditText) findViewById(R.id.rwp)).setError(null);
                }

                country = country_field.getText().toString();
                if (TextUtils.isEmpty(country) || "Select Country".equalsIgnoreCase(country)) {
                    findViewById(R.id.country).requestFocus();
                    ((EditText) findViewById(R.id.country)).setError("Country is Required");
                    return;
                }
                else {
                    ((EditText) findViewById(R.id.country)).setError(null);
                }

                if (View.VISIBLE == state_field.getVisibility()) {

                    state = state_field.getText().toString();
                    if (TextUtils.isEmpty(state) || "Select State".equalsIgnoreCase(state)) {
                        findViewById(R.id.states).requestFocus();
                        ((EditText) findViewById(R.id.states)).setError("State is Required");
                        return;
                    }
                    else {
                        ((EditText) findViewById(R.id.states)).setError(null);
                    }
                }
                else state = "";

                proofTypeSelected = prooftype_field.getText().toString();

                if (!proofTypeSelected.equalsIgnoreCase("Proof Type")) {
                    proofNo = ((EditText) findViewById(R.id.proofNo)).getText().toString().trim();
                    if(TextUtils.isEmpty(proofNo)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Enter Proof Number");
                        return;
                    }
                    else if (("Adhar Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Adhaar number should be 12 digit number");
                        return;
                    }
                    else if (("Pan Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Pan Card number should be alpha-numeric length of 10");
                        return;
                    }
                    /*else if (("Driving License".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        ((EditText) findViewById(R.id.proofNo)).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Driving License should be 12 digit number");
                        return;
                    }*/
                    else if (("Voter Identity Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Voter id number should be alpha-numeric length of 10");
                        return;
                    }
                    else if (("Ration Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Ration Card number should be alpha-numeric length of 15");
                        return;
                    }
                    /*else if (("Student Identity Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        ((EditText) findViewById(R.id.proofNo)).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Student Id number");
                        return;
                    }*/
                    else if (("Passport".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        findViewById(R.id.proofNo).requestFocus();
                        ((EditText) findViewById(R.id.proofNo)).setError("Passport number should be an alpha-numeric length of 8 ");
                        return;
                    }
                    else
                        ((EditText) findViewById(R.id.proofNo)).setError(null);
                }

                postalAddress = ((EditText) findViewById(R.id.postal_address)).getText().toString().trim();
                if (TextUtils.isEmpty(postalAddress)) {
                    findViewById(R.id.postal_address).requestFocus();
                    ((EditText) findViewById(R.id.postal_address)).setError("address is Required");
                    return;
                }
                else
                    ((EditText) findViewById(R.id.postal_address)).setError(null);


                CheckBox cb = (CheckBox) findViewById(R.id.regagree);
                if (!cb.isChecked()) {
                    cb.requestFocus();
                    cb.setError("Please agree before submit");
                    return;
                }
                else
                    cb.setError(null);

                Map<String, String> params = new HashMap<>();
                params.put("serverUrl", serverUrl);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("gender", gender);
                params.put("dob", dateOfBirth);
                params.put("country", country);
                params.put("state", state);
                params.put("relationship", relationselected);
                params.put("agree", "");
                params.put("emailId", email);
                params.put("mobileNo", mobileNo);
                params.put("proofType", proofTypeSelected);
                params.put("proofNumber", proofNo);
                params.put("address", postalAddress);
                params.put("searchCid", "");
                params.put("operationType", "3");
                params.put("cid", User.cid);
                params.put("bBname", User.brandorBranchName);
                AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                        Registration.this, new ActionCallback() {
                    public void onCallback(Map<String, String> result) {
                        if (!result.isEmpty()) {
                            if (result.get("result") != null) {
                                Intent menuactivity = new Intent(
                                        getApplicationContext(),
                                        Home.class);
                                Toast.makeText(getApplicationContext(),
                                        "Generated Patient id : " + result.get("result"), Toast.LENGTH_LONG)
                                        .show();
                                startActivity(menuactivity);
                            }
                        } else {
                            new AlertDialog.Builder(Registration.this)
                                    .setTitle("Error")
                                    .setMessage(result.get("error"))
                                    .show();
                        }
                    }
                }, "Registering Patient");
                worker.execute(params);
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

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    private boolean isValidProofNumber(String proofNumber, String proofType)
    {
        String adharRegEx = "^\\d{12}$";
        String pancardRegEx = "[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}";
        String voterRegEx = "[A-Za-z]{3}(\\d){7}";
        String passportRegEx = "[A-Za-z]{1}(\\d){7}";
        String rationCardRegEx = "[A-Za-z]{3}(\\d){12}";
        if("Adhar Card".equalsIgnoreCase(proofType) && proofNumber.matches(adharRegEx))
            return true;
        else if("Pan Card".equalsIgnoreCase(proofType) && proofNumber.matches(pancardRegEx))
            return true;
        else if("Driving License".equalsIgnoreCase(proofType))
            return true;
        else if("Voter Identity Card".equalsIgnoreCase(proofType) && proofNumber.matches(voterRegEx))
            return true;
        else if("Ration Card".equalsIgnoreCase(proofType) && proofNumber.matches(rationCardRegEx))
            return true;
        else if("Student Identity Card".equalsIgnoreCase(proofType))
            return true;
        else if("Passport".equalsIgnoreCase(proofType) && proofNumber.matches(passportRegEx))
            return true;
        return false;
    }
    private boolean isValidMobile(String phone)
    {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidName(String name)
    {
        String nameRegEx = "^[A-Za-z]((\\s)?[A-Za-z])*$";
        if(name.matches(nameRegEx))
            return true;
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
                    Registration.this, new ActionCallback() {
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
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId())
        {
            case R.id.gender:
                groupId = 1;
                list = getResources().getStringArray(R.array.array_gender);
                break;

            case R.id.rwp:
                groupId = 2;
                list = getResources().getStringArray(R.array.relation);
                break;

            case R.id.country:
                groupId = 3;
                list = getResources().getStringArray(R.array.countries);
                break;

            case R.id.states:
                groupId = 4;
                list = statesList;
                break;

            case R.id.proofType:
                groupId = 5;
                list = getResources().getStringArray(R.array.proofType);
                break;
            default:
                super.onCreateContextMenu(menu, v, menuInfo);
        }

        menu.setHeaderTitle(list[0]);
        for (int i = 1; i < list.length; i++) {
            menu.add(groupId, v.getId(), i, list[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title = (String) item.getTitle();
        EditText et = null;

        switch (item.getGroupId())
        {
            case 1 :
                et = (EditText)findViewById(R.id.gender);
                break;

            case 2 :
                et = (EditText)findViewById(R.id.rwp);
                break;

            case 3 :
                et = (EditText) findViewById(R.id.country);
                if ("India".equalsIgnoreCase(title)) {
                    statesList = getResources().getStringArray(R.array.array_States_IN);
                }
                else if("USA".equalsIgnoreCase(title)){
                    statesList = getResources().getStringArray(R.array.array_States_USA);
                }
                else statesList = null;

                if(statesList != null && statesList.length>0)
                    state_field.setVisibility(View.VISIBLE);
                else state_field.setVisibility(View.GONE);
                break;

            case 4 :
                et = (EditText)findViewById(R.id.states);
                break;

            case 5 :
                et = (EditText) findViewById(R.id.proofType);
                if("Proof Type".equalsIgnoreCase(title))
                    proofnum_field.setVisibility(View.GONE);
                else proofnum_field.setVisibility(View.VISIBLE);
                break;
            default:
                return super.onContextItemSelected(item);
        }

        if(et != null)
            et.setText(title);

        return true;
    }
}
