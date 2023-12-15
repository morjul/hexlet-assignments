package exercise.controller;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    // BEGIN
    @GetMapping(path = "")
    public ResponseEntity<List<AuthorDTO>> index() {
        var authors = authorService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(authors.size()))
                .body(authors);
    }

    @GetMapping(path = "/{id}")
    public AuthorDTO show(@PathVariable long id) {
        var authorDTO =  authorService.findById(id);
        return authorDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@Valid @RequestBody AuthorCreateDTO authorData) {
        AuthorDTO authorDTO = authorService.create(authorData);
        return authorDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorDTO> update(@Valid @RequestBody AuthorUpdateDTO authorData, @PathVariable Long id) {
        AuthorDTO update = authorService.update(authorData, id);
        return ResponseEntity.ok()
                .body(update);
    }

    @DeleteMapping("/{id}") // Удаление страницы
    public void destroy(@PathVariable long id) {
        authorService.delete(id);
    }
    // END
}
