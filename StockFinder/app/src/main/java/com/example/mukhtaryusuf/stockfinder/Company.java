package com.example.mukhtaryusuf.stockfinder;

/**
 * Created by mukhtaryusuf on 6/16/16.
 */

public class Company {
    private String name;
    private String price;
    private String weekHigh;
    private String weekLow;
    private String dividend;
    private String volume;
    private String priceEarning;
    private String netChange;

    public Company(String name, String price, String weekHigh, String weekLow, String dividend, String volume, String priceEarning, String netChange) {
        this.name = name;
        this.price = price;
        this.weekHigh = weekHigh;
        this.weekLow = weekLow;
        this.dividend = dividend;
        this.volume = volume;
        this.priceEarning = priceEarning;
        this.netChange = netChange;
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

    @Override
    public String toString() {
        return "Company Name: " + name + "\n" +
                "Week High: " + weekHigh + "\n" +
                "Week Low: " + weekLow + "\n" +
                "Dividend: " + dividend + "\n" +
                "Volume: " + volume + "\n" +
                "Price/Earning: " + priceEarning + "\n" +
                "Net Change: " + netChange;
    }
}
