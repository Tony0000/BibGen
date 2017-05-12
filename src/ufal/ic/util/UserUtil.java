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
public class UserUtil {

    private static EntityManager manager;
    private static Vector<String> userColumns;

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
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(user);
        manager.getTransaction().commit();
    }

    /** Inserts the data of a given user into the database
     * @param user the object to be inserted */
    public static void insert(User user){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    /** Removes the data of a given user in the database
     * @param user the object to be removed */
    public static void remove(User user){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.remove(findBy(user.getEnrollment()));
        manager.getTransaction().commit();
    }

    /** Finds the data of a given user in the database
     * @param field the primary key of the object
     * @return the object found */
    public static User findBy(String field){
        manager = HibernateUtil.getManager();
        return manager.find(User.class, field);
    }

    /** Finds the data of a user in the database given its name
     * @param condition name field of the user
     * @return list of the objects that satisfy the query */
    public static List<User> queryUserTableByName(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM User WHERE name = :user_id");
        q.setParameter("user_id", condition);
        List<User> users = q.getResultList();
        return users;
    }

    /** Finds the data of a user in the database given its email
     * @param condition email field of the user
     * @return list of the objects that satisfy the query */
    public static List<User> queryUserTableByEmail(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM User WHERE email = :user_id");
        q.setParameter("user_id", condition);
        List<User> users = q.getResultList();
        return users;
    }

}
