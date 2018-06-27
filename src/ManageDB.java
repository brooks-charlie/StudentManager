import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageDB {

    public SessionFactory createFactory(){
        SessionFactory factory;
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Student.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return factory;
    }
}
