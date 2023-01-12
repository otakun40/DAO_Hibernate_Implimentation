package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age INT)").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tr = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            tr.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) {
                tr.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tr = session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            tr.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) {
                tr.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        List<User> result = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            tr = session.beginTransaction();
            result = session.createQuery("SELECT u FROM User u", User.class).getResultList();
            tr.commit();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) {
                tr.rollback();
            }
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            tr = session.beginTransaction();
            session.createQuery("DELETE FROM User u").executeUpdate();
            tr.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tr != null) {
                tr.rollback();
            }
        }
    }
}
