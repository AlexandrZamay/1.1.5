package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/TestKata";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return connection;
        // реализуйте настройку соеденения с БД
    }

    private static Configuration hibernateConfig(){

        Configuration configuration = new Configuration();

        Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", LOGIN);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.current_session_context_class", "thread");
        properties.put("hibernate.show_sql", true); //для удобства
        properties.put("hbm2ddl.auto", "create-drop"); //права для hibernate

        configuration.addProperties(properties).addAnnotatedClass(User.class);

        return configuration;

    }

    public static SessionFactory globalSession(){
        SessionFactory sessionFactory = hibernateConfig().buildSessionFactory();
        return  sessionFactory;
    }
}