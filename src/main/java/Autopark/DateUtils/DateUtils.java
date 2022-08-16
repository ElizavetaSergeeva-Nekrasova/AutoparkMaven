package Autopark.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static Date formatStringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Wrong date format", e);
        }
    }
}