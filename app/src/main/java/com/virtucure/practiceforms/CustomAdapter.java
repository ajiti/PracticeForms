package com.virtucure.practiceforms;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AJITI on 6/25/2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private int LayoutID;
    private int TextViewID;

    private LayoutInflater Inflater;

    private List<String> ObjectsList;

    public CustomAdapter (Context ActivityContext, int ResourceID, int TextViewResourceID, List<String> WordList) {
        super(ActivityContext, ResourceID, TextViewResourceID, new ArrayList<String>());

        LayoutID = ResourceID;
        TextViewID = TextViewResourceID;

        ObjectsList = WordList;

        Inflater = LayoutInflater.from(ActivityContext);
    }

    @Override
    public View getView(int Position, View ConvertView, ViewGroup Parent) {
        String Word = getItem(Position);

        if(ConvertView == null) {
            ConvertView = Inflater.inflate(LayoutID, null);

            ResultHolder Holder = new ResultHolder();

            Holder.ResultLabel= (TextView) ConvertView.findViewById(TextViewID);

            ConvertView.setTag(Holder);
        }

        ResultHolder Holder = (ResultHolder) ConvertView.getTag();

        Holder.ResultLabel.setText(Word);

        return ConvertView;
    }

    @Override
    public Filter getFilter() {
        return CustomFilter;
    }

    private Filter CustomFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object ResultValue) {
            return ((String) ResultValue);
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence Constraint) {
            FilterResults ResultsFilter = new FilterResults();

            ArrayList<String> OriginalValues = new ArrayList<String>(ObjectsList);

            if(Constraint == null || Constraint.length() == 0){
                ResultsFilter.values = OriginalValues;
                ResultsFilter.count = OriginalValues.size();
            } else {
                String PrefixString = Constraint.toString().toLowerCase();

                final ArrayList<String> NewValues = new ArrayList<String>();

                for(String Word : OriginalValues){
                    String ValueText = Word.toLowerCase();

                    if(ValueText.contains(PrefixString))
                        NewValues.add(Word);
                }

                ResultsFilter.values = NewValues;
                ResultsFilter.count = NewValues.size();
            }

            return ResultsFilter;
        }

        @Override
        protected void publishResults(CharSequence Constraint, FilterResults Results) {
            clear();

            if(Results.count > 0)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    addAll(((ArrayList<String>) Results.values));
                }
            else
                notifyDataSetInvalidated();
        }
    };

    private static class ResultHolder {
        TextView ResultLabel;
    }

}