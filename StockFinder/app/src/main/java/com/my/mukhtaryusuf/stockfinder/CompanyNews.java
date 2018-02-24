package com.my.mukhtaryusuf.stockfinder;

import java.io.Serializable;

/**
 * Created by mukhtaryusuf on 2/6/18.
 */

public class CompanyNews implements Serializable{
    private String headline;
    private String source;
    private String date;
    private String url;

    public CompanyNews(String headline, String source, String date, String url){
        this.headline = headline;
        setSource(source);
        setDate(date);
        this.url = url;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = "Source: " + source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        StringBuilder formattedDateTime = new StringBuilder();
        String dateString = date.substring(0, 10);
        formattedDateTime.append("On: ");
        formattedDateTime.append(dateString);

        this.date = formattedDateTime.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return "Headline: " + headline + "\n"
                + "Source: " + source + "\n"
                + "Date: " + date + "\n"
                + "Url: " + url + "\n";
    }
}
