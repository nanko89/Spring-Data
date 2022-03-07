package bookshop.system;

import bookshop.system.services.AuthorService;
import bookshop.system.services.BookService;
import bookshop.system.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
       seedData();

        printAllBooksTitleAfterYear();
        System.out.println("1.  Result of all books after the year 2000. Print only their titles.");

        printAllAuthorsWithBookReleaseBeforeYear();
        System.out.println("2.  Result of all authors with at least one book with release date before 1990. " +
                "Print their first name and last name.");

        printAllAuthorOrderByBooks();
        System.out.println("3.  Result of all authors, ordered by the number of their books (descending). " +
                "Print their first name, last name and book count.\t");

        printAllBooksFromAuthor();
        System.out.println("4.  Result of all books from author George Powell, ordered by their release date " +
                "(descending), then by book title (ascending). Print the book's title, release date and copies.");


    }

    private void printAllBooksFromAuthor() {
        bookService.findAllBooksFromAuthorOrderByReleaseDateAndTitle("George", "Powell")
                .forEach(System.out::println);
    }

    private void printAllAuthorOrderByBooks() {
        authorService.getAllAuthorOrderByBooksCount()
                .forEach(System.out::println);
    }

    private void printAllAuthorsWithBookReleaseBeforeYear() {
            bookService.findAllAuthorsWithBookReleaseDateBeforeYear(1990)
                    .forEach(System.out::println);
    }

    private void printAllBooksTitleAfterYear() {
        bookService.findAllBooksAfterYear(2000)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
