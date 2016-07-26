package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtucare.practiceforms.dto.CaseRecordDataDTO;
import com.virtucare.practiceforms.dto.FileAttachmentDTO;
import com.virtucare.practiceforms.dto.Form;
import com.virtucare.practiceforms.dto.FormFieldsDTO;
import com.virtucare.practiceforms.dto.FormNamesDTO;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 5/9/2016.
 */
public class CaseRecordFormsActivity extends AppCompatActivity {
    private static final String TAG = CaseRecordFormsActivity.class.getSimpleName();
    private static final String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getcaserecordsdata";
    private String formServerUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/assmntform";
    private Toolbar toolbar;
    private static final int PICK_FILE_REQUEST = 1;
    ExpandableCaseRecordFormsListAdapter listAdapter;
    ExpandableListView expListView;
    FloatingActionButton shareBtn;
    List<Form> listDataHeader;
    private CaseRecordDataDTO caseRecordData;
    private ListView formListView;
    private String formName;
    private List<FormFieldsDTO> formsList = new ArrayList<>();
    private String caseRecordNo;
    private BroadcastReceiver logoutReceiver = null;
    private int lastExpandedPosition = -1;
    private int ageInMonths;
    private String patientGender;
    private String hrid;
    private String patientName;
    private String ghid;
    private String proofNumber;
    private String proofType;
    private Context context = this;
    private List<String> formList;

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
        patientGender = getIntent().getExtras().getString("gender");
        final String patientDob = getIntent().getExtras().getString("dob");
        ageInMonths = getAgeInMonths(patientDob);

