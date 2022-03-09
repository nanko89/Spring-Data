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

        switch (exerciseNumber){
            case 1 :
                System.out.println("Enter one of this Age Restriction (minor, teen or adult):");
                String restriction = bufferedReader.readLine();
                 bookService.findAllBooksTitleByAgeRestriction(restriction)
                    .forEach(System.out::println);
                 break;
            case 2:
                bookService.findAllGoldenBooksTitleWith5000Copies(5000)
                    .forEach(System.out::println);
                break;
            case 3:
                bookService.findAllBooksWithCurrentPrice(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                        .forEach(System.out::println);
                break;
            case 4:
                System.out.println("Enter year(yyyy) to find books:");
                String  year =bufferedReader.readLine();
                bookService.findAllBooksNOTReleaseInYear(year)
                        .forEach(System.out::println);
                break;
            case 5:
                System.out.println("Enter date in the format dd-MM-yyyy:");
                String dateStr = bufferedReader.readLine();
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }


        bookService.findAllBooksWithCurrentPrice(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(System.out::println);


    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
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
