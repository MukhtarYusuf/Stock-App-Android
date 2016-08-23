package com.example.mukhtaryusuf.stockfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    ArrayList<String> symbols;

    ListView companyListView;
    CompanyAdapter companyAdapter;
//    TextView results;
//    Button processButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        results = (TextView) findViewById(R.id.textViewResult);
//        processButton = (Button) findViewById(R.id.buttonProcess);
        companyListView = (ListView) findViewById(R.id.listViewResult);
        companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Company selectedCompany = (Company) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
                intent.putExtra("SelectedCompany", selectedCompany);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Click works", Toast.LENGTH_SHORT).show();
            }
        });

        symbols = new ArrayList<String>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));

        Synchronizer synchronizer = new Synchronizer(symbols);
        synchronizer.execute();

        SharedPreferences sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);
        

//        processButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Synchronizer synchronizer = new Synchronizer(symbols);
//                synchronizer.execute();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(companyAdapter != null){
                    companyAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            return true;
        }
        if(id == R.id.add_company){
            Intent intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public class Synchronizer extends CompaniesData {
        public Synchronizer(ArrayList<String> arrayList) {
            super(arrayList);
        }

        public void execute(){
            //super.execute();
            SynchronizedDownloader sd = new SynchronizedDownloader();
            sd.execute();
        }

        public class SynchronizedDownloader extends Downloader {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.i(LOG_TAG, data);
                //results.setText(data);
                for(Company c : companyList){
                    Log.i(LOG_TAG, c.toString());
                }

                companyAdapter = new CompanyAdapter(getApplicationContext(), R.layout.company_row, R.id.company_name, companyList);
                companyListView.setAdapter(companyAdapter);
            }
        }
    }
}
