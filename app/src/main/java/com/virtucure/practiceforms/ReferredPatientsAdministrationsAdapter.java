package com.virtucure.practiceforms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.ReferredPatientsDTO;

import java.util.List;

/**
 * Created by AJITI on 6/28/2016.
 */
public class ReferredPatientsAdministrationsAdapter extends RecyclerView.Adapter<ReferredPatientsAdministrationsAdapter.ViewHolder> {

    private static final String TAG = "ReferredPatientsAdapter";
    private final List<ReferredPatientsDTO> referredAdminsList;
    private final OnRecyclerItemClickListener listener;
    private final Context context;

    public ReferredPatientsAdministrationsAdapter(Context context, List<ReferredPatientsDTO> referredAdminsList, OnRecyclerItemClickListener listener) {
        this.referredAdminsList = referredAdminsList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ReferredPatientsAdministrationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_administration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferredPatientsAdministrationsAdapter.ViewHolder holder, int position) {
        try {
            holder.bind(referredAdminsList.get(position), listener);
        }
        catch (Exception e) {
            Log.e(TAG, "Getting Administrations for Referred Patients", e);
        }
    }

    @Override
    public int getItemCount() {
        return (referredAdminsList != null) ? referredAdminsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView branchCount;
        public TextView administrationName;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
                branchCount = (TextView) itemView.findViewById(R.id.branch_count);
                branchCount.bringToFront();
                administrationName = (TextView) itemView.findViewById(R.id.administration_name);
            }
        }

        public void bind(final ReferredPatientsDTO item, final OnRecyclerItemClickListener listener) {
            if(item != null && itemView != null) {
                administrationName.setText(item.getDisplayProjName());
                branchCount.setText(item.getBrandorBranchNameCount().size()+"");
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
