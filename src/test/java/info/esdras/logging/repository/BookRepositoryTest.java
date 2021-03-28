package info.esdras.logging.repository;

import info.esdras.logging.api.dto.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    void returnTrueWhenIsbnExists() {
        // Cenario
        String isbn = "123";
        entityManager.persist(new Book().setTitle("Enter de Matrix").setAuthor("Lana W.").setIsbn(isbn));

        //Execucao
        boolean exists = repository.existsByIsbn(isbn);

        //Verificacao
        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando existir um livro na base com isbn informado")
    void returnTrueWhenIsbnEDoesntExists() {
        // Cenario
        String isbn = "123";

        //Execucao
        boolean exists = repository.existsByIsbn(isbn);

        //Verificacao
        Assertions.assertThat(exists).isFalse();
    }

}
