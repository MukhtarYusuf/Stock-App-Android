package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 8/30/16.
 */

public class DeleteCompanyAdapter extends ArrayAdapter<Company> {
    Context context;
    int resource;
    ArrayList<Company> originalData;
    ArrayList<Company> data = new ArrayList<>();
    ArrayList<Company> filteredData = new ArrayList<>();
    ArrayList<Boolean> selectedData;
    Filter filter;
    int selectedItemsCount = 0;

    public DeleteCompanyAdapter(Context context, int resource, int textViewResourceId, ArrayList<Company> data) {
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.selectedData = new ArrayList<>(this.data.size());

        Log.i("Size of data: ", Integer.toString(this.data.size()));

        for(int i = 0; i < this.data.size(); i++){
            this.selectedData.add(false);
        }

        this.originalData = new ArrayList<>(data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        View rowView = convertView;

        if(rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(this.resource, parent, false);
        }

        final CheckBox dCompanyCheckBox = (CheckBox) rowView.findViewById(R.id.delete_checkbox);
        TextView cSymbolTextView = (TextView) rowView.findViewById(R.id.company_symbol);
        TextView cNameTextView = (TextView) rowView.findViewById(R.id.company_name);

        cSymbolTextView.setText(data.get(position).getSymbol());
        cNameTextView.setText(data.get(position).getName());
        dCompanyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dCompanyCheckBox.isChecked()) {
                    selectedData.set(position, true);
                    selectedItemsCount++;
                }else {
                    selectedData.set(position, false);
                    selectedItemsCount--;
                }

                Log.i("Selected data: ", selectedData.get(position).toString());
            }
        });

        if(selectedData.get(position))
            dCompanyCheckBox.setChecked(true);
        else
            dCompanyCheckBox.setChecked(false);

        return rowView;
    }
}
