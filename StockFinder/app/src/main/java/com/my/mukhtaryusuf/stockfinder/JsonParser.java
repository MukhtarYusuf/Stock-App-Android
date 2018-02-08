package com.my.mukhtaryusuf.stockfinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 2/5/18.
 */

public class JsonParser {

    //--JSON KEYS--
    final static String QUOTE_KEY = "quote";
    final static String NEWS_KEY = "news";
    final static String SYMBOL_KEY = "symbol";
    final static String NAME_KEY = "companyName";
    final static String PRICE_KEY = "latestPrice";
    final static String WEEK_HIGH_KEY = "week52High";
    final static String WEEK_LOW_KEY = "week52Low";
    final static String VOLUME_KEY = "latestVolume";
    final static String PE_KEY = "peRatio";
    final static String NET_CHANGE_KEY = "change";

    public static ArrayList<Company> parse(String jsonString, ArrayList<String> symbols){
        ArrayList<Company> companyList = new ArrayList<>();
        try {
            JSONObject topObject = new JSONObject(jsonString);
            for(int i = 0; i < symbols.size(); i++){//Iterate through JSON Company Objects
                String s = symbols.get(i);
                JSONObject companyObject = topObject.getJSONObject(s);
                JSONObject quotesObject = companyObject.getJSONObject(QUOTE_KEY);

                String cSymbol = quotesObject.getString(SYMBOL_KEY);
                String cName = quotesObject.getString(NAME_KEY);
                String cPrice = quotesObject.getString(PRICE_KEY);
                String cWeekHigh = quotesObject.getString(WEEK_HIGH_KEY);
                String cWeekLow = quotesObject.getString(WEEK_LOW_KEY);
                String cVolume = quotesObject.getString(VOLUME_KEY);
                String cPE = quotesObject.getString(PE_KEY);
                String cNetChange = quotesObject.getString(NET_CHANGE_KEY);

                Company company = new Company(cSymbol,cName,cPrice,cWeekHigh,cWeekLow,cVolume,cPE,cNetChange);
                companyList.add(company);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return companyList;
    }
}
