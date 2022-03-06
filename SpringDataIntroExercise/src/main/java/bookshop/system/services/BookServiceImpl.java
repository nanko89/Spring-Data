package bookshop.system.services;

import bookshop.system.models.entity.*;
import bookshop.system.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    private static final String BOOK_FILE_PATH = "src/main/resources/file/books.txt";


    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0){
            return;
        }

       Files.readAllLines(Path.of(BOOK_FILE_PATH))
               .forEach(row -> {
                   String[] currentBook = row.split("\\s+");

                   Book book = createBook(currentBook);

                   bookRepository.save(book);

               });
    }

    private Book createBook(String[] currentBook) {
        EditionType editionType = EditionType.values()[Integer.parseInt(currentBook[0])];
        LocalDate releaseDate = LocalDate.parse(currentBook[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(currentBook[2]);
        BigDecimal price = new BigDecimal(currentBook[3]);
        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(currentBook[4])];

        String title = Arrays
                .stream(currentBook)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService.getRandomCategories();

        Book book = new Book(editionType,releaseDate,copies,price,ageRestriction,title,author,categories);
        return book;
    }
}
