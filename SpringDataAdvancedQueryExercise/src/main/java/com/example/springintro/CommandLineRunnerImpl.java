package com.example.springintro;


import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println("Enter exercise number:");

        int exerciseNumber = Integer.parseInt(bufferedReader.readLine());

        switch (exerciseNumber) {
            case 1 -> bookTitleByAgeRestriction();
            case 2 -> goldenBooks();
            case 3 -> bookByPrice();
            case 4 -> notReleaseBook();
            case 5 -> bookReleasedBeforeDate();
            case 6 -> authorSearch();
            case 7 -> bookSearch();
            case 8 -> bookTitlesSearch();
            case 9 -> countBooks();
            case 10 -> totalBookCopies();
            case 11 -> reduceBook();
            case 12 -> increaseBookCopies();
            case 13 -> removeBooks();
            case 14 -> storedProcedure();
            default -> System.out.println("Invalid exercise number!");
        }
    }

    private void storedProcedure() throws IOException {
        System.out.println("Enter the full name of the author:");
        String[] fullName = bufferedReader.readLine().split("\\s+");
        int books = bookService.findTotalNumberOfBookByAuthor(fullName[0], fullName[1]);

        System.out.printf("%s %s has written %d books%n", fullName[0], fullName[1], books);

        //TODO:Make this procedure before start!
//        CREATE PROCEDURE find_count_of_books (In first_name varchar(40), In last_name varchar(40), OUT books_count INT )
//        BEGIN
//        SELECT COUNT(*) into books_count FROM books AS b
//        JOIN authors a on a.id = b.author_id
//        WHERE a.first_name = first_name AND a.last_name = last_name;
//        end;
    }

    private void removeBooks() throws IOException {
        System.out.println("Enter number of books copies:");
        int copies = Integer.parseInt(bufferedReader.readLine());

        System.out.println(bookService.removeBooksWithCopiesLowerThen(copies));
    }

    private void increaseBookCopies() throws IOException {
        System.out.println("Enter date in format dd MMM yyyy(01 Jun 1999):");
        String date = bufferedReader.readLine();

        LocalDate currentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));

        System.out.println("Enter book copies:");
        int copies = Integer.parseInt(bufferedReader.readLine());

        int increaseBooks = bookService.increaseAllBooksWithReleaseDateAfter(currentDate, copies);

        System.out.println(increaseBooks * copies);
    }

    private void bookTitleByAgeRestriction() throws IOException {
        System.out.println("Enter one of this Age Restriction (minor, teen or adult):");

        String restriction = bufferedReader.readLine();

        bookService.findAllBooksTitleByAgeRestriction(restriction)
                .forEach(System.out::println);
    }

    private void goldenBooks() {
        bookService.findAllGoldenBooksTitleWith5000Copies(5000)
                .forEach(System.out::println);
    }

    private void bookByPrice() {
        bookService.findAllBooksWithCurrentPrice(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(System.out::println);
    }

    private void notReleaseBook() throws IOException {
        System.out.println("Enter year(yyyy) to find books:");

        String year = bufferedReader.readLine();

        bookService.findAllBooksNOTReleaseInYear(year)
                .forEach(System.out::println);
    }

    private void bookReleasedBeforeDate() throws IOException {
        System.out.println("Enter date in the format dd-MM-yyyy:");

        String dateStr = bufferedReader.readLine();

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findAllBooksReleaseBeforeDate(date)
                .forEach(System.out::println);
    }

    private void authorSearch() throws IOException {
        System.out.println("Enter symbol or string that ends the author's name:");

        String endWith = bufferedReader.readLine();

        authorService.findAllAuthorsFirstNameFinishWithString(endWith)
                .forEach(System.out::println);
    }

    private void bookSearch() throws IOException {
        System.out.println("Enter a symbol or string that is contained in the title of book:");

        String input = bufferedReader.readLine();

        bookService.findAllBooksThatContainsString(input)
                .forEach(System.out::println);
    }

    private void bookTitlesSearch() throws IOException {
        System.out.println("Enter string for beginning the author's last name:");

        String startLastName = bufferedReader.readLine();

        bookService.findAllBooksByAuthorFirstNameStartWith(startLastName)
                .forEach(System.out::println);
    }

    private void countBooks() throws IOException {
        System.out.println("Enter the minimum length of the book title:");

        int length = Integer.parseInt(bufferedReader.readLine());

        int countBooks = bookService.countAllBooksByTitleLongerThen(length);

        System.out.println(countBooks);
    }

    private void totalBookCopies() {
        authorService.findAllBooksCopiesByTheirAuthor()
                .forEach(System.out::println);
    }

    private void reduceBook() throws IOException {
        System.out.println("Enter title of book:");

        String title = bufferedReader.readLine();

        System.out.println(bookService.findBookInfoByTitle(title));
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastName(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
