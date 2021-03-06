package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastName(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal less, BigDecimal greater );

   @Query("SELECT b FROM Book b WHERE SUBSTRING(b.releaseDate, 1, 4) <> :year")
    List<Book> findAllByReleaseDateNotInYear(String year);

   List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

   List<Book> findAllByTitleContaining(String title);

   List<Book> findAllByAuthor_LastNameStartsWith(String lastName);

   @Query("SELECT COUNT(b) FROM Book b where length(b.title) > :length")
   int countBookByTitleLengthGreaterThan(int length);

   Book findByTitleEquals(String title);

   @Modifying
   @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :currentDate")
   int increaseCopiesOfBooks(LocalDate currentDate, int copies);

   int deleteBookByCopiesLessThan(Integer copies);

   @Procedure("find_count_of_books")
   Integer findBookByAuthorName(@Param("first_name") String firstName, @Param("last_name") String lastName);
}
