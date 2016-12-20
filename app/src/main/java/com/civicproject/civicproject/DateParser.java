package com.civicproject.civicproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateParser {

    private int days = 0;

    public String dateNotification() {
        String statement = "null";

        Calendar actualDate = Calendar.getInstance();
        actualDate.clear();
        actualDate.set(2016,9,18);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year_string = yearFormat.format(actualDate.getTime());
        int year = Integer.parseInt(year_string);

        Calendar startDate = Calendar.getInstance();
        startDate.clear();
        startDate.set(year, 9, 10);

        Calendar endDate = Calendar.getInstance();
        endDate.clear();
        endDate.set(year, 10, 9);

        int daysCounterBefore = 0, daysCounter = 0;

        // warunek 1
        if (actualDate.before(startDate)) {
            daysCounterBefore = daysCounter(actualDate.getTime(), startDate.getTime());
            if (daysCounterBefore > 30) {
                statement = "hidden";
            } else {
                setDays(daysCounterBefore);
                statement = "Głosowanie rozpocznie się za " + getDays() + " dni. Aby uzyskać więcej informacji...";
            }
        }

        // warunek 2
        if (actualDate.equals(startDate)) {
            statement = "Głosowanie rozpoczęło się właśnie dzisiaj! Aby uzyskać więcej informacji...";
        }

        // warunek 3
        if (actualDate.after(startDate) && actualDate.before(endDate)) {
            daysCounter = daysCounter(startDate.getTime(), endDate.getTime());
            setDays(daysCounter);
            statement = "Głosowanie jest już możliwe i będzie trwało jeszcze " + getDays() + " dni. Aby uzyskać więcej informacji...";
        }

        // warunek 4
        if (actualDate.after(endDate)) {
            statement = "hidden";
        }

        return statement;
    }

    public int daysCounter(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDate(String downloadedDate) {

        String formatedDate;

        ArrayList<String> monthsPL = new ArrayList<>(Arrays.asList("", "STYCZEŃ", "Luty", "Marzec","Kwiecień",
                "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "GRUDZIEŃ"));

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        
        Date date = null;
        try {
            date = formatter.parse(downloadedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!(date.getYear() < Calendar.getInstance().getTime().getYear()) ) {
            long minutes = getMinutesByDifference(date);
            if(minutes <= 1440) {
                if(minutes < 60)
                    if(minutes <= 1) {
                        formatedDate = minutes + " minutę temu";
                        return formatedDate;
                    } else {
                        formatedDate =  minutes + " minut temu";
                        return formatedDate;
                    }
                else {
                    minutes /= 60;
                    if(minutes == 1) {
                        formatedDate = minutes + " godzinę temu";
                        return formatedDate;
                    } else {
                        formatedDate = minutes + " godzin temu";
                        return formatedDate;
                    }
                }
            }
            else {
                String dayString = dayFormat.format(date.getTime());
                int monthInt = Integer.parseInt(monthFormat.format(date.getTime()));
                formatedDate = dayString + " " + monthsPL.get(monthInt);
                return formatedDate;
            }
        } else {
            String dayString = dayFormat.format(date.getTime());
            int monthInt = Integer.parseInt(monthFormat.format(date.getTime()));
            String yearString = yearFormat.format(date.getTime());
            formatedDate = dayString + " " + monthsPL.get(monthInt) + " " + yearString;
            return formatedDate;
        }
    }

    public static long getMinutesByDifference(java.util.Date baseDate) {
        long minutes = (System.currentTimeMillis() - baseDate.getTime()) / (1000 * 60);
        return minutes;
    }

}