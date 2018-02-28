package com.my.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 2/6/18.
 */

public class CompanyNewsAdapter extends ArrayAdapter<CompanyNews> {
    Context context;
    int resource;
    ArrayList<CompanyNews> companyNews;

    public CompanyNewsAdapter(Context context, int resource, int textViewResourceId, ArrayList<CompanyNews> companyNews){
        super(context, resource, textViewResourceId, companyNews);
        this.context = context;
        this.resource = resource;
        this.companyNews = companyNews;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        super.getView(position, convertView, parent);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.news_row, parent, false);

        TextView headLineTV = (TextView)rowView.findViewById(R.id.news_headline);
        TextView sourceTV = (TextView)rowView.findViewById(R.id.news_source);
        TextView dateTV = (TextView)rowView.findViewById(R.id.news_date);

        headLineTV.setText(companyNews.get(position).getHeadline());
        sourceTV.setText(companyNews.get(position).getSource());
        dateTV.setText(companyNews.get(position).getDate());

        return rowView;
    }
}
