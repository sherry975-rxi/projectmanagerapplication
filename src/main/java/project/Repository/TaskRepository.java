package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Project;
import project.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	Task findById(Long id);

	List<Task> findAllByProject(Project project);
}
