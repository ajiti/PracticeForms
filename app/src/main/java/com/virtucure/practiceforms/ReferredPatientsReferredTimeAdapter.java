package com.virtucure.practiceforms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.virtucare.practiceforms.dto.ReferredPatientHridCaseRecordDTO;
import com.virtucare.practiceforms.dto.ReferredPatientReferredTimeDTO;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AJITI on 6/29/2016.
 */
public class ReferredPatientsReferredTimeAdapter extends RecyclerView.Adapter<ReferredPatientsReferredTimeAdapter.ViewHolder> {

    private List<ReferredPatientReferredTimeDTO> referredTimeList;
    private final OnRecyclerItemClickListener listener;

    public ReferredPatientsReferredTimeAdapter(List<ReferredPatientReferredTimeDTO> referredTimeList, OnRecyclerItemClickListener listener) {
        this.referredTimeList = referredTimeList;
        this.listener = listener;
    }

    @Override
    public ReferredPatientsReferredTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_referred_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReferredPatientsReferredTimeAdapter.ViewHolder holder, int position) {
        holder.bind(referredTimeList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return (referredTimeList != null) ? referredTimeList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView referredTime;
        private TextView referredCount;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
                referredTime = (TextView) itemView.findViewById(R.id.referred_time);
                referredCount = (TextView) itemView.findViewById(R.id.referred_count);
                referredCount.bringToFront();
            }
        }

        public void bind(final ReferredPatientReferredTimeDTO item, final OnRecyclerItemClickListener listener) {

            if(item != null && itemView != null) {
                item.setFormIdsArray(new Gson().fromJson(item.getFormIdsList(), Integer[].class));
                referredTime.setText(Util.convertUnixTimeToDate(item.getSharedDate()));
                referredCount.setText(item.getFormIdsArray().length+"");

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
