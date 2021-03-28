package info.esdras.logging.service;

import info.esdras.logging.api.dto.Book;
import info.esdras.logging.exception.BusinessException;
import info.esdras.logging.repository.BookRepository;
import info.esdras.logging.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setup() {
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void saveBookTest() {

        // Cenario
        Book book = getBook();

        Mockito.when(repository.save(book))
                .thenReturn(new Book()
                        .setId(1L)
                        .setTitle("As aventuras")
                        .setAuthor("Fulano")
                        .setIsbn("987654321"));

        //Execucao
        Book savedBook = service.save(book);

        //Verificacao
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
        assertThat(savedBook.getIsbn()).isEqualTo("987654321");
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
    }

    private Book getBook() {
        return new Book("As aventuras", "Fulano", "987654321");
    }

    // Teste na camada de serviço
    @Test
    @DisplayName("Deve lançar erro de negócio ao tentar salvar livro com isbn duplicado")
    void shoulNotSaveABookWithDuplicatedISBN() {

        // Cenario
        Book book = getBook();

        Mockito.when(repository.existsByIsbn(Mockito.anyString()))
                .thenReturn(true);

        // Execucao
        Throwable exception = catchThrowable(() -> service.save(book));

        // Validacao
        assertThat(exception).isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado");

        Mockito.verify(repository, Mockito.never()).save(book);
    }
}
