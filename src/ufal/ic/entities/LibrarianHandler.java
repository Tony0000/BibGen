package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.EntityManager;

/**
 * Created by manoel on 05/05/2017.
 */
public class LibrarianHandler {
    private static EntityManager manager;

    public static Librarian findBy(String field){
        manager = HibernateUtil.getSession();
        return manager.find(Librarian.class, field);
    }
}
