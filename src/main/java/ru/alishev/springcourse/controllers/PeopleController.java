package ru.alishev.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        // Получим всех людей из DAO и передадим на отображение
        model.addAttribute("people", personDAO.showAll());


        return "people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        // получаем одного человека из DAO и передадим этого человека на отображение

        model.addAttribute("person", personDAO.show(id));

        return "person";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person",new Person());

        return "newPerson";
    }

    @PostMapping
    public String createPerson(@ModelAttribute Person person){
        // добавили нового пользователя в DAO.
        personDAO.save(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));

        return "edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.delete(id);

        return "redirect:/people";
    }

}
