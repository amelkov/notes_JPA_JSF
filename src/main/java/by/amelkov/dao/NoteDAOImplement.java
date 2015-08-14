package by.amelkov.dao;

import by.amelkov.hibernate.HibernateFactory;
import by.amelkov.model.Note;
import by.amelkov.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class NoteDAOImplement implements NoteDAO {

    public void addNote(Note note) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(note);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }

    }

    public void editNote(Note note) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(note);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void deleteNote(Note note) {

        Session session = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(note);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public Note getNote(int id) {

        Session session = null;
        Note note = null;
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            note = (Note) session.get(Note.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return note;
    }

    public List<Note> getUserNotes(User login) {

        Session session = null;
        List notes = new ArrayList<Note>();
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(Note.class).add(Restrictions.like("login", login)).addOrder(Order.desc("dateCreate"));
            notes = crit.list();

        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return notes;
    }

    public List<Note> getLastUserNotes(User login) {

        Session session = null;
        List notes = new ArrayList<Note>();
        try {
            session = HibernateFactory.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(Note.class).add(Restrictions.like("login", login)).addOrder(Order.desc("dateCreate"));
            crit.setMaxResults(10);
            notes = crit.list();

        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return notes;
    }
}
