package fi.matiaspaavilainen.masuitecore;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginDescription;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Updator {
    public void checkVersion(PluginDescription pdf, String id) {
        ProxyServer.getInstance().getScheduler().runAsync(new MaSuiteCore(), () -> {
            try {
                HttpsURLConnection con = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id).openConnection();
                con.setRequestMethod("GET");
                String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

                if (!(version.equals(pdf.getVersion()))) {
                    System.out.println("[MaSuite] An update for " + pdf.getName() + " is available! Download it now at https://www.spigotmc.org/resources/" + id);
                }
            } catch (IOException ignored) {
                // For some reason the update check failed, this is very rare so the exception isn't printed.
                System.out.println("Failed to check for an update!");
            }
        });
    }
}
