package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
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
    @Transactional(rollbackOn = org.hibernate.HibernateException.class)
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional(rollbackOn = org.hibernate.HibernateException.class)
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional(rollbackOn = org.hibernate.HibernateException.class)
    public List<User> getAllUsers() {
        List<User> result;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            result = session.createQuery("SELECT u FROM User u", User.class).getResultList();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    @Transactional(rollbackOn = org.hibernate.HibernateException.class)
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User u").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
