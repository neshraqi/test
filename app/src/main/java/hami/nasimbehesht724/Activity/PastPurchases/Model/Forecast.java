package hami.nasimbehesht724.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;


public class Forecast {

    @SerializedName("code")
    private int code;
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private String day;
    @SerializedName("temp")
    private String temp;
    @SerializedName("high")
    private String high;
    @SerializedName("low")
    private String low;
    @SerializedName("text")
    private String text;

    //-----------------------------------------------
    public Forecast() {
    }
    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getText() {
        return text;
    }


    public String getTempFahrenheit() {
        if (high != null && low != null && high.length() > 0 && low.length() > 0) {
            String value = String.valueOf((Integer.valueOf(high) + Integer.valueOf(low)) / 2);
            return value;
        }
        return temp;
    }

    public String getTempCentigrade() {
        if (high != null && low != null && high.length() > 0 && low.length() > 0) {
            long value = ((Long.valueOf(high) + Long.valueOf(low)) / 2);
            value = Math.round((value - 32) / 1.8);
            return String.valueOf(value);
        }
        long value = Math.round((Long.valueOf(temp) - 32) / 1.8);
        return String.valueOf(value);
    }

    public int getIcon() {
        switch (code) {
            //tofani , bad
            case 0:
            case 1:
            case 2:
            case 24:
                break;
            //rain , snow
            case 5:
            case 6:
            case 7:
            case 10:
            case 13:
            case 14:
            case 15:
            case 16:
            case 35:
            case 41:
            case 42:
            case 43:
            case 46:

                break;

            case 29:
            case 30:
            case 44:
                break;

            case 27:
            case 28:
            case 38:
            case 39:
            case 40:
            //case 42:
                break;

        }
        return -1;
    }
    /*

0	tornado
1	tropical storm
2	hurricane
3	severe thunderstorms
4	thunderstorms
5	mixed rain and snow
6	mixed rain and sleet
7	mixed snow and sleet
8	freezing drizzle
9	drizzle
10	freezing rain
11	showers
12	showers
13	snow flurries
14	light snow showers
15	blowing snow
16	snow
17	hail
18	sleet
19	dust
20	foggy
21	haze
22	smoky
23	blustery
24	windy
25	cold
26	cloudy
27	mostly cloudy (night)
28	mostly cloudy (day)
29	partly cloudy (night)
30	partly cloudy (day)
31	clear (night)
32	sunny
33	fair (night)
34	fair (day)
35	mixed rain and hail
36	hot
37	isolated thunderstorms
38	scattered thunderstorms
39	scattered thunderstorms
40	scattered showers
41	heavy snow
42	scattered snow showers
43	heavy snow
44	partly cloudy
45	thundershowers
46	snow showers
47	isolated thundershowers

     */
}
