package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.cfg.AvailableSettings.URL;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();
        userDao.saveUser("Nikita", "Lozhka", (byte) 25);
        userDao.saveUser("John", "Titor", (byte) 47);
        userDao.saveUser("Suzuha", "Amane", (byte) 8);
        userDao.saveUser("Beauty", "Milf", (byte) 35);

        userDao.getAllUsers();
        // Альтернативный способ вывода, чтобы не добавлять вывод в getAllUsers();
        // userDao.getAllUsers().forEach(user -> System.out.println("User с именем - " + user.getName() + " добавлен в базу данных"));

        userDao.cleanUsersTable();
        userDao.dropUsersTable();
       }
}
