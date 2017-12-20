package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.TaskRepository;
import project.model.UserRepository;;

public class GetFinishedTaskListController {

	Company myCompany = Company.getTheInstance();
	UserRepository userRepository;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	List<Task> finishedTaskList = new ArrayList<>();

	public List<Task> getFinishedTaskListController(Project p1) {

		try {
			projectRepository = myCompany.getProjectsRepository();
			for (Project other : projectRepository.getAllProjects()) {
				if (other.equals(p1)) {
					finishedTaskList = p1.getTaskRepository().getFinishedTasks();
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Project does not exist!");
		}

		return finishedTaskList;

	}

}
