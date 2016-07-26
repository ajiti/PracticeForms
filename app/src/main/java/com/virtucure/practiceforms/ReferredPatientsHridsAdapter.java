package com.virtucure.practiceforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.ReferredPatientHridDTO;
import com.virtucare.practiceforms.dto.ReferredPatientsDTO;

import java.util.List;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientsHridsAdapter extends RecyclerView.Adapter<ReferredPatientsHridsAdapter.ViewHolder> {

    private List<ReferredPatientHridDTO> hridsList;
    private final OnRecyclerItemClickListener listener;

    public ReferredPatientsHridsAdapter(List<ReferredPatientHridDTO> hridsList, OnRecyclerItemClickListener listener) {
        this.hridsList = hridsList;
        this.listener = listener;
    }

    @Override
    public ReferredPatientsHridsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_hrid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferredPatientsHridsAdapter.ViewHolder holder, int position) {
        holder.bind(hridsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (hridsList != null) ? hridsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView hrid;
        public TextView emailId;
        public TextView mobileNo;
        private TextView caseRecordCount;
        private TextView userName;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
                userName = (TextView) itemView.findViewById(R.id.userName);
                name = (TextView) itemView.findViewById(R.id.name);
                hrid = (TextView) itemView.findViewById(R.id.hrid);
                emailId = (TextView) itemView.findViewById(R.id.emailId);
                mobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
                caseRecordCount = (TextView) itemView.findViewById(R.id.caseRecord_count);
                caseRecordCount.bringToFront();
            }
        }

        public void bind(final ReferredPatientHridDTO item, final OnRecyclerItemClickListener listener) {

            if(item != null && itemView != null) {
                userName.setText(item.getUserName());
                name.setText(item.getFirstName() + " " + item.getLastName());
                userName.setText(item.getUserName());
                emailId.setText(item.getEmailId());
                mobileNo.setText(item.getMobileNo());
                hrid.setText(item.getHrid());
                caseRecordCount.setText(item.getCaseRecordNoCount().size()+"");

                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onRecyclerItemClick(item);
                    }
                });

            }
        }

    }
}
