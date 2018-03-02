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
    String lOpen = "Open";
    String lClose = "Close";
    String lHigh = "High";
    String lLow = "Low";
    String lWeekHigh = "Week High";
    String lWeekLow = "Week Low";
    String lVolume = "Volume";
    String lNetChange = "Net Change";
    String lMarketCap = "Market Capacity";

    //Initialize Definitions
    String dPrice = "The price of the company's stock";
    String dOpen = "The official open price";
    String dClose = "The official close price";
    String dHigh = "The market wide highest price";
    String dLow = "The market wide lowest price";
    String dWeekHigh = "The highest price that the stock has reached in a 52 week period";
    String dWeekLow = "The lowest price that the stock has reached in a 52 week period";
    String dVolume = "The number of shares of this particular stock that were traded today";
    String dNetChange = "The change in stock price in the current day compared to the previous day";
    String dMarketCap = "The market value of the company's outstanding shares";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.help_list);
        labels = new ArrayList<>(Arrays.asList(lprice, lOpen, lClose, lHigh, lLow, lWeekHigh, lWeekLow,
                lVolume, lNetChange, lMarketCap));
        definitions = new ArrayList<>(Arrays.asList(dPrice, dOpen, dClose, dHigh, dLow, dWeekHigh, dWeekLow,
                dVolume, dNetChange, dMarketCap));

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
