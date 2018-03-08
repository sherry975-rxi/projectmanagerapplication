package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByEmail(String email);
}
