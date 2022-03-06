package bookshop.system.repositories;

import bookshop.system.models.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM authors a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();
}
