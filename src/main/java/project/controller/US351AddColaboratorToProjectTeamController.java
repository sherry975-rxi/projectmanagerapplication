package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class US351AddColaboratorToProjectTeamController {

	/**
	 * This controller add user to project team
	 * 
	 * respond to US 350
	 * 
	 * @param user
	 * @param project
	 * @param effort
	 */
	public void addUserToProjectTeam(User user, Project project, int effort) {

		project.addUserToProjectTeam(user, effort);

	}

	/**
	 * Returns all the available users
	 *
	 * @return List with all the available users
	 */
	public List<User> getAllUsers() {
		return Company.getTheInstance().getUsersRepository().getAllActiveCollaboratorsFromRepository();
	}

	/**
	 * Returns all the available projects
	 *
	 * @return List with all the available projects
	 */
	public List<Project> getAllProjects() {
		return Company.getTheInstance().getProjectsRepository().getActiveProjects();
	}

}
