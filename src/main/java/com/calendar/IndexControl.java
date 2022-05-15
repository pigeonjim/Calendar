package com.calendar;
import java.util.Set;

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

    public Integer findFirstNotInUse(Set<Integer> list) {
        Integer i = 1;
        while (list.contains(i)) {
            i++;
        }
        return i;
    }
}
