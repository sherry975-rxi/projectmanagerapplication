package project.controller;

import java.util.List;

import project.model.Project;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;

public class AddColaboratorToProjectTeamController {

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
}
