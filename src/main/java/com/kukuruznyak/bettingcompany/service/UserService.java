package com.kukuruznyak.bettingcompany.service;

import com.kukuruznyak.bettingcompany.dao.UserDao;
import com.kukuruznyak.bettingcompany.entity.user.Client;
import com.kukuruznyak.bettingcompany.entity.user.User;
import com.kukuruznyak.bettingcompany.entity.user.UserRole;
import com.kukuruznyak.bettingcompany.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserService extends AbstractService {
    private static UserService instance;
    private UserDao userDao = daoFactory.getUserDao();
    private static ResourceBundle validationBundle = ResourceBundle.getBundle("validationPatterns");

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                    LOGGER.info("Instance of " + UserService.class.getSimpleName() + " was created");
                }
            }
        }
        return instance;
    }

    private UserService() {
    }

    public boolean isUserExist(String login, String password) throws ServiceException {
        User user;
        user = getUserByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    public User getUserByLogin(String login) {
        return userDao.getByLogin(login);
    }

    public List<User> getAllAdministrators() {
        return userDao.getUsersByRole(UserRole.ADMINISTRATOR.toString());
    }

    public List<User> getAllBookmakers() {
        return userDao.getUsersByRole(UserRole.BOOKMAKER.toString());
    }

    public List<User> getAllRiskControllers() {
        return userDao.getUsersByRole(UserRole.RISK_CONTROLLER.toString());
    }

    public List<User> getStaff() {
        List<User> staff = getAllAdministrators();
        staff.addAll(getAllBookmakers());
        staff.addAll(getAllRiskControllers());
        return staff;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        return clients;
    }

    public boolean isValidUser(User user) {
        if (!user.getFirstName().matches(validationBundle.getString("firstName"))) {
            user.setFirstName("");
            return false;
        }
        if (!user.getLastName().matches(validationBundle.getString("lastName"))) {
            user.setLastName("");
            return false;
        }
        if (!user.getLogin().matches(validationBundle.getString("login"))) {
            user.setLogin("");
            return false;
        }
        if (!user.getEmail().matches(validationBundle.getString("email"))) {
            user.setEmail("");
            return false;
        }
        System.out.println(validationBundle.getString("password"));
        if (!user.getPassword().matches(validationBundle.getString("password"))) {
            user.setPassword("");
            return false;
        }
        return true;
    }

    public User add(User user) {
        return userDao.add(user);
    }

    public User getUserById(String id) {
        return userDao.getById(Long.valueOf(id));
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(Long.valueOf(id));
    }
}