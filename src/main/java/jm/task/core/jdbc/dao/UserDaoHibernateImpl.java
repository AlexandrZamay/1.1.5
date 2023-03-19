package jm.task.core.jdbc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;
import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final static SessionFactory SESSION_FACTORY = Util.globalSession();


    public UserDaoHibernateImpl() {

    }


    @Override

    public void createUsersTable() {
        try {
            String sqlRequest = "CREATE TABLE IF NOT EXISTS User " +
                    "(id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(20), " +
                    " lastname VARCHAR(20), " +
                    " age INTEGER)";
            Session session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlRequest).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            closeGlobalSession();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
        String sqlRequest = "DROP TABLE IF EXISTS user";
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery(sqlRequest);
        query.executeUpdate();
        session.getTransaction().commit();
        } catch (Exception e) {
            closeGlobalSession();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        } catch (Exception e) {
            closeGlobalSession();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.remove(user);
        session.getTransaction().commit();
        } catch (Exception e) {
            closeGlobalSession();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = null;
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM User ");
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            closeGlobalSession();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        try {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        String sqlRequest = "DELETE FROM User";
        session.createQuery(sqlRequest).executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        closeGlobalSession();
    }
    }
    public static void closeGlobalSession( ){
        SESSION_FACTORY.close();
    }
}
