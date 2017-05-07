package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Class which handles commands such as insert, update, remove, find to the hibernate connection.
 * Created by manoel on 05/05/2017.
 */
public class UserHandler {

    private static EntityManager manager;

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
