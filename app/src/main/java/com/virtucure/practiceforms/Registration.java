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
    private EditText firstName_field, lastName_field, mobileNo_field, emailId_field, state_field, proofnum_field, gender_field, dob_field, relation_field, country_field, prooftype_field, address_field;
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
        setContentView(R.layout.registration_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        firstName_field = (EditText) findViewById(R.id.firstName);
        lastName_field = (EditText) findViewById(R.id.lastName);
        mobileNo_field = (EditText) findViewById(R.id.mobileNo);
        emailId_field = (EditText) findViewById(R.id.email_address);
        gender_field = (EditText) findViewById(R.id.gender);
        dob_field = (EditText) findViewById(R.id.reg_dob);
        relation_field = (EditText) findViewById(R.id.rwp);
        country_field = (EditText) findViewById(R.id.country);
        state_field = (EditText) findViewById(R.id.states);
        prooftype_field = (EditText) findViewById(R.id.proofType);
        proofnum_field = (EditText) findViewById(R.id.proofNo);
        address_field = (EditText) findViewById(R.id.postal_address);

        gender_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.requestFocus();
//                    initiateContextMenu(v);
                    return true;
                }
                return false;
            }
        });
        gender_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    initiateContextMenu(v);
                }
            }
        });

        dob_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.requestFocus();
//                    SetDate(v);
                    return true;
                }
                return false;
            }
        });
        dob_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    SetDate(v);
                }
            }
        });

        relation_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.requestFocus();
//                    initiateContextMenu(v);
                    return true;
                }
                return false;
            }
        });
        relation_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    initiateContextMenu(v);
                }
            }
        });

        country_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    state_field.setText("Select State");
                    v.requestFocus();
//                    initiateContextMenu(v);
                    return true;
                }
                return false;
            }
        });
        country_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    state_field.setText("Select State");
                    initiateContextMenu(v);
                }
            }
        });

        state_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.requestFocus();
//                    initiateContextMenu(v);
                    return true;
                }
                return false;
            }
        });
        state_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    initiateContextMenu(v);
                }
            }
        });

        prooftype_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    proofnum_field.setText("");
                    v.requestFocus();
