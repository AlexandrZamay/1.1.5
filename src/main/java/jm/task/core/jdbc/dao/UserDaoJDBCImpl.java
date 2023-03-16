package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                        "(id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                        " name VARCHAR(20), " +
                        " lastname VARCHAR(20), " +
                        " age INTEGER)";
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "DROP TABLE IF EXISTS USERS;";
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "INSERT user(name, lastname, age) " +
                        "VALUES ('" + name + "', '" + lastName + "', " + age + "); ";
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM user WHERE id = " + id + ";";
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                Byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                result.add(user);
            }
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "TRUNCATE user;";
                statement.executeUpdate(sql);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}