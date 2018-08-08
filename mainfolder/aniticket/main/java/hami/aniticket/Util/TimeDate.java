package hami.mainapp.Util;

import android.support.annotation.Nullable;

import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by renjer on 1/16/2017.
 */

public class TimeDate {
    //-----------------------------------------------
    public static Date getDate(Object dateString) {
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

            Date d = f.parse(dateString.toString());

            return d;
        } catch (Exception e) {
            return new Date();
        }
    }

    //-----------------------------------------------
    public static String getTime(@Nullable String format, String input) {
        if (format == null) {
            format = "yyyy-MM-dd'T'kk:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(input.replaceAll("Z$", "+0000"));
            SimpleDateFormat output = new SimpleDateFormat("HH:mm");
            String formattedTime = output.format(date);
            return formattedTime;
            //return DateFormat.getTimeInstance(DateFormat.SHORT).format(millis);
            //strDate = DateFormat.getDateInstance().format(date);

        } catch (ParseException e) {
            return "";
        }
    }

    //-----------------------------------------------
    public static String getDate(@Nullable String format, String input) {
        if (format == null) {
            format = "yyyy-MM-dd'T'HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(input.replaceAll("Z$", "+0000"));
            return DateFormat.getDateInstance().format(date);

        } catch (ParseException e) {
            return "";
        }
    }

    public static String getTimePersian(String time) {

        try {
            String[] value = time.split(":");

            String resutlt = value[0] + " ساعت " + value[1] + " دقیقه ";

            return resutlt;
        } catch (Exception e) {

        }
        return "";
    }

    //-----------------------------------------------
    public static Calendar getDate(String input) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            if (input == null || input.length() == 0)
                return calendar;
            Date date = formatter.parse(input);
            calendar.setTime(date);
            return calendar;

        } catch (Exception e) {
            return calendar;
        }
    }

    //-----------------------------------------------
    public static MonthAdapter.CalendarDay getCurrentDate(int nextYear) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) + nextYear;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        MonthAdapter.CalendarDay calendarDay = new MonthAdapter.CalendarDay(year, month, day);
        return calendarDay;
    }

    //-----------------------------------------------
    public static long getTime(String time) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("hmmaa");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
        try {
            Date date = dateFormat2.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static String printDifferenceTime(String startTimeString, String endTimeString, String strDate) {
        Date startDate, endDate;
        String format = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Calendar calendar = new GregorianCalendar();
            Date date = formatter.parse(strDate);
            calendar.setTime(date);
            long fromTime = getTime(startTimeString);
            long toTime = getTime(endTimeString);
            long fromDate = 0;
            long toDate = 0;
            if (toTime < fromTime) {
                fromDate = calendar.getTime().getTime() + fromTime;
                calendar.add(Calendar.DATE, 1);
                toDate = calendar.getTime().getTime() + toTime;
            } else {
                fromDate = fromTime;
                toDate = toTime;
            }


            long different = toDate - fromDate; //getTime(endTimeString) - getTime(startTimeString);
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            String resutlt = "";
            if (elapsedDays > 0) {
                resutlt = elapsedDays + " روز ";
            }
            resutlt += elapsedHours + " ساعت " + elapsedMinutes + " دقیقه ";

            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    elapsedDays,
                    elapsedHours, elapsedMinutes, elapsedSeconds);
            return resutlt;
        } catch (Exception e) {

        }
        return "";
    }

    //-----------------------------------------------
    public static ResultDateDiff dateDiff(Date dateNow, Date dateTakeOff) {
        //milliseconds
        long different = dateTakeOff.getTime() - dateNow.getTime();
        ResultDateDiff resultDateDiff = null;
        System.out.println("startDate : " + dateNow);
        System.out.println("endDate : " + dateTakeOff);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        resultDateDiff = new ResultDateDiff(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return resultDateDiff;
    }

    //-----------------------------------------------
    public static ResultDateDiff dateDiff(String dateNow, String dateTakeOff) {
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date1 = formatter.parse(dateNow.replaceAll("Z$", "+0000"));
            Date date2 = formatter.parse(dateTakeOff.replaceAll("Z$", "+0000"));
            long different = date2.getTime() - date1.getTime();
            ResultDateDiff resultDateDiff = null;
            System.out.println("startDate : " + dateNow);
            System.out.println("endDate : " + dateTakeOff);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            //long daysInMilli = hoursInMilli * 24;

            //long elapsedDays = different / daysInMilli;
            //different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            resultDateDiff = new ResultDateDiff(0, elapsedHours, elapsedMinutes, elapsedSeconds);
            System.out.printf(
                    "%d days, %d hours, %d minutes, %d seconds%n",
                    0, elapsedHours, elapsedMinutes, elapsedSeconds);
            return resultDateDiff;
        } catch (ParseException e) {
            return null;
        }
    }

    //-----------------------------------------------
    public static long dateDiffToMinuets(String dateNow, String dateTakeOff) {
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date1 = formatter.parse(dateNow.replaceAll("Z$", "+0000"));
            Date date2 = formatter.parse(dateTakeOff.replaceAll("Z$", "+0000"));
            long different = date2.getTime() - date1.getTime();
            ResultDateDiff resultDateDiff = null;
            System.out.println("startDate : " + dateNow);
            System.out.println("endDate : " + dateTakeOff);
            System.out.println("different : " + different);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            //long daysInMilli = hoursInMilli * 24;

            //long elapsedDays = different / daysInMilli;
            //different = different % daysInMilli;

//            long elapsedHours = different / hoursInMilli;
//            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            return elapsedMinutes;
        } catch (ParseException e) {
            return 0;
        }
    }

    //-----------------------------------------------
    public static ResultDateDiff convertToHourMin(int minute) {
        long hours = minute / 60; //since both are ints, you get an int
        long minutes = minute % 60;
        return new ResultDateDiff(0, hours, minutes, 0);
    }

    //-----------------------------------------------
    public static class ResultDateDiff {
        long elapsedDays = 0;
        long elapsedHours = 0;
        long elapsedMinutes = 0;
        long elapsedSeconds = 0;

        public ResultDateDiff() {
        }

        public ResultDateDiff(long elapsedDays, long elapsedHours, long elapsedMinutes, long elapsedSeconds) {
            this.elapsedDays = elapsedDays;
            this.elapsedHours = elapsedHours;
            this.elapsedMinutes = elapsedMinutes;
            this.elapsedSeconds = elapsedSeconds;
        }

        public long getElapsedDays() {
            return elapsedDays;
        }

        public long getElapsedHours() {
            return elapsedHours;
        }

        public long getElapsedMinutes() {
            return elapsedMinutes;
        }

        public long getElapsedSeconds() {
            return elapsedSeconds;
        }

        public void setElapsedDays(long elapsedDays) {
            this.elapsedDays = elapsedDays;
        }

        public void setElapsedHours(long elapsedHours) {
            this.elapsedHours = elapsedHours;
        }

        public void setElapsedMinutes(long elapsedMinutes) {
            this.elapsedMinutes = elapsedMinutes;
            if (this.elapsedMinutes > 60) {
                long hours = this.elapsedMinutes / 60; //since both are ints, you get an int
                long minutes = this.elapsedMinutes % 60;
                this.elapsedHours += hours;
                this.elapsedMinutes = minutes;
            }
        }

        public void setElapsedSeconds(long elapsedSeconds) {
            this.elapsedSeconds = elapsedSeconds;
        }
    }
    //-----------------------------------------------
}
