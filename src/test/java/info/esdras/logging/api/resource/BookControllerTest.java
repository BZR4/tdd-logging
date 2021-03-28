package info.esdras.logging.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.esdras.logging.api.dto.Book;
import info.esdras.logging.api.dto.BookDTO;
import info.esdras.logging.exception.BusinessException;
import info.esdras.logging.repository.BookRepository;
import info.esdras.logging.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService service;

    @MockBean
    BookRepository repository;

    private static final String BOOK_API = "/api/books";

    @DisplayName("Deve criar um livro com sucesso.")
    @Test
    void createBookTest() throws Exception {

        var bookDTO = createNewBook();

        var savedBook = new Book().setId(1L)
                .setTitle(bookDTO.getTitle())
                .setAuthor(bookDTO.getAuthor())
                .setIsbn(bookDTO.getIsbn());

        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);

        final String json = new ObjectMapper().writeValueAsString(savedBook);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("Meu livro"))
                .andExpect(jsonPath("author").value("Autor"))
                .andExpect(jsonPath("isbn").value("12345678"));
    }



    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação do livro.")
    @Test
    void createInvalidBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));
    }

    @DisplayName("Develançar erro ao tentar cadastrar um livro com isbn já utilizado por outro.")
    @Test
    void createBookWIthDuplicatedIsbn() throws Exception {
        BookDTO dto = createNewBookDTO();
        String json = new ObjectMapper().writeValueAsString(dto);
        String errorMessage = "Isbn já cadastrado";

        BDDMockito.given(service.save(Mockito.any(Book.class)))
                .willThrow(new BusinessException(errorMessage));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(errorMessage));

    }



    private BookDTO createNewBookDTO() {
        return new BookDTO("Meu livro", "Autor", "12345678");
    }

    private Book createNewBook() {
        return new Book("Meu livro", "Autor", "12345678");
    }
}
