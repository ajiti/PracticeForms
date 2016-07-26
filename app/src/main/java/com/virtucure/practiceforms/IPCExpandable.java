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

/**
 * Created by pc on 7/5/2016.
 */
public class IPCExpandable extends BaseExpandableListAdapter {

    private static final String TAG = IPCExpandable.class.getSimpleName();
    private Context context;
    private List<String> listDataHeaderTitles;
    private LinearLayout aLayout, bLayout;
    private View basicView;
    FormListener listener;

    public interface FormListener {
        void onFormSaveListener(View view);
    }

    public IPCExpandable(Context context, List<String> listDataHeaderTitles, FormListener listener) {
        this.context = context;
        this.listDataHeaderTitles = listDataHeaderTitles;
        this.listener=listener;
        try{
            basicView = View.inflate(context, R.layout.activity_investigation_pathlogy, null);
            if (basicView != null) {
                aLayout = (LinearLayout) basicView.findViewById(R.id.hamatology_block);
                bLayout = (LinearLayout) basicView.findViewById(R.id.clinical_block);
                listener.onFormSaveListener(basicView);
            }
        }catch(Exception e){
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
    }

    @Override
    public int getGroupCount() {
        return (listDataHeaderTitles != null) ? listDataHeaderTitles.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeaderTitles.get(groupPosition);
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
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.caserecordforms_list_group, null);
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
        if(groupPosition == 0){
            aLayout.setVisibility(View.VISIBLE);
            bLayout.setVisibility(View.GONE);
        }
        else if(groupPosition == 1){
            aLayout.setVisibility(View.GONE);
            bLayout.setVisibility(View.VISIBLE);
        }
        basicView.invalidate();
        return basicView;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    } {

    }
}
