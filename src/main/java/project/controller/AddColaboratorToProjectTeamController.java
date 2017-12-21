package project.controller;

import project.model.Project;
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
