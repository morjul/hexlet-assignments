package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAll() {
        var books = repository.findAll();
        var result = books.stream()
                .map(bookMapper::map)
                .toList();
        return result;
    }

    public BookDTO findById(Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
        var bookDTO = bookMapper.map(book);
        return bookDTO;
    }

    public BookDTO create(BookCreateDTO bookData) {
        var book = bookMapper.map(bookData);
        Optional<Author> optionalAuthor = authorRepository.findById(bookData.getAuthorId());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.getBooks().add(book);
            book.setAuthor(author);
            authorRepository.save(author);
        } else {
            return null;
        }
        repository.save(book);
        var bookDTO = bookMapper.map(book);
        return bookDTO;
    }

    public BookDTO update(BookUpdateDTO bookData, Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        if (bookData.getAuthorId().isPresent()) {
            Optional<Author> optionalAuthor = authorRepository.findById(bookData.getAuthorId().get());
            if (optionalAuthor.isPresent()) {
                Author author = optionalAuthor.get();
                author.getBooks().add(book);
                book.setAuthor(author);
                authorRepository.save(author);
            } else {
                return null;
            }
        }
        bookMapper.update(bookData, book);
        repository.save(book);
        var bookDTO = bookMapper.map(book);
        return bookDTO;
    }

    public void delete(Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthor().getId());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.getBooks().remove(book);
            book.setAuthor(null);
            authorRepository.save(author);
        }

        repository.deleteById(id);
    }
    // END
}
