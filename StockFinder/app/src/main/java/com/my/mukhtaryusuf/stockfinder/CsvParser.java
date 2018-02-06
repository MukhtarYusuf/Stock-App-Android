package com.my.mukhtaryusuf.stockfinder;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mukhtaryusuf on 7/14/16.
 */

public class CsvParser {
    final static String LOG_TAG = CsvParser.class.getSimpleName();

    //--Note: Consider optimizing parse function--
    //Parse function is dependent on the nature and order of API call as well as Company object
    public static ArrayList<Company> parse(String data){
        Log.i(LOG_TAG, data);
        String[] companyRows = null;

        ArrayList<Company> parsedCompanyList = new ArrayList<Company>();

        //Split string into rows
        if(data != null && data != "") {
            //data = data.replace("\"","");
            companyRows = data.split("\n");

            if(companyRows != null) {
                for (String s : companyRows) {
                    ArrayList<String> companyColumnsAsList = new ArrayList<>(); //Makes it possible to construct company columns from lists
                    String[] testComopanyColumns = null;
                    String[] testCompanyColumns2 = null;
                    Company company = null;

                    testComopanyColumns = s.split("\"");
                    if(testComopanyColumns.length > 4) { //Full Data Retrieved with no N/A results

                        //Split other comma separated data
                        testCompanyColumns2 = testComopanyColumns[4].split(",");

                        companyColumnsAsList.add(testComopanyColumns[1]); //Add symbol. Skip index 0 because it's a space from split() function
                        companyColumnsAsList.add(testComopanyColumns[3]); //Add name
                        companyColumnsAsList.addAll(Arrays.asList(testCompanyColumns2)); //Add rest of data
                        companyColumnsAsList.add(testComopanyColumns[5]); //Add % change

                        String[] companyFields = new String[companyColumnsAsList.size()];
                        companyFields = companyColumnsAsList.toArray(companyFields);

                        //Skip companyFields[2] because an empty string from split() is stored there
                        company = new Company(companyFields[0],
                                companyFields[1],
                                companyFields[3],
                                companyFields[4],
                                companyFields[5],
                                companyFields[6],
                                companyFields[7],
                                companyFields[8]
                        );
                    }else{
                        testCompanyColumns2 = testComopanyColumns[2].split(",");

                        companyColumnsAsList.add(testComopanyColumns[1]);
                        companyColumnsAsList.addAll(Arrays.asList(testCompanyColumns2));

                        if(testComopanyColumns.length == 4) //Row has extra quotes at the end for % change
                            companyColumnsAsList.add(testComopanyColumns[3]);

                        String[] companyFields = new String[companyColumnsAsList.size()];
                        companyFields = companyColumnsAsList.toArray(companyFields);

                        //Skip companyFields[1] because an empty string from split() is stored there
                        company = new Company(companyFields[0],
                                companyFields[2],
                                companyFields[3],
                                companyFields[4],
                                companyFields[5],
                                companyFields[6],
                                companyFields[7],
                                companyFields[8]
                        );
                    }
                    parsedCompanyList.add(company);
                }
            }
            return parsedCompanyList;
        }else{
            return null;
        }
    }
}
