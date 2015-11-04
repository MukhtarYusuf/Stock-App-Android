package com.example.links;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	String URL = "";
	String result = "0.0";
	String symbol = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Code for Spinner
		Spinner spinner = (Spinner) findViewById(R.id.symbol_spinner);
		//Create an Adapter to Populate Spinner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.symbols, android.R.layout.simple_spinner_item);
		//Layout for Drop Down View
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Apply the Created Adapter to the Spinner 
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Method to Link to SJSU Website
	public void openWebpage(View view){
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sjsu.edu"));
    	startActivity(intent);
    }
	
	//Method to Check Stock Value Based on Symbol
	public void checkStock(View view){
		
		//Retrieve Spinner and Selected Value
		Spinner spinner = (Spinner) findViewById(R.id.symbol_spinner);
        symbol = spinner.getSelectedItem().toString();
        
        //Concatenate Symbol With URL to Generate Full URL
        URL = "http://finance.yahoo.com/d/quotes.csv?f=l1&s=";
        URL = URL + symbol;
        
        AsyncTask<Void, Void, String> task = new Task().execute();    
	}
	
	class Task extends AsyncTask<Void, Void, String>
    {
        
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params){
        
        	HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);
            ResponseHandler<String> handler = new BasicResponseHandler();
            
        	//result = "Does it Work?";
        	try{
            	result = httpclient.execute(request,handler);
            }
        	catch (ClientProtocolException e){
        		e.printStackTrace();
        	} 
            catch (IOException e) {
        		e.printStackTrace();
            }
           
            //Terminate
            httpclient.getConnectionManager().shutdown();
                
            return result;
        }
        @Override
        protected void onPostExecute(String result){
        	//Display Stock Result
            TextView stockResult = (TextView) findViewById(R.id.result);
            stockResult.setText("Stock Result: " + result);
        }
    }
}
