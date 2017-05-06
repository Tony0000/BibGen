package ufal.ic.entities;

import javax.persistence.Query;

/**
 * Created by manoel on 05/05/2017.
 */
public class Logic {

    public static String queryUserTable(String givenField, String findColumn, String likeThis){
        Query query = HibernateUtil.getSession().createQuery("select :given from User " +
                "where :findColumn = :param");
        query.setParameter("given", givenField);
        query.setParameter("findColumn",findColumn);
        query.setParameter("param",likeThis);
        System.out.println(query.getSingleResult().toString());
        return query.getSingleResult().toString();
    }

    public static String queryBookTable(String givenField, String findColumn, String likeThis){
        Query query = HibernateUtil.getSession().createQuery("select :given from Book " +
                "where :findColumn = :param");
        query.setParameter("given", givenField);
        query.setParameter("findColumn",findColumn);
        query.setParameter("param",likeThis);
        System.out.println(query.getSingleResult().toString());
        return query.getSingleResult().toString();
    }

}
