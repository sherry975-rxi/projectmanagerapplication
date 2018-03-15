package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	Task findById(Long id);
}
