package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 7/13/16.
 */

public class CompanyAdapter extends ArrayAdapter<Company> {

    Context context;
    int resource;
    ArrayList<Company> originalData;
    ArrayList<Company> data = new ArrayList<>();
    ArrayList<Company> filteredData = new ArrayList<>();
    Filter filter;

    public CompanyAdapter(Context context, int resource, int textViewResourceId, ArrayList<Company> data) {
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.originalData = new ArrayList<>(data);
    }

    //    public CompanyAdapter(Context context, int resource, ArrayList<Company> data) {
//        super(context, resource, data);
//
//        this.context = context;
//        this.resource = resource;
//        this.data = data;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.company_row_test1, parent, false);

        TextView cSymbolTextView = (TextView) rowView.findViewById(R.id.company_symbol);
        TextView cNameTextView = (TextView) rowView.findViewById(R.id.company_name);
        TextView sValueTextView = (TextView) rowView.findViewById(R.id.stock_value);
        TextView pChangeTextView = (TextView) rowView.findViewById(R.id.percentage_change);

        cSymbolTextView.setText(data.get(position).getSymbol());
        cNameTextView.setText(data.get(position).getName());
        sValueTextView.setText(data.get(position).getPrice());
        pChangeTextView.setText(data.get(position).getNetChange());

        if(data.get(position).getNetChange().charAt(0) == '+')
            pChangeTextView.setTextColor(context.getResources().getColor(R.color.md_green_A700));
        else if(data.get(position).getNetChange().charAt(0) == '-')
            pChangeTextView.setTextColor(context.getResources().getColor(R.color.md_red_400));

//        TextView cNameTextView = (TextView) rowView.findViewById(R.id.company_name);
//        TextView sValueTextView = (TextView) rowView.findViewById(R.id.stock_value);
//
//        //Set TextViews Here
//        cNameTextView.setText(data.get(position).getName());
//        sValueTextView.setText(data.get(position).getPrice());


        return rowView;
    }

    @Override
    public Filter getFilter(){
        if(filter == null)
            filter = new CompanyFilter();
        return filter;
    }

    public class CompanyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredData.clear();
            FilterResults filterResults = new FilterResults();
            String query = constraint.toString().toLowerCase();

            if(query == null || query.length() == 0){
                filteredData.addAll(originalData);
                filterResults.values = filteredData;
                filterResults.count = filteredData.size();
            }else {
                for(Company c : originalData){
                    if(c.getName().toLowerCase().contains(query) || c.getSymbol().toLowerCase().contains(query))
                        filteredData.add(c);
                }
                filterResults.values = filteredData;
                filterResults.count = filteredData.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Company> fResults = (ArrayList<Company>) results.values;
            clear();
            Log.i("Query", constraint.toString());
            displayArrayList(fResults);

            for(Company c : fResults) {
                data.add(c);
            }

            notifyDataSetChanged();
        }
    }

    public void displayArrayList(ArrayList<Company> companyList){
        for(Company c : companyList){
            Log.i("Filtered List", c.toString());
        }
    }
}
