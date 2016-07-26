package com.virtucure.practiceforms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.virtucare.practiceforms.dto.FormFieldsDTO;

import java.util.List;

public class FormNamesAdapter extends BaseAdapter {

    private List<FormFieldsDTO> formNames;
    private Activity activity;
    private LayoutInflater inflater;

    public List<FormFieldsDTO> getFormNames() {
        return formNames;
    }

    public void setFormNames(List<FormFieldsDTO> formNames) {
        this.formNames = formNames;
    }


    @Override
    public int getCount() {
        return formNames.size();
    }

    @Override
    public Object getItem(int position) {
        return formNames.get(position);
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
            convertView = inflater.inflate(R.layout.formname_layout, null);
        FormFieldsDTO formFieldsDTO = formNames.get(position);
        TextView displayFormName = (TextView) convertView.findViewById(R.id.formname);
        displayFormName.setText(formFieldsDTO.getDisplayFormName());
        return convertView;
    }
    public FormNamesAdapter(Activity act, List<FormFieldsDTO> formNames) {
        this.activity = act;
        this.formNames = formNames;
    }
}
