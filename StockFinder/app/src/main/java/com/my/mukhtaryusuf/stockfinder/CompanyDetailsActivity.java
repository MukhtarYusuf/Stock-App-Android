package com.my.mukhtaryusuf.stockfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CompanyDetailsActivity extends AppCompatActivity {
//    final String BASE_URL = "https://ichart.finance.yahoo.com/w?s=";
    final String BASE_URL = "http://www.google.com/finance/historical?q=";
    //final String BASE_URL = "http://ichart.yahoo.com/v?s=";
    String finalUrl="";

    TextView companyHeaderSymbol;
    TextView companyHeaderName;
    ListView companyDetails;
    ListView companyNews;
    ImageView companyChart;

    ArrayList<String[]> detailsList = new ArrayList<>();
    ArrayList<CompanyNews> newsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Company selectedCompany = (Company) intent.getSerializableExtra("SelectedCompany");
        detailsList = selectedCompany.toArrayList();
        newsArrayList = selectedCompany.getCompanyNews();
        CompanyDetailsAdapter companyDetailsAdapter = new CompanyDetailsAdapter(getApplicationContext(), R.layout.company_details_row, R.id.attribute_label, detailsList);
        CompanyNewsAdapter companyNewsAdapter = new CompanyNewsAdapter(getApplicationContext(), R.layout.news_row, R.id.news_headline, newsArrayList);

        companyHeaderSymbol = (TextView) findViewById(R.id.company_header_symbol);
        companyHeaderName = (TextView) findViewById(R.id.company_header_name);
        companyDetails = (ListView) findViewById(R.id.details_list);
        companyNews = (ListView) findViewById(R.id.news_list);
//        companyChart = (ImageView) findViewById(R.id.company_chart);

        companyHeaderSymbol.setText(selectedCompany.getSymbol());
        companyHeaderName.setText(selectedCompany.getName());
        companyDetails.setAdapter(companyDetailsAdapter);
        companyNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyNews selectedNews = (CompanyNews)parent.getAdapter().getItem(position);
                String uriString = selectedNews.getUrl();
                Uri uri = Uri.parse(uriString);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
            }
        });
        companyNews.setAdapter(companyNewsAdapter);

        finalUrl = BASE_URL+selectedCompany.getSymbol();
        Uri uri = Uri.parse(finalUrl);
//        Picasso.with(getApplicationContext())
//                .load(uri)
//                .into(companyChart);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return true;
    }
}
