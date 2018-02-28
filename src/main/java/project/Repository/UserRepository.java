package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Book;
import project.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
}
