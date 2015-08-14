package by.amelkov.controller;

import by.amelkov.dao.UserDAO;
import by.amelkov.dao.UserDAOImplement;
import by.amelkov.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;

    public void logInRequest() throws IOException {
        UserDAO userDAO = new UserDAOImplement();

        User tempUser = userDAO.getUser(username, password);

        if (tempUser != null) {
            if (tempUser.getPassword().equals(password)) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

                session.setAttribute("acptLogin", tempUser.getLogin());
                session.setAttribute("isAdding", true);
                facesContext.getExternalContext().redirect("index.xhtml");
            } else {
                FacesMessage fm = new FacesMessage("Incorrect password!");
                FacesContext.getCurrentInstance().addMessage("Incorrect password!", fm);
            }
        } else {
            FacesMessage fm = new FacesMessage("User not found!");
            FacesContext.getCurrentInstance().addMessage("User not found!", fm);
        }
        password = null;
    }

    public void logOutRequest() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("acptLogin", null);
        facesContext.getExternalContext().redirect("login.xhtml");
    }

    public boolean isLoggedIn() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String tmp = (String) session.getAttribute("acptLogin");
        return (tmp != null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
