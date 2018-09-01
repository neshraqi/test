package com.hami.common.Util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by renjer on 2018-01-07.
 */

public class UtilPrice {
    public static String getFinalPriceToman(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {
            return price + " ریال";
        }

    }
    //-----------------------------------------------
    public static String getFinalPriceRial(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice));
            finalPrice += " ریال";
            return finalPrice;
        } catch (Exception e) {
            return price + " ریال";
        }

    }
    //-----------------------------------------------
    public static String convertToToman(double price) {
        String finalPrice = "";
        try {
            Long value = Math.round(Math.ceil(price));
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(value / 10);
            finalPrice += " تومان";
            return finalPrice;
        } catch (Exception e) {
            return price + " ریال";
        }

    }

    //-----------------------------------------------
}
