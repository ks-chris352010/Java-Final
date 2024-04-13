import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // Method to convert String to Date:
    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateString);
    }

    // Method to convert Date to String:
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }
            
    // Method to convert String to java.sql.Date:
    public static java.sql.Date stringToSqlDate(String dateString) throws ParseException {
        Date utilDate = stringToDate(dateString);
        return new java.sql.Date(utilDate.getTime());
    }

    // Method to convert java.sql.Date to String:
    public static String sqlDateToString(java.sql.Date sqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(sqlDate);
    }
}
