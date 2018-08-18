package fi.matiaspaavilainen.masuitecore.chat;

import java.text.SimpleDateFormat;

public class Date {
    public String getDate(java.util.Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
