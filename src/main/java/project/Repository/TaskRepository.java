package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Task;
import project.model.User;

public interface TaskRepository extends JpaRepository<Task, Integer>{
}
