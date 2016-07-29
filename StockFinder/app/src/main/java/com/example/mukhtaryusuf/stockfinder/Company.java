package com.example.mukhtaryusuf.stockfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 6/16/16.
 */

public class Company implements Serializable {

    ArrayList<String[]> detailsList = new ArrayList<>();

    private String symbol;
    private String name;
    private String price;
    private String weekHigh;
    private String weekLow;
    private String dividend;
    private String volume;
    private String priceEarning;
    private String netChange;

    public Company(String symbol, String name, String price, String weekHigh, String weekLow, String dividend, String volume, String priceEarning, String netChange) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.weekHigh = weekHigh;
        this.weekLow = weekLow;
        this.dividend = dividend;
        this.volume = volume;
        this.priceEarning = priceEarning;
        this.netChange = netChange;
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

    public String getDividend() {
        return dividend;
    }

    public void setDividend(String dividend) {
        this.dividend = dividend;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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

    public ArrayList toArrayList(){

        String[] priceRow = {"Price", getPrice()};
        String[] netChangeRow = {"Net Change", getNetChange()};
        String[] wHighRow = {"Week High", getWeekHigh()};
        String[] wLowRow = {"Week Low", getWeekLow()};
        String[] dividendRow = {"Dividend", getDividend()};
        String[] volumeRow = {"Volume", getVolume()};
        String[] pERow = {"P/E", getPriceEarning()};

        detailsList.add(priceRow);
        detailsList.add(netChangeRow);
        detailsList.add(wHighRow);
        detailsList.add(wLowRow);
        detailsList.add(dividendRow);
        detailsList.add(volumeRow);
        detailsList.add(pERow);

        return detailsList;
    }

    @Override
    public String toString() {
        return "Company Symbol: " + symbol + "\n" +
                "Company Name: " + name + "\n" +
                "Price: " + price + "\n" +
                "Week High: " + weekHigh + "\n" +
                "Week Low: " + weekLow + "\n" +
                "Dividend: " + dividend + "\n" +
                "Volume: " + volume + "\n" +
                "Price/Earning: " + priceEarning + "\n" +
                "Net Change: " + netChange;
    }
}
