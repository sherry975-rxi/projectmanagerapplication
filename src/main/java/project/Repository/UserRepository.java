package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByEmail(String email);

    void deleteByEmail(String email);
}
