package charlie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtils {

    private static final Logger logger = Logger.getLogger(DateUtils.class.getName());
    private static final String[] DATE_FORMATS = {
        "yyyy-MM-dd'T'HH:mm",
        "EEE MMM dd HH:mm:ss Z yyyy"
    };
    
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
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateInString);
            } catch (ParseException e) {
                System.out.println("error" + e);
            }
        }
        
        logger.log(Level.SEVERE, "Got exception while parsing date: {0}", dateInString);
        return null;
    }

    
    public static Date parseDefaultDate(String dateInString) {
        return parseDate(dateInString, "EEE MMM dd HH:mm:ss Z yyyy");
    }
    
    public static Date getDateMinusFiveMinutes() {
        Date toDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.add(Calendar.MINUTE, -5);
        return cal.getTime();
    }
    
    public static String convertDateToCustomFormat(String input) {
        try {
            // Parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
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
