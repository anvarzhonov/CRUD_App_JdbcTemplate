package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.controllers.PeopleController;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PersonDAO {
    private static int AUTO_INCREMENT;

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

        Statement statement = null;
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
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

        return null;
    }

    public void save(Person person) {
//        person.setId(++AUTO_INCREMENT);
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO person VALUES(" + 1 + ",'" + person.getName() + "'," + person.getAge() + ",'" +
                    person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id); // нашли человека по его id с помощью show. Это человек которого
        // необходимо обновить
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
//        people.removeIf(person -> person.getId() == id);
    }
}
