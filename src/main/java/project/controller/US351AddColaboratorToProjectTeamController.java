package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;

@Controller
public class US351AddColaboratorToProjectTeamController {

	@Autowired
	public ProjectService projectService;

	/*
	 * Default constructor for controller
	 */

	public US351AddColaboratorToProjectTeamController() {

	}

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

		ProjectCollaborator projectCollaborator = projectService.createProjectCollaborator(user, project, effort);
		projectService.addProjectCollaborator(projectCollaborator);

	}

	/**
	 * Returns all the available users
	 *
	 * @return List with all the available users
	 */
	public List<User> getAllUsers() {
		UserService userContainer = new UserService();
		return userContainer.getAllActiveCollaboratorsFromRepository();
	}

	public boolean isUserAlreadyInProject(User user, Project project) {

		boolean result = false;
		if (user != null) {
			result = projectService.isUserActiveInProject(user, project);
		}
		return result;

	}

	public User searchUserByID(String id) {
		for (User other : this.getAllUsers()) {
			if (id.equals(other.getIdNumber())) {
				return other;
			}
		}
		return null;
	}

}
