package com.virtucure.practiceforms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.SharedIdDTO;
import com.virtucare.practiceforms.dto.SharedToMemberDetailsDTO;

import java.util.List;

/**
 * Created by AJITI on 16-Jul-16.
 */
public class SharedMembersListAdapter extends BaseAdapter {

    private Context context;
    private List<SharedIdDTO> sharedMembers;
    private TextView dateTimeView, patientView, doctorView;
    private List<SharedToMemberDetailsDTO> memberDetails;
    private static final String TAG = SharedMembersListAdapter.class.getSimpleName();

    public SharedMembersListAdapter(Context context, List<SharedIdDTO> sharedMembers) {
        this.context = context;
        this.sharedMembers = sharedMembers;
    }

    @Override
    public int getCount() {
        return (sharedMembers != null) ? sharedMembers.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return sharedMembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shared_members_layout, null);
            }

//        dateTimeView = (TextView) convertView.findViewById(R.id.dateTime);
//        patientView = (TextView) convertView.findViewById(R.id.patientId);
            doctorView = (TextView) convertView.findViewById(R.id.doctorId);

            memberDetails = ((SharedIdDTO) getItem(position)).getSharedMembers();
            String doctorId = "";
            for(SharedToMemberDetailsDTO dto : memberDetails){
//            dateTimeView.setText(Util.convertUnixTimeToDate(dto.getSharedDate()));
/*            if("patientid".equals(dto.getSharedType())) {
                patientView.setText(context.getResources().getString(R.string.shared_name_string, dto.getFirstName(), dto.getLastName(), dto.getSharedTo()));
                isFirst = Boolean.FALSE;
//                patientView.setText(dto.getFirstName() + " " + dto.getLastName() + " ( " + dto.getSharedTo() + " )");
            }
            else*/ if("cc".equals(dto.getSharedType())){
                    if(doctorId.isEmpty()){
                        doctorId = context.getString(R.string.shared_name_string, dto.getFullName(), dto.getSharedTo());
                    }
                    else {
                        doctorId += "\n" + context.getString(R.string.shared_name_string, dto.getFullName(), dto.getSharedTo());
                    }
                }
            }
            if(!doctorId.isEmpty()){
                doctorView.setText(doctorId.toLowerCase());
                return convertView;
            }
            else {
                return emptyView();
            }
        }
        catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage(), e);
            return emptyView();
        }
    }

    private View emptyView() {
        View v = new View(context);
        v.setLayoutParams(new AbsListView.LayoutParams(0,0));
        return v;
    }
}
