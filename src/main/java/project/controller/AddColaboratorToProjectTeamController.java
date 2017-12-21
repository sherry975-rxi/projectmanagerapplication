package project.controller;

import project.model.Project;
import project.model.User;

public class AddColaboratorToProjectTeamController {

	public void addUserToProjectTeam(User u1, Project p1, int effort) {

		p1.addUserToProjectTeam(u1, effort);

	}
}
