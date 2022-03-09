package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBooksTitleByAgeRestriction(String restriction);

    List<String> findAllGoldenBooksTitleWith5000Copies(int copies);

    List<String> findAllBooksWithCurrentPrice(BigDecimal lower, BigDecimal higher);

    List<String> findAllBooksNOTReleaseInYear(String  year);
}
