package fi.matiaspaavilainen.masuitecore.core;

import lombok.AllArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class Updator {

    private String version;
    private String name;
    private String resourceId;


    /**
     * Check version with BungeeCord
     */
    public void checkUpdates() {

        CompletableFuture.runAsync(() -> {
            try {
                HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openConnection();
                con.setRequestMethod("GET");
                String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

                if (!(version.equals(version.replace("-SNAPSHOT", "")))) {
                    System.out.println("[MaSuite] An update for " + name + " is available! Download it now at https://www.spigotmc.org/resources/" + resourceId);
                }
            } catch (IOException ignored) {
                System.out.println("[MaSuite] Failed to check for an update!");
            }
        });

    }
}
