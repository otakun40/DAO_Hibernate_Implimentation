package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private Session session;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "age INT)").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.remove(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result;
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        result = session.createQuery("SELECT u FROM User u", User.class).getResultList();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User u").executeUpdate();
        session.getTransaction().commit();
    }
}
