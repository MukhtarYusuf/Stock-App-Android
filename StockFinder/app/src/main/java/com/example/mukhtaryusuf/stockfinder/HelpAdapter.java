package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 8/24/16.
 */

public class HelpAdapter extends ArrayAdapter<String> {
    Context context;
    int resource;
    ArrayList<String> labels;
    ArrayList<String> definitions;

    public HelpAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> labels, ArrayList<String> definitions){
        super (context, resource, textViewResourceId, labels);

        this.context = context;
        this.resource = resource;
        this.labels = labels;
        this.definitions = definitions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.help_row, parent, false);

        TextView tLabel = (TextView) view.findViewById(R.id.help_label);
        TextView tDefinition = (TextView) view.findViewById(R.id.help_definition);

        tLabel.setText(labels.get(position) + ": ");
        tDefinition.setText(definitions.get(position));

        return view;
    }
}
