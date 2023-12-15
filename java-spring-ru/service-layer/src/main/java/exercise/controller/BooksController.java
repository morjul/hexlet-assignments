package exercise.controller;

import java.util.List;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping(path = "")
    public ResponseEntity<List<BookDTO>> index() {
        var books = bookService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @GetMapping(path = "/{id}")
    public BookDTO show(@PathVariable long id) {
        var bookDTO =  bookService.findById(id);
        return bookDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookCreateDTO bookData) {
        BookDTO bookDTO = bookService.create(bookData);
        if (bookDTO == null) {
            return ResponseEntity.status(400).body(bookDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDTO> update(@Valid @RequestBody BookUpdateDTO bookData, @PathVariable Long id) {
        BookDTO update = bookService.update(bookData, id);
        if (update == null) {
            return ResponseEntity.status(400).body(update);
        }
        return ResponseEntity.ok()
                .body(update);
    }

    @DeleteMapping("/{id}") // Удаление страницы
    public void destroy(@PathVariable long id) {
        bookService.delete(id);
    }
    // END
}
