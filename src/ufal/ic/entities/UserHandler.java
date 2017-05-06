package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.EntityManager;

/**
 * Class which handles commands such as insert, update, remove, find to the hibernate connection.
 * Created by manoel on 05/05/2017.
 */
public class UserHandler {

    private static EntityManager manager;

    public static void update(User user){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.merge(user);
        manager.getTransaction().commit();
    }

    public static void insert(User user){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
    }

    public static void remove(User user){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.remove(findBy(user.getEnrollment()));
        manager.getTransaction().commit();
    }

    public static User findBy(String field){
        manager = HibernateUtil.getSession();
        return manager.find(User.class, field);
    }

}
