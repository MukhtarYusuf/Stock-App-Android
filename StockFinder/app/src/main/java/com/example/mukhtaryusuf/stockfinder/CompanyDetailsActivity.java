package com.example.mukhtaryusuf.stockfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompanyDetailsActivity extends AppCompatActivity {
    final String BASE_URL = "http://ichart.finance.yahoo.com/w?s=";
    //final String BASE_URL = "http://ichart.yahoo.com/v?s=";
    String finalUrl="";

    TextView companyHeaderSymbol;
    TextView companyHeaderName;
    ListView companyDetails;
    ImageView companyChart;

    ArrayList<String[]> detailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Company selectedCompany = (Company) intent.getSerializableExtra("SelectedCompany");
        detailsList = selectedCompany.toArrayList();
        CompanyDetailsAdapter companyDetailsAdapter = new CompanyDetailsAdapter(getApplicationContext(), R.layout.company_details_row, R.id.attribute_label, detailsList);

        companyHeaderSymbol = (TextView) findViewById(R.id.company_header_symbol);
        companyHeaderName = (TextView) findViewById(R.id.company_header_name);
        companyDetails = (ListView) findViewById(R.id.details_list);
        companyChart = (ImageView) findViewById(R.id.company_chart);

        companyHeaderSymbol.setText(selectedCompany.getSymbol());
        companyHeaderName.setText(selectedCompany.getName());
        companyDetails.setAdapter(companyDetailsAdapter);

        finalUrl = BASE_URL+selectedCompany.getSymbol();
        Uri uri = Uri.parse(finalUrl);
        Picasso.with(getApplicationContext())
                .load(uri)
                .into(companyChart);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return true;
    }
}
