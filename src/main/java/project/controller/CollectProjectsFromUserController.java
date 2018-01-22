package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.ProjectRepository;
import project.model.User;

public class CollectProjectsFromUserController {

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
	 * 
	 * @param User
	 * 
	 * @return List of Projects from user
	 */
	public List<String> getProjectsFromUserAndProjectManager() {

		List<String> myProjects = new ArrayList<>();
		ProjectRepository myProjRepo = Company.getTheInstance().getProjectsRepository();
		for (Project ii : myProjRepo.getAllProjects()) {
			if (ii.isUserActiveInProject(user)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName());
			} else if (ii.isProjectManager(user)) {
				myProjects.add("[" + ii.getIdCode() + "]" + " " + ii.getName() + " - PM ");
			}
		}
		return myProjects;
	}

	/**
	 * This method returns a set of Projects where a certain user
	 * 
	 * @param User
	 * 
	 * @return List of Projects of a user
	 */
	public List<Project> getProjectsFromUser() {
		List<Project> listOfProjectsOfUser = new ArrayList<>();
		listOfProjectsOfUser.addAll(Company.getTheInstance().getProjectsRepository().getProjectsFromUser(this.user));
		return listOfProjectsOfUser;
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
	public List<Project> getProjectsFromProjectManager() {
		List<Project> listOfProjectsOfProjectManager = new ArrayList<>();
		listOfProjectsOfProjectManager
				.addAll(Company.getTheInstance().getProjectsRepository().getProjectsFromProjectManager(this.user));
		return listOfProjectsOfProjectManager;
	}

}
