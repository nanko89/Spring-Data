package bookshop.system.services;

import bookshop.system.models.entity.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorOrderByBooksCount();
}
