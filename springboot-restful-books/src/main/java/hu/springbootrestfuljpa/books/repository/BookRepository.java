package hu.springbootrestfuljpa.books.repository;

import hu.springbootrestfuljpa.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}