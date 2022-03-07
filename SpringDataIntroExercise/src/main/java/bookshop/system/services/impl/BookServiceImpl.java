package bookshop.system.services.impl;

import bookshop.system.models.*;
import bookshop.system.models.entity.Author;
import bookshop.system.models.entity.Book;
import bookshop.system.models.entity.Category;
import bookshop.system.repositories.BookRepository;
import bookshop.system.services.AuthorService;
import bookshop.system.services.BookService;
import bookshop.system.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

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

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(year, 1,1));
    }

    @Override
    public List<String> findAllAuthorsWithBookReleaseDateBeforeYear(int year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s",
                        book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct().
                collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksFromAuthorOrderByReleaseDateAndTitle(String firstName, String lastName) {
        return bookRepository.findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName,lastName)
                .stream()
                .map(b-> String.format("%s %s %d", b.getTitle(), b.getReleaseDate(), b.getCopies()))
                .collect(Collectors.toList());
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
