package com.calendar;

public class IndexControl {

    public Integer findHighestInUse(Integer[] list){
        Integer highest = 0;
        for(int i = 0;i < list.length; i++){
            if (list[i] > highest){
                highest = list[i];
            }
        }
        return highest;
    }

}
