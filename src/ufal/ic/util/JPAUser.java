package ufal.ic.util;

import ufal.ic.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Vector;

/**
 * Class which handles commands such as insert, update, remove, find to the hibernate connection.
 * Created by manoel on 05/05/2017.
 */
public class JPAUser {

    private static EntityManager manager;
    private static Vector<String> userColumns;
    public static final int SEARCHABLE_FIELDS = 3;

    /** Creates an vector with users header and returns the vector */
    public static Vector<String> getColumns(){
        userColumns = new Vector<>();
        userColumns.add("Enrollment");
        userColumns.add("Name");
        userColumns.add("Email");
        userColumns.add("Course");
        return userColumns;
    }

    /** Updates the data of a given user in the database
     * @param user the object to be updated */
    public static void update(User user){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.merge(user);
        manager.getTransaction().commit();
    }

    /** Inserts the data of a given user into the database
     * @param user the object to be inserted */
    public static void insert(User user){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    /** Removes the data of a given user in the database
     * @param user the object to be removed */
    public static void remove(User user){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.remove(findBy(user.getEnrollment()));
        manager.getTransaction().commit();
    }

    /** Finds the data of a given user in the database
     * @param field the primary key of the object
     * @return the object found */
    public static User findBy(String field){
        manager = JPAClient.getSessionManager();
        return manager.find(User.class, field);
    }

    public static User queryByField(String identifier, String condition){
        EntityManager EM = JPAClient.getSessionManager();

        Query q = EM.createNamedQuery(identifier, User.class)
                .setParameter("user_id", condition);
        List<User> users = q.getResultList();
        return users.get(0);
    }
}