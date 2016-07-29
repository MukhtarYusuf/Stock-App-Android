package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 7/28/16.
 */

public class CompanyDetailsAdapter extends ArrayAdapter<String[]> {
    Context context;
    int resource;
    ArrayList<String[]> data;

    public CompanyDetailsAdapter(Context context, int resource, int textViewResourceId, ArrayList<String[]> data) {
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.company_details_row, parent, false);

        TextView attributeLabel = (TextView) rowView.findViewById(R.id.attribute_label);
        TextView attributeValue = (TextView) rowView.findViewById(R.id.attribute_value);

        attributeLabel.setText(data.get(position)[0]);
        attributeValue.setText(data.get(position)[1]);

        return  rowView;
    }
}