        initForms();
        getCaseRecordForms();
        setContentView(R.layout.caserecordsform_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

        expListView = (ExpandableListView) findViewById(R.id.formsExp);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition)
                    expListView.collapseGroup(lastExpandedPosition);
                lastExpandedPosition = groupPosition;
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                } else {
                    parent.collapseGroup(groupPosition);
                }
                parent.setSelectedGroup(groupPosition);
                return true;
            }
        });

        getFormNames();
        formListView = (ListView) findViewById(R.id.formnamesList);

        Button addFormBtn = (Button) findViewById(R.id.addfrms);
        addFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(formListView);
                openContextMenu(formListView);
            }
        });

        Button uploadBtn = (Button) findViewById(R.id.fileUploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select location"), PICK_FILE_REQUEST);
            }
        });

        shareBtn = (FloatingActionButton) findViewById(R.id.formShareBtn);
        if(shareBtn != null) {
/*            shareBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_MOVE:
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.setMargins((int) event.getRawX() - v.getWidth() / 2, (int) (event.getRawY() - v.getHeight() * 1.5), (int) event.getRawX() - v.getWidth() / 2, (int) (event.getRawY() - v.getHeight() * 1.5));
                            v.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:

                            ArrayList<String> formIds = new ArrayList<>();
                            for (Form form : listDataHeader) {
                                if (form.isSelected()) {
                                    formIds.add(form.getFormId());
                                }
                            }
                            if (formIds.size() == 0) {
                                new AlertDialog.Builder(context).setTitle("Alert").setMessage("Select Forms To Share")
                                        .setCancelable(false).setPositiveButton("Ok", null).show();
                                return false;
                            }
                            Intent shareIntent = new Intent(getApplicationContext(), FormShareActivity.class);
                            shareIntent.putExtra("caseRecordNo", caseRecordNo);
                            shareIntent.putStringArrayListExtra("formIdArray", formIds);
                            shareIntent.putExtra("healthRegistrationId", hrid);
                            shareIntent.putExtra("patientName", patientName);
                            shareIntent.putExtra("proofType", proofType);
                            shareIntent.putExtra("proofNumber", proofNumber);
                            shareIntent.putExtra("regnLinkId", ghid);
                            shareIntent.putExtra("gender", patientGender);
                            shareIntent.putExtra("email", getIntent().getExtras().getString("email"));
                            shareIntent.putExtra("dob", patientDob);
                            shareIntent.putExtra("phone", getIntent().getExtras().getString("phone"));
                            startActivity(shareIntent);
                            break;
                        default:
                            return false;
                    }
                    v.invalidate();
                    return true;
                }
            });*/
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> formIds = new ArrayList<>();
                    for (Form form : listDataHeader) {
                        if (form.isSelected()) {
                            formIds.add(form.getFormId());
                        }
                    }
                    if (formIds.size() == 0) {
                        new AlertDialog.Builder(context).setTitle("Alert").setMessage("Select Forms To Share")
                                .setCancelable(false).setPositiveButton("Ok", null).show();
                        return;
                    }
                    Intent shareIntent = new Intent(getApplicationContext(), FormShareActivity.class);
                    shareIntent.putExtra("caseRecordNo", caseRecordNo);
                    shareIntent.putStringArrayListExtra("formIdArray", formIds);
                    shareIntent.putExtra("healthRegistrationId", hrid);
                    shareIntent.putExtra("patientName", patientName);
                    shareIntent.putExtra("proofType", proofType);
                    shareIntent.putExtra("proofNumber", proofNumber);
                    shareIntent.putExtra("regnLinkId", ghid);
                    shareIntent.putExtra("gender", patientGender);
                    shareIntent.putExtra("email", getIntent().getExtras().getString("email"));
                    shareIntent.putExtra("dob", patientDob);
                    shareIntent.putExtra("phone", getIntent().getExtras().getString("phone"));
                    startActivity(shareIntent);
                }
            });
        }
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();

        if(caseRecordData != null && caseRecordData.getCaseRecordsList().size()>0)
        {
            HashMap<String, String> commonData = caseRecordData.getCommonData();
            List<FileAttachmentDTO> attachments = new Gson().fromJson(commonData.get("attachement"), new TypeToken<List<FileAttachmentDTO>>(){}.getType());
            caseRecordNo = commonData.get("caseRecordNo");
            hrid = commonData.get("healthRegistrationId");
            ghid = commonData.get("regnLinkId");
            patientName = commonData.get("patientName");
            proofType = commonData.get("proofType");
            proofNumber = commonData.get("proofNumber");
            TextView crTxtView = (TextView) findViewById(R.id.crno);
            TextView hridTxtView = (TextView) findViewById(R.id.hrid);
            TextView ghidTxtView = (TextView) findViewById(R.id.ghid);
            TextView pdTxtView = (TextView) findViewById(R.id.prvdiag);
            TextView fdTxtView = (TextView) findViewById(R.id.fnldiag);
            TextView patientNameTxtView = (TextView) findViewById(R.id.patientName);
            TextView dateTimeTxtView = (TextView) findViewById(R.id.dateTime);
            crTxtView.setText(caseRecordNo);
            hridTxtView.setText(hrid);
            ghidTxtView.setText(ghid);
            patientNameTxtView.setText(patientName);
            dateTimeTxtView.setText(Util.convertUnixTimeToDate(commonData.get("createdDate")));
            String provisionalDiagnosis = commonData.get("provisionalDiagnosis");
            String finalDiagnosis = commonData.get("finalDiagnosis");
            if(!provisionalDiagnosis.isEmpty())
                pdTxtView.setText(provisionalDiagnosis);
            else pdTxtView.setText("    -");
            if(!finalDiagnosis.isEmpty())
                fdTxtView.setText(finalDiagnosis);
            else fdTxtView.setText("    -");

            List<Object> caseRecordsList = caseRecordData.getCaseRecordsList();
            for (Object caseRecords : caseRecordsList)
            {
                Map<String, Object> caseRecord = (Map<String, Object>) caseRecords;
                String practiceFormName = (String) caseRecord.get("practiceFormName");
                String crNo = (String) caseRecord.get("caseRecordNo");
                String formId = (String) caseRecord.get("formId");
                if(formList != null && formList.contains(practiceFormName)){
                    Map<String, String> caseRecordFormData = (Map<String, String>) caseRecord.get("caseRecordData");
                    if(caseRecordFormData != null) {
                        listDataHeader.add(new Form(formId, caseRecordFormData.get("displayFormName"), practiceFormName, caseRecordFormData));
                    }
                }
            }
            if(attachments != null && attachments.size() > 0) {
                listDataHeader.add(new Form("", "Upload Files", "uploadfiles", attachments));
            }
        }
        listAdapter = new ExpandableCaseRecordFormsListAdapter(this, listDataHeader);
        expListView.setAdapter(listAdapter);
    }

    private void getCaseRecordForms() {
        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl",serverUrl);
        nameValues.put("operationType", "8");
        nameValues.put("caserecordno", getIntent().getExtras().getString("caserecordno"));

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                CaseRecordFormsActivity.this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                HashMap<String, Object> hmres = new Gson().fromJson(result.get("result"), new TypeToken<HashMap<String, Object>>(){}.getType());
                if (hmres != null && !hmres.isEmpty() && "success".equals(hmres.get("status"))) {
                    try {
                        caseRecordData = new Gson().fromJson(result.get("result"), CaseRecordDataDTO.class);
                        prepareListData();
                    }
                    catch (Exception e){
                        Log.e(TAG, "Exception", e);
                    }
                } else {
                    new AlertDialog.Builder(CaseRecordFormsActivity.this).setTitle("Error")
                            .setMessage(result.get("error")).show();
                }
            }
        }, "Retrieving Case Records");
        worker.execute(nameValues);
    }

    private void getFormNames() {
        Map<String,String> nameValues = new HashMap<>();

        nameValues.put("serverUrl", formServerUrl);
        nameValues.put("operationType", "7");

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(this, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (result.get("result") != null && !result.isEmpty() ) {

                    try {
                        FormNamesDTO formNamesDTO = new Gson().fromJson(result.get("result"), FormNamesDTO.class);
                        if(formNamesDTO != null && formNamesDTO.getFormNamesList() != null
                                && formNamesDTO.getFormNamesList().size()>0 ) {
                            formsList = formNamesDTO.getFormNamesList();
                            if(ageInMonths > 216){
                                for(FormFieldsDTO fdto : formsList){
                                    if("schoolhealthcarescreening".equalsIgnoreCase(fdto.getFormName()))
                                        formsList.remove(fdto);
                                }
                            }
                        }
                        //listView.setAdapter(adapter);
                    }
                    catch (Exception e){

                    }
                } else {
                    new AlertDialog.Builder(CaseRecordFormsActivity.this)
                            .setTitle("Error")
                            .setMessage(result.get("error"))
                            .show();
                }

            }
        }, "Retrieving Forms");
        worker.execute(nameValues);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        int i=0;
        menu.setHeaderTitle("Select Form");
        menu.setHeaderIcon(R.drawable.form);
        for(FormFieldsDTO dto : formsList)
            menu.add(Menu.NONE, v.getId(), i++, dto.getDisplayFormName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        formName = formsList.get(item.getOrder()).getFormName();
        Intent formintent = null;
        switch (formName)
        {
            case "historytaking":
                formintent = new Intent(getApplicationContext(), HealthHistory.class);
                break;

            case "dentalassessment":
                formintent = new Intent(getApplicationContext(), DentalAssessment.class);
                break;

            case "generalhealthscreening":
                formintent = new Intent(getApplicationContext(), GeneralHealthScreening.class);
                break;

            case "schoolhealthcarescreening":
                if(ageInMonths>216)
                    Toast.makeText(this, "The Patient is not Eligible to Fill This Form", Toast.LENGTH_SHORT).show();
                else {
                    formintent = new Intent(getApplicationContext(), SchoolHealthcareScreening.class);
                    formintent.putExtra("ageInMonths", ageInMonths);
                }
                break;

            case "generalphysicalexamination":
                formintent = new Intent(getApplicationContext(), GeneralExaminationActivity.class);
                break;

            case "dischargesheet":
                formintent = new Intent(getApplicationContext(), DischargeSheetActivity.class);
                break;

            case "investigationbiochemistry":
                formintent = new Intent(getApplicationContext(), InvestigationBioChemistry.class);
                break;

            case "investigationmicrobiology":
                formintent = new Intent(getApplicationContext(), InvestigationMicrobiology.class);
                break;

            case "investigationpathology":
                formintent = new Intent(getApplicationContext(), InvestigationPathology.class);
                break;

            default:
                Toast.makeText(this, "Form Under Development", Toast.LENGTH_SHORT).show();
                break;
        }

        if(formintent != null) {
            formintent.putExtra("formName", formName);
            formintent = populateIntentwithExtras(formintent);
            startActivity(formintent);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && PICK_FILE_REQUEST == requestCode && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            final String filepath = Util.getPath(getApplicationContext(), selectedImageUri);
            final String fileName = new File(filepath).getName();

            if(filepath != null && !filepath.isEmpty()) {
                new AlertDialog.Builder(this).setTitle(fileName).setMessage("Do you want to upload file").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upload(filepath);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        }
    }

    public Intent populateIntentwithExtras(Intent intent){
        intent.putExtra("caseid", caseRecordNo);
        intent.putExtra("regid", getIntent().getExtras().getString("regid"));
        intent.putExtra("name", getIntent().getExtras().getString("name"));
        intent.putExtra("regLinkId", getIntent().getExtras().getString("regLinkId"));
        intent.putExtra("proofType", getIntent().getExtras().getString("proofType"));
        intent.putExtra("proofNumber", getIntent().getExtras().getString("proofNumber"));
        intent.putExtra("dob", getIntent().getExtras().getString("dob"));
        intent.putExtra("phone", getIntent().getExtras().getString("phone"));
        intent.putExtra("email", getIntent().getExtras().getString("email"));
        intent.putExtra("gender", patientGender);
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
                    CaseRecordFormsActivity.this, new ActionCallback() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caserecord_forms, menu);
        return true;
    }

    private int getAgeInMonths(String dateOfBirth){
        final SimpleDateFormat formats[] = {new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("dd/MM/yyyy")};
        Calendar currentDateCalendar = Calendar.getInstance();
        Calendar birthDateCalendar = Calendar.getInstance();
        int diffInYears, diffInMonths, diffInDays;
        diffInYears = diffInMonths = diffInDays = 0;
        for(SimpleDateFormat sdf : formats) {
            try {
                birthDateCalendar.setTime(sdf.parse(dateOfBirth));
                diffInYears = currentDateCalendar.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR);
                diffInMonths = (diffInYears * 12) + currentDateCalendar.get(Calendar.MONTH) - birthDateCalendar.get(Calendar.MONTH);
                diffInDays = currentDateCalendar.get(Calendar.DAY_OF_MONTH) + birthDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDateCalendar.get(Calendar.DAY_OF_MONTH);
                if (diffInDays > 0) diffInMonths++;
                else if(diffInYears<0 || diffInMonths<0 || diffInDays<0) diffInMonths = 1000;
                break;
            } catch (ParseException e) {
                Log.e(TAG, "Date parse Exception", e);
            }
        }
        return diffInMonths;
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
        Intent patientDetail = new Intent(getApplicationContext(), PatientDetail.class);
        patientDetail.putExtra("regid", getIntent().getExtras().getString("regid"));
        patientDetail.putExtra("name", getIntent().getExtras().getString("name"));
        patientDetail.putExtra("phone", getIntent().getExtras().getString("phone"));
        patientDetail.putExtra("email", getIntent().getExtras().getString("email"));
        patientDetail.putExtra("proofType", getIntent().getExtras().getString("proofType"));
        patientDetail.putExtra("proofNumber", getIntent().getExtras().getString("proofNumber"));
        patientDetail.putExtra("dob", getIntent().getExtras().getString("dob"));
        patientDetail.putExtra("gender", getIntent().getExtras().getString("gender"));
        patientDetail.putExtra("reqLinkId", getIntent().getExtras().getString("regLinkId"));
        startActivity(patientDetail);
        finish();
    }

    private void upload(String filepath){
        String serverUrl2 = ServerUtil.serverUrl+ "VCRegionalAPP/rest/caserecordinsert/upload/" + caseRecordNo;
        Map<String, String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl2);
        nameValues.put("operationType", "11");
        nameValues.put("inputfile", filepath);
        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(this, new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))){

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("File Uploaded Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getApplicationContext(),
                                            CaseRecordFormsActivity.class);
                                    caseRecordsActivity.putExtra("regid", getIntent().getExtras().getString("regid"));
                                    caseRecordsActivity.putExtra("name", patientName);
                                    caseRecordsActivity.putExtra("phone", getIntent().getExtras().getString("phone"));
                                    caseRecordsActivity.putExtra("dob", getIntent().getExtras().getString("dob"));
                                    caseRecordsActivity.putExtra("email", getIntent().getExtras().getString("email"));
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", patientGender);
                                    caseRecordsActivity.putExtra("reqLinkId", getIntent().getExtras().getString("regLinkId"));
                                    caseRecordsActivity.putExtra("caserecordno", caseRecordNo);
                                    caseRecordsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(caseRecordsActivity);
                                    finish();
                                }
                            }).show();
                        }
                        else{
                            new AlertDialog.Builder(getBaseContext())
                                    .setTitle("Error")
                                    .setMessage("Failed to upload file")
                                    .show();
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Exception", ex);
                    }
                }
                else{
                    new AlertDialog.Builder(getBaseContext())
                            .setTitle("Error")
                            .setMessage("Failed to upload file")
                            .show();
                }
            }
        }, "Uploading File");
        worker.execute(nameValues);
    }

    private void initForms(){
        formList = new ArrayList<>();
        formList.add("dentalassessment");
        formList.add("generalhealthscreening");
        formList.add("historytaking");
        formList.add("generalphysicalexamination");
        formList.add("dischargesheet");
        formList.add("investigationmicrobiology");
        formList.add("investigationbiochemistry");
        formList.add("investigationpathology");
    }
}
