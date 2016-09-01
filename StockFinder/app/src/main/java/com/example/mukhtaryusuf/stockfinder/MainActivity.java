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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private String LOG_TAG = MainActivity.class.getSimpleName();
    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));
    ArrayList<String> symbols;
    ArrayList<Company> companyList1;

    ListView companyListView;
    CompanyAdapter companyAdapter;
    TextView errorMessage;

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

//        processButton = (Button) findViewById(R.id.buttonProcess);
        errorMessage = (TextView) findViewById(R.id.error_message);
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

        sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);

        //For Clearing SharedPreferences
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();

        updateUI();
        //Toast.makeText(this, "onCreate() executed", Toast.LENGTH_LONG).show();

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

        if (id == R.id.help) {
            Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(intent);
        }
        if(id == R.id.add_company){
            Intent intent = new Intent(getApplicationContext(), AddCompanyActivity.class);
            startActivity(intent);
        }
        if(id == R.id.delete_companies){
            Intent intent = new Intent(getApplicationContext(), DeleteActivity.class);
            intent.putExtra("CompanyList", companyList1);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //onResume() not needed for now
//    @Override
//    public void onResume(){
//        super.onResume();
//        updateUI();
//        Toast.makeText(this, "onResume() executed", Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onRestart(){
        super.onRestart();
        updateUI();
        //Toast.makeText(this, "onRestart() executed", Toast.LENGTH_LONG).show();
    }

    //Method to Update UI
    public void updateUI(){
        try {
            symbols = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("SAVED_SYMBOLS", ObjectSerializer.serialize(new ArrayList<String>(DEFAULT_SYMBOLS))));
        }catch (Exception e){
            e.printStackTrace();
        }

        Synchronizer synchronizer = new Synchronizer(symbols);
        synchronizer.execute();
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
                companyList1 = companyList;
                String companyName = "";

                Log.i(LOG_TAG, data);
                //results.setText(data);
                for(Company c : companyList){
                    Log.i(LOG_TAG, c.toString());
                }

                companyAdapter = new CompanyAdapter(getApplicationContext(), R.layout.company_row_test1, R.id.company_name, companyList);
                //Check if List is Empty
                if(!companyAdapter.isEmpty())
                    companyName = companyAdapter.getItem(0).getName();
                else {
                    errorMessage.setVisibility(View.VISIBLE);
                    companyListView.setVisibility(View.GONE);
                    errorMessage.setText("List is Empty...");
                }

                //Check if List was properly set. If not display error message
                if(companyName.equals("N/A")){
                    errorMessage.setVisibility(View.VISIBLE);
                    companyListView.setVisibility(View.GONE);
                }else{
                    errorMessage.setVisibility(View.GONE);
                    companyListView.setVisibility(View.VISIBLE);
                }
                companyListView.setAdapter(companyAdapter);
            }
        }
    }
}
