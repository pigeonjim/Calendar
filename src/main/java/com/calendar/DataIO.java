package com.calendar;

import java.io.FileWriter;
import java.io.IOException;

public class DataIO {

    AllData allData;

    public DataIO(AllData allData){

        this.allData = allData;
    }

    public void outputToCSV(String name){
        try{
            FileWriter csvFile = new FileWriter(name);


        } catch (Exception e){
            System.out.println(e);
        }
    }



}
