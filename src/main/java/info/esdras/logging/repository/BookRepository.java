package info.esdras.logging.repository;


import info.esdras.logging.api.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
