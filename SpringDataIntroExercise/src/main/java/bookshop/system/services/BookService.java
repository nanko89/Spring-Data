package bookshop.system.services;

import bookshop.system.models.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<Book> findAllAuthorsWithBookReleaseDateBeforeYear(int year);

    List<String> findAllBooksFromAuthorOrderByReleaseDateAndTitle(String firstName, String lastName);
}
