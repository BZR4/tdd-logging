package info.esdras.logging.api.resource;

import info.esdras.logging.api.dto.Book;
import info.esdras.logging.api.dto.BookDTO;
import info.esdras.logging.api.exceptions.ApiErrors;
import info.esdras.logging.exception.BusinessException;
import info.esdras.logging.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;


    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);
        return BookDTO.getInstanceFromBook(book);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException argumentNotValidException) {
        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException businessException) {
        return new ApiErrors(businessException);
    }
}
