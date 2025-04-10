package carrent.utils;

import carrent.models.Rental;
import carrent.models.User;
import carrent.models.Vehicle;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            String dbUrl = System.getenv("DATABASE_URL");
            System.out.println("DATABASE_URL = " + dbUrl);

            if (dbUrl == null || dbUrl.isEmpty()) {
                throw new IllegalStateException("Zmienna środowiskowa DATABASE_URL nie została ustawiona!");
            }




            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
            configuration.registerTypeOverride(new JsonBinaryType(), new String[]{"jsonb"});
            configuration.addAnnotatedClass(Vehicle.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Rental.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Inicialize Hibernate ERROR: " + ex);
        }
    }
}