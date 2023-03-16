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
                String sql = "CREATE TABLE IF NOT EXISTS user " +
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
                String sql = "DROP TABLE IF EXISTS user;";
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
                String sql = "INSERT INTO user(name, lastName, age) " +
                        "VALUES (?, ?, ?) ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
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
        List<User> list = new ArrayList<>();
        String sqlRequest = "SELECT * FROM user";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sqlRequest);
            ResultSet resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                list.add(user);
                System.out.println("User с именем - " + user.getName() + " добавлен в базу данных");
            }


        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
        }

        return list;
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