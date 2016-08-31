package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteActivity extends AppCompatActivity {

    private String LOG_TAG = DeleteActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));
    ArrayList<String> symbols;

    ListView dCompanyListView;
    DeleteCompanyAdapter deleteCompanyAdapter;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        errorMessage = (TextView) findViewById(R.id.error_message1);
        dCompanyListView = (ListView) findViewById(R.id.listViewResult1);
        sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);
        updateUI();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return true;
    }

    public void updateUI(){
        try {
            symbols = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("SAVED_SYMBOLS", ObjectSerializer.serialize(new ArrayList<String>(DEFAULT_SYMBOLS))));
        }catch (Exception e){
            e.printStackTrace();
        }

        DeleteActivity.Synchronizer synchronizer = new DeleteActivity.Synchronizer(symbols);
        synchronizer.execute();
    }

    public class Synchronizer extends CompaniesData {
        public Synchronizer(ArrayList<String> arrayList) {
            super(arrayList);
        }

        public void execute(){
            //super.execute();
            DeleteActivity.Synchronizer.SynchronizedDownloader sd = new DeleteActivity.Synchronizer.SynchronizedDownloader();
            sd.execute();
        }

        public class SynchronizedDownloader extends Downloader {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                String companyName = "";

                deleteCompanyAdapter = new DeleteCompanyAdapter(getApplicationContext(), R.layout.company_row_edit, R.id.company_name, companyList);
                //Check if List is Empty
                if(!deleteCompanyAdapter.isEmpty())
                    companyName = deleteCompanyAdapter.getItem(0).getName();
                else {
                    errorMessage.setVisibility(View.VISIBLE);
                    dCompanyListView.setVisibility(View.GONE);
                    errorMessage.setText("List is Empty...");
                }

                //Check if List was properly set. If not display error message
                if(companyName.equals("N/A")){
                    errorMessage.setVisibility(View.VISIBLE);
                    dCompanyListView.setVisibility(View.GONE);
                }else{
                    errorMessage.setVisibility(View.GONE);
                    dCompanyListView.setVisibility(View.VISIBLE);
                }
                dCompanyListView.setAdapter(deleteCompanyAdapter);
            }
        }
    }
}
