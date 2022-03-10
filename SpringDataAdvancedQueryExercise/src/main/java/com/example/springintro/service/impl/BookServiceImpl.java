package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
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

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllBooksByAuthorFirstAndLastName(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastName(firstName, lastName);
    }

    @Override
    public List<String> findAllBooksTitleByAgeRestriction(String restriction) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());
        return bookRepository.findAllByAgeRestrictionEquals(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGoldenBooksTitleWith5000Copies(int copies) {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD,copies)
                .stream().map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithCurrentPrice(BigDecimal lower, BigDecimal higher) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lower, higher)
                .stream()
                .map(b -> String.format("%s - $ %.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksNOTReleaseInYear(String year) {

        LocalDate lower = LocalDate.of(Integer.parseInt(year), 1,1);
        LocalDate upper = LocalDate.of(Integer.parseInt(year), 12, 31);

        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lower, upper)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

//        return bookRepository.findAllByReleaseDateNotInYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksReleaseBeforeDate(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(b -> String.format("%s %s %.2f", b.getTitle(), b.getEditionType().name(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksThatContainsString(String input) {
        return bookRepository.findAllByTitleContaining(input)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstNameStartWith(String startLastName) {
        return bookRepository.findAllByAuthor_LastNameStartsWith(startLastName)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int countAllBooksByTitleLongerThen(int length) {

        return bookRepository.countBookByTitleLengthGreaterThan(length);
    }

    @Override
    public String findBookInfoByTitle(String title) {
        Book book = bookRepository.findByTitleEquals(title);
        return String.format("%s %s %s %.2f",
                book.getTitle(),
                book.getEditionType().name(),
                book.getAgeRestriction().name(),
                book.getPrice());
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
