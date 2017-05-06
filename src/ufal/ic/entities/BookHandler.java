package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.EntityManager;

/**
 * Created by manoel on 05/05/2017.
 */
public class BookHandler {

    private static EntityManager manager;

    public static void update(Book book){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.merge(book);
        manager.getTransaction().commit();
    }

    public static void insert(Book book){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.persist(book);
        manager.getTransaction().commit();
    }

    public static void remove(Book book){
        manager = HibernateUtil.getSession();
        manager.getTransaction().begin();
        manager.remove(findBy(book.getIsbn()));
        manager.getTransaction().commit();
    }

    public static Book findBy(String field){
        manager = HibernateUtil.getSession();
        return manager.find(Book.class, field);
    }
}
