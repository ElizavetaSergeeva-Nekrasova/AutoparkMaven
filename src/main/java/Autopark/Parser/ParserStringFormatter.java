package Autopark.Parser;

public class ParserStringFormatter {
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