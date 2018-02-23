package com.my.mukhtaryusuf.stockfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeleteActivity extends AppCompatActivity {

    private String LOG_TAG = DeleteActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Company> companyList1;
    ArrayList<String> symbols;
    final ArrayList<String> DEFAULT_SYMBOLS = new ArrayList<>(Arrays.asList("IBM","ORCL","GOOG","AAPL","MSFT","ADBE","EBAY","FB","SNAP"
            , "CAJ", "ETSY", "FIS", "GRUB", "PYPL", "V", "WU", "Z", "DLB", "GPRO", "IMAX"));
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
            if(deleteCompanyAdapter != null) {
                if (deleteCompanyAdapter.selectedItemsCount == 0 || deleteCompanyAdapter.data == null || deleteCompanyAdapter.data.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select Items to Delete...", Toast.LENGTH_LONG).show();
                } else {
                    Object[] dataArray0 = deleteCompanyAdapter.data.toArray();
                    Company[] dataArray = new Company[dataArray0.length];
                    for (int i = 0; i < dataArray.length; i++) {
                        dataArray[i] = (Company) dataArray0[i];
                    }
                    selectedData1 = deleteCompanyAdapter.selectedData;
                    for (int i = 0; i < selectedData1.size(); i++) {
                        if (selectedData1.get(i)) {

                            deleteCompanyAdapter.data.set(i, null);

                            deleteCompanyAdapter.selectedData.set(i, null);
                            symbols.set(i, null);

                        }
                    }
                    deleteCompanyAdapter.data.removeAll(Collections.singleton(null));
                    deleteCompanyAdapter.selectedData.removeAll(Collections.singleton(null));
                    symbols.removeAll(Collections.singleton(null));

                    deleteCompanyAdapter.notifyDataSetChanged();
                    try {
                        editor.putString("SAVED_SYMBOLS", ObjectSerializer.serialize(symbols));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.commit();
                    Toast.makeText(getApplicationContext(), deleteCompanyAdapter.selectedItemsCount
                            + " item(s) deleted", Toast.LENGTH_SHORT).show();
                    deleteCompanyAdapter.selectedItemsCount = 0;
                }
            }else{
                Toast.makeText(getApplicationContext(), "List is Empty...", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}
