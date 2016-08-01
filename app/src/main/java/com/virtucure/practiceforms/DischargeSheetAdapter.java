package com.virtucure.practiceforms;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DischargeSheetAdapter extends BaseExpandableListAdapter
{
    private List<String> listDataHeader;
    private Context context;
    private View basicView;
    private LinearLayout admissonLayout, dischargeLayout, clinicLayout, adviceLayout;
    FormListener listener;

    public interface FormListener {
        void onFormSaveListener(View view);
    }

    public DischargeSheetAdapter(Context context,List<String> listDataHeader, FormListener listener)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listener = listener;
        try {
            basicView = View.inflate(context, R.layout.activity_discharge_sheet, null);
            if(basicView != null) {
                admissonLayout = (LinearLayout) basicView.findViewById(R.id.admission_Details);
                dischargeLayout = (LinearLayout) basicView.findViewById(R.id.discharge_Details);
                clinicLayout = (LinearLayout) basicView.findViewById(R.id.clinicInformation);
                adviceLayout = (LinearLayout) basicView.findViewById(R.id.advice_recommendations_futureplan);
                listener.onFormSaveListener(basicView);
            }
        }
        catch (Exception e) {
            Log.e("Exception", e.getLocalizedMessage());
        }
    }

    @Override
    public int getGroupCount() {
        return (listDataHeader != null) ? listDataHeader.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.caserecordforms_list_group, null);
            convertView.findViewById(R.id.formShareCheckBox).setVisibility(View.GONE);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        lblListHeader.setBackgroundColor(Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(groupPosition == 0) {
            admissonLayout.setVisibility(View.VISIBLE);
            dischargeLayout.setVisibility(View.GONE);
            clinicLayout.setVisibility(View.GONE);
            adviceLayout.setVisibility(View.GONE);
        }else if(groupPosition == 1){
            dischargeLayout.setVisibility(View.VISIBLE);
            admissonLayout.setVisibility(View.GONE);
            clinicLayout.setVisibility(View.GONE);
            adviceLayout.setVisibility(View.GONE);
        }else if(groupPosition == 2){
            clinicLayout.setVisibility(View.VISIBLE);
            dischargeLayout.setVisibility(View.GONE);
            admissonLayout.setVisibility(View.GONE);
            adviceLayout.setVisibility(View.GONE);
        }else if(groupPosition == 3){
            adviceLayout.setVisibility(View.VISIBLE);
            clinicLayout.setVisibility(View.GONE);
            dischargeLayout.setVisibility(View.GONE);
            admissonLayout.setVisibility(View.GONE);
        }
        basicView.invalidate();
        return basicView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
