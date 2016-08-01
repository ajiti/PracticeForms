package com.virtucure.practiceforms;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class loginactivity extends AppCompatActivity {

    public static String currentLoggedInEmail;
    public static String currentLoggedInPassword;
    private static long back_pressed_time;
    private static long BACK_PRESS_TIME_GAP = 2000;
    private static final String TAG = loginactivity.class.getSimpleName();
    private CheckBox rememberme;
    private AutoCompleteTextView usernameText;
    private DBUserAdapter dbUserAdapter;
    private ArrayList<String> usernameList;
    private int clearDrawable;

    private static String serverUrl = ServerUtil.serverUrl + "VCRegionalAPP/rest/login";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        setContentView(R.layout.activity_loginactivity);

        dbUserAdapter = new DBUserAdapter(this);
        try {
            dbUserAdapter.open();
            usernameList = dbUserAdapter.retrieveData();
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        } finally {
            dbUserAdapter.close();
        }

        clearDrawable = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? R.drawable.ic_clear_white : R.drawable.ic_action_cancel;

        rememberme = (CheckBox) findViewById(R.id.rememberme);
        usernameText = ((AutoCompleteTextView) findViewById(R.id.editText));

        usernameText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
        final TextView passwordText = ((EditText) findViewById(R.id.editText2));
        passwordText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pass, 0, 0, 0);

        ArrayAdapter<String> adapter= new ArrayAdapter<>(getApplicationContext(), R.layout.username_item, R.id.multi, usernameList);
        usernameText.setAdapter(adapter);
        usernameText.setThreshold(2);

        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (usernameText.getText().length() > 0) {

                    usernameText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, clearDrawable, 0);
                    currentLoggedInEmail = usernameText.getText().toString().trim();
                    dbUserAdapter = new DBUserAdapter(loginactivity.this);
                    try {
                        dbUserAdapter.open();
                        currentLoggedInPassword = dbUserAdapter.getPassword(currentLoggedInEmail);

                        if(currentLoggedInPassword != null && !currentLoggedInPassword.isEmpty()) {
                            passwordText.setText(currentLoggedInPassword);
                            rememberme.setChecked(true);
                        } else {
                            passwordText.setText("");
                            rememberme.setChecked(false);
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    } finally {
                        dbUserAdapter.close();
                    }

                } else {
                    usernameText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (passwordText.getText().length() > 0) {
                    passwordText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pass, 0, clearDrawable, 0);
                } else {
                    passwordText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pass, 0, 0, 0);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

        });

        usernameText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (usernameText.getCompoundDrawables()[2] != null) {
                        if (event.getX() >= (usernameText.getRight() - usernameText.getLeft() - usernameText.getCompoundDrawables()[2].getBounds().width() - usernameText.getPaddingRight())) {
                            usernameText.setText("");
                        }
                    }
                }
                return false;
            }
        });
        passwordText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (passwordText.getCompoundDrawables()[2] != null) {
                        if (event.getX() >= (passwordText.getRight() - passwordText.getLeft() - passwordText.getCompoundDrawables()[2].getBounds().width() - passwordText.getPaddingRight())) {
                            passwordText.setText("");
                        }
                    }
                }
                return false;
            }
        });

        final Button loginBtn = (Button) findViewById(R.id.button);
        if(loginBtn != null){
            loginBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // PRESSED
                            Thread timerThread = new Thread(){
                                public void run()
                                {
                                    try {
                                        sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                    loginBtn.setAlpha(0.95F);
                                                }
                                            }
                                        });
                                    } catch(InterruptedException e) {
                                        Log.e(TAG, e.getLocalizedMessage(), e);
                                    }
                                }
                            };
                            timerThread.start();
                            return false;

                        case MotionEvent.ACTION_UP:
                            // RELEASED
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                loginBtn.setAlpha(0.55F);
                            }
                            return false; // if you want to handle the touch event
                    }
                    return false;
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    currentLoggedInEmail = usernameText.getText().toString().trim();
                    currentLoggedInPassword = passwordText.getText().toString().trim();

                    if (currentLoggedInEmail.isEmpty()) {
                        usernameText.requestFocus();
                        usernameText.setError("Username cannot be empty");
                        return;
                    }
                    if (currentLoggedInPassword.isEmpty()) {
                        passwordText.requestFocus();
                        passwordText.setError("Password cannot be empty");
                        return;
                    }

                    dbUserAdapter = new DBUserAdapter(getApplicationContext());
                    dbUserAdapter.open();

                    try {
                        dbUserAdapter.deleteLoginDetails(currentLoggedInEmail);
                        if(rememberme != null && rememberme.isChecked()){
                            dbUserAdapter.AddUserDetails(currentLoggedInEmail, currentLoggedInPassword);
                        } else {
                            dbUserAdapter.AddUserDetails(currentLoggedInEmail, "");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    } finally {
                        dbUserAdapter.close();
                    }

                    if(isNetworkConnectionAvailable()) {
                        doLogin();
                    }
                }
            });
        }

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    private void doLogin() {
        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("username", currentLoggedInEmail);
        nameValues.put("password", currentLoggedInPassword);
        nameValues.put("operationType", "1");
        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                loginactivity.this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {

                if (!result.isEmpty() && result.containsKey("result") && result.get("result") != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        User.cid = jsonObject.get("cid").toString();
                        User.brandorBranchName =  jsonObject.get("brandorBranchName").toString();
                        User.username = jsonObject.get("userName").toString();
                        User.fullName = jsonObject.get("fullName").toString();
                        User.linkType = jsonObject.get("linkType").toString();
                        User.userType = jsonObject.get("userType").toString();
                        Intent home = new Intent(
                                getApplicationContext(),
                                Home.class);
                        startActivity(home);
                    } catch (Exception e){
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }

                } else {
                    new AlertDialog.Builder(loginactivity.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }
            }
        }, "Logging in");
        worker.execute(nameValues);
    }

    @Override
    public void onBackPressed() {

        if ((back_pressed_time + BACK_PRESS_TIME_GAP) > System.currentTimeMillis()) {
            //super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed_time = System.currentTimeMillis();
    }

    private boolean isNetworkConnectionAvailable() {

        ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            new AlertDialog.Builder(loginactivity.this).setTitle("Error")
                    .setMessage("Internet Connection is Required").show();
            return false;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void onResume(){
        super.onResume();
        CookieSyncManager.getInstance().stopSync();
    }

    @SuppressWarnings("deprecation")
    public void onPause(){
        super.onPause();
        CookieSyncManager.getInstance().sync();
    }
}
