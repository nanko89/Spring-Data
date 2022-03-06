package bookshop.system.services;

import bookshop.system.models.entity.Author;
import bookshop.system.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private static final String AUTHOR_FILE_PATH = "src/main/resources/file/authors.txt";


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(AUTHOR_FILE_PATH))
                .stream()
                .filter(author -> !author.isEmpty())
                .forEach(authorName -> {
                    String firstName = authorName.split("\\s+")[0];
                    String lastName = authorName.split("\\s+")[1];
                    Author author = new Author(firstName, lastName);
                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {

        Random random = new Random();
        Long randomId = random.nextLong(1,31);

        return authorRepository.findById(randomId).orElse(null);
    }
}
