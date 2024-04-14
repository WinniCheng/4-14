package hkmu.comps380f.onlinebookstoreapp.dao;

import hkmu.comps380f.onlinebookstoreapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
