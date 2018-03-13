package project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.TaskRepository;
import project.model.Task;
import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.Finished;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class TaskContainerService {

	@Autowired
	private TaskRepository taskRepository;

	public TaskContainerService() {
		
	}
	
	/**
	 * Constructor created for JPA purposes.
	 * @param taskRepository
	 */
	public TaskContainerService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	/**
	 * Create a Task using Creation Pattern and saves the Task created in the DB
	 * @param description
	 * @return A new Task object
	 */
	public Task createTask(String description) {

		Task newTask = new Task(description);
		this.taskRepository.save(newTask);
		return newTask;
	}

	
}
