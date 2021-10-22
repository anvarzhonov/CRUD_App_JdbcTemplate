package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> showAll() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    // если у нас в списке будет один объект, то он будет возвращен, иначе список пустой и будет возвращен null
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("SELECT * FROM person WHERE id= ? ");
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery(); // выполняем запрос к базе данных
//
//            resultSet.next();
//
//            person = new Person();
//
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setEmail(resultSet.getString("email"));
//            person.setAge(resultSet.getInt("age"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES(1,?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, age= ?, email=? WHERE id =?", updatedPerson.getName()
                , updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
       jdbcTemplate.update("DELETE FROM person WHERE id =?", id);
    }
}
