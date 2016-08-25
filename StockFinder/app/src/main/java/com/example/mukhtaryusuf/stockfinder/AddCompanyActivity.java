package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCompanyActivity extends AppCompatActivity {
    final String LOG_TAG = AddCompanyActivity.class.getSimpleName();

    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));

    CompanyAdapter companyAdapter;
    ArrayList<String> originalList;
    ArrayList<String> addedList;

    EditText enterCompany;
    ListView addCompanyResults;
    TextView invalidSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get Saved List From SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        invalidSymbol = (TextView) findViewById(R.id.invalid_symbol);
        addCompanyResults = (ListView) findViewById(R.id.add_company_results);

        enterCompany = (EditText) findViewById(R.id.enter_company_symbol);
        enterCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addedList = new ArrayList<String>(Arrays.asList(s.toString().toUpperCase()));
                AddCompanyActivity.Synchronizer synchronizer = new AddCompanyActivity.Synchronizer(addedList);
                synchronizer.execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Set onItemClickListener for addCompanyResults
        addCompanyResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Company company = (Company) parent.getAdapter().getItem(position);
                if(company.getName() != "N/A" && !originalList.contains(company.getSymbol()))
                    originalList.add(company.getSymbol());

                //Add new List Back to Preferences
                try{
                    editor.putString("SAVED_SYMBOLS", ObjectSerializer.serialize(originalList));
                }catch (Exception e){
                    e.printStackTrace();
                }
                editor.commit();
                Toast.makeText(getApplicationContext(), company.getName() + " Added", Toast.LENGTH_SHORT).show();
            }
        });

        //Get Original List From SharedPreferences
        try {
            originalList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("SAVED_SYMBOLS", ObjectSerializer.serialize(new ArrayList<String>(DEFAULT_SYMBOLS))));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return true;
    }

    public class Synchronizer extends CompaniesData {
        public Synchronizer(ArrayList<String> arrayList) {
            super(arrayList);
        }

        public void execute(){
            AddCompanyActivity.Synchronizer.SynchronizedDownloader sd = new AddCompanyActivity.Synchronizer.SynchronizedDownloader();
            sd.execute();
        }

        public class SynchronizedDownloader extends Downloader {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                String companyName = "";

                companyAdapter = new CompanyAdapter(getApplicationContext(), R.layout.company_row_test1, R.id.company_name, companyList);
                if(!companyAdapter.isEmpty())
                    companyName = companyAdapter.getItem(0).getName();

                //Check if valid company name is returned. Display invalid symbol if not
                if(companyName.equals("N/A")){
                    Log.i(LOG_TAG, companyName);
                    invalidSymbol.setVisibility(View.VISIBLE);
                    addCompanyResults.setVisibility(View.GONE);
                }else{
                    invalidSymbol.setVisibility(View.GONE);
                    addCompanyResults.setVisibility(View.VISIBLE);
                }

                addCompanyResults.setAdapter(companyAdapter);
            }
        }
    }
}
