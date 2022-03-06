package bookshop.system.services;

import bookshop.system.models.entity.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

}
