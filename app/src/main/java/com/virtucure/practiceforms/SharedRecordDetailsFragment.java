package com.virtucure.practiceforms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virtucare.practiceforms.dto.SharedCaseRecordsDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AJITI on 18-Jul-16.
 */
public class SharedRecordDetailsFragment extends Fragment {

    private static final String TAG = SharedRecordDetailsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView emptyRecordsView;
    private SharedRecordDetailsAdapter adapter;
    private List<SharedCaseRecordsDTO> sharedRecords;
    private FragmentActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSharedRecords();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_referred_patient_administrations_list, container, false);

        emptyRecordsView = (TextView) rootView.findViewById(R.id.emptyRecords);
        emptyRecordsView.setText(getResources().getString(R.string.info_share_empty_records));
        emptyRecordsView.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.administrations_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        return rootView;

    }

    private void getSharedRecords() {

        String serverUrl = ServerUtil.serverUrl+"VCRegionalAPP/rest/getSharedRecords";

        final Map<String,String> nameValues = new HashMap<>();
        nameValues.put("serverUrl", serverUrl);
        nameValues.put("operationType", "14");

        AsyncWorkerNetwork worker = new AsyncWorkerNetwork(
                activity, new ActionCallback() {
            public void onCallback(Map<String,String> result) {
                if (!result.isEmpty() && result.get("result") != null) {
                    try {
                        sharedRecords = new Gson().fromJson(result.get("result"), new TypeToken<List<SharedCaseRecordsDTO>>(){}.getType());
                        if(sharedRecords != null && !sharedRecords.isEmpty()) {
                            emptyRecordsView.setVisibility(View.GONE);
                            adapter = new SharedRecordDetailsAdapter(activity, sharedRecords);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    catch (Exception e){
                        Log.e(TAG, "Exception", e);
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setTitle("Error")
                            .setMessage("Failed To Get Records")
                            .show();
                }
            }
        }, "Retrieving Shared Records");
        worker.execute(nameValues);
    }

}
