package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class CollectProjectsFromUserController {

	User user;

	/**
	 * Constructor
	 * 
	 */
	public CollectProjectsFromUserController(User user) {
		this.user = user;
	}

	/**
	 * This method returns a set of Projects where a certain user was defined as
	 * Project Manager
	 * 
	 * @param projectManager
	 *            User defined as Project Manager
	 * 
	 * @return List of Projects of a Project Manager
	 */
	public List<Project> getProjectsFromUser() {

		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();

		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsFromUser(this.user));

		return listOfProjectsOfProjectManager;
	}

}
