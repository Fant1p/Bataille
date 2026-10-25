package fr.jeu.persistence;

import fr.jeu.persistence.entity.Partie;
import fr.jeu.persistence.entity.JoueurPartie;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY =
            buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || password == null) {
            throw new IllegalStateException(
                    "Les variables DB_USER et DB_PASSWORD doivent être configurées."
            );
        }

        Configuration configuration = new Configuration();

        configuration.setProperty(
                "hibernate.connection.driver_class",
                "org.postgresql.Driver"
        );
        configuration.setProperty(
                "hibernate.connection.url",
                "jdbc:postgresql://localhost:5432/jeu_bataille"
        );
        configuration.setProperty("hibernate.connection.username", user);
        configuration.setProperty("hibernate.connection.password", password);

        configuration.setProperty(
                "hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect"
        );
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.format_sql", "false");
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.jboss.logging").setLevel(Level.SEVERE);

        configuration.addAnnotatedClass(Partie.class);
        configuration.addAnnotatedClass(JoueurPartie.class);

        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        if (!SESSION_FACTORY.isClosed()) {
            SESSION_FACTORY.close();
        }
    }
}
