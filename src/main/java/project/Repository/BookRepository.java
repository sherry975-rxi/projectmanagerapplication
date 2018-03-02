package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
}
