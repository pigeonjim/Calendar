package com.calendar;

import java.io.PrintWriter;
import java.util.Scanner;
import java.nio.file.Paths;
import java.time.LocalDate;

public class DataIO {

    AllData allData;
    public DataIO(AllData allData){
        this.allData = allData;
    }

        public void outputToCSV(String path){
        try{
            PrintWriter csvWriter = new PrintWriter(path);
            for(String entry: allData.allDataInCSV()){
                System.out.println(entry);
                csvWriter.print(entry);
            }
            csvWriter.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void readFromCSV(String path){
       try(Scanner lineIn = new Scanner(Paths.get(path))){
            while(lineIn.hasNextLine()){
                String row = lineIn.nextLine();
                String[] words = row.split(",");
                LocalDate date = LocalDate.parse(words[0]);
                allData.addNewDayData(date, words[1]);
        }
       } catch(Exception e){
           System.out.println("error - " + e.toString());
        }
    }

}
