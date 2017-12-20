package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.Task;
import project.model.UserRepository;;

public class GetFinishedTaskListController {

	Company myCompany;
	UserRepository userRepository;
	Project project;
	ProjectRepository projectRepository;

	public List<Task> getFinishedTaskListController() {

		List<Task> finishedTaskList = new ArrayList<>();

		myCompany = Company.getTheInstance();

		return finishedTaskList;

	}

}
