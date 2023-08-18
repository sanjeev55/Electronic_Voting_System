package charlie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {

    private static final Logger logger = Logger.getLogger(DateUtils.class.getName());
    
    public static Date parseDate(String dateInString, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateInString);
        } catch (ParseException e) {
           logger.log(Level.SEVERE, "Got exception while parsing date: " + dateInString, e);
        }
        
        return null;
    }
    
    public static Date parseDate(String dateInString) {
        return parseDate(dateInString, "yyyy-MM-dd'T'HH:mm");
    }
    
    public static String convertDateToCustomFormat(String input) {
        try {
            // Parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CEST' yyyy");
            inputFormat.setTimeZone(TimeZone.getTimeZone("CET"));
            Date date = inputFormat.parse(input);

            // Convert the parsed date to the desired format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            return outputFormat.format(date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Got exception while converting date format: " + input, e);
            return null;
        }
    }
    
}
