package fi.matiaspaavilainen.masuitecore.core;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Updator {

    private String[] info;

    /**
     * Constructor for Info
     * @param info version, name, id
     */
    public Updator(String[] info) {
        this.info = info;
    }

    /**
     * Check version with BungeeCord
     */
    public void checkUpdates() {

        CompletableFuture.runAsync(() -> {
            try {
                HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + info[2]).openConnection();
                con.setRequestMethod("GET");
                String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

                if (!(version.equals(info[0]))) {
                    System.out.println("[MaSuite] An update for " + info[1] + " is available! Download it now at https://www.spigotmc.org/resources/" + info[2]);
                }
            } catch (IOException ignored) {
                // For some reason the update check failed, this is very rare so the exception isn't printed.
                System.out.println("[MaSuite] Failed to check for an update!");
            }
        });

    }
}
