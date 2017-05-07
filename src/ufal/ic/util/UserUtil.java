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

    public static Vector<String> getColumns(){
        userColumns = new Vector<>();
        userColumns.add("Enrollment");
        userColumns.add("Name");
        userColumns.add("Email");
        userColumns.add("Course");
        return userColumns;
    }

    public static void update(User user){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(user);
        manager.getTransaction().commit();
    }

    public static void insert(User user){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    public static void remove(User user){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.remove(findBy(user.getEnrollment()));
        manager.getTransaction().commit();
    }

    public static User findBy(String field){
        manager = HibernateUtil.getManager();
        return manager.find(User.class, field);
    }

    public static List<User> queryUserTableByName(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM User WHERE name = :user_id");
        q.setParameter("user_id", condition);
        List<User> users = q.getResultList();
        return users;
    }

    public static List<User> queryUserTableByEmail(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM User WHERE email = :user_id");
        q.setParameter("user_id", condition);
        List<User> users = q.getResultList();
        return users;
    }

}
