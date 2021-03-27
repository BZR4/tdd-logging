package info.esdras.logging.api.dto;

import javax.validation.constraints.NotEmpty;

public class BookDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String isbn;

    public BookDTO(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public static BookDTO getInstanceFromBook(Book book) {
        return new BookDTO()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn());
    }

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public BookDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookDTO setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
