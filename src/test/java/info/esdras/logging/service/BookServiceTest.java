package info.esdras.logging.service;

import info.esdras.logging.api.dto.Book;
import info.esdras.logging.repository.BookRepository;
import info.esdras.logging.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        this.bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void saveBookTest() {

        // Cenario
        Book book = new Book("As aventuras", "Fulano", "987654321");

        Mockito.when(bookRepository.save(book))
                .thenReturn(new Book().setId(1L).setTitle("As aventuras").setAuthor("Fulano").setIsbn("987654321"));

        //Execucao
        Book savedBook = bookService.save(book);

        //Verificacao
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
        assertThat(savedBook.getIsbn()).isEqualTo("987654321");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
    }
}
