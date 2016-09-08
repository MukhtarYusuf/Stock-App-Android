package com.example.mukhtaryusuf.stockfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    SharedPreferences.Editor editor;

    private String LOG_TAG = MainActivity.class.getSimpleName();
    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));
    ArrayList<String> symbols;
    ArrayList<Company> companyList1;

    ListView companyListView;
    CompanyAdapter companyAdapter;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        errorMessage = (TextView) findViewById(R.id.error_message);
        companyListView = (ListView) findViewById(R.id.listViewResult);
        companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Company selectedCompany = (Company) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
                intent.putExtra("SelectedCompany", selectedCompany);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Get ConnectivityManager and Check for Network Connection
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
            updateUI();
        else
            displayError("No Internet Connection...");
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
        if(id == R.id.restore_defaults){
            editor.clear();
            editor.commit();
            updateUI();
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
            Log.i(LOG_TAG, String.valueOf(symbols.size()));
        }catch (Exception e){
            e.printStackTrace();
        }

        Synchronizer synchronizer = new Synchronizer(symbols);
        synchronizer.execute();
    }

    //Method to Display Error Message
    public void displayError(String error){
        errorMessage.setVisibility(View.VISIBLE);
        companyListView.setVisibility(View.GONE);
        errorMessage.setText(error);
    }

    //Method to Display Default Error Message
    public void displayError(){
        errorMessage.setVisibility(View.VISIBLE);
        companyListView.setVisibility(View.GONE);
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

                //CompanyList is Null Because of Network Error
                if(companyList == null && isNetworkError){
                    displayError("Error Retrieving Data. Please Check Internet Connection...");
                }else if(companyList == null)//List of Symbols is null or empty and returns null companyList
                    displayError("List is Empty...");
                else{
                    companyAdapter = new CompanyAdapter(getApplicationContext(), R.layout.company_row_test1, R.id.company_name, companyList);
                    //Check if List is empty and get first company name
                    if(!companyAdapter.isEmpty()) {
                        companyName = companyAdapter.getItem(0).getName();
                        //Check if List was properly set. If not display default error message
                        if (companyName.equals("N/A")) {
                            displayError();
                        } else {
                            errorMessage.setVisibility(View.GONE);
                            companyListView.setVisibility(View.VISIBLE);
                        }
                    }else {//List is Empty for Other Reasons
                        displayError("List is Empty...");
                    }
                    companyListView.setAdapter(companyAdapter);
                }
            }
        }
    }
}
