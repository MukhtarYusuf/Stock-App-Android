package com.example.mukhtaryusuf.stockfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCompanyActivity extends AppCompatActivity {
    CompanyAdapter companyAdapter;
    ArrayList<String> addedList;

    EditText enterCompany;
    ListView addCompanyResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        addCompanyResults = (ListView) findViewById(R.id.add_company_results);

        enterCompany = (EditText) findViewById(R.id.enter_company_symbol);
        enterCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addedList = new ArrayList<String>(Arrays.asList(s.toString()));
                AddCompanyActivity.Synchronizer synchronizer = new AddCompanyActivity.Synchronizer(addedList);
                synchronizer.execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

                companyAdapter = new CompanyAdapter(getApplicationContext(), R.layout.company_row, R.id.company_name, companyList);
                addCompanyResults.setAdapter(companyAdapter);
            }
        }
    }
}
