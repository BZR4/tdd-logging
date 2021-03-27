package info.esdras.logging.service.impl;

import info.esdras.logging.api.dto.Book;
import info.esdras.logging.repository.BookRepository;
import info.esdras.logging.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    public BookServiceImpl() {

    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
