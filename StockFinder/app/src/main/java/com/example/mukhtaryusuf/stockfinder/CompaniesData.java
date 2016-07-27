package com.example.mukhtaryusuf.stockfinder;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 6/16/16.
 */

public class CompaniesData {
    ArrayList<String> companySymbols;
    ArrayList<Company> companyList;
    final String BASE_URL = "http://finance.yahoo.com/d/quotes.csv?f=snl1kjdvrp2&s=";
    String completeUrl;
    String data;

    public CompaniesData(ArrayList<String> arrayList){
        this.companySymbols = arrayList;
    }

    public void execute(){
        Downloader downloader = new Downloader();
        downloader.execute();
    }

    public class Downloader extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection;
            BufferedReader bufferedReader;
            try {
                for(String s : companySymbols){
                    completeUrl = BASE_URL + s;
                    URL url = new URL(completeUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream is = connection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(is));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                }

            }catch (Exception e) {
            Log.e("Error", e.getMessage());
            }

            data = stringBuilder.toString();
            companyList = CsvParser.parse(data);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
