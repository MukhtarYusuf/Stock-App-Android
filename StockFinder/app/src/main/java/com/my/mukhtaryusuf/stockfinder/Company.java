package com.my.mukhtaryusuf.stockfinder;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 6/16/16.
 */

public class Company implements Serializable {

    ArrayList<String[]> detailsList = new ArrayList<>();

    private String symbol;
    private String name;
    private String price;
    private String open;
    private String close;
    private String high;
    private String low;
    private String weekHigh;
    private String weekLow;
//    private String dividend;
    private String volume;
    private String priceEarning;
    private String netChange;
    private String marketCap;



    private ArrayList<CompanyNews> companyNews;

    public Company(String symbol, String name, String price, String open, String close,
                   String high, String low, String weekHigh, String weekLow, String volume,
                   String priceEarning, String netChange, String marketCap) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.weekHigh = weekHigh;
        this.weekLow = weekLow;
//        this.dividend = dividend;
        setVolume(volume);
        this.priceEarning = priceEarning;
        this.netChange = netChange;
        setMarketCap(marketCap);
        companyNews = new ArrayList<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeekHigh() {
        return weekHigh;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setWeekHigh(String weekHigh) {
        this.weekHigh = weekHigh;
    }

    public String getWeekLow() {
        return weekLow;
    }

    public void setWeekLow(String weekLow) {
        this.weekLow = weekLow;
    }

//    public String getDividend() {
//        return dividend;
//    }
//
//    public void setDividend(String dividend) {
//        this.dividend = dividend;
//    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        try {
            long lVolume = Long.parseLong(volume);
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            this.volume = decimalFormat.format(lVolume);
        }catch (NumberFormatException e){
            this.volume = volume;
        }
    }

    public String getPriceEarning() {
        return priceEarning;
    }

    public void setPriceEarning(String priceEarning) {
        this.priceEarning = priceEarning;
    }

    public String getNetChange() {
        return netChange;
    }

    public void setNetChange(String netChange) {
        this.netChange = netChange;
    }

    public int getAttributeCount() {
        return getClass().getDeclaredFields().length;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        try{
            long lMarketCap = Long.parseLong(marketCap);
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            this.marketCap = decimalFormat.format(lMarketCap);
        }catch (NumberFormatException e){
            this.marketCap = marketCap;
        }
    }

    public ArrayList<CompanyNews> getCompanyNews() {
        return companyNews;
    }

    public void setCompanyNews(ArrayList<CompanyNews> companyNews) {
        this.companyNews = companyNews;
    }

    public ArrayList toArrayList(){

        String[] priceRow = {"Price", getPrice()};
        String[] openRow = {"Open", getOpen()};
        String[] closeRow = {"Close", getClose()};
        String[] highRow = {"High", getHigh()};
        String[] lowRow = {"Low", getLow()};
        String[] netChangeRow = {"Net Change", getNetChange()};
        String[] wHighRow = {"52 Week High", getWeekHigh()};
        String[] wLowRow = {"52 Week Low", getWeekLow()};
//        String[] dividendRow = {"Dividend", getDividend()};
        String[] volumeRow = {"Volume", getVolume()};
        String[] pERow = {"P/E", getPriceEarning()};
        String[] mktcapRow = {"Market Cap", getMarketCap()};

        detailsList.add(priceRow);
        detailsList.add(openRow);
        detailsList.add(closeRow);
        detailsList.add(highRow);
        detailsList.add(lowRow);
        detailsList.add(netChangeRow);
        detailsList.add(wHighRow);
        detailsList.add(wLowRow);
//        detailsList.add(dividendRow);
        detailsList.add(volumeRow);
        detailsList.add(pERow);
        detailsList.add(mktcapRow);

        return detailsList;
    }

    @Override
    public String toString() {
        return "Company Symbol: " + symbol + "\n" +
                "Company Name: " + name + "\n" +
                "Price: " + price + "\n" +
                "Week High: " + weekHigh + "\n" +
                "Week Low: " + weekLow + "\n" +
                "Volume: " + volume + "\n" +
                "Price/Earning: " + priceEarning + "\n" +
                "Net Change: " + netChange;
    }
}
