package by.amelkov.dao;

import by.amelkov.model.User;

public interface UserDAO {
    void addUser(User user);

    void editUser(User user);

    void deleteUser(User user);

    User getUser(String login, String password);
}
