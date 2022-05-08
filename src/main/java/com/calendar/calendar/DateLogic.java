package com.calendar.calendar;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLogic {
        //a class of functions that provides logic for working with dates
        private HashMap<String,Integer[]> months;
        private HashMap<Integer,String> days;
        LocalDate lineInTheSand;
        public DateLogic(){
            months = new HashMap<>();
            populateMonths();
            //this date is the first sunday in 2021
            //date is used to work out leap years and day names
            lineInTheSand = LocalDate.of(2021,01,03);
            days = new HashMap<>();
            populateDays();
        }
        private void populateMonths(){
            //method to populate the hashMap of months and
            //number of days
            months.put("January",new Integer[] {31,1});
            months.put("February",new Integer[] {28,2});
            months.put("March",new Integer[] {31,3});
            months.put("April",new Integer[] {30,4});
            months.put("May",new Integer[] {31,5});
            months.put("June",new Integer[] {30,6});
            months.put("July",new Integer[] {31,7});
            months.put("August",new Integer[] {31,8});
            months.put("September",new Integer[] {30,9});
            months.put("October",new Integer[] {31,10});
            months.put("November",new Integer[] {30,11});
            months.put("December",new Integer[] {31,12});
        }
        private void populateDays(){
            //populates the days array with day number and name
            //weeks start at 0 on Sunday
            days.put(0,"Sunday");
            days.put(1,"Monday");
            days.put(2,"Tuesday");
            days.put(3,"Wednesday");
            days.put(4,"Thursday");
            days.put(5,"Friday");
            days.put(6,"Saturday");
        }
        public boolean isLeapYear(int yearToCheck){
            //will return true if year passed as argument is a leap year
            if (yearToCheck%400 == 0){
                return true;
            }
            if(yearToCheck%100 ==0){
                return false;
            }
            if(yearToCheck%4 ==0){
                return true;
            }
            return false;
        }
        public int extraLeapYearDays(LocalDate date1){
            //will return number of extra days added onto the usual 365 by leap years since 01/01/2021
            int noOfLeapYears = (date1.getYear() - 2020)/4;
            if(date1.getMonthValue() > 2){
                return noOfLeapYears;
            }
            if(noOfLeapYears > 0){
                return noOfLeapYears - 1;
            }
            return 0;
        }
        public long numberOfDaysBetweenTwoDates(LocalDate date1, LocalDate date2){
            //return the number of days between two local dates
            //using chronounit and extraLeapYearDays
            long chronoDays = ChronoUnit.DAYS.between(date1,date2);
            int leapYearDays = extraLeapYearDays(date2) -
                    extraLeapYearDays(date1);
            return chronoDays + leapYearDays;
        }
        public boolean isDateWeekend(LocalDate date1){
            //takes in a local date and returns true if date is a weekend
            String dayName = getWeekDayName(date1);
            if(dayName.equals("Sunday") || dayName.equals("Saturday")){
                return true;
            }
            return false;
        }

        public String getWeekDayName(LocalDate date1){
                //takes in a local date and returns the name of the day
                //method uses the days between lineInTheSand and date argument
                //method uses the same system as days; sunday is day 0 to saturday = day 6
            Long dayNo = (numberOfDaysBetweenTwoDates(lineInTheSand,date1) - extraLeapYearDays(date1))%7;
            return days.get(dayNo.intValue());
        }
}