package ufal.ic.util;

/**
 * Created by manoel on 04/05/2017.
 */
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** Helper class in which provides the EntityManager to handle the hibernate connection to your database*/
public class HibernateUtil {

    private static final EntityManager manager = buildSessionFactory();

    private static EntityManager buildSessionFactory() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("BibGen");
        return factory.createEntityManager();
    }

    /** Session handler for hibernate commands
     * @return an EntityManager from which you can grab the session */
    public static EntityManager getManager() {
        return manager;
    }
}
