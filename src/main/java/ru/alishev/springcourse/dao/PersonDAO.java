package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static final String URL = "jdbc:mysql://localhost:3307/jdbc_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "2001";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> showAll() {
        List<Person> people = new ArrayList<>();

        Statement statement;
        try {
            statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();

                int id_db = resultSet.getInt("id");
                String name_db = resultSet.getString("name");
                int age_db = resultSet.getInt("age");
                String email_db = resultSet.getString("email");

                person.setId(id_db);
                person.setName(name_db);
                person.setAge(age_db);
                person.setEmail(email_db);

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person WHERE id= ? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
//        person.setId(++AUTO_INCREMENT);
//        people.add(person);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person VALUES(1,?,?,?)");

            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated = show(id); // нашли человека по его id с помощью show. Это человек которого
//        // необходимо обновить
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE person SET name=?,age=?,email=? WHERE id=?");

            preparedStatement.setString(1,updatedPerson.getName());
            preparedStatement.setInt(2,updatedPerson.getAge());
            preparedStatement.setString(3,updatedPerson.getEmail());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
//        people.removeIf(person -> person.getId() == id);

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
