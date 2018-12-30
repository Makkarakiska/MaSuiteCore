package fi.matiaspaavilainen.masuitecore.core.utils;

import java.text.SimpleDateFormat;

public class Date {

    /**
     * Create formatted timestamp
     * @param date to format
     * @return formatted date
     */
    public String getDate(java.util.Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
