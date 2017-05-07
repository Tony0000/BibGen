package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by manoel on 05/05/2017.
 */
public class BookHandler {

    private static EntityManager manager;

    public static void update(Book book){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(book);
        manager.getTransaction().commit();
    }

    public static void insert(Book book){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.persist(book);
        manager.getTransaction().commit();
    }

    public static void remove(Book book){
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.remove(findBy(book.getIsbn()));
        manager.getTransaction().commit();
    }

    public static Book findBy(String field){
        manager = HibernateUtil.getManager();
        return manager.find(Book.class, field);
    }

    public static List<Book> queryBookTableByTitle(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM Book WHERE title = :book_id");
        q.setParameter("book_id", condition);
        List<Book> books = q.getResultList();
        System.out.println(books.size());
        return books;
    }

    public static List<Book> queryBookTableByAuthor(String condition){
        EntityManager EM = HibernateUtil.getManager();
        Query q = EM.createQuery(
                "FROM Book WHERE author = :book_id");
        q.setParameter("book_id", condition);
        List<Book> books = q.getResultList();
        System.out.println(books.size());
        return books;
    }
}
