package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.BookDetail;

public interface BookDetailRepository extends JpaRepository<BookDetail, Integer>{
}
