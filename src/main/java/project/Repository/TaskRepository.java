package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import project.model.Project;
import project.model.Task;

import java.util.List;

@Component
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	Task findById(Long id);

	Task findByTaskID(String id);

	List<Task> findAllByProject(Project project);
}
