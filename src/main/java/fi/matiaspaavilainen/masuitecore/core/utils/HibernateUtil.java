package fi.matiaspaavilainen.masuitecore.core.utils;

import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.models.MaSuitePlayer;
import net.md_5.bungee.config.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    private static EntityManagerFactory emFactory;
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static BungeeConfiguration config = new BungeeConfiguration();

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();
                Configuration dbInfo = config.load(null, "config.yml");
                Map<String, Object> settings = new HashMap<>();
                settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://" + dbInfo.getString("database.address") + ":" + dbInfo.getInt("database.port") + "/" + dbInfo.getString("database.name") + "?useSSL=false");
                settings.put(Environment.USER, dbInfo.getString("database.username"));
                settings.put(Environment.PASS, dbInfo.getString("database.password"));
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, false);

                // HikariCP settings

                // Maximum waiting time for a connection from the pool
                settings.put("hibernate.hikari.connectionTimeout", "5000");
                // Minimum number of ideal connections in the pool
                settings.put("hibernate.hikari.minimumIdle", "10");
                // Maximum number of actual connection in the pool
                settings.put("hibernate.hikari.maximumPoolSize", "10");
                settings.put("hibernate.hikari.maxLifetime", "30000");
                registryBuilder.applySettings(settings);

                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(MaSuitePlayer.class);

                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static EntityManager getEntityManager() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            emFactory = session.getEntityManagerFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emFactory.createEntityManager();
    }
}