package ufal.ic.entities;

import ufal.ic.util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by manoel on 05/05/2017.
 */
public class Logic {

    public static List<User> queryUserTable(String givenField, String findColumn, String likeThis){
        Query query = HibernateUtil.getSession().createQuery("select :given from User " +
                "where :findColumn = :param");
        query.setParameter("given", givenField);
        query.setParameter("findColumn",findColumn);
        query.setParameter("param",likeThis);

        return query.getResultList();
    }

    public static List<Book> queryBookTable(String givenField, String findColumn, String likeThis){
        Query query = HibernateUtil.getSession().createQuery("select :given from Book " +
                "where :findColumn = :param");
        query.setParameter("given", givenField);
        query.setParameter("findColumn",findColumn);
        query.setParameter("param",likeThis);
        return query.getResultList();
    }

}
