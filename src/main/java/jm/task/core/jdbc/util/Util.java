package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/TestKata";
    private static final String login = "root";
    private static final String password = "root";


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, login, password);
        return connection;
        // реализуйте настройку соеденения с БД
    }

//    public static Connection getConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(URL, login, password);
//        return connection;
//        // реализуйте настройку соеденения с БД
//    }
}
