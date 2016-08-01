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

public class GeneralExaminationAdapter extends BaseExpandableListAdapter
{
    private List<String> listDataHeader;
    private Context context;
    private View basicView;
    private LinearLayout gLayout, vLayout, aLayout;
    formListener listener;

    public interface formListener {
        void onFormSaveListener(View view);
    }

    public GeneralExaminationAdapter(Context context,List<String> listDataHeader, formListener listener) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listener = listener;
        try {
            basicView = View.inflate(context, R.layout.activity_general_examination, null);
            if(basicView != null) {
                gLayout = (LinearLayout) basicView.findViewById(R.id.general);
                vLayout = (LinearLayout) basicView.findViewById(R.id.vital);
                aLayout = (LinearLayout) basicView.findViewById(R.id.amthropometry);
                listener.onFormSaveListener(basicView);
            }
        } catch (Exception e) {
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
            gLayout.setVisibility(View.VISIBLE);
            vLayout.setVisibility(View.GONE);
            aLayout.setVisibility(View.GONE);
        }else if(groupPosition == 1){
            vLayout.setVisibility(View.VISIBLE);
            gLayout.setVisibility(View.GONE);
            aLayout.setVisibility(View.GONE);
        }else if(groupPosition == 2){
            aLayout.setVisibility(View.VISIBLE);
            vLayout.setVisibility(View.GONE);
            gLayout.setVisibility(View.GONE);
        }
        basicView.invalidate();
        return basicView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}


