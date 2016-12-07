package com.civicproject.civicproject;

import java.text.SimpleDateFormat;
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

}