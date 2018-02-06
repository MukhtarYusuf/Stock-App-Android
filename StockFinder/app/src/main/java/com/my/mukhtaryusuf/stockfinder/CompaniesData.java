package com.my.mukhtaryusuf.stockfinder;

import android.net.Uri;
import android.os.AsyncTask;

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
    private String LOG_TAG = CompaniesData.class.getSimpleName();

    ArrayList<String> companySymbols;
    ArrayList<Company> companyList;
    Uri destinationUri;
//    final String BASE_URL = "http://download.finance.yahoo.com/d/quotes.csv?f=snl1kjdvrp2&s=";
    final String BASE_URL = "http://download.finance.yahoo.com/d/quotes.csv?f=snl1kjvrp2&s=";
    String symbolsParams;
    String completeUrl;
    String data;
    boolean isNetworkError = false;

    public CompaniesData(ArrayList<String> arrayList){
        this.companySymbols = arrayList;
        constructSymbolsParams();
        buildDestinationUri();
    }

    public void execute(){
        Downloader downloader = new Downloader();
        downloader.execute();
    }

    private void buildDestinationUri(){
        final String BASE_URL = "https://api.iextrading.com/1.0/stock/market/batch";
        final String SYMBOLS_PARAM = "symbols";
        final String TYPES_PARAM = "types";
        destinationUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SYMBOLS_PARAM, symbolsParams)
                .appendQueryParameter(TYPES_PARAM, "quote,news")
                .build();
    }
    private void constructSymbolsParams(){
        StringBuilder sb = new StringBuilder();
        int size = companySymbols.size();
        for(int i = 0; i < size; i++){
            sb.append(companySymbols.get(i));
            if(i != size - 1)
                sb.append(",");
        }
        symbolsParams = sb.toString();
    }

    public class Downloader extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection;
            BufferedReader bufferedReader;
            try {
                if(companySymbols != null) {
                    URL url = new URL(destinationUri.toString());
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
                e.printStackTrace();
                isNetworkError = true;
            }

            data = stringBuilder.toString();
            companyList = JsonParser.parse(data,companySymbols);
//            companyList = CsvParser.parse(data);
//            System.out.print(data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
