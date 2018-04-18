package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import project.model.Project;
import project.model.Task;

import java.util.List;
import java.util.Optional;

@Component
public interface TaskRepository extends JpaRepository<Task, Integer>{

	Optional<Task> findByDbTaskId(Long id);

	Optional<Task> findByTaskID(String id);

	List<Task> findAllByProject(Project project);
}
