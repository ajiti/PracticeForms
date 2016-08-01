package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
public class CaseRecordFormsFragment extends Fragment {

    private static final String TAG = CaseRecordFormsFragment.class.getSimpleName();
    private static final String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getcaserecordsdata";
    private String formServerUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/assmntform";
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
    private int lastExpandedPosition = -1;
    private int ageInMonths;
    private String patientGender;
    private String hrid;
    private String patientName;
    private String ghid;
    private String proofNumber;
    private String proofType;
    private String dob;
    private String email;
    private String phone;
    private String regid;
    private String regLinkId;
    private List<String> formList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initForms();
        getFormNames();

        Bundle args = getArguments();
        patientGender = args.getString("gender");
        email = args.getString("email");
        dob = args.getString("dob");
        phone = args.getString("phone");
        caseRecordNo = args.getString("caserecordno");
        regid = args.getString("regid");
        patientName = args.getString("name");
        regLinkId = args.getString("regLinkId");
        proofType = args.getString("proofType");
        proofNumber = args.getString("proofNumber");

        View rootView = inflater.inflate(R.layout.caserecordsform_layout, container, false);

        getCaseRecordForms(rootView);

        ageInMonths = getAgeInMonths(dob);
        expListView = (ExpandableListView) rootView.findViewById(R.id.formsExp);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
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

        formListView = (ListView) rootView.findViewById(R.id.formnamesList);

