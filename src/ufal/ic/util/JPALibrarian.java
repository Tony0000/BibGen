package ufal.ic.util;

import ufal.ic.entities.Librarian;

import javax.persistence.EntityManager;

/**
 * Created by manoel on 05/05/2017.
 */
public class JPALibrarian {
    private static EntityManager manager;

    /** Find the librarian account in the database given its login name
     * @param field login field
     * @return the account found */
    public static Librarian findBy(String field){
        manager = JPAClient.getSessionManager();
        return manager.find(Librarian.class, field);
    }
}
