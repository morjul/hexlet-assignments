package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping("") // Список страниц
    public List<Person> index() {
        return personRepository.findAll();
    }

    @PostMapping("") // Создание страницы
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        Person newPerson = new Person();
        newPerson.setFirstName(person.getFirstName());
        newPerson.setLastName(person.getLastName());
        personRepository.save(newPerson);
        return newPerson;
    }

    @DeleteMapping("/{id}") // Удаление страницы
    public void destroy(@PathVariable long id) {
        personRepository.deleteById(id);
    }
    // END
}
