package com.bonc.dem.util;

public class StringUtils {
    public static boolean equals(String str1, String str2){
        if(str1.equals(str2)){
            return true;
        }
        return false;
    }

    public static String getDateStrMonth(String date){
        return date.substring(5,7);
    }

    public static String getDateStrDay(String date){
        return date.substring(date.length()-2 ,date.length());
    }
}
