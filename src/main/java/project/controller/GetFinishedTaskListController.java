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

	Company myCompany;
	UserRepository userRepository;
	Project project;
	ProjectRepository projectRepository;
	TaskRepository taskRepository;
	List<Task> finishedTaskList = new ArrayList<>();

	public List<Task> getFinishedTaskListController(Project p1) {

		myCompany = Company.getTheInstance();
		projectRepository = myCompany.getProjectsRepository();

		finishedTaskList = p1.getTaskRepository().getFinishedTasks();

		return finishedTaskList;

	}

}
