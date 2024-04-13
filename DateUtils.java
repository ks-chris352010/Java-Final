import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for handling date conversions.
*/
public class DateUtils {

    /**
     * Date format pattern used for string to date conversion.
    */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Converts a string representation of a date to a Date object.
     *
     * @param dateString the string representation of the date
     * @return the Date object parsed from the input string
     * @throws ParseException if the input string is not in the expected format
    */
    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateString);
    }

    /**
     * Converts a Date object to a string representation using the specified date format.
     *
     * @param date the Date object to convert
     * @return the string representation of the date
    */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a string representation of a date to a java.sql.Date object.
     *
     * @param dateString the string representation of the date
     * @return the java.sql.Date object parsed from the input string
     * @throws ParseException if the input string is not in the expected format
    */
    public static java.sql.Date stringToSqlDate(String dateString) throws ParseException {
        Date utilDate = stringToDate(dateString);
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * Converts a java.sql.Date object to a string representation using the specified date format.
     *
     * @param sqlDate the java.sql.Date object to convert
     * @return the string representation of the date
    */
    public static String sqlDateToString(java.sql.Date sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(sqlDate);
    }
}

