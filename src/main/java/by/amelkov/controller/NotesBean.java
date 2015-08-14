package by.amelkov.controller;

import by.amelkov.dao.NoteDAO;
import by.amelkov.dao.NoteDAOImplement;
import by.amelkov.model.Note;
import by.amelkov.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@ManagedBean(name = "notes")
@SessionScoped
public class NotesBean implements Serializable {

    private String text = "";
    private boolean isEditing;
    private int id;
    private List<Note> notesList;

    public void addNoteRequest() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String login = (String) session.getAttribute("acptLogin");

        NoteDAO noteDAO = new NoteDAOImplement();
        User user = new User(login, null);
        Note note = new Note(text, user, new Timestamp(new java.util.Date().getTime()));
        noteDAO.addNote(note);
        text = null;

    }

    public void editNoteRequest() {

        NoteDAO noteDAO = new NoteDAOImplement();
        Note note = noteDAO.getNote(id);
        if (note != null) {
            note.setText(text);
            note.setDateCreate(new Timestamp(new java.util.Date().getTime()));
            noteDAO.editNote(note);
            text = null;
            isEditing = false;
        }
    }

    public void editingRequest(int id) {

        NoteDAO noteDAO = new NoteDAOImplement();
        Note note = noteDAO.getNote(id);
        if (note != null) {
            this.id = id;
            isEditing = true;
            text = note.getText();
        }
    }

    public void deleteNoteRequest(int id) {

        NoteDAO noteDAO = new NoteDAOImplement();
        Note note = noteDAO.getNote(id);
        if (note != null) {
            this.id = id;
            noteDAO.deleteNote(note);
        }
        text = null;
    }

    public void getNotesRequest() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String login = (String) session.getAttribute("acptLogin");
        User user = new User(login, null);

        NoteDAO noteDAO = new NoteDAOImplement();
        notesList = noteDAO.getLastUserNotes(user);
    }

    public void getAllNotesRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String login = (String) session.getAttribute("acptLogin");
        User user = new User(login, null);

        NoteDAO noteDAO = new NoteDAOImplement();
        notesList = noteDAO.getUserNotes(user);
    }

    public List<Note> getNotesList() {
        getNotesRequest();
        return notesList;
    }

    public List<Note> getAllNotesList() {
        getAllNotesRequest();
        return notesList;
    }

    public void setNotesList(List<Note> notesList) {
        this.notesList = notesList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
