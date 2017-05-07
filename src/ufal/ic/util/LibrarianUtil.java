package ufal.ic.util;

import ufal.ic.entities.Librarian;

import javax.persistence.EntityManager;

/**
 * Created by manoel on 05/05/2017.
 */
public class LibrarianUtil {
    private static EntityManager manager;

    public static Librarian findBy(String field){
        manager = HibernateUtil.getManager();
        return manager.find(Librarian.class, field);
    }
}
