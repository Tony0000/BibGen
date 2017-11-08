package ufal.ic.control;

import ufal.ic.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Vector;

/**
 * Created by manoel on 05/05/2017.
 */
public class JPABook {

    private static EntityManager manager;
    private static Vector<String> columns;
    public static final int SEARCHABLE_FIELDS = 3;

    /** Creates an vector with books header and returns the vector */
    public static Vector<String> getBookColumns(){
        columns = new Vector<>();
        columns.add("ISBN");
        columns.add("Title");
        columns.add("Author");
        columns.add("Publisher");
        columns.add("Units");
        return columns;
    }

    /** Creates an vector with books header specifically for the rent table and returns the vector */
    public static Vector<String> getRentBookColumns(){
        columns = new Vector<>();
        columns.add("ISBN");
        columns.add("Title");
        columns.add("Author");
        columns.add("Rented at");
        columns.add("Due in");
        return columns;
    }

    /** Updates the data of a given book in the database
     * @param book the object to be updated */
    public static void update(Book book){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.merge(book);
        manager.getTransaction().commit();
    }

    /** Inserts the data of a given book into the database
     * @param book the object to be inserted */
    public static void insert(Book book){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.persist(book);
        manager.getTransaction().commit();
    }

    /** Removes the data of a given book in the database
     * @param book the object to be updated */
    public static void remove(Book book){
        manager = JPAClient.getSessionManager();
        manager.getTransaction().begin();
        manager.remove(findBy(book.getIsbn()));
        manager.getTransaction().commit();
    }

    /** Finds the data of a book in the database
     * @param field the primary key of the object
     * @return the object found */
    public static Book findBy(String field){
        manager = JPAClient.getSessionManager();
        return manager.find(Book.class, field);
    }

    /** Finds the data of a book in the database given its title
     * @param condition title field of the book
     * @return list of the objects that satisfy the query */
    public static Book queryByField(String identifier, String condition){
        EntityManager EM = JPAClient.getSessionManager();

        Query q = EM.createNamedQuery(identifier, Book.class)
                .setParameter("book_id", condition);
        List<Book> books = q.getResultList();
        return books.get(0);
    }
}
