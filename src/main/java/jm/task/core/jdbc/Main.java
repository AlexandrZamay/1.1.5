package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hibernate.cfg.AvailableSettings.URL;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Sasha", "Perepelkin", (byte)69);
        userService.saveUser("Nikita", "Lozhka", (byte)22);
        userService.saveUser("Beauty", "Milfa", (byte)42);
        userService.saveUser("Someone", "Whosovich", (byte)15);

        userService.getAllUsers().forEach(x -> System.out.println("User с именем " + x.getName() + " выведен в консоль"));

        userService.cleanUsersTable();
        userService.dropUsersTable();

        UserDaoHibernateImpl.closeGlobalSession();
    }
}