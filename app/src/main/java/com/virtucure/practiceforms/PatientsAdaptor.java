package com.virtucure.practiceforms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.PatientDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by KH1748 on 04-Mar-16.
 */
public class PatientsAdaptor extends BaseAdapter implements SectionIndexer {

    private List<PatientDTO> patientDTOs;
    private Activity activity;
    private LayoutInflater inflater;
    HashMap<String, Integer> azIndexer;
    String[] sections;
    ArrayList<String> keyList;

    @Override
    public int getCount() {
        return patientDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return patientDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.patientshealthrecord, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        /*ImageView arrow = (ImageView) convertView.findViewById(R.id.arrow);
        arrow.setImageResource(R.drawable.point);*/
        TextView mobile_email = (TextView) convertView.findViewById(R.id.phone_email);
        TextView dob_gender = (TextView) convertView.findViewById(R.id.dobgender);
        TextView proof = (TextView) convertView.findViewById(R.id.proof);
        TextView hrid = (TextView) convertView.findViewById(R.id.hrid);
        TextView bgTextView = (TextView) convertView.findViewById(R.id.textbg);

        PatientDTO m = patientDTOs.get(position);

        name.setText(m.getFirstName() +" "+m.getLastName());

        mobile_email.setText(m.getMobileNo() + "/" + m.getEmailId());

        dob_gender.setText(m.getDob()+"/"+m.getGender());

        proof.setText(m.getProofType() + ":" + m.getProofNumber());

        hrid.setText(m.getRegId());

        String fLetter = String.valueOf((m.getFirstName().length() > 0 ? m.getFirstName() : m.getLastName()).charAt(0)).toUpperCase();
        Util.updatebgcolor(bgTextView);
        bgTextView.setText(fLetter);

        azIndexer = new HashMap<>();
        for (position= getCount() - 1; position>= 0; position--) {
            PatientDTO m1= patientDTOs.get(position);
            String flname = m1.getFirstName().length()>1 ? m1.getFirstName() : m1.getLastName();
            azIndexer.put(flname.toUpperCase().substring(0, 1), position);
        }
        Set<String> keys = azIndexer.keySet();

        Iterator<String> it = keys.iterator();
        keyList = new ArrayList<>();
        while (it.hasNext()) {
            String key = it.next();
            keyList.add(key);
        }
        Collections.sort(keyList);
        sections = new String[keyList.size()];
        keyList.toArray(sections);

        return convertView;
    }

    public PatientsAdaptor(Activity act, List<PatientDTO> patientDTOs) {
        this.activity = act;
        this.patientDTOs = patientDTOs;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if(sections.length == 1){
            sectionIndex = 0;
        }
        return azIndexer.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
