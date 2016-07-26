package com.virtucure.practiceforms;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.SharedCaseRecordsDTO;
import com.virtucare.practiceforms.dto.SharedIdDTO;
import com.virtucare.practiceforms.dto.SharedToMemberDetailsDTO;

import java.util.EmptyStackException;
import java.util.List;

public class SharedRecordDetailsAdapter extends RecyclerView.Adapter<SharedRecordDetailsAdapter.ViewHolder> {

    private static final String TAG = SharedRecordDetailsAdapter.class.getSimpleName();
    private Context context;
    private List<SharedCaseRecordsDTO> sharedRecords;
    private View popupView;
    private TextView popupCaseRecordView;
    private TextView popupPatientView;
    private ImageButton popupClose;
    private ListView popupListView;
    private PopupWindow popupWindow;
    private SharedMembersListAdapter adapter;
    private List<SharedIdDTO> sharedMembers;

    public SharedRecordDetailsAdapter(Context context, List<SharedCaseRecordsDTO> sharedRecords){
        this.context = context;
        this.sharedRecords = sharedRecords;
        initPopupWindow();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_patient_records_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(sharedRecords.get(position));
    }

    @Override
    public int getItemCount() {
        return (sharedRecords == null) ? 0 : sharedRecords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView caseRecordView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null){
                caseRecordView = (TextView) itemView.findViewById(R.id.caseRecordNo);
            }
        }

        public void bind(final SharedCaseRecordsDTO sharedCaseRecordsDTO) {
            final String caseId = sharedCaseRecordsDTO.getCaseRecordNo();
            caseRecordView.setText(caseId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sharedMembers = sharedCaseRecordsDTO.getMembersList();
                        SharedToMemberDetailsDTO dto = sharedMembers.get(sharedMembers.size()-1).getSharedMembers().get(0);
                        popupPatientView.setText(context.getString(R.string.shared_name_string, dto.getFullName(), dto.getSharedTo()).toLowerCase());
                        adapter = new SharedMembersListAdapter(context, sharedMembers);
                        popupListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                        popupCaseRecordView.setText(caseId);
                        popupClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                    }catch (Exception e){
                        Log.e(TAG, e.getLocalizedMessage(), e);
                    }
                }
            });
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = null;
        if(context != null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(inflater != null){
            popupView = inflater.inflate(R.layout.shared_record_popup, null);
            if(popupView != null){
                popupCaseRecordView = (TextView) popupView.findViewById(R.id.caseRecordNo);
                popupPatientView = (TextView) popupView.findViewById(R.id.patientId);
                popupClose = (ImageButton) popupView.findViewById(R.id.close);
                popupListView = (ListView) popupView.findViewById(R.id.membersList);

                popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//                popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, 700, true);
                if(popupWindow != null){
                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(false);
                    popupWindow.setAnimationStyle(R.style.Animation);
                }
            }
        }
    }
}
