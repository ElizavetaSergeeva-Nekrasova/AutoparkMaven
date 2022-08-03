package Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ParserStringFormatter {
    public static Date formatStringToDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Wrong date format", e);
        }
    }

    public static String[] splitLineWithComma(String csvString) {
        String formattedCsvString = formatString(csvString);
        String[] fields = formattedCsvString.split(",");

        return fields;
    }

    private static String formatString(String csvString) {
        String regex = "(\")(\\d+)(,)(\\d+)(\")";
        return csvString.replaceAll(regex, "$2" + "." + "$4");
    }
}