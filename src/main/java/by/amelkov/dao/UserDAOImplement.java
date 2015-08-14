package by.amelkov.dao;

import by.amelkov.hibernate.HibernateFactory;
import by.amelkov.model.User;
import org.hibernate.Session;

public class UserDAOImplement implements UserDAO {

    public void addUser(User user) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    public void editUser(User user) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void deleteUser(User user) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public User getUser(String login, String password) {

        Session session = null;
        User user = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            user = (User) session.get(User.class, login);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return user;
    }
}