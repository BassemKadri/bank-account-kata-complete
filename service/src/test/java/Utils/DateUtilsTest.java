package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtilsTest {
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public static Date getDate(String dateAsString) {
        Date date = null;
        try {
            date = formatter.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean checkDateBetweenTwoDate(Date d1, Date start, Date end) {
        return start.compareTo(d1) * d1.compareTo(end) > 0;
    }
}
