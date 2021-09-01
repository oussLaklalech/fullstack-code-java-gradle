package se.kry.codetest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * Format a {@link Date} to a {@link String}.
     *
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateAsString = dateFormat.format(date);

        return dateAsString;
    }

    /**
     * Format a {@link String} to a {@link Date}.
     *
     * @param dateAsString
     * @return
     */
    public static Date formatStringToDate(String dateAsString) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        Date creationDate = null;
        try {
            creationDate = formatter.parse(dateAsString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return creationDate;
    }
}
