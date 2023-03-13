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
        String sqlRequest = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(20) NOT NULL , " +
                "lastName VARCHAR(20) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sqlRequest);
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
        }

    }

    public void dropUsersTable() {
        String sqlRequest = "DROP TABLE IF EXISTS user";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sqlRequest);
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
        }

    }


    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT user(name, lastname, age) " + "VALUES (?,?,?) ";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user (name, LastName, Age) VALUES (?,?,?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
        }
    }

    public void removeUserById(long id) {
        String sqlRequest = "DELETE FROM user WHERE id = " + id;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sqlRequest);
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
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
        String sqlRequest = "TRUNCATE user ";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(sqlRequest);
        } catch (SQLException e) {
            System.out.println("Ошибка SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Проблема с драйвером!");
        }


    }
}