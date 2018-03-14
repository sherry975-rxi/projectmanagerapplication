package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import project.Services.ProjectService;
import project.model.Project;
import project.model.User;

import java.util.ArrayList;
import java.util.List;


public class CollectProjectsFromUserController {

	private ProjectService projContainer = new ProjectService();
	private User user;

	/**
	 * Constructor
	 * 
	 */
	public CollectProjectsFromUserController(User user) {
		this.user = user;
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * @return List of Projects from user
	 */
	public List<String> getProjectsFromUserAndProjectManager() {

		List<String> myProjects = new ArrayList<>();
		for (Project ii : projContainer.getAllProjectsfromProjectsContainer()) {
			if (ii.isProjectManager(user)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName() + " - PM ");
			} else if (ii.isUserActiveInProject(user)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName());
			}
		}
		return myProjects;
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * @return List of Projects of a user
	 */
	public List<Project> getProjectsFromUser() {
		return new ArrayList<>(projContainer.getProjectsFromUser(this.user));
	}

	/**
	 * This method returns a set of Projects where a certain user was defined as
	 * Project Manager
	 * @return List of Projects of a Project Manager
	 */
	public List<Project> getProjectsFromProjectManager() {
		return new ArrayList<>(projContainer.getProjectsFromProjectManager(this.user));
	}

}
