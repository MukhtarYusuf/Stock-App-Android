package com.my.mukhtaryusuf.stockfinder;

import org.json.JSONArray;
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
    final static String OPEN_KEY = "open";
    final static String CLOSE_KEY = "close";
    final static String HIGH_KEY = "high";
    final static String LOW_KEY = "low";
    final static String WEEK_HIGH_KEY = "week52High";
    final static String WEEK_LOW_KEY = "week52Low";
    final static String VOLUME_KEY = "latestVolume";
    final static String PE_KEY = "peRatio";
    final static String NET_CHANGE_KEY = "change";
    final static String MKT_CAP_KEY = "marketCap";

    final static String HEADLINE_KEY = "headline";
    final static String SOURCE_KEY = "source";
    final static String DATE_KEY = "datetime";
    final static String URL_KEY = "url";

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
                String cOpen = quotesObject.getString(OPEN_KEY);
                String cClose = quotesObject.getString(CLOSE_KEY);
                String cHigh = quotesObject.getString(HIGH_KEY);
                String cLow = quotesObject.getString(LOW_KEY);
                String cWeekHigh = quotesObject.getString(WEEK_HIGH_KEY);
                String cWeekLow = quotesObject.getString(WEEK_LOW_KEY);
                String cVolume = quotesObject.getString(VOLUME_KEY);
                String cPE = quotesObject.getString(PE_KEY);
                String cNetChange = quotesObject.getString(NET_CHANGE_KEY);
                String cMarketCap = quotesObject.getString(MKT_CAP_KEY);

                JSONArray newsJSONArray = companyObject.getJSONArray(NEWS_KEY);
                ArrayList<CompanyNews> cNews = new ArrayList<>();
                for(int j = 0; j < newsJSONArray.length(); j++){
                    JSONObject newsJSONObject = newsJSONArray.getJSONObject(j);
                    String headLineString = newsJSONObject.getString(HEADLINE_KEY);
                    String sourceString = newsJSONObject.getString(SOURCE_KEY);
                    String dateString = newsJSONObject.getString(DATE_KEY);
                    String urlString = newsJSONObject.getString(URL_KEY);

                    CompanyNews companyNews = new CompanyNews(headLineString, sourceString, dateString, urlString);
                    cNews.add(companyNews);
                }

                Company company = new Company(cSymbol,cName,cPrice, cOpen, cClose, cHigh,
                        cLow, cWeekHigh,cWeekLow,cVolume,cPE,cNetChange, cMarketCap);
                company.setCompanyNews(cNews);
                companyList.add(company);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return companyList;
    }
}
