package com.virtucure.practiceforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.ReferredPatientsDTO;

import java.util.List;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientsBranchesAdapter extends RecyclerView.Adapter<ReferredPatientsBranchesAdapter.ViewHolder> {

    private List<ReferredPatientsDTO> branchesList;
    private final OnRecyclerItemClickListener listener;

    public ReferredPatientsBranchesAdapter(List<ReferredPatientsDTO> branchesList, OnRecyclerItemClickListener listener) {
        this.branchesList = branchesList;
        this.listener = listener;
    }

    @Override
    public ReferredPatientsBranchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_branch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferredPatientsBranchesAdapter.ViewHolder holder, int position) {
        holder.bind(branchesList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (branchesList != null) ? branchesList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView userName;
        public TextView emailId;
        public TextView mobileNo;
        public TextView address;
        private TextView hridCount;
        private TextView branchName;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
                branchName = (TextView) itemView.findViewById(R.id.branch_name);
                name = (TextView) itemView.findViewById(R.id.name);
                userName = (TextView) itemView.findViewById(R.id.userName);
                emailId = (TextView) itemView.findViewById(R.id.emailId);
                mobileNo = (TextView) itemView.findViewById(R.id.mobileNo);
                address = (TextView) itemView.findViewById(R.id.address);
                hridCount = (TextView) itemView.findViewById(R.id.hrid_count);
                hridCount.bringToFront();
            }
        }

        public void bind(final ReferredPatientsDTO item, final OnRecyclerItemClickListener listener) {

            if(item != null && itemView != null) {
                branchName.setText(item.getBrandorBranchName().toUpperCase());
                name.setText(item.getFirstname() + " " + item.getLastname());
                userName.setText(item.getUserName());
                emailId.setText(item.getEmailid());
                mobileNo.setText(item.getMobilenumber());
                address.setText(item.getAddress());
                hridCount.setText(item.getHridCount().size()+"");

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
