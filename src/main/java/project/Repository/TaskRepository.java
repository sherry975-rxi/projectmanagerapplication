package project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Project;
import project.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
	
	Task findById(Long id);

	Task findByTaskId(String id);

	List<Task> findAllByProject(Project project);
}