        Button addFormBtn = (Button) rootView.findViewById(R.id.addfrms);
        addFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(formListView);
                getActivity().openContextMenu(formListView);
            }
        });

        Button uploadBtn = (Button) rootView.findViewById(R.id.fileUploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Select location"), PICK_FILE_REQUEST);
            }
        });

        shareBtn = (FloatingActionButton) rootView.findViewById(R.id.formShareBtn);
        if(shareBtn != null) {
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listDataHeader == null || listDataHeader.isEmpty()) {
                        Toast.makeText(getContext(), "No Forms To Share", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ArrayList<String> formIds = new ArrayList<>();
                    for (Form form : listDataHeader) {
                        if ( form != null && form.isSelected()) {
                            formIds.add(form.getFormId());
                        }
                    }

                    if (formIds.size() == 0) {
                        new AlertDialog.Builder(getContext()).setTitle("Alert").setMessage("Select Forms To Share")
                                .setCancelable(false).setPositiveButton("Ok", null).show();
                        return;
                    }

                    Intent shareIntent = new Intent(getActivity(), FormShareActivity.class);
                    shareIntent.putExtra("caseRecordNo", caseRecordNo);
                    shareIntent.putStringArrayListExtra("formIdArray", formIds);
                    shareIntent.putExtra("healthRegistrationId", hrid);
                    shareIntent.putExtra("patientName", patientName);
                    shareIntent.putExtra("proofType", proofType);
                    shareIntent.putExtra("proofNumber", proofNumber);
                    shareIntent.putExtra("regnLinkId", ghid);
                    shareIntent.putExtra("gender", patientGender);
                    shareIntent.putExtra("email", email);
                    shareIntent.putExtra("dob", dob);
                    shareIntent.putExtra("phone", phone);
                    startActivity(shareIntent);
                }
            });
        }

        return rootView;
    }

    private void prepareListData(View rootView) {
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
            TextView crTxtView = (TextView) rootView.findViewById(R.id.crno);
            TextView hridTxtView = (TextView) rootView.findViewById(R.id.hrid);
            TextView ghidTxtView = (TextView) rootView.findViewById(R.id.ghid);
            TextView pdTxtView = (TextView) rootView.findViewById(R.id.prvdiag);
            TextView fdTxtView = (TextView) rootView.findViewById(R.id.fnldiag);
            TextView patientNameTxtView = (TextView) rootView.findViewById(R.id.patientName);
            TextView dateTimeTxtView = (TextView) rootView.findViewById(R.id.dateTime);
            crTxtView.setText(caseRecordNo);
            hridTxtView.setText(hrid);
            ghidTxtView.setText(ghid);
            patientNameTxtView.setText(patientName);
            dateTimeTxtView.setText(Util.convertUnixTimeToDate(commonData.get("createdDate")));
            String provisionalDiagnosis = commonData.get("provisionalDiagnosis");
            String finalDiagnosis = commonData.get("finalDiagnosis");

            pdTxtView.setText(!provisionalDiagnosis.isEmpty() ? provisionalDiagnosis : "    -");
            fdTxtView.setText(!finalDiagnosis.isEmpty() ? finalDiagnosis : "    -");

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
            if(attachments != null && !attachments.isEmpty()) {
                listDataHeader.add(new Form("", "Upload Files", "uploadfiles", attachments));
            }
        }
        listAdapter = new ExpandableCaseRecordFormsListAdapter(getContext(), listDataHeader);
        expListView.setAdapter(listAdapter);
    }

    private void getCaseRecordForms(final View rootView) {
        Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl",serverUrl);
        nameValues.put("operationType", "8");
        nameValues.put("caserecordno", caseRecordNo);

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                getContext(), new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                HashMap<String, Object> hmres = new Gson().fromJson(result.get("result"), new TypeToken<HashMap<String, Object>>(){}.getType());
                if (hmres != null && !hmres.isEmpty() && "success".equals(hmres.get("status"))) {
                    try {
                        caseRecordData = new Gson().fromJson(result.get("result"), CaseRecordDataDTO.class);
                        prepareListData(rootView);
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
                } else {
                    new AlertDialog.Builder(getContext()).setTitle("Error")
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

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(getContext(), new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (result.get("result") != null && !result.isEmpty() ) {

                    try {
                        FormNamesDTO formNamesDTO = new Gson().fromJson(result.get("result"), FormNamesDTO.class);
                        if(formNamesDTO != null && formNamesDTO.getFormNamesList() != null
                                && !formNamesDTO.getFormNamesList().isEmpty() ) {
                            formsList = formNamesDTO.getFormNamesList();
                            if(ageInMonths > 216){
                                for(FormFieldsDTO fdto : formsList){
                                    if("schoolhealthcarescreening".equalsIgnoreCase(fdto.getFormName())) {
                                        formsList.remove(fdto);
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
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
        if(formList == null && formList.isEmpty()) {
            Toast.makeText(getContext(), "No Forms To Add", Toast.LENGTH_SHORT).show();
            return;
        }
        for(FormFieldsDTO dto : formsList){
            if(dto != null){
                menu.add(Menu.NONE, v.getId(), i++, dto.getDisplayFormName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        formName = formsList.get(item.getOrder()).getFormName();
        Intent formintent = null;
        switch (formName)
        {
            case "historytaking":
                formintent = new Intent(getContext(), HealthHistory.class);
                break;

            case "dentalassessment":
                formintent = new Intent(getContext(), DentalAssessment.class);
                break;

            case "generalhealthscreening":
                formintent = new Intent(getContext(), GeneralHealthScreening.class);
                break;

            case "schoolhealthcarescreening":
                if(ageInMonths>216)
                    Toast.makeText(getContext(), "The Patient is not Eligible to Fill This Form", Toast.LENGTH_SHORT).show();
                else {
                    formintent = new Intent(getContext(), SchoolHealthcareScreening.class);
                    formintent.putExtra("ageInMonths", ageInMonths);
                }
                break;

            case "generalphysicalexamination":
                formintent = new Intent(getContext(), GeneralExaminationActivity.class);
                break;

            case "dischargesheet":
                formintent = new Intent(getContext(), DischargeSheetActivity.class);
                break;

            case "investigationbiochemistry":
                formintent = new Intent(getContext(), InvestigationBioChemistry.class);
                break;

            case "investigationmicrobiology":
                formintent = new Intent(getContext(), InvestigationMicrobiology.class);
                break;

            case "investigationpathology":
                formintent = new Intent(getContext(), InvestigationPathology.class);
                break;

            default:
                Toast.makeText(getContext(), "Form Under Development", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && PICK_FILE_REQUEST == requestCode && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            final String filepath = Util.getPath(getContext(), selectedImageUri);
            final String fileName = new File(filepath).getName();

            if(filepath != null && !filepath.isEmpty()) {
                new AlertDialog.Builder(getContext()).setTitle(fileName).setMessage("Do you want to upload file").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
        intent.putExtra("regid", regid);
        intent.putExtra("name", patientName);
        intent.putExtra("regLinkId", regLinkId);
        intent.putExtra("proofType", proofType);
        intent.putExtra("proofNumber", proofNumber);
        intent.putExtra("dob", dob);
        intent.putExtra("phone", phone);
        intent.putExtra("email", email);
        intent.putExtra("gender", patientGender);
        return  intent;
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

    private void upload(String filepath){
        String serverUrl2 = ServerUtil.serverUrl+ "VCRegionalAPP/rest/caserecordinsert/upload/" + caseRecordNo;
        Map<String, String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl2);
        nameValues.put("operationType", "11");
        nameValues.put("inputfile", filepath);
        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(getContext(), new ActionCallback() {
            public void onCallback(Map<String, String> result) {
                if (result.get("result") != null && !result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("result"));
                        if("success".equalsIgnoreCase(jsonObject.getString("status"))){

                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());

                            alertBuilder.setTitle("Success");
                            alertBuilder.setMessage("File Uploaded Successfully").setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent caseRecordsActivity = new Intent(
                                            getContext(),
                                            CaseRecordFormsMainActivity.class);
                                    caseRecordsActivity.putExtra("regid", regid);
                                    caseRecordsActivity.putExtra("name", patientName);
                                    caseRecordsActivity.putExtra("phone", phone);
                                    caseRecordsActivity.putExtra("dob", dob);
                                    caseRecordsActivity.putExtra("email", email);
                                    caseRecordsActivity.putExtra("proofType", proofType);
                                    caseRecordsActivity.putExtra("proofNumber", proofNumber);
                                    caseRecordsActivity.putExtra("gender", patientGender);
                                    caseRecordsActivity.putExtra("regLinkId", regLinkId);
                                    caseRecordsActivity.putExtra("caserecordno", caseRecordNo);
                                    caseRecordsActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(caseRecordsActivity);
                                    getActivity().finish();
                                }
                            }).show();
                        } else{
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Error")
                                    .setMessage("Failed to upload file")
                                    .show();
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Exception", ex);
                    }
                }
                else{
                    new AlertDialog.Builder(getContext())
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
