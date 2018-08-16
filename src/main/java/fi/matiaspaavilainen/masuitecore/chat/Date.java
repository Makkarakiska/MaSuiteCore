package fi.matiaspaavilainen.masuitecore.chat;

import java.text.SimpleDateFormat;

public class Date {
    public static String getDate() {
        java.util.Date now = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(now);
    }
}
