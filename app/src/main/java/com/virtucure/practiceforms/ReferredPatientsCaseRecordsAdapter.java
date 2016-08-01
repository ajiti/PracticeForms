package com.virtucure.practiceforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.ReferredPatientHridCaseRecordDTO;

import java.util.List;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientsCaseRecordsAdapter extends RecyclerView.Adapter<ReferredPatientsCaseRecordsAdapter.ViewHolder> {

    private List<ReferredPatientHridCaseRecordDTO> caseRecordsList;
    private final OnRecyclerItemClickListener listener;

    public ReferredPatientsCaseRecordsAdapter(List<ReferredPatientHridCaseRecordDTO> caseRecordsList, OnRecyclerItemClickListener listener) {
        this.caseRecordsList = caseRecordsList;
        this.listener = listener;
    }

    @Override
    public ReferredPatientsCaseRecordsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_case_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferredPatientsCaseRecordsAdapter.ViewHolder holder, int position) {
        holder.bind(caseRecordsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (caseRecordsList != null) ? caseRecordsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        public TextView provisionalDiagnosis;
        private TextView formCount;
        private TextView caseRecordNo;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
//                provisionalDiagnosis = (TextView) itemView.findViewById(R.id.prov_diag);
                caseRecordNo = (TextView) itemView.findViewById(R.id.caseRecordNo);
                formCount = (TextView) itemView.findViewById(R.id.referred_count);
                formCount.bringToFront();
            }
        }

        public void bind(final ReferredPatientHridCaseRecordDTO item, final OnRecyclerItemClickListener listener) {

            if(item != null && itemView != null) {
//                provisionalDiagnosis.setText(checkIsEmpty(item.getProvisionalDiagnosis()));
                caseRecordNo.setText(item.getCaseRecordNo());
                formCount.setText(item.getSharedDateCount().size()+"");

                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onRecyclerItemClick(item);
                    }
                });

            }
        }

        private String checkIsEmpty(String string) {
            return (string != null && !string.isEmpty()) ? string : "-";
        }

    }
}
