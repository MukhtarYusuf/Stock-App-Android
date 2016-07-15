package com.example.mukhtaryusuf.stockfinder;

import java.util.ArrayList;

/**
 * Created by mukhtaryusuf on 7/14/16.
 */

public class CsvParser {
    public static ArrayList<Company> parse(String data){
        String[] companyRows = null;
        ArrayList<Company> parsedCompanyList = new ArrayList<Company>();

        if(data != null || data != "") {
            data = data.replace("\"","");
            companyRows = data.split("\n");
        }

        if(companyRows != null) {
            for (String s : companyRows) {
                String[] companyFields = s.split(",");
                Company company = new Company(companyFields[0],
                        companyFields[1],
                        companyFields[2],
                        companyFields[3],
                        companyFields[4],
                        companyFields[5],
                        companyFields[6],
                        companyFields[7]
                );
                parsedCompanyList.add(company);
            }
        }

        return parsedCompanyList;
    }
}