//                    initiateContextMenu(v);
                    return true;
                }
                return false;
            }
        });
        prooftype_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    proofnum_field.setText("");
                    initiateContextMenu(v);
                }
            }
        });

        Button submit = (Button)findViewById(R.id.register);
        if(submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstName = firstName_field.getText().toString().trim();
                    if (TextUtils.isEmpty(firstName)) {
                        firstName_field.requestFocus();
                        firstName_field.setError("First Name is Required");
                        return;
                    } else if (!isValidName(firstName)) {
                        firstName_field.requestFocus();
                        firstName_field.setError("Only characters and space allowed");
                        return;
                    } else {
                        firstName_field.setError(null);
                    }

                    lastName = lastName_field.getText().toString().trim();
                    if (TextUtils.isEmpty(lastName)) {
                        lastName_field.requestFocus();
                        lastName_field.setError("Last Name  is Required");
                        return;
                    } else if (!isValidName(lastName)) {
                        lastName_field.requestFocus();
                        lastName_field.setError("Only characters and space allowed");
                        return;
                    } else {
                        lastName_field.setError(null);
                    }

                    mobileNo = mobileNo_field.getText().toString().trim();
                    if (!isValidMobile(mobileNo)) {
                        mobileNo_field.requestFocus();
                        mobileNo_field.setError("Invalid Mobile Number");
                        return;
                    } else {
                        mobileNo_field.setError(null);
                    }

                    email = emailId_field.getText().toString().trim();
                    if (!isValidEmail(email)) {
                        emailId_field.requestFocus();
                        emailId_field.setError("Invalid email address");
                        return;
                    } else {
                        emailId_field.setError(null);
                    }

                    gender = gender_field.getText().toString();
                    if (TextUtils.isEmpty(gender) || "Gender".equalsIgnoreCase(gender)) {
                        gender_field.requestFocus();
                        gender_field.setError("Gender is Required");
                        return;
                    } else {
                        gender_field.setError(null);
                        if("Decline To Mention".equalsIgnoreCase(gender)) {
                            gender = "Decline";
                        }
                    }

                    dateOfBirth = dob_field.getText().toString().trim();
                    try {
                        selectedDate = sdf.parse(dateOfBirth);
                        datediff = new Date().compareTo(selectedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(dateOfBirth)) {
                        dob_field.requestFocus();
                        dob_field.setError("Date of Birth is Required");
                        return;
                    } else if(datediff < 0) {
                        dob_field.requestFocus();
                        dob_field.setError("Pleas select valid date.");
                        return;
                    } else {
                        dob_field.setError(null);
                    }

                    relationselected = relation_field.getText().toString();
                    if (TextUtils.isEmpty(relationselected) || "Relation With Patient".equalsIgnoreCase(relationselected)) {
                        relation_field.requestFocus();
                        relation_field.setError("Relation is Required");
                        return;
                    } else {
                        relation_field.setError(null);
                    }

                    country = country_field.getText().toString();
                    if (TextUtils.isEmpty(country) || "Select Country".equalsIgnoreCase(country)) {
                        country_field.requestFocus();
                        country_field.setError("Country is Required");
                        return;
                    } else {
                        country_field.setError(null);
                    }

                    if (View.VISIBLE == state_field.getVisibility()) {

                        state = state_field.getText().toString();
                        if (TextUtils.isEmpty(state) || "Select State".equalsIgnoreCase(state)) {
                            state_field.requestFocus();
                            state_field.setError("State is Required");
                            return;
                        } else {
                            state_field.setError(null);
                        }
                    } else state = "";

                    proofTypeSelected = prooftype_field.getText().toString();

                    if (!"Proof Type".equalsIgnoreCase(proofTypeSelected) && !proofTypeSelected.isEmpty()) {
                        proofNo = proofnum_field.getText().toString().trim();
                        if(TextUtils.isEmpty(proofNo)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Enter Proof Number");
                            return;
                        } else if (("Adhar Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Adhaar number should be 12 digit number");
                            return;
                        } else if (("Pan Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Pan Card number should be alpha-numeric length of 10");
                            return;
                        } /*else if (("Driving License".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        proofnum_field.requestFocus();
                        proofnum_field.setError("Driving License should be 12 digit number");
                        return;
                        }*/ else if (("Voter Identity Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Voter id number should be alpha-numeric length of 10");
                            return;
                        }
                        else if (("Ration Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Ration Card number should be alpha-numeric length of 15");
                            return;
                        } /*else if (("Student Identity Card".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                        proofnum_field.requestFocus();
                        proofnum_field.setError("Student Id number");
                        return;
                        }*/ else if (("Passport".equalsIgnoreCase(proofTypeSelected)) && !isValidProofNumber(proofNo, proofTypeSelected)) {
                            proofnum_field.requestFocus();
                            proofnum_field.setError("Passport number should be an alpha-numeric length of 8 ");
                            return;
                        } else {
                            proofnum_field.setError(null);
                        }
                    }

                    postalAddress = address_field.getText().toString().trim();
                    if (TextUtils.isEmpty(postalAddress)) {
                        address_field.requestFocus();
                        address_field.setError("address is Required");
                        return;
                    } else {
                        address_field.setError(null);
                    }

                    CheckBox cb = (CheckBox) findViewById(R.id.regagree);
                    if (!cb.isChecked()) {
                        cb.requestFocus();
                        cb.setError("Please agree before submit");
                        return;
                    } else {
                        cb.setError(null);
                    }

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
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    private boolean isValidEmail(CharSequence target) {
        return (target == null) ? Boolean.FALSE : android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidProofNumber(String proofNumber, String proofType) {

        String adharRegEx = "^\\d{12}$";
        String pancardRegEx = "[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}";
        String voterRegEx = "[A-Za-z]{3}(\\d){7}";
        String passportRegEx = "[A-Za-z]{1}(\\d){7}";
        String rationCardRegEx = "[A-Za-z]{3}(\\d){12}";

        if("Adhar Card".equalsIgnoreCase(proofType) && proofNumber.matches(adharRegEx)) {
            return true;
        } else if("Pan Card".equalsIgnoreCase(proofType) && proofNumber.matches(pancardRegEx)) {
            return true;
        } else if("Driving License".equalsIgnoreCase(proofType)) {
            return true;
        } else if("Voter Identity Card".equalsIgnoreCase(proofType) && proofNumber.matches(voterRegEx)) {
            return true;
        } else if("Ration Card".equalsIgnoreCase(proofType) && proofNumber.matches(rationCardRegEx)) {
            return true;
        } else if("Student Identity Card".equalsIgnoreCase(proofType)) {
            return true;
        } else if("Passport".equalsIgnoreCase(proofType) && proofNumber.matches(passportRegEx)) {
            return true;
        }
        return false;
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidName(String name) {
        String nameRegEx = "^[A-Za-z]((\\s)?[A-Za-z])*$";
        return (name.matches(nameRegEx)) ? Boolean.TRUE : Boolean.FALSE;
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        switch (v.getId()) {

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

        switch (item.getGroupId()) {

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
                } else if("USA".equalsIgnoreCase(title)) {
                    statesList = getResources().getStringArray(R.array.array_States_USA);
                } else {
                    statesList = null;
                }

                state_field.setVisibility((statesList != null && statesList.length > 0) ? View.VISIBLE : View.GONE);
                break;

            case 4 :
                et = (EditText)findViewById(R.id.states);
                break;

            case 5 :
                et = (EditText) findViewById(R.id.proofType);
                proofnum_field.setVisibility(("Proof Type".equalsIgnoreCase(title)) ? View.GONE : View.VISIBLE);
                break;

            default:
                return super.onContextItemSelected(item);
        }

        if(et != null) {
            et.setText(title);
        }

        return true;
    }

    private void initiateContextMenu(View view) {
        if(view != null) {
            Util.hideKeyboard(view);
            registerForContextMenu(view);
            openContextMenu(view);
        }
    }

}
