package com.bonc.dem.util;

import java.text.NumberFormat;

public class MathUtils {
    public static String getPercent(Integer divisor, Integer dividend) {
        String result = null;

        if(null != dividend && null == divisor){
            return "0%";
        }

        if (null != dividend && null != divisor) {
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMinimumIntegerDigits(0);
            result = nf.format((float) divisor / (float) dividend);
        }

        return result;
    }
}
