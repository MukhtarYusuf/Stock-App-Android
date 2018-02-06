package com.my.mukhtaryusuf.stockfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<String> labels;
    ArrayList<String> definitions;

    //Initialize Labels
    String lprice = "Price";
    String lNetChange = "Net Change";
    String lWeekHigh = "Week High";
    String lWeekLow = "Week Low";
    String lDividend = "Dividend";
    String lVolume = "Volume";

    //Initialize Definitions
    String dPrice = "The price of the company's stock";
    String dNetChange = "The change in stock price in the current day compared to the previous day";
    String dWeekHigh = "The highest price that the stock has reached in a 52 week period";
    String dWeekLow = "The lowest price that the stock has reached in a 52 week period";
    String dDividend = "The annual dividend quoted. The value indicates that payments have been made to stockholders";
    String dVolume = "The number of shares of this particular stock that were traded today";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.help_list);
        labels = new ArrayList<>(Arrays.asList(lprice, lNetChange, lWeekHigh, lWeekLow, lDividend, lVolume));
        definitions = new ArrayList<>(Arrays.asList(dPrice, dNetChange, dWeekHigh, dWeekLow, dDividend, dVolume));

        HelpAdapter helpAdapter = new HelpAdapter(getApplicationContext(), R.layout.help_row, R.id.help_label, labels, definitions);
        listView.setAdapter(helpAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return true;
    }
}
