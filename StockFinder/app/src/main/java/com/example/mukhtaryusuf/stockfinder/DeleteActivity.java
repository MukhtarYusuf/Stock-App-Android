package com.example.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteActivity extends AppCompatActivity {

    private String LOG_TAG = DeleteActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Company> companyList1;
    ArrayList<String> symbols;
    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","YHOO","MSFT","ADBE","EBAY"));
    ArrayList<Boolean> selectedData1;

    ListView dCompanyListView;
    DeleteCompanyAdapter deleteCompanyAdapter;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        sharedPreferences = getSharedPreferences("STOCK_FINDER_PREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try{
            symbols = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("SAVED_SYMBOLS", ObjectSerializer.serialize(new ArrayList<String>(DEFAULT_SYMBOLS))));
        }catch (Exception e){
            e.printStackTrace();
        }

        errorMessage = (TextView) findViewById(R.id.error_message1);
        dCompanyListView = (ListView) findViewById(R.id.listViewResult1);

        Intent intent = getIntent();
        companyList1 = (ArrayList<Company>) intent.getSerializableExtra("CompanyList");
        if(companyList1 != null){
            deleteCompanyAdapter = new DeleteCompanyAdapter(getApplicationContext(), R.layout.company_row_edit, R.id.company_name, companyList1);
            dCompanyListView.setAdapter(deleteCompanyAdapter);

            String companyName = "";

            //Check That List is not Empty
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
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //Back Button was Tapped
        if(id == android.R.id.home)
            finish();

        //Delete Button was Tapped
        if(id == R.id.delete){
            selectedData1 = deleteCompanyAdapter.selectedData;
            for(int i = 0; i < selectedData1.size(); i++){
                if(selectedData1.get(i)){
                    Company selectedCompany = deleteCompanyAdapter.getItem(i);
                    Log.i(LOG_TAG, selectedCompany.toString());
                    deleteCompanyAdapter.remove(selectedCompany);
                    deleteCompanyAdapter.selectedData.remove(deleteCompanyAdapter.selectedData.get(i));
                    symbols.remove(selectedCompany.getSymbol());
                }
            }
            deleteCompanyAdapter.notifyDataSetChanged();
            try{
                editor.putString("SAVED_SYMBOLS", ObjectSerializer.serialize(symbols));
            }catch (Exception e){
                e.printStackTrace();
            }
            editor.commit();
        }
        return true;
    }
}
