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
        Session session = null;
        try {
            String sqlRequest = "CREATE TABLE IF NOT EXISTS User " +
                    "(id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(20), " +
                    " lastname VARCHAR(20), " +
                    " age INTEGER)";
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlRequest).addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            String sqlRequest = "DROP TABLE IF EXISTS user";
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sqlRequest);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
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
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            String sqlRequest = "DELETE FROM User";
            session.createQuery(sqlRequest).executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Ошибка Hibernate");
            e.printStackTrace();
        }
    }
    public static void closeGlobalSession(){
        SESSION_FACTORY.close();
    }

}
